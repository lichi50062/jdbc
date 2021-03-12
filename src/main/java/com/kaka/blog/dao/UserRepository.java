package com.kaka.blog.dao;

import com.kaka.blog.po.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Kaka
 */
public interface UserRepository extends JpaRepository<User, Long> {

    User findUserByUsernameAndPassword(String userName, String userPassword);

    User findUserByActiveCode(String activeCode);

    Integer countByUsername(String userName);

    Integer countByEmail(String email);

    User findUserByEmail(String email);

    User findUserByUsername(String userName);
}
