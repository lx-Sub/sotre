package com.rabbiter.hrm.vo;

import lombok.Data;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 交易金额统计VO
 */
@Data
public class AmountStatisticsVO {

    // 汇总统计
    private BigDecimal totalAmount;                   // 总交易额
    private BigDecimal todayAmount;                    // 今日交易额
    private BigDecimal weekAmount;                     // 本周交易额
    private BigDecimal monthAmount;                    // 本月交易额
    private BigDecimal yearAmount;                     // 本年交易额

    // 同比环比
    private BigDecimal dayOverDay;                     // 日环比
    private BigDecimal weekOverWeek;                   // 周环比
    private BigDecimal monthOverMonth;                 // 月环比
    private BigDecimal yearOverYear;                   // 年同比

    // 支付方式分布
    private Map<String, BigDecimal> paymentDistribution; // 支付方式分布

    // 时间趋势
    private List<TimeSeriesData> trend;                 // 时间趋势

    @Data
    public static class TimeSeriesData {
        private String time;                            // 时间点
        private BigDecimal amount;                       // 交易额
        private Long orderCount;                         // 订单数
    }
}