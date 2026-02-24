package com.rabbiter.hrm.vo;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 售后视图对象
 * @author 李鑫
 * @date 2026/2/24
 */
@Data
public class AfterSaleVO {

    /**
     * 售后ID
     */
    private Long id;

    /**
     * 售后单号
     */
    private String afterSaleNo;

    /**
     * 订单ID
     */
    private Long orderId;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 申请人ID
     */
    private Long userId;

    /**
     * 申请人姓名
     */
    private String userName;

    /**
     * 售后类型：1-退款 2-退货退款 3-换货
     */
    private Integer type;

    /**
     * 售后类型名称
     */
    private String typeName;

    /**
     * 申请原因
     */
    private String reason;

    /**
     * 退款金额
     */
    private BigDecimal amount;

    /**
     * 状态：0-待处理 1-卖家同意 2-卖家拒绝 3-平台仲裁中 4-已完成 5-已关闭
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
     * 获取售后类型名称
     */
    public String getTypeName() {
        if (type == null) return "未知";
        switch (type) {
            case 1: return "退款";
            case 2: return "退货退款";
            case 3: return "换货";
            default: return "未知";
        }
    }

    /**
     * 获取状态名称
     */
    public String getStatusName() {
        if (status == null) return "未知";
        switch (status) {
            case 0: return "待处理";
            case 1: return "卖家同意";
            case 2: return "卖家拒绝";
            case 3: return "平台仲裁中";
            case 4: return "已完成";
            case 5: return "已关闭";
            default: return "未知";
        }
    }
}