package cn.com.taiji.actual.service.impl;

import cn.com.taiji.actual.domain.Role;
import cn.com.taiji.actual.domain.UserInfo;
import cn.com.taiji.actual.repository.RoleRepository;
import cn.com.taiji.actual.service.RoleService;
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
 * @date 2018/12/17 15:38
 */
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<Role> findByState(String state){
        return roleRepository.findByState(state);
    }

    @Override
    public Role findById(Integer id) {
        return roleRepository.findOne(id);
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
        Specification<Role> specification = new Specification<Role>() {
            @Override
            public Predicate toPredicate(Root<Role> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                // 查询出未删除的
                predicates.add(cb.equal(root.<Integer>get("state"), 1));
                return cb.and(predicates.toArray(new Predicate[0]));
            }
        };
        Page<Role> pageList = roleRepository.findAll(specification, pageable);
        Map result = new HashMap(16);
        int pageSize = (int)pageList.getTotalElements();
        if(pageSize%pageNum==0){
            result.put("total",pageSize/pageNum);
        }else{
            result.put("total",(pageSize/pageNum)+1);
        }
        if(pageSize==0){
            result.put("total",1);
        }
        result.put("page", pageList.getNumber()+1);
        List<Role> list = pageList.getContent();
        result.put("roles",list);
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public void deleteById(Integer id) {
        roleRepository.deleteById(id,"0");
    }

    @Override
    public void addRole(Role role) {
        role.setCreateDate(new Date());
        role.setState("1");
        roleRepository.saveAndFlush(role);
    }

    @Override
    public void updateRole(Role role) {
        Role resultRole = roleRepository.findOne(role.getRid());
        resultRole.setRoleName(role.getRoleName());
        roleRepository.saveAndFlush(resultRole);

    }

    @Override
    public void updateRolePermission(Role role) {
        Role result = roleRepository.getOne(role.getRid());
        result.setPermissions(role.getPermissions());
        roleRepository.saveAndFlush(result);

    }
}
