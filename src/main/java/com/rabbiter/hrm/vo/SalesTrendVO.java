package com.rabbiter.hrm.vo;

/**
 * @description:
 * @author: 李鑫
 * @date: 2026/2/24 17:57
 */

import lombok.Data;
import java.math.BigDecimal;

/**
 * 销售趋势VO
 */
@Data
public class SalesTrendVO {

    private String date;           // 日期
    private Long orderCount;        // 订单数
    private BigDecimal amount;      // 销售额
    private Long visitorCount;      // 访客数
    private Integer conversionRate; // 转化率
}