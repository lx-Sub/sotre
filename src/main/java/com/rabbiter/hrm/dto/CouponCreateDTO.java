package com.rabbiter.hrm.dto;

import lombok.Data;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 优惠券创建DTO
 */
@Data
public class CouponCreateDTO {

    @NotBlank(message = "优惠券名称不能为空")
    @Size(max = 100, message = "优惠券名称最多100字")
    private String name;

    @NotNull(message = "优惠券类型不能为空")
    private Integer type; // 1-满减券 2-折扣券

    @DecimalMin(value = "0.01", message = "满减条件必须大于0")
    private BigDecimal conditionAmount; // 满减条件

    private BigDecimal discountAmount; // 减免金额

    @DecimalMin(value = "0.01", message = "折扣率必须大于0")
    @DecimalMax(value = "0.99", message = "折扣率不能超过0.99")
    private BigDecimal discountRate; // 折扣率

    @NotNull(message = "发放总量不能为空")
    @Min(value = 1, message = "发放总量至少为1")
    private Integer totalCount;

    @NotNull(message = "每人限领不能为空")
    @Min(value = 1, message = "每人限领至少为1")
    private Integer perLimit = 1;

    @NotNull(message = "开始时间不能为空")
    private LocalDateTime startTime;

    @NotNull(message = "结束时间不能为空")
    private LocalDateTime endTime;
}