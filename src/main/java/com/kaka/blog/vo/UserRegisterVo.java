package com.kaka.blog.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author Kaka
 */

@Data
public class UserRegisterVo {

    @NotBlank(message = "請輸入帳號")
    private String userName;
    @NotBlank(message = "請輸入密碼")
    private String userPassword;
    @NotBlank(message = "請輸入信箱")
    private String email;
    @NotBlank(message = "請輸入大頭貼網址")
    private String avatar;
}
