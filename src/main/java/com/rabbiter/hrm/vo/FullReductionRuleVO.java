package com.rabbiter.hrm.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @description:
 * @author: 李鑫
 * @date: 2026/2/24 18:19
 */
@Data
public class FullReductionRuleVO {
    private Long id;
    private BigDecimal conditionAmount;
    private BigDecimal discountAmount;
}
