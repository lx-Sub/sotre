package com.rabbiter.hrm.dto;

import lombok.Data;
import javax.validation.constraints.*;

@Data
public class CreditUpdateDTO {
    private Long userId;

    @NotNull(message = "信用分数不能为空")
    @Min(value = -100, message = "信用分变更范围-100到100")
    @Max(value = 100, message = "信用分变更范围-100到100")
    private Integer score;

    @NotBlank(message = "变更原因不能为空")
    @Size(max = 500, message = "原因最多500字")
    private String reason;

    private String type;
}