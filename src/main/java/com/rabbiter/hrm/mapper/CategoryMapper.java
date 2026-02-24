package com.rabbiter.hrm.mapper;

import com.rabbiter.hrm.entity.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 分类Mapper接口
 * @author 李鑫
 * @date 2026/2/24
 */
@Mapper
public interface CategoryMapper {

    /**
     * 根据ID查询分类
     */
    Category selectById(@Param("id") Long id);

    /**
     * 根据名称查询分类
     */
    Category selectByName(@Param("name") String name);

    /**
     * 查询所有分类
     */
    List<Category> selectAll();

    /**
     * 查询启用的分类
     */
    List<Category> selectEnabled();

    /**
     * 插入分类
     */
    void insert(Category category);

    /**
     * 更新分类
     */
    void updateById(Category category);

    /**
     * 更新分类状态
     */
    void updateStatus(@Param("id") Long id, @Param("status") Integer status);

    /**
     * 删除分类
     */
    void deleteById(@Param("id") Long id);

    /**
     * 统计分类下的帖子数
     */
    int countPosts(@Param("id") Long id);

    /**
     * 检查分类名称是否存在
     */
    int checkNameExists(@Param("name") String name, @Param("excludeId") Long excludeId);
}