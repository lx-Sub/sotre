package com.rabbiter.hrm.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 交换订单实体类
 * @author 李鑫
 * @date 2026/2/24
 */
@Data
public class ExchangeOrder {

    /**
     * 交换订单ID
     */
    private Long id;

    /**
     * 交换单号
     */
    private String exchangeNo;

    /**
     * 发起方ID
     */
    private Long initiatorId;

    /**
     * 接收方ID
     */
    private Long receiverId;

    /**
     * 发起方商品ID
     */
    private Long initiatorProductId;

    /**
     * 接收方商品ID
     */
    private Long receiverProductId;

    /**
     * 发起方商品名称
     */
    private String initiatorProductName;

    /**
     * 接收方商品名称
     */
    private String receiverProductName;

    /**
     * 补差价金额
     */
    private BigDecimal priceDiff;

    /**
     * 状态：0-待对方确认 1-待付款 2-待双方发货 3-待确认收货 4-已完成 5-已取消 6-仲裁中
     */
    private Integer status;

    /**
     * 发起方物流单号
     */
    private String initiatorLogisticsNo;

    /**
     * 接收方物流单号
     */
    private String receiverLogisticsNo;

    /**
     * 发起方发货时间
     */
    private LocalDateTime initiatorShipTime;

    /**
     * 接收方发货时间
     */
    private LocalDateTime receiverShipTime;

    /**
     * 发起方收货时间
     */
    private LocalDateTime initiatorReceiveTime;

    /**
     * 接收方收货时间
     */
    private LocalDateTime receiverReceiveTime;

    /**
     * 差价付款时间
     */
    private LocalDateTime payTime;

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