package com.rabbiter.hrm.vo;

/**
 * @description:
 * @author: 李鑫
 * @date: 2026/2/24 17:56
 */

import lombok.Data;
import java.math.BigDecimal;
import java.util.Map;

/**
 * 经营数据概览VO
 */
@Data
public class BusinessOverviewVO {

    // 今日数据
    private Integer todayOrders;          // 今日订单数
    private BigDecimal todayAmount;     // 今日销售额
    private Integer todayVisitors;         // 今日访客数

    // 昨日数据
    private Integer yesterdayOrders;
    private BigDecimal yesterdayAmount;
    private Integer yesterdayVisitors;

    // 本周数据
    private Integer weekOrders;
    private BigDecimal weekAmount;
    private Integer weekVisitors;

    // 本月数据
    private Integer monthOrders;
    private BigDecimal monthAmount;
    private Integer monthVisitors;

    // 总计数据
    private Integer totalOrders;
    private BigDecimal totalAmount;
    private Integer totalProducts;

    // 待处理事项
    private Integer pendingOrders;         // 待发货订单
    private Integer pendingAfterSales;      // 待处理售后
    private Integer pendingConsults;        // 待回复咨询

    // 环比增长率
    private Map<String, Double> growthRates;
}