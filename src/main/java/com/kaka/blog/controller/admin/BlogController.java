package com.kaka.blog.controller.admin;

import com.kaka.blog.po.*;
import com.kaka.blog.service.BlogService;
import com.kaka.blog.service.TagService;
import com.kaka.blog.service.TypeService;
import com.kaka.blog.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.servlet.http.HttpSession;
import java.util.*;


/**
 * @author Kaka
 */
@Controller
@RequestMapping("/admin")
public class BlogController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final BlogService blogService;
    private final UserService userService;
    private final TypeService typeService;
    private final TagService tagService;

    @Autowired
    public BlogController(BlogService blogService, UserService userService, TypeService typeService, TagService tagService) {
        this.blogService = blogService;
        this.userService = userService;
        this.typeService = typeService;
        this.tagService = tagService;
    }

    private final String ADMIN_BLOG_INPUT = "admin/blog-input";
    private final String ADMIN_BLOGS = "admin/blogs";
    private final String REDIRECT_ADMIN_BLOGS = "redirect:/admin/blogs";

    /**
     * blog後台首頁
     * @param session
     * @param pageable
     * @param model
     * @return
     */
    @GetMapping("/blogs")
    public String blogs(HttpSession session, @PageableDefault(sort = "updateTime", direction = Sort.Direction.DESC) Pageable pageable, Model model) {
        log.info("進入admin/blogs...");
        User user = (User) session.getAttribute("user");
        model.addAttribute("types", typeService.listType());
        model.addAttribute("page", blogService.findBlogByUserId(userService.findUserByEmail(user.getEmail()).getId(), pageable));
        return ADMIN_BLOGS;
    }

    /**
     * 後台搜尋
     * @param pageable
     * @param blogQuery
     * @param model
     * @return
     */
    @PostMapping("/blogs/search")
    public String search(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable, BlogQuery blogQuery, Model model) {
        log.info("後台blog搜尋...");
        model.addAttribute("page", blogService.listBlog(pageable, blogQuery));
        return "admin/blogs :: blogList";
    }

    /**
     * 跳轉到新增blog
     * @param session
     * @param model
     * @return
     */
    @GetMapping("/blog/input")
    public String blogInput(HttpSession session, Model model) {
        log.info("跳轉到新增Blog...");
        List<Tag> tagList = tagService.listTag();
        List<Type> typeList = typeService.listType();
        model.addAttribute("user", session.getAttribute("user"));
        model.addAttribute("tags", tagList);
        model.addAttribute("blog", new Blog());
        model.addAttribute("types", typeList);
        return ADMIN_BLOG_INPUT;
    }

    /**
     * 跳轉到修改blog
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/blogs/{id}/input")
    public String editBlog(@PathVariable Long id, Model model) {
        log.info("跳轉到修改Blog...");
        Blog blog = blogService.getBlog(id).orElse(null);
        assert blog != null;
        blog.init();
        model.addAttribute("blog", blog);
        model.addAttribute("tags", tagService.listTag());
        model.addAttribute("types", typeService.listType());
        return ADMIN_BLOG_INPUT;
    }

    /**
     *送出新增或修改blog
     * @param blog
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/blog/input")
    public String blogPost(Blog blog, HttpSession session, RedirectAttributes redirectAttributes) {
        log.info("送出新增或修改Blog...");
        blog.setUser((User) session.getAttribute("user"));
        blog.setType(typeService.getType(blog.getTypeId()));
        blog.setTags(tagService.listTag(blog.getTagIds()));
        Blog blog1;
        if (blog.getId() != null) {
            blog1 = blogService.updateBlog(blog, blog.getId());
        } else {
            blog1 = blogService.saveBlog(blog);
        }
        if (blog1 != null) {
            redirectAttributes.addFlashAttribute("positiveMessage", "成功");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "失敗");
        }
        return REDIRECT_ADMIN_BLOGS;
    }

    /**
     * 刪除Blog
     * @param id
     * @param redirectAttributes
     * @return
     */
    @GetMapping("/blogs/{id}/delete")
    public String deleteBlog(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        log.info("刪除Blog...");
        blogService.deleteBlog(id);
        redirectAttributes.addFlashAttribute("message", "刪除成功");
        return REDIRECT_ADMIN_BLOGS;
    }
}