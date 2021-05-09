package service;

import entity.User;

public interface UserService {

    int register(User user);

    int login(String userName, String password);

    User findByName(String userName);
}
