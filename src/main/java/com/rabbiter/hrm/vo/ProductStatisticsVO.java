package com.rabbiter.hrm.vo;

import lombok.Data;
import java.util.List;
import java.util.Map;

/**
 * 商品统计数据VO
 */
@Data
public class ProductStatisticsVO {

    // 总量统计
    private Integer totalProducts;                      // 总商品数
    private Integer onSaleProducts;                      // 在售商品
    private Integer pendingProducts;                     // 待审核商品
    private Integer soldOutProducts;                     // 售罄商品

    // 分类分布
    private Map<String, Integer> categoryDistribution;   // 分类分布

    // 价格区间分布
    private Map<String, Integer> priceRangeDistribution; // 价格区间分布

    // 商家/个人分布
    private Map<String, Integer> sellerTypeDistribution; // 卖家类型分布

    // 热门商品
    private List<HotProductVO> hotProducts;           // 热门商品TopN
}