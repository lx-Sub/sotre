package com.rabbiter.hrm.vo;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 用户资料VO
 * @author 李鑫
 * @date 2026/2/24
 */
@Data
public class UserProfileVO {

    /**
     * 用户ID
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

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
     * 性别：0-未知 1-男 2-女
     */
    private Integer gender;

    /**
     * 性别名称
     */
    private String genderName;

    /**
     * 生日
     */
    private String birthday;

    /**
     * 个人简介
     */
    private String bio;

    /**
     * 角色：1-普通用户 2-商家 3-管理员
     */
    private Integer role;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 状态：0-冻结 1-正常
     */
    private Integer status;

    /**
     * 是否实名认证
     */
    private Boolean verified;

    /**
     * 信用分
     */
    private Integer creditScore;

    /**
     * 信用等级
     */
    private String creditLevel;

    /**
     * 信用徽章
     */
    private String creditBadge;

    /**
     * 注册时间
     */
    private LocalDateTime createTime;

    public String getGenderName() {
        if (gender == null) return "未知";
        switch (gender) {
            case 1: return "男";
            case 2: return "女";
            default: return "未知";
        }
    }

    public String getRoleName() {
        if (role == null) return "未知";
        switch (role) {
            case 1: return "普通用户";
            case 2: return "商家";
            case 3: return "管理员";
            default: return "未知";
        }
    }
}