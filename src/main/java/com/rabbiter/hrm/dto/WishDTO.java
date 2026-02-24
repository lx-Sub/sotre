package com.rabbiter.hrm.dto;

import lombok.Data;
import javax.validation.constraints.*;

/**
 * 心愿单DTO
 * @author 李鑫
 * @date 2026/2/24
 */
@Data
public class WishDTO {

    /**
     * 期望装备名称
     */
    @NotBlank(message = "装备名称不能为空")
    @Size(max = 100, message = "装备名称不能超过100字")
    private String productName;

    /**
     * 期望品牌
     */
    @Size(max = 50, message = "品牌不能超过50字")
    private String brand;

    /**
     * 期望分类
     */
    private String category;

    /**
     * 备注
     */
    @Size(max = 200, message = "备注不能超过200字")
    private String remark;
}