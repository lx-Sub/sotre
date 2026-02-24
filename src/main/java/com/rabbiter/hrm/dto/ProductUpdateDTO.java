package com.rabbiter.hrm.dto;

import lombok.Data;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;

/**
 * 商品更新DTO
 */
@Data
public class ProductUpdateDTO {

    private Long id;

    @NotBlank(message = "商品名称不能为空")
    @Size(max = 200, message = "商品名称最多200字")
    private String name;

    @Size(max = 2000, message = "商品描述最多2000字")
    private String description;

    @NotNull(message = "价格不能为空")
    @DecimalMin(value = "0.01", message = "价格必须大于0")
    private BigDecimal price;

    @NotNull(message = "库存不能为空")
    @Min(value = 0, message = "库存不能小于0")
    private Integer stock;

    @NotBlank(message = "分类不能为空")
    private String category;

    private String brand;

    private String model;

    private String condition;

    private List<String> images;

    private Integer tradeType;

    private List<ProductSpecDTO> specs;
}