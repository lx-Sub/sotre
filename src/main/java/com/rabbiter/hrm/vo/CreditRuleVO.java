package com.rabbiter.hrm.vo;

import lombok.Data;

/**
 * 信用规则VO
 * @author 李鑫
 * @date 2026/2/24
 */
@Data
public class CreditRuleVO {

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
     * 类型名称
     */
    private String typeName;

    /**
     * 影响分数
     */
    private Integer score;

    /**
     * 图标
     */
    private String icon;

    public String getTypeName() {
        return type == 1 ? "加分项" : "扣分项";
    }
}