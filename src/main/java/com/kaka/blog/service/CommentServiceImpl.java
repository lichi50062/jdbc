package com.kaka.blog.service;

import com.kaka.blog.dao.CommentRepository;
import com.kaka.blog.po.Comment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Kaka
 */
@Service
public class CommentServiceImpl implements CommentService{
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final CommentRepository commentRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public List<Comment> listComments(Long blogId) {
        log.info("取得所有父留言...");
        Sort sort = Sort.by(Sort.Direction.ASC, "createTime");
        List<Comment> commentList = commentRepository.findByBlogIdAndParentCommentNull(blogId, sort);
        return eachComments(commentList);
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public Comment saveComment(Comment comment) {
        log.info("新增留言...");
       Long parentCommentId = comment.getParentComment().getId();
        if (parentCommentId != -1) {
            comment.setParentComment(commentRepository.getOne(parentCommentId));
        } else {
            comment.setParentComment(null);
        }
        comment.setCreateTime(new Date());
        return commentRepository.save(comment);
    }

    /**
     * 複製父留言
     * @param parentComments
     * @return
     */
    private List<Comment> eachComments(List<Comment> parentComments) {
        log.info("複製父留言...");
        List<Comment> newListParentComment = new ArrayList<>();
        for (Comment sourceParentComment : parentComments) {
            Comment targetParentComment = new Comment();
            BeanUtils.copyProperties(sourceParentComment, targetParentComment);
            newListParentComment.add(targetParentComment);
        }
        mergeChildComments(newListParentComment);
        return newListParentComment;
    }

    /**
     * 合併父留言下的子留言
     * @param parentComments
     * @return
     */
    private void mergeChildComments(List<Comment> parentComments) {
        log.info("合併父留言下的子留言...");
        for (Comment parentComment : parentComments) {
            List<Comment> listChildComments = parentComment.getChildComments();
            List<Comment> tempChildComments = new ArrayList<>();
            flattenComments(listChildComments, tempChildComments);
            parentComment.setChildComments(tempChildComments);
        }
    }

    /**
     * 找出子留言的子留言
     * @param childComments
     * @return
     */
    private void flattenComments(List<Comment> childComments, List<Comment> tempChildComments) {
        log.info("找出子留言的子留言...");
        for (Comment childComment : childComments) {
            tempChildComments.add(childComment);
            List<Comment> listChildComments = childComment.getChildComments();
            if (childComments.size() > 0) {
                flattenComments(listChildComments, tempChildComments);
            }
        }
    }
}
