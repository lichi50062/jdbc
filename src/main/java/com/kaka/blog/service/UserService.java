package com.kaka.blog.service;

import com.kaka.blog.headler.EmailExistException;
import com.kaka.blog.headler.UserNameExitException;
import com.kaka.blog.po.User;
import com.kaka.blog.vo.UserLoginVo;
import com.kaka.blog.vo.UserRegisterVo;

/**
 * @author Kaka
 */
public interface UserService{

    /**
     * 使用者註冊
     * @param vo
     * @param requestURI
     * @return
     */
    void userRegister(UserRegisterVo vo);

    User isUser(UserRegisterVo vo);


    /**
     * 檢查使用者名稱是否重複
     * @param userName
     */
    void checkUserName(String userName) throws UserNameExitException;

    /**
     ** 檢查email是否重複
     * @param email
     */
    void checkEmail(String email) throws EmailExistException;
    /**
     * 根據信箱驗證碼查詢使用者
     * @param activeCode
     * @return
     */
    User findUserByActiveCode(String activeCode);

    /**
     * 修改使用者
     * @param user
     */
    void updateUser(User user);

    /**
     * 查詢使用者ID
     * @param email
     * @return
     */
    User findUserByEmail(String email);

    /**
     * 登入驗證
     * @param vo
     * @return
     */
    User login(UserLoginVo vo);

    Object findUserByName(String name);
}
