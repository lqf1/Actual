package cn.com.taiji.actual;

import cn.com.taiji.actual.domain.UserInfo;
import cn.com.taiji.actual.repository.UserInfoRepository;
import cn.com.taiji.actual.service.ArticleService;
import cn.com.taiji.actual.service.BlogService;
import cn.com.taiji.actual.service.DiscussionGroupService;
import cn.com.taiji.actual.service.UserInfoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Date;
import java.util.Map;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ActualApplicationTests {
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired
    private BlogService blogService;
    @Autowired
    private  ArticleService articleService;
    @Autowired
    private  DiscussionGroupService discussionGroupService;
    Logger logger = LoggerFactory.getLogger(getClass());

    @Test
    public void contextLoads() {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encode = passwordEncoder.encode("123456");
        logger.info(encode);
    }
    @Test
    public  void  setBlogService(){

        System.out.println(blogService.findAll());
    }

    @Test
    public void   Group(){
        System.out.println(discussionGroupService.findShow());
    }
    @Test
    public void   Article(){
        System.out.println(articleService.findShow());
    }
}

