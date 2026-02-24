package com.rabbiter.hrm.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 商家申请实体类
 */
@Data
public class MerchantApply {

    /**
     * 申请ID
     */
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 公司名称
     */
    private String companyName;

    /**
     * 营业执照号
     */
    private String businessLicense;

    /**
     * 营业执照URL
     */
    private String businessLicenseUrl;

    /**
     * 品牌名称
     */
    private String brand;

    /**
     * 品牌授权书URL
     */
    private String brandAuthorizationUrl;

    /**
     * 联系人姓名
     */
    private String contactName;

    /**
     * 联系电话
     */
    private String contactPhone;

    /**
     * 联系邮箱
     */
    private String contactEmail;

    /**
     * 申请状态：0-待审核 1-已通过 2-已驳回
     */
    private Integer status;

    /**
     * 申请时间
     */
    private LocalDateTime applyTime;

    /**
     * 审核时间
     */
    private LocalDateTime auditTime;

    /**
     * 审核备注
     */
    private String auditRemark;

    /**
     * 审核人ID
     */
    private Long auditorId;
}