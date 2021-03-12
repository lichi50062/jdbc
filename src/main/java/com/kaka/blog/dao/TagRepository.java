package com.kaka.blog.dao;

import com.kaka.blog.po.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Kaka
 */
public interface TagRepository extends JpaRepository<Tag,Long> {

    Tag findByTagName(String tagName);

    @Query("select t from Tag t")
    List<Tag> findTop(Pageable pageable);
}
