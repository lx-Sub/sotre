package com.rabbiter.hrm.dto;

import lombok.Data;
import javax.validation.constraints.*;

/**
 * 举报处理数据传输对象
 * @author 李鑫
 * @date 2026/2/24
 */
@Data
public class ReportHandleDTO {

    /**
     * 举报ID
     */
    private Long reportId;

    /**
     * 处理结果：1-确认违规 2-驳回举报
     */
    @NotNull(message = "处理结果不能为空")
    @Min(value = 1, message = "处理结果不正确")
    @Max(value = 2, message = "处理结果不正确")
    private Integer result;

    /**
     * 处理反馈
     */
    @Size(max = 500, message = "反馈最多500字")
    private String feedback;

    /**
     * 是否警告用户
     */
    private Boolean warnUser = false;

    /**
     * 是否禁言用户
     */
    private Boolean muteUser = false;

    /**
     * 禁言天数
     */
    @Min(value = 1, message = "禁言天数最小为1")
    @Max(value = 365, message = "禁言天数最大为365")
    private Integer muteDays;

    /**
     * 是否封号
     */
    private Boolean banUser = false;

    /**
     * 扣除信用分
     */
    @Min(value = 0, message = "扣除信用分不能为负数")
    @Max(value = 100, message = "单次扣除最多100分")
    private Integer deductCredit = 0;
}