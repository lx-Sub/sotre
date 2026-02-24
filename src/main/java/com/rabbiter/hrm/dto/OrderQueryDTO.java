package com.rabbiter.hrm.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 订单查询数据传输对象
 * @author 李鑫
 * @date 2026/2/24
 */
@Data
@Builder
public class OrderQueryDTO {

    /**
     * 页码，默认1
     */
    private Integer pageNum = 1;

    /**
     * 每页大小，默认10
     */
    private Integer pageSize = 10;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 订单类型：1-销售 2-交换
     */
    private Integer orderType;

    /**
     * 订单状态
     */
    private Integer status;

    /**
     * 买家名称
     */
    private String buyerName;

    /**
     * 卖家名称
     */
    private String sellerName;

    /**
     * 开始时间
     */
    private LocalDateTime startDate;

    /**
     * 结束时间
     */
    private LocalDateTime endDate;
}