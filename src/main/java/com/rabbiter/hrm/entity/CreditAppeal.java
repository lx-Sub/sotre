package com.rabbiter.hrm.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 信用申诉实体类
 * @author 李鑫
 * @date 2026/2/24
 */
@Data
public class CreditAppeal {

    /**
     * 申诉ID
     */
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 关联信用记录ID
     */
    private Long creditRecordId;

    /**
     * 申诉原因
     */
    private String reason;

    /**
     * 申诉材料（JSON数组）
     */
    private String evidence;

    /**
     * 状态：0-待处理 1-已通过 2-已驳回
     */
    private Integer status;

    /**
     * 处理反馈
     */
    private String feedback;

    /**
     * 处理人ID
     */
    private Long handlerId;

    /**
     * 处理时间
     */
    private LocalDateTime handleTime;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}