package com.rabbiter.hrm.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 咨询实体类
 */
@Data
public class Consult {

    private Long id;
    private Long userId;                // 咨询用户ID
    private Long productId;             // 商品ID
    private String content;             // 咨询内容
    private Integer status;             // 状态：0-待回复 1-已回复
    private String replyContent;        // 回复内容
    private LocalDateTime replyTime;    // 回复时间
    private LocalDateTime createTime;
}