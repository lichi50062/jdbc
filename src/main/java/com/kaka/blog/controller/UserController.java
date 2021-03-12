package com.kaka.blog.controller;

import com.kaka.blog.headler.EmailExistException;
import com.kaka.blog.headler.UserNameExitException;
import com.kaka.blog.po.User;
import com.kaka.blog.service.UserService;
import com.kaka.blog.vo.UserRegisterVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.validation.Valid;

/**
 * @author Kaka
 */
@Controller
@RequestMapping
public class UserController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    private static final String REDIRECT_LOGIN = "redirect:/login";
    private static final String SIGNUP = "signup";
    private static final String REDIRECT_SIGNUP = "redirect:/signup";

    /**
     * 跳轉到註冊頁面
     * @return
     */
    @GetMapping("/signup")
    public String goSignup(@ModelAttribute UserRegisterVo userRegisterVo, Model model) {
        model.addAttribute("userRegisterVo", userRegisterVo);
        return SIGNUP;
    }

    /**
     * 信箱驗證
     * @param activeCode
     * @return
     */
    @GetMapping("/checkCode")
    public String checkActiveCode(@RequestParam("activeCode") String activeCode, RedirectAttributes redirectAttributes) {
        logger.info("信箱驗證...");
        User user = userService.findUserByActiveCode(activeCode);
        if (user != null) {
            user.setActiveStatus(1);
            user.setActiveCode("");
            userService.updateUser(user);
            redirectAttributes.addFlashAttribute("positiveMessage", "信箱驗證成功");
            return REDIRECT_LOGIN;
        }
        return null;
    }

    /**
     * 使用者註冊
     * @param vo
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/signup")
    public String signup(@Valid UserRegisterVo vo, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        logger.info("使用者註冊...");
        if (bindingResult.hasErrors()) {
            return SIGNUP;
        }
        try {
            userService.userRegister(vo);
        } catch (UserNameExitException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "使用者名稱重複");
            return REDIRECT_SIGNUP;
        } catch (EmailExistException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "email重複");
            return REDIRECT_SIGNUP;
        }
        redirectAttributes.addFlashAttribute("positiveMessage", "註冊成功 請到信箱驗證");
        return REDIRECT_LOGIN;
    }
}
