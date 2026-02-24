package com.rabbiter.hrm.controller;

import com.rabbiter.hrm.dto.ResponseDTO;
import com.rabbiter.hrm.dto.Response;
import com.rabbiter.hrm.dto.*;
import com.rabbiter.hrm.service.MerchantService;
import com.rabbiter.hrm.vo.*;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 商家控制器
 */
@RestController
@RequestMapping("/api/merchant")
public class MerchantController {

    @Autowired
    private MerchantService merchantService;

    // ==================== 2.1 店铺管理 ====================

    /**
     * 商家入驻申请
     */
    @PostMapping("/shop/apply")
    public ResponseDTO applyShop(@Valid @RequestBody ShopApplyDTO applyDTO) {
        merchantService.applyShop(applyDTO);
        return Response.success("入驻申请提交成功");
    }

    /**
     * 获取店铺信息
     */
    @GetMapping("/shop/info")
    public ResponseDTO getShopInfo() {
        ShopVO shopInfo = merchantService.getShopInfo();
        return Response.success(shopInfo);
    }

    /**
     * 更新店铺信息
     */
    @PutMapping("/shop/update")
    public ResponseDTO updateShop(@Valid @RequestBody ShopUpdateDTO updateDTO) {
        merchantService.updateShop(updateDTO);
        return Response.success("店铺信息更新成功");
    }

    /**
     * 查看入驻审核状态
     */
    @GetMapping("/shop/audit/status")
    public ResponseDTO getAuditStatus() {
        AuditStatusVO status = merchantService.getAuditStatus();
        return Response.success(status);
    }

    // ==================== 2.2 商品管理 ====================

    /**
     * 发布商品
     */
    @PostMapping("/product/publish")
    public ResponseDTO publishProduct(@Valid @RequestBody ProductPublishDTO publishDTO) {
        merchantService.publishProduct(publishDTO);
        return Response.success("商品发布成功，等待审核");
    }

    /**
     * 批量发布商品
     */
    @PostMapping("/product/batch/publish")
    public ResponseDTO batchPublishProducts(@Valid @RequestBody List<ProductPublishDTO> publishDTOs) {
        merchantService.batchPublishProducts(publishDTOs);
        return Response.success("批量发布成功，共" + publishDTOs.size() + "个商品等待审核");
    }

    /**
     * 获取商品列表
     */
    @GetMapping("/product/list")
    public ResponseDTO getProductList(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Integer status) {

        PageInfo<MerchantProductVO> pageInfo = merchantService.getProductList(pageNum, pageSize, keyword, category, status);
        return Response.success(pageInfo);
    }

    /**
     * 获取商品详情
     */
    @GetMapping("/product/{id}")
    public ResponseDTO getProductDetail(@PathVariable Long id) {
        MerchantProductDetailVO productDetail = merchantService.getProductDetail(id);
        return Response.success(productDetail);
    }

    /**
     * 更新商品信息
     */
    @PutMapping("/product/{id}/update")
    public ResponseDTO updateProduct(@PathVariable Long id, @Valid @RequestBody ProductUpdateDTO updateDTO) {
        updateDTO.setId(id);
        merchantService.updateProduct(updateDTO);
        return Response.success("商品更新成功");
    }

    /**
     * 上架商品
     */
    @PutMapping("/product/{id}/onsale")
    public ResponseDTO onSaleProduct(@PathVariable Long id) {
        merchantService.onSaleProduct(id);
        return Response.success("商品已上架");
    }

    /**
     * 下架商品
     */
    @PutMapping("/product/{id}/offsale")
    public ResponseDTO offSaleProduct(@PathVariable Long id) {
        merchantService.offSaleProduct(id);
        return Response.success("商品已下架");
    }

    /**
     * 删除商品
     */
    @DeleteMapping("/product/{id}")
    public ResponseDTO deleteProduct(@PathVariable Long id) {
        merchantService.deleteProduct(id);
        return Response.success("商品已删除");
    }

    // ==================== 优惠券管理 ====================

    /**
     * 创建优惠券
     */
    @PostMapping("/coupon/create")
    public ResponseDTO createCoupon(@Valid @RequestBody CouponCreateDTO createDTO) {
        merchantService.createCoupon(createDTO);
        return Response.success("优惠券创建成功");
    }

