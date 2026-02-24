package com.rabbiter.hrm.service.Impl;

import com.rabbiter.hrm.dto.*;
import com.rabbiter.hrm.entity.*;
import com.rabbiter.hrm.enums.BusinessStatusEnum;
import com.rabbiter.hrm.exception.BusinessException;
import com.rabbiter.hrm.mapper.*;
import com.rabbiter.hrm.service.MerchantService;
import com.rabbiter.hrm.util.SecurityUtils;
import com.rabbiter.hrm.vo.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 商家服务实现类
 */
@Slf4j
@Service
public class MerchantServiceImpl implements MerchantService {

    @Autowired
    private ShopMapper shopMapper;

    @Autowired
    private MerchantApplyMapper merchantApplyMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductSpecMapper productSpecMapper;

    @Autowired
    private CouponMapper couponMapper;

    @Autowired
    private FullReductionMapper fullReductionMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private AfterSaleMapper afterSaleMapper;

    @Autowired
    private ConsultMapper consultMapper;

    @Autowired
    private DynamicMapper dynamicMapper;

    @Autowired
    private ReviewMapper reviewMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private OperationLogMapper operationLogMapper;

    // ==================== 2.1 店铺管理 ====================

    @Override
    @Transactional
    public void applyShop(ShopApplyDTO applyDTO) {
        Long currentUserId = SecurityUtils.getCurrentUserId();

        // 检查是否已申请
        MerchantApply existApply = merchantApplyMapper.selectByUserId(currentUserId);
        if (existApply != null && existApply.getStatus() == 0) {
            throw new BusinessException(BusinessStatusEnum.FORBIDDEN.getCode(), "已有待审核的申请");
        }

        // 创建商家申请
        MerchantApply apply = new MerchantApply();
        BeanUtils.copyProperties(applyDTO, apply);
        apply.setUserId(currentUserId);
        apply.setStatus(0); // 待审核
        apply.setApplyTime(LocalDateTime.now());
        merchantApplyMapper.insert(apply);

        // 创建店铺（待审核状态）
        Shop shop = new Shop();
        shop.setMerchantId(currentUserId);
        shop.setShopName(applyDTO.getShopName());
        shop.setShopLogo(applyDTO.getShopLogo());
        shop.setShopBanner(applyDTO.getShopBanner());
        shop.setShopDesc(applyDTO.getShopDesc());
        shop.setShopAddress(applyDTO.getShopAddress());
        shop.setContactPhone(applyDTO.getContactPhone());
        shop.setContactEmail(applyDTO.getContactEmail());
        shop.setAuditStatus(0); // 待审核
        shop.setStatus(0); // 关闭状态
        shop.setCreateTime(LocalDateTime.now());
        shopMapper.insert(shop);

        // 更新用户商家状态
        User user = userMapper.selectById(currentUserId);
        user.setMerchantStatus(0); // 待审核
        userMapper.updateById(user);

        saveOperationLog("apply_shop", currentUserId, "merchant", "提交商家入驻申请", "success", null);
    }

    @Override
    public ShopVO getShopInfo() {
        Long currentUserId = SecurityUtils.getCurrentUserId();

        Shop shop = shopMapper.selectByMerchantId(currentUserId);
        if (shop == null) {
            throw new BusinessException(BusinessStatusEnum.NOT_FOUND.getCode(), "店铺不存在");
        }

        ShopVO vo = new ShopVO();
        BeanUtils.copyProperties(shop, vo);

        // 获取申请状态
        MerchantApply apply = merchantApplyMapper.selectByUserId(currentUserId);
        if (apply != null) {
            vo.setAuditStatus(apply.getStatus());
            vo.setAuditRemark(apply.getAuditRemark());
            vo.setAuditTime(apply.getAuditTime());
        }

        return vo;
    }

    @Override
    @Transactional
    public void updateShop(ShopUpdateDTO updateDTO) {
        Long currentUserId = SecurityUtils.getCurrentUserId();

        Shop shop = shopMapper.selectByMerchantId(currentUserId);
        if (shop == null) {
            throw new BusinessException(BusinessStatusEnum.NOT_FOUND.getCode(), "店铺不存在");
        }

        BeanUtils.copyProperties(updateDTO, shop);
        shop.setUpdateTime(LocalDateTime.now());
        shopMapper.updateById(shop);

        saveOperationLog("update_shop", shop.getId(), "shop", "更新店铺信息", "success", null);
    }

    @Override
    public AuditStatusVO getAuditStatus() {
        Long currentUserId = SecurityUtils.getCurrentUserId();

        MerchantApply apply = merchantApplyMapper.selectByUserId(currentUserId);
        if (apply == null) {
            throw new BusinessException(BusinessStatusEnum.NOT_FOUND.getCode(), "未找到申请记录");
        }

        AuditStatusVO vo = new AuditStatusVO();
        vo.setStatus(apply.getStatus());
        vo.setAuditRemark(apply.getAuditRemark());
        vo.setAuditTime(apply.getAuditTime());

        return vo;
    }

    // ==================== 2.2 商品管理 ====================

