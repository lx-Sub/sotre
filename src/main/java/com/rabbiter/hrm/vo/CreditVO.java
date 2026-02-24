package com.rabbiter.hrm.vo;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CreditVO {
    private Long userId;
    private String username;
    private String nickname;
    private Integer creditScore;
    private String creditLevel;
    private Integer totalViolations;
    private Integer totalAppeals;
    private LocalDateTime lastUpdateTime;
}