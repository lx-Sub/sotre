package com.rabbiter.hrm.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 用户关注实体类
 * @author 李鑫
 * @date 2026/2/24
 */
@Data
public class UserFollow {

    /**
     * 关注ID
     */
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 目标用户ID
     */
    private Long targetUserId;

    /**
     * 关注时间
     */
    private LocalDateTime createTime;
}