    @Override
    @Transactional
    public void publishProduct(ProductPublishDTO publishDTO) {
        Long currentUserId = SecurityUtils.getCurrentUserId();

        // 检查店铺状态
        checkShopStatus(currentUserId);

        // 创建商品
        Product product = new Product();
        BeanUtils.copyProperties(publishDTO, product);
        product.setUserId(currentUserId);
        product.setUserType(1); // 商家
        product.setStatus(0); // 待审核
        product.setCreateTime(LocalDateTime.now());
        productMapper.insert(product);

        // 添加规格
        if (publishDTO.getSpecs() != null && !publishDTO.getSpecs().isEmpty()) {
            for (ProductSpecDTO specDTO : publishDTO.getSpecs()) {
                ProductSpec spec = new ProductSpec();
                BeanUtils.copyProperties(specDTO, spec);
                spec.setProductId(product.getId());
                productSpecMapper.insert(spec);
            }
        }

        saveOperationLog("publish_product", product.getId(), "product", "发布商品：" + product.getName(), "success", null);
    }

    @Override
    @Transactional
    public void batchPublishProducts(List<ProductPublishDTO> publishDTOs) {
        for (ProductPublishDTO dto : publishDTOs) {
            publishProduct(dto);
        }
    }

    @Override
    public PageInfo<MerchantProductVO> getProductList(Integer pageNum, Integer pageSize, String keyword, String category, Integer status) {
        Long currentUserId = SecurityUtils.getCurrentUserId();

        PageHelper.startPage(pageNum, pageSize);
        List<Product> products = productMapper.selectByMerchantId(currentUserId, keyword, category, status);

        List<MerchantProductVO> vos = products.stream().map(product -> {
            MerchantProductVO vo = new MerchantProductVO();
            BeanUtils.copyProperties(product, vo);

            // 获取封面图
            if (product.getImages() != null) {
                List<String> images = com.alibaba.fastjson.JSON.parseArray(product.getImages(), String.class);
                if (!images.isEmpty()) {
                    vo.setCoverImage(images.get(0));
                }
            }

            return vo;
        }).collect(Collectors.toList());

        return new PageInfo<>(vos);
    }

    @Override
    public MerchantProductDetailVO getProductDetail(Long id) {
        Long currentUserId = SecurityUtils.getCurrentUserId();

        Product product = productMapper.selectById(id);
        if (product == null || !product.getUserId().equals(currentUserId)) {
            throw new BusinessException(BusinessStatusEnum.NOT_FOUND.getCode(), "商品不存在");
        }

        MerchantProductDetailVO vo = new MerchantProductDetailVO();
        BeanUtils.copyProperties(product, vo);

        // 解析图片
        if (product.getImages() != null) {
            List<String> images = com.alibaba.fastjson.JSON.parseArray(product.getImages(), String.class);
            vo.setImages(images);
            if (!images.isEmpty()) {
                vo.setCoverImage(images.get(0));
            }
        }

        // 获取规格
        List<ProductSpec> specs = productSpecMapper.selectByProductId(id);
        List<ProductSpecVO> specVOs = specs.stream().map(spec -> {
            ProductSpecVO specVO = new ProductSpecVO();
            BeanUtils.copyProperties(spec, specVO);
            return specVO;
        }).collect(Collectors.toList());
        vo.setSpecs(specVOs);

        return vo;
    }

    @Override
    @Transactional
    public void updateProduct(ProductUpdateDTO updateDTO) {
        Long currentUserId = SecurityUtils.getCurrentUserId();

        Product product = productMapper.selectById(updateDTO.getId());
        if (product == null || !product.getUserId().equals(currentUserId)) {
            throw new BusinessException(BusinessStatusEnum.NOT_FOUND.getCode(), "商品不存在");
        }

        // 如果商品已上架，更新后需要重新审核
        if (product.getStatus() == 1) {
            product.setStatus(0);
            product.setAuditRemark(null);
            product.setAuditTime(null);
        }

        BeanUtils.copyProperties(updateDTO, product);
        product.setUpdateTime(LocalDateTime.now());
        productMapper.updateById(product);

        // 更新规格
        productSpecMapper.deleteByProductId(product.getId());
        if (updateDTO.getSpecs() != null && !updateDTO.getSpecs().isEmpty()) {
            for (ProductSpecDTO specDTO : updateDTO.getSpecs()) {
                ProductSpec spec = new ProductSpec();
                BeanUtils.copyProperties(specDTO, spec);
                spec.setProductId(product.getId());
                productSpecMapper.insert(spec);
            }
        }

        saveOperationLog("update_product", product.getId(), "product", "更新商品：" + product.getName(), "success", null);
    }

    @Override
    @Transactional
    public void onSaleProduct(Long id) {
        Long currentUserId = SecurityUtils.getCurrentUserId();

        Product product = productMapper.selectById(id);
        if (product == null || !product.getUserId().equals(currentUserId)) {
            throw new BusinessException(BusinessStatusEnum.NOT_FOUND.getCode(), "商品不存在");
        }

        if (product.getStatus() != 1) {
            throw new BusinessException(BusinessStatusEnum.FORBIDDEN.getCode(), "商品状态不正确");
        }

        product.setStatus(1);
        product.setShelfTime(LocalDateTime.now());
        productMapper.updateById(product);

        saveOperationLog("onsale_product", id, "product", "上架商品", "success", null);
    }

    @Override
    @Transactional
    public void offSaleProduct(Long id) {
        Long currentUserId = SecurityUtils.getCurrentUserId();

        Product product = productMapper.selectById(id);
        if (product == null || !product.getUserId().equals(currentUserId)) {
            throw new BusinessException(BusinessStatusEnum.NOT_FOUND.getCode(), "商品不存在");
        }

        product.setStatus(2);
        productMapper.updateById(product);

        saveOperationLog("offsale_product", id, "product", "下架商品", "success", null);
    }

