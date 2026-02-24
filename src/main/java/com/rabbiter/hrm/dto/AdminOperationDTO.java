package com.rabbiter.hrm.dto;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import javax.validation.constraints.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminOperationDTO {
    @NotNull(message = "用户ID不能为空")
    private Long userId;

    @NotBlank(message = "操作类型不能为空")
    private String operation;

    private String reason;
    private Long operatorId;
}