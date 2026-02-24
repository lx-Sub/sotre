package com.rabbiter.hrm.mapper;

import com.rabbiter.hrm.entity.ProductSpec;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 商品规格Mapper接口
 */
@Mapper
public interface ProductSpecMapper {

    /**
     * 根据ID查询规格
     */
    ProductSpec selectById(@Param("id") Long id);

    /**
     * 根据商品ID查询规格
     */
    List<ProductSpec> selectByProductId(@Param("productId") Long productId);

    /**
     * 插入规格
     */
    void insert(ProductSpec productSpec);

    /**
     * 批量插入规格
     */
    void batchInsert(List<ProductSpec> specs);

    /**
     * 更新规格
     */
    void updateById(ProductSpec productSpec);

    /**
     * 根据商品ID删除规格
     */
    void deleteByProductId(@Param("productId") Long productId);

    /**
     * 根据ID删除规格
     */
    void deleteById(@Param("id") Long id);
}