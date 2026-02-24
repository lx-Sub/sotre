package com.rabbiter.hrm.vo;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单详情视图对象
 * @author 李鑫
 * @date 2026/2/24
 */
@Data
public class OrderDetailVO extends OrderVO {

    /**
     * 商品图片
     */
    private String productImage;

    /**
     * 商品数量
     */
    private Integer quantity;

    /**
     * 单价
     */
    private BigDecimal unitPrice;

    /**
     * 支付方式
     */
    private Integer paymentMethod;

    /**
     * 支付方式名称
     */
    private String paymentMethodName;

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
     * 物流单号
     */
    private String logisticsNo;

    /**
     * 物流公司
     */
    private String logisticsCompany;

    /**
     * 买家备注
     */
    private String buyerRemark;

    /**
     * 卖家备注
     */
    private String sellerRemark;

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
     * 完成时间
     */
    private LocalDateTime completeTime;

    /**
     * 获取支付方式名称
     */
    public String getPaymentMethodName() {
        if (paymentMethod == null) return "未知";
        switch (paymentMethod) {
            case 1: return "微信支付";
            case 2: return "支付宝";
            case 3: return "银行卡";
            default: return "未知";
        }
    }
}