package cn.com.taiji.actual.repository;

import cn.com.taiji.actual.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
/**
 * @author Barry
 * @version v1.0
 * @description 帖子接口层，对接数据库
 * @date created on 2018/12/20 9:17
 */

public interface ArticleRepository extends JpaRepository<Article, Integer>, JpaSpecificationExecutor<Article> {

    /**
     * 根据帖子标题查询帖子
     *
     * @param aName 帖子挑剔
     * @return 帖子的所有内容
     */
    Article findArticleByAName(String aName);

//
//    @Query("select Article from Article where disGroup=:disGroup")
//    List<Article> selectById(@Param("disGroup") Integer disGroup);


   List<Article> findByStateOrderByCreateDateDesc(String state);
}
