package cn.com.taiji.actual.controller;

import cn.com.taiji.actual.domain.Blog;
import cn.com.taiji.actual.domain.UserInfo;
import cn.com.taiji.actual.domain.Permission;
import cn.com.taiji.actual.service.BlogService;
import cn.com.taiji.actual.service.UserInfoService;
import cn.com.taiji.actual.service.impl.BlogServiceImpl;
import cn.com.taiji.actual.untils.Result;
import cn.com.taiji.actual.untils.ResultUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Barry
 * @version v1.0
 * @description 博客功能控制层部分，控制页面与后台的交互
 * @date created on 2018/12/17 11:29
 */

@Controller
public class BlogController {

    private BlogService blogServiceImpl;

    @Autowired
    public BlogController(BlogService blogServiceImpl) {
        this.blogServiceImpl = blogServiceImpl;
    }

    @GetMapping("/md")
    public String blogOnlineMarkdown() {
        return "public/blog";
    }

    @GetMapping("/blogContent")
    @ResponseBody
    public String releaseBlog(Blog blog, @RequestParam("content") String content,
                              UserInfo userInfo) {
        blogServiceImpl.addBlog(blog, content, userInfo.getUsername());
        return "redirect:home";
    }

    @GetMapping("/showBlog/{blog}")
    public String showBlog(@PathVariable("blog") Blog blog, Model model) {
        Blog blogInfo = blogServiceImpl.findById(blog.getBid());
        model.addAttribute("blogInfo", blogInfo);
        model.addAttribute("blogAuthor", blogInfo.getUserInfo().getUsername());
        model.addAttribute("blogContent", new String(blogInfo.getBContent(), StandardCharsets.UTF_8));
        return "public/blog-content";
    }

    /**
     * 分页
     * @author zxx
     * @param num 页数
     * @param model model
     * @return 博客管理页面
     */
    @GetMapping("/blog/page/{num}")
    public String getPage(@PathVariable("num") Integer num, Model model) {
        Map pagination = blogServiceImpl.findPagination(num);
        int pageSize = (int) pagination.get("total");
        List<Blog> permissionList = (List<Blog>) pagination.get("blogs");
        model.addAttribute("blogList", permissionList);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("page", num);
        return "/blog/index";
    }

    /**
     * 根据id删除
     * @author zxx
     * @param id id
     * @return 删除结果
     */
    @GetMapping("/blog/delete")
    @ResponseBody
    public Result deleteById(Integer id) {
        blogServiceImpl.deleteById(id);
        return ResultUtils.Success("删除成功");
    }

    /**
     * 跳转后台查看页面
     * @author zxx
     * @param id id
     * @param model model
     * @return 后台详情页面
     */
    @GetMapping("/blog/contentPage/{id}")
    public String editUser(@PathVariable("id") Integer id, Model model) {
        Blog blog = blogServiceImpl.findById(id);
        model.addAttribute("blogContent", new String(blog.getBContent(), StandardCharsets.UTF_8));
        model.addAttribute("blog", blog);
        return "/blog/edit";
    }
}
