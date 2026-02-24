package com.rabbiter.hrm.vo;

import lombok.Data;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 订单统计数据VO
 */
@Data
public class OrderStatisticsVO {

    // 总量统计
    private Integer totalOrders;                      // 总订单数
    private Integer completedOrders;                   // 已完成订单
    private Integer cancelledOrders;                    // 已取消订单
    private Integer refundingOrders;                    // 退款中订单

    // 金额统计
    private BigDecimal totalAmount;                  // 总金额
    private BigDecimal avgAmount;                     // 平均金额
    private BigDecimal maxAmount;                     // 最大金额

    // 订单类型分布
    private Map<String, Integer> typeDistribution;      // 销售/交换订单分布

    // 状态分布
    private Map<String, Integer> statusDistribution;    // 订单状态分布

    // 时间趋势
    private List<TimeSeriesData> trend;              // 时间趋势数据

    @Data
    public static class TimeSeriesData {
        private String time;                          // 时间点
        private Integer orderCount;                      // 订单数
        private BigDecimal amount;                     // 交易额
    }
}