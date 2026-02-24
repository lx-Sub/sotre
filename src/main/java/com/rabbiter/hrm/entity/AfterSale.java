package com.rabbiter.hrm.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 售后实体类
 * @author 李鑫
 * @date 2026/2/24
 */
@Data
public class AfterSale {

    /**
     * 售后ID
     */
    private Long id;

    /**
     * 售后单号
     */
    private String afterSaleNo;

    /**
     * 关联订单ID
     */
    private Long orderId;

    /**
     * 关联交换订单ID
     */
    private Long exchangeOrderId;

    /**
     * 申请人ID
     */
    private Long userId;

    /**
     * 售后类型：1-退款 2-退货退款 3-换货
     */
    private Integer type;

    /**
     * 申请原因
     */
    private String reason;

    /**
     * 退款金额
     */
    private BigDecimal amount;

    /**
     * 状态：0-待处理 1-卖家同意 2-卖家拒绝 3-平台仲裁中 4-已完成 5-已关闭
     */
    private Integer status;

    /**
     * 凭证图片（JSON数组）
     */
    private String evidenceImages;

    /**
     * 卖家回复
     */
    private String sellerResponse;

    /**
     * 仲裁结果
     */
    private String arbitrationResult;

    /**
     * 仲裁时间
     */
    private LocalDateTime arbitrationTime;

    /**
     * 完成时间
     */
    private LocalDateTime completeTime;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}