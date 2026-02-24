package com.rabbiter.hrm.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 品牌动态实体类
 */
@Data
public class Dynamic {

    private Long id;
    private Long shopId;                // 店铺ID
    private Long merchantId;            // 商家ID
    private String title;               // 标题
    private String content;             // 内容
    private String images;              // 图片JSON
    private String videoUrl;            // 视频URL
    private Long productId;             // 关联商品ID
    private Integer viewCount;          // 浏览次数
    private Integer likeCount;          // 点赞次数
    private Integer commentCount;       // 评论次数
    private Integer status;             // 状态：0-草稿 1-已发布
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}