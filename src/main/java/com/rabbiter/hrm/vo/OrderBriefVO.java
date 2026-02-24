package com.rabbiter.hrm.vo;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单简要信息视图对象
 * 用于在用户详情页面显示最近的订单列表
 */
@Data
public class OrderBriefVO {

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
     * 订单类型名称
     */
    private String orderTypeName;

    /**
     * 订单金额
     */
    private BigDecimal amount;

    /**
     * 订单状态：0-待付款 1-已付款 2-已发货 3-已完成 4-已取消 5-退款中
     */
    private Integer status;

    /**
     * 订单状态名称
     */
    private String statusName;

    /**
     * 商品名称/简要信息
     */
    private String productInfo;

    /**
     * 商品数量
     */
    private Integer productCount;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 完成时间
     */
    private LocalDateTime completeTime;

    /**
     * 对方用户ID（买家/交换方）
     */
    private Long otherUserId;

    /**
     * 对方用户名
     */
    private String otherUserName;

    /**
     * 是否有售后纠纷
     */
    private Boolean hasDispute;

    /**
     * 获取状态名称
     */
    public String getStatusName() {
        if (status == null) return "未知";
        switch (status) {
            case 0: return "待付款";
            case 1: return "已付款";
            case 2: return "已发货";
            case 3: return "已完成";
            case 4: return "已取消";
            case 5: return "退款中";
            default: return "未知";
        }
    }

    /**
     * 获取订单类型名称
     */
    public String getOrderTypeName() {
        if (orderType == null) return "未知";
        switch (orderType) {
            case 1: return "销售订单";
            case 2: return "交换订单";
            default: return "未知";
        }
    }
}