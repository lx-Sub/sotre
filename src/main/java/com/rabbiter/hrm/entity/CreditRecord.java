package com.rabbiter.hrm.entity;

/**
 * @description:
 * @author: 李鑫
 * @date: 2026/2/24 11:31
 */
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CreditRecord {
    private Long id;
    private Long userId;
    private Integer changeScore; // 变动分数
    private Integer newScore; // 变动后分数
    private String reason; // 变动原因
    private Integer type; // 1-交易完成 2-评价良好 3-违规处罚 4-申诉成功
    private Long relatedId; // 关联ID（订单ID、举报ID等）
    private LocalDateTime createTime;
}
