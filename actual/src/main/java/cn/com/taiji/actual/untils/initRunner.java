package cn.com.taiji.actual.untils;

import cn.com.taiji.actual.domain.UserInfo;
import cn.com.taiji.actual.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author zxx
 * @version 1.0
 * @date 2018/12/19 19:33
 */
@Component
public class initRunner implements CommandLineRunner {
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    RedisTemplate redisTemplate;
    @Override
    public void run(String... args) throws Exception {
        List<UserInfo> all = userInfoService.findAll();
        String namespace = "users:";
        String key = null;
        for (UserInfo user:all) {
            if("1".equals(user.getState())){
                key=namespace+user.getUsername();
                redisTemplate.opsForValue().set(key,user,60, TimeUnit.MINUTES);
            }
        }
    }
}
