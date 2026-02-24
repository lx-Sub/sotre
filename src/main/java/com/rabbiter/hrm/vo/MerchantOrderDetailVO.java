package com.rabbiter.hrm.vo;

/**
 * @description:
 * @author: 李鑫
 * @date: 2026/2/24 17:55
 */


import lombok.Data;

import java.time.LocalDateTime;

/**
 * 商家订单详情VO
 */
@Data
public class MerchantOrderDetailVO extends MerchantOrderVO {

    private String receiverName;
    private String receiverPhone;
    private String receiverAddress;
    private String buyerRemark;
    private String sellerRemark;
    private String logisticsNo;
    private String logisticsCompany;
    private LocalDateTime receiveTime;
    private LocalDateTime completeTime;
    private String closeReason;
}
