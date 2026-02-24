package com.rabbiter.hrm.dto;

import lombok.Data;
import javax.validation.constraints.*;

/**
 * 店铺信息更新DTO
 */
@Data
public class ShopUpdateDTO {

    @NotBlank(message = "店铺名称不能为空")
    @Size(max = 50, message = "店铺名称最多50字")
    private String shopName;

    @Size(max = 200, message = "店铺简介最多200字")
    private String shopDesc;

    private String shopLogo;

    private String shopBanner;

    @Size(max = 200, message = "店铺地址最多200字")
    private String shopAddress;

    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "联系电话格式不正确")
    private String contactPhone;

    @Email(message = "邮箱格式不正确")
    private String contactEmail;
}