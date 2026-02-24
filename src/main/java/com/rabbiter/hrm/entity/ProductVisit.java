package com.rabbiter.hrm.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 商品访问记录实体类
 */
@Data
public class ProductVisit {

    private Long id;
    private Long productId;             // 商品ID
    private Long visitorId;              // 访客ID
    private LocalDateTime visitTime;     // 访问时间
}