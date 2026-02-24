package com.rabbiter.hrm.vo;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 管理员视图对象
 */
@Data
public class AdminVO {

    /**
     * 管理员ID
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
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 头像URL
     */
    private String avatar;

    /**
     * 角色：1-超级管理员 2-普通管理员
     */
    private Integer role;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 权限列表（权限代码）
     */
    private List<String> permissions;

    /**
     * 权限列表（权限名称）
     */
    private List<String> permissionNames;

    /**
     * 状态：0-禁用 1-正常
     */
    private Integer status;

    /**
     * 状态名称
     */
    private String statusName;

    /**
     * 最后登录时间
     */
    private LocalDateTime lastLoginTime;

    /**
     * 最后登录IP
     */
    private String lastLoginIp;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 获取角色名称
     */
    public String getRoleName() {
        if (role == null) return "未知";
        switch (role) {
            case 1: return "超级管理员";
            case 2: return "普通管理员";
            default: return "未知";
        }
    }

    /**
     * 获取状态名称
     */
    public String getStatusName() {
        if (status == null) return "未知";
        switch (status) {
            case 0: return "禁用";
            case 1: return "正常";
            default: return "未知";
        }
    }
}