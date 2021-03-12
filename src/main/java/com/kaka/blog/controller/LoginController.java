package com.kaka.blog.controller;

import com.kaka.blog.po.User;
import com.kaka.blog.service.UserService;
import com.kaka.blog.vo.UserLoginVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author Kaka
 */
@Controller
@RequestMapping
public class LoginController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserService userService;
    private static final String LOGIN = "login";
    private static final String REDIRECT_LOGIN = "redirect:/login";
    private static final String REDIRECT_INDEX = "redirect:/admin/blogs";

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 跳轉到登入
     * @return
     */
    @GetMapping("/login")
    public String logIn(HttpSession session) {
        if (session.getAttribute("user") != null) {
            return REDIRECT_INDEX;
        }
        return LOGIN;
    }

    /**
     * 使用者登入
     * @param vo
     * @param session
     * @param model
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/login")
    public String login(UserLoginVo vo, HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        logger.info("使用者登入...");
        User user = userService.login(vo);
        if (user == null) {
            model.addAttribute("email", vo.getEmail());
            redirectAttributes.addFlashAttribute("errorMessage", "帳號或密碼錯誤");
            return REDIRECT_LOGIN;
        }
        if (user.getActiveStatus() == 0) {
            redirectAttributes.addFlashAttribute("errorMessage", "信箱未驗證");
            return REDIRECT_LOGIN;
        }
        user.setPassword(null);
        session.setAttribute("user", user);
        model.addAttribute("user", user);
        return REDIRECT_INDEX;
    }

    /**
     * 使用者登出
     * @param session
     * @return
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        logger.info("使用者登出...");
        if (session.getAttribute("user") != null) {
            session.removeAttribute("user");
        }
        return LOGIN;
    }
}
