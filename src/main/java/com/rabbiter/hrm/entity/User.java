package com.rabbiter.hrm.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 用户实体类
 */
@Data
public class User {

    /**
     * 用户ID
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像URL
     */
    private String avatar;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 角色：1-普通用户 2-商家 3-管理员
     */
    private Integer role;

    /**
     * 状态：0-冻结 1-正常 -1-已注销
     */
    private Integer status;

    /**
     * 商家状态：0-待审核 1-正常 2-驳回
     */
    private Integer merchantStatus;

    /**
     * 信用分
     */
    private Integer creditScore;

    /**
     * 是否实名认证
     */
    private Boolean verified;

    /**
     * 寄售功能是否开启
     */
    private Boolean consignmentEnabled;

    /**
     * 寄售资质有效期
     */
    private LocalDateTime consignmentExpiry;

    /**
     * 最后登录时间
     */
    private LocalDateTime lastLoginTime;

    /**
     * 最后登录IP
     */
    private String lastLoginIp;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 删除时间
     */
    private LocalDateTime deleteTime;
}