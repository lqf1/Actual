package cn.com.taiji.actual.service;

import cn.com.taiji.actual.domain.DiscussionGroup;
import cn.com.taiji.actual.domain.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * @author LWL
 * @version 1.0
 * @description
 * @date 2018/12/17 11:16
 */
public interface DiscussionGroupService {
    /**
     * 查询所有讨论组
     * @return
     */
    List<DiscussionGroup> findAll();

    /**
     * 分页显示讨论组
     * @param page
     * @return
     */
    Map findPagination(Integer page);

    /**
     * 根据id删除单个讨论组
     * @param id
     * @return
     */
    void deleteById(Integer id);

    /**
     * 新增讨论组
     * @param discussionGroup
     */
    void addDiscussion(DiscussionGroup discussionGroup);

    /**
     * 更新讨论组
     * @param discussionGroup
     */
    void updateDiscussion(DiscussionGroup discussionGroup);

    /**
     * 根据id查询单个
     * @param id
     * @return
     */
    DiscussionGroup findById(Integer id);


    /**
     * 主页查所有
     */
    List<DiscussionGroup> findShow();

    /**
     * 根据id删除单个讨论组
     * @param id
     * @return
     */
    void deleteArticleById(Integer id);
}
