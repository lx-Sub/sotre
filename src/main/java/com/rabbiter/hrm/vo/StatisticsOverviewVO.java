package com.rabbiter.hrm.vo;

import lombok.Data;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 核心数据概览VO
 */
@Data
public class StatisticsOverviewVO {

    // 用户相关
    private Integer totalUsers;                // 总注册用户数
    private Integer todayNewUsers;              // 今日新增用户
    private Integer activeUsersToday;           // 今日活跃用户
    private Integer activeUsersWeek;            // 本周活跃用户
    private Integer activeUsersMonth;           // 本月活跃用户

    // 商家相关
    private Integer totalMerchants;             // 总商家数
    private Integer pendingMerchants;           // 待审核商家
    private Integer verifiedMerchants;          // 已认证商家

    // 商品相关
    private Integer totalProducts;              // 总商品数
    private Integer pendingProducts;            // 待审核商品
    private Integer onSaleProducts;             // 在售商品

    // 订单相关
    private Integer totalOrders;                 // 总订单量
    private Integer todayOrders;                  // 今日订单量
    private Integer pendingOrders;                // 待处理订单
    private Integer completedOrders;              // 已完成订单

    // 交易金额
    private BigDecimal totalAmount;            // 总交易额
    private BigDecimal todayAmount;            // 今日交易额
    private BigDecimal weekAmount;             // 本周交易额
    private BigDecimal monthAmount;            // 本月交易额

    // 社区相关
    private Integer totalPosts;                   // 总帖子数
    private Integer todayPosts;                    // 今日新帖
    private Integer pendingPosts;                  // 待审核帖子
    private Integer totalComments;                 // 总评论数

    // 待处理事项
    private Integer pendingReports;                // 待处理举报
    private Integer pendingAfterSales;             // 待处理售后
    private Integer pendingCreditAppeals;          // 待处理信用申诉

    // 环比数据（与上期对比）
    private Map<String, Double> growthRates;    // 增长率
}