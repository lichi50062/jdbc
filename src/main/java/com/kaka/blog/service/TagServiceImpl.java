package com.kaka.blog.service;

import com.kaka.blog.dao.TagRepository;
import com.kaka.blog.po.Tag;
import com.kaka.blog.web.ClassNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Kaka
 */
@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagRepository tagRepository;

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public Tag saveTag(Tag tag) {
        return tagRepository.save(tag);
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public Tag getTag(Long id) {
        return tagRepository.getOne(id);
    }

    @Override
    public Tag getTagByName(String tagName) {
        return tagRepository.findByTagName(tagName);
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public Page<Tag> listTag(Pageable pageable) {
        return tagRepository.findAll(pageable);
    }

    @Override
    public List<Tag> listTag() {
        return tagRepository.findAll();
    }

    @Override
    public List<Tag> listTag(String ids) { //1,2,3
        return tagRepository.findAllById(convertToList(ids));
    }

    private List<Long> convertToList(String ids) {
        List<Long> list = new ArrayList<>();
        if (!"".equals(ids) && ids != null) {
            String[] idarray = ids.split(",");
            for (String s : idarray) {
                list.add(new Long(s));
            }
        }
        return list;
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public Tag updateTag(Long id, Tag tag) throws ClassNotFoundException {
        Tag tag1 = tagRepository.findById(id).orElse(null);
        if (tag1 == null) {
            throw new ClassCastException("找不到tag");
        }
        BeanUtils.copyProperties(tag,tag1);
        return tagRepository.save(tag1);
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void deleteTag(Long id) {
        tagRepository.deleteById(id);
    }
}
