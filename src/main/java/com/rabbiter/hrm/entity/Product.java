package com.rabbiter.hrm.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 商品实体类
 * @author 李鑫
 * @date 2026/2/24
 */
@Data
public class Product {

    /**
     * 商品ID
     */
    private Long id;

    /**
     * 卖家ID
     */
    private Long userId;

    /**
     * 卖家类型：1-商家 2-个人
     */
    private Integer userType;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 商品描述
     */
    private String description;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 库存
     */
    private Integer stock;

    /**
     * 分类
     */
    private String category;

    /**
     * 品牌
     */
    private String brand;

    /**
     * 型号
     */
    private String model;

    /**
     * 成色：全新/99新/95新等
     */
    private String condition;

    /**
     * 图片URL数组（JSON）
     */
    private String images;

    /**
     * 交易类型：1-仅出售 2-可交换
     */
    private Integer tradeType;

    /**
     * 状态：0-待审核 1-上架 2-下架 3-违规下架
     */
    private Integer status;

    /**
     * 审核备注
     */
    private String auditRemark;

    /**
     * 审核时间
     */
    private LocalDateTime auditTime;

    /**
     * 浏览次数
     */
    private Integer viewCount;

    /**
     * 收藏次数
     */
    private Integer favoriteCount;

    /**
     * 销量
     */
    private Integer saleCount;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 上架时间
     */
    private LocalDateTime shelfTime;
}