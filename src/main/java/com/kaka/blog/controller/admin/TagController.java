package com.kaka.blog.controller.admin;


import com.kaka.blog.po.Tag;
import com.kaka.blog.service.TagService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * @author Kaka
 */
@Controller
@RequestMapping("/admin")
public class TagController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    private final String TAGS = "admin/tags";
    private final String TAG_INPUT = "admin/tag-input";
    private final String REDIRECT_TAGS = "redirect:/admin/tags";

    /**
     * 進入admin/tags
     * @param pageable
     * @param model
     * @return
     */
    @GetMapping("/tags")
    public String tagIndex(@PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable, Model model) {
        log.info("進入admin/tags...");
        Page<Tag> page = tagService.listTag(pageable);
        model.addAttribute("page", page);
        return TAGS;
    }

    /**
     * 跳轉到新增tag
     * @param model
     * @return
     */
    @GetMapping("/add/tag")
    public String tagInputPage(Model model) {
        model.addAttribute("tag", new Tag());
        return TAG_INPUT;
    }

    /**
     * 新增tag
     * @param tag
     * @param bindingResult
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/add/tag")
    public String addTag(@Valid Tag tag, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors()) {
            return TAG_INPUT;
        }
        Tag tag1 = tagService.saveTag(tag);
        if (tag1 == null) {
            redirectAttributes.addFlashAttribute("message", "新稱失敗");
        } else {
            redirectAttributes.addFlashAttribute("message", "新增成功");
        }
        return REDIRECT_TAGS;
    }

    /**
     * 跳轉到修改tag
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/edit/{id}/tag")
    public String editTagPage(@PathVariable Long id, Model model) {
        Tag tag = tagService.getTag(id);
        model.addAttribute("tag", tag);
        return TAG_INPUT;
    }

    /**
     * 修改tag
     * @param id
     * @param tag
     * @param bindingResult
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/edit/{id}/tag")
    public String editTag(@PathVariable Long id, @Valid Tag tag, BindingResult bindingResult,RedirectAttributes redirectAttributes, Model model) {
        Tag tagName = tagService.getTagByName(tag.getTagName());
        if (tagName != null) {
            bindingResult.rejectValue("tagName", "tagName錯誤", "tagName重複");
        }
        if (bindingResult.hasErrors()) {
            return TAG_INPUT;
        }
        Tag tag1 = tagService.updateTag(id, tag);
        if (tag1 == null) {
            redirectAttributes.addFlashAttribute("message", "修改失敗");
        } else {
            redirectAttributes.addFlashAttribute("message", "修改成功");
        }
        return REDIRECT_TAGS;
    }

    /**
     * 刪除tag
     * @param id
     * @param pageable
     * @param redirectAttributes
     * @param model
     * @return
     */
    @GetMapping("/delete/{id}/tag")
    public String deleteTag(@PathVariable Long id, Pageable pageable, RedirectAttributes redirectAttributes, Model model) {
        Page page = tagService.listTag(pageable);
        tagService.deleteTag(id);
        model.addAttribute("page", page);
        redirectAttributes.addFlashAttribute("message", "刪除成功");
        return REDIRECT_TAGS;
    }
}
