package com.rabbiter.hrm.vo;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 交换订单视图对象
 * @author 李鑫
 * @date 2026/2/24
 */
@Data
public class ExchangeOrderVO {

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
     * 发起方姓名
     */
    private String initiatorName;

    /**
     * 接收方ID
     */
    private Long receiverId;

    /**
     * 接收方姓名
     */
    private String receiverName;

    /**
     * 发起方商品
     */
    private String initiatorProduct;

    /**
     * 接收方商品
     */
    private String receiverProduct;

    /**
     * 补差价金额
     */
    private BigDecimal priceDiff;

    /**
     * 状态
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
     * 完成时间
     */
    private LocalDateTime completeTime;

    /**
     * 获取状态名称
     */
    public String getStatusName() {
        if (status == null) return "未知";
        switch (status) {
            case 0: return "待对方确认";
            case 1: return "待付款";
            case 2: return "待双方发货";
            case 3: return "待确认收货";
            case 4: return "已完成";
            case 5: return "已取消";
            case 6: return "仲裁中";
            default: return "未知";
        }
    }
}