    @Override
    @Transactional
    public void deleteProduct(Long id) {
        Long currentUserId = SecurityUtils.getCurrentUserId();

        Product product = productMapper.selectById(id);
        if (product == null || !product.getUserId().equals(currentUserId)) {
            throw new BusinessException(BusinessStatusEnum.NOT_FOUND.getCode(), "商品不存在");
        }

        productMapper.deleteById(id);
        productSpecMapper.deleteByProductId(id);

        saveOperationLog("delete_product", id, "product", "删除商品", "success", null);
    }

    // ==================== 优惠券管理 ====================

    @Override
    @Transactional
    public void createCoupon(CouponCreateDTO createDTO) {
        Long currentUserId = SecurityUtils.getCurrentUserId();

        Shop shop = shopMapper.selectByMerchantId(currentUserId);
        if (shop == null) {
            throw new BusinessException(BusinessStatusEnum.NOT_FOUND.getCode(), "店铺不存在");
        }

        Coupon coupon = new Coupon();
        BeanUtils.copyProperties(createDTO, coupon);
        coupon.setShopId(shop.getId());
        coupon.setRemainingCount(createDTO.getTotalCount());
        coupon.setStatus(0); // 未开始
        coupon.setCreateTime(LocalDateTime.now());
        couponMapper.insert(coupon);

        saveOperationLog("create_coupon", coupon.getId(), "coupon", "创建优惠券：" + coupon.getName(), "success", null);
    }

    @Override
    public PageInfo<CouponVO> getCouponList(Integer pageNum, Integer pageSize, Integer status) {
        Long currentUserId = SecurityUtils.getCurrentUserId();

        Shop shop = shopMapper.selectByMerchantId(currentUserId);
        if (shop == null) {
            throw new BusinessException(BusinessStatusEnum.NOT_FOUND.getCode(), "店铺不存在");
        }

        PageHelper.startPage(pageNum, pageSize);
        List<Coupon> coupons = couponMapper.selectByShopId(shop.getId(), status);

        List<CouponVO> vos = coupons.stream().map(coupon -> {
            CouponVO vo = new CouponVO();
            BeanUtils.copyProperties(coupon, vo);
            return vo;
        }).collect(Collectors.toList());

        return new PageInfo<>(vos);
    }

    @Override
    @Transactional
    public void updateCouponStatus(Long id, Integer status) {
        Long currentUserId = SecurityUtils.getCurrentUserId();

        Coupon coupon = couponMapper.selectById(id);
        if (coupon == null) {
            throw new BusinessException(BusinessStatusEnum.NOT_FOUND.getCode(), "优惠券不存在");
        }

        // 验证店铺所有权
        verifyShopOwnership(coupon.getShopId(), currentUserId);

        coupon.setStatus(status);
        couponMapper.updateById(coupon);

        saveOperationLog("update_coupon_status", id, "coupon", "更新优惠券状态", "success", null);
    }

    @Override
    @Transactional
    public void deleteCoupon(Long id) {
        Long currentUserId = SecurityUtils.getCurrentUserId();

        Coupon coupon = couponMapper.selectById(id);
        if (coupon == null) {
            throw new BusinessException(BusinessStatusEnum.NOT_FOUND.getCode(), "优惠券不存在");
        }

        // 验证店铺所有权
        verifyShopOwnership(coupon.getShopId(), currentUserId);

        couponMapper.deleteById(id);

        saveOperationLog("delete_coupon", id, "coupon", "删除优惠券", "success", null);
    }

    // ==================== 满减活动管理 ====================

    @Override
    @Transactional
    public void createFullReduction(FullReductionCreateDTO createDTO) {
        Long currentUserId = SecurityUtils.getCurrentUserId();

        Shop shop = shopMapper.selectByMerchantId(currentUserId);
        if (shop == null) {
            throw new BusinessException(BusinessStatusEnum.NOT_FOUND.getCode(), "店铺不存在");
        }

        FullReduction reduction = new FullReduction();
        BeanUtils.copyProperties(createDTO, reduction);
        reduction.setShopId(shop.getId());
        reduction.setStatus(0); // 未开始
        reduction.setCreateTime(LocalDateTime.now());
        fullReductionMapper.insert(reduction);

        // 添加规则
        for (FullReductionRuleDTO ruleDTO : createDTO.getRules()) {
            FullReductionRule rule = new FullReductionRule();
            rule.setReductionId(reduction.getId());
            rule.setConditionAmount(ruleDTO.getConditionAmount());
            rule.setDiscountAmount(ruleDTO.getDiscountAmount());
            fullReductionMapper.insertRule(rule);
        }

        saveOperationLog("create_full_reduction", reduction.getId(), "full_reduction", "创建满减活动", "success", null);
    }

