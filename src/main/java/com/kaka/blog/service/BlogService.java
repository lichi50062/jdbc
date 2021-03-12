package com.kaka.blog.service;

import com.kaka.blog.po.Blog;
import com.kaka.blog.po.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

/**
 * @author Kaka
 */
public interface BlogService {

    Blog saveBlog(Blog blog);

    /**
     * Markdown轉換成html
     * @param id
     * @return
     */
    Blog getAndConvert(Long id);

    Optional<Blog> getBlog(Long id);

    Page<Blog> listBlog(Pageable pageable, BlogQuery blogQuery);

    Page<Blog> listBlog(Long tagId, Pageable pageable);
    /**
     * 搜尋Blog
     * @param pageable
     * @param query
     * @return
     */
    Page<Blog> searchBlog(Pageable pageable, String query);

    /**
     * 前台Blog
     * @param pageable
     * @return
     */
    Page<Blog> listBlog(Pageable pageable);

    /**
     * 後台Blog
     * @param userId
     * @param pageable
     * @return
     */
    Page<Blog> findBlogByUserId(Long userId, Pageable pageable);

    Blog updateBlog(Blog blog, Long id);

    /**
     * 首頁最新文章
     * @param x
     * @return
     */
    List<Blog> blogFindTop(Integer x);

    void deleteBlog(Long id);

}
