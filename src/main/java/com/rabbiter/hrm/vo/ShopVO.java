package com.rabbiter.hrm.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 店铺信息VO
 */
@Data
public class ShopVO {

    private Long id;
    private Long merchantId;
    private String shopName;
    private String shopLogo;
    private String shopBanner;
    private String shopDesc;
    private String shopAddress;
    private String contactPhone;
    private String contactEmail;
    private Integer auditStatus; // 0-待审核 1-已通过 2-已驳回
    private String auditRemark;
    private LocalDateTime auditTime;
    private Integer status; // 0-关闭 1-营业中
    private BigDecimal score; // 店铺评分
    private Integer saleCount; // 总销量
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    // 扩展字段
    private String auditStatusName;
    private String statusName;

    public String getAuditStatusName() {
        if (auditStatus == null) return "未知";
        switch (auditStatus) {
            case 0: return "待审核";
            case 1: return "已通过";
            case 2: return "已驳回";
            default: return "未知";
        }
    }

    public String getStatusName() {
        return status == 1 ? "营业中" : "已关闭";
    }
}