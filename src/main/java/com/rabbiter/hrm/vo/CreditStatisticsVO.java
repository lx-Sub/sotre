package com.rabbiter.hrm.vo;

import lombok.Data;
import java.util.List;
import java.util.Map;

/**
 * 信用分统计VO
 */
@Data
public class CreditStatisticsVO {

    // 信用分分布
    private Map<String, Integer> creditDistribution;     // 信用分分布（优秀/良好/一般/较差）

    // 统计指标
    private Double avgCreditScore;                     // 平均信用分
    private Integer maxCreditScore;                    // 最高信用分
    private Integer minCreditScore;                    // 最低信用分
    private Integer totalViolations;                       // 总违规次数

    // 信用变更统计
    private Map<String, Integer> changeTypeDistribution;  // 变更类型分布

    // 信用分TopN
    private List<UserCreditVO> topCreditUsers;         // 信用分最高的用户
    private List<UserCreditVO> bottomCreditUsers;      // 信用分最低的用户
}