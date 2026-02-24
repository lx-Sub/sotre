package com.rabbiter.hrm.controller;

import com.rabbiter.hrm.dto.*;
import com.rabbiter.hrm.service.UserService;
import com.rabbiter.hrm.vo.*;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.Valid;
import java.util.List;

/**
 * 普通用户控制器
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    // ==================== 3.1 账号与个人中心 ====================

    /**
     * 发送注册验证码
     */
    @PostMapping("/auth/send-code")
    public ResponseDTO sendVerificationCode(@RequestParam String phone) {
        userService.sendVerificationCode(phone);
        return Response.success("验证码发送成功");
    }

    /**
     * 手机号注册
     */
    @PostMapping("/auth/register")
    public ResponseDTO register(@Valid @RequestBody RegisterDTO registerDTO) {
        String token = userService.register(registerDTO);
        return Response.success("注册成功");
    }

    /**
     * 手机号登录
     */
    @PostMapping("/auth/login")
    public ResponseDTO login(@Valid @RequestBody LoginDTO loginDTO) {
        String token = userService.login(loginDTO);
        return Response.success("登录成功");
    }

    /**
     * 获取当前用户信息
     */
    @GetMapping("/profile")
    public ResponseDTO getCurrentUser() {
        UserProfileVO profile = userService.getCurrentUser();
        return Response.success(profile);
    }

    /**
     * 更新个人资料
     */
    @PutMapping("/profile/update")
    public ResponseDTO updateProfile(@Valid @RequestBody UserProfileUpdateDTO updateDTO) {
        userService.updateProfile(updateDTO);
        return Response.success("资料更新成功");
    }

    /**
     * 上传头像
     */
    @PostMapping("/profile/avatar")
    public ResponseDTO uploadAvatar(@RequestParam("file") MultipartFile file) {
        String avatarUrl = userService.uploadAvatar(file);
        return Response.success("头像上传成功");
    }

    /**
     * 修改密码
     */
    @PutMapping("/profile/password")
    public ResponseDTO changePassword(@Valid @RequestBody ChangePasswordDTO changeDTO) {
        userService.changePassword(changeDTO);
        return Response.success("密码修改成功");
    }

    /**
     * 获取收货地址列表
     */
    @GetMapping("/address/list")
    public ResponseDTO getAddressList() {
        List<AddressVO> addresses = userService.getAddressList();
        return Response.success(addresses);
    }

    /**
     * 添加收货地址
     */
    @PostMapping("/address/add")
    public ResponseDTO addAddress(@Valid @RequestBody AddressDTO addressDTO) {
        userService.addAddress(addressDTO);
        return Response.success("地址添加成功");
    }

    /**
     * 更新收货地址
     */
    @PutMapping("/address/{id}/update")
    public ResponseDTO updateAddress(@PathVariable Long id, @Valid @RequestBody AddressDTO addressDTO) {
        addressDTO.setId(id);
        userService.updateAddress(addressDTO);
        return Response.success("地址更新成功");
    }

    /**
     * 删除收货地址
     */
    @DeleteMapping("/address/{id}")
    public ResponseDTO deleteAddress(@PathVariable Long id) {
        userService.deleteAddress(id);
        return Response.success("地址删除成功");
    }

    /**
     * 设置默认地址
     */
    @PutMapping("/address/{id}/default")
    public ResponseDTO setDefaultAddress(@PathVariable Long id) {
        userService.setDefaultAddress(id);
        return Response.success("默认地址设置成功");
    }

    /**
     * 获取我的信用信息
     */
    @GetMapping("/credit/info")
    public ResponseDTO getCreditInfo() {
        CreditInfoVO creditInfo = userService.getCreditInfo();
        return Response.success(creditInfo);
    }

    /**
     * 获取信用分变更历史
     */
    @GetMapping("/credit/history")
    public ResponseDTO getCreditHistory(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<CreditHistoryVO> history = userService.getCreditHistory(pageNum, pageSize);
        return Response.success(history);
    }

    /**
     * 获取信用规则
     */
    @GetMapping("/credit/rules")
    public ResponseDTO getCreditRules() {
        List<CreditRuleVO> rules = userService.getCreditRules();
        return Response.success(rules);
    }

    /**
     * 获取我的装备库列表
     */
    @GetMapping("/equipment/list")
    public ResponseDTO getEquipmentList(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String category) {
        PageInfo<EquipmentVO> pageInfo = userService.getEquipmentList(pageNum, pageSize, keyword, category);
        return Response.success(pageInfo);
    }

    /**
     * 添加装备
     */
    @PostMapping("/equipment/add")
    public ResponseDTO addEquipment(@Valid @RequestBody EquipmentDTO equipmentDTO) {
        userService.addEquipment(equipmentDTO);
        return Response.success("装备添加成功");
    }

    /**
     * 更新装备信息
     */
    @PutMapping("/equipment/{id}/update")
    public ResponseDTO updateEquipment(@PathVariable Long id, @Valid @RequestBody EquipmentDTO equipmentDTO) {
        equipmentDTO.setId(id);
        userService.updateEquipment(equipmentDTO);
        return Response.success("装备更新成功");
    }

    /**
     * 删除装备
     */
    @DeleteMapping("/equipment/{id}")
    public ResponseDTO deleteEquipment(@PathVariable Long id) {
        userService.deleteEquipment(id);
        return Response.success("装备删除成功");
    }

    /**
     * 获取装备详情
     */
    @GetMapping("/equipment/{id}")
    public ResponseDTO getEquipmentDetail(@PathVariable Long id) {
        EquipmentDetailVO equipment = userService.getEquipmentDetail(id);
        return Response.success(equipment);
    }

    /**
     * 上传装备图片
     */
    @PostMapping("/equipment/upload-image")
    public ResponseDTO uploadEquipmentImage(@RequestParam("file") MultipartFile file) {
        String imageUrl = userService.uploadEquipmentImage(file);
        return Response.success("图片上传成功");
    }

    /**
     * 获取心愿单列表
     */
    @GetMapping("/wish/list")
    public ResponseDTO getWishList(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<WishVO> pageInfo = userService.getWishList(pageNum, pageSize);
        return Response.success(pageInfo);
    }

    /**
     * 添加心愿单
     */
    @PostMapping("/wish/add")
    public ResponseDTO addWish(@Valid @RequestBody WishDTO wishDTO) {
        userService.addWish(wishDTO);
        return Response.success("心愿添加成功");
    }

    /**
     * 删除心愿单
     */
    @DeleteMapping("/wish/{id}")
    public ResponseDTO deleteWish(@PathVariable Long id) {
        userService.deleteWish(id);
        return Response.success("心愿删除成功");
    }

    /**
     * 获取收藏列表
     */
    @GetMapping("/favorite/list")
    public ResponseDTO getFavoriteList(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam Integer type) { // 1-商品 2-帖子 3-用户/商家
        PageInfo<FavoriteVO> pageInfo = userService.getFavoriteList(pageNum, pageSize, type);
        return Response.success(pageInfo);
    }

    /**
     * 添加收藏
     */
    @PostMapping("/favorite/add")
    public ResponseDTO addFavorite(@Valid @RequestBody FavoriteDTO favoriteDTO) {
        userService.addFavorite(favoriteDTO);
        return Response.success("收藏成功");
    }

    /**
     * 取消收藏
     */
    @DeleteMapping("/favorite/remove")
    public ResponseDTO removeFavorite(@RequestParam Integer type, @RequestParam Long targetId) {
        userService.removeFavorite(type, targetId);
        return Response.success("已取消收藏");
    }

    /**
     * 检查是否已收藏
     */
    @GetMapping("/favorite/check")
    public ResponseDTO checkFavorite(@RequestParam Integer type, @RequestParam Long targetId) {
        boolean isFavorited = userService.checkFavorite(type, targetId);
        return Response.success(isFavorited);
    }

    /**
     * 获取关注列表
     */
    @GetMapping("/follow/list")
    public ResponseDTO getFollowList(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<FollowVO> pageInfo = userService.getFollowList(pageNum, pageSize);
        return Response.success(pageInfo);
    }

    /**
     * 关注用户/商家
     */
    @PostMapping("/follow/add")
    public ResponseDTO addFollow(@RequestParam Long targetUserId) {
        userService.addFollow(targetUserId);
        return Response.success("关注成功");
    }

    /**
     * 取消关注
     */
    @DeleteMapping("/follow/remove")
    public ResponseDTO removeFollow(@RequestParam Long targetUserId) {
        userService.removeFollow(targetUserId);
        return Response.success("已取消关注");
    }

    /**
     * 检查是否已关注
     */
    @GetMapping("/follow/check")
    public ResponseDTO checkFollow(@RequestParam Long targetUserId) {
        boolean isFollowed = userService.checkFollow(targetUserId);
        return Response.success(isFollowed);
    }
}