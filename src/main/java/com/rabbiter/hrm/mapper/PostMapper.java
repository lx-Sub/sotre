package com.rabbiter.hrm.mapper;

import com.rabbiter.hrm.entity.Post;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 帖子Mapper接口
 * @author 李鑫
 * @date 2026/2/24
 */
@Mapper
public interface PostMapper {

    /**
     * 根据ID查询帖子
     */
    Post selectById(@Param("id") Long id);

    /**
     * 根据用户ID查询帖子
     */
    List<Post> selectByUserId(@Param("userId") Long userId);

    /**
     * 根据条件查询帖子
     */
    List<Post> selectByCondition(@Param("keyword") String keyword,
                                 @Param("status") Integer status,
                                 @Param("category") String category);

    /**
     * 查询所有帖子
     */
    List<Post> selectAll();

    /**
     * 查询待审核帖子
     */
    List<Post> selectPending();

    /**
     * 插入帖子
     */
    void insert(Post post);

    /**
     * 更新帖子
     */
    void updateById(Post post);

    /**
     * 更新帖子状态
     */
    void updateStatus(@Param("id") Long id, @Param("status") Integer status);

    /**
     * 更新帖子审核信息
     */
    void updateAuditInfo(@Param("id") Long id,
                         @Param("status") Integer status,
                         @Param("auditRemark") String auditRemark);

    /**
     * 更新帖子精华状态
     */
    void updateEssence(@Param("id") Long id, @Param("isEssence") Boolean isEssence);

    /**
     * 更新帖子置顶状态
     */
    void updateTop(@Param("id") Long id, @Param("isTop") Boolean isTop);

    /**
     * 增加浏览次数
     */
    void incrementViewCount(@Param("id") Long id);

    /**
     * 增加点赞次数
     */
    void incrementLikeCount(@Param("id") Long id);

    /**
     * 增加评论次数
     */
    void incrementCommentCount(@Param("id") Long id);

    /**
     * 统计用户帖子数
     */
    int countByUserId(@Param("userId") Long userId);

    /**
     * 统计待审核帖子数
     */
    int countPending();

    /**
     * 删除帖子
     */
    void deleteById(@Param("id") Long id);

    /**
     * 统计所有帖子数
     */
    int countAll();

    /**
     * 按日期统计帖子数
     */
    int countByDate(@Param("startDate") LocalDateTime startDate,
                    @Param("endDate") LocalDateTime endDate);

    /**
     * 按分类统计帖子分布
     */
    List<Map<String, Object>> statisticsByCategory(@Param("limit") Integer limit);
}