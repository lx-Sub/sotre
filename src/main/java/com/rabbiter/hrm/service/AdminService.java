package com.rabbiter.hrm.service;

import com.rabbiter.hrm.dto.*;
import com.rabbiter.hrm.vo.*;
import com.github.pagehelper.PageInfo;
import java.util.List;

public interface AdminService {

    /**
     * 获取管理员列表
     */
    PageInfo<AdminVO> getAdminList(Integer pageNum, Integer pageSize);

    /**
     * 分配管理员权限
     */
    void assignPermissions(Long adminId, List<String> permissions);

    /**
     * 获取所有权限列表
     */
    List<PermissionVO> getAllPermissions();

    /**
     * 创建子管理员
     */
    void createSubAdmin(CreateAdminDTO createDTO);

    /**
     * 获取管理员详情
     */
    AdminVO getAdminDetail(Long adminId);

    /**
     * 更新管理员状态
     */
    void updateAdminStatus(Long adminId, Integer status);

    /**
     * 删除管理员
     */
    void deleteAdmin(Long adminId);
}