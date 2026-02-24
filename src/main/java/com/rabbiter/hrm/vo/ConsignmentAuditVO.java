package com.rabbiter.hrm.vo;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 个人寄售资质审核视图对象
 */
@Data
public class ConsignmentAuditVO {

    /**
     * 认证ID
     */
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 身份证号（脱敏显示）
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
     * 装备权属凭证（多个文件）
     */
    private String equipmentCredentials;

    /**
     * 认证状态：0-待审核 1-已通过 2-已驳回
     */
    private Integer status;

    /**
     * 状态名称
     */
    private String statusName;

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
     * 审核人姓名
     */
    private String verifierName;

    /**
     * 资质有效期
     */
    private LocalDateTime expiryDate;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 获取状态名称
     */
    public String getStatusName() {
        if (status == null) return "未知";
        switch (status) {
            case 0: return "待审核";
            case 1: return "已通过";
            case 2: return "已驳回";
            default: return "未知";
        }
    }

    /**
     * 获取脱敏的身份证号
     */
    public String getIdNumber() {
        if (idNumber != null && idNumber.length() > 10) {
            return idNumber.substring(0, 6) + "********" +
                    idNumber.substring(idNumber.length() - 4);
        }
        return idNumber;
    }
}