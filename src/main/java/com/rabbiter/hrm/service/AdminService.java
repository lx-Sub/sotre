package com.rabbiter.hrm.service;

import com.rabbiter.hrm.dto.*;
import com.rabbiter.hrm.vo.*;
import com.github.pagehelper.PageInfo;

import java.time.LocalDateTime;
import java.util.List;

public interface AdminService {

    // ==================== 原有管理员相关方法 ====================
    PageInfo<AdminVO> getAdminList(Integer pageNum, Integer pageSize);
    void assignPermissions(Long adminId, List<String> permissions);
    List<PermissionVO> getAllPermissions();
    void createSubAdmin(CreateAdminDTO createDTO);
    AdminVO getAdminDetail(Long adminId);
    void updateAdminStatus(Long adminId, Integer status);
    void deleteAdmin(Long adminId);

    // ==================== 内容管理 ====================

    /**
     * 获取帖子列表
     */
    PageInfo<PostVO> getPostList(Integer pageNum, Integer pageSize, String keyword, Integer status, String category);

    /**
     * 审核帖子
     */
    void auditPost(Long id, Integer status, String reason);

    /**
     * 设置帖子精华
     */
    void setPostEssence(Long id, Boolean isEssence);

    /**
     * 设置帖子置顶
     */
    void setPostTop(Long id, Boolean isTop);

    /**
     * 获取评论列表
     */
    PageInfo<CommentVO> getCommentList(Integer pageNum, Integer pageSize, Long postId, Integer status);

    /**
     * 屏蔽评论
     */
    void blockComment(Long id, String reason);

    /**
     * 获取所有分区
     */
    List<CategoryVO> getCategoryList();

    /**
     * 新增分区
     */
    void addCategory(CategoryDTO categoryDTO);

    /**
     * 修改分区
     */
    void updateCategory(CategoryDTO categoryDTO);

    /**
     * 删除分区
     */
    void deleteCategory(Long id);

    /**
     * 获取待审核商品列表
     */
    PageInfo<ProductVO> getPendingProducts(Integer pageNum, Integer pageSize);

    /**
     * 审核商品
     */
    void auditProduct(Long id, Integer status, String reason);

    /**
     * 获取举报列表
     */
    PageInfo<ReportVO> getReportList(Integer pageNum, Integer pageSize, Integer status, Integer targetType);

    /**
     * 处理举报
     */
    void handleReport(ReportHandleDTO handleDTO);

    // ==================== 交易管理 ====================

    /**
     * 获取订单列表
     */
    PageInfo<OrderVO> getOrderList(OrderQueryDTO queryDTO);

    /**
     * 获取订单详情
     */
    OrderDetailVO getOrderDetail(Long id);

    /**
     * 获取售后列表
     */
    PageInfo<AfterSaleVO> getAfterSaleList(Integer pageNum, Integer pageSize, Integer status);

    /**
     * 售后仲裁
     */
    void arbitrateAfterSale(ArbitrationDTO arbitrationDTO);

    /**
     * 获取交换订单列表
     */
    PageInfo<ExchangeOrderVO> getExchangeOrderList(Integer pageNum, Integer pageSize, String exchangeNo, Integer status);

    // ==================== 系统管理 ====================

    /**
     * 获取公告列表
     */
    PageInfo<AnnouncementVO> getAnnouncementList(Integer pageNum, Integer pageSize, Integer type, Integer status);

    /**
     * 发布公告
     */
    void publishAnnouncement(AnnouncementDTO announcementDTO);

    /**
     * 修改公告
     */
    void updateAnnouncement(AnnouncementDTO announcementDTO);

    /**
     * 删除公告
     */
    void deleteAnnouncement(Long id);

    /**
     * 获取私信列表
     */
    PageInfo<PrivateMessageVO> getMessageList(Integer pageNum, Integer pageSize, Long senderId,
                                              Long receiverId, LocalDateTime startDate, LocalDateTime endDate);

    /**
     * 获取私信详情
     */
    PrivateMessageDetailVO getMessageDetail(Long id);

    /**
     * 获取操作日志
     */
    PageInfo<OperationLogVO> getOperationLogs(LogQueryDTO queryDTO);
}