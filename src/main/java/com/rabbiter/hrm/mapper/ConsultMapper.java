package com.rabbiter.hrm.mapper;

import com.rabbiter.hrm.entity.Consult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 咨询Mapper接口
 */
@Mapper
public interface ConsultMapper {

    /**
     * 根据ID查询咨询
     */
    Consult selectById(@Param("id") Long id);

    /**
     * 根据商品ID查询咨询
     */
    List<Consult> selectByProductId(@Param("productId") Long productId);

    /**
     * 根据商家ID查询咨询
     */
    List<Consult> selectByMerchantId(@Param("merchantId") Long merchantId,
                                     @Param("status") Integer status);

    /**
     * 查询待回复咨询
     */
    List<Consult> selectPendingByMerchantId(@Param("merchantId") Long merchantId);

    /**
     * 统计待回复咨询数
     */
    int countPendingByMerchantId(@Param("merchantId") Long merchantId);

    /**
     * 插入咨询
     */
    void insert(Consult consult);

    /**
     * 更新咨询（回复）
     */
    void updateById(Consult consult);
}