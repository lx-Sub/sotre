package com.rabbiter.hrm.service.Impl;

/**
 * @description:
 * @author: 李鑫
 * @date: 2026/2/24 14:37
 */

import com.rabbiter.hrm.dto.CreateAdminDTO;
import com.rabbiter.hrm.entity.Admin;
import com.rabbiter.hrm.entity.AdminPermission;
import com.rabbiter.hrm.entity.Permission;
import com.rabbiter.hrm.exception.BusinessException;
import com.rabbiter.hrm.mapper.AdminMapper;
import com.rabbiter.hrm.mapper.AdminPermissionMapper;
import com.rabbiter.hrm.mapper.PermissionMapper;
import com.rabbiter.hrm.service.AdminService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rabbiter.hrm.util.SecurityUtils;
import com.rabbiter.hrm.vo.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private AdminPermissionMapper adminPermissionMapper;


    @Override
    public PageInfo<AdminVO> getAdminList(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Admin> admins = adminMapper.selectAll();

        List<AdminVO> adminVOS = admins.stream().map(admin -> {
            AdminVO vo = new AdminVO();
            BeanUtils.copyProperties(admin, vo);

            // 获取管理员的权限列表
            List<String> permissions = adminPermissionMapper.selectPermissionsByAdminId(admin.getId());
            vo.setPermissions(permissions);

            return vo;
        }).collect(Collectors.toList());

        return new PageInfo<>(adminVOS);
    }

    @Override
    @Transactional
    public void assignPermissions(Long adminId, List<String> permissions) {
        // 先删除原有权限
        adminPermissionMapper.deleteByAdminId(adminId);

        // 添加新权限
        for (String permissionCode : permissions) {
            Permission permission = permissionMapper.selectByCode(permissionCode);
            if (permission != null) {
                AdminPermission adminPermission = new AdminPermission();
                adminPermission.setAdminId(adminId);
                adminPermission.setPermissionId(permission.getId());
                adminPermission.setCreateTime(LocalDateTime.now());
                adminPermissionMapper.insert(adminPermission);
            }
        }
    }

    @Override
    public List<PermissionVO> getAllPermissions() {
        List<Permission> permissions = permissionMapper.selectAll();

        return permissions.stream().map(permission -> {
            PermissionVO vo = new PermissionVO();
            BeanUtils.copyProperties(permission, vo);
            return vo;
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void createSubAdmin(CreateAdminDTO createDTO) {
        // 检查用户名是否已存在
        Admin existAdmin = adminMapper.selectByUsername(createDTO.getUsername());
        if (existAdmin != null) {
            throw new BusinessException("用户名已存在");
        }

        // 创建管理员
        Admin admin = new Admin();
        BeanUtils.copyProperties(createDTO, admin);
        admin.setPassword(createDTO.getPassword());
        admin.setStatus(1);
        admin.setCreateTime(LocalDateTime.now());
        admin.setCreateBy(SecurityUtils.getCurrentUserId());

        adminMapper.insert(admin);

        // 分配权限
        if (createDTO.getPermissions() != null && !createDTO.getPermissions().isEmpty()) {
            assignPermissions(admin.getId(), createDTO.getPermissions());
        }
    }

    @Override
    public AdminVO getAdminDetail(Long adminId) {
        Admin admin = adminMapper.selectById(adminId);
        if (admin == null) {
            throw new BusinessException("管理员不存在");
        }

        AdminVO vo = new AdminVO();
        BeanUtils.copyProperties(admin, vo);

        List<String> permissions = adminPermissionMapper.selectPermissionsByAdminId(adminId);
        vo.setPermissions(permissions);

        return vo;
    }

    @Override
    public void updateAdminStatus(Long adminId, Integer status) {
        Admin admin = adminMapper.selectById(adminId);
        if (admin == null) {
            throw new BusinessException("管理员不存在");
        }

        admin.setStatus(status);
        admin.setUpdateTime(LocalDateTime.now());
        adminMapper.updateById(admin);
    }

    @Override
    @Transactional
    public void deleteAdmin(Long adminId) {
        // 不能删除自己
        if (adminId.equals(SecurityUtils.getCurrentUserId())) {
            throw new BusinessException("不能删除当前登录的管理员");
        }

        // 删除管理员
        adminMapper.deleteById(adminId);

        // 删除权限关联
        adminPermissionMapper.deleteByAdminId(adminId);
    }
}