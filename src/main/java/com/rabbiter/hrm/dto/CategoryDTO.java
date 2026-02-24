package com.rabbiter.hrm.dto;

import lombok.Data;
import javax.validation.constraints.*;

/**
 * 分类数据传输对象
 * @author 李鑫
 * @date 2026/2/24
 */
@Data
public class CategoryDTO {

    /**
     * 分类ID（新增时为空，修改时必填）
     */
    private Long id;

    /**
     * 分类名称
     */
    @NotBlank(message = "分类名称不能为空")
    @Size(max = 50, message = "分类名称最多50字")
    private String name;

    /**
     * 分类描述
     */
    @Size(max = 200, message = "描述最多200字")
    private String description;

    /**
     * 排序值（越小越靠前）
     */
    private Integer sort = 0;

    /**
     * 状态：0-禁用 1-启用
     */
    private Integer status = 1;
}