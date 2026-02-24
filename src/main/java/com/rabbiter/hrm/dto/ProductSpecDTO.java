package com.rabbiter.hrm.dto;

import lombok.Data;
import javax.validation.constraints.*;
import java.math.BigDecimal;

/**
 * 商品规格DTO
 */
@Data
public class ProductSpecDTO {

    @NotBlank(message = "规格名称不能为空")
    private String specName;

    @NotBlank(message = "规格值不能为空")
    private String specValue;

    @NotNull(message = "库存不能为空")
    @Min(value = 0, message = "库存不能小于0")
    private Integer stock;

    private BigDecimal price; // 规格价格，为空则使用商品价格
}