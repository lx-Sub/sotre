package com.rabbiter.hrm.mapper;

import com.rabbiter.hrm.entity.UserFavorite;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 收藏Mapper接口
 * @author 李鑫
 * @date 2026/2/24
 */
@Mapper
public interface FavoriteMapper {

    /**
     * 根据ID查询收藏
     */
    UserFavorite selectById(@Param("id") Long id);

    /**
     * 根据用户ID查询收藏列表
     */
    List<UserFavorite> selectByUserId(@Param("userId") Long userId,
                                      @Param("type") Integer type);

    /**
     * 根据用户和目标查询收藏
     */
    UserFavorite selectByUserAndTarget(@Param("userId") Long userId,
                                       @Param("type") Integer type,
                                       @Param("targetId") Long targetId);

    /**
     * 插入收藏
     */
    void insert(UserFavorite favorite);

    /**
     * 删除收藏
     */
    void deleteById(@Param("id") Long id);

    /**
     * 根据用户和目标删除收藏
     */
    void deleteByUserAndTarget(@Param("userId") Long userId,
                               @Param("type") Integer type,
                               @Param("targetId") Long targetId);

    /**
     * 统计用户收藏数量
     */
    int countByUserId(@Param("userId") Long userId, @Param("type") Integer type);

    /**
     * 统计目标的收藏数量
     */
    int countByTarget(@Param("type") Integer type, @Param("targetId") Long targetId);
}