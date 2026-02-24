package com.rabbiter.hrm.mapper;

import com.rabbiter.hrm.entity.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;


@Mapper
public interface PermissionMapper {

    /**
     * 查询所有权限
     */
    List<Permission> selectAll();

    /**
     * 根据ID查询
     */
    Permission selectById(@Param("id") Long id);

    /**
     * 根据代码查询
     */
    Permission selectByCode(@Param("code") String code);

    /**
     * 根据管理员ID查询权限
     */
    List<Permission> selectByAdminId(@Param("adminId") Long adminId);

    /**
     * 插入权限
     */
    void insert(Permission permission);

    /**
     * 更新权限
     */
    void updateById(Permission permission);

    /**
     * 删除权限
     */
    void deleteById(@Param("id") Long id);

    /**
     * 批量删除
     */
    void deleteByIds(@Param("ids") List<Long> ids);
}