package com.rabbiter.hrm.mapper;

import com.rabbiter.hrm.entity.UserEquipment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 装备库Mapper接口
 * @author 李鑫
 * @date 2026/2/24
 */
@Mapper
public interface EquipmentMapper {

    /**
     * 根据ID查询装备
     */
    UserEquipment selectById(@Param("id") Long id);

    /**
     * 根据用户ID查询装备列表
     */
    List<UserEquipment> selectByUserId(@Param("userId") Long userId,
                                       @Param("keyword") String keyword,
                                       @Param("category") String category);

    /**
     * 插入装备
     */
    void insert(UserEquipment equipment);

    /**
     * 更新装备
     */
    void updateById(UserEquipment equipment);

    /**
     * 删除装备
     */
    void deleteById(@Param("id") Long id);

    /**
     * 统计用户装备数量
     */
    int countByUserId(@Param("userId") Long userId);
}