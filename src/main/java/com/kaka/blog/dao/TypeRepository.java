package com.kaka.blog.dao;

import com.kaka.blog.po.Type;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Kaka
 */
public interface TypeRepository extends JpaRepository<Type,Long> {

    Type findTypeByTypeName(String typeName);
}
