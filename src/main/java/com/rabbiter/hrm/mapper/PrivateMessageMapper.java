package com.rabbiter.hrm.mapper;

import com.rabbiter.hrm.entity.PrivateMessage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 私信Mapper接口
 * @author 李鑫
 * @date 2026/2/24
 */
@Mapper
public interface PrivateMessageMapper {

    /**
     * 根据ID查询私信
     */
    PrivateMessage selectById(@Param("id") Long id);

    /**
     * 根据发送者ID查询
     */
    List<PrivateMessage> selectBySenderId(@Param("senderId") Long senderId);

    /**
     * 根据接收者ID查询
     */
    List<PrivateMessage> selectByReceiverId(@Param("receiverId") Long receiverId);

    /**
     * 根据条件查询私信
     */
    List<PrivateMessage> selectByCondition(@Param("senderId") Long senderId,
                                           @Param("receiverId") Long receiverId,
                                           @Param("startDate") LocalDateTime startDate,
                                           @Param("endDate") LocalDateTime endDate);

    /**
     * 查询两个用户间的私信
     */
    List<PrivateMessage> selectConversation(@Param("userId1") Long userId1,
                                            @Param("userId2") Long userId2,
                                            @Param("offset") Integer offset,
                                            @Param("limit") Integer limit);

    /**
     * 获取最近的私信列表（按用户分组）
     */
    List<Map<String, Object>> selectRecentConversations(@Param("userId") Long userId);

    /**
     * 插入私信
     */
    void insert(PrivateMessage privateMessage);

    /**
     * 更新私信阅读状态
     */
    void updateReadStatus(@Param("id") Long id,
                          @Param("isRead") Boolean isRead,
                          @Param("readTime") LocalDateTime readTime);

    /**
     * 批量更新已读状态
     */
    void batchUpdateReadStatus(@Param("userId") Long userId,
                               @Param("senderId") Long senderId);

    /**
     * 统计未读私信数
     */
    int countUnread(@Param("userId") Long userId);

    /**
     * 统计与某个用户的未读私信数
     */
    int countUnreadWithUser(@Param("userId") Long userId, @Param("otherUserId") Long otherUserId);

    /**
     * 统计私信总数
     */
    int countByCondition(@Param("senderId") Long senderId,
                         @Param("receiverId") Long receiverId,
                         @Param("startDate") LocalDateTime startDate,
                         @Param("endDate") LocalDateTime endDate);

    /**
     * 删除私信
     */
    void deleteById(@Param("id") Long id);

    /**
     * 删除与某用户的会话
     */
    void deleteConversation(@Param("userId1") Long userId1, @Param("userId2") Long userId2);
}