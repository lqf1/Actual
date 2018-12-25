package cn.com.taiji.actual.service.impl;

import cn.com.taiji.actual.domain.Article;
import cn.com.taiji.actual.domain.Comment;
import cn.com.taiji.actual.repository.CommentRepository;
import cn.com.taiji.actual.service.ArticleService;
import cn.com.taiji.actual.service.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author Barry
 * @version v1.0
 * @description
 * @date created on 2018/12/20 10:35
 */

@Service
public class CommentServiceImpl implements CommentService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private CommentRepository commentRepository;
    private ArticleService articleServiceImpl;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository,
                              ArticleService articleServiceImpl) {
        this.commentRepository = commentRepository;
        this.articleServiceImpl = articleServiceImpl;
    }

    @Override
    public List<Comment> findAllComment(Article article) {
        return commentRepository.findCommentsByArticle(article);
    }

    @Override
    public void addComment(Comment comment, String articleName) {
        Article article = articleServiceImpl.findArticleByAName(articleName);
        comment.setCreateDate(new Date());
        comment.setState("1");
        comment.setArticle(article);
        commentRepository.saveAndFlush(comment);
    }
}
