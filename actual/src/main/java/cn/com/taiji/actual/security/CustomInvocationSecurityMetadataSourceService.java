package cn.com.taiji.actual.security;

import cn.com.taiji.actual.domain.Permission;
import cn.com.taiji.actual.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author zxx
 * @date 2018/12/18 15:11
 * @version 1.0
 */
@Service
public class CustomInvocationSecurityMetadataSourceService implements
        FilterInvocationSecurityMetadataSource {

    @Autowired
    private PermissionRepository permissionRepository;

    private HashMap<String, Collection<ConfigAttribute>> map = null;

    /**
     * 加载权限资源，初始化资源变量
     */
    public void loadResourceDefine() {
        map = new HashMap<>();
        List<Permission> permissions = permissionRepository.findByState("1");
        for (Permission permission : permissions) {
            Collection<ConfigAttribute> array = new ArrayList<>();
            ConfigAttribute cfg = new SecurityConfig(permission.getPermissionName());
            array.add(cfg);
            map.put(permission.getUrl(), array);
        }
    }

    /**
     * 方法返回本次访问需要的权限，可以有多个权限。在上面的实现中如果没有匹配的url直接返回null
     * www也就是没有配置权限的url默认都为白名单，想要换成默认是黑名单只要修改这里即可。
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        if (map == null) {
            loadResourceDefine();
        }

        HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();
        AntPathRequestMatcher matcher;
        String resUrl;
        for (Iterator<String> iter = map.keySet().iterator(); iter.hasNext(); ) {
            resUrl = iter.next();
            matcher = new AntPathRequestMatcher(resUrl);
            if (matcher.matches(request)) {
                return map.get(resUrl);
            }
        }
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
