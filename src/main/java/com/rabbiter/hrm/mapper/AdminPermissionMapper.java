package com.rabbiter.hrm.mapper;

import com.rabbiter.hrm.entity.AdminPermission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface AdminPermissionMapper {

    /**
     * 插入管理员权限关联
     */
    void insert(AdminPermission adminPermission);

    /**
     * 根据管理员ID删除关联
     */
    void deleteByAdminId(@Param("adminId") Long adminId);

    /**
     * 根据管理员ID查询权限代码列表
     */
    List<String> selectPermissionsByAdminId(@Param("adminId") Long adminId);

    /**
     * 根据权限ID删除关联
     */
    void deleteByPermissionId(@Param("permissionId") Long permissionId);
}