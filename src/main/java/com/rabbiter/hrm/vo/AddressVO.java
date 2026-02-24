package com.rabbiter.hrm.vo;

import lombok.Data;

/**
 * 收货地址VO
 * @author 李鑫
 * @date 2026/2/24
 */
@Data
public class AddressVO {

    /**
     * 地址ID
     */
    private Long id;

    /**
     * 收货人姓名
     */
    private String receiverName;

    /**
     * 收货人电话
     */
    private String receiverPhone;

    /**
     * 省份
     */
    private String province;

    /**
     * 城市
     */
    private String city;

    /**
     * 区/县
     */
    private String district;

    /**
     * 详细地址
     */
    private String detailAddress;

    /**
     * 完整地址
     */
    private String fullAddress;

    /**
     * 是否默认地址：0-否 1-是
     */
    private Integer isDefault;

    /**
     * 是否默认显示文本
     */
    private String isDefaultText;

    public String getFullAddress() {
        return province + city + district + detailAddress;
    }

    public String getIsDefaultText() {
        return isDefault == 1 ? "默认地址" : "";
    }
}