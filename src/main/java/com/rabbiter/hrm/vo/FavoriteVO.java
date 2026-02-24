package com.rabbiter.hrm.vo;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 收藏VO
 * @author 李鑫
 * @date 2026/2/24
 */
@Data
public class FavoriteVO {

    /**
     * 收藏ID
     */
    private Long id;

    /**
     * 收藏类型：1-商品 2-帖子 3-用户/商家
     */
    private Integer type;

    /**
     * 类型名称
     */
    private String typeName;

    /**
     * 目标ID
     */
    private Long targetId;

    /**
     * 目标标题/名称
     */
    private String targetTitle;

    /**
     * 目标图片
     */
    private String targetImage;

    /**
     * 目标描述/摘要
     */
    private String targetDesc;

    /**
     * 目标信息
     */
    private Object targetInfo;

    /**
     * 收藏时间
     */
    private LocalDateTime createTime;

    public String getTypeName() {
        if (type == null) return "未知";
        switch (type) {
            case 1: return "商品";
            case 2: return "帖子";
            case 3: return "用户";
            default: return "未知";
        }
    }
}