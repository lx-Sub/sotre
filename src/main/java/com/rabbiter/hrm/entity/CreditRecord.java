package com.rabbiter.hrm.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 信用记录实体类
 * @author 李鑫
 * @date 2026/2/24
 */
@Data
public class CreditRecord {

    /**
     * 记录ID
     */
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 变更分数
     */
    private Integer changeScore;

    /**
     * 变更前分数
     */
    private Integer beforeScore;

    /**
     * 变更后分数
     */
    private Integer afterScore;

    /**
     * 变更原因
     */
    private String reason;

    /**
     * 类型：violation-违规 appeal-申诉 manual-手动调整 arbitration-仲裁
     */
    private String type;

    /**
     * 关联业务ID（订单ID、申诉ID等）
     */
    private Long businessId;

    /**
     * 操作人ID
     */
    private Long operatorId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}