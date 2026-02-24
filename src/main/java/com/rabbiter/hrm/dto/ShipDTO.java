package com.rabbiter.hrm.dto;

/**
 * @description:
 * @author: 李鑫
 * @date: 2026/2/24 17:52
 */

import lombok.Data;
import javax.validation.constraints.*;

/**
 * 发货DTO
 */
@Data
public class ShipDTO {

    private Long orderId;

    @NotBlank(message = "物流单号不能为空")
    private String logisticsNo;

    @NotBlank(message = "物流公司不能为空")
    private String logisticsCompany;

    private String remark;
}