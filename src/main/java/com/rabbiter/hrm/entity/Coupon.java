package com.rabbiter.hrm.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 优惠券实体类
 */
@Data
public class Coupon {

    private Long id;
    private Long shopId;                // 店铺ID
    private String name;                // 优惠券名称
    private Integer type;               // 类型：1-满减券 2-折扣券
    private BigDecimal conditionAmount; // 满减条件
    private BigDecimal discountAmount;  // 减免金额
    private BigDecimal discountRate;    // 折扣率
    private Integer totalCount;         // 发放总量
    private Integer remainingCount;     // 剩余数量
    private Integer perLimit;           // 每人限领
    private LocalDateTime startTime;    // 开始时间
    private LocalDateTime endTime;      // 结束时间
    private Integer status;             // 状态：0-下架 1-上架
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}