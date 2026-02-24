package com.rabbiter.hrm.controller;

import com.rabbiter.hrm.dto.ResponseDTO;
import com.rabbiter.hrm.dto.Response;
import com.rabbiter.hrm.dto.*;
import com.rabbiter.hrm.service.AdminService;
import com.rabbiter.hrm.service.AdminUserService;
import com.rabbiter.hrm.vo.*;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminUserService adminUserService;

    @Autowired
    private AdminService adminService;

    // ==================== 用户管理 ====================

    /**
     * 获取用户列表（管理员功能）
     */
    @GetMapping("/users/list")
    public ResponseDTO getUserList(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Integer role,
            @RequestParam(required = false) Integer status,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDateTime endDate) {

        UserQueryDTO queryDTO = UserQueryDTO.builder()
                .pageNum(pageNum)
                .pageSize(pageSize)
                .keyword(keyword)
                .role(role)
                .status(status)
                .startDate(startDate)
                .endDate(endDate)
                .build();

        PageInfo<UserVO> pageInfo = adminUserService.getUserList(queryDTO);
        return Response.success(pageInfo);
    }

    /**
     * 获取用户详情
     */
    @GetMapping("/users/{id}")
    public ResponseDTO getUserDetail(@PathVariable Long id) {
        UserDetailVO userDetail = adminUserService.getUserDetail(id);
        return Response.success(userDetail);
    }

    /**
     * 冻结/解冻用户
     */
    @PutMapping("/users/{id}/status")
    public ResponseDTO updateUserStatus(@PathVariable Long id,
                                        @RequestParam Integer status,
                                        @RequestParam(required = false) String reason) {
        AdminOperationDTO operationDTO = AdminOperationDTO.builder()
                .userId(id)
                .operation(status == 0 ? "freeze" : "unfreeze")
                .reason(reason)
                .build();
        adminUserService.updateUserStatus(operationDTO);
        return Response.success(status == 0 ? "用户已冻结" : "用户已解冻");
    }

    /**
     * 批量操作用户状态
     */
    @PutMapping("/users/batch/status")
    public ResponseDTO batchUpdateUserStatus(@RequestBody BatchUserStatusDTO batchDTO) {
        adminUserService.batchUpdateUserStatus(batchDTO);
        return Response.success("批量操作成功");
    }

    /**
     * 注销用户
     */
    @DeleteMapping("/users/{id}")
    public ResponseDTO deleteUser(@PathVariable Long id,
                                  @RequestParam String reason) {
        adminUserService.deleteUser(id, reason);
        return Response.success("用户已注销");
    }

    // ==================== 商家审核 ====================

    /**
     * 获取待审核商家列表
     */
    @GetMapping("/merchant/pending")
    public ResponseDTO getPendingMerchants(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<MerchantAuditVO> pageInfo = adminUserService.getPendingMerchants(pageNum, pageSize);
        return Response.success(pageInfo);
    }

    /**
     * 审核商家入驻
     */
    @PostMapping("/merchant/audit")
    public ResponseDTO auditMerchant(@Valid @RequestBody MerchantAuditDTO auditDTO) {
        adminUserService.auditMerchant(auditDTO);
        String message = auditDTO.getApprove() == 1 ? "审核通过" : "已驳回";
        return Response.success(message);
    }

    /**
     * 查看商家资质文件
     */
    @GetMapping("/merchant/{userId}/credentials")
    public ResponseDTO getMerchantCredentials(@PathVariable Long userId) {
        List<CredentialVO> credentials = adminUserService.getMerchantCredentials(userId);
        return Response.success(credentials);
    }

    // ==================== 信用管理 ====================

    /**
     * 获取信用分列表
     */
    @GetMapping("/credit/list")
    public ResponseDTO getCreditList(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Integer minScore,
            @RequestParam(required = false) Integer maxScore) {

        CreditQueryDTO queryDTO = CreditQueryDTO.builder()
                .pageNum(pageNum)
                .pageSize(pageSize)
                .keyword(keyword)
                .minScore(minScore)
                .maxScore(maxScore)
                .build();

        PageInfo<CreditVO> pageInfo = adminUserService.getCreditList(queryDTO);
        return Response.success(pageInfo);
    }

    /**
     * 更新用户信用分
     */
    @PutMapping("/credit/{userId}")
    public ResponseDTO updateCredit(@PathVariable Long userId,
                                    @Valid @RequestBody CreditUpdateDTO creditDTO) {
        creditDTO.setUserId(userId);
        adminUserService.updateCreditScore(creditDTO);
        return Response.success("信用分已更新");
    }

    /**
     * 获取信用变更历史
     */
    @GetMapping("/credit/{userId}/history")
    public ResponseDTO getCreditHistory(@PathVariable Long userId,
                                        @RequestParam(defaultValue = "1") Integer pageNum,
                                        @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<CreditHistoryVO> history = adminUserService.getCreditHistory(userId, pageNum, pageSize);
        return Response.success(history);
    }

    /**
     * 处理信用申诉
     */
    @PostMapping("/credit/appeal")
    public ResponseDTO handleCreditAppeal(@Valid @RequestBody CreditAppealDTO appealDTO) {
        adminUserService.handleCreditAppeal(appealDTO);
        return Response.success("申诉已处理");
    }

    // ==================== 个人寄售资质审核 ====================

    /**
     * 获取待审核个人寄售资质列表
     */
    @GetMapping("/consignment/pending")
    public ResponseDTO getPendingConsignment(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<ConsignmentAuditVO> pageInfo = adminUserService.getPendingConsignment(pageNum, pageSize);
        return Response.success(pageInfo);
    }

    /**
     * 审核个人寄售资质
     */
    @PostMapping("/consignment/audit")
    public ResponseDTO auditConsignment(@Valid @RequestBody ConsignmentAuditDTO auditDTO) {
        adminUserService.auditConsignment(auditDTO);
        return Response.success(auditDTO.getApprove() == 1 ? "资质审核通过" : "资质审核驳回");
    }

    /**
     * 查看用户实名认证信息
     */
    @GetMapping("/users/{userId}/verification")
    public ResponseDTO getUserVerification(@PathVariable Long userId) {
        UserVerificationVO verification = adminUserService.getUserVerification(userId);
        return Response.success(verification);
    }

    // ==================== 权限分配 ====================

    /**
     * 获取管理员列表
     */
    @GetMapping("/admins/list")
    public ResponseDTO getAdminList(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<AdminVO> pageInfo = adminService.getAdminList(pageNum, pageSize);
        return Response.success(pageInfo);
    }

    /**
     * 分配管理员权限
     */
    @PutMapping("/admins/{adminId}/permissions")
    public ResponseDTO assignPermissions(@PathVariable Long adminId,
                                         @RequestBody List<String> permissions) {
        adminService.assignPermissions(adminId, permissions);
        return Response.success("权限分配成功");
    }

    /**
     * 获取权限列表
     */
    @GetMapping("/permissions/list")
    public ResponseDTO getPermissionList() {
        List<PermissionVO> permissions = adminService.getAllPermissions();
        return Response.success(permissions);
    }

    /**
     * 创建子管理员
     */
    @PostMapping("/admins/create")
    public ResponseDTO createSubAdmin(@Valid @RequestBody CreateAdminDTO createDTO) {
        adminService.createSubAdmin(createDTO);
        return Response.success("子管理员创建成功");
    }


    // ==================== 内容管理 ====================

    /**
     * 获取帖子列表（待审核/全部）
     */
    @GetMapping("/content/posts")
    public ResponseDTO getPostList(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Integer status,  // 0-待审核 1-已发布 2-已屏蔽
            @RequestParam(required = false) String category) {

        PageInfo<PostVO> pageInfo = adminService.getPostList(pageNum, pageSize, keyword, status, category);
        return Response.success(pageInfo);
    }

    /**
     * 审核帖子
     */
    @PutMapping("/content/posts/{id}/audit")
    public ResponseDTO auditPost(@PathVariable Long id,
                                 @RequestParam Integer status,  // 1-通过 2-屏蔽
                                 @RequestParam(required = false) String reason) {
        adminService.auditPost(id, status, reason);
        String message = status == 1 ? "帖子已通过" : "帖子已屏蔽";
        return Response.success(message);
    }

    /**
     * 帖子加精/取消加精
     */
    @PutMapping("/content/posts/{id}/essence")
    public ResponseDTO setPostEssence(@PathVariable Long id,
                                      @RequestParam Boolean isEssence) {
        adminService.setPostEssence(id, isEssence);
        return Response.success(isEssence ? "已设为精华" : "已取消精华");
    }

    /**
     * 帖子置顶/取消置顶
     */
    @PutMapping("/content/posts/{id}/top")
    public ResponseDTO setPostTop(@PathVariable Long id,
                                  @RequestParam Boolean isTop) {
        adminService.setPostTop(id, isTop);
        return Response.success(isTop ? "已置顶" : "已取消置顶");
    }

    /**
     * 获取评论列表
     */
    @GetMapping("/content/comments")
    public ResponseDTO getCommentList(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) Long postId,
            @RequestParam(required = false) Integer status) {

        PageInfo<CommentVO> pageInfo = adminService.getCommentList(pageNum, pageSize, postId, status);
        return Response.success(pageInfo);
    }

    /**
     * 屏蔽评论
     */
    @PutMapping("/content/comments/{id}/block")
    public ResponseDTO blockComment(@PathVariable Long id,
                                    @RequestParam String reason) {
        adminService.blockComment(id, reason);
        return Response.success("评论已屏蔽");
    }

    /**
     * 分区管理 - 获取所有分区
     */
    @GetMapping("/content/categories")
    public ResponseDTO getCategoryList() {
        List<CategoryVO> categories = adminService.getCategoryList();
        return Response.success(categories);
    }

    /**
     * 新增分区
     */
    @PostMapping("/content/categories")
    public ResponseDTO addCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        adminService.addCategory(categoryDTO);
        return Response.success("分区创建成功");
    }

    /**
     * 修改分区
     */
    @PutMapping("/content/categories/{id}")
    public ResponseDTO updateCategory(@PathVariable Long id,
                                      @Valid @RequestBody CategoryDTO categoryDTO) {
        categoryDTO.setId(id);
        adminService.updateCategory(categoryDTO);
        return Response.success("分区修改成功");
    }

    /**
     * 删除分区
     */
    @DeleteMapping("/content/categories/{id}")
    public ResponseDTO deleteCategory(@PathVariable Long id) {
        adminService.deleteCategory(id);
        return Response.success("分区删除成功");
    }

    /**
     * 商品审核 - 获取待审核商品列表
     */
    @GetMapping("/content/products/pending")
    public ResponseDTO getPendingProducts(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {

        PageInfo<ProductVO> pageInfo = adminService.getPendingProducts(pageNum, pageSize);
        return Response.success(pageInfo);
    }

    /**
     * 审核商品
     */
    @PutMapping("/content/products/{id}/audit")
    public ResponseDTO auditProduct(@PathVariable Long id,
                                    @RequestParam Integer status,  // 1-通过 2-违规下架
                                    @RequestParam(required = false) String reason) {
        adminService.auditProduct(id, status, reason);
        return Response.success(status == 1 ? "商品已通过" : "商品已下架");
    }

    /**
     * 违规举报处理 - 获取举报列表
     */
    @GetMapping("/content/reports")
    public ResponseDTO getReportList(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) Integer status,  // 0-待处理 1-已处理 2-驳回
            @RequestParam(required = false) Integer targetType) {  // 1-帖子 2-评论 3-商品

        PageInfo<ReportVO> pageInfo = adminService.getReportList(pageNum, pageSize, status, targetType);
        return Response.success(pageInfo);
    }

    /**
     * 处理举报
     */
    @PostMapping("/content/reports/{id}/handle")
    public ResponseDTO handleReport(@PathVariable Long id,
                                    @Valid @RequestBody ReportHandleDTO handleDTO) {
        handleDTO.setReportId(id);
        adminService.handleReport(handleDTO);
        return Response.success("举报已处理");
    }

    // ==================== 交易管理 ====================

    /**
     * 获取订单列表（监控）
     */
    @GetMapping("/trade/orders")
    public ResponseDTO getOrderList(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String orderNo,
            @RequestParam(required = false) Integer orderType,  // 1-销售 2-交换
            @RequestParam(required = false) Integer status,
            @RequestParam(required = false) String buyerName,
            @RequestParam(required = false) String sellerName,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDateTime endDate) {

        OrderQueryDTO queryDTO = OrderQueryDTO.builder()
                .pageNum(pageNum)
                .pageSize(pageSize)
                .orderNo(orderNo)
                .orderType(orderType)
                .status(status)
                .buyerName(buyerName)
                .sellerName(sellerName)
                .startDate(startDate)
                .endDate(endDate)
                .build();

        PageInfo<OrderVO> pageInfo = adminService.getOrderList(queryDTO);
        return Response.success(pageInfo);
    }

    /**
     * 获取订单详情
     */
    @GetMapping("/trade/orders/{id}")
    public ResponseDTO getOrderDetail(@PathVariable Long id) {
        OrderDetailVO orderDetail = adminService.getOrderDetail(id);
        return Response.success(orderDetail);
    }

    /**
     * 获取售后列表
     */
    @GetMapping("/trade/after-sales")
    public ResponseDTO getAfterSaleList(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) Integer status) {  // 0-待处理 3-仲裁中

        PageInfo<AfterSaleVO> pageInfo = adminService.getAfterSaleList(pageNum, pageSize, status);
        return Response.success(pageInfo);
    }

    /**
     * 售后仲裁
     */
    @PostMapping("/trade/after-sales/{id}/arbitrate")
    public ResponseDTO arbitrateAfterSale(@PathVariable Long id,
                                          @Valid @RequestBody ArbitrationDTO arbitrationDTO) {
        arbitrationDTO.setAfterSaleId(id);
        adminService.arbitrateAfterSale(arbitrationDTO);
        return Response.success("仲裁完成");
    }

    /**
     * 获取交换订单列表
     */
    @GetMapping("/trade/exchange-orders")
    public ResponseDTO getExchangeOrderList(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String exchangeNo,
            @RequestParam(required = false) Integer status) {

        PageInfo<ExchangeOrderVO> pageInfo = adminService.getExchangeOrderList(pageNum, pageSize, exchangeNo, status);
        return Response.success(pageInfo);
    }

    // ==================== 系统管理 ====================

    /**
     * 获取公告列表
     */
    @GetMapping("/system/announcements")
    public ResponseDTO getAnnouncementList(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) Integer type,
            @RequestParam(required = false) Integer status) {

        PageInfo<AnnouncementVO> pageInfo = adminService.getAnnouncementList(pageNum, pageSize, type, status);
        return Response.success(pageInfo);
    }

    /**
     * 发布公告
     */
    @PostMapping("/system/announcements")
    public ResponseDTO publishAnnouncement(@Valid @RequestBody AnnouncementDTO announcementDTO) {
        adminService.publishAnnouncement(announcementDTO);
        return Response.success("公告发布成功");
    }

    /**
     * 修改公告
     */
    @PutMapping("/system/announcements/{id}")
    public ResponseDTO updateAnnouncement(@PathVariable Long id,
                                          @Valid @RequestBody AnnouncementDTO announcementDTO) {
        announcementDTO.setId(id);
        adminService.updateAnnouncement(announcementDTO);
        return Response.success("公告修改成功");
    }

    /**
     * 删除公告
     */
    @DeleteMapping("/system/announcements/{id}")
    public ResponseDTO deleteAnnouncement(@PathVariable Long id) {
        adminService.deleteAnnouncement(id);
        return Response.success("公告删除成功");
    }

    /**
     * 消息监控 - 获取私信列表
     */
    @GetMapping("/system/messages")
    public ResponseDTO getMessageList(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) Long senderId,
            @RequestParam(required = false) Long receiverId,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDateTime endDate) {

        PageInfo<PrivateMessageVO> pageInfo = adminService.getMessageList(pageNum, pageSize,
                senderId, receiverId, startDate, endDate);
        return Response.success(pageInfo);
    }

    /**
     * 查看私信详情
     */
    @GetMapping("/system/messages/{id}")
    public ResponseDTO getMessageDetail(@PathVariable Long id) {
        PrivateMessageDetailVO messageDetail = adminService.getMessageDetail(id);
        return Response.success(messageDetail);
    }

    /**
     * 获取操作日志
     */
    @GetMapping("/system/logs")
    public ResponseDTO getOperationLogs(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) Long operatorId,
            @RequestParam(required = false) String operation,
            @RequestParam(required = false) String targetType,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDateTime endDate) {

        LogQueryDTO queryDTO = LogQueryDTO.builder()
                .pageNum(pageNum)
                .pageSize(pageSize)
                .operatorId(operatorId)
                .operation(operation)
                .targetType(targetType)
                .startDate(startDate)
                .endDate(endDate)
                .build();

        PageInfo<OperationLogVO> pageInfo = adminService.getOperationLogs(queryDTO);
        return Response.success(pageInfo);
    }
}