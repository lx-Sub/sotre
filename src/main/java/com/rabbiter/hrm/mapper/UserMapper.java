package com.rabbiter.hrm.mapper;

/**
 * @description:
 * @author: 李鑫
 * @date: 2026/2/24 13:56
 */

import com.rabbiter.hrm.dto.CreditQueryDTO;
import com.rabbiter.hrm.dto.UserQueryDTO;
import com.rabbiter.hrm.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface UserMapper {

    User selectById(@Param("id") Long id);

    User selectByPhone(@Param("phone") String phone);

    List<User> selectList(@Param("keyword") String keyword,
                          @Param("role") Integer role,
                          @Param("status") Integer status);

    int insert(User user);

    int update(User user);

    int updateStatus(@Param("id") Long id, @Param("status") Integer status);

    int updateCreditScore(@Param("id") Long id, @Param("score") Integer score);

    /**
     * 根据条件查询用户（用于信用分列表）
     */
    List<User> selectByCreditCondition(CreditQueryDTO queryDTO);


    List<User> selectByCondition(UserQueryDTO queryDTO);


    /**
     * 更新用户
     */
    void updateById(User user);

    /**
     * 批量更新用户状态
     */
    void batchUpdateStatus(@Param("userIds") List<Long> userIds,
                           @Param("status") Integer status);
}
