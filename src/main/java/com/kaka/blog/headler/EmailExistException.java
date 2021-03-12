package com.kaka.blog.headler;

/**
 * @author Kaka
 */
public class EmailExistException extends RuntimeException{

    public EmailExistException() {
        super("信箱重複");
    }
}
