package cn.com.taiji.actual.repository;

import cn.com.taiji.actual.domain.Permission;
import cn.com.taiji.actual.domain.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author zxx
 * @date 2018/12/14 11:39
 * @version 1.0
 */
public interface PermissionRepository extends JpaRepository<Permission,Integer>, JpaSpecificationExecutor<Permission> {
    /**
     * 根据id删除(操作状态state=0)
     * @param pid
     * @param state
     */
    @Modifying
    @Query("update Permission set state=:state where pid=:pid")
    void deleteById(@Param("pid") Integer pid, @Param("state") String state);

    /**
     * 查询未删除的
     * @param state
     * @return
     */
    List<Permission> findByState(String state);
}
