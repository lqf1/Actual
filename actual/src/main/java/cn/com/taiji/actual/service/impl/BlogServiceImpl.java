package cn.com.taiji.actual.service.impl;

import cn.com.taiji.actual.domain.Blog;
import cn.com.taiji.actual.domain.UserInfo;
import cn.com.taiji.actual.repository.BlogRepository;
import cn.com.taiji.actual.service.BlogService;
import cn.com.taiji.actual.service.UserInfoService;
import cn.com.taiji.actual.untils.PaginationUntil;
import cn.com.taiji.actual.untils.Result;
import org.hibernate.loader.plan.spi.Return;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

/**
 * @author Barry
 * @version v1.0
 * @description 博客服务层实现类，实现接口层的所有方法
 * @date created on 2018/12/18 15:29
 */

@Service
public class BlogServiceImpl implements BlogService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private BlogRepository blogRepository;
    private UserInfoService userInfoServiceImpl;

    @Autowired
    public BlogServiceImpl(BlogRepository blogRepository,
                           UserInfoService userInfoServiceImpl) {
        this.blogRepository = blogRepository;
        this.userInfoServiceImpl = userInfoServiceImpl;
    }

    @Override
    public void addBlog(Blog blog, String content, String loginName) {
        byte[] bContent = content.getBytes();
        blog.setBContent(bContent);
        logger.info("blog's info is {}", blog);
        blog.setCreateDate(new Date());
        blog.setState("1");
        blog.setUserInfo(userInfoServiceImpl.findByUsername(loginName));
        blogRepository.saveAndFlush(blog);
    }

    @Override
    public Blog findBlogByBName(Blog blog) {
        return blogRepository.findBlogByBName(blog.getBName());
    }

    @Override
    public Map findPagination(Integer page) {
        Integer pageNum = 10;
        //生成pageable
        Map<String, Object> map = new HashMap<>(16);
        map.put("page", page);
        map.put("pageSize", 10);
        Pageable pageable = PaginationUntil.getPage(map);
        //构建查询条件
        Specification<Blog> specification = new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                // 查询出未删除的
                predicates.add(cb.equal(root.<Integer>get("state"), 1));
                return cb.and(predicates.toArray(new Predicate[0]));
            }
        };
        Page<Blog> pageList = blogRepository.findAll(specification, pageable);
        Map<String, Object> result = new HashMap<>(16);
        int pageSize = (int) pageList.getTotalElements();
        if (pageSize % pageNum == 0) {
            result.put("total", pageSize / pageNum);
        } else {
            result.put("total", (pageSize / pageNum) + 1);
        }
        if(pageSize==0){
            result.put("total",1);
        }
        result.put("page", pageList.getNumber() + 1);
        List<Blog> list = pageList.getContent();
        result.put("blogs", list);
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteById(Integer id) {
        blogRepository.deleteById(id, "0");
    }

    @Override
    public Blog findById(Integer id) {
        return blogRepository.findOne(id);
    }

    @Override
    public List<Blog> findAll(){
     List<Blog> result=blogRepository.findByStateOrderByCreateDateDesc("1");
     return result;
    }

}
