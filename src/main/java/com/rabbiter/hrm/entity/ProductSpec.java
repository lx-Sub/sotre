package com.rabbiter.hrm.entity;

import lombok.Data;
import java.math.BigDecimal;

/**
 * 商品规格实体类
 */
@Data
public class ProductSpec {

    private Long id;
    private Long productId;             // 商品ID
    private String specName;            // 规格名称
    private String specValue;           // 规格值
    private Integer stock;              // 库存
    private BigDecimal price;           // 规格价格
}