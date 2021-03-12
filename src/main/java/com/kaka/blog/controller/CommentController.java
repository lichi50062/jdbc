package com.kaka.blog.controller;
import com.kaka.blog.po.Comment;
import com.kaka.blog.po.User;
import com.kaka.blog.service.BlogService;
import com.kaka.blog.service.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author Kaka
 */
@Controller
public class CommentController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Value("${comment.avatar}")
    String avatar;

    private final CommentService commentService;
    private final BlogService blogService;

    @Autowired
    public CommentController(CommentService commentService, BlogService blogService) {
        this.commentService = commentService;
        this.blogService = blogService;
    }

    /**
     * 取得所有留言
     * @param blogId
     * @param model
     * @return
     */
    @GetMapping("comments/{blogId}")
    public String commentList(@PathVariable Long blogId, Model model) {
        log.info("取得所有留言...");
        List<Comment> commentList = commentService.listComments(blogId);
        model.addAttribute("commentList", commentList);
        return "blog :: commentList";
    }

    /**
     * 新增留言
     * @param comment
     * @return
     */
    @PostMapping("comments")
    public String postComment(Comment comment, HttpSession session) {
        log.info("新增留言...");
        Long blogId = comment.getBlog().getId();
        comment.setBlog(blogService.getBlog(blogId).orElse(null));
        User user = (User) session.getAttribute("user");
        if (user != null && user.getEmail().equals(comment.getBlog().getUser().getEmail())) {
            comment.setAdminComment(true);
            comment.setAvatar(user.getAvatar());
        } else {
            comment.setAvatar(avatar);
        }
        commentService.saveComment(comment);
        return "redirect:/comments/" + blogId;
    }
}
