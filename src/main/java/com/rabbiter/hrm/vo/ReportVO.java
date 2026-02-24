package com.rabbiter.hrm.vo;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 举报视图对象
 * @author 李鑫
 * @date 2026/2/24
 */
@Data
public class ReportVO {

    /**
     * 举报ID
     */
    private Long id;

    /**
     * 举报人ID
     */
    private Long reporterId;

    /**
     * 举报人姓名
     */
    private String reporterName;

    /**
     * 被举报人ID
     */
    private Long reportedUserId;

    /**
     * 被举报人姓名
     */
    private String reportedUserName;

    /**
     * 举报类型：1-帖子 2-评论 3-商品
     */
    private Integer targetType;

    /**
     * 举报类型名称
     */
    private String targetTypeName;

    /**
     * 目标ID
     */
    private Long targetId;

    /**
     * 目标标题/内容
     */
    private String targetTitle;

    /**
     * 举报原因
     */
    private String reason;

    /**
     * 详细描述
     */
    private String description;

    /**
     * 状态：0-待处理 1-已处理 2-驳回
     */
    private Integer status;

    /**
     * 状态名称
     */
    private String statusName;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 获取举报类型名称
     */
    public String getTargetTypeName() {
        if (targetType == null) return "未知";
        switch (targetType) {
            case 1: return "帖子";
            case 2: return "评论";
            case 3: return "商品";
            default: return "未知";
        }
    }

    /**
     * 获取状态名称
     */
    public String getStatusName() {
        if (status == null) return "未知";
        switch (status) {
            case 0: return "待处理";
            case 1: return "已处理";
            case 2: return "已驳回";
            default: return "未知";
        }
    }
}