package service;

import dao.UserDao;
import entity.User;

public class UserServiceImpl implements UserService {
    UserDao userDao = new UserDao();
    // 註冊
    @Override
    public int register(User user) {
        return userDao.register(user);
    }
    // 登陸
    @Override
    public int login(String userName, String password) {
        return userDao.login(userName,password);
    }

    // 根據使用者名稱查詢資訊
    @Override
    public User findByName(String userName) {
        return userDao.findByName(userName);
    }
}
