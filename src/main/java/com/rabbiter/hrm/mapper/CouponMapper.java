package com.rabbiter.hrm.mapper;

import com.rabbiter.hrm.entity.Coupon;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 优惠券Mapper接口
 */
@Mapper
public interface CouponMapper {

    /**
     * 根据ID查询优惠券
     */
    Coupon selectById(@Param("id") Long id);

    /**
     * 根据店铺ID查询优惠券
     */
    List<Coupon> selectByShopId(@Param("shopId") Long shopId,
                                @Param("status") Integer status);

    /**
     * 查询进行中的优惠券
     */
    List<Coupon> selectActiveCoupons(@Param("shopId") Long shopId);

    /**
     * 插入优惠券
     */
    void insert(Coupon coupon);

    /**
     * 更新优惠券
     */
    void updateById(Coupon coupon);

    /**
     * 更新优惠券状态
     */
    void updateStatus(@Param("id") Long id, @Param("status") Integer status);

    /**
     * 减少剩余数量
     */
    int decreaseRemainingCount(@Param("id") Long id);

    /**
     * 删除优惠券
     */
    void deleteById(@Param("id") Long id);
}