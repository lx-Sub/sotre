package com.rabbiter.hrm.mapper;

import com.rabbiter.hrm.entity.Review;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

/**
 * 评价Mapper接口
 */
@Mapper
public interface ReviewMapper {

    /**
     * 根据ID查询评价
     */
    Review selectById(@Param("id") Long id);

    /**
     * 根据订单ID查询评价
     */
    Review selectByOrderId(@Param("orderId") Long orderId);

    /**
     * 根据商家ID查询评价
     */
    List<Review> selectByMerchantId(@Param("merchantId") Long merchantId);

    /**
     * 根据商品ID查询评价
     */
    List<Review> selectByProductId(@Param("productId") Long productId);

    /**
     * 获取商家评分统计
     */
    Map<String, Object> getRatingStatsByMerchantId(@Param("merchantId") Long merchantId);

    /**
     * 获取分数分布
     */
    Map<Integer, Long> getScoreDistributionByMerchantId(@Param("merchantId") Long merchantId);

    /**
     * 插入评价
     */
    void insert(Review review);

    /**
     * 更新评价（回复）
     */
    void updateById(Review review);
}