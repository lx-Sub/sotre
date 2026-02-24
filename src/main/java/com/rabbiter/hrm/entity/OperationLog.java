package com.rabbiter.hrm.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 操作日志实体类
 */
@Data
public class OperationLog {

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
     * 操作类型：freeze-冻结 unfreeze-解冻 delete-删除 audit-审核等
     */
    private String operation;

    /**
     * 操作目标ID
     */
    private Long targetId;

    /**
     * 操作目标类型：user-用户 merchant_apply-商家申请 order-订单等
     */
    private String targetType;

    /**
     * 操作详情（JSON格式存储详细数据）
     */
    private String details;

    /**
     * 操作结果：success-成功 fail-失败
     */
    private String result;

    /**
     * 操作原因/备注
     */
    private String reason;

    /**
     * 操作IP地址
     */
    private String ipAddress;

    /**
     * 操作用户代理
     */
    private String userAgent;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}