package com.kaka.blog.controller.admin;


import com.kaka.blog.po.Type;
import com.kaka.blog.service.TypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * @author Kaka
 */
@Controller
@RequestMapping("/admin")
public class TypeController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final TypeService typeService;

    @Autowired
    public TypeController(TypeService typeService) {
        this.typeService = typeService;
    }

    private final String TYPES = "admin/types";
    private final String TYPE_INPUT = "admin/type-input";
    private final String REDIRECT_TYPES = "redirect:/admin/types";

    /**
     * 進入admin/types
     * @param pageable
     * @param model
     * @return
     */
    @GetMapping("/types")
    public String goTypes(@PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable, Model model) {
        log.info("進入admin/tags...");
        Page<Type> page = typeService.typeList(pageable);
        model.addAttribute("page", page);
        return TYPES;
    }

    /**
     * 跳轉到新增type
     * @param model
     * @return
     */
    @GetMapping("/add/type")
    public String goTypeInput(Model model) {
        model.addAttribute("type", new Type());
        return TYPE_INPUT;
    }

    /**
     * 新增type
     * @param type
     * @param bindingResult
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/add/type")
    public String addTypeInput(@Valid Type type, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return TYPE_INPUT;
        }
        Type type1 = typeService.saveType(type);
        if (type1 == null) {
            redirectAttributes.addFlashAttribute("message", "新稱失敗");
        } else {
            redirectAttributes.addFlashAttribute("message", "新增成功");
        }
        return REDIRECT_TYPES;
    }

    /**
     * 跳轉到修改type
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/edit/{id}/type")
    public String goEditType(@PathVariable Long id, Model model) {
        Type type = typeService.getType(id);
        model.addAttribute("type", type);
        return TYPE_INPUT;
    }

    /**
     * 修改type
     * @param id
     * @param type
     * @param bindingResult
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/edit/{id}/type")
    public String editType(@PathVariable Long id, @Valid Type type, BindingResult bindingResult,RedirectAttributes redirectAttributes, Model model) {
        Type typeName = typeService.getTypeName(type.getTypeName());
        if (typeName != null) {
            bindingResult.rejectValue("typeName", "typeName錯誤", "TypeName重複");
        }
        if (bindingResult.hasErrors()) {
            return TYPE_INPUT;
        }
        Type type1 = typeService.updateType(id, type);
        if (type1 == null) {
            redirectAttributes.addFlashAttribute("message", "修改失敗");
        } else {
            redirectAttributes.addFlashAttribute("message", "修改成功");
        }
        return REDIRECT_TYPES;
    }

    /**
     * 刪除type
     * @param id
     * @param pageable
     * @param redirectAttributes
     * @param model
     * @return
     */
    @GetMapping("/delete/{id}/type")
    public String deleteType(@PathVariable Long id, Pageable pageable, RedirectAttributes redirectAttributes, Model model) {
        Page page = typeService.typeList(pageable);
        typeService.deleteType(id);
        model.addAttribute("page", page);
        redirectAttributes.addFlashAttribute("message", "刪除成功");
        return REDIRECT_TYPES;
    }
}
