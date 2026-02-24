package com.rabbiter.hrm.dto;

import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @description:
 * @author: 李鑫
 * @date: 2026/2/24 18:16
 */
@Data
public class FullReductionRuleDTO {
    @NotNull(message = "满减条件不能为空")
    @DecimalMin(value = "0.01", message = "满减条件必须大于0")
    private BigDecimal conditionAmount;

    @NotNull(message = "减免金额不能为空")
    @DecimalMin(value = "0.01", message = "减免金额必须大于0")
    private BigDecimal discountAmount;

}
