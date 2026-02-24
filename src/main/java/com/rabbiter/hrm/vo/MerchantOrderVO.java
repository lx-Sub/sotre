package com.rabbiter.hrm.vo;

/**
 * @description:
 * @author: 李鑫
 * @date: 2026/2/24 17:54
 */

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 商家订单VO
 */
@Data
public class MerchantOrderVO {

    private Long id;
    private String orderNo;
    private Long buyerId;
    private String buyerName;
    private String buyerPhone;
    private Long productId;
    private String productName;
    private String productImage;
    private BigDecimal price;
    private Integer quantity;
    private BigDecimal totalAmount;
    private Integer orderType;
    private String orderTypeName;
    private Integer status;
    private String statusName;
    private LocalDateTime createTime;
    private LocalDateTime payTime;
    private LocalDateTime shipTime;

    public String getOrderTypeName() {
        return orderType == 1 ? "销售订单" : "交换订单";
    }

    public String getStatusName() {
        if (status == null) return "未知";
        switch (status) {
            case 0: return "待付款";
            case 1: return "待发货";
            case 2: return "待收货";
            case 3: return "已完成";
            case 4: return "退款中";
            case 5: return "已退款";
            case 6: return "已取消";
            default: return "未知";
        }
    }
}
