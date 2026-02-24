package com.rabbiter.hrm.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 举报实体类
 * @author 李鑫
 * @date 2026/2/24
 */
@Data
public class Report {

    /**
     * 举报ID
     */
    private Long id;

    /**
     * 举报人ID
     */
    private Long reporterId;

    /**
     * 被举报人ID
     */
    private Long reportedUserId;

    /**
     * 举报类型：1-帖子 2-评论 3-商品
     */
    private Integer targetType;

    /**
     * 目标ID
     */
    private Long targetId;

    /**
     * 举报原因
     */
    private String reason;

    /**
     * 详细描述
     */
    private String description;

    /**
     * 证据图片（JSON数组）
     */
    private String evidenceImages;

    /**
     * 处理状态：0-待处理 1-已处理 2-驳回
     */
    private Integer status;

    /**
     * 处理结果
     */
    private String handleResult;

    /**
     * 处理时间
     */
    private LocalDateTime handleTime;

    /**
     * 处理人ID
     */
    private Long handlerId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}