    @Override
    public PageInfo<FullReductionVO> getFullReductionList(Integer pageNum, Integer pageSize, Integer status) {
        Long currentUserId = SecurityUtils.getCurrentUserId();

        Shop shop = shopMapper.selectByMerchantId(currentUserId);
        if (shop == null) {
            throw new BusinessException(BusinessStatusEnum.NOT_FOUND.getCode(), "店铺不存在");
        }

        PageHelper.startPage(pageNum, pageSize);
        List<FullReduction> reductions = fullReductionMapper.selectByShopId(shop.getId(), status);

        List<FullReductionVO> vos = reductions.stream().map(reduction -> {
            FullReductionVO vo = new FullReductionVO();
            BeanUtils.copyProperties(reduction, vo);

            // 获取规则
            List<FullReductionRule> rules = fullReductionMapper.selectRulesByReductionId(reduction.getId());
            List<FullReductionRuleVO> ruleVOs = rules.stream().map(rule -> {
                FullReductionRuleVO ruleVO = new FullReductionRuleVO();
                BeanUtils.copyProperties(rule, ruleVO);
                return ruleVO;
            }).collect(Collectors.toList());
            vo.setRules(ruleVOs);

            return vo;
        }).collect(Collectors.toList());

        return new PageInfo<>(vos);
    }

    @Override
    @Transactional
    public void updateFullReductionStatus(Long id, Integer status) {
        Long currentUserId = SecurityUtils.getCurrentUserId();

        FullReduction reduction = fullReductionMapper.selectById(id);
        if (reduction == null) {
            throw new BusinessException(BusinessStatusEnum.NOT_FOUND.getCode(), "活动不存在");
        }

        // 验证店铺所有权
        verifyShopOwnership(reduction.getShopId(), currentUserId);

        reduction.setStatus(status);
        fullReductionMapper.updateById(reduction);

        saveOperationLog("update_full_reduction_status", id, "full_reduction", "更新活动状态", "success", null);
    }

    // ==================== 2.3 订单管理 ====================

    @Override
    public PageInfo<MerchantOrderVO> getPendingOrders(Integer pageNum, Integer pageSize) {
        Long currentUserId = SecurityUtils.getCurrentUserId();

        PageHelper.startPage(pageNum, pageSize);
        List<Order> orders = orderMapper.selectPendingBySellerId(currentUserId);

        List<MerchantOrderVO> vos = convertToOrderVOs(orders);
        return new PageInfo<>(vos);
    }

    @Override
    public PageInfo<MerchantOrderVO> getOrderList(OrderQueryDTO queryDTO) {
        Long currentUserId = SecurityUtils.getCurrentUserId();

        PageHelper.startPage(queryDTO.getPageNum(), queryDTO.getPageSize());
        List<Order> orders = orderMapper.selectBySellerIdAndCondition(currentUserId, queryDTO);

        List<MerchantOrderVO> vos = convertToOrderVOs(orders);
        return new PageInfo<>(vos);
    }

    @Override
    public MerchantOrderDetailVO getOrderDetail(Long id) {
        Long currentUserId = SecurityUtils.getCurrentUserId();

        Order order = orderMapper.selectById(id);
        if (order == null || !order.getSellerId().equals(currentUserId)) {
            throw new BusinessException(BusinessStatusEnum.NOT_FOUND.getCode(), "订单不存在");
        }

        MerchantOrderDetailVO vo = new MerchantOrderDetailVO();
        BeanUtils.copyProperties(order, vo);

        // 获取买家信息
        User buyer = userMapper.selectById(order.getBuyerId());
        if (buyer != null) {
            vo.setBuyerName(buyer.getUsername());
            vo.setBuyerPhone(buyer.getPhone());
        }

        return vo;
    }

    @Override
    @Transactional
    public void shipOrder(ShipDTO shipDTO) {
        Long currentUserId = SecurityUtils.getCurrentUserId();

        Order order = orderMapper.selectById(shipDTO.getOrderId());
        if (order == null || !order.getSellerId().equals(currentUserId)) {
            throw new BusinessException(BusinessStatusEnum.NOT_FOUND.getCode(), "订单不存在");
        }

        if (order.getStatus() != 1) {
            throw new BusinessException(BusinessStatusEnum.FORBIDDEN.getCode(), "订单状态不正确");
        }

        order.setStatus(2); // 待收货
        order.setLogisticsNo(shipDTO.getLogisticsNo());
        order.setLogisticsCompany(shipDTO.getLogisticsCompany());
        order.setShipTime(LocalDateTime.now());
        orderMapper.updateById(order);

        saveOperationLog("ship_order", order.getId(), "order", "发货", "success", null);
    }

    @Override
    @Transactional
    public void batchShipOrders(List<ShipDTO> shipDTOs) {
        for (ShipDTO dto : shipDTOs) {
            shipOrder(dto);
        }
    }

    @Override
    @Transactional
    public void modifyOrderAmount(Long id, BigDecimal amount) {
        Long currentUserId = SecurityUtils.getCurrentUserId();

        Order order = orderMapper.selectById(id);
        if (order == null || !order.getSellerId().equals(currentUserId)) {
            throw new BusinessException(BusinessStatusEnum.NOT_FOUND.getCode(), "订单不存在");
        }

        if (order.getStatus() != 0) {
            throw new BusinessException(BusinessStatusEnum.FORBIDDEN.getCode(), "只能修改待付款订单的金额");
        }

        order.setTotalAmount(amount);
        orderMapper.updateById(order);

        saveOperationLog("modify_order_amount", id, "order", "修改订单金额", "success", null);
    }

