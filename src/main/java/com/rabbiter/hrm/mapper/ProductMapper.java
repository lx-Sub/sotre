package com.rabbiter.hrm.mapper;

import com.rabbiter.hrm.entity.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.math.BigDecimal;
import java.util.Map;

/**
 * 商品Mapper接口
 * @author 李鑫
 * @date 2026/2/24
 */
@Mapper
public interface ProductMapper {

    /**
     * 根据条件统计商品数量
     */
    int countByCondition(@Param("keyword") String keyword,
                         @Param("category") String category,
                         @Param("status") Integer status,
                         @Param("userId") Long userId);
    /**
     * 根据ID查询商品
     */
    Product selectById(@Param("id") Long id);

    /**
     * 根据用户ID查询商品
     */
    List<Product> selectByUserId(@Param("userId") Long userId);

    /**
     * 根据状态查询商品
     */
    List<Product> selectByStatus(@Param("status") Integer status);

    /**
     * 根据条件查询商品
     */
    List<Product> selectByCondition(@Param("keyword") String keyword,
                                    @Param("category") String category,
                                    @Param("status") Integer status,
                                    @Param("userId") Long userId,
                                    @Param("minPrice") BigDecimal minPrice,
                                    @Param("maxPrice") BigDecimal maxPrice,
                                    @Param("tradeType") Integer tradeType);

    /**
     * 查询待审核商品
     */
    List<Product> selectPending();

    /**
     * 查询上架商品
     */
    List<Product> selectOnSale();

    /**
     * 插入商品
     */
    void insert(Product product);

    /**
     * 更新商品
     */
    void updateById(Product product);

    /**
     * 更新商品状态
     */
    void updateStatus(@Param("id") Long id,
                      @Param("status") Integer status,
                      @Param("auditRemark") String auditRemark);

    /**
     * 更新商品库存
     */
    void updateStock(@Param("id") Long id, @Param("stock") Integer stock);

    /**
     * 增加浏览次数
     */
    void incrementViewCount(@Param("id") Long id);

    /**
     * 增加收藏次数
     */
    void incrementFavoriteCount(@Param("id") Long id);

    /**
     * 增加销量
     */
    void incrementSaleCount(@Param("id") Long id, @Param("quantity") Integer quantity);

    /**
     * 统计用户商品数
     */
    int countByUserId(@Param("userId") Long userId);

    /**
     * 统计待审核商品数
     */
    int countPending();

    /**
     * 删除商品
     */
    void deleteById(@Param("id") Long id);

    /**
     * 统计所有商品数
     */
    int countAll();

    /**
     * 根据状态统计商品数
     */
    int countByStatus(@Param("status") Integer status);

    /**
     * 根据卖家类型统计
     */
    int countByUserType(@Param("userType") Integer userType);

    /**
     * 根据价格区间统计
     */
    int countByPriceRange(@Param("min") BigDecimal min,
                          @Param("max") BigDecimal max);

    /**
     * 统计售罄商品数
     */
    int countSoldOut();

    /**
     * 根据分类统计商品数
     */
    int countByCategory(@Param("category") String category);

    /**
     * 按分类统计商品分布
     */
    List<Map<String, Object>> statisticsByCategory();

    /**
     * 查询热门商品
     */
    List<Product> selectHotProducts(@Param("limit") Integer limit);
}