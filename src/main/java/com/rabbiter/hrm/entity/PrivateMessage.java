package com.rabbiter.hrm.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 私信实体类
 * @author 李鑫
 * @date 2026/2/24
 */
@Data
public class PrivateMessage {

    /**
     * 私信ID
     */
    private Long id;

    /**
     * 发送者ID
     */
    private Long senderId;

    /**
     * 接收者ID
     */
    private Long receiverId;

    /**
     * 内容
     */
    private String content;

    /**
     * 是否已读：0-否 1-是
     */
    private Boolean isRead;

    /**
     * 阅读时间
     */
    private LocalDateTime readTime;

    /**
     * 发送时间
     */
    private LocalDateTime createTime;
}