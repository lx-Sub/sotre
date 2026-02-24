package com.rabbiter.hrm.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单实体类
 * @author 李鑫
 * @date 2026/2/24
 */
@Data
public class Order {

    /**
     * 订单ID
     */
    private Long id;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 买家ID
     */
    private Long buyerId;

    /**
     * 卖家ID
     */
    private Long sellerId;

    /**
     * 卖家类型：1-商家 2-个人
     */
    private Integer sellerType;

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
     * 规格ID
     */
    private Long specId;

    /**
     * 规格描述
     */
    private String specDesc;

    /**
     * 成交单价
     */
    private BigDecimal price;

    /**
     * 购买数量
     */
    private Integer quantity;

    /**
     * 总金额
     */
    private BigDecimal totalAmount;

    /**
     * 订单类型：1-销售订单 2-交换订单
     */
    private Integer orderType;

    /**
     * 状态：0-待付款 1-待发货 2-待收货 3-已完成 4-退款中 5-已退款 6-已取消
     */
    private Integer status;

    /**
     * 物流单号
     */
    private String logisticsNo;

    /**
     * 物流公司
     */
    private String logisticsCompany;

    /**
     * 收货人姓名
     */
    private String receiverName;

    /**
     * 收货人电话
     */
    private String receiverPhone;

    /**
     * 收货地址
     */
    private String receiverAddress;

    /**
     * 买家备注
     */
    private String buyerRemark;

    /**
     * 卖家备注
     */
    private String sellerRemark;

    /**
     * 关闭原因
     */
    private String closeReason;

    /**
     * 付款时间
     */
    private LocalDateTime payTime;

    /**
     * 发货时间
     */
    private LocalDateTime shipTime;

    /**
     * 收货时间
     */
    private LocalDateTime receiveTime;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}