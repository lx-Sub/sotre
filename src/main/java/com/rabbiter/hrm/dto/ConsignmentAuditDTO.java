package com.rabbiter.hrm.dto;

import lombok.Data;
import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Data
public class ConsignmentAuditDTO {
    @NotNull(message = "用户ID不能为空")
    private Long userId;

    @NotNull(message = "审核结果不能为空")
    private Integer approve;

    private String reason;

    private LocalDateTime expiryDate;
}