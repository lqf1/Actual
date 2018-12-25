package cn.com.taiji.actual.service;

import cn.com.taiji.actual.domain.Article;
import cn.com.taiji.actual.domain.Comment;

import java.util.List;

/**
 * @author Barry
 * @version v1.0
 * @description
 * @date created on 2018/12/20 10:35
 */

public interface CommentService {

    /**
     * 查询帖子下的所有评论
     *
     * @return 所有评论
     */
    List<Comment> findAllComment(Article article);

    /**
     * 对帖子发表评论
     *
     * @param comment 评论
     */
    void addComment(Comment comment, String articleName);
}
