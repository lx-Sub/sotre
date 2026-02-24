package com.rabbiter.hrm.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 公告实体类
 * @author 李鑫
 * @date 2026/2/24
 */
@Data
public class Announcement {

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
     * 是否置顶：0-否 1-是
     */
    private Boolean isTop;

    /**
     * 状态：0-草稿 1-已发布 2-已下架
     */
    private Integer status;

    /**
     * 发布时间
     */
    private LocalDateTime publishTime;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}