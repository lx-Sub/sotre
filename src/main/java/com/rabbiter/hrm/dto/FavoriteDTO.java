package com.rabbiter.hrm.dto;

import lombok.Data;
import javax.validation.constraints.*;

/**
 * 收藏DTO
 * @author 李鑫
 * @date 2026/2/24
 */
@Data
public class FavoriteDTO {

    /**
     * 收藏类型：1-商品 2-帖子 3-用户/商家
     */
    @NotNull(message = "收藏类型不能为空")
    @Min(value = 1, message = "收藏类型不正确")
    @Max(value = 3, message = "收藏类型不正确")
    private Integer type;

    /**
     * 目标ID
     */
    @NotNull(message = "目标ID不能为空")
    private Long targetId;
}