package com.rabbiter.hrm.dto;

import lombok.Data;
import javax.validation.constraints.*;
import java.util.List;

/**
 * 商家入驻申请DTO
 */
@Data
public class ShopApplyDTO {

    @NotBlank(message = "公司名称不能为空")
    @Size(max = 100, message = "公司名称最多100字")
    private String companyName;

    @NotBlank(message = "营业执照号不能为空")
    @Size(max = 50, message = "营业执照号最多50字")
    private String businessLicense;

    @NotBlank(message = "营业执照图片不能为空")
    private String businessLicenseUrl;

    @Size(max = 100, message = "品牌名称最多100字")
    private String brand;

    private String brandAuthorizationUrl;

    @NotBlank(message = "联系人姓名不能为空")
    @Size(max = 50, message = "联系人姓名最多50字")
    private String contactName;

    @NotBlank(message = "联系电话不能为空")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String contactPhone;

    @Email(message = "邮箱格式不正确")
    private String contactEmail;

    @NotBlank(message = "店铺名称不能为空")
    @Size(max = 50, message = "店铺名称最多50字")
    private String shopName;

    @Size(max = 200, message = "店铺简介最多200字")
    private String shopDesc;

    private String shopLogo;

    private String shopBanner;

    @Size(max = 200, message = "店铺地址最多200字")
    private String shopAddress;
}