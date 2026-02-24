package com.rabbiter.hrm.dto;

import lombok.Data;
import javax.validation.constraints.*;

@Data
public class CreditAppealDTO {
    @NotNull(message = "申诉ID不能为空")
    private Long appealId;

    @NotNull(message = "处理结果不能为空")
    private Integer result;

    private String feedback;
    private Integer adjustScore;
}