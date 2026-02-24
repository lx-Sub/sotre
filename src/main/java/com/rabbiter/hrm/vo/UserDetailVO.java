package com.rabbiter.hrm.vo;

import lombok.Data;
import java.util.List;

@Data
public class UserDetailVO extends UserVO {
    private String realName;
    private String idNumber;
    private String address;
    private Integer orderCount;
    private Integer exchangeCount;
    private Integer postCount;
    private Integer commentCount;
    private List<CreditHistoryVO> recentCreditRecords;
    private List<OrderBriefVO> recentOrders;
}