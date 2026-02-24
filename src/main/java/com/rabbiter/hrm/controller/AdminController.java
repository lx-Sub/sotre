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
}