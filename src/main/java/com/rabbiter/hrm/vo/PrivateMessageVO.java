package com.rabbiter.hrm.vo;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 私信视图对象
 * @author 李鑫
 * @date 2026/2/24
 */
@Data
public class PrivateMessageVO {

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
     * 接收者ID
     */
    private Long receiverId;

    /**
     * 接收者姓名
     */
    private String receiverName;

    /**
     * 内容摘要
     */
    private String contentPreview;

    /**
     * 是否已读
     */
    private Boolean isRead;

    /**
     * 发送时间
     */
    private LocalDateTime createTime;
}