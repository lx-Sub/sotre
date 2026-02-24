package com.rabbiter.hrm.mapper;

import com.rabbiter.hrm.entity.UserVerification;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface UserVerificationMapper {

    /**
     * 根据用户ID查询认证信息
     */
    UserVerification selectByUserId(@Param("userId") Long userId);

    /**
     * 查询待审核的认证列表
     */
    List<UserVerification> selectPending();

    /**
     * 查询所有认证信息
     */
    List<UserVerification> selectAll();

    /**
     * 根据ID查询
     */
    UserVerification selectById(@Param("id") Long id);

    /**
     * 插入认证信息
     */
    void insert(UserVerification userVerification);

    /**
     * 更新认证信息
     */
    void updateById(UserVerification userVerification);

    /**
     * 统计待审核数量
     */
    int countPending();

    /**
     * 统计已认证数量
     */
    int countVerified();
}