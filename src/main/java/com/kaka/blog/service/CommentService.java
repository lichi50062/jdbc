package com.kaka.blog.service;

import com.kaka.blog.po.Comment;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * @author Kaka
 */
public interface CommentService {

    /**
     * 取得所有留言
     * @param blogId
     * @return
     */
    List<Comment> listComments(Long blogId);

    /**
     * 新增留言
     * @param comment
     * @return
     */
    Comment saveComment(Comment comment);
}
