package com.rabbiter.hrm.mapper;

import com.rabbiter.hrm.entity.AfterSale;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.time.LocalDateTime;

/**
 * 售后Mapper接口
 * @author 李鑫
 * @date 2026/2/24
 */
@Mapper
public interface AfterSaleMapper {

    /**
     * 根据ID查询售后
     */
    AfterSale selectById(@Param("id") Long id);

    /**
     * 根据售后单号查询
     */
    AfterSale selectByAfterSaleNo(@Param("afterSaleNo") String afterSaleNo);

    /**
     * 根据订单ID查询售后
     */
    AfterSale selectByOrderId(@Param("orderId") Long orderId);

    /**
     * 根据交换订单ID查询售后
     */
    AfterSale selectByExchangeOrderId(@Param("exchangeOrderId") Long exchangeOrderId);

    /**
     * 根据用户ID查询售后
     */
    List<AfterSale> selectByUserId(@Param("userId") Long userId);

    /**
     * 根据状态查询售后
     */
    List<AfterSale> selectByStatus(@Param("status") Integer status);

    /**
     * 根据条件查询售后
     */
    List<AfterSale> selectByCondition(@Param("status") Integer status,
                                      @Param("type") Integer type,
                                      @Param("userId") Long userId,
                                      @Param("startDate") LocalDateTime startDate,
                                      @Param("endDate") LocalDateTime endDate);

    /**
     * 查询待处理售后
     */
    List<AfterSale> selectPending();

    /**
     * 查询所有售后
     */
    List<AfterSale> selectAll();

    /**
     * 插入售后
     */
    void insert(AfterSale afterSale);

    /**
     * 更新售后
     */
    void updateById(AfterSale afterSale);

    /**
     * 更新售后状态
     */
    void updateStatus(@Param("id") Long id,
                      @Param("status") Integer status,
                      @Param("arbitrationResult") String arbitrationResult);

    /**
     * 更新卖家响应
     */
    void updateSellerResponse(@Param("id") Long id, @Param("sellerResponse") String sellerResponse);

    /**
     * 完成售后
     */
    void completeAfterSale(@Param("id") Long id, @Param("completeTime") LocalDateTime completeTime);

    /**
     * 统计待处理售后数
     */
    int countPending();

    /**
     * 统计用户售后数
     */
    int countByUserId(@Param("userId") Long userId);

    /**
     * 删除售后
     */
    void deleteById(@Param("id") Long id);
}