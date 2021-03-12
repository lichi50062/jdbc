package com.kaka.blog.controller;

import com.kaka.blog.po.BlogQuery;
import com.kaka.blog.service.BlogService;
import com.kaka.blog.service.TagService;
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

/**
 * @author Kaka
 */
@Controller
public class TagControllerFrontend {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final TagService tagService;
    private final BlogService blogService;

    @Autowired
    public TagControllerFrontend(TagService tagService, BlogService blogService) {
        this.tagService = tagService;
        this.blogService = blogService;
    }

    private final String TAGS = "tags";

    /**
     * 所有type的所有blog
     * @param model
     * @param pageable
     * @return
     */
    @GetMapping("/tags")
    public String types(Model model,@PageableDefault(sort = "createTime", direction = Sort.Direction.DESC) Pageable pageable) {
        log.info("進入tags.html...");
        model.addAttribute("tags", tagService.listTag());
        model.addAttribute("page", blogService.listBlog(pageable));
        return TAGS;
    }

    /**
     * 根據typeId取得blog
     * @param id
     * @param model
     * @param pageable
     * @return
     */
    @GetMapping("/tags/{id}")
    public String types(@PathVariable Long id, Model model,@PageableDefault(sort = "createTime", direction = Sort.Direction.DESC) Pageable pageable) {
        log.info("tags.html根據tagId取得blog...");
        model.addAttribute("tags", tagService.listTag());
        model.addAttribute("page", blogService.listBlog(id, pageable));
        model.addAttribute("activeTagId", id);
        return TAGS;
    }
}
