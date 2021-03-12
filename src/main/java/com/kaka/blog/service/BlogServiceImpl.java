package com.kaka.blog.service;

import com.kaka.blog.dao.BlogRepository;
import com.kaka.blog.po.Blog;
import com.kaka.blog.po.BlogQuery;
import com.kaka.blog.po.Type;
import com.kaka.blog.util.BeanUtil;
import com.kaka.blog.util.MarkdownUtils;
import com.kaka.blog.web.ClassNotFoundException;
import lombok.val;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import javax.persistence.criteria.*;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author Kaka
 */
@Service
public class BlogServiceImpl implements BlogService {
    private final BlogRepository blogRepository;

    @Autowired
    public BlogServiceImpl(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public Blog saveBlog(Blog blog) {
        if (blog.getId() != null) {
            blog.setUpdateTime(new Date());
        } else {
            blog.setCreateTime(new Date());
            blog.setUpdateTime(new Date());
            blog.setViews(0);
        }
        return blogRepository.save(blog);
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public Blog updateBlog(Blog blog, Long id) {
        Blog targetBlog = blogRepository.findById(id).orElse(null);
        if (targetBlog == null) {
            throw new ClassNotFoundException("blog查詢失敗");
        }
        BeanUtils.copyProperties(blog, targetBlog, BeanUtil.getNullProperty(blog));
        targetBlog.setUpdateTime(new Date());
        return blogRepository.save(targetBlog);
    }

    @Override
    public Blog getAndConvert(Long id) {
        Blog blog = blogRepository.findById(id).orElse(null);
        if (blog == null) {
            throw new ClassNotFoundException("blog未找到");
        }
        Blog targetBlog = new Blog();
        BeanUtils.copyProperties(blog, targetBlog);
        String content = targetBlog.getContent();
        targetBlog.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
        blogRepository.updateViews(id);
        return targetBlog;
    }

    @Override
    public Optional<Blog> getBlog(Long id) {
        return blogRepository.findById(id);
    }

    /**
     * Blog查詢
     * @param pageable
     * @param blogQuery
     * @return
     */
    @Override
    public Page<Blog> listBlog(Pageable pageable, BlogQuery blogQuery) {
        return blogRepository.findAll(new Specification<Blog>() {
            /**
             * Root：代表Criteria查詢的Object，與SQL查詢中的FROM子句類似
             * CriteriaQuery：代表一個specific的頂層查詢Object，它包含着查詢的各個部分，比如：select 、from、where、group by、order by等
             * CriteriaBuilder是Predicate實例的工廠，通過調用CriteriaBuilder 的條件方法（ equal，notEqual， gt， ge，lt， le，between，like等）創建Predicate。
             */
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (!"".equals(blogQuery.getTitle()) && blogQuery.getTitle() != null) {
                    predicates.add(criteriaBuilder.like(root.<String>get("title"), "%"+blogQuery.getTitle()+"%"));
                }
                if (blogQuery.getTypeId() != null) {
                    predicates.add(criteriaBuilder.equal(root.<Type>get("type").get("id"), blogQuery.getTypeId()));
                }
                if (blogQuery.isRecommend()) {
                    predicates.add(criteriaBuilder.equal(root.<Boolean>get("recommend"), blogQuery.isRecommend()));
                }
                criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        },pageable);
    }

    @Override
    public Page<Blog> listBlog(Long tagId, Pageable pageable) {
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Join join = root.join("tags");
                return criteriaBuilder.equal(join.get("id"), tagId);
            }
        }, pageable);
    }

    @Override
    public Page<Blog> searchBlog(Pageable pageable, String query) {
        return blogRepository.findBySearch(pageable, query);
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable) {
        return blogRepository.findBlogByPublished(pageable);
    }

    @Override
    public Page<Blog> findBlogByUserId(Long userId, Pageable pageable) {
        return blogRepository.findBlogByUserId(userId, pageable);
    }

    @Override
    public List<Blog> blogFindTop(Integer x) {
        Sort sort = Sort.by(Sort.Direction.DESC, "updateTime");
        Pageable pageable = PageRequest.of(0, x, sort);
        return blogRepository.findTop(pageable);
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void deleteBlog(Long id) {
        blogRepository.deleteById(id);
    }
}
