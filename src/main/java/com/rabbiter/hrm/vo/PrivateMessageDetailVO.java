package com.rabbiter.hrm.vo;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 私信详情视图对象
 * @author 李鑫
 * @date 2026/2/24
 */
@Data
public class PrivateMessageDetailVO {

    /**
     * 私信ID
     */
    private Long id;

    /**
     * 发送者ID
     */
    private Long senderId;

    /**
     * 发送者姓名
     */
    private String senderName;

    /**
     * 发送者头像
     */
    private String senderAvatar;

    /**
     * 接收者ID
     */
    private Long receiverId;

    /**
     * 接收者姓名
     */
    private String receiverName;

    /**
     * 接收者头像
     */
    private String receiverAvatar;

    /**
     * 完整内容
     */
    private String content;

    /**
     * 是否已读
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