package com.rabbiter.hrm.mapper;

import com.rabbiter.hrm.entity.Shop;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 店铺Mapper接口
 */
@Mapper
public interface ShopMapper {

    /**
     * 根据ID查询店铺
     */
    Shop selectById(@Param("id") Long id);

    /**
     * 根据商家ID查询店铺
     */
    Shop selectByMerchantId(@Param("merchantId") Long merchantId);

    /**
     * 插入店铺
     */
    void insert(Shop shop);

    /**
     * 更新店铺
     */
    void updateById(Shop shop);

    /**
     * 更新店铺审核状态
     */
    void updateAuditStatus(@Param("id") Long id,
                           @Param("auditStatus") Integer auditStatus,
                           @Param("auditRemark") String auditRemark,
                           @Param("auditTime") java.time.LocalDateTime auditTime);

    /**
     * 更新店铺状态
     */
    void updateStatus(@Param("id") Long id, @Param("status") Integer status);
}