package com.rabbiter.hrm.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 用户收藏实体类
 * @author 李鑫
 * @date 2026/2/24
 */
@Data
public class UserFavorite {

    /**
     * 收藏ID
     */
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 收藏类型：1-商品 2-帖子 3-用户/商家
     */
    private Integer type;

    /**
     * 目标ID
     */
    private Long targetId;

    /**
     * 收藏时间
     */
    private LocalDateTime createTime;
}