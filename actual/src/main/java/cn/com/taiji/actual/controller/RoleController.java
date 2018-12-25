package cn.com.taiji.actual.controller;

import cn.com.taiji.actual.domain.Permission;
import cn.com.taiji.actual.domain.Role;
import cn.com.taiji.actual.service.PermissionService;
import cn.com.taiji.actual.service.RoleService;
import cn.com.taiji.actual.untils.Result;
import cn.com.taiji.actual.untils.ResultUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author zxx
 * @version 1.0
 * @date 2018/12/17 15:40
 */
@Controller
@RequestMapping("role")
public class RoleController {
    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;

    Logger logger = LoggerFactory.getLogger(getClass());
    /**
     * 分页
     * @param num 页数
     * @param model model
     * @return 角色后台管理页
     */
    @GetMapping("page/{num}")
    public String getPage(@PathVariable("num") Integer num, Model model){
        Map pagination = roleService.findPagination(num);
        int pageSize =(int)pagination.get("total");
        List<Role> roleList = (List<Role>)pagination.get("roles");
        model.addAttribute("roleList",roleList);
        model.addAttribute("pageSize",pageSize);
        model.addAttribute("page",num);
        return "/role/index";
    }

    /**
     * 根据id删除
     * @param id id
     * @return 删除结果
     */
    @GetMapping("delete")
    @ResponseBody
    public Result deleteById(Integer id){
        roleService.deleteById(id);
        return ResultUtils.Success("删除成功");
    }

    /**
     * 跳转添加页面
     * @param model model
     * @return 添加页
     */
    @GetMapping("addPage")
    public String addUser(Model model){
        Role role = new Role();
        model.addAttribute("role",role);
        return "/role/edit";
    }

    /**
     * 跳转编辑页面
     * @param id id
     * @param model model
     * @return 编辑页
     */
    @GetMapping("editPage/{id}")
    public String editUser(@PathVariable("id")Integer id,Model model){
        Role role = roleService.findById(id);
        model.addAttribute("role",role);
        return "/role/edit";
    }

    /**
     * 跳转权限编辑页面
     * @param id id
     * @param model model
     * @return 权限编辑页
     */
    @GetMapping("editPermission/{id}")
    public String editRole(@PathVariable("id")Integer id,Model model){
        Role role = roleService.findById(id);
        logger.info(role.toString());
        List<Permission> permissions = permissionService.findByState("1");
        model.addAttribute("permissions",permissions);
        model.addAttribute("role",role);
        return "/role/editPermission";
    }
    /**
     * 新增操作
     * @param role 角色实体
     * @return 角色后台管理首页
     */
    @PostMapping("add")
    public String addUser(Role role){
        roleService.addRole(role);
        return "redirect:/role/page/1";
    }

    /**
     * 更新操作
     * @param role 角色实体
     * @return 角色管理首页
     */
    @PostMapping("edit")
    public String editUser(Role role){
        roleService.updateRole(role);
        return "redirect:/role/page/1";
    }
    /**
     * 更新角色权限操作
     * @param role 角色实体
     * @return 角色管理首页
     */
    @PostMapping("editRole")
    public String editRole(Role role){
        roleService.updateRolePermission(role);
        return "redirect:/role/page/1";
    }
}
