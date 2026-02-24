package com.rabbiter.hrm.vo;

/**
 * @description:
 * @author: 李鑫
 * @date: 2026/2/24 17:57
 */


import lombok.Data;
import java.math.BigDecimal;

/**
 * 商品排行VO
 */
@Data
public class ProductRankVO {

    private Long productId;
    private String productName;
    private String productImage;
    private BigDecimal price;
    private Integer rank;           // 排名
    private Long saleCount;          // 销量
    private BigDecimal saleAmount;    // 销售额
    private Long viewCount;           // 浏览量
    private Double conversionRate;    // 转化率
}