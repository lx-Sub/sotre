package com.rabbiter.hrm.vo;

/**
 * @description:
 * @author: 李鑫
 * @date: 2026/2/24 17:58
 */


import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 店铺评价VO
 */
@Data
public class ShopReviewVO {

    private Long id;
    private Long orderId;
    private Long userId;
    private String userName;
    private String userAvatar;
    private Integer userCreditLevel;
    private String productName;
    private String productImage;

    // 多维度评分
    private Integer matchScore;      // 描述相符
    private Integer communicationScore; // 沟通态度
    private Integer shippingScore;    // 发货速度
    private Integer overallScore;     // 综合评分

    private String content;
    private List<String> images;
    private String replyContent;
    private LocalDateTime replyTime;
    private LocalDateTime createTime;
}