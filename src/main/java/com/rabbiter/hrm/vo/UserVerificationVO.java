package com.rabbiter.hrm.vo;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UserVerificationVO {
    private Long userId;
    private String realName;
    private String idNumber;
    private String idCardFront;
    private String idCardBack;
    private String handheldIdCard;
    private Integer status;
    private LocalDateTime submitTime;
    private LocalDateTime verifyTime;
    private String verifyRemark;
}