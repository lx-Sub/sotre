package com.rabbiter.hrm.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 用户心愿单实体类
 * @author 李鑫
 * @date 2026/2/24
 */
@Data
public class UserWish {

    /**
     * 心愿ID
     */
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 期望装备名称
     */
    private String productName;

    /**
     * 期望品牌
     */
    private String brand;

    /**
     * 期望分类
     */
    private String category;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}