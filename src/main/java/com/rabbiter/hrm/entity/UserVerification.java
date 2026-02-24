package com.rabbiter.hrm.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 用户实名认证实体类
 */
@Data
public class UserVerification {

    /**
     * 认证ID
     */
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 身份证号
     */
    private String idNumber;

    /**
     * 身份证正面照片URL
     */
    private String idCardFront;

    /**
     * 身份证反面照片URL
     */
    private String idCardBack;

    /**
     * 手持身份证照片URL
     */
    private String handheldIdCard;

    /**
     * 装备权属凭证（JSON数组存储多个文件URL）
     */
    private String equipmentCredentials;

    /**
     * 认证状态：0-待审核 1-已通过 2-已驳回
     */
    private Integer status;

    /**
     * 提交时间
     */
    private LocalDateTime submitTime;

    /**
     * 审核时间
     */
    private LocalDateTime verifyTime;

    /**
     * 审核备注
     */
    private String verifyRemark;

    /**
     * 审核人ID
     */
    private Long verifierId;

    /**
     * 资质有效期
     */
    private LocalDateTime expiryDate;
}