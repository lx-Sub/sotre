package com.rabbiter.hrm.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单实体类
 */
@Data
public class Order {

    /**
     * 订单ID
     */
    private Long id;

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 订单类型：1-销售订单 2-交换订单
     */
    private Integer orderType;

    /**
     * 用户ID（买家/发起方）
     */
    private Long userId;

    /**
     * 对方用户ID（卖家/接收方）
     */
    private Long otherUserId;

    /**
     * 商品ID
     */
    private Long productId;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 商品图片
     */
    private String productImage;

    /**
     * 商品数量
     */
    private Integer productCount;

    /**
     * 订单金额
     */
    private BigDecimal amount;

    /**
     * 订单状态：0-待付款 1-已付款 2-已发货 3-已完成 4-已取消 5-退款中
     */
    private Integer status;

    /**
     * 支付方式：1-微信 2-支付宝 3-银行卡
     */
    private Integer paymentMethod;

    /**
     * 支付时间
     */
    private LocalDateTime paymentTime;

    /**
     * 发货时间
     */
    private LocalDateTime deliveryTime;

    /**
     * 完成时间
     */
    private LocalDateTime completeTime;

    /**
     * 是否有售后纠纷
     */
    private Boolean hasDispute;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}