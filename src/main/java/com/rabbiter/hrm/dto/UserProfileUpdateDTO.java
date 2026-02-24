package com.rabbiter.hrm.dto;

import lombok.Data;
import javax.validation.constraints.*;

/**
 * 用户资料更新DTO
 * @author 李鑫
 * @date 2026/2/24
 */
@Data
public class UserProfileUpdateDTO {

    /**
     * 昵称
     */
    @Size(max = 50, message = "昵称不能超过50字")
    private String nickname;

    /**
     * 头像URL
     */
    private String avatar;

    /**
     * 邮箱
     */
    @Email(message = "邮箱格式不正确")
    private String email;

    /**
     * 性别：0-未知 1-男 2-女
     */
    private Integer gender;

    /**
     * 生日
     */
    private String birthday;

    /**
     * 个人简介
     */
    @Size(max = 200, message = "个人简介不能超过200字")
    private String bio;
}