package com.rabbiter.hrm.vo;

/**
 * @description:
 * @author: 李鑫
 * @date: 2026/2/24 17:53
 */


import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 商家商品列表VO
 */
@Data
public class MerchantProductVO {

    private Long id;
    private String name;
    private BigDecimal price;
    private Integer stock;
    private String category;
    private String brand;
    private String coverImage;
    private Integer status; // 0-待审核 1-上架 2-下架 3-违规下架
    private String statusName;
    private Integer viewCount;
    private Integer saleCount;
    private LocalDateTime createTime;
    private LocalDateTime shelfTime;
    private String auditRemark; // 审核备注

    public String getStatusName() {
        if (status == null) return "未知";
        switch (status) {
            case 0: return "待审核";
            case 1: return "已上架";
            case 2: return "已下架";
            case 3: return "违规下架";
            default: return "未知";
        }
    }
}