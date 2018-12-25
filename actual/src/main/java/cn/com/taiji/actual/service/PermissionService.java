package cn.com.taiji.actual.service;

import cn.com.taiji.actual.domain.Permission;
import cn.com.taiji.actual.domain.Role;

import java.util.List;
import java.util.Map;

/**
 * @author zxx
 * @version 1.0
 * @date 2018/12/18 10:21
 */
public interface PermissionService {
    /**
     * 查未删除的
     * @param state 状态
     * @return 权限实体List
     */
    List<Permission> findByState(String state);
    /**
     * 根据id查询单个
     * @param id id
     * @return 权限实体
     */
    Permission findById(Integer id);
    /**
     * 分页显示权限
     * @param page 页数
     * @return 存有分页信息和查出数据的map
     */
    Map findPagination(Integer page);

    /**
     * 删除角色
     * @param id id
     */

    void deleteById(Integer id);

    /**
     * 新增权限
     * @param permission 权限实体
     */
    void addPermission(Permission permission);

    /**
     * 更新权限
     * @param permission 权限实体
     */
    void updatePermission(Permission permission);
}
