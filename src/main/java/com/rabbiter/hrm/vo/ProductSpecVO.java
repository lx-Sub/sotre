package com.rabbiter.hrm.vo;

/**
 * @description:
 * @author: 李鑫
 * @date: 2026/2/24 18:17
 */

import lombok.Data;
import java.math.BigDecimal;

/**
 * 商品规格VO
 */
@Data
public class ProductSpecVO {

    /**
     * 规格ID
     */
    private Long id;

    /**
     * 商品ID
     */
    private Long productId;

    /**
     * 规格名称（如：颜色、尺寸）
     */
    private String specName;

    /**
     * 规格值（如：红色、XL码）
     */
    private String specValue;

    /**
     * 库存
     */
    private Integer stock;

    /**
     * 规格价格（如果为空，则使用商品价格）
     */
    private BigDecimal price;

    /**
     * 是否选中（用于前端展示）
     */
    private Boolean selected;

    /**
     * 显示文本（规格名称 + 规格值）
     */
    public String getDisplayName() {
        return specName + ": " + specValue;
    }
}