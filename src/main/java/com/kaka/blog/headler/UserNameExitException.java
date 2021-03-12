package com.kaka.blog.headler;

/**
 * @author Kaka
 */
public class UserNameExitException extends RuntimeException{

    public UserNameExitException() {
        super("使用者名稱重複");
    }
}
