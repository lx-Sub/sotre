package com.rabbiter.hrm.entity;

/**
 * @description:
 * @author: 李鑫
 * @date: 2026/2/24 11:30
 */
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Product {
    private Long id;
    private Long userId; // 卖家ID
    private Integer userType; // 1-商家 2-个人
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stock;
    private String category; // 分类：篮球鞋、足球鞋等
    private String brand;
    private String model;
    private String images; // JSON数组
    private Integer tradeType; // 1-仅出售 2-可交换
    private Integer status; // 0-待审核 1-上架 2-下架 3-违规下架
    private Integer viewCount;
    private Integer favoriteCount;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private LocalDateTime shelfTime;
}
