package com.rabbiter.hrm.vo;

import lombok.Data;
import java.util.List;

/**
 * 装备详情VO
 * @author 李鑫
 * @date 2026/2/24
 */
@Data
public class EquipmentDetailVO extends EquipmentVO {

    /**
     * 详细描述
     */
    private String description;

    /**
     * 图片列表
     */
    private List<String> images;

    /**
     * 关联的商品ID（如果已发布）
     */
    private Long productId;

    /**
     * 是否已发布
     */
    private Boolean isPublished;
}