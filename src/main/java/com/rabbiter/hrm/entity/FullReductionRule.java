package com.rabbiter.hrm.entity;

import lombok.Data;
import java.math.BigDecimal;

/**
 * 满减活动规则实体类
 */
@Data
public class FullReductionRule {

    private Long id;
    private Long reductionId;           // 活动ID
    private BigDecimal conditionAmount; // 满减条件
    private BigDecimal discountAmount;  // 减免金额
}