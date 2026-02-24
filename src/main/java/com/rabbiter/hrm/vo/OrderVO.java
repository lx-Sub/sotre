package com.rabbiter.hrm.vo;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单视图对象
 * @author 李鑫
 * @date 2026/2/24
 */
@Data
public class OrderVO {

    /**
     * 订单ID
     */
    private Long id;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 订单类型：1-销售 2-交换
     */
    private Integer orderType;

    /**
     * 订单类型名称
     */
    private String orderTypeName;

    /**
     * 买家ID
     */
    private Long buyerId;

    /**
     * 买家姓名
     */
    private String buyerName;

    /**
     * 卖家ID
     */
    private Long sellerId;

    /**
     * 卖家姓名
     */
    private String sellerName;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 订单金额
     */
    private BigDecimal amount;

    /**
     * 订单状态
     */
    private Integer status;

    /**
     * 状态名称
     */
    private String statusName;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 获取订单类型名称
     */
    public String getOrderTypeName() {
        return orderType == 1 ? "销售订单" : "交换订单";
    }
}