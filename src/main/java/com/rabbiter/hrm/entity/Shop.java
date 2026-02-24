package com.rabbiter.hrm.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 店铺实体类
 */
@Data
public class Shop {

    private Long id;
    private Long merchantId;           // 商家ID
    private String shopName;            // 店铺名称
    private String shopLogo;            // 店铺Logo
    private String shopBanner;          // 店铺横幅
    private String shopDesc;            // 店铺简介
    private String shopAddress;         // 店铺地址
    private String contactPhone;        // 联系电话
    private String contactEmail;        // 联系邮箱
    private String businessLicense;     // 营业执照
    private String brandAuthorization;   // 品牌授权书
    private Integer auditStatus;        // 审核状态：0-待审核 1-已通过 2-已驳回
    private String auditRemark;         // 审核备注
    private LocalDateTime auditTime;    // 审核时间
    private Integer status;             // 店铺状态：0-关闭 1-营业中
    private BigDecimal score;           // 店铺评分
    private Integer saleCount;          // 总销量
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}