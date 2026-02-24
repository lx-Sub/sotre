package com.rabbiter.hrm.vo;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 公告视图对象
 * @author 李鑫
 * @date 2026/2/24
 */
@Data
public class AnnouncementVO {

    /**
     * 公告ID
     */
    private Long id;

    /**
     * 公告标题
     */
    private String title;

    /**
     * 公告内容
     */
    private String content;

    /**
     * 类型：1-系统公告 2-活动通知 3-平台规则
     */
    private Integer type;

    /**
     * 类型名称
     */
    private String typeName;

    /**
     * 是否置顶
     */
    private Boolean isTop;

    /**
     * 状态：0-草稿 1-已发布 2-已下架
     */
    private Integer status;

    /**
     * 状态名称
     */
    private String statusName;

    /**
     * 发布时间
     */
    private LocalDateTime publishTime;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 获取类型名称
     */
    public String getTypeName() {
        if (type == null) return "未知";
        switch (type) {
            case 1: return "系统公告";
            case 2: return "活动通知";
            case 3: return "平台规则";
            default: return "未知";
        }
    }

    /**
     * 获取状态名称
     */
    public String getStatusName() {
        if (status == null) return "未知";
        switch (status) {
            case 0: return "草稿";
            case 1: return "已发布";
            case 2: return "已下架";
            default: return "未知";
        }
    }
}