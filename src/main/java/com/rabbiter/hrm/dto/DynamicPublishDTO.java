package com.rabbiter.hrm.dto;

/**
 * @description:
 * @author: 李鑫
 * @date: 2026/2/24 17:52
 */

import lombok.Data;
import javax.validation.constraints.*;
import java.util.List;

/**
 * 品牌动态发布DTO
 */
@Data
public class DynamicPublishDTO {

    @NotBlank(message = "动态标题不能为空")
    @Size(max = 200, message = "标题最多200字")
    private String title;

    @NotBlank(message = "动态内容不能为空")
    private String content;

    private List<String> images;

    private String videoUrl;

    private Long productId; // 关联商品
}