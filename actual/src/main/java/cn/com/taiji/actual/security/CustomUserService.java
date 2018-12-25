package cn.com.taiji.actual.security;

import cn.com.taiji.actual.domain.Permission;
import cn.com.taiji.actual.domain.Role;
import cn.com.taiji.actual.domain.UserInfo;
import cn.com.taiji.actual.repository.UserInfoRepository;
import cn.com.taiji.actual.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
/**
 * 自定义UserDetailsService 接口
 * @author zxx
 * @date 2018/12/18 15:11
 * @version 1.0
 */
@Service
public class CustomUserService implements UserDetailsService {

    @Autowired
    private UserInfoService userInfoService;


    @Override
    public UserDetails loadUserByUsername(String username) {
        //重写loadUserByUsername 方法获得 userdetails 类型用户
        UserInfo user = userInfoService.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        //获取用户的权限
        List<Permission> permissions = new ArrayList<>();
        List<Role> roles = user.getRoles();
        for (Role role:roles) {
            permissions.addAll(role.getPermissions());
        }
       List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        //添加用户权限到grantedAuthorities
        for (Permission permission : permissions) {
            if (permission != null && permission.getPermissionName() != null) {

                GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(permission.getPermissionName());
                grantedAuthorities.add(grantedAuthority);
            }
        }
        return new org.springframework.security.core.userdetails.User
                (user.getUsername(), user.getPassword(), grantedAuthorities);

    }


}
