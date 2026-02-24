package com.rabbiter.hrm.mapper;

import com.rabbiter.hrm.entity.UserAddress;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 收货地址Mapper接口
 * @author 李鑫
 * @date 2026/2/24
 */
@Mapper
public interface AddressMapper {

    /**
     * 根据ID查询地址
     */
    UserAddress selectById(@Param("id") Long id);

    /**
     * 根据用户ID查询地址列表
     */
    List<UserAddress> selectByUserId(@Param("userId") Long userId);

    /**
     * 查询用户的默认地址
     */
    UserAddress selectDefaultByUserId(@Param("userId") Long userId);

    /**
     * 插入地址
     */
    void insert(UserAddress address);

    /**
     * 更新地址
     */
    void updateById(UserAddress address);

    /**
     * 删除地址
     */
    void deleteById(@Param("id") Long id);

    /**
     * 清除用户的默认地址
     */
    void clearDefaultAddress(@Param("userId") Long userId);

    /**
     * 统计用户地址数量
     */
    int countByUserId(@Param("userId") Long userId);
}