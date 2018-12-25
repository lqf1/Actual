package cn.com.taiji.actual.repository;

import cn.com.taiji.actual.domain.Article;
import cn.com.taiji.actual.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @author Barry
 * @version v1.0
 * @description
 * @date created on 2018/12/20 10:34
 */

public interface CommentRepository extends JpaRepository<Comment, Integer>, JpaSpecificationExecutor<Comment> {

    List<Comment> findCommentsByArticle(Article article);
}
