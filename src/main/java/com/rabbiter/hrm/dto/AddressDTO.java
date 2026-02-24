package com.rabbiter.hrm.dto;

import lombok.Data;
import javax.validation.constraints.*;

/**
 * 收货地址DTO
 * @author 李鑫
 * @date 2026/2/24
 */
@Data
public class AddressDTO {

    /**
     * 地址ID（更新时必填）
     */
    private Long id;

    /**
     * 收货人姓名
     */
    @NotBlank(message = "收货人姓名不能为空")
    @Size(max = 50, message = "收货人姓名不能超过50字")
    private String receiverName;

    /**
     * 收货人电话
     */
    @NotBlank(message = "收货人电话不能为空")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String receiverPhone;

    /**
     * 省份
     */
    @NotBlank(message = "省份不能为空")
    private String province;

    /**
     * 城市
     */
    @NotBlank(message = "城市不能为空")
    private String city;

    /**
     * 区/县
     */
    @NotBlank(message = "区/县不能为空")
    private String district;

    /**
     * 详细地址
     */
    @NotBlank(message = "详细地址不能为空")
    @Size(max = 200, message = "详细地址不能超过200字")
    private String detailAddress;

    /**
     * 是否默认地址：0-否 1-是
     */
    private Integer isDefault = 0;
}