    @Override
    @Transactional
    public void closeOrder(Long id, String reason) {
        Long currentUserId = SecurityUtils.getCurrentUserId();

        Order order = orderMapper.selectById(id);
        if (order == null || !order.getSellerId().equals(currentUserId)) {
            throw new BusinessException(BusinessStatusEnum.NOT_FOUND.getCode(), "订单不存在");
        }

        if (order.getStatus() != 0) {
            throw new BusinessException(BusinessStatusEnum.FORBIDDEN.getCode(), "只能关闭待付款订单");
        }

        order.setStatus(6); // 已取消
        order.setCloseReason(reason);
        orderMapper.updateById(order);

        saveOperationLog("close_order", id, "order", "关闭订单：" + reason, "success", null);
    }

    // ==================== 售后处理 ====================

    @Override
    public PageInfo<MerchantAfterSaleVO> getAfterSaleList(Integer pageNum, Integer pageSize, Integer status) {
        Long currentUserId = SecurityUtils.getCurrentUserId();

        PageHelper.startPage(pageNum, pageSize);
        List<AfterSale> afterSales = afterSaleMapper.selectBySellerId(currentUserId, status);

        List<MerchantAfterSaleVO> vos = afterSales.stream().map(afterSale -> {
            MerchantAfterSaleVO vo = new MerchantAfterSaleVO();
            BeanUtils.copyProperties(afterSale, vo);

            Order order = orderMapper.selectById(afterSale.getOrderId());
            if (order != null) {
                vo.setOrderNo(order.getOrderNo());
            }

            return vo;
        }).collect(Collectors.toList());

        return new PageInfo<>(vos);
    }

    @Override
    public MerchantAfterSaleDetailVO getAfterSaleDetail(Long id) {
        Long currentUserId = SecurityUtils.getCurrentUserId();

        AfterSale afterSale = afterSaleMapper.selectById(id);
        if (afterSale == null) {
            throw new BusinessException(BusinessStatusEnum.NOT_FOUND.getCode(), "售后记录不存在");
        }

        // 验证订单所有权
        Order order = orderMapper.selectById(afterSale.getOrderId());
        if (order == null || !order.getSellerId().equals(currentUserId)) {
            throw new BusinessException(BusinessStatusEnum.FORBIDDEN.getCode(), "无权查看此售后");
        }

        MerchantAfterSaleDetailVO vo = new MerchantAfterSaleDetailVO();
        BeanUtils.copyProperties(afterSale, vo);

        if (order != null) {
            vo.setOrderNo(order.getOrderNo());
        }

        // 解析图片
        if (afterSale.getEvidenceImages() != null) {
            List<String> images = com.alibaba.fastjson.JSON.parseArray(afterSale.getEvidenceImages(), String.class);
            vo.setEvidenceImages(images);
        }

        return vo;
    }

    @Override
    @Transactional
    public void agreeAfterSale(Long id, String remark) {
        Long currentUserId = SecurityUtils.getCurrentUserId();

        AfterSale afterSale = afterSaleMapper.selectById(id);
        if (afterSale == null) {
            throw new BusinessException(BusinessStatusEnum.NOT_FOUND.getCode(), "售后记录不存在");
        }

        // 验证订单所有权
        Order order = orderMapper.selectById(afterSale.getOrderId());
        if (order == null || !order.getSellerId().equals(currentUserId)) {
            throw new BusinessException(BusinessStatusEnum.FORBIDDEN.getCode(), "无权操作此售后");
        }

        afterSale.setStatus(1); // 卖家同意
        afterSale.setSellerResponse(remark);
        afterSaleMapper.updateById(afterSale);

        saveOperationLog("agree_after_sale", id, "after_sale", "同意售后", "success", null);
    }

    @Override
    @Transactional
    public void rejectAfterSale(Long id, String reason) {
        Long currentUserId = SecurityUtils.getCurrentUserId();

        AfterSale afterSale = afterSaleMapper.selectById(id);
        if (afterSale == null) {
            throw new BusinessException(BusinessStatusEnum.NOT_FOUND.getCode(), "售后记录不存在");
        }

        // 验证订单所有权
        Order order = orderMapper.selectById(afterSale.getOrderId());
        if (order == null || !order.getSellerId().equals(currentUserId)) {
            throw new BusinessException(BusinessStatusEnum.FORBIDDEN.getCode(), "无权操作此售后");
        }

        afterSale.setStatus(2); // 卖家拒绝
        afterSale.setSellerResponse(reason);
        afterSaleMapper.updateById(afterSale);

        saveOperationLog("reject_after_sale", id, "after_sale", "拒绝售后：" + reason, "success", null);
    }

    @Override
    @Transactional
    public void confirmReturnReceipt(Long id) {
        Long currentUserId = SecurityUtils.getCurrentUserId();

        AfterSale afterSale = afterSaleMapper.selectById(id);
        if (afterSale == null) {
            throw new BusinessException(BusinessStatusEnum.NOT_FOUND.getCode(), "售后记录不存在");
        }

        // 验证订单所有权
        Order order = orderMapper.selectById(afterSale.getOrderId());
        if (order == null || !order.getSellerId().equals(currentUserId)) {
            throw new BusinessException(BusinessStatusEnum.FORBIDDEN.getCode(), "无权操作此售后");
        }

        afterSale.setStatus(4); // 已完成
        afterSale.setCompleteTime(LocalDateTime.now());
        afterSaleMapper.updateById(afterSale);

        saveOperationLog("confirm_return_receipt", id, "after_sale", "确认收到退货", "success", null);
    }

