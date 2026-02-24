package com.rabbiter.hrm.dto;

/**
 * @description:
 * @author: 李鑫
 * @date: 2026/2/24 17:52
 */

import lombok.Data;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 满减活动创建DTO
 */
@Data
public class FullReductionCreateDTO {

    @NotBlank(message = "活动名称不能为空")
    @Size(max = 100, message = "活动名称最多100字")
    private String name;

    @NotNull(message = "开始时间不能为空")
    private LocalDateTime startTime;

    @NotNull(message = "结束时间不能为空")
    private LocalDateTime endTime;

    @NotEmpty(message = "满减规则不能为空")
    private List<FullReductionRuleDTO> rules;
}

