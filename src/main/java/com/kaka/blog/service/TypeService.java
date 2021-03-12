package com.kaka.blog.service;

import com.kaka.blog.po.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * @author Kaka
 */
public interface TypeService {

    Type saveType(Type type);

    Type getType(Long id);

    Type getTypeName(String typeName);

    Type updateType(Long id, Type type);

    List<Type> listType();

    Page<Type> typeList(Pageable pageable);

    void deleteType(Long id);

}
