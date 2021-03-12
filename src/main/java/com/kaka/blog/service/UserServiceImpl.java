package com.kaka.blog.service;

import com.kaka.blog.dao.UserRepository;
import com.kaka.blog.headler.EmailExistException;
import com.kaka.blog.headler.UserNameExitException;
import com.kaka.blog.po.User;
import com.kaka.blog.util.UUIDutils;
import com.kaka.blog.vo.UserLoginVo;
import com.kaka.blog.vo.UserRegisterVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author Kaka
 */
@Service
public class UserServiceImpl implements UserService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MailService mailService;

    @Override
    public User isUser(UserRegisterVo vo) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return userRepository.findUserByUsernameAndPassword(vo.getUserName(), passwordEncoder.encode(vo.getUserPassword()));
    }

    /**
     * 使用者註冊並發送驗證信
     * @param vo
     * @param requestURI
     * @return
     */
    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void userRegister(UserRegisterVo vo) {
        logger.info("使用者註冊...");
        //檢查資料是否重複
        checkUserName(vo.getUserName());
        checkEmail(vo.getEmail());

        User user = new User();
        user.setUsername(vo.getUserName());
        user.setEmail(vo.getEmail());
        //密碼加鹽
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encode = passwordEncoder.encode(vo.getUserPassword());
        user.setPassword(encode);

        user.setActiveStatus(0);
        user.setActiveCode(UUIDutils.getUUID());
        String activeCode = user.getActiveCode();
        String mailTitle = "blog信箱驗證";
        String mailContent = "<a href=\"http://127.0.0.1:8080/checkCode?activeCode=" + activeCode + "\">驗證連結:" + activeCode + "</a>";
        mailService.sendMail(vo.getEmail(), mailTitle, mailContent);
        user.setCreateTime(new Date());
        userRepository.save(user);
    }

    @Override
    public void checkUserName(String userName) throws UserNameExitException {
        Integer countByUsername = userRepository.countByUsername(userName);
        if (countByUsername > 0) {
            throw new UserNameExitException();
        }
    }

    @Override
    public void checkEmail(String email) throws EmailExistException {
        Integer countByEmail = userRepository.countByEmail(email);
        if (countByEmail > 0) {
            throw new EmailExistException();
        }
    }

    /**
     * 根據信箱驗證碼查詢使用者
     * @param activeCode
     * @return
     */
    @Override
    public User findUserByActiveCode(String activeCode) {
        logger.info("根據信箱驗證碼查詢使用者...");
        return userRepository.findUserByActiveCode(activeCode);
    }

    @Override
    public void updateUser(User user) {
        userRepository.saveAndFlush(user);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public User login(UserLoginVo vo) {
        User user = userRepository.findUserByEmail(vo.getEmail());
        if (user == null) {
            return null;
        } else {
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            boolean matches = bCryptPasswordEncoder.matches(vo.getPassword(), user.getPassword());
            if (matches) {
                return user;
            } else {
                return null;
            }
        }
    }

    @Override
    public User findUserByName(String userName) {
        return userRepository.findUserByUsername(userName);
    }
}