package com.rabbiter.hrm.mapper;


import com.rabbiter.hrm.entity.OperationLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface OperationLogMapper {

    /**
     * 插入操作日志
     */
    void insert(OperationLog operationLog);

    /**
     * 根据ID查询
     */
    OperationLog selectById(@Param("id") Long id);

    /**
     * 查询操作日志列表
     */
    List<OperationLog> selectList(@Param("operatorId") Long operatorId,
                                  @Param("targetType") String targetType,
                                  @Param("startTime") String startTime,
                                  @Param("endTime") String endTime);

    /**
     * 查询用户的操作日志
     */
    List<OperationLog> selectByUserId(@Param("userId") Long userId);

    /**
     * 查询目标对象的操作日志
     */
    List<OperationLog> selectByTarget(@Param("targetId") Long targetId,
                                      @Param("targetType") String targetType);
}