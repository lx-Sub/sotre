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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

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

    // 在 UserMapper.java 中添加以下方法


    /**
     * 更新用户信用分
     */
    void updateCreditScore(@Param("id") Long id, @Param("score") Integer score);

    /**
     * 更新商家状态
     */
    void updateMerchantStatus(@Param("id") Long id,
                              @Param("merchantStatus") Integer merchantStatus,
                              @Param("consignmentEnabled") Boolean consignmentEnabled,
                              @Param("consignmentExpiry") LocalDateTime consignmentExpiry);

    /**
     * 更新最后登录信息
     */
    void updateLastLogin(@Param("id") Long id,
                         @Param("lastLoginTime") LocalDateTime lastLoginTime,
                         @Param("lastLoginIp") String lastLoginIp);

    /**
     * 批量冻结用户
     */
    int batchFreezeUsers(@Param("userIds") List<Long> userIds,
                         @Param("reason") String reason);

    /**
     * 统计用户状态分布
     */
    List<Map<String, Object>> statisticsByStatus();

    /**
     * 统计用户角色分布
     */
    List<Map<String, Object>> statisticsByRole();

    /**
     * 统计每日注册量
     */
    List<Map<String, Object>> statisticsByDay(@Param("startDate") LocalDateTime startDate,
                                              @Param("endDate") LocalDateTime endDate);

    /**
     * 根据条件统计用户数量
     */
    int countByCondition(@Param("keyword") String keyword,
                         @Param("role") Integer role,
                         @Param("status") Integer status);

    /**
     * 根据时间范围统计用户数量
     */
    int countByDate(@Param("startDate") LocalDateTime startDate,
                    @Param("endDate") LocalDateTime endDate);

    /**
     * 统计活跃用户数
     */
    int countActiveUsers();

    /**
     * 根据状态统计用户数
     */
    int countByStatus(@Param("status") Integer status);

    /**
     * 根据角色统计用户数
     */
    int countByRole(@Param("role") Integer role);

    /**
     * 根据商家状态统计
     */
    int countByMerchantStatus(@Param("merchantStatus") Integer merchantStatus);

    /**
     * 根据信用分范围统计
     */
    int countByCreditRange(@Param("min") Integer min,
                           @Param("max") Integer max);

    /**
     * 平均信用分
     */
    Double avgCreditScore();

    /**
     * 最高信用分
     */
    Integer maxCreditScore();

    /**
     * 最低信用分
     */
    Integer minCreditScore();

    /**
     * 查询信用分最高的用户
     */
    List<User> selectTopCreditUsers(@Param("limit") Integer limit);

    /**
     * 查询信用分最低的用户
     */
    List<User> selectBottomCreditUsers(@Param("limit") Integer limit);

    /**
     * 按日期范围统计用户
     */
    List<Map<String, Object>> statisticsByDateRange(@Param("startDate") LocalDateTime startDate,
                                                    @Param("endDate") LocalDateTime endDate,
                                                    @Param("groupBy") String groupBy);
}
