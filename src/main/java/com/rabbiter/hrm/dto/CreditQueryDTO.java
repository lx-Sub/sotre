package com.rabbiter.hrm.dto;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * 信用分查询DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreditQueryDTO {

    /**
     * 页码，默认1
     */
    private Integer pageNum = 1;

    /**
     * 每页大小，默认10
     */
    private Integer pageSize = 10;

    /**
     * 关键词搜索（用户名、昵称）
     */
    private String keyword;

    /**
     * 最小信用分
     */
    private Integer minScore;

    /**
     * 最大信用分
     */
    private Integer maxScore;

    /**
     * 排序字段
     */
    private String orderBy;

    /**
     * 排序方式：asc-升序 desc-降序
     */
    private String orderType;

    /**
     * 信用等级（可选）
     */
    private String creditLevel;

    /**
     * 是否有违规记录
     */
    private Boolean hasViolation;

    /**
     * 获取排序语句
     */
    public String getOrderByClause() {
        if (orderBy == null || orderBy.isEmpty()) {
            return "credit_score DESC";
        }
        return orderBy + " " + (orderType != null ? orderType : "DESC");
    }
}