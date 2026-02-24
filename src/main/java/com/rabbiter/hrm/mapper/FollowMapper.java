package com.rabbiter.hrm.mapper;

import com.rabbiter.hrm.entity.UserFollow;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 关注Mapper接口
 * @author 李鑫
 * @date 2026/2/24
 */
@Mapper
public interface FollowMapper {

    /**
     * 根据ID查询关注
     */
    UserFollow selectById(@Param("id") Long id);

    /**
     * 根据用户ID查询关注列表
     */
    List<UserFollow> selectByUserId(@Param("userId") Long userId);

    /**
     * 根据用户和目标查询关注
     */
    UserFollow selectByUserAndTarget(@Param("userId") Long userId,
                                     @Param("targetUserId") Long targetUserId);

    /**
     * 插入关注
     */
    void insert(UserFollow follow);

    /**
     * 删除关注
     */
    void deleteById(@Param("id") Long id);

    /**
     * 根据用户和目标删除关注
     */
    void deleteByUserAndTarget(@Param("userId") Long userId,
                               @Param("targetUserId") Long targetUserId);

    /**
     * 统计用户关注数量
     */
    int countByUserId(@Param("userId") Long userId);

    /**
     * 统计用户的粉丝数量
     */
    int countFansByUserId(@Param("targetUserId") Long targetUserId);

    /**
     * 检查是否互相关注
     */
    int checkMutualFollow(@Param("userId1") Long userId1, @Param("userId2") Long userId2);
}