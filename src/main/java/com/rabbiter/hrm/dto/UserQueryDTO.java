package com.rabbiter.hrm.dto;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserQueryDTO {
    private Integer pageNum = 1;
    private Integer pageSize = 10;
    private String keyword;
    private Integer role;
    private Integer status;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String orderBy;
    private String orderType;
}