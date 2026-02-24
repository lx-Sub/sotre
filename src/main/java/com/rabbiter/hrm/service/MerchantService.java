package com.rabbiter.hrm.service;

import com.rabbiter.hrm.dto.*;
import com.rabbiter.hrm.vo.*;
import com.github.pagehelper.PageInfo;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 商家服务接口
 */
public interface MerchantService {

    // ==================== 2.1 店铺管理 ====================

    /**
     * 商家入驻申请
     */
    void applyShop(ShopApplyDTO applyDTO);

    /**
     * 获取店铺信息
     */
    ShopVO getShopInfo();

    /**
     * 更新店铺信息
     */
    void updateShop(ShopUpdateDTO updateDTO);

    /**
     * 获取入驻审核状态
     */
    AuditStatusVO getAuditStatus();


    // ==================== 2.2 商品管理 ====================

    /**
     * 发布商品
     */
    void publishProduct(ProductPublishDTO publishDTO);

    /**
     * 批量发布商品
     */
    void batchPublishProducts(List<ProductPublishDTO> publishDTOs);

    /**
     * 获取商品列表
     */
    PageInfo<MerchantProductVO> getProductList(Integer pageNum, Integer pageSize, String keyword, String category, Integer status);

    /**
     * 获取商品详情
     */
    MerchantProductDetailVO getProductDetail(Long id);

    /**
     * 更新商品
     */
    void updateProduct(ProductUpdateDTO updateDTO);

    /**
     * 上架商品
     */
    void onSaleProduct(Long id);

    /**
     * 下架商品
     */
    void offSaleProduct(Long id);

    /**
     * 删除商品
     */
    void deleteProduct(Long id);


    // ==================== 优惠券管理 ====================

    /**
     * 创建优惠券
     */
    void createCoupon(CouponCreateDTO createDTO);

    /**
     * 获取优惠券列表
     */
    PageInfo<CouponVO> getCouponList(Integer pageNum, Integer pageSize, Integer status);

    /**
     * 更新优惠券状态
     */
    void updateCouponStatus(Long id, Integer status);

    /**
     * 删除优惠券
     */
    void deleteCoupon(Long id);


    // ==================== 满减活动管理 ====================

    /**
     * 创建满减活动
     */
    void createFullReduction(FullReductionCreateDTO createDTO);

    /**
     * 获取满减活动列表
     */
    PageInfo<FullReductionVO> getFullReductionList(Integer pageNum, Integer pageSize, Integer status);

    /**
     * 更新满减活动状态
     */
    void updateFullReductionStatus(Long id, Integer status);


    // ==================== 2.3 订单管理 ====================

    /**
     * 获取待处理订单
     */
    PageInfo<MerchantOrderVO> getPendingOrders(Integer pageNum, Integer pageSize);

    /**
     * 获取订单列表
     */
    PageInfo<MerchantOrderVO> getOrderList(OrderQueryDTO queryDTO);

    /**
     * 获取订单详情
     */
    MerchantOrderDetailVO getOrderDetail(Long id);

    /**
     * 发货
     */
    void shipOrder(ShipDTO shipDTO);

    /**
     * 批量发货
     */
    void batchShipOrders(List<ShipDTO> shipDTOs);

    /**
     * 修改订单金额
     */
    void modifyOrderAmount(Long id, BigDecimal amount);

    /**
     * 关闭订单
     */
    void closeOrder(Long id, String reason);


    // ==================== 售后处理 ====================

    /**
     * 获取售后列表
     */
    PageInfo<MerchantAfterSaleVO> getAfterSaleList(Integer pageNum, Integer pageSize, Integer status);

    /**
     * 获取售后详情
     */
    MerchantAfterSaleDetailVO getAfterSaleDetail(Long id);

    /**
     * 同意售后
     */
    void agreeAfterSale(Long id, String remark);

    /**
     * 拒绝售后
     */
    void rejectAfterSale(Long id, String reason);

    /**
     * 确认收到退货
     */
    void confirmReturnReceipt(Long id);


    // ==================== 2.4 社区运营与消息 ====================

    /**
     * 获取咨询列表
     */
    PageInfo<ConsultVO> getConsultList(Integer pageNum, Integer pageSize, Integer status);

    /**
     * 回复咨询
     */
    void replyConsult(Long id, String content);

    /**
     * 发布品牌动态
     */
    void publishDynamic(DynamicPublishDTO publishDTO);

    /**
     * 获取动态列表
     */
    PageInfo<DynamicVO> getDynamicList(Integer pageNum, Integer pageSize);

    /**
     * 删除动态
     */
    void deleteDynamic(Long id);


    // ==================== 2.5 数据查看 ====================

    /**
     * 获取经营数据概览
     */
    BusinessOverviewVO getBusinessOverview();

    /**
     * 获取销售趋势
     */
    List<SalesTrendVO> getSalesTrend(LocalDateTime startDate, LocalDateTime endDate, String groupBy);

    /**
     * 获取商品销售排行
     */
    List<ProductRankVO> getProductRank(Integer limit);

    /**
     * 获取店铺评价列表
     */
    PageInfo<ShopReviewVO> getShopReviews(Integer pageNum, Integer pageSize);

    /**
     * 获取店铺评分详情
     */
    ShopRatingVO getShopRating();

    /**
     * 回复评价
     */
    void replyReview(Long id, String content);
}