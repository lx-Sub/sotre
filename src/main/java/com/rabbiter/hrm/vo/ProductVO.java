package com.rabbiter.hrm.vo;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 商品视图对象
 * @author 李鑫
 * @date 2026/2/24
 */
@Data
public class ProductVO {

    /**
     * 商品ID
     */
    private Long id;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 卖家ID
     */
    private Long userId;

    /**
     * 卖家用户名
     */
    private String username;

    /**
     * 卖家类型：1-商家 2-个人
     */
    private Integer userType;

    /**
     * 卖家类型名称
     */
    private String userTypeName;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 分类
     */
    private String category;

    /**
     * 品牌
     */
    private String brand;

    /**
     * 库存
     */
    private Integer stock;

    /**
     * 状态：0-待审核 1-上架 2-下架 3-违规下架
     */
    private Integer status;

    /**
     * 状态名称
     */
    private String statusName;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 获取卖家类型名称
     */
    public String getUserTypeName() {
        return userType == 1 ? "商家" : "个人";
    }

    /**
     * 获取状态名称
     */
    public String getStatusName() {
        if (status == null) return "未知";
        switch (status) {
            case 0: return "待审核";
            case 1: return "上架";
            case 2: return "下架";
            case 3: return "违规下架";
            default: return "未知";
        }
    }
}