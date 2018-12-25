package cn.com.taiji.actual.controller;

import cn.com.taiji.actual.domain.Role;
import cn.com.taiji.actual.domain.UserInfo;
import cn.com.taiji.actual.service.RoleService;
import cn.com.taiji.actual.service.UserInfoService;
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
 * 用户相关的控制类
 * @author zxx
 * @version 1.0
 * @date 2018/12/16 23:13
 */
@Controller
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private RoleService roleService;

    Logger logger = LoggerFactory.getLogger(getClass());
    /**
     * 分页
     * @param num 页数
     * @param model model
     * @return 用户管理后台首页
     */
    @GetMapping("page/{num}")
    public String getPage(@PathVariable("num") Integer num, Model model){
        Map pagination = userInfoService.findPagination(num);
        int pageSize =(int)pagination.get("total");
        List<UserInfo> userList = (List<UserInfo>)pagination.get("users");
        model.addAttribute("userList",userList);
        model.addAttribute("pageSize",pageSize);
        model.addAttribute("page",num);
        return "/user/index";
    }

    /**
     * 根据id删除
     * @param id id
     * @return 删除结果
     */
    @GetMapping("delete")
    @ResponseBody
    public Result deleteById(Integer id){
        userInfoService.deleteById(id);
        return ResultUtils.Success("删除成功");
    }

    /**
     * 跳转添加页面
     * @param model model
     * @return 添加页面
     */
    @GetMapping("addPage")
    public String addUser(Model model){
        UserInfo userInfo = new UserInfo();
        model.addAttribute("userInfo",userInfo);
        return "/user/edit";
    }

    /**
     * 跳转编辑页面
     * @param id id
     * @param model model
     * @return 编辑页面
     */
    @GetMapping("editPage/{id}")
    public String editUser(@PathVariable("id")Integer id,Model model){
        UserInfo userInfo = userInfoService.findById(id);
        model.addAttribute("userInfo",userInfo);
        return "/user/edit";
    }

    /**
     * 跳转角色编辑页面
     * @param id id
     * @param model model
     * @return 编辑用户角色页面
     */
    @GetMapping("editRole/{id}")
    public String editRole(@PathVariable("id")Integer id,Model model){
        UserInfo userInfo = userInfoService.findById(id);
        List<Role> roles = roleService.findByState("1");
        model.addAttribute("roles",roles);
        model.addAttribute("userInfo",userInfo);
        return "/user/editRole";
    }
    /**
     * 新增操作
     * @param userInfo 用户实体
     * @return 角色管理首页
     */
    @PostMapping("add")
    public String addUser(UserInfo userInfo){
        userInfoService.addUser(userInfo);
        return "redirect:/user/page/1";
    }

    /**
     * 更新操作
     * @param userInfo 用户实体
     * @return 角色管理首页
     */
    @PostMapping("edit")
    public String editUser(UserInfo userInfo){
        userInfoService.updateUser(userInfo);
        return "redirect:/user/page/1";
    }

    /**
     * 更新用户角色操作
     * @param userInfo 用户实体
     * @return 角色管理首页
     */
    @PostMapping("editRole")
    public String editRole(UserInfo userInfo){
        logger.info(userInfo.toString());
        userInfoService.updateUserRole(userInfo);
        return "redirect:/user/page/1";
    }

    /**
     * 重置用户密码
     * @param id
     */
    @GetMapping("reset/{id}")
    @ResponseBody
    public Result resetPassword(@PathVariable("id")Integer id){
        userInfoService.resetPassword(id);
        return ResultUtils.Success("重置成功");
    }

}
