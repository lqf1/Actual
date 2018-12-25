package cn.com.taiji.actual.service;

import cn.com.taiji.actual.domain.Blog;

import java.util.List;
import java.util.Map;

/**
 * @author Barry
 * @version v1.0
 * @description 博客服务层接口，定义接口但不实现
 * @date created on 2018/12/18 15:29
 */

public interface BlogService {

    /**
     * 发布一条新的博客
     *
     * @param blog 博客的标题和内容
     */
    void addBlog(Blog blog, String content, String loginName);

    /**
     * 根据博客名查询博客的详细信息
     *
     * @param blog 博客名
     * @return 博客的详细信息
     */
    Blog findBlogByBName(Blog blog);

    /**
     * 分页显示博客
     * @author zxx
     * @param page 页数
     * @return 存有分页信息和查出数据的map
     */
    Map findPagination(Integer page);

    /**
     * 删除博客
     * @author zxx
     * @param id id
     */
    void deleteById(Integer id);

    /**
     * 根据id查询单个
     * @author zxx
     * @param id id
     * @return 博客实体
     */
    Blog findById(Integer id);

    /**
     * 排序查所有
     */
    List<Blog> findAll();


}

