package com.rabbiter.hrm.dto;

import lombok.Data;
import javax.validation.constraints.*;
import java.math.BigDecimal;

/**
 * 售后仲裁数据传输对象
 * @author 李鑫
 * @date 2026/2/24
 */
@Data
public class ArbitrationDTO {

    /**
     * 售后单ID
     */
    private Long afterSaleId;

    /**
     * 仲裁结果：1-支持买家 2-支持卖家 3-双方各担责
     */
    @NotNull(message = "仲裁结果不能为空")
    @Min(value = 1, message = "仲裁结果不正确")
    @Max(value = 3, message = "仲裁结果不正确")
    private Integer result;

    /**
     * 仲裁理由
     */
    @NotBlank(message = "仲裁理由不能为空")
    @Size(max = 500, message = "仲裁理由最多500字")
    private String reason;

    /**
     * 买家信用分变更
     */
    @Min(value = -100, message = "信用分变更范围-100到100")
    @Max(value = 100, message = "信用分变更范围-100到100")
    private Integer buyerCreditChange = 0;

    /**
     * 卖家信用分变更
     */
    @Min(value = -100, message = "信用分变更范围-100到100")
    @Max(value = 100, message = "信用分变更范围-100到100")
    private Integer sellerCreditChange = 0;

    /**
     * 退款金额（支持买家时）
     */
    @DecimalMin(value = "0.00", message = "退款金额不能为负数")
    private BigDecimal refundAmount;
}