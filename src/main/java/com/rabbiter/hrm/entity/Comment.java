package com.rabbiter.hrm.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 评论实体类
 * @author 李鑫
 * @date 2026/2/24
 */
@Data
public class Comment {

    /**
     * 评论ID
     */
    private Long id;

    /**
     * 帖子ID
     */
    private Long postId;

    /**
     * 评论用户ID
     */
    private Long userId;

    /**
     * 父评论ID
     */
    private Long parentId;

    /**
     * 回复目标用户ID
     */
    private Long replyToUserId;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 状态：0-正常 1-屏蔽
     */
    private Integer status;

    /**
     * 点赞数
     */
    private Integer likeCount;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}