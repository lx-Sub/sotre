package com.rabbiter.hrm.vo;

import lombok.Data;

/**
 * 分类统计VO
 */
@Data
public class CategoryStatVO {
    private String categoryName;      // 分类名称
    private Integer postCount;           // 帖子数
    private Integer productCount;        // 商品数
    private Integer orderCount;          // 订单数
    private Double percentage;         // 占比
}