    // ==================== 2.4 社区运营与消息 ====================

    @Override
    public PageInfo<ConsultVO> getConsultList(Integer pageNum, Integer pageSize, Integer status) {
        Long currentUserId = SecurityUtils.getCurrentUserId();

        PageHelper.startPage(pageNum, pageSize);
        List<Consult> consults = consultMapper.selectByMerchantId(currentUserId, status);

        List<ConsultVO> vos = consults.stream().map(consult -> {
            ConsultVO vo = new ConsultVO();
            BeanUtils.copyProperties(consult, vo);

            User user = userMapper.selectById(consult.getUserId());
            if (user != null) {
                vo.setUserName(user.getUsername());
                vo.setUserAvatar(user.getAvatar());
            }

            return vo;
        }).collect(Collectors.toList());

        return new PageInfo<>(vos);
    }

    @Override
    @Transactional
    public void replyConsult(Long id, String content) {
        Long currentUserId = SecurityUtils.getCurrentUserId();

        Consult consult = consultMapper.selectById(id);
        if (consult == null) {
            throw new BusinessException(BusinessStatusEnum.NOT_FOUND.getCode(), "咨询记录不存在");
        }

        // 验证商品所有权
        Product product = productMapper.selectById(consult.getProductId());
        if (product == null || !product.getUserId().equals(currentUserId)) {
            throw new BusinessException(BusinessStatusEnum.FORBIDDEN.getCode(), "无权回复此咨询");
        }

        consult.setStatus(1); // 已回复
        consult.setReplyContent(content);
        consult.setReplyTime(LocalDateTime.now());
        consultMapper.updateById(consult);

        saveOperationLog("reply_consult", id, "consult", "回复用户咨询", "success", null);
    }

    @Override
    @Transactional
    public void publishDynamic(DynamicPublishDTO publishDTO) {
        Long currentUserId = SecurityUtils.getCurrentUserId();

        Shop shop = shopMapper.selectByMerchantId(currentUserId);
        if (shop == null) {
            throw new BusinessException(BusinessStatusEnum.NOT_FOUND.getCode(), "店铺不存在");
        }

        Dynamic dynamic = new Dynamic();
        BeanUtils.copyProperties(publishDTO, dynamic);
        dynamic.setShopId(shop.getId());
        dynamic.setMerchantId(currentUserId);
        dynamic.setStatus(1); // 已发布
        dynamic.setCreateTime(LocalDateTime.now());
        dynamicMapper.insert(dynamic);

        saveOperationLog("publish_dynamic", dynamic.getId(), "dynamic", "发布品牌动态", "success", null);
    }

    @Override
    public PageInfo<DynamicVO> getDynamicList(Integer pageNum, Integer pageSize) {
        Long currentUserId = SecurityUtils.getCurrentUserId();

        PageHelper.startPage(pageNum, pageSize);
        List<Dynamic> dynamics = dynamicMapper.selectByMerchantId(currentUserId);

        List<DynamicVO> vos = dynamics.stream().map(dynamic -> {
            DynamicVO vo = new DynamicVO();
            BeanUtils.copyProperties(dynamic, vo);

            // 解析图片
            if (dynamic.getImages() != null) {
                List<String> images = com.alibaba.fastjson.JSON.parseArray(dynamic.getImages(), String.class);
                vo.setImages(images);
            }

            return vo;
        }).collect(Collectors.toList());

        return new PageInfo<>(vos);
    }

    @Override
    @Transactional
    public void deleteDynamic(Long id) {
        Long currentUserId = SecurityUtils.getCurrentUserId();

        Dynamic dynamic = dynamicMapper.selectById(id);
        if (dynamic == null || !dynamic.getMerchantId().equals(currentUserId)) {
            throw new BusinessException(BusinessStatusEnum.NOT_FOUND.getCode(), "动态不存在");
        }

        dynamicMapper.deleteById(id);

        saveOperationLog("delete_dynamic", id, "dynamic", "删除品牌动态", "success", null);
    }

    // ==================== 2.5 数据查看 ====================

