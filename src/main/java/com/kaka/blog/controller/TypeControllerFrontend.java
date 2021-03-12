package com.kaka.blog.controller;

import com.kaka.blog.po.BlogQuery;
import com.kaka.blog.service.BlogService;
import com.kaka.blog.service.TypeService;
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
public class TypeControllerFrontend {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final TypeService typeService;
    private final BlogService blogService;

    @Autowired
    public TypeControllerFrontend(TypeService typeService, BlogService blogService) {
        this.typeService = typeService;
        this.blogService = blogService;
    }

    private final String TYPES = "types";

    /**
     * 所有type的所有blog
     * @param model
     * @param pageable
     * @return
     */
    @GetMapping("/types")
    public String types(Model model, @PageableDefault(sort = "createTime", direction = Sort.Direction.DESC) Pageable pageable) {
        log.info("進入types.html...");
        model.addAttribute("types", typeService.listType());
        model.addAttribute("page", blogService.listBlog(pageable));
        return TYPES;
    }

    /**
     * 根據typeId取得blog
     * @param id
     * @param model
     * @param pageable
     * @return
     */
    @GetMapping("/types/{id}")
    public String types(@PathVariable Long id, Model model, @PageableDefault(sort = "createTime", direction = Sort.Direction.DESC) Pageable pageable) {
        log.info("types.html根據typeId取得blog...");
        BlogQuery blogQuery = new BlogQuery();
        blogQuery.setTypeId(id);
        model.addAttribute("types", typeService.listType());
        model.addAttribute("page", blogService.listBlog(pageable, blogQuery));
        model.addAttribute("activeTypeId", id);
        return TYPES;
    }
}
