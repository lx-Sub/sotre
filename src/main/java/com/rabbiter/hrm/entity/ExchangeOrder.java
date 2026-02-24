package com.rabbiter.hrm.entity;

/**
 * @description:
 * @author: 李鑫
 * @date: 2026/2/24 11:31
 */
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ExchangeOrder {
    private Long id;
    private String exchangeNo; // 交换单号
    private Long initiatorId; // 发起方ID
    private Long receiverId; // 接收方ID
    private Long initiatorProductId; // 发起方商品ID
    private Long receiverProductId; // 接收方商品ID
    private BigDecimal priceDiff; // 补差价金额
    private Integer status; // 0-待对方确认 1-待付款 2-待双方发货 3-待确认收货 4-已完成 5-已取消 6-仲裁中
    private String initiatorLogisticsNo; // 发起方物流单号
    private String receiverLogisticsNo; // 接收方物流单号
    private LocalDateTime createTime;
    private LocalDateTime completeTime;
}
