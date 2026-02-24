package com.rabbiter.hrm.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 管理员权限关联实体类
 * @author 李鑫
 * @date 2026/2/24
 */
@Data
public class AdminPermission {

    /**
     * ID
     */
    private Long id;

    /**
     * 管理员ID
     */
    private Long adminId;

    /**
     * 权限ID
     */
    private Long permissionId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}