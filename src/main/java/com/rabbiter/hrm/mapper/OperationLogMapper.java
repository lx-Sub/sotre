package com.rabbiter.hrm.mapper;

import com.rabbiter.hrm.entity.OperationLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 操作日志Mapper接口
 * @author 李鑫
 * @date 2026/2/24
 */
@Mapper
public interface OperationLogMapper {

    /**
     * 根据ID查询日志
     */
    OperationLog selectById(@Param("id") Long id);

    /**
     * 根据操作人ID查询
     */
    List<OperationLog> selectByOperatorId(@Param("operatorId") Long operatorId);

    /**
     * 根据目标查询日志
     */
    List<OperationLog> selectByTarget(@Param("targetId") Long targetId,
                                      @Param("targetType") String targetType);

    /**
     * 根据条件查询操作日志
     * @param operatorId 操作人ID
     * @param operation 操作类型
     * @param targetType 目标类型
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return 操作日志列表
     */
    List<OperationLog> selectByCondition(@Param("operatorId") Long operatorId,
                                         @Param("operation") String operation,
                                         @Param("targetType") String targetType,
                                         @Param("startDate") LocalDateTime startDate,
                                         @Param("endDate") LocalDateTime endDate);

    /**
     * 查询操作类型统计
     */
    List<Map<String, Object>> statisticsByOperation(@Param("startDate") LocalDateTime startDate,
                                                    @Param("endDate") LocalDateTime endDate);

    /**
     * 查询操作人统计
     */
    List<Map<String, Object>> statisticsByOperator(@Param("startDate") LocalDateTime startDate,
                                                   @Param("endDate") LocalDateTime endDate);

    /**
     * 查询每日操作量
     */
    List<Map<String, Object>> statisticsByDay(@Param("startDate") LocalDateTime startDate,
                                              @Param("endDate") LocalDateTime endDate);

    /**
     * 插入日志
     */
    void insert(OperationLog operationLog);

    /**
     * 批量插入日志
     */
    void batchInsert(List<OperationLog> logs);

    /**
     * 删除旧日志
     */
    int deleteOldLogs(@Param("beforeDate") LocalDateTime beforeDate);

    /**
     * 统计日志数量
     */
    int countByCondition(@Param("operatorId") Long operatorId,
                         @Param("operation") String operation,
                         @Param("targetType") String targetType,
                         @Param("startDate") LocalDateTime startDate,
                         @Param("endDate") LocalDateTime endDate);
}