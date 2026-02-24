package com.rabbiter.hrm.service.Impl;

import com.rabbiter.hrm.dto.*;
import com.rabbiter.hrm.entity.*;
import com.rabbiter.hrm.enums.BusinessStatusEnum;
import com.rabbiter.hrm.exception.BusinessException;
import com.rabbiter.hrm.mapper.*;
import com.rabbiter.hrm.service.AdminService;
import com.rabbiter.hrm.util.ExcelExportUtil;
import com.rabbiter.hrm.util.SecurityUtils;
import com.rabbiter.hrm.vo.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private AdminPermissionMapper adminPermissionMapper;

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ReportMapper reportMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private ExchangeOrderMapper exchangeOrderMapper;

    @Autowired
    private AfterSaleMapper afterSaleMapper;

    @Autowired
    private AnnouncementMapper announcementMapper;

    @Autowired
    private PrivateMessageMapper privateMessageMapper;

    @Autowired
    private OperationLogMapper operationLogMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CreditRecordMapper creditRecordMapper;

    @Autowired
    private MerchantApplyMapper merchantApplyMapper;

    // ==================== 管理员管理 ====================

    @Override
    public PageInfo<AdminVO> getAdminList(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Admin> admins = adminMapper.selectAll();

        List<AdminVO> adminVOS = admins.stream().map(admin -> {
            AdminVO vo = new AdminVO();
            BeanUtils.copyProperties(admin, vo);

            List<String> permissions = adminPermissionMapper.selectPermissionsByAdminId(admin.getId());
            vo.setPermissions(permissions);

            return vo;
        }).collect(Collectors.toList());

        return new PageInfo<>(adminVOS);
    }

    @Override
    @Transactional
    public void assignPermissions(Long adminId, List<String> permissions) {
        adminPermissionMapper.deleteByAdminId(adminId);

        for (String permissionCode : permissions) {
            Permission permission = permissionMapper.selectByCode(permissionCode);
            if (permission != null) {
                AdminPermission adminPermission = new AdminPermission();
                adminPermission.setAdminId(adminId);
                adminPermission.setPermissionId(permission.getId());
                adminPermission.setCreateTime(LocalDateTime.now());
                adminPermissionMapper.insert(adminPermission);
            }
        }

        // 记录操作日志
        saveOperationLog("assign_permissions", adminId, "admin",
                "分配权限：" + permissions.toString(), "success", null);
    }

    @Override
    public List<PermissionVO> getAllPermissions() {
        List<Permission> permissions = permissionMapper.selectAll();

        // 构建树形结构
        List<PermissionVO> parentList = permissions.stream()
                .filter(p -> p.getParentCode() == null || p.getParentCode().isEmpty())
                .map(this::convertToPermissionVO)
                .collect(Collectors.toList());

        for (PermissionVO parent : parentList) {
            List<PermissionVO> children = permissions.stream()
                    .filter(p -> parent.getCode().equals(p.getParentCode()))
                    .map(this::convertToPermissionVO)
                    .collect(Collectors.toList());
            parent.setChildren(children);
        }

        return parentList;
    }

    @Override
    @Transactional
    public void createSubAdmin(CreateAdminDTO createDTO) {
        Admin existAdmin = adminMapper.selectByUsername(createDTO.getUsername());
        if (existAdmin != null) {
            throw new BusinessException(BusinessStatusEnum.USER_EXISTS.getCode(), "用户名已存在");
        }

        Admin admin = new Admin();
        BeanUtils.copyProperties(createDTO, admin);
        admin.setPassword(createDTO.getPassword());
        admin.setRole(2); // 普通管理员
        admin.setStatus(1);
        admin.setCreateTime(LocalDateTime.now());
        admin.setCreateBy(SecurityUtils.getCurrentUserId());

        adminMapper.insert(admin);

        if (createDTO.getPermissions() != null && !createDTO.getPermissions().isEmpty()) {
            assignPermissions(admin.getId(), createDTO.getPermissions());
        }

        saveOperationLog("create_admin", admin.getId(), "admin",
                "创建子管理员：" + admin.getUsername(), "success", null);
    }

    @Override
    public AdminVO getAdminDetail(Long adminId) {
        Admin admin = adminMapper.selectById(adminId);
        if (admin == null) {
            throw new BusinessException(BusinessStatusEnum.USER_NOT_FOUND.getCode(), "管理员不存在");
        }

        AdminVO vo = new AdminVO();
        BeanUtils.copyProperties(admin, vo);

        List<String> permissions = adminPermissionMapper.selectPermissionsByAdminId(adminId);
        vo.setPermissions(permissions);

        return vo;
    }

    @Override
    public void updateAdminStatus(Long adminId, Integer status) {
        Admin admin = adminMapper.selectById(adminId);
        if (admin == null) {
            throw new BusinessException(BusinessStatusEnum.USER_NOT_FOUND.getCode(), "管理员不存在");
        }

        admin.setStatus(status);
        admin.setUpdateTime(LocalDateTime.now());
        admin.setUpdateBy(SecurityUtils.getCurrentUserId());
        adminMapper.updateById(admin);

        saveOperationLog("update_admin_status", adminId, "admin",
                "更新管理员状态为：" + status, "success", null);
    }

    @Override
    @Transactional
    public void deleteAdmin(Long adminId) {
        if (adminId.equals(SecurityUtils.getCurrentUserId())) {
            throw new BusinessException(BusinessStatusEnum.FORBIDDEN.getCode(), "不能删除当前登录的管理员");
        }

        adminMapper.deleteById(adminId);
        adminPermissionMapper.deleteByAdminId(adminId);

        saveOperationLog("delete_admin", adminId, "admin", "删除管理员", "success", null);
    }

    // ==================== 内容管理 ====================

    @Override
    public PageInfo<PostVO> getPostList(Integer pageNum, Integer pageSize, String keyword, Integer status, String category) {
        PageHelper.startPage(pageNum, pageSize);
        List<Post> posts = postMapper.selectByCondition(keyword, status, category);

        List<PostVO> vos = posts.stream().map(post -> {
            PostVO vo = new PostVO();
            BeanUtils.copyProperties(post, vo);

            User user = userMapper.selectById(post.getUserId());
            if (user != null) {
                vo.setUsername(user.getUsername());
                vo.setNickname(user.getNickname());
            }

            // 内容摘要
            if (post.getContent() != null && post.getContent().length() > 100) {
                vo.setSummary(post.getContent().substring(0, 100) + "...");
            } else {
                vo.setSummary(post.getContent());
            }

            return vo;
        }).collect(Collectors.toList());

        return new PageInfo<>(vos);
    }

    @Override
    @Transactional
    public void auditPost(Long id, Integer status, String reason) {
        Post post = postMapper.selectById(id);
        if (post == null) {
            throw new BusinessException(BusinessStatusEnum.NOT_FOUND.getCode(), "帖子不存在");
        }

        post.setStatus(status);
        post.setAuditRemark(reason);
        post.setAuditTime(LocalDateTime.now());
        postMapper.updateById(post);

        saveOperationLog("audit_post", id, "post",
                "审核帖子，结果：" + (status == 1 ? "通过" : "屏蔽") + "，原因：" + reason,
                "success", null);
    }

    @Override
    @Transactional
    public void setPostEssence(Long id, Boolean isEssence) {
        Post post = postMapper.selectById(id);
        if (post == null) {
            throw new BusinessException(BusinessStatusEnum.NOT_FOUND.getCode(), "帖子不存在");
        }

        post.setIsEssence(isEssence);
        post.setUpdateTime(LocalDateTime.now());
        postMapper.updateById(post);

        saveOperationLog("set_post_essence", id, "post",
                (isEssence ? "设为精华" : "取消精华"), "success", null);
    }

    @Override
    @Transactional
    public void setPostTop(Long id, Boolean isTop) {
        Post post = postMapper.selectById(id);
        if (post == null) {
            throw new BusinessException(BusinessStatusEnum.NOT_FOUND.getCode(), "帖子不存在");
        }

        post.setIsTop(isTop);
        post.setUpdateTime(LocalDateTime.now());
        postMapper.updateById(post);

        saveOperationLog("set_post_top", id, "post",
                (isTop ? "设为置顶" : "取消置顶"), "success", null);
    }

    @Override
    public PageInfo<CommentVO> getCommentList(Integer pageNum, Integer pageSize, Long postId, Integer status) {
        PageHelper.startPage(pageNum, pageSize);
        List<Comment> comments = commentMapper.selectByCondition(postId, status);

        List<CommentVO> vos = comments.stream().map(comment -> {
            CommentVO vo = new CommentVO();
            BeanUtils.copyProperties(comment, vo);

            Post post = postMapper.selectById(comment.getPostId());
            if (post != null) {
                vo.setPostTitle(post.getTitle());
            }

            User user = userMapper.selectById(comment.getUserId());
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
    public void blockComment(Long id, String reason) {
        Comment comment = commentMapper.selectById(id);
        if (comment == null) {
            throw new BusinessException(BusinessStatusEnum.NOT_FOUND.getCode(), "评论不存在");
        }

        comment.setStatus(1); // 屏蔽
        commentMapper.updateById(comment);

        saveOperationLog("block_comment", id, "comment", "屏蔽评论，原因：" + reason, "success", null);
    }

    @Override
    public List<CategoryVO> getCategoryList() {
        List<Category> categories = categoryMapper.selectAll();

        return categories.stream().map(category -> {
            CategoryVO vo = new CategoryVO();
            BeanUtils.copyProperties(category, vo);

            int postCount = categoryMapper.countPosts(category.getId());
            vo.setPostCount(postCount);

            return vo;
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void addCategory(CategoryDTO categoryDTO) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO, category);
        category.setCreateTime(LocalDateTime.now());

        categoryMapper.insert(category);

        saveOperationLog("add_category", category.getId(), "category",
                "新增分区：" + category.getName(), "success", null);
    }

    @Override
    @Transactional
    public void updateCategory(CategoryDTO categoryDTO) {
        Category category = categoryMapper.selectById(categoryDTO.getId());
        if (category == null) {
            throw new BusinessException(BusinessStatusEnum.NOT_FOUND.getCode(), "分区不存在");
        }

        BeanUtils.copyProperties(categoryDTO, category);
        category.setUpdateTime(LocalDateTime.now());
        categoryMapper.updateById(category);

        saveOperationLog("update_category", category.getId(), "category",
                "修改分区：" + category.getName(), "success", null);
    }

    @Override
    @Transactional
    public void deleteCategory(Long id) {
        Category category = categoryMapper.selectById(id);
        if (category == null) {
            throw new BusinessException(BusinessStatusEnum.NOT_FOUND.getCode(), "分区不存在");
        }

        int postCount = categoryMapper.countPosts(id);
        if (postCount > 0) {
            throw new BusinessException(BusinessStatusEnum.FORBIDDEN.getCode(), "该分区下还有帖子，无法删除");
        }

        categoryMapper.deleteById(id);

        saveOperationLog("delete_category", id, "category", "删除分区：" + category.getName(), "success", null);
    }

    @Override
    public PageInfo<ProductVO> getPendingProducts(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Product> products = productMapper.selectByStatus(0); // 待审核

        List<ProductVO> vos = products.stream().map(product -> {
            ProductVO vo = new ProductVO();
            BeanUtils.copyProperties(product, vo);

            User user = userMapper.selectById(product.getUserId());
            if (user != null) {
                vo.setUsername(user.getUsername());
                vo.setUserType(user.getRole() == 2 ? 1 : 2);
            }

            return vo;
        }).collect(Collectors.toList());

        return new PageInfo<>(vos);
    }

    @Override
    @Transactional
    public void auditProduct(Long id, Integer status, String reason) {
        Product product = productMapper.selectById(id);
        if (product == null) {
            throw new BusinessException(BusinessStatusEnum.NOT_FOUND.getCode(), "商品不存在");
        }

        product.setStatus(status);
        product.setAuditRemark(reason);
        product.setAuditTime(LocalDateTime.now());
        productMapper.updateById(product);

        saveOperationLog("audit_product", id, "product",
                "审核商品，结果：" + (status == 1 ? "通过" : "违规下架") + "，原因：" + reason,
                "success", null);
    }

    @Override
    public PageInfo<ReportVO> getReportList(Integer pageNum, Integer pageSize, Integer status, Integer targetType) {
        PageHelper.startPage(pageNum, pageSize);
        List<Report> reports = reportMapper.selectByCondition(status, targetType);

        List<ReportVO> vos = reports.stream().map(report -> {
            ReportVO vo = new ReportVO();
            BeanUtils.copyProperties(report, vo);

            User reporter = userMapper.selectById(report.getReporterId());
            if (reporter != null) {
                vo.setReporterName(reporter.getUsername());
            }

            User reported = userMapper.selectById(report.getReportedUserId());
            if (reported != null) {
                vo.setReportedUserName(reported.getUsername());
            }

            // 获取目标标题
            if (report.getTargetType() == 1) { // 帖子
                Post post = postMapper.selectById(report.getTargetId());
                if (post != null) {
                    vo.setTargetTitle(post.getTitle());
                }
            } else if (report.getTargetType() == 2) { // 评论
                Comment comment = commentMapper.selectById(report.getTargetId());
                if (comment != null) {
                    vo.setTargetTitle(comment.getContent());
                }
            } else if (report.getTargetType() == 3) { // 商品
                Product product = productMapper.selectById(report.getTargetId());
                if (product != null) {
                    vo.setTargetTitle(product.getName());
                }
            }

            return vo;
        }).collect(Collectors.toList());

        return new PageInfo<>(vos);
    }

    @Override
    @Transactional
    public void handleReport(ReportHandleDTO handleDTO) {
        Report report = reportMapper.selectById(handleDTO.getReportId());
        if (report == null) {
            throw new BusinessException(BusinessStatusEnum.NOT_FOUND.getCode(), "举报不存在");
        }

        report.setStatus(handleDTO.getResult() == 1 ? 1 : 2); // 1-已处理 2-驳回
        report.setHandleResult(handleDTO.getFeedback());
        report.setHandleTime(LocalDateTime.now());
        report.setHandlerId(SecurityUtils.getCurrentUserId());
        reportMapper.updateById(report);

        // 如果确认违规，进行处罚
        if (handleDTO.getResult() == 1) {
            // 扣信用分
            if (handleDTO.getDeductCredit() > 0) {
                CreditUpdateDTO creditDTO = new CreditUpdateDTO();
                creditDTO.setUserId(report.getReportedUserId());
                creditDTO.setScore(-handleDTO.getDeductCredit());
                creditDTO.setReason("举报违规：" + handleDTO.getFeedback());
                creditDTO.setType("violation");
                updateCreditScore(creditDTO);
            }

            // 禁言/封号等操作（需要根据实际业务实现）
            if (handleDTO.getMuteUser()) {
                // 禁言逻辑
            }
            if (handleDTO.getBanUser()) {
                // 封号逻辑
                User user = userMapper.selectById(report.getReportedUserId());
                if (user != null) {
                    user.setStatus(-1);
                    userMapper.updateById(user);
                }
            }
        }

        saveOperationLog("handle_report", handleDTO.getReportId(), "report",
                "处理举报，结果：" + (handleDTO.getResult() == 1 ? "确认违规" : "驳回") +
                        "，反馈：" + handleDTO.getFeedback(), "success", null);
    }

    // ==================== 交易管理 ====================

    @Override
    public PageInfo<OrderVO> getOrderList(OrderQueryDTO queryDTO) {
        PageHelper.startPage(queryDTO.getPageNum(), queryDTO.getPageSize());
        List<Order> orders = orderMapper.selectByCondition(queryDTO);

        List<OrderVO> vos = orders.stream().map(order -> {
            OrderVO vo = new OrderVO();
            BeanUtils.copyProperties(order, vo);

            User buyer = userMapper.selectById(order.getBuyerId());
            if (buyer != null) {
                vo.setBuyerName(buyer.getUsername());
            }

            User seller = userMapper.selectById(order.getSellerId());
            if (seller != null) {
                vo.setSellerName(seller.getUsername());
            }

            return vo;
        }).collect(Collectors.toList());

        return new PageInfo<>(vos);
    }

    @Override
    public OrderDetailVO getOrderDetail(Long id) {
        Order order = orderMapper.selectById(id);
        if (order == null) {
            throw new BusinessException(BusinessStatusEnum.ORDER_NOT_FOUND.getCode(), "订单不存在");
        }

        OrderDetailVO vo = new OrderDetailVO();
        BeanUtils.copyProperties(order, vo);

        User buyer = userMapper.selectById(order.getBuyerId());
        if (buyer != null) {
            vo.setBuyerName(buyer.getUsername());
        }

        User seller = userMapper.selectById(order.getSellerId());
        if (seller != null) {
            vo.setSellerName(seller.getUsername());
        }

        return vo;
    }

    @Override
    public PageInfo<AfterSaleVO> getAfterSaleList(Integer pageNum, Integer pageSize, Integer status) {
        PageHelper.startPage(pageNum, pageSize);
        List<AfterSale> afterSales = afterSaleMapper.selectByStatus(status);

        List<AfterSaleVO> vos = afterSales.stream().map(afterSale -> {
            AfterSaleVO vo = new AfterSaleVO();
            BeanUtils.copyProperties(afterSale, vo);

            Order order = orderMapper.selectById(afterSale.getOrderId());
            if (order != null) {
                vo.setOrderNo(order.getOrderNo());
            }

            User user = userMapper.selectById(afterSale.getUserId());
            if (user != null) {
                vo.setUserName(user.getUsername());
            }

            return vo;
        }).collect(Collectors.toList());

        return new PageInfo<>(vos);
    }

    @Override
    @Transactional
    public void arbitrateAfterSale(ArbitrationDTO arbitrationDTO) {
        AfterSale afterSale = afterSaleMapper.selectById(arbitrationDTO.getAfterSaleId());
        if (afterSale == null) {
            throw new BusinessException(BusinessStatusEnum.NOT_FOUND.getCode(), "售后单不存在");
        }

        afterSale.setStatus(4); // 已完成
        afterSale.setArbitrationResult(arbitrationDTO.getReason());
        afterSale.setArbitrationTime(LocalDateTime.now());
        afterSale.setCompleteTime(LocalDateTime.now());
        afterSaleMapper.updateById(afterSale);

        // 信用分奖惩
        if (arbitrationDTO.getBuyerCreditChange() != 0) {
            CreditUpdateDTO buyerCredit = new CreditUpdateDTO();
            buyerCredit.setUserId(afterSale.getUserId());
            buyerCredit.setScore(arbitrationDTO.getBuyerCreditChange());
            buyerCredit.setReason("售后仲裁：" + arbitrationDTO.getReason());
            buyerCredit.setType("arbitration");
            updateCreditScore(buyerCredit);
        }

        Order order = orderMapper.selectById(afterSale.getOrderId());
        if (order != null && arbitrationDTO.getSellerCreditChange() != 0) {
            CreditUpdateDTO sellerCredit = new CreditUpdateDTO();
            sellerCredit.setUserId(order.getSellerId());
            sellerCredit.setScore(arbitrationDTO.getSellerCreditChange());
            sellerCredit.setReason("售后仲裁：" + arbitrationDTO.getReason());
            sellerCredit.setType("arbitration");
            updateCreditScore(sellerCredit);
        }

        saveOperationLog("arbitrate_after_sale", arbitrationDTO.getAfterSaleId(), "after_sale",
                "售后仲裁，结果：" + arbitrationDTO.getReason(), "success", null);
    }

    @Override
    public PageInfo<ExchangeOrderVO> getExchangeOrderList(Integer pageNum, Integer pageSize, String exchangeNo, Integer status) {
        PageHelper.startPage(pageNum, pageSize);
        List<ExchangeOrder> exchangeOrders = exchangeOrderMapper.selectByCondition(exchangeNo, status);

        List<ExchangeOrderVO> vos = exchangeOrders.stream().map(exchangeOrder -> {
            ExchangeOrderVO vo = new ExchangeOrderVO();
            BeanUtils.copyProperties(exchangeOrder, vo);

            User initiator = userMapper.selectById(exchangeOrder.getInitiatorId());
            if (initiator != null) {
                vo.setInitiatorName(initiator.getUsername());
            }

            User receiver = userMapper.selectById(exchangeOrder.getReceiverId());
            if (receiver != null) {
                vo.setReceiverName(receiver.getUsername());
            }

            return vo;
        }).collect(Collectors.toList());

        return new PageInfo<>(vos);
    }

    // ==================== 系统管理 ====================

    @Override
    public PageInfo<AnnouncementVO> getAnnouncementList(Integer pageNum, Integer pageSize, Integer type, Integer status) {
        PageHelper.startPage(pageNum, pageSize);
        List<Announcement> announcements = announcementMapper.selectByCondition(type, status);

        List<AnnouncementVO> vos = announcements.stream().map(announcement -> {
            AnnouncementVO vo = new AnnouncementVO();
            BeanUtils.copyProperties(announcement, vo);
            return vo;
        }).collect(Collectors.toList());

        return new PageInfo<>(vos);
    }

    @Override
    @Transactional
    public void publishAnnouncement(AnnouncementDTO announcementDTO) {
        Announcement announcement = new Announcement();
        BeanUtils.copyProperties(announcementDTO, announcement);

        if (announcementDTO.getStatus() == 1 && announcementDTO.getPublishTime() == null) {
            announcement.setPublishTime(LocalDateTime.now());
        }

        announcement.setCreateTime(LocalDateTime.now());
        announcementMapper.insert(announcement);

        saveOperationLog("publish_announcement", announcement.getId(), "announcement",
                "发布公告：" + announcement.getTitle(), "success", null);
    }

    @Override
    @Transactional
    public void updateAnnouncement(AnnouncementDTO announcementDTO) {
        Announcement announcement = announcementMapper.selectById(announcementDTO.getId());
        if (announcement == null) {
            throw new BusinessException(BusinessStatusEnum.NOT_FOUND.getCode(), "公告不存在");
        }

        BeanUtils.copyProperties(announcementDTO, announcement);
        announcement.setUpdateTime(LocalDateTime.now());

        if (announcementDTO.getStatus() == 1 && announcement.getPublishTime() == null) {
            announcement.setPublishTime(LocalDateTime.now());
        }

        announcementMapper.updateById(announcement);

        saveOperationLog("update_announcement", announcement.getId(), "announcement",
                "修改公告：" + announcement.getTitle(), "success", null);
    }

    @Override
    @Transactional
    public void deleteAnnouncement(Long id) {
        Announcement announcement = announcementMapper.selectById(id);
        if (announcement == null) {
            throw new BusinessException(BusinessStatusEnum.NOT_FOUND.getCode(), "公告不存在");
        }

        announcementMapper.deleteById(id);

        saveOperationLog("delete_announcement", id, "announcement",
                "删除公告：" + announcement.getTitle(), "success", null);
    }

    @Override
    public PageInfo<PrivateMessageVO> getMessageList(Integer pageNum, Integer pageSize, Long senderId,
                                                     Long receiverId, LocalDateTime startDate, LocalDateTime endDate) {
        PageHelper.startPage(pageNum, pageSize);
        List<PrivateMessage> messages = privateMessageMapper.selectByCondition(senderId, receiverId, startDate, endDate);

        List<PrivateMessageVO> vos = messages.stream().map(message -> {
            PrivateMessageVO vo = new PrivateMessageVO();
            BeanUtils.copyProperties(message, vo);

            User sender = userMapper.selectById(message.getSenderId());
            if (sender != null) {
                vo.setSenderName(sender.getUsername());
            }

            User receiver = userMapper.selectById(message.getReceiverId());
            if (receiver != null) {
                vo.setReceiverName(receiver.getUsername());
            }

            // 内容摘要
            if (message.getContent() != null && message.getContent().length() > 50) {
                vo.setContentPreview(message.getContent().substring(0, 50) + "...");
            } else {
                vo.setContentPreview(message.getContent());
            }

            return vo;
        }).collect(Collectors.toList());

        return new PageInfo<>(vos);
    }

    @Override
    public PrivateMessageDetailVO getMessageDetail(Long id) {
        PrivateMessage message = privateMessageMapper.selectById(id);
        if (message == null) {
            throw new BusinessException(BusinessStatusEnum.NOT_FOUND.getCode(), "消息不存在");
        }

        PrivateMessageDetailVO vo = new PrivateMessageDetailVO();
        BeanUtils.copyProperties(message, vo);

        User sender = userMapper.selectById(message.getSenderId());
        if (sender != null) {
            vo.setSenderName(sender.getUsername());
            vo.setSenderAvatar(sender.getAvatar());
        }

        User receiver = userMapper.selectById(message.getReceiverId());
        if (receiver != null) {
            vo.setReceiverName(receiver.getUsername());
            vo.setReceiverAvatar(receiver.getAvatar());
        }

        return vo;
    }

    @Override
    public PageInfo<OperationLogVO> getOperationLogs(LogQueryDTO queryDTO) {
        PageHelper.startPage(queryDTO.getPageNum(), queryDTO.getPageSize());
        List<OperationLog> logs = operationLogMapper.selectByCondition(
                queryDTO.getOperatorId(),
                queryDTO.getOperation(),
                queryDTO.getTargetType(),
                queryDTO.getStartDate(),
                queryDTO.getEndDate()
        );

        List<OperationLogVO> vos = logs.stream().map(log -> {
            OperationLogVO vo = new OperationLogVO();
            BeanUtils.copyProperties(log, vo);

            // 操作类型名称转换
            vo.setOperationName(getOperationName(log.getOperation()));

            return vo;
        }).collect(Collectors.toList());

        return new PageInfo<>(vos);
    }

    // ==================== 私有方法 ====================

    /**
     * 保存操作日志
     */
    private void saveOperationLog(String operation, Long targetId, String targetType,
                                  String details, String result, String reason) {
        OperationLog log = new OperationLog();
        log.setOperatorId(SecurityUtils.getCurrentUserId());
        log.setOperatorName(SecurityUtils.getCurrentAdmin() != null ?
                SecurityUtils.getCurrentAdmin().getUsername() : "system");
        log.setOperation(operation);
        log.setTargetId(targetId);
        log.setTargetType(targetType);
        log.setDetails(details);
        log.setResult(result);
        log.setReason(reason);
        log.setIpAddress(SecurityUtils.getClientIp());
        log.setUserAgent(SecurityUtils.getUserAgent());
        log.setCreateTime(LocalDateTime.now());

        operationLogMapper.insert(log);
    }

    /**
     * 更新信用分
     */
    @Transactional
    public void updateCreditScore(CreditUpdateDTO creditDTO) {
        User user = userMapper.selectById(creditDTO.getUserId());
        if (user == null) {
            throw new BusinessException(BusinessStatusEnum.USER_NOT_FOUND.getCode(), "用户不存在");
        }

        int beforeScore = user.getCreditScore();
        int afterScore = beforeScore + creditDTO.getScore();

        if (afterScore < 0 || afterScore > 1000) {
            throw new BusinessException(BusinessStatusEnum.CREDIT_OUT_OF_RANGE.getCode(), "信用分超出有效范围");
        }

        user.setCreditScore(afterScore);
        userMapper.updateById(user);

        CreditRecord record = new CreditRecord();
        record.setUserId(creditDTO.getUserId());
        record.setChangeScore(creditDTO.getScore());
        record.setBeforeScore(beforeScore);
        record.setAfterScore(afterScore);
        record.setReason(creditDTO.getReason());
        record.setType(creditDTO.getType());
        record.setOperatorId(SecurityUtils.getCurrentUserId());
        record.setCreateTime(LocalDateTime.now());
        creditRecordMapper.insert(record);
    }

    /**
     * 转换为PermissionVO
     */
    private PermissionVO convertToPermissionVO(Permission permission) {
        PermissionVO vo = new PermissionVO();
        BeanUtils.copyProperties(permission, vo);
        return vo;
    }

    /**
     * 获取操作名称
     */
    private String getOperationName(String operation) {
        switch (operation) {
            case "assign_permissions": return "分配权限";
            case "create_admin": return "创建管理员";
            case "update_admin_status": return "更新管理员状态";
            case "delete_admin": return "删除管理员";
            case "audit_post": return "审核帖子";
            case "set_post_essence": return "设置精华";
            case "set_post_top": return "设置置顶";
            case "block_comment": return "屏蔽评论";
            case "add_category": return "新增分区";
            case "update_category": return "修改分区";
            case "delete_category": return "删除分区";
            case "audit_product": return "审核商品";
            case "handle_report": return "处理举报";
            case "arbitrate_after_sale": return "售后仲裁";
            case "publish_announcement": return "发布公告";
            case "update_announcement": return "修改公告";
            case "delete_announcement": return "删除公告";
            default: return operation;
        }
    }

    // ==================== 数据统计实现 ====================

    @Override
    public StatisticsOverviewVO getStatisticsOverview() {
        StatisticsOverviewVO vo = new StatisticsOverviewVO();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime todayStart = now.toLocalDate().atStartOfDay();
        LocalDateTime weekStart = now.minusWeeks(1);
        LocalDateTime monthStart = now.minusMonths(1);

        // 用户相关统计
        vo.setTotalUsers(userMapper.countByCondition(null, null, null));
        vo.setTodayNewUsers(userMapper.countByCondition(null, null, null));
        vo.setActiveUsersToday(orderMapper.countDistinctUsersByDate(todayStart, now));
        vo.setActiveUsersWeek(orderMapper.countDistinctUsersByDate(weekStart, now));
        vo.setActiveUsersMonth(orderMapper.countDistinctUsersByDate(monthStart, now));

        // 商家相关统计
        vo.setTotalMerchants(userMapper.countByCondition(null, 2, null));
        vo.setPendingMerchants(merchantApplyMapper.countPending());
        vo.setVerifiedMerchants(userMapper.countByMerchantStatus(1));

        // 商品相关统计
        vo.setTotalProducts(productMapper.countByCondition(null, null, null, null));
        vo.setPendingProducts(productMapper.countPending());
        vo.setOnSaleProducts(productMapper.countByStatus(1));

        // 订单相关统计
        vo.setTotalOrders(orderMapper.countAll());
        vo.setTodayOrders(orderMapper.countByDate(todayStart, now));
        vo.setPendingOrders(orderMapper.countByStatus(0)); // 待付款
        vo.setCompletedOrders(orderMapper.countByStatus(3)); // 已完成

        // 交易金额统计
        vo.setTotalAmount(orderMapper.sumAmountByStatus(3)); // 已完成订单总金额
        vo.setTodayAmount(orderMapper.sumAmountByDate(todayStart, now));
        vo.setWeekAmount(orderMapper.sumAmountByDate(weekStart, now));
        vo.setMonthAmount(orderMapper.sumAmountByDate(monthStart, now));

        // 社区相关统计
        vo.setTotalPosts(postMapper.countAll());
        vo.setTodayPosts(postMapper.countByDate(todayStart, now));
        vo.setPendingPosts(postMapper.countPending());
        vo.setTotalComments(commentMapper.countAll());

        // 待处理事项
        vo.setPendingReports(reportMapper.countPending());
        vo.setPendingAfterSales(afterSaleMapper.countPending());
        vo.setPendingCreditAppeals(creditRecordMapper.countPendingAppeals());

        // 计算增长率（与上期对比）
        Map<String, Double> growthRates = calculateGrowthRates();
        vo.setGrowthRates(growthRates);

        return vo;
    }

    @Override
    public UserStatisticsVO getUserStatistics(LocalDateTime startDate, LocalDateTime endDate, String groupBy) {
        UserStatisticsVO vo = new UserStatisticsVO();

        // 用户总量统计
        vo.setTotalUsers(userMapper.countByCondition(null, null, null));
        vo.setActiveUsers(userMapper.countActiveUsers());
        vo.setFrozenUsers(userMapper.countByStatus(0));
        vo.setDeletedUsers(userMapper.countByStatus(-1));

        // 角色分布
        Map<String, Integer> roleDist = new HashMap<>();
        roleDist.put("普通用户", userMapper.countByRole(1));
        roleDist.put("商家", userMapper.countByRole(2));
        roleDist.put("管理员", userMapper.countByRole(3));
        vo.setRoleDistribution(roleDist);

        // 时间趋势数据
        List<UserStatisticsVO.TimeSeriesData> trend = new ArrayList<>();
        List<Map<String, Object>> statistics = userMapper.statisticsByDateRange(startDate, endDate, groupBy);
        for (Map<String, Object> stat : statistics) {
            UserStatisticsVO.TimeSeriesData data = new UserStatisticsVO.TimeSeriesData();
            data.setTime(stat.get("date").toString());
            data.setNewUsers(((Integer) stat.get("new_count")));
            data.setActiveUsers(((Integer) stat.get("active_count")));
            trend.add(data);
        }
        vo.setTrend(trend);

        return vo;
    }

    @Override
    public OrderStatisticsVO getOrderStatistics(LocalDateTime startDate, LocalDateTime endDate, String groupBy) {
        OrderStatisticsVO vo = new OrderStatisticsVO();

        // 订单总量统计
        vo.setTotalOrders(orderMapper.countByDateRange(startDate, endDate));
        vo.setCompletedOrders(orderMapper.countByStatusAndDate(3, startDate, endDate));
        vo.setCancelledOrders(orderMapper.countByStatusAndDate(6, startDate, endDate));
        vo.setRefundingOrders(orderMapper.countByStatusAndDate(4, startDate, endDate));

        // 金额统计
        vo.setTotalAmount(orderMapper.sumAmountByDateRange(startDate, endDate));
        vo.setAvgAmount(orderMapper.avgAmountByDateRange(startDate, endDate));
        vo.setMaxAmount(orderMapper.maxAmountByDateRange(startDate, endDate));

        // 订单类型分布
        Map<String, Integer> typeDist = new HashMap<>();
        typeDist.put("销售订单", orderMapper.countByTypeAndDate(1, startDate, endDate));
        typeDist.put("交换订单", orderMapper.countByTypeAndDate(2, startDate, endDate));
        vo.setTypeDistribution(typeDist);

        // 订单状态分布
        Map<String, Integer> statusDist = new HashMap<>();
        statusDist.put("待付款", orderMapper.countByStatusAndDate(0, startDate, endDate));
        statusDist.put("待发货", orderMapper.countByStatusAndDate(1, startDate, endDate));
        statusDist.put("待收货", orderMapper.countByStatusAndDate(2, startDate, endDate));
        statusDist.put("已完成", orderMapper.countByStatusAndDate(3, startDate, endDate));
        statusDist.put("退款中", orderMapper.countByStatusAndDate(4, startDate, endDate));
        statusDist.put("已取消", orderMapper.countByStatusAndDate(6, startDate, endDate));
        vo.setStatusDistribution(statusDist);

        // 时间趋势
        List<OrderStatisticsVO.TimeSeriesData> trend = new ArrayList<>();
        List<Map<String, Object>> statistics = orderMapper.statisticsByDateRange(startDate, endDate, groupBy);
        for (Map<String, Object> stat : statistics) {
            OrderStatisticsVO.TimeSeriesData data = new OrderStatisticsVO.TimeSeriesData();
            data.setTime(stat.get("date").toString());
            data.setOrderCount(((Integer) stat.get("order_count")));
            data.setAmount(new BigDecimal(stat.get("amount").toString()));
            trend.add(data);
        }
        vo.setTrend(trend);

        return vo;
    }

    @Override
    public AmountStatisticsVO getAmountStatistics(LocalDateTime startDate, LocalDateTime endDate, String groupBy) {
        AmountStatisticsVO vo = new AmountStatisticsVO();

        // 汇总统计
        vo.setTotalAmount(orderMapper.sumAmountByDateRange(startDate, endDate));

        // 计算同比环比
        LocalDateTime previousPeriodStart = calculatePreviousPeriodStart(startDate, endDate);
        LocalDateTime previousPeriodEnd = startDate.minusNanos(1);

        BigDecimal previousAmount = orderMapper.sumAmountByDateRange(previousPeriodStart, previousPeriodEnd);
        BigDecimal currentAmount = vo.getTotalAmount();

        if (previousAmount.compareTo(BigDecimal.ZERO) > 0) {
            vo.setDayOverDay(currentAmount.subtract(previousAmount)
                    .divide(previousAmount, 4, RoundingMode.HALF_UP)
                    .multiply(new BigDecimal(100)));
        }

        // 支付方式分布
        Map<String, BigDecimal> paymentDist = new HashMap<>();
        paymentDist.put("微信支付", orderMapper.sumAmountByPaymentAndDate(1, startDate, endDate));
        paymentDist.put("支付宝", orderMapper.sumAmountByPaymentAndDate(2, startDate, endDate));
        paymentDist.put("银行卡", orderMapper.sumAmountByPaymentAndDate(3, startDate, endDate));
        vo.setPaymentDistribution(paymentDist);

        // 时间趋势
        List<AmountStatisticsVO.TimeSeriesData> trend = new ArrayList<>();
        List<Map<String, Object>> statistics = orderMapper.amountStatisticsByDateRange(startDate, endDate, groupBy);
        for (Map<String, Object> stat : statistics) {
            AmountStatisticsVO.TimeSeriesData data = new AmountStatisticsVO.TimeSeriesData();
            data.setTime(stat.get("date").toString());
            data.setAmount(new BigDecimal(stat.get("amount").toString()));
            data.setOrderCount(((Number) stat.get("order_count")).longValue());
            trend.add(data);
        }
        vo.setTrend(trend);

        return vo;
    }

    @Override
    public ProductStatisticsVO getProductStatistics(LocalDateTime startDate, LocalDateTime endDate) {
        ProductStatisticsVO vo = new ProductStatisticsVO();

        // 总量统计
        vo.setTotalProducts(productMapper.countAll());
        vo.setOnSaleProducts(productMapper.countByStatus(1));
        vo.setPendingProducts(productMapper.countByStatus(0));
        vo.setSoldOutProducts(productMapper.countSoldOut());

        // 分类分布
        List<Map<String, Object>> categoryStats = productMapper.statisticsByCategory();
        Map<String, Integer> categoryDist = new HashMap<>();
        for (Map<String, Object> stat : categoryStats) {
            categoryDist.put(stat.get("category").toString(),
                    ((Integer) stat.get("count")));
        }
        vo.setCategoryDistribution(categoryDist);

        // 价格区间分布
        Map<String, Integer> priceRangeDist = new HashMap<>();
        priceRangeDist.put("0-100", productMapper.countByPriceRange(BigDecimal.ZERO, new BigDecimal(100)));
        priceRangeDist.put("100-500", productMapper.countByPriceRange(new BigDecimal(100), new BigDecimal(500)));
        priceRangeDist.put("500-1000", productMapper.countByPriceRange(new BigDecimal(500), new BigDecimal(1000)));
        priceRangeDist.put("1000-5000", productMapper.countByPriceRange(new BigDecimal(1000), new BigDecimal(5000)));
        priceRangeDist.put("5000以上", productMapper.countByPriceRange(new BigDecimal(5000), null));
        vo.setPriceRangeDistribution(priceRangeDist);

        // 卖家类型分布
        Map<String, Integer> sellerTypeDist = new HashMap<>();
        sellerTypeDist.put("商家", productMapper.countByUserType(1));
        sellerTypeDist.put("个人", productMapper.countByUserType(2));
        vo.setSellerTypeDistribution(sellerTypeDist);

        // 热门商品
        List<HotProductVO> hotProducts = new ArrayList<>();
        List<Product> products = productMapper.selectHotProducts(10);
        for (Product product : products) {
            HotProductVO hot = new HotProductVO();
            BeanUtils.copyProperties(product, hot);
            // 计算热度评分
            double popularity = product.getViewCount() * 0.3
                    + product.getSaleCount() * 0.5
                    + product.getFavoriteCount() * 0.2;
            hot.setPopularityScore(popularity);
            hotProducts.add(hot);
        }
        vo.setHotProducts(hotProducts);

        return vo;
    }

    @Override
    public CreditStatisticsVO getCreditStatistics() {
        CreditStatisticsVO vo = new CreditStatisticsVO();

        // 信用分分布
        Map<String, Integer> creditDist = new HashMap<>();
        creditDist.put("优秀(800+)", userMapper.countByCreditRange(800, 1000));
        creditDist.put("良好(600-799)", userMapper.countByCreditRange(600, 799));
        creditDist.put("一般(400-599)", userMapper.countByCreditRange(400, 599));
        creditDist.put("较差(0-399)", userMapper.countByCreditRange(0, 399));
        vo.setCreditDistribution(creditDist);

        // 统计指标
        vo.setAvgCreditScore(userMapper.avgCreditScore());
        vo.setMaxCreditScore(userMapper.maxCreditScore());
        vo.setMinCreditScore(userMapper.minCreditScore());
        vo.setTotalViolations(creditRecordMapper.countByType("violation"));

        // 信用变更类型分布
        Map<String, Integer> changeTypeDist = new HashMap<>();
        changeTypeDist.put("违规扣分", creditRecordMapper.countByType("violation"));
        changeTypeDist.put("申诉恢复", creditRecordMapper.countByType("appeal"));
        changeTypeDist.put("手动调整", creditRecordMapper.countByType("manual"));
        changeTypeDist.put("仲裁调整", creditRecordMapper.countByType("arbitration"));
        vo.setChangeTypeDistribution(changeTypeDist);

        // 信用分TopN
        List<UserCreditVO> topUsers = new ArrayList<>();
        List<User> topUserList = userMapper.selectTopCreditUsers(10);
        for (User user : topUserList) {
            UserCreditVO userVO = new UserCreditVO();
            BeanUtils.copyProperties(user, userVO);
            userVO.setCreditLevel(getCreditLevel(user.getCreditScore()));
            topUsers.add(userVO);
        }
        vo.setTopCreditUsers(topUsers);

        // 信用分BottomN
        List<UserCreditVO> bottomUsers = new ArrayList<>();
        List<User> bottomUserList = userMapper.selectBottomCreditUsers(10);
        for (User user : bottomUserList) {
            UserCreditVO userVO = new UserCreditVO();
            BeanUtils.copyProperties(user, userVO);
            userVO.setCreditLevel(getCreditLevel(user.getCreditScore()));
            bottomUsers.add(userVO);
        }
        vo.setBottomCreditUsers(bottomUsers);

        return vo;
    }
    /**
     * 获取信用等级
     * @param score 信用分
     * @return 信用等级描述
     */
    private String getCreditLevel(Integer score) {
        if (score == null) return "未知";
        if (score >= 800) return "优秀";
        if (score >= 700) return "良好";
        if (score >= 600) return "一般";
        if (score >= 400) return "较差";
        return "极差";
    }
    @Override
    public List<CategoryStatVO> getCategoryStatistics(Integer limit) {
        List<CategoryStatVO> result = new ArrayList<>();
        List<Map<String, Object>> stats = postMapper.statisticsByCategory(limit);

        for (Map<String, Object> stat : stats) {
            CategoryStatVO vo = new CategoryStatVO();
            vo.setCategoryName(stat.get("category").toString());
            vo.setPostCount(((Integer) stat.get("post_count")));
            vo.setProductCount(productMapper.countByCategory(stat.get("category").toString()));
            vo.setOrderCount(orderMapper.countByProductCategory(stat.get("category").toString()));

            // 计算占比
            Integer total = vo.getPostCount() + vo.getProductCount() + vo.getOrderCount();
            if (total > 0) {
                vo.setPercentage(total.doubleValue() / getTotalCount() * 100);
            }

            result.add(vo);
        }

        return result;
    }

// ==================== 私有辅助方法 ====================

    /**
     * 计算增长率
     */
    private Map<String, Double> calculateGrowthRates() {
        Map<String, Double> growthRates = new HashMap<>();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime todayStart = now.toLocalDate().atStartOfDay();
        LocalDateTime yesterdayStart = todayStart.minusDays(1);
        LocalDateTime lastWeekStart = now.minusWeeks(2);
        LocalDateTime lastWeekEnd = now.minusWeeks(1).minusNanos(1);

        // 用户增长率
        Integer todayUsers = userMapper.countByDate(todayStart, now);
        Integer yesterdayUsers = userMapper.countByDate(yesterdayStart, todayStart);
        growthRates.put("userGrowth", calculateGrowthRate(todayUsers, yesterdayUsers));

        // 订单增长率
        BigDecimal todayAmount = orderMapper.sumAmountByDate(todayStart, now);
        BigDecimal yesterdayAmount = orderMapper.sumAmountByDate(yesterdayStart, todayStart);
        growthRates.put("amountGrowth", calculateGrowthRate(todayAmount, yesterdayAmount));

        // 订单量增长率
        Integer todayOrders = orderMapper.countByDate(todayStart, now);
        Integer yesterdayOrders = orderMapper.countByDate(yesterdayStart, todayStart);
        growthRates.put("orderGrowth", calculateGrowthRate(todayOrders, yesterdayOrders));

        return growthRates;
    }

    /**
     * 计算增长率（整数）
     */
    private Double calculateGrowthRate(Integer current, Integer previous) {
        if (previous == null || previous == 0) {
            return current > 0 ? 100.0 : 0.0;
        }
        return ((current.doubleValue() - previous.doubleValue()) / previous.doubleValue()) * 100;
    }

    /**
     * 计算增长率（BigDecimal）
     */
    private Double calculateGrowthRate(BigDecimal current, BigDecimal previous) {
        if (previous == null || previous.compareTo(BigDecimal.ZERO) == 0) {
            return current.compareTo(BigDecimal.ZERO) > 0 ? 100.0 : 0.0;
        }
        return current.subtract(previous)
                .divide(previous, 4, RoundingMode.HALF_UP)
                .multiply(new BigDecimal(100))
                .doubleValue();
    }

    /**
     * 计算上一周期开始时间
     */
    private LocalDateTime calculatePreviousPeriodStart(LocalDateTime startDate, LocalDateTime endDate) {
        long days = ChronoUnit.DAYS.between(startDate, endDate);
        return startDate.minusDays(days + 1);
    }

    /**
     * 获取总计数（用于占比计算）
     */
    private Integer getTotalCount() {
        return postMapper.countAll() + productMapper.countAll() + orderMapper.countAll();
    }

    @Override
    public byte[] exportUsers(UserQueryDTO queryDTO) {
        List<User> users = userMapper.selectByCondition(queryDTO);

        String[] headers = {"用户ID", "用户名", "昵称", "手机号", "邮箱", "角色", "状态", "信用分", "注册时间"};
        String[] fields = {"id", "username", "nickname", "phone", "email", "role", "status", "creditScore", "createTime"};

        try {
            return ExcelExportUtil.exportObjectsToExcel("用户列表", headers, fields, users);
        } catch (Exception e) {
            log.error("导出用户列表失败", e);
            throw new BusinessException("导出失败：" + e.getMessage());
        }
    }

    @Override
    public byte[] exportOrders(OrderQueryDTO queryDTO) {
        List<Order> orders = orderMapper.selectByCondition(queryDTO);

        String[] headers = {"订单号", "买家", "卖家", "商品名称", "单价", "数量", "总金额", "订单状态", "创建时间"};
        String[] fields = {"orderNo", "buyerId", "sellerId", "productName", "price", "quantity", "totalAmount", "status", "createTime"};

        try {
            return ExcelExportUtil.exportObjectsToExcel("订单列表", headers, fields, orders);
        } catch (Exception e) {
            log.error("导出订单列表失败", e);
            throw new BusinessException("导出失败：" + e.getMessage());
        }
    }

    @Override
    public byte[] exportProducts(String keyword, String category, Integer status,
                                 LocalDateTime startDate, LocalDateTime endDate) {
        List<Product> products = productMapper.selectByCondition(keyword, category, status, null,
                null, null, null);

        String[] headers = {"商品ID", "商品名称", "卖家类型", "价格", "库存", "分类", "品牌", "状态", "销量", "上架时间"};
        String[] fields = {"id", "name", "userType", "price", "stock", "category", "brand", "status", "saleCount", "createTime"};

        try {
            return ExcelExportUtil.exportObjectsToExcel("商品列表", headers, fields, products);
        } catch (Exception e) {
            log.error("导出商品列表失败", e);
            throw new BusinessException("导出失败：" + e.getMessage());
        }
    }

    @Override
    public byte[] exportStatisticsReport(LocalDateTime startDate, LocalDateTime endDate) {
        try (Workbook workbook = new XSSFWorkbook()) {
            // 创建多个sheet
            createOverviewSheet(workbook, startDate, endDate);
            createUserStatsSheet(workbook, startDate, endDate);
            createOrderStatsSheet(workbook, startDate, endDate);
            createProductStatsSheet(workbook, startDate, endDate);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return outputStream.toByteArray();
        } catch (Exception e) {
            log.error("导出统计报表失败", e);
            throw new BusinessException("导出失败：" + e.getMessage());
        }
    }

    private void createOverviewSheet(Workbook workbook, LocalDateTime startDate, LocalDateTime endDate) {
        Sheet sheet = workbook.createSheet("数据概览");

        // 获取统计数据
        StatisticsOverviewVO overview = getStatisticsOverview();

        int rowNum = 0;
        Row titleRow = sheet.createRow(rowNum++);
        titleRow.createCell(0).setCellValue("数据统计报表");

        rowNum++; // 空行

        // 用户统计
        Row userTitleRow = sheet.createRow(rowNum++);
        userTitleRow.createCell(0).setCellValue("用户统计");

        Row[] userRows = {
                sheet.createRow(rowNum++), sheet.createRow(rowNum++), sheet.createRow(rowNum++)
        };
        userRows[0].createCell(0).setCellValue("总用户数");
        userRows[0].createCell(1).setCellValue(overview.getTotalUsers());
        userRows[1].createCell(0).setCellValue("今日新增");
        userRows[1].createCell(1).setCellValue(overview.getTodayNewUsers());
        userRows[2].createCell(0).setCellValue("活跃用户");
        userRows[2].createCell(1).setCellValue(overview.getActiveUsersToday());

        rowNum++; // 空行

        // 订单统计
        Row orderTitleRow = sheet.createRow(rowNum++);
        orderTitleRow.createCell(0).setCellValue("订单统计");

        Row[] orderRows = {
                sheet.createRow(rowNum++), sheet.createRow(rowNum++), sheet.createRow(rowNum++)
        };
        orderRows[0].createCell(0).setCellValue("总订单数");
        orderRows[0].createCell(1).setCellValue(overview.getTotalOrders());
        orderRows[1].createCell(0).setCellValue("今日订单");
        orderRows[1].createCell(1).setCellValue(overview.getTodayOrders());
        orderRows[2].createCell(0).setCellValue("总交易额");
        orderRows[2].createCell(1).setCellValue(overview.getTotalAmount().toString());
    }

    private void createUserStatsSheet(Workbook workbook, LocalDateTime startDate, LocalDateTime endDate) {
        Sheet sheet = workbook.createSheet("用户统计");
        UserStatisticsVO stats = getUserStatistics(startDate, endDate, "day");

        int rowNum = 0;
        Row titleRow = sheet.createRow(rowNum++);
        titleRow.createCell(0).setCellValue("用户统计详情");

        rowNum++; // 空行

        // 趋势数据
        Row trendTitleRow = sheet.createRow(rowNum++);
        trendTitleRow.createCell(0).setCellValue("日期");
        trendTitleRow.createCell(1).setCellValue("新增用户");
        trendTitleRow.createCell(2).setCellValue("活跃用户");

        for (UserStatisticsVO.TimeSeriesData data : stats.getTrend()) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(data.getTime());
            row.createCell(1).setCellValue(data.getNewUsers());
            row.createCell(2).setCellValue(data.getActiveUsers());
        }
    }

    private void createOrderStatsSheet(Workbook workbook, LocalDateTime startDate, LocalDateTime endDate) {
        Sheet sheet = workbook.createSheet("订单统计");
        OrderStatisticsVO stats = getOrderStatistics(startDate, endDate, "day");

        int rowNum = 0;
        Row titleRow = sheet.createRow(rowNum++);
        titleRow.createCell(0).setCellValue("订单统计详情");

        rowNum++; // 空行

        // 趋势数据
        Row trendTitleRow = sheet.createRow(rowNum++);
        trendTitleRow.createCell(0).setCellValue("日期");
        trendTitleRow.createCell(1).setCellValue("订单数");
        trendTitleRow.createCell(2).setCellValue("交易额");

        for (OrderStatisticsVO.TimeSeriesData data : stats.getTrend()) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(data.getTime());
            row.createCell(1).setCellValue(data.getOrderCount());
            row.createCell(2).setCellValue(data.getAmount().toString());
        }
    }

    private void createProductStatsSheet(Workbook workbook, LocalDateTime startDate, LocalDateTime endDate) {
        Sheet sheet = workbook.createSheet("商品统计");
        ProductStatisticsVO stats = getProductStatistics(startDate, endDate);

        int rowNum = 0;
        Row titleRow = sheet.createRow(rowNum++);
        titleRow.createCell(0).setCellValue("商品统计详情");

        rowNum++; // 空行

        // 热门商品
        Row hotTitleRow = sheet.createRow(rowNum++);
        hotTitleRow.createCell(0).setCellValue("商品名称");
        hotTitleRow.createCell(1).setCellValue("分类");
        hotTitleRow.createCell(2).setCellValue("价格");
        hotTitleRow.createCell(3).setCellValue("销量");
        hotTitleRow.createCell(4).setCellValue("热度");

        for (HotProductVO product : stats.getHotProducts()) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(product.getProductName());
            row.createCell(1).setCellValue(product.getCategory());
            row.createCell(2).setCellValue(product.getPrice().toString());
            row.createCell(3).setCellValue(product.getSaleCount());
            row.createCell(4).setCellValue(product.getPopularityScore());
        }
    }
}