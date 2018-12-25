package cn.com.taiji.actual.service.impl;

import cn.com.taiji.actual.domain.Permission;
import cn.com.taiji.actual.domain.UserInfo;
import cn.com.taiji.actual.repository.PermissionRepository;
import cn.com.taiji.actual.security.CustomInvocationSecurityMetadataSourceService;
import cn.com.taiji.actual.service.PermissionService;
import cn.com.taiji.actual.untils.PaginationUntil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

/**
 * @author zxx
 * @version 1.0
 * @date 2018/12/18 10:21
 */
@Service
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private PermissionRepository permissionRepository;
    @Autowired
    private CustomInvocationSecurityMetadataSourceService customInvocationSecurityMetadataSourceService;

    @Override
    public List<Permission> findByState(String state) {
        return permissionRepository.findByState(state);
    }

    @Override
    public Permission findById(Integer id) {
        return permissionRepository.findOne(id);
    }

    @Override
    public Map findPagination(Integer page) {
        Integer pageNum = 10;
        //生成pageable
        Map map = new HashMap(16);
        map.put("page",page);
        map.put("pageSize",10);
        Pageable pageable = PaginationUntil.getPage(map);
        //构建查询条件
        Specification<Permission> specification = new Specification<Permission>() {
            @Override
            public Predicate toPredicate(Root<Permission> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                // 查询出未删除的
                predicates.add(cb.equal(root.<Integer>get("state"), 1));
                return cb.and(predicates.toArray(new Predicate[0]));
            }
        };
        Page<Permission> pageList = permissionRepository.findAll(specification, pageable);
        Map result = new HashMap(16);
        int pageSize = (int)pageList.getTotalElements();
        if(pageSize%pageNum==0){
            result.put("total",pageSize/pageNum);
        }else{
            result.put("total",(pageSize/pageNum)+1);
        }
        result.put("page", pageList.getNumber()+1);
        List<Permission> list = pageList.getContent();
        result.put("permissions",list);
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public void deleteById(Integer id) {
        permissionRepository.deleteById(id,"0");
        customInvocationSecurityMetadataSourceService.loadResourceDefine();
    }

    @Override
    public void addPermission(Permission permission) {
        permission.setCreateDate(new Date());
        permission.setState("1");
        permissionRepository.saveAndFlush(permission);
        customInvocationSecurityMetadataSourceService.loadResourceDefine();
    }

    @Override
    public void updatePermission(Permission permission) {
        Permission result = permissionRepository.findOne(permission.getPid());
        result.setPermissionName(permission.getPermissionName());
        result.setPermissionDescription(permission.getPermissionDescription());
        result.setUrl(permission.getUrl());
        permissionRepository.saveAndFlush(result);
        customInvocationSecurityMetadataSourceService.loadResourceDefine();
    }



}
