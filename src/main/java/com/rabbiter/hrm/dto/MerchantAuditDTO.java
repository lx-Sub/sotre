package com.rabbiter.hrm.dto;

import lombok.Data;
import javax.validation.constraints.*;
import java.util.List;

@Data
public class MerchantAuditDTO {
    @NotNull(message = "用户ID不能为空")
    private Long userId;

    @NotNull(message = "审核结果不能为空")
    @Min(value = 0, message = "审核结果不正确")
    @Max(value = 2, message = "审核结果不正确")
    private Integer approve;

    private String reason;

    @Size(max = 10, message = "最多上传10个资质文件")
    private List<Long> credentialIds;
}