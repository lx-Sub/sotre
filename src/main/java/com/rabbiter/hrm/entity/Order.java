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
public class Order {
    private Long id;
    private String orderNo; // 订单号
    private Long buyerId;
    private Long sellerId;
    private Integer sellerType; // 1-商家 2-个人
    private Long productId;
    private String productName;
    private BigDecimal price;
    private Integer quantity;
    private BigDecimal totalAmount;
    private Integer orderType; // 1-销售订单 2-交换订单
    private Integer status; // 0-待付款 1-待发货 2-待收货 3-已完成 4-退款中 5-已退款 6-已取消
    private String logisticsNo; // 物流单号
    private String logisticsCompany;
    private String receiverName;
    private String receiverPhone;
    private String receiverAddress;
    private String remark;
    private LocalDateTime createTime;
    private LocalDateTime payTime;
    private LocalDateTime shipTime;
    private LocalDateTime receiveTime;
}