    @Override
    public BusinessOverviewVO getBusinessOverview() {
        Long currentUserId = SecurityUtils.getCurrentUserId();

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime todayStart = now.toLocalDate().atStartOfDay();
        LocalDateTime yesterdayStart = todayStart.minusDays(1);
        LocalDateTime weekStart = now.minusWeeks(1);
        LocalDateTime monthStart = now.minusMonths(1);

        BusinessOverviewVO vo = new BusinessOverviewVO();

        // 今日数据
        vo.setTodayOrders(orderMapper.countBySellerIdAndDate(currentUserId, todayStart, now));
        vo.setTodayAmount(orderMapper.sumAmountBySellerIdAndDate(currentUserId, todayStart, now));
        vo.setTodayVisitors(productMapper.countVisitorsByMerchantId(currentUserId, todayStart, now));

        // 昨日数据
        vo.setYesterdayOrders(orderMapper.countBySellerIdAndDate(currentUserId, yesterdayStart, todayStart));
        vo.setYesterdayAmount(orderMapper.sumAmountBySellerIdAndDate(currentUserId, yesterdayStart, todayStart));
        vo.setYesterdayVisitors(productMapper.countVisitorsByMerchantId(currentUserId, yesterdayStart, todayStart));

        // 本周数据
        vo.setWeekOrders(orderMapper.countBySellerIdAndDate(currentUserId, weekStart, now));
        vo.setWeekAmount(orderMapper.sumAmountBySellerIdAndDate(currentUserId, weekStart, now));
        vo.setWeekVisitors(productMapper.countVisitorsByMerchantId(currentUserId, weekStart, now));

        // 本月数据
        vo.setMonthOrders(orderMapper.countBySellerIdAndDate(currentUserId, monthStart, now));
        vo.setMonthAmount(orderMapper.sumAmountBySellerIdAndDate(currentUserId, monthStart, now));
        vo.setMonthVisitors(productMapper.countVisitorsByMerchantId(currentUserId, monthStart, now));

        // 总计数据
        vo.setTotalOrders(orderMapper.countBySellerId(currentUserId));
        vo.setTotalAmount(orderMapper.sumAmountBySellerId(currentUserId));
        vo.setTotalProducts(productMapper.countByMerchantId(currentUserId));

        // 待处理事项
        vo.setPendingOrders(orderMapper.countPendingBySellerId(currentUserId));
        vo.setPendingAfterSales(afterSaleMapper.countPendingBySellerId(currentUserId));
        vo.setPendingConsults(consultMapper.countPendingByMerchantId(currentUserId));

        // 计算增长率
        Map<String, Double> growthRates = calculateGrowthRates(currentUserId);
        vo.setGrowthRates(growthRates);

        return vo;
    }

    @Override
    public List<SalesTrendVO> getSalesTrend(LocalDateTime startDate, LocalDateTime endDate, String groupBy) {
        Long currentUserId = SecurityUtils.getCurrentUserId();

        List<Map<String, Object>> statistics = orderMapper.salesTrendByMerchantId(currentUserId, startDate, endDate, groupBy);

        List<SalesTrendVO> trend = new ArrayList<>();
        for (Map<String, Object> stat : statistics) {
            SalesTrendVO vo = new SalesTrendVO();
            vo.setDate(stat.get("date").toString());
            vo.setOrderCount(((Number) stat.get("order_count")).longValue());
            vo.setAmount(new BigDecimal(stat.get("amount").toString()));
            vo.setVisitorCount(((Number) stat.get("visitor_count")).longValue());

            // 计算转化率
            if (vo.getVisitorCount() > 0) {
                double rate = (vo.getOrderCount().doubleValue() / vo.getVisitorCount().doubleValue()) * 100;
                vo.setConversionRate((int) Math.round(rate));
            } else {
                vo.setConversionRate(0);
            }

            trend.add(vo);
        }

        return trend;
    }

    @Override
    public List<ProductRankVO> getProductRank(Integer limit) {
        Long currentUserId = SecurityUtils.getCurrentUserId();

        List<Map<String, Object>> ranks = orderMapper.productRankByMerchantId(currentUserId, limit);

        List<ProductRankVO> result = new ArrayList<>();
        int rank = 1;
        for (Map<String, Object> item : ranks) {
            ProductRankVO vo = new ProductRankVO();
            vo.setRank(rank++);
            vo.setProductId(((Number) item.get("product_id")).longValue());
            vo.setProductName(item.get("product_name").toString());
            vo.setProductImage(item.get("product_image").toString());
            vo.setPrice(new BigDecimal(item.get("price").toString()));
            vo.setSaleCount(((Number) item.get("sale_count")).longValue());
            vo.setSaleAmount(new BigDecimal(item.get("sale_amount").toString()));
            vo.setViewCount(((Number) item.get("view_count")).longValue());

            // 计算转化率
            if (vo.getViewCount() > 0) {
                double rate = (vo.getSaleCount().doubleValue() / vo.getViewCount().doubleValue()) * 100;
                vo.setConversionRate(rate);
            } else {
                vo.setConversionRate(0.0);
            }

            result.add(vo);
        }

        return result;
    }

    @Override
    public PageInfo<ShopReviewVO> getShopReviews(Integer pageNum, Integer pageSize) {
        Long currentUserId = SecurityUtils.getCurrentUserId();

        PageHelper.startPage(pageNum, pageSize);
        List<Review> reviews = reviewMapper.selectByMerchantId(currentUserId);

        List<ShopReviewVO> vos = reviews.stream().map(review -> {
            ShopReviewVO vo = new ShopReviewVO();
            BeanUtils.copyProperties(review, vo);

            User user = userMapper.selectById(review.getUserId());
            if (user != null) {
                vo.setUserName(user.getUsername());
                vo.setUserAvatar(user.getAvatar());
                vo.setUserCreditLevel(getCreditLevel(user.getCreditScore()));
            }

            // 解析图片
            if (review.getImages() != null) {
                List<String> images = com.alibaba.fastjson.JSON.parseArray(review.getImages(), String.class);
                vo.setImages(images);
            }

            return vo;
        }).collect(Collectors.toList());

        return new PageInfo<>(vos);
    }

