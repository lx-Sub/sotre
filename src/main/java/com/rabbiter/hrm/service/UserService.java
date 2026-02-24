package com.rabbiter.hrm.service;

import com.rabbiter.hrm.dto.*;
import com.rabbiter.hrm.vo.*;
import com.github.pagehelper.PageInfo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 用户服务接口
 * @author 李鑫
 * @date 2026/2/24
 */
public interface UserService {

    // ==================== 认证相关 ====================

    /**
     * 发送验证码
     */
    void sendVerificationCode(String phone);

    /**
     * 注册
     */
    String register(RegisterDTO registerDTO);

    /**
     * 登录
     */
    String login(LoginDTO loginDTO);


    // ==================== 个人资料 ====================

    /**
     * 获取当前用户信息
     */
    UserProfileVO getCurrentUser();

    /**
     * 更新个人资料
     */
    void updateProfile(UserProfileUpdateDTO updateDTO);

    /**
     * 上传头像
     */
    String uploadAvatar(MultipartFile file);

    /**
     * 修改密码
     */
    void changePassword(ChangePasswordDTO changeDTO);


    // ==================== 收货地址 ====================

    /**
     * 获取收货地址列表
     */
    List<AddressVO> getAddressList();

    /**
     * 添加收货地址
     */
    void addAddress(AddressDTO addressDTO);

    /**
     * 更新收货地址
     */
    void updateAddress(AddressDTO addressDTO);

    /**
     * 删除收货地址
     */
    void deleteAddress(Long id);

    /**
     * 设置默认地址
     */
    void setDefaultAddress(Long id);


    // ==================== 信用管理 ====================

    /**
     * 获取信用信息
     */
    CreditInfoVO getCreditInfo();

    /**
     * 获取信用历史
     */
    PageInfo<CreditHistoryVO> getCreditHistory(Integer pageNum, Integer pageSize);

    /**
     * 获取信用规则
     */
    List<CreditRuleVO> getCreditRules();


    // ==================== 装备库 ====================

    /**
     * 获取装备列表
     */
    PageInfo<EquipmentVO> getEquipmentList(Integer pageNum, Integer pageSize, String keyword, String category);

    /**
     * 添加装备
     */
    void addEquipment(EquipmentDTO equipmentDTO);

    /**
     * 更新装备
     */
    void updateEquipment(EquipmentDTO equipmentDTO);

    /**
     * 删除装备
     */
    void deleteEquipment(Long id);

    /**
     * 获取装备详情
     */
    EquipmentDetailVO getEquipmentDetail(Long id);

    /**
     * 上传装备图片
     */
    String uploadEquipmentImage(MultipartFile file);


    // ==================== 心愿单 ====================

    /**
     * 获取心愿单列表
     */
    PageInfo<WishVO> getWishList(Integer pageNum, Integer pageSize);

    /**
     * 添加心愿
     */
    void addWish(WishDTO wishDTO);

    /**
     * 删除心愿
     */
    void deleteWish(Long id);


    // ==================== 收藏关注 ====================

    /**
     * 获取收藏列表
     */
    PageInfo<FavoriteVO> getFavoriteList(Integer pageNum, Integer pageSize, Integer type);

    /**
     * 添加收藏
     */
    void addFavorite(FavoriteDTO favoriteDTO);

    /**
     * 取消收藏
     */
    void removeFavorite(Integer type, Long targetId);

    /**
     * 检查是否已收藏
     */
    boolean checkFavorite(Integer type, Long targetId);

    /**
     * 获取关注列表
     */
    PageInfo<FollowVO> getFollowList(Integer pageNum, Integer pageSize);

    /**
     * 添加关注
     */
    void addFollow(Long targetUserId);

    /**
     * 取消关注
     */
    void removeFollow(Long targetUserId);

    /**
     * 检查是否已关注
     */
    boolean checkFollow(Long targetUserId);
}