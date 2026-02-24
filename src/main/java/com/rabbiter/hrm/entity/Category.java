package com.rabbiter.hrm.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 分类实体类
 * @author 李鑫
 * @date 2026/2/24
 */
@Data
public class Category {

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
     * 排序值（越小越靠前）
     */
    private Integer sort;

    /**
     * 状态：0-禁用 1-启用
     */
    private Integer status;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}