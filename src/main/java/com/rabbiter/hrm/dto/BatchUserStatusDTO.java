package com.rabbiter.hrm.dto;

import lombok.Data;
import javax.validation.constraints.*;
import java.util.List;

@Data
public class BatchUserStatusDTO {
    @NotEmpty(message = "用户ID列表不能为空")
    private List<Long> userIds;

    @NotNull(message = "状态不能为空")
    private Integer status;

    private String reason;
}