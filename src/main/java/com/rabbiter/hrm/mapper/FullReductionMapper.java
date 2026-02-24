package com.rabbiter.hrm.mapper;

import com.rabbiter.hrm.entity.FullReduction;
import com.rabbiter.hrm.entity.FullReductionRule;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 满减活动Mapper接口
 */
@Mapper
public interface FullReductionMapper {

    /**
     * 根据ID查询活动
     */
    FullReduction selectById(@Param("id") Long id);

    /**
     * 根据店铺ID查询活动
     */
    List<FullReduction> selectByShopId(@Param("shopId") Long shopId,
                                       @Param("status") Integer status);

    /**
     * 查询进行中的活动
     */
    List<FullReduction> selectActiveReductions(@Param("shopId") Long shopId);

    /**
     * 插入活动
     */
    void insert(FullReduction fullReduction);

    /**
     * 更新活动
     */
    void updateById(FullReduction fullReduction);

    /**
     * 更新活动状态
     */
    void updateStatus(@Param("id") Long id, @Param("status") Integer status);

    /**
     * 删除活动
     */
    void deleteById(@Param("id") Long id);

    // ==================== 活动规则 ====================

    /**
     * 插入规则
     */
    void insertRule(FullReductionRule rule);

    /**
     * 根据活动ID查询规则
     */
    List<FullReductionRule> selectRulesByReductionId(@Param("reductionId") Long reductionId);

    /**
     * 根据活动ID删除规则
     */
    void deleteRulesByReductionId(@Param("reductionId") Long reductionId);
}