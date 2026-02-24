package com.rabbiter.hrm.vo;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 关注VO
 * @author 李鑫
 * @date 2026/2/24
 */
@Data
public class FollowVO {

    /**
     * 关注ID
     */
    private Long id;

    /**
     * 目标用户ID
     */
    private Long targetUserId;

    /**
     * 目标用户名
     */
    private String targetUsername;

    /**
     * 目标昵称
     */
    private String targetNickname;

    /**
     * 目标头像
     */
    private String targetAvatar;

    /**
     * 目标角色：1-普通用户 2-商家
     */
    private Integer targetRole;

    /**
     * 目标角色名称
     */
    private String targetRoleName;

    /**
     * 目标信用等级
     */
    private String targetCreditLevel;

    /**
     * 是否互相关注
     */
    private Boolean isMutual;

    /**
     * 关注时间
     */
    private LocalDateTime createTime;

    public String getTargetRoleName() {
        return targetRole == 2 ? "商家" : "用户";
    }
}