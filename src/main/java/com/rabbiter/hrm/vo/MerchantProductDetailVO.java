package com.rabbiter.hrm.vo;

/**
 * @description:
 * @author: 李鑫
 * @date: 2026/2/24 17:54CouponVO
 */

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 商家商品详情VO
 */
@Data
public class MerchantProductDetailVO extends MerchantProductVO {

    private String description;
    private List<String> images;
    private String model;
    private String condition;
    private Integer tradeType; // 1-仅出售 2-可交换
    private String tradeTypeName;
    private List<ProductSpecVO> specs;
    private Integer favoriteCount;
    private LocalDateTime updateTime;

    public String getTradeTypeName() {
        return tradeType == 1 ? "仅出售" : "可交换";
    }
}

