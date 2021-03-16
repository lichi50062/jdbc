package com.kaka.blog.controller;

import com.kaka.blog.service.BlogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author kaka
 */
@RequestMapping()
@Controller
public class ArchiveController {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    private final String ARCHIVES = "archives";

    private final BlogService blogService;
    public ArchiveController(BlogService blogService) {
        this.blogService = blogService;
    }

    @GetMapping("/archives")
    public String archives(Model model) {
        logger.info("依年分取得所有blog");
        model.addAttribute("archiveMap", blogService.archiveBlogList());
        model.addAttribute("archivesCount", blogService.countBlogs());
        return ARCHIVES;
    }

}
