package com.rabbiter.hrm.vo;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 帖子视图对象
 * @author 李鑫
 * @date 2026/2/24
 */
@Data
public class PostVO {

    /**
     * 帖子ID
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
     * 帖子标题
     */
    private String title;

    /**
     * 帖子内容（摘要）
     */
    private String summary;

    /**
     * 板块分类
     */
    private String category;

    /**
     * 浏览次数
     */
    private Integer viewCount;

    /**
     * 点赞次数
     */
    private Integer likeCount;

    /**
     * 评论次数
     */
    private Integer commentCount;

    /**
     * 是否加精
     */
    private Boolean isEssence;

    /**
     * 是否置顶
     */
    private Boolean isTop;

    /**
     * 状态：0-待审核 1-已发布 2-已屏蔽
     */
    private Integer status;

    /**
     * 状态名称
     */
    private String statusName;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 获取状态名称
     */
    public String getStatusName() {
        if (status == null) return "未知";
        switch (status) {
            case 0: return "待审核";
            case 1: return "已发布";
            case 2: return "已屏蔽";
            default: return "未知";
        }
    }
}