package com.rabbiter.hrm.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 信用规则实体类
 * @author 李鑫
 * @date 2026/2/24
 */
@Data
public class CreditRule {

    /**
     * 规则ID
     */
    private Long id;

    /**
     * 规则名称
     */
    private String name;

    /**
     * 规则描述
     */
    private String description;

    /**
     * 规则类型：1-加分 2-扣分
     */
    private Integer type;

    /**
     * 影响分数
     */
    private Integer score;

    /**
     * 图标
     */
    private String icon;

    /**
     * 排序
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
}