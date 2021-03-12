package com.kaka.blog.service;

import com.kaka.blog.dao.TypeRepository;
import com.kaka.blog.po.Type;
import com.kaka.blog.web.ClassNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * @author Kaka
 */
@Service
public class TypeServiceImpl implements TypeService {

    @Autowired
    TypeRepository typeRepository;

    @Transactional
    @Override
    public Type saveType(Type type) {
        return typeRepository.save(type);
    }

    @Transactional
    @Override
    public Type getTypeName(String typeName) {
        return typeRepository.findTypeByTypeName(typeName);
    }

    @Transactional
    @Override
    public Type getType(Long id) {
        return typeRepository.getOne(id);
    }

    @Override
    public List<Type> listType() {
        return typeRepository.findAll();
    }

    @Transactional
    @Override
    public Type updateType(Long id, Type type) {
        Type typeTarget = typeRepository.findById(id).orElse(null);
        if (typeTarget == null) {
            throw new ClassNotFoundException("找不到type");
        }
        BeanUtils.copyProperties(type, typeTarget);
        return typeRepository.save(typeTarget);
    }

    @Override
    public Page<Type> typeList(Pageable pageable) {
        return typeRepository.findAll(pageable);
    }

    @Transactional
    @Override
    public void deleteType(Long id) {
        typeRepository.deleteById(id);
    }
}
