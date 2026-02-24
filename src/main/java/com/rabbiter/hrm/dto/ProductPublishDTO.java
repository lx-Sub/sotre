package com.rabbiter.hrm.dto;

import lombok.Data;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;

/**
 * 商品发布DTO
 */
@Data
public class ProductPublishDTO {

    @NotBlank(message = "商品名称不能为空")
    @Size(max = 200, message = "商品名称最多200字")
    private String name;

    @Size(max = 2000, message = "商品描述最多2000字")
    private String description;

    @NotNull(message = "价格不能为空")
    @DecimalMin(value = "0.01", message = "价格必须大于0")
    @DecimalMax(value = "999999.99", message = "价格不能超过999999.99")
    private BigDecimal price;

    @NotNull(message = "库存不能为空")
    @Min(value = 0, message = "库存不能小于0")
    private Integer stock;

    @NotBlank(message = "分类不能为空")
    private String category;

    private String brand;

    private String model;

    private String condition;

    @NotEmpty(message = "商品图片不能为空")
    private List<String> images;

    @NotNull(message = "交易类型不能为空")
    private Integer tradeType; // 1-仅出售 2-可交换

    private List<ProductSpecDTO> specs; // 商品规格
}