    /**
     * 获取优惠券列表
     */
    @GetMapping("/coupon/list")
    public ResponseDTO getCouponList(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) Integer status) {

        PageInfo<CouponVO> pageInfo = merchantService.getCouponList(pageNum, pageSize, status);
        return Response.success(pageInfo);
    }

    /**
     * 更新优惠券状态
     */
    @PutMapping("/coupon/{id}/status")
    public ResponseDTO updateCouponStatus(@PathVariable Long id, @RequestParam Integer status) {
        merchantService.updateCouponStatus(id, status);
        return Response.success("优惠券状态已更新");
    }

    /**
     * 删除优惠券
     */
    @DeleteMapping("/coupon/{id}")
    public ResponseDTO deleteCoupon(@PathVariable Long id) {
        merchantService.deleteCoupon(id);
        return Response.success("优惠券已删除");
    }

    // ==================== 满减活动管理 ====================

    /**
     * 创建满减活动
     */
    @PostMapping("/promotion/full-reduction")
    public ResponseDTO createFullReduction(@Valid @RequestBody FullReductionCreateDTO createDTO) {
        merchantService.createFullReduction(createDTO);
        return Response.success("满减活动创建成功");
    }

    /**
     * 获取满减活动列表
     */
    @GetMapping("/promotion/full-reduction/list")
    public ResponseDTO getFullReductionList(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) Integer status) {

        PageInfo<FullReductionVO> pageInfo = merchantService.getFullReductionList(pageNum, pageSize, status);
        return Response.success(pageInfo);
    }

    /**
     * 更新满减活动状态
     */
    @PutMapping("/promotion/full-reduction/{id}/status")
    public ResponseDTO updateFullReductionStatus(@PathVariable Long id, @RequestParam Integer status) {
        merchantService.updateFullReductionStatus(id, status);
        return Response.success("活动状态已更新");
    }

    // ==================== 2.3 订单管理 ====================

    /**
     * 获取待处理订单列表
     */
    @GetMapping("/order/pending")
    public ResponseDTO getPendingOrders(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {

        PageInfo<MerchantOrderVO> pageInfo = merchantService.getPendingOrders(pageNum, pageSize);
        return Response.success(pageInfo);
    }

    /**
     * 获取订单列表
     */
    @GetMapping("/order/list")
    public ResponseDTO getOrderList(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String orderNo,
            @RequestParam(required = false) Integer status,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDateTime endDate) {

        OrderQueryDTO queryDTO = OrderQueryDTO.builder()
                .pageNum(pageNum)
                .pageSize(pageSize)
                .orderNo(orderNo)
                .status(status)
                .startDate(startDate)
                .endDate(endDate)
                .build();

        PageInfo<MerchantOrderVO> pageInfo = merchantService.getOrderList(queryDTO);
        return Response.success(pageInfo);
    }

    /**
     * 获取订单详情
     */
    @GetMapping("/order/{id}")
    public ResponseDTO getOrderDetail(@PathVariable Long id) {
        MerchantOrderDetailVO orderDetail = merchantService.getOrderDetail(id);
        return Response.success(orderDetail);
    }

    /**
     * 发货
     */
    @PutMapping("/order/{id}/ship")
    public ResponseDTO shipOrder(@PathVariable Long id, @Valid @RequestBody ShipDTO shipDTO) {
        shipDTO.setOrderId(id);
        merchantService.shipOrder(shipDTO);
        return Response.success("发货成功");
    }

    /**
     * 批量发货
     */
    @PostMapping("/order/batch/ship")
    public ResponseDTO batchShipOrders(@Valid @RequestBody List<ShipDTO> shipDTOs) {
        merchantService.batchShipOrders(shipDTOs);
        return Response.success("批量发货成功");
    }

    /**
     * 修改订单金额
     */
    @PutMapping("/order/{id}/amount")
    public ResponseDTO modifyOrderAmount(@PathVariable Long id, @RequestParam BigDecimal amount) {
        merchantService.modifyOrderAmount(id, amount);
        return Response.success("订单金额已修改");
    }

    /**
     * 关闭订单
     */
    @PutMapping("/order/{id}/close")
    public ResponseDTO closeOrder(@PathVariable Long id, @RequestParam String reason) {
        merchantService.closeOrder(id, reason);
        return Response.success("订单已关闭");
    }

    // ==================== 售后处理 ====================

    /**
     * 获取售后申请列表
     */
    @GetMapping("/after-sale/list")
    public ResponseDTO getAfterSaleList(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) Integer status) {

        PageInfo<MerchantAfterSaleVO> pageInfo = merchantService.getAfterSaleList(pageNum, pageSize, status);
        return Response.success(pageInfo);
    }

    /**
     * 获取售后详情
     */
    @GetMapping("/after-sale/{id}")
    public ResponseDTO getAfterSaleDetail(@PathVariable Long id) {
        MerchantAfterSaleDetailVO detail = merchantService.getAfterSaleDetail(id);
        return Response.success(detail);
    }

    /**
     * 同意退款/退货
     */
    @PutMapping("/after-sale/{id}/agree")
    public ResponseDTO agreeAfterSale(@PathVariable Long id, @RequestParam(required = false) String remark) {
        merchantService.agreeAfterSale(id, remark);
        return Response.success("已同意售后申请");
    }

    /**
     * 拒绝退款/退货
     */
    @PutMapping("/after-sale/{id}/reject")
    public ResponseDTO rejectAfterSale(@PathVariable Long id, @RequestParam String reason) {
        merchantService.rejectAfterSale(id, reason);
        return Response.success("已拒绝售后申请");
    }

    /**
     * 确认收货（退货）
     */
    @PutMapping("/after-sale/{id}/confirm-receipt")
    public ResponseDTO confirmReturnReceipt(@PathVariable Long id) {
        merchantService.confirmReturnReceipt(id);
        return Response.success("已确认收到退货");
    }

    // ==================== 2.4 社区运营与消息 ====================

    /**
     * 获取用户咨询列表
     */
    @GetMapping("/consult/list")
    public ResponseDTO getConsultList(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) Integer status) {

        PageInfo<ConsultVO> pageInfo = merchantService.getConsultList(pageNum, pageSize, status);
        return Response.success(pageInfo);
    }

    /**
     * 回复用户咨询
     */
    @PostMapping("/consult/{id}/reply")
    public ResponseDTO replyConsult(@PathVariable Long id, @RequestParam String content) {
        merchantService.replyConsult(id, content);
        return Response.success("回复成功");
    }

    /**
     * 发布品牌动态
     */
    @PostMapping("/dynamic/publish")
    public ResponseDTO publishDynamic(@Valid @RequestBody DynamicPublishDTO publishDTO) {
        merchantService.publishDynamic(publishDTO);
        return Response.success("动态发布成功");
    }

    /**
     * 获取品牌动态列表
     */
    @GetMapping("/dynamic/list")
    public ResponseDTO getDynamicList(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {

        PageInfo<DynamicVO> pageInfo = merchantService.getDynamicList(pageNum, pageSize);
        return Response.success(pageInfo);
    }

    /**
     * 删除品牌动态
     */
    @DeleteMapping("/dynamic/{id}")
    public ResponseDTO deleteDynamic(@PathVariable Long id) {
        merchantService.deleteDynamic(id);
        return Response.success("动态已删除");
    }

    // ==================== 2.5 数据查看 ====================

    /**
     * 获取经营数据概览
     */
    @GetMapping("/data/overview")
    public ResponseDTO getBusinessOverview() {
        BusinessOverviewVO overview = merchantService.getBusinessOverview();
        return Response.success(overview);
    }

    /**
     * 获取销售额趋势
     */
    @GetMapping("/data/sales-trend")
    public ResponseDTO getSalesTrend(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDateTime startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDateTime endDate,
            @RequestParam(defaultValue = "day") String groupBy) {

        List<SalesTrendVO> trend = merchantService.getSalesTrend(startDate, endDate, groupBy);
        return Response.success(trend);
    }

    /**
     * 获取商品销售排行
     */
    @GetMapping("/data/product-rank")
    public ResponseDTO getProductRank(
            @RequestParam(defaultValue = "10") Integer limit) {

        List<ProductRankVO> rank = merchantService.getProductRank(limit);
        return Response.success(rank);
    }

    /**
     * 获取店铺评价列表
     */
    @GetMapping("/data/reviews")
    public ResponseDTO getShopReviews(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {

        PageInfo<ShopReviewVO> pageInfo = merchantService.getShopReviews(pageNum, pageSize);
        return Response.success(pageInfo);
    }

    /**
     * 获取店铺评分详情
     */
    @GetMapping("/data/rating")
    public ResponseDTO getShopRating() {
        ShopRatingVO rating = merchantService.getShopRating();
        return Response.success(rating);
    }

    /**
     * 回复用户评价
     */
    @PostMapping("/data/review/{id}/reply")
    public ResponseDTO replyReview(@PathVariable Long id, @RequestParam String content) {
        merchantService.replyReview(id, content);
        return Response.success("回复成功");
    }
}