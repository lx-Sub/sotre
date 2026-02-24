package com.rabbiter.hrm.vo;

import lombok.Data;
import java.util.List;

/**
 * 信用信息VO
 * @author 李鑫
 * @date 2026/2/24
 */
@Data
public class CreditInfoVO {

    /**
     * 当前信用分
     */
    private Integer currentScore;

    /**
     * 信用等级
     */
    private String creditLevel;

    /**
     * 信用徽章
     */
    private String creditBadge;

    /**
     * 下一等级所需分数
     */
    private Integer nextLevelScore;

    /**
     * 信用等级进度百分比
     */
    private Integer progressPercent;

    /**
     * 总加分
     */
    private Integer totalAdded;

    /**
     * 总扣分
     */
    private Integer totalDeducted;

    /**
     * 信用规则列表
     */
    private List<CreditRuleVO> rules;
}