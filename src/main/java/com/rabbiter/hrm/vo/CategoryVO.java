package com.rabbiter.hrm.vo;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 分类视图对象
 * @author 李鑫
 * @date 2026/2/24
 */
@Data
public class CategoryVO {

    /**
     * 分类ID
     */
    private Long id;

    /**
     * 分类名称
     */
    private String name;

    /**
     * 分类描述
     */
    private String description;

    /**
     * 排序值
     */
    private Integer sort;

    /**
     * 帖子数量
     */
    private Integer postCount;

    /**
     * 状态：0-禁用 1-启用
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
     * 获取状态名称
     */
    public String getStatusName() {
        return status == 1 ? "启用" : "禁用";
    }
}