package com.rabbiter.hrm.vo;

import lombok.Data;
import java.util.List;
import java.util.Map;

/**
 * 用户统计数据VO
 */
@Data
public class UserStatisticsVO {

    // 总量统计
    private Integer totalUsers;                    // 总用户数
    private Integer activeUsers;                    // 活跃用户数
    private Integer frozenUsers;                    // 冻结用户数
    private Integer deletedUsers;                    // 注销用户数

    // 角色分布
    private Map<String, Integer> roleDistribution;   // 角色分布

    // 时间趋势
    private List<TimeSeriesData> trend;           // 时间趋势数据

    // 地域分布（如果有点击数据）
    private Map<String, Integer> regionDistribution; // 地域分布

    @Data
    public static class TimeSeriesData {
        private String time;                       // 时间点
        private Integer newUsers;                      // 新增用户
        private Integer activeUsers;                    // 活跃用户
    }
}