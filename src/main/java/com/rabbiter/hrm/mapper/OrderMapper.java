package com.rabbiter.hrm.mapper;

import com.rabbiter.hrm.dto.OrderQueryDTO;
import com.rabbiter.hrm.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Mapper
public interface OrderMapper {

    /**
     * 根据ID查询订单
     */
    Order selectById(@Param("id") Long id);

    /**
     * 根据订单号查询
     */
    Order selectByOrderNo(@Param("orderNo") String orderNo);

    /**
     * 查询用户订单列表
     */
    List<Order> selectByUserId(@Param("userId") Long userId);

    /**
     * 统计用户订单数量
     */
    int countByUserId(@Param("userId") Long userId);

    /**
     * 查询所有订单
     */
    List<Order> selectAll();

    /**
     * 插入订单
     */
    void insert(Order order);

    /**
     * 更新订单
     */
    void updateById(Order order);

    /**
     * 统计今日订单数
     */
    int countToday();

    /**
     * 统计今日交易额
     */
    Double sumTodayAmount();

    /**
     * 根据条件查询订单（用于管理员列表）
     * @param queryDTO 查询条件
     * @return 订单列表
     */
    List<Order> selectByCondition(OrderQueryDTO queryDTO);
    // ==================== 新增统计方法 ====================

    /**
     * 统计所有订单数
     */
    int countAll();

    /**
     * 按日期范围统计订单数
     */
    int countByDateRange(@Param("startDate") LocalDateTime startDate,
                         @Param("endDate") LocalDateTime endDate);

    /**
     * 按日期统计订单数
     */
    int countByDate(@Param("startDate") LocalDateTime startDate,
                    @Param("endDate") LocalDateTime endDate);

    /**
     * 按状态统计订单数
     */
    int countByStatus(@Param("status") Integer status);

    /**
     * 按状态和日期统计
     */
    int countByStatusAndDate(@Param("status") Integer status,
                             @Param("startDate") LocalDateTime startDate,
                             @Param("endDate") LocalDateTime endDate);

    /**
     * 按类型和日期统计
     */
    int countByTypeAndDate(@Param("orderType") Integer orderType,
                           @Param("startDate") LocalDateTime startDate,
                           @Param("endDate") LocalDateTime endDate);

    /**
     * 统计不同日期的活跃用户数
     */
    int countDistinctUsersByDate(@Param("startDate") LocalDateTime startDate,
                                 @Param("endDate") LocalDateTime endDate);

    /**
     * 按状态统计交易额
     */
    BigDecimal sumAmountByStatus(@Param("status") Integer status);

    /**
     * 按日期范围统计交易额
     */
    BigDecimal sumAmountByDateRange(@Param("startDate") LocalDateTime startDate,
                                    @Param("endDate") LocalDateTime endDate);

    /**
     * 按日期统计交易额
     */
    BigDecimal sumAmountByDate(@Param("startDate") LocalDateTime startDate,
                               @Param("endDate") LocalDateTime endDate);

    /**
     * 按支付方式和日期统计交易额
     */
    BigDecimal sumAmountByPaymentAndDate(@Param("paymentMethod") Integer paymentMethod,
                                         @Param("startDate") LocalDateTime startDate,
                                         @Param("endDate") LocalDateTime endDate);

    /**
     * 平均交易额
     */
    BigDecimal avgAmountByDateRange(@Param("startDate") LocalDateTime startDate,
                                    @Param("endDate") LocalDateTime endDate);

    /**
     * 最大交易额
     */
    BigDecimal maxAmountByDateRange(@Param("startDate") LocalDateTime startDate,
                                    @Param("endDate") LocalDateTime endDate);

    /**
     * 按日期范围统计订单趋势
     */
    List<Map<String, Object>> statisticsByDateRange(@Param("startDate") LocalDateTime startDate,
                                                    @Param("endDate") LocalDateTime endDate,
                                                    @Param("groupBy") String groupBy);

    /**
     * 按日期范围统计金额趋势
     */
    List<Map<String, Object>> amountStatisticsByDateRange(@Param("startDate") LocalDateTime startDate,
                                                          @Param("endDate") LocalDateTime endDate,
                                                          @Param("groupBy") String groupBy);

    /**
     * 根据商品分类统计订单数
     */
    int countByProductCategory(@Param("category") String category);
}