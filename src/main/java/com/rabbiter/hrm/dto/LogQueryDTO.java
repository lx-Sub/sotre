package com.rabbiter.hrm.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 日志查询数据传输对象
 * @author 李鑫
 * @date 2026/2/24
 */
@Data
@Builder
public class LogQueryDTO {

    /**
     * 页码，默认1
     */
    private Integer pageNum = 1;

    /**
     * 每页大小，默认10
     */
    private Integer pageSize = 10;

    /**
     * 操作人ID
     */
    private Long operatorId;

    /**
     * 操作类型
     */
    private String operation;

    /**
     * 操作目标类型
     */
    private String targetType;

    /**
     * 开始时间
     */
    private LocalDateTime startDate;

    /**
     * 结束时间
     */
    private LocalDateTime endDate;
}