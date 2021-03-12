package com.kaka.blog.dao;

import com.kaka.blog.po.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Kaka
 */
public interface BlogRepository extends JpaRepository<Blog, Long>, JpaSpecificationExecutor<Blog> {

    /**
     * Blog是否推薦
     * @return
     */
    @Query("select b from Blog b where b.recommend = true")
    List<Blog> findBlogByRecommend();

    /**
     * Blog是否存檔
     * @return
     */
    @Query("select b from Blog b where b.published = true ")
    Page<Blog> findBlogByPublished(Pageable pageable);

    /**
     * 搜尋Blog
     * @param pageable
     * @param query
     * @return
     */
    @Query("select b from Blog b where b.content like ?1 or b.title like ?1")
    Page<Blog> findBySearch(Pageable pageable, String query);

    /**
     * 後台Blog
     * @param pageable
     * @param userId
     * @return
     */
    @Query("select b from Blog b inner join b.user c where c.id = ?1")
    Page<Blog> findBlogByUserId(Long userId, Pageable pageable);

    /**
     * 更新blog瀏覽次數
     * 直接修改要加@Modifying
     * @param id
     */
    @Transactional()
    @Modifying
    @Query("update Blog b set b.views = b.views+1 where b.id = ?1")
    void updateViews(Long id);

    /**
     * 首頁最新文章
     * @param pageable
     * @return
     */
    @Query("select b from Blog b")
    List<Blog> findTop(Pageable pageable);
}
