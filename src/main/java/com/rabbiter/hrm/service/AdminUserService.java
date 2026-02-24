package com.rabbiter.hrm.service;

import com.rabbiter.hrm.dto.*;
import com.rabbiter.hrm.vo.*;
import com.github.pagehelper.PageInfo;
import java.util.List;

public interface AdminUserService {

    // 用户管理
    PageInfo<UserVO> getUserList(UserQueryDTO queryDTO);
    UserDetailVO getUserDetail(Long userId);
    void updateUserStatus(AdminOperationDTO operationDTO);
    void batchUpdateUserStatus(BatchUserStatusDTO batchDTO);
    void deleteUser(Long userId, String reason);

    // 商家审核
    PageInfo<MerchantAuditVO> getPendingMerchants(Integer pageNum, Integer pageSize);
    void auditMerchant(MerchantAuditDTO auditDTO);
    List<CredentialVO> getMerchantCredentials(Long userId);

    // 信用管理
    PageInfo<CreditVO> getCreditList(CreditQueryDTO queryDTO);
    void updateCreditScore(CreditUpdateDTO creditDTO);
    PageInfo<CreditHistoryVO> getCreditHistory(Long userId, Integer pageNum, Integer pageSize);
    void handleCreditAppeal(CreditAppealDTO appealDTO);

    // 个人寄售资质
    PageInfo<ConsignmentAuditVO> getPendingConsignment(Integer pageNum, Integer pageSize);
    void auditConsignment(ConsignmentAuditDTO auditDTO);
    UserVerificationVO getUserVerification(Long userId);

    // 导出功能
    byte[] exportUserList(UserQueryDTO queryDTO);
    byte[] exportCreditList(CreditQueryDTO queryDTO);
}