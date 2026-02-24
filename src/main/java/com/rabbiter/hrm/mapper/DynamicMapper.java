package com.rabbiter.hrm.mapper;

import com.rabbiter.hrm.entity.Dynamic;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 品牌动态Mapper接口
 */
@Mapper
public interface DynamicMapper {

    /**
     * 根据ID查询动态
     */
    Dynamic selectById(@Param("id") Long id);

    /**
     * 根据商家ID查询动态
     */
    List<Dynamic> selectByMerchantId(@Param("merchantId") Long merchantId);

    /**
     * 根据店铺ID查询动态
     */
    List<Dynamic> selectByShopId(@Param("shopId") Long shopId,
                                 @Param("pageNum") Integer pageNum,
                                 @Param("pageSize") Integer pageSize);

    /**
     * 插入动态
     */
    void insert(Dynamic dynamic);

    /**
     * 更新动态
     */
    void updateById(Dynamic dynamic);

    /**
     * 增加浏览次数
     */
    void incrementViewCount(@Param("id") Long id);

    /**
     * 增加点赞次数
     */
    void incrementLikeCount(@Param("id") Long id);

    /**
     * 删除动态
     */
    void deleteById(@Param("id") Long id);
}