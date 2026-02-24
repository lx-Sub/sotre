package com.rabbiter.hrm.vo;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 操作日志视图对象
 * @author 李鑫
 * @date 2026/2/24
 */
@Data
public class OperationLogVO {

    /**
     * 日志ID
     */
    private Long id;

    /**
     * 操作人ID
     */
    private Long operatorId;

    /**
     * 操作人姓名
     */
    private String operatorName;

    /**
     * 操作类型
     */
    private String operation;

    /**
     * 操作类型名称
     */
    private String operationName;

    /**
     * 操作目标ID
     */
    private Long targetId;

    /**
     * 操作目标类型
     */
    private String targetType;

    /**
     * 操作详情
     */
    private String details;

    /**
     * 操作结果
     */
    private String result;

    /**
     * 操作原因
     */
    private String reason;

    /**
     * IP地址
     */
    private String ipAddress;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}