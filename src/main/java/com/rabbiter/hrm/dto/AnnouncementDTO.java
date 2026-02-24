package com.rabbiter.hrm.dto;

import lombok.Data;
import javax.validation.constraints.*;
import java.time.LocalDateTime;

/**
 * 公告数据传输对象
 * @author 李鑫
 * @date 2026/2/24
 */
@Data
public class AnnouncementDTO {

    /**
     * 公告ID（新增时为空，修改时必填）
     */
    private Long id;

    /**
     * 公告标题
     */
    @NotBlank(message = "公告标题不能为空")
    @Size(max = 200, message = "标题最多200字")
    private String title;

    /**
     * 公告内容
     */
    @NotBlank(message = "公告内容不能为空")
    private String content;

    /**
     * 公告类型：1-系统公告 2-活动通知 3-平台规则
     */
    @NotNull(message = "公告类型不能为空")
    @Min(value = 1, message = "公告类型不正确")
    @Max(value = 3, message = "公告类型不正确")
    private Integer type = 1;

    /**
     * 是否置顶：false-否 true-是
     */
    private Boolean isTop = false;

    /**
     * 状态：0-草稿 1-已发布 2-已下架
     */
    private Integer status = 0;

    /**
     * 发布时间
     */
    private LocalDateTime publishTime;
}