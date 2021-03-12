package com.kaka.blog.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * @author Kaka
 */
@Service
public class MailServiceImpl implements MailService{

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String from;

    /**
     * 發送驗證信
     * @param sendTo
     * @param mailTitle
     * @param mailContent
     */
    @Override
    public void sendMail(String sendTo, String mailTitle, String mailContent) {
        logger.info("開始寫入驗證信...");
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true);
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setSubject(mailTitle);
            mimeMessageHelper.setTo(sendTo);
            mimeMessageHelper.setText(mailContent, true);
            javaMailSender.send(message);
            logger.info("驗證信寄出...");
        } catch (MessagingException e) {
            logger.error("驗證信寄出錯誤...", e);
        }

    }
}
