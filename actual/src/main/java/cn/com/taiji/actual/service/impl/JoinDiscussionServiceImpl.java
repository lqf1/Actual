package cn.com.taiji.actual.service.impl;


import cn.com.taiji.actual.domain.UserInfo;

import cn.com.taiji.actual.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author LWL
 * @version 1.0
 * @description
 * @date 2018/12/24 10:07
 */
@Service
public class JoinDiscussionServiceImpl {

    @Autowired
    private UserInfoRepository userInfoRepository;

    /**
     * 在个人中心显示加入的讨论组
     * @param username
     * @return
     */
    public UserInfo findUser(String username){
        return userInfoRepository.findByUsernameAndAndState(username,"1");
    }


}
