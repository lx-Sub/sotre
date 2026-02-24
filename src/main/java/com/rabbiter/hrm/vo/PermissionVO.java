package com.rabbiter.hrm.vo;

import lombok.Data;
import java.util.List;

/**
 * 权限视图对象
 */
@Data
public class PermissionVO {

    /**
     * 权限ID
     */
    private Long id;

    /**
     * 权限名称
     */
    private String name;

    /**
     * 权限代码
     */
    private String code;

    /**
     * 类型
     */
    private String type;

    /**
     * 父权限代码
     */
    private String parentCode;

    /**
     * 路径
     */
    private String path;

    /**
     * 组件
     */
    private String component;

    /**
     * 图标
     */
    private String icon;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 子权限列表
     */
    private List<PermissionVO> children;
}