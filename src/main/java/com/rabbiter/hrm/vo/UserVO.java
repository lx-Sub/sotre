package com.rabbiter.hrm.vo;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UserVO {
    private Long id;
    private String username;
    private String nickname;
    private String avatar;
    private String phone;
    private String email;
    private Integer role;
    private String roleName;
    private Integer status;
    private Integer creditScore;
    private LocalDateTime createTime;
    private LocalDateTime lastLoginTime;
    private Boolean verified;
    private Boolean consignmentEnabled;
}