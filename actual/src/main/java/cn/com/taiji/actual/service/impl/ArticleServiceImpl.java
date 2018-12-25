package cn.com.taiji.actual.service.impl;
import cn.com.taiji.actual.domain.Article;
import cn.com.taiji.actual.domain.DiscussionGroup;
import cn.com.taiji.actual.domain.UserInfo;
import cn.com.taiji.actual.repository.ArticleRepository;
import cn.com.taiji.actual.repository.UserInfoRepository;
import cn.com.taiji.actual.service.ArticleService;
import cn.com.taiji.actual.untils.PaginationUntil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.util.*;

/**
 * @author Barry
 * @version v1.0
 * @description 帖子服务层的实现类，实现接口层的所有方法
 * @date created on 2018/12/20 9:19
 */

@Service
public class ArticleServiceImpl implements ArticleService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private ArticleRepository articleRepository;
    private UserInfoRepository userInfoRepository;

    @Autowired
    public ArticleServiceImpl(ArticleRepository articleRepository,
                              UserInfoRepository userInfoRepository) {
        this.articleRepository = articleRepository;
        this.userInfoRepository = userInfoRepository;
    }

    @Override
    public void addArticle(Article article, String content, DiscussionGroup group, String username) {
        logger.info("article's name is {} and content is {}", article.getAName(), content);
        article.setAContent(content.getBytes());
        article.setCreateDate(new Date());
        article.setState("1");
        article.setDisGroup(group);
        article.setUserInfo(userInfoRepository.findByUsernameAndAndState(username,"1"));
        articleRepository.saveAndFlush(article);
    }

    @Override
    public Article findArticleByAName(String articleName) {
        return articleRepository.findArticleByAName(articleName);
    }



    @Override
    public Map findPagination(Integer page,Integer disId) {
        //生成pageable
        Map map = new HashMap();
        map.put("page",page);
        map.put("pageSize",10);
        Pageable pageable = PaginationUntil.getPage(map);
        //构建查询条件
        Specification<Article> specification = new Specification<Article>() {
            @Override
            public Predicate toPredicate(Root<Article> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                Join<DiscussionGroup,Article> joins = root.join("DisGroup");
                // 查询出未删除的
                predicates.add(cb.equal(joins.get("did"), disId));

                predicates.add(cb.equal(root.<Integer>get("state"), 1));
                return cb.and(predicates.toArray(new Predicate[0]));
            }
        };
        Page<Article> pageList = articleRepository.findAll(specification, pageable);
        Map result = new HashMap();
        int pageSize = (int)pageList.getTotalElements();
        if(pageSize%10==0){
            result.put("total",pageSize/10);
        }else{
            result.put("total",(pageSize/10)+1);
        }
        if(pageSize==0){
            result.put("total",1);
        }
        result.put("page", pageList.getNumber()+1);
        List<Article> list = pageList.getContent();
        result.put("article",list);
        return result;
    }

    @Override
    public List<Article> findShow(){
        List<Article> articles=articleRepository.findByStateOrderByCreateDateDesc("1");
        List<Article> result =articles.subList(0,2);
        return result;


    }

    @Override
    public Article findById(Integer id) {
        return articleRepository.findOne(id);
    }

}
