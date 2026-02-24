package com.rabbiter.hrm.vo;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class MerchantAuditVO {
    private Long id;
    private Long userId;
    private String username;
    private String companyName;
    private String businessLicense;
    private String brand;
    private String brandAuthorization;
    private Integer status;
    private LocalDateTime applyTime;
    private String contactName;
    private String contactPhone;
}