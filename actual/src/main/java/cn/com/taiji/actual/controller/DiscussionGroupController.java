package cn.com.taiji.actual.controller;

import cn.com.taiji.actual.domain.Article;
import cn.com.taiji.actual.domain.Blog;
import cn.com.taiji.actual.domain.DiscussionGroup;
import cn.com.taiji.actual.domain.UserInfo;
import cn.com.taiji.actual.service.ArticleService;
import cn.com.taiji.actual.service.DiscussionGroupService;
import cn.com.taiji.actual.untils.Result;
import cn.com.taiji.actual.untils.ResultUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;


/**
 * @author LWL
 * @version 1.0
 * @description
 * @date 2018/12/17 11:26
 */

@Controller
@RequestMapping("/disGroup")
public class DiscussionGroupController {

    @Autowired
    DiscussionGroupService discussionGroupService;

    @Autowired
    ArticleService articleService;

    Logger logger = LoggerFactory.getLogger(getClass());

//    @GetMapping("/disgroup")
//    public String findAll(Model model){
//
//        List<DiscussionGroup> discussionGroups = discussionGroupService.findAll();
//
//        model.addAttribute("groupList",discussionGroups);
//
//        return "/discussionGroup";
//    }

    /**
     *分页查询
     * @param num
     * @param model
     * @return
     */
    @GetMapping("page/{num}")
    public String getPage(@PathVariable("num") Integer num, Model model){
        Map pagination = discussionGroupService.findPagination(num);
        int pageSize =(int)pagination.get("total");
        List<DiscussionGroup> discussion = (List<DiscussionGroup>)pagination.get("discussions");
        model.addAttribute("groupList",discussion);
        model.addAttribute("pageSize",pageSize);
        model.addAttribute("page",num);
        return "discussion/index";
    }

    /**
     * 根据id删除讨论组
     * @param id
     * @return
     */
    @GetMapping("delete")
    @ResponseBody
    public Result deleteById(Integer id){
        discussionGroupService.deleteById(id);
        return ResultUtils.Success("删除成功");
    }

    /**
     * 添加页面跳转
     * @param model
     * @return
     */
    @GetMapping("/addPage")
    public String addPage(Model model){
        DiscussionGroup discussionGroup = new DiscussionGroup();
        model.addAttribute("dissionList",discussionGroup);
        return "/discussion/edit";
    }

    /**
     * 新增操作
     * @param discussionGroup
     * @return
     */
    @PostMapping("add")
    public String addUser(DiscussionGroup discussionGroup){
        discussionGroupService.addDiscussion(discussionGroup);
        return "redirect:/disGroup/page/1";
    }

    /**
     * 跳转编辑页面
     * @param id
     * @param model
     * @return
     */
    @GetMapping("editPage/{id}")
    public String editUser(@PathVariable("id")Integer id,Model model){
        DiscussionGroup discussionGroup = discussionGroupService.findById(id);
        model.addAttribute("dissionList",discussionGroup);
        return "/discussion/edit";
    }

    /**
     * 更新操作
     * @param discussionGroup
     * @return
     */
    @PostMapping("edit")
    public String editUser(DiscussionGroup discussionGroup){
        discussionGroupService.updateDiscussion(discussionGroup);
        return "redirect:/disGroup/page/1";
    }


    /**
     * 帖子管理
     * @param num
     * @param model
     * @return
     */
    @GetMapping("/articlePage/{num}")
    public String article(@PathVariable("num") Integer num, Model model,Integer disId){
        logger.info(disId.toString());
        Map pagination = articleService.findPagination(num,disId);
        int pageSize =(int)pagination.get("total");
        List<Article> article = (List<Article>)pagination.get("article");
        model.addAttribute("articleList",article);
        model.addAttribute("pageSize",pageSize);
        model.addAttribute("page",num);
        model.addAttribute("disId",disId);
        return "/discussion/article";
    }

    /**
     * 根据id删除讨论组
     * @param id
     * @return
     */
    @GetMapping("deleteArticle")
    @ResponseBody
    public Result deleteArticleById(Integer id){
        discussionGroupService.deleteArticleById(id);
        return ResultUtils.Success("删除成功");
    }

    /**
     * 跳转查看页面
     *
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/contentPage/{id}")
    public String editArticle(@PathVariable("id") Integer id, Model model) {
        Article article = articleService.findById(id);
        model.addAttribute("articleContent", new String(article.getAContent(), StandardCharsets.UTF_8));
        model.addAttribute("article", article);
        return "/discussion/artedit";
    }

//    @GetMapping("/blog/contentPage/{id}")
//    public String editUser(@PathVariable("id") Integer id, Model model) {
//        Blog blog = blogServiceImpl.findById(id);
//        model.addAttribute("blogContent", new String(blog.getBContent(), StandardCharsets.UTF_8));
//        model.addAttribute("blog", blog);
//        return "/blog/edit";
//    }

}
