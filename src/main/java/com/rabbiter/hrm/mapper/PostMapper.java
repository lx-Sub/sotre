package com.rabbiter.hrm.mapper;

import com.rabbiter.hrm.entity.Post;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface PostMapper {

    /**
     * 根据ID查询帖子
     */
    Post selectById(@Param("id") Long id);

    /**
     * 查询用户帖子列表
     */
    List<Post> selectByUserId(@Param("userId") Long userId);

    /**
     * 统计用户帖子数量
     */
    int countByUserId(@Param("userId") Long userId);

    /**
     * 查询所有帖子
     */
    List<Post> selectAll();

    /**
     * 插入帖子
     */
    void insert(Post post);

    /**
     * 更新帖子
     */
    void updateById(Post post);

    /**
     * 删除帖子
     */
    void deleteById(@Param("id") Long id);
}