package com.rabbiter.hrm.vo;

import lombok.Data;
import java.math.BigDecimal;

/**
 * 热门商品VO
 */
@Data
public class HotProductVO {
    private Long productId;
    private String productName;
    private String category;
    private BigDecimal price;
    private Long viewCount;           // 浏览次数
    private Long saleCount;            // 销量
    private Long favoriteCount;        // 收藏数
    private Double popularityScore;    // 热度评分
}