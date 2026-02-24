package com.rabbiter.hrm.mapper;

import com.rabbiter.hrm.entity.UserWish;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 心愿单Mapper接口
 * @author 李鑫
 * @date 2026/2/24
 */
@Mapper
public interface WishMapper {

    /**
     * 根据ID查询心愿
     */
    UserWish selectById(@Param("id") Long id);

    /**
     * 根据用户ID查询心愿列表
     */
    List<UserWish> selectByUserId(@Param("userId") Long userId);

    /**
     * 插入心愿
     */
    void insert(UserWish wish);

    /**
     * 更新心愿
     */
    void updateById(UserWish wish);

    /**
     * 删除心愿
     */
    void deleteById(@Param("id") Long id);

    /**
     * 统计用户心愿数量
     */
    int countByUserId(@Param("userId") Long userId);
}