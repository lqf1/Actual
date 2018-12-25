package cn.com.taiji.actual.service;

import cn.com.taiji.actual.domain.Article;
import cn.com.taiji.actual.domain.DiscussionGroup;

import java.util.List;
import java.util.Map;

/**
 * @author Barry
 * @version v1.0
 * @description 帖子服务层接口，定义方法但不实现
 * @date created on 2018/12/20 9:18
 */

public interface ArticleService {

    /**
     * 发布一个新的帖子
     *
     * @param article 帖子的标题
     * @param content 帖子的内容
     */
    void addArticle(Article article, String content, DiscussionGroup group, String username);

    /**
     * 根据帖子标题查询帖子
     *
     * @param articleName 帖子名
     * @return 帖子的所有内容
     */
    Article findArticleByAName(String articleName);
    /**
    * @author LWL
     * 分页查询所有帖子
     * @return
     */
    Map findPagination(Integer page,Integer id);

    /**
     * 前台讨论组显示帖子所有
     * @return
     */
    List<Article> findShow();

    /**
     * 根据id查询单个
     *
     * @param id
     * @return
     */
    Article findById(Integer id);


}
