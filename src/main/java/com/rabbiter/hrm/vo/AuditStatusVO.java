package com.rabbiter.hrm.vo;

/**
 * @description:
 * @author: 李鑫
 * @date: 2026/2/24 17:53
 */

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 审核状态VO
 */
@Data
public class AuditStatusVO {

    private Integer status; // 0-待审核 1-已通过 2-已驳回
    private String statusName;
    private String auditRemark;
    private LocalDateTime auditTime;
    private String auditorName;

    public String getStatusName() {
        if (status == null) return "未知";
        switch (status) {
            case 0: return "待审核";
            case 1: return "审核通过";
            case 2: return "已驳回";
            default: return "未知";
        }
    }
}
