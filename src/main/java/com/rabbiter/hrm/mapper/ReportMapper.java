package com.rabbiter.hrm.mapper;

import com.rabbiter.hrm.entity.Report;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 举报Mapper接口
 * @author 李鑫
 * @date 2026/2/24
 */
@Mapper
public interface ReportMapper {

    /**
     * 根据ID查询举报
     */
    Report selectById(@Param("id") Long id);

    /**
     * 根据举报人ID查询
     */
    List<Report> selectByReporterId(@Param("reporterId") Long reporterId);

    /**
     * 根据被举报人ID查询
     */
    List<Report> selectByReportedUserId(@Param("reportedUserId") Long reportedUserId);

    /**
     * 根据目标查询举报
     */
    List<Report> selectByTarget(@Param("targetType") Integer targetType,
                                @Param("targetId") Long targetId);

    /**
     * 根据条件查询举报
     */
    List<Report> selectByCondition(@Param("status") Integer status,
                                   @Param("targetType") Integer targetType);

    /**
     * 查询待处理举报
     */
    List<Report> selectPending();

    /**
     * 查询所有举报
     */
    List<Report> selectAll();

    /**
     * 插入举报
     */
    void insert(Report report);

    /**
     * 更新举报
     */
    void updateById(Report report);

    /**
     * 更新举报状态
     */
    void updateStatus(@Param("id") Long id,
                      @Param("status") Integer status,
                      @Param("handlerId") Long handlerId,
                      @Param("handleResult") String handleResult);

    /**
     * 统计待处理举报数
     */
    int countPending();

    /**
     * 统计用户被举报次数
     */
    int countByReportedUser(@Param("userId") Long userId);

    /**
     * 删除举报
     */
    void deleteById(@Param("id") Long id);
}