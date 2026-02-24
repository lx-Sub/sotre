package com.rabbiter.hrm.service.Impl;

import com.rabbiter.hrm.dto.*;
import com.rabbiter.hrm.entity.*;
import com.rabbiter.hrm.exception.BusinessException;
import com.rabbiter.hrm.mapper.*;
import com.rabbiter.hrm.service.AdminUserService;
import com.rabbiter.hrm.util.SecurityUtils;
import com.rabbiter.hrm.vo.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AdminUserServiceImpl implements AdminUserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CreditRecordMapper creditRecordMapper;

    @Autowired
    private MerchantApplyMapper merchantApplyMapper;

    @Autowired
    private UserVerificationMapper userVerificationMapper;

    @Autowired
    private OperationLogMapper operationLogMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private PostMapper postMapper;

    @Override
    public PageInfo<UserVO> getUserList(UserQueryDTO queryDTO) {
        PageHelper.startPage(queryDTO.getPageNum(), queryDTO.getPageSize());
        List<User> users = userMapper.selectByCondition(queryDTO);

        List<UserVO> userVOs = users.stream().map(user -> {
            UserVO vo = new UserVO();
            BeanUtils.copyProperties(user, vo);
            vo.setRoleName(getRoleName(user.getRole()));
            return vo;
        }).collect(Collectors.toList());

        return new PageInfo<>(userVOs);
    }

    @Override
    public UserDetailVO getUserDetail(Long userId) {
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }

        UserDetailVO vo = new UserDetailVO();
        BeanUtils.copyProperties(user, vo);

        vo.setOrderCount(orderMapper.countByUserId(userId));
        vo.setPostCount(postMapper.countByUserId(userId));
        vo.setRecentCreditRecords(creditRecordMapper.selectRecentByUserId(userId, 5));

        return vo;
    }

    @Override
    @Transactional
    public void updateUserStatus(AdminOperationDTO operationDTO) {
        User user = userMapper.selectById(operationDTO.getUserId());
        if (user == null) {
            throw new BusinessException("用户不存在");
        }

        int newStatus = "freeze".equals(operationDTO.getOperation()) ? 0 : 1;
        user.setStatus(newStatus);
        userMapper.updateById(user);

        OperationLog log = new OperationLog();
        log.setOperatorId(operationDTO.getOperatorId() != null ?
                operationDTO.getOperatorId() : SecurityUtils.getCurrentUserId());
        log.setOperation(operationDTO.getOperation());
        log.setTargetId(operationDTO.getUserId());
        log.setTargetType("user");
        log.setReason(operationDTO.getReason());
        operationLogMapper.insert(log);
    }

    @Override
    @Transactional
    public void batchUpdateUserStatus(BatchUserStatusDTO batchDTO) {
        userMapper.batchUpdateStatus(batchDTO.getUserIds(), batchDTO.getStatus());

        OperationLog log = new OperationLog();
        log.setOperatorId(SecurityUtils.getCurrentUserId());
        log.setOperation("batch_update_status");
        log.setTargetType("user");
        log.setReason(batchDTO.getReason());
        log.setDetails("批量更新用户状态为：" + batchDTO.getStatus() +
                "，用户IDs：" + batchDTO.getUserIds().toString());
        operationLogMapper.insert(log);
    }

    @Override
    @Transactional
    public void deleteUser(Long userId, String reason) {
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }

        user.setStatus(-1);
        user.setDeleteTime(LocalDateTime.now());
        userMapper.updateById(user);

        OperationLog log = new OperationLog();
        log.setOperatorId(SecurityUtils.getCurrentUserId());
        log.setOperation("delete");
        log.setTargetId(userId);
        log.setTargetType("user");
        log.setReason(reason);
        operationLogMapper.insert(log);
    }

    @Override
    public PageInfo<MerchantAuditVO> getPendingMerchants(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<MerchantApply> applies = merchantApplyMapper.selectPending();

        List<MerchantAuditVO> vos = applies.stream().map(apply -> {
            MerchantAuditVO vo = new MerchantAuditVO();
            BeanUtils.copyProperties(apply, vo);

            User user = userMapper.selectById(apply.getUserId());
            if (user != null) {
                vo.setUsername(user.getUsername());
            }
            return vo;
        }).collect(Collectors.toList());

        return new PageInfo<>(vos);
    }

    @Override
    @Transactional
    public void auditMerchant(MerchantAuditDTO auditDTO) {
        MerchantApply apply = merchantApplyMapper.selectByUserId(auditDTO.getUserId());
        if (apply == null) {
            throw new BusinessException("未找到商家申请");
        }

        apply.setStatus(auditDTO.getApprove());
        apply.setAuditTime(LocalDateTime.now());
        apply.setAuditRemark(auditDTO.getReason());
        merchantApplyMapper.updateById(apply);

        if (auditDTO.getApprove() == 1) {
            User user = userMapper.selectById(auditDTO.getUserId());
            user.setRole(2);
            user.setMerchantStatus(1);
            userMapper.updateById(user);
        }

        OperationLog log = new OperationLog();
        log.setOperatorId(SecurityUtils.getCurrentUserId());
        log.setOperation("merchant_audit");
        log.setTargetId(auditDTO.getUserId());
        log.setTargetType("merchant_apply");
        log.setResult(auditDTO.getApprove() == 1 ? "通过" : "驳回");
        log.setReason(auditDTO.getReason());
        operationLogMapper.insert(log);
    }

    @Override
    public List<CredentialVO> getMerchantCredentials(Long userId) {
        return merchantApplyMapper.selectCredentials(userId);
    }

    @Override
    public PageInfo<CreditVO> getCreditList(CreditQueryDTO queryDTO) {
        PageHelper.startPage(queryDTO.getPageNum(), queryDTO.getPageSize());
        List<User> users = userMapper.selectByCreditCondition(queryDTO);

        List<CreditVO> vos = users.stream().map(user -> {
            CreditVO vo = new CreditVO();
            BeanUtils.copyProperties(user, vo);
            vo.setCreditLevel(getCreditLevel(user.getCreditScore()));
            vo.setTotalViolations(creditRecordMapper.countViolations(user.getId()));
            vo.setTotalAppeals(creditRecordMapper.countAppeals(user.getId()));
            return vo;
        }).collect(Collectors.toList());

        return new PageInfo<>(vos);
    }

    @Override
    @Transactional
    public void updateCreditScore(CreditUpdateDTO creditDTO) {
        User user = userMapper.selectById(creditDTO.getUserId());
        if (user == null) {
            throw new BusinessException("用户不存在");
        }

        int beforeScore = user.getCreditScore();
        int afterScore = beforeScore + creditDTO.getScore();

        if (afterScore < 0 || afterScore > 1000) {
            throw new BusinessException("信用分超出有效范围（0-1000）");
        }

        user.setCreditScore(afterScore);
        userMapper.updateById(user);

        CreditRecord record = new CreditRecord();
        record.setUserId(creditDTO.getUserId());
        record.setChangeScore(creditDTO.getScore());
        record.setBeforeScore(beforeScore);
        record.setAfterScore(afterScore);
        record.setReason(creditDTO.getReason());
        record.setType(creditDTO.getType() != null ? creditDTO.getType() : "manual");
        record.setOperatorId(SecurityUtils.getCurrentUserId());
        creditRecordMapper.insert(record);
    }

    @Override
    public PageInfo<CreditHistoryVO> getCreditHistory(Long userId, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<CreditRecord> records = creditRecordMapper.selectByUserId(userId);

        List<CreditHistoryVO> vos = records.stream().map(record -> {
            CreditHistoryVO vo = new CreditHistoryVO();
            BeanUtils.copyProperties(record, vo);

            if (record.getOperatorId() != null) {
                User operator = userMapper.selectById(record.getOperatorId());
                if (operator != null) {
                    vo.setOperatorName(operator.getUsername());
                }
            }
            return vo;
        }).collect(Collectors.toList());

        return new PageInfo<>(vos);
    }

    @Override
    @Transactional
    public void handleCreditAppeal(CreditAppealDTO appealDTO) {
        CreditAppeal appeal = creditRecordMapper.selectAppealById(appealDTO.getAppealId());
        if (appeal == null) {
            throw new BusinessException("申诉不存在");
        }

        appeal.setStatus(appealDTO.getResult());
        appeal.setHandleTime(LocalDateTime.now());
        appeal.setFeedback(appealDTO.getFeedback());
        appeal.setHandlerId(SecurityUtils.getCurrentUserId());
        creditRecordMapper.updateAppeal(appeal);

        if (appealDTO.getResult() == 1 && appealDTO.getAdjustScore() != null) {
            CreditUpdateDTO updateDTO = new CreditUpdateDTO();
            updateDTO.setUserId(appeal.getUserId());
            updateDTO.setScore(appealDTO.getAdjustScore());
            updateDTO.setReason("申诉处理：" + appealDTO.getFeedback());
            updateDTO.setType("appeal");
            updateCreditScore(updateDTO);
        }
    }

    @Override
    public PageInfo<ConsignmentAuditVO> getPendingConsignment(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<UserVerification> verifications = userVerificationMapper.selectPending();

        List<ConsignmentAuditVO> vos = verifications.stream().map(verification -> {
            ConsignmentAuditVO vo = new ConsignmentAuditVO();
            BeanUtils.copyProperties(verification, vo);

            User user = userMapper.selectById(verification.getUserId());
            if (user != null) {
                vo.setUsername(user.getUsername());
                vo.setNickname(user.getNickname());
            }
            return vo;
        }).collect(Collectors.toList());

        return new PageInfo<>(vos);
    }

    @Override
    @Transactional
    public void auditConsignment(ConsignmentAuditDTO auditDTO) {
        UserVerification verification = userVerificationMapper.selectByUserId(auditDTO.getUserId());
        if (verification == null) {
            throw new BusinessException("未找到认证信息");
        }

        verification.setStatus(auditDTO.getApprove());
        verification.setVerifyTime(LocalDateTime.now());
        verification.setVerifyRemark(auditDTO.getReason());
        userVerificationMapper.updateById(verification);

        if (auditDTO.getApprove() == 1) {
            User user = userMapper.selectById(auditDTO.getUserId());
            user.setConsignmentEnabled(true);
            user.setConsignmentExpiry(auditDTO.getExpiryDate());
            userMapper.updateById(user);
        }

        OperationLog log = new OperationLog();
        log.setOperatorId(SecurityUtils.getCurrentUserId());
        log.setOperation("consignment_audit");
        log.setTargetId(auditDTO.getUserId());
        log.setTargetType("user_verification");
        log.setResult(auditDTO.getApprove() == 1 ? "通过" : "驳回");
        log.setReason(auditDTO.getReason());
        operationLogMapper.insert(log);
    }

    @Override
    public UserVerificationVO getUserVerification(Long userId) {
        UserVerification verification = userVerificationMapper.selectByUserId(userId);
        if (verification == null) {
            throw new BusinessException("未找到认证信息");
        }

        UserVerificationVO vo = new UserVerificationVO();
        BeanUtils.copyProperties(verification, vo);

        if (verification.getIdNumber() != null && verification.getIdNumber().length() > 10) {
            String idNumber = verification.getIdNumber();
            vo.setIdNumber(idNumber.substring(0, 6) + "********" +
                    idNumber.substring(idNumber.length() - 4));
        }

        return vo;
    }

    @Override
    public byte[] exportUserList(UserQueryDTO queryDTO) {
        // 实现Excel导出逻辑
        return new byte[0];
    }

    @Override
    public byte[] exportCreditList(CreditQueryDTO queryDTO) {
        // 实现Excel导出逻辑
        return new byte[0];
    }

    private String getRoleName(Integer role) {
        switch (role) {
            case 1: return "普通用户";
            case 2: return "商家";
            case 3: return "管理员";
            default: return "未知";
        }
    }

    private String getCreditLevel(Integer score) {
        if (score == null) return "未知";
        if (score >= 800) return "优秀";
        if (score >= 600) return "良好";
        if (score >= 400) return "一般";
        return "较差";
    }
}