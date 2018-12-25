package cn.com.taiji.actual.repository;

import cn.com.taiji.actual.domain.Role;
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
public interface RoleRepository extends JpaRepository<Role,Integer>, JpaSpecificationExecutor<Role> {
    /**
     * 根据id删除(操作状态state=0)
     * @param rid
     * @param state
     */
    @Modifying
    @Query("update Role set state=:state where rid=:rid")
    void deleteById(@Param("rid") Integer rid, @Param("state") String state);

    /**
     * 根据角色名查询
     * @param RoleName
     * @return
     */
    Role findByRoleName(String RoleName);
    /**
     * 查所有未删除的
     * @param state
     * @return
     */
    List<Role> findByState(String state);
}
