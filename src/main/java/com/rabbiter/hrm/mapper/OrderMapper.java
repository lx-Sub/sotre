package com.rabbiter.hrm.mapper;

import com.rabbiter.hrm.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

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
}