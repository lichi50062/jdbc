package com.kaka.blog.controller;

import com.kaka.blog.service.BlogService;
import com.kaka.blog.service.TagService;
import com.kaka.blog.service.TypeService;
import com.kaka.blog.web.ClassNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;


/**
 * @author Kaka
 */
@RequestMapping
@Controller
public class IndexController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final BlogService blogService;
    private final TypeService typeService;
    private final TagService tagService;
    @Autowired
    public IndexController(BlogService blogService, TypeService typeService, TagService tagService) {
        this.blogService = blogService;
        this.typeService = typeService;
        this.tagService = tagService;
    }

    private final String INDEX = "index";
    private final String SEARCH = "search";
    private final String BLOG = "blog";

    /**
     * blog前台首頁
     * @param pageable
     * @param model
     * @return
     * @throws ClassNotFoundException
     */
    @GetMapping()
    public String index(@PageableDefault(size = 5, sort = ("createTime"), direction = Sort.Direction.DESC) Pageable pageable, Model model) throws ClassNotFoundException {
        log.info("進入前台首頁...");
        model.addAttribute("page", blogService.listBlog(pageable));
        model.addAttribute("types", typeService.listType());
        model.addAttribute("tags", tagService.listTag());
        model.addAttribute("newBlogs", blogService.blogFindTop(5));
        return INDEX;
    }

    /**
     * blog搜尋
     * @param pageable
     * @param query
     * @param model
     * @return
     * @throws ClassNotFoundException
     */
    @PostMapping("/search")
    public String search(@PageableDefault(size = 5, sort = ("createTime"), direction = Sort.Direction.DESC) Pageable pageable, @RequestParam String query, Model model) throws ClassNotFoundException {
        log.info("blog搜尋...");
        model.addAttribute("page", blogService.searchBlog(pageable, "%" + query + "%"));
        model.addAttribute("query", query);
        return SEARCH;
    }

    /**
     * blog內容
     * @param id
     * @param model
     * @return
     * @throws ClassNotFoundException
     */
    @GetMapping("/blog/{id}")
    public String index(@PathVariable Long id, Model model) throws ClassNotFoundException {
        log.info("blog內容...");
        model.addAttribute("blog", blogService.getAndConvert(id));
        return BLOG;
    }
}

