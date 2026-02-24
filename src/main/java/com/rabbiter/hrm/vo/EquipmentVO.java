package com.rabbiter.hrm.vo;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 装备VO
 * @author 李鑫
 * @date 2026/2/24
 */
@Data
public class EquipmentVO {

    /**
     * 装备ID
     */
    private Long id;

    /**
     * 装备名称
     */
    private String name;

    /**
     * 品牌
     */
    private String brand;

    /**
     * 型号
     */
    private String model;

    /**
     * 分类
     */
    private String category;

    /**
     * 购买日期
     */
    private LocalDate purchaseDate;

    /**
     * 使用情况
     */
    private String usageCondition;

    /**
     * 使用情况名称
     */
    private String usageConditionName;

    /**
     * 封面图片
     */
    private String coverImage;

    /**
     * 描述（摘要）
     */
    private String description;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    public String getUsageConditionName() {
        if (usageCondition == null) return "未知";
        switch (usageCondition) {
            case "全新": return "全新";
            case "轻微": return "轻微使用痕迹";
            case "明显": return "明显使用痕迹";
            case "磨损": return "严重磨损";
            default: return usageCondition;
        }
    }
}