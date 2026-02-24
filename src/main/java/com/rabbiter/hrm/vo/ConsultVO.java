package com.rabbiter.hrm.vo;

/**
 * @description:
 * @author: 李鑫
 * @date: 2026/2/24 17:56
 */


import lombok.Data;
import java.time.LocalDateTime;

/**
 * 用户咨询VO
 */
@Data
public class ConsultVO {

    private Long id;
    private Long userId;
    private String userName;
    private String userAvatar;
    private Long productId;
    private String productName;
    private String content;
    private Integer status;
    private String statusName;
    private String replyContent;
    private LocalDateTime replyTime;
    private LocalDateTime createTime;

    public String getStatusName() {
        return status == 1 ? "已回复" : "待回复";
    }
}