    @Override
    public ShopRatingVO getShopRating() {
        Long currentUserId = SecurityUtils.getCurrentUserId();

        ShopRatingVO vo = new ShopRatingVO();

        // 获取评分统计
        Map<String, Object> ratingStats = reviewMapper.getRatingStatsByMerchantId(currentUserId);

        vo.setOverallRating(new BigDecimal(ratingStats.get("avg_overall").toString()));
        vo.setMatchRating(new BigDecimal(ratingStats.get("avg_match").toString()));
        vo.setCommunicationRating(new BigDecimal(ratingStats.get("avg_communication").toString()));
        vo.setShippingRating(new BigDecimal(ratingStats.get("avg_shipping").toString()));

        // 将 Integer 转换为 Long
        vo.setTotalReviews(((Number) ratingStats.get("total_count")).longValue());
        vo.setGoodReviews(((Number) ratingStats.get("good_count")).longValue());
        vo.setMediumReviews(((Number) ratingStats.get("medium_count")).longValue());
        vo.setBadReviews(((Number) ratingStats.get("bad_count")).longValue());

        // 分数分布
        Map<Integer, Long> distribution = reviewMapper.getScoreDistributionByMerchantId(currentUserId);
        vo.setScoreDistribution(distribution);

        // 计算各项占比
        vo.calculateRates();

        return vo;
    }

    @Override
    @Transactional
    public void replyReview(Long id, String content) {
        Long currentUserId = SecurityUtils.getCurrentUserId();

        Review review = reviewMapper.selectById(id);
        if (review == null) {
            throw new BusinessException(BusinessStatusEnum.NOT_FOUND.getCode(), "评价不存在");
        }

        // 验证店铺所有权
        Order order = orderMapper.selectById(review.getOrderId());
        if (order == null || !order.getSellerId().equals(currentUserId)) {
            throw new BusinessException(BusinessStatusEnum.FORBIDDEN.getCode(), "无权回复此评价");
        }

        review.setReplyContent(content);
        review.setReplyTime(LocalDateTime.now());
        reviewMapper.updateById(review);

        saveOperationLog("reply_review", id, "review", "回复用户评价", "success", null);
    }

    // ==================== 私有辅助方法 ====================

    /**
     * 检查店铺状态
     */
    private void checkShopStatus(Long merchantId) {
        Shop shop = shopMapper.selectByMerchantId(merchantId);
        if (shop == null) {
            throw new BusinessException(BusinessStatusEnum.FORBIDDEN.getCode(), "请先申请开店");
        }
        if (shop.getAuditStatus() != 1) {
            throw new BusinessException(BusinessStatusEnum.FORBIDDEN.getCode(), "店铺审核未通过");
        }
        if (shop.getStatus() != 1) {
            throw new BusinessException(BusinessStatusEnum.FORBIDDEN.getCode(), "店铺已关闭");
        }
    }

    /**
     * 验证店铺所有权
     */
    private void verifyShopOwnership(Long shopId, Long merchantId) {
        Shop shop = shopMapper.selectById(shopId);
        if (shop == null || !shop.getMerchantId().equals(merchantId)) {
            throw new BusinessException(BusinessStatusEnum.FORBIDDEN.getCode(), "无权操作");
        }
    }

    /**
     * 转换订单VO
     */
    private List<MerchantOrderVO> convertToOrderVOs(List<Order> orders) {
        return orders.stream().map(order -> {
            MerchantOrderVO vo = new MerchantOrderVO();
            BeanUtils.copyProperties(order, vo);

            User buyer = userMapper.selectById(order.getBuyerId());
            if (buyer != null) {
                vo.setBuyerName(buyer.getUsername());
                vo.setBuyerPhone(buyer.getPhone());
            }

            return vo;
        }).collect(Collectors.toList());
    }

    /**
     * 获取信用等级
     */
    private Integer getCreditLevel(Integer score) {
        if (score == null) return 0;
        if (score >= 800) return 5;
        if (score >= 700) return 4;
        if (score >= 600) return 3;
        if (score >= 400) return 2;
        return 1;
    }

    /**
     * 计算增长率
     */
    private Map<String, Double> calculateGrowthRates(Long merchantId) {
        Map<String, Double> growthRates = new HashMap<>();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime todayStart = now.toLocalDate().atStartOfDay();
        LocalDateTime yesterdayStart = todayStart.minusDays(1);
        LocalDateTime thisWeekStart = now.minusWeeks(1);
        LocalDateTime lastWeekStart = now.minusWeeks(2);

        // 日环比
        BigDecimal todayAmount = orderMapper.sumAmountBySellerIdAndDate(merchantId, todayStart, now);
        BigDecimal yesterdayAmount = orderMapper.sumAmountBySellerIdAndDate(merchantId, yesterdayStart, todayStart);
        growthRates.put("dailyGrowth", calculateGrowthRate(todayAmount, yesterdayAmount));

        // 周环比
        BigDecimal thisWeekAmount = orderMapper.sumAmountBySellerIdAndDate(merchantId, thisWeekStart, now);
        BigDecimal lastWeekAmount = orderMapper.sumAmountBySellerIdAndDate(merchantId, lastWeekStart, thisWeekStart);
        growthRates.put("weeklyGrowth", calculateGrowthRate(thisWeekAmount, lastWeekAmount));

        return growthRates;
    }

    /**
     * 计算增长率
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
     * 保存操作日志
     */
    private void saveOperationLog(String operation, Long targetId, String targetType,
                                  String details, String result, String reason) {
        OperationLog log = new OperationLog();
        log.setOperatorId(SecurityUtils.getCurrentUserId());
        log.setOperatorName(SecurityUtils.getCurrentUser() != null ?
                SecurityUtils.getCurrentUser().getUsername() : "system");
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
}