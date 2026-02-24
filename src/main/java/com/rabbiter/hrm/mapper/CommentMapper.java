package com.rabbiter.hrm.mapper;

import com.rabbiter.hrm.entity.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 评论Mapper接口
 * @author 李鑫
 * @date 2026/2/24
 */
@Mapper
public interface CommentMapper {

    /**
     * 根据ID查询评论
     */
    Comment selectById(@Param("id") Long id);

    /**
     * 根据帖子ID查询评论
     */
    List<Comment> selectByPostId(@Param("postId") Long postId);

    /**
     * 根据用户ID查询评论
     */
    List<Comment> selectByUserId(@Param("userId") Long userId);

    /**
     * 根据条件查询评论
     */
    List<Comment> selectByCondition(@Param("postId") Long postId,
                                    @Param("status") Integer status);

    /**
     * 查询所有评论
     */
    List<Comment> selectAll();

    /**
     * 插入评论
     */
    void insert(Comment comment);

    /**
     * 更新评论
     */
    void updateById(Comment comment);

    /**
     * 更新评论状态
     */
    void updateStatus(@Param("id") Long id, @Param("status") Integer status);

    /**
     * 增加点赞次数
     */
    void incrementLikeCount(@Param("id") Long id);

    /**
     * 统计帖子评论数
     */
    int countByPostId(@Param("postId") Long postId);

    /**
     * 统计用户评论数
     */
    int countByUserId(@Param("userId") Long userId);

    /**
     * 删除评论
     */
    void deleteById(@Param("id") Long id);

    /**
     * 根据帖子ID删除评论
     */
    void deleteByPostId(@Param("postId") Long postId);

    /**
     * 统计所有评论数
     */
    int countAll();
}