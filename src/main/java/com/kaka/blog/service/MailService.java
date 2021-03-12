package com.kaka.blog.service;

/**
 * @author Kaka
 */
public interface MailService {

    /**
     * 寄出驗證信
     * @param sendTo
     * @param mailTitle
     * @param mailContent
     */
    void sendMail(String sendTo, String mailTitle, String mailContent);
}
