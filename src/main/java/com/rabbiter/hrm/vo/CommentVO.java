package com.rabbiter.hrm.vo;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 评论视图对象
 * @author 李鑫
 * @date 2026/2/24
 */
@Data
public class CommentVO {

    /**
     * 评论ID
     */
    private Long id;

    /**
     * 帖子ID
     */
    private Long postId;

    /**
     * 帖子标题
     */
    private String postTitle;

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
     * 评论内容
     */
    private String content;

    /**
     * 点赞数
     */
    private Integer likeCount;

    /**
     * 状态：0-正常 1-屏蔽
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
        return status == 0 ? "正常" : "已屏蔽";
    }
}