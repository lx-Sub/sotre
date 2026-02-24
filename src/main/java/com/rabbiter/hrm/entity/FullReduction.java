package com.rabbiter.hrm.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 满减活动实体类
 */
@Data
public class FullReduction {

    private Long id;
    private Long shopId;                // 店铺ID
    private String name;                // 活动名称
    private LocalDateTime startTime;    // 开始时间
    private LocalDateTime endTime;      // 结束时间
    private Integer status;             // 状态：0-未开始 1-进行中 2-已结束
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}