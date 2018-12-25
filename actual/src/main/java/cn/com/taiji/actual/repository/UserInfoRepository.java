package cn.com.taiji.actual.repository;

import cn.com.taiji.actual.domain.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author zxx
 * @version 1.0
 * @date 2018/12/14 11:39
 */
public interface UserInfoRepository extends JpaRepository<UserInfo, Integer>, JpaSpecificationExecutor<UserInfo> {
    /**
     * 根据id删除(操作状态state=0)
     *
     * @param uid   用户id
     * @param state 状态
     */
    @Modifying
    @Query("update UserInfo set state=:state where uid=:uid")
    void deleteById(@Param("uid") Integer uid, @Param("state") String state);

    /**
     * 根据id修改密码
     *
     * @param uid      用户id
     * @param password 修改的密码
     */
    @Modifying
    @Query("update UserInfo set password=:password where uid=:uid")
    void resetPassword(@Param("uid") Integer uid, @Param("password") String password);


    /**
     * 根据用户名查找
     *
     * @param username 用户名
     * @param state    状态
     * @return 用户实体
     */
    UserInfo findByUsernameAndAndState(String username, String state);

    /**
     * 根据用户名查询用户信息
     *
     * @param username
     * @return
     */
    UserInfo findUserInfoByUsername(String username);

}
