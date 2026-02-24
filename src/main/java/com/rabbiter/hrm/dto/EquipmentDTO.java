package com.rabbiter.hrm.dto;

import lombok.Data;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

/**
 * 装备DTO
 * @author 李鑫
 * @date 2026/2/24
 */
@Data
public class EquipmentDTO {

    /**
     * 装备ID（更新时必填）
     */
    private Long id;

    /**
     * 装备名称
     */
    @NotBlank(message = "装备名称不能为空")
    @Size(max = 100, message = "装备名称不能超过100字")
    private String name;

    /**
     * 品牌
     */
    @Size(max = 50, message = "品牌不能超过50字")
    private String brand;

    /**
     * 型号
     */
    @Size(max = 100, message = "型号不能超过100字")
    private String model;

    /**
     * 分类
     */
    @NotBlank(message = "分类不能为空")
    private String category;

    /**
     * 购买日期
     */
    private LocalDate purchaseDate;

    /**
     * 使用情况：全新/轻微/明显/磨损
     */
    @NotBlank(message = "使用情况不能为空")
    private String usageCondition;

    /**
     * 描述
     */
    @Size(max = 500, message = "描述不能超过500字")
    private String description;

    /**
     * 图片URL数组
     */
    private List<String> images;
}