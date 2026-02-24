package com.rabbiter.hrm.vo;

/**
 * @description:
 * @author: 李鑫
 * @date: 2026/2/24 17:56
 */


import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 商家售后详情VO
 */
@Data
public class MerchantAfterSaleDetailVO extends MerchantAfterSaleVO {

    private String description;
    private List<String> evidenceImages;
    private String sellerResponse;
    private String arbitrationResult;
    private LocalDateTime arbitrationTime;
    private LocalDateTime completeTime;
}
