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
 * 优惠券VO
 */
@Data
public class CouponVO {

    private Long id;
    private String name;
    private Integer type;
    private String typeName;
    private BigDecimal conditionAmount;
    private BigDecimal discountAmount;
    private BigDecimal discountRate;
    private Integer totalCount;
    private Integer remainingCount;
    private Integer perLimit;
    private Integer receivedCount;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer status;
    private String statusName;
    private LocalDateTime createTime;

    public String getTypeName() {
        return type == 1 ? "满减券" : "折扣券";
    }

    public String getStatusName() {
        if (status == null) return "未知";
        switch (status) {
            case 0: return "已下架";
            case 1: return "进行中";
            case 2: return "已结束";
            default: return "未知";
        }
    }
}
