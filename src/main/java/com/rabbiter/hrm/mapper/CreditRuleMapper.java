package com.rabbiter.hrm.mapper;

import com.rabbiter.hrm.entity.CreditRule;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 信用规则Mapper接口
 * @author 李鑫
 * @date 2026/2/24
 */
@Mapper
public interface CreditRuleMapper {

    /**
     * 根据ID查询规则
     */
    CreditRule selectById(@Param("id") Long id);

    /**
     * 查询所有规则
     */
    List<CreditRule> selectAll();

    /**
     * 根据类型查询规则
     */
    List<CreditRule> selectByType(@Param("type") Integer type);

    /**
     * 插入规则
     */
    void insert(CreditRule rule);

    /**
     * 更新规则
     */
    void updateById(CreditRule rule);

    /**
     * 删除规则
     */
    void deleteById(@Param("id") Long id);
}