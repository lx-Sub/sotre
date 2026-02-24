package com.rabbiter.hrm.mapper;

import com.rabbiter.hrm.entity.Announcement;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 公告Mapper接口
 * @author 李鑫
 * @date 2026/2/24
 */
@Mapper
public interface AnnouncementMapper {

    /**
     * 根据ID查询公告
     */
    Announcement selectById(@Param("id") Long id);

    /**
     * 根据标题查询公告
     */
    List<Announcement> selectByTitle(@Param("title") String title);

    /**
     * 查询所有公告
     */
    List<Announcement> selectAll();

    /**
     * 根据条件查询公告
     */
    List<Announcement> selectByCondition(@Param("type") Integer type,
                                         @Param("status") Integer status);

    /**
     * 查询置顶公告
     */
    List<Announcement> selectTopAnnouncements();

    /**
     * 查询已发布的公告
     */
    List<Announcement> selectPublished();

    /**
     * 插入公告
     */
    void insert(Announcement announcement);

    /**
     * 更新公告
     */
    void updateById(Announcement announcement);

    /**
     * 更新公告状态
     */
    void updateStatus(@Param("id") Long id, @Param("status") Integer status);

    /**
     * 更新置顶状态
     */
    void updateTop(@Param("id") Long id, @Param("isTop") Boolean isTop);

    /**
     * 发布公告
     */
    void publish(@Param("id") Long id, @Param("publishTime") java.time.LocalDateTime publishTime);

    /**
     * 删除公告
     */
    void deleteById(@Param("id") Long id);

    /**
     * 统计公告数量
     */
    int countByCondition(@Param("type") Integer type, @Param("status") Integer status);
}