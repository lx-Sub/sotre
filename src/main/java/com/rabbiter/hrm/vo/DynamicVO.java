package com.rabbiter.hrm.vo;

/**
 * @description:
 * @author: 李鑫
 * @date: 2026/2/24 17:56
 */


import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 品牌动态VO
 */
@Data
public class DynamicVO {

    private Long id;
    private String title;
    private String content;
    private List<String> images;
    private String videoUrl;
    private Long productId;
    private String productName;
    private Integer viewCount;
    private Integer likeCount;
    private Integer commentCount;
    private LocalDateTime createTime;
}
