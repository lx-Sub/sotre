package com.rabbiter.hrm.vo;

import lombok.Data;
import java.math.BigDecimal;
import java.util.Map;

/**
 * 店铺评分VO
 */
@Data
public class ShopRatingVO {

    /**
     * 综合评分
     */
    private BigDecimal overallRating;

    /**
     * 描述相符评分
     */
    private BigDecimal matchRating;

    /**
     * 沟通态度评分
     */
    private BigDecimal communicationRating;

    /**
     * 发货速度评分
     */
    private BigDecimal shippingRating;

    /**
     * 总评价数
     */
    private Long totalReviews;

    /**
     * 好评数（评分 >= 4）
     */
    private Long goodReviews;

    /**
     * 中评数（评分 = 3）
     */
    private Long mediumReviews;

    /**
     * 差评数（评分 <= 2）
     */
    private Long badReviews;

    /**
     * 分数分布（key: 分数, value: 数量）
     * 例如：{5: 100, 4: 200, 3: 50, 2: 10, 1: 5}
     */
    private Map<Integer, Long> scoreDistribution;

    /**
     * 好评率
     */
    private Double goodRate;

    /**
     * 中评率
     */
    private Double mediumRate;

    /**
     * 差评率
     */
    private Double badRate;

    /**
     * 计算各项评分占比
     */
    public void calculateRates() {
        if (totalReviews != null && totalReviews > 0) {
            this.goodRate = goodReviews != null ?
                    (double) goodReviews / totalReviews * 100 : 0.0;
            this.mediumRate = mediumReviews != null ?
                    (double) mediumReviews / totalReviews * 100 : 0.0;
            this.badRate = badReviews != null ?
                    (double) badReviews / totalReviews * 100 : 0.0;
        } else {
            this.goodRate = 0.0;
            this.mediumRate = 0.0;
            this.badRate = 0.0;
        }
    }
}