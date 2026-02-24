package com.rabbiter.hrm.vo;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 信用历史视图对象
 */
@Data
public class CreditHistoryVO {

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
     * 变更类型：violation-违规 appeal-申诉 manual-手动调整
     */
    private String type;

    /**
     * 操作人ID
     */
    private Long operatorId;

    /**
     * 操作人姓名
     */
    private String operatorName;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}