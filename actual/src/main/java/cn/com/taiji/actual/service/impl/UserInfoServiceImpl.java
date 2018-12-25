package cn.com.taiji.actual.service.impl;

import cn.com.taiji.actual.domain.DiscussionGroup;
import cn.com.taiji.actual.domain.Role;
import cn.com.taiji.actual.domain.UserInfo;
import cn.com.taiji.actual.repository.DiscussionGroupRepository;
import cn.com.taiji.actual.repository.RoleRepository;
import cn.com.taiji.actual.repository.UserInfoRepository;
import cn.com.taiji.actual.service.UserInfoService;
import cn.com.taiji.actual.untils.PaginationUntil;
import org.hibernate.annotations.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

/**
 * 用户相关操作Service的实现类
 *
 * @author zxx
 * @version 1.0
 * @date 2018/12/16 20:35
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private DiscussionGroupRepository groupRepository;

    Logger logger =LoggerFactory.getLogger(getClass());
    @Override
    @Cacheable(value = "users",key = "#id")
    public UserInfo findById(Integer id) {
        logger.info("进入查询数据库方法");
        return userInfoRepository.findOne(id);
    }

    @Override
    @Cacheable(value = "users",key = "#username")
    public UserInfo findByUsername(String username) {
        return userInfoRepository.findByUsernameAndAndState(username,"1");
    }

    @Override

    public List<UserInfo> findAll() {
        return userInfoRepository.findAll();
    }

    @Override
    public Map findPagination(Integer page) {
        Integer pageNum = 10;
        //生成pageable
        Map map = new HashMap(16);
        map.put("page", page);
        map.put("pageSize", 10);
        Pageable pageable = PaginationUntil.getPage(map);
        //构建查询条件
        Specification<UserInfo> specification = new Specification<UserInfo>() {
            @Override
            public Predicate toPredicate(Root<UserInfo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                // 查询出未删除的
                predicates.add(cb.equal(root.<Integer>get("state"), 1));
                return cb.and(predicates.toArray(new Predicate[0]));
            }
        };
        Page<UserInfo> pageList = userInfoRepository.findAll(specification, pageable);
        Map result = new HashMap(16);
        int pageSize = (int) pageList.getTotalElements();
        if (pageSize % pageNum == 0) {
            result.put("total", pageSize / pageNum);
        } else {
            result.put("total", (pageSize / pageNum) + 1);
        }
        if (pageSize == 0) {
            result.put("total", 1);
        }
        result.put("page", pageList.getNumber() + 1);
        List<UserInfo> list = pageList.getContent();
        result.put("users", list);
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)

    @Caching(
            evict = {
                    @CacheEvict(value = "users",beforeInvocation = true,key = "#id"),
            }
    )
    public void deleteById(Integer id) {
        userInfoRepository.deleteById(id, "0");
    }

    @Override
    @Caching(
            put = {
                    @CachePut(value = "users",key = "#result.uid")
            },
            evict = {
                    @CacheEvict(value = "users",key = "#result.username")
            }
    )
    public void addUser(UserInfo userInfo) {
        userInfo.setCreateDate(new Date());
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encode = passwordEncoder.encode("123456");
        userInfo.setPassword(encode);
        userInfo.setState("1");
        Role role = roleRepository.findByRoleName("ROLE_USER");
        List<Role> list = new ArrayList<>();
        list.add(role);
        userInfo.setRoles(list);
        userInfoRepository.saveAndFlush(userInfo);
    }

    @Override
    @Caching(
            put = {
                    @CachePut(value = "users",key = "#result.uid")
            },
            evict = {
                    @CacheEvict(value = "users",key = "#result.username")
            }
    )
    public UserInfo updateUser(UserInfo userInfo) {
        UserInfo user = userInfoRepository.findOne(userInfo.getUid());
        user.setUsername(userInfo.getUsername());
        user.setEmail(userInfo.getEmail());
        user.setPhoneNumber(userInfo.getPhoneNumber());
        userInfoRepository.saveAndFlush(user);
        return user;
    }

    @Override
    @Caching(
            put = {
                    @CachePut(value = "users",key = "#result.uid")
            },
            evict = {
                    @CacheEvict(value = "users",key = "#result.username")
            }
    )
    public UserInfo updateUserRole(UserInfo userInfo) {
        UserInfo user = userInfoRepository.findOne(userInfo.getUid());
        user.setRoles(userInfo.getRoles());
        userInfoRepository.saveAndFlush(user);
        return user;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @CacheEvict(value = "users",beforeInvocation = true,key = "#id")
    public void resetPassword(Integer id) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encode = bCryptPasswordEncoder.encode("123456");
        userInfoRepository.resetPassword(id, encode);
    }

    @Override
    @Caching(
            put = {
                    @CachePut(value = "users",key = "#result.uid")

            },
            evict = {
                    @CacheEvict(value = "users",key = "#result.username")
            }
    )
    public UserInfo addUserIntoGroup(UserInfo userInfo, Integer groupId) {
        UserInfo result = userInfoRepository.findByUsernameAndAndState(userInfo.getUsername(),"1");
        Role role = roleRepository.findByRoleName("ROLE_DIS" + groupId);
        List<Role> roles = result.getRoles();
        roles.add(role);
        result.setRoles(roles);

        List<DiscussionGroup> groups = result.getDisGroupList();
        groups.add(groupRepository.findOne(groupId));
        result.setDisGroupList(groups);

        userInfoRepository.saveAndFlush(result);
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @CacheEvict(value = "users",beforeInvocation = true,key = "#user.uid")
    public boolean updatePassword(String oldPwd, String newPwd, String username) {
        UserInfo user = userInfoRepository.findUserInfoByUsername(username);
        LoggerFactory.getLogger(getClass()).info("user {}", user);
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        boolean result = bCryptPasswordEncoder.matches(oldPwd, user.getPassword());
        LoggerFactory.getLogger(getClass()).info("result {}", result);
        if (result) {
            userInfoRepository.resetPassword(user.getUid(), bCryptPasswordEncoder.encode(newPwd));
            return true;
        }
        return false;
    }
}
