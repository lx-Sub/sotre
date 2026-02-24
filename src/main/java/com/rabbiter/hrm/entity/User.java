package com.rabbiter.hrm.entity;

/**
 * @description:
 * @author: 李鑫
 * @date: 2026/2/24 11:29
 */

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class User {
    private Long id;
    private String username;
    private String password;
    private String phone;
    private String email;
    private String avatar;
    private Integer role; // 1-管理员 2-商家 3-普通用户
    private Integer status; // 0-正常 1-冻结 2-注销
    private Integer creditScore; // 信用分
    private String realName; // 真实姓名
    private String idCard; // 身份证号
    private Integer isMerchant; // 0-否 1-是
    private String merchantLicense; // 营业执照
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private LocalDateTime lastLoginTime;
}
