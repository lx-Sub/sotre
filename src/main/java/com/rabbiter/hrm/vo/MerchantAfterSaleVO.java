package com.rabbiter.hrm.vo;

/**
 * @description:
 * @author: 李鑫
 * @date: 2026/2/24 17:55
 */

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 商家售后VO
 */
@Data
public class MerchantAfterSaleVO {

    private Long id;
    private String afterSaleNo;
    private Long orderId;
    private String orderNo;
    private Long userId;
    private String userName;
    private Integer type;
    private String typeName;
    private String reason;
    private BigDecimal amount;
    private Integer status;
    private String statusName;
    private LocalDateTime createTime;

    public String getTypeName() {
        if (type == null) return "未知";
        switch (type) {
            case 1: return "退款";
            case 2: return "退货退款";
            case 3: return "换货";
            default: return "未知";
        }
    }

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