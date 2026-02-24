package com.rabbiter.hrm.vo;

import lombok.Data;

/**
 * 用户信用VO
 */
@Data
public class UserCreditVO {
    private Long userId;
    private String username;
    private String nickname;
    private Integer creditScore;
    private String creditLevel;
}