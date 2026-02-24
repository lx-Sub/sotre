package com.rabbiter.hrm.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 评价实体类
 */
@Data
public class Review {

    private Long id;
    private Long orderId;               // 订单ID
    private Long userId;                // 用户ID
    private Long productId;              // 商品ID
    private Integer matchScore;          // 描述相符评分
    private Integer communicationScore;  // 沟通态度评分
    private Integer shippingScore;       // 发货速度评分
    private Integer overallScore;        // 综合评分
    private String content;              // 评价内容
    private String images;               // 评价图片JSON
    private String replyContent;         // 商家回复
    private LocalDateTime replyTime;     // 回复时间
    private LocalDateTime createTime;
}