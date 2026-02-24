package com.rabbiter.hrm.entity;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 用户装备实体类
 * @author 李鑫
 * @date 2026/2/24
 */
@Data
public class UserEquipment {

    /**
     * 装备ID
     */
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

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
     * 使用情况：全新/轻微/明显/磨损
     */
    private String usageCondition;

    /**
     * 描述
     */
    private String description;

    /**
     * 图片URL数组（JSON格式）
     */
    private String images;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}