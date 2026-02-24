package com.rabbiter.hrm.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Admin {
    private Long id;
    private String username;
    private String password;
    private String nickname;
    private String email;
    private String phone;
    private String avatar;
    private Integer role; // 1-超级管理员 2-普通管理员
    private Integer status; // 0-禁用 1-正常
    private LocalDateTime lastLoginTime;
    private String lastLoginIp;
    private Long createBy;
    private LocalDateTime createTime;
    private Long updateBy;
    private LocalDateTime updateTime;
}