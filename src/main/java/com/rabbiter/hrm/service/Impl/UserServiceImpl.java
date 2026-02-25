package com.rabbiter.hrm.service.Impl;

import com.rabbiter.hrm.dto.*;
import com.rabbiter.hrm.entity.*;
import com.rabbiter.hrm.enums.BusinessStatusEnum;
import com.rabbiter.hrm.exception.BusinessException;
import com.rabbiter.hrm.mapper.*;
import com.rabbiter.hrm.service.UserService;
import com.rabbiter.hrm.util.JWTUtil;
import com.rabbiter.hrm.util.RedisUtil;
import com.rabbiter.hrm.util.SecurityUtils;
import com.rabbiter.hrm.vo.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 用户服务实现类
 * @author 李鑫
 * @date 2026/2/24
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private AddressMapper addressMapper;

    @Autowired
    private CreditRecordMapper creditRecordMapper;

    @Autowired
    private CreditRuleMapper creditRuleMapper;

    @Autowired
    private EquipmentMapper equipmentMapper;

    @Autowired
    private WishMapper wishMapper;

    @Autowired
    private FavoriteMapper favoriteMapper;

    @Autowired
    private FollowMapper followMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private JWTUtil jwtUtil;

    @Value("${upload.avatar.path:/uploads/avatar}")
    private String avatarUploadPath;

    @Value("${upload.equipment.path:/uploads/equipment}")
    private String equipmentUploadPath;

    // ==================== 认证相关 ====================

    @Override
    public void sendVerificationCode(String phone) {
        // 生成6位随机验证码
        String code = String.valueOf((int) ((Math.random() * 9 + 1) * 100000));

        // 存入Redis，有效期5分钟
        redisUtil.set("SMS:" + phone, code, 300);

        // TODO: 调用短信服务发送验证码
        log.info("验证码发送成功，手机号：{}，验证码：{}", phone, code);
    }

    @Override
    @Transactional
    public String register(RegisterDTO registerDTO) {
        // 验证两次密码是否一致
        if (!registerDTO.getPassword().equals(registerDTO.getConfirmPassword())) {
            throw new BusinessException(BusinessStatusEnum.PARAM_ERROR.getCode(), "两次密码不一致");
        }

        // 检查手机号是否已注册
        User existUser = userMapper.selectByPhone(registerDTO.getPhone());
        if (existUser != null) {
            throw new BusinessException(BusinessStatusEnum.USER_EXISTS.getCode(), "手机号已注册");
        }

        // 创建新用户
        User user = new User();
        user.setUsername(registerDTO.getPhone()); // 默认用户名为手机号
        user.setPhone(registerDTO.getPhone());
        user.setNickname(registerDTO.getNickname() != null ? registerDTO.getNickname() : "用户" + registerDTO.getPhone().substring(7));
        user.setPassword(registerDTO.getPassword());
        user.setRole(registerDTO.getRole()); // 角色：1-普通用户 2-商家 3-管理员
        user.setStatus(1); // 正常
        user.setCreditScore(600); // 初始信用分
        user.setVerified(false);
        user.setConsignmentEnabled(false);
        user.setCreateTime(LocalDateTime.now());

        userMapper.insert(user);

        // 生成token
        return jwtUtil.generateToken(user.getId().intValue(), user.getPhone());
    }

    @Override
    public String login(LoginDTO loginDTO) {
        String token = "";
        if (!"3".equals(loginDTO.getRole())){
            // 根据手机号查询用户
            User user = userMapper.selectByPhone(loginDTO.getPhone());
            if (user == null) {
                throw new BusinessException(BusinessStatusEnum.USER_NOT_FOUND.getCode(), "用户不存在");
            }

            // 检查用户状态
            if (user.getStatus() != 1) {
                throw new BusinessException(BusinessStatusEnum.USER_FROZEN.getCode(), "账号已被冻结");
            }

            // 更新最后登录时间
            user.setLastLoginTime(LocalDateTime.now());
            user.setLastLoginIp(SecurityUtils.getClientIp());
            userMapper.updateById(user);
            SecurityUtils.setCurrentUser(user);

            // 生成token
            token = jwtUtil.generateToken(user.getId().intValue(), user.getPhone());
        }else {
            Admin admin = adminMapper.selectByUsername(loginDTO.getPhone());
            SecurityUtils.setCurrentAdmin(admin);
            token = jwtUtil.generateToken(admin.getId().intValue(), admin.getPhone());
        }

       return token;
    }

    // ==================== 个人资料 ====================

    @Override
    public UserProfileVO getCurrentUser() {
        Long currentUserId = SecurityUtils.getCurrentUserId();

        User user = userMapper.selectById(currentUserId);
        if (user == null) {
            throw new BusinessException(BusinessStatusEnum.USER_NOT_FOUND.getCode(), "用户不存在");
        }

        UserProfileVO vo = new UserProfileVO();
        BeanUtils.copyProperties(user, vo);

        // 设置信用等级
        vo.setCreditLevel(getCreditLevel(user.getCreditScore()));
        vo.setCreditBadge(getCreditBadge(user.getCreditScore()));

        return vo;
    }

    @Override
    @Transactional
    public void updateProfile(UserProfileUpdateDTO updateDTO) {
        Long currentUserId = SecurityUtils.getCurrentUserId();

        User user = userMapper.selectById(currentUserId);
        if (user == null) {
            throw new BusinessException(BusinessStatusEnum.USER_NOT_FOUND.getCode(), "用户不存在");
        }

        BeanUtils.copyProperties(updateDTO, user);
        userMapper.updateById(user);
    }

    @Override
    public String uploadAvatar(MultipartFile file) {
        try {
            // 生成文件名
            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            Path uploadPath = Paths.get(avatarUploadPath);

            // 创建目录
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // 保存文件
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath);

            // 返回访问URL
            return "/uploads/avatar/" + fileName;

        } catch (IOException e) {
            log.error("头像上传失败", e);
            throw new BusinessException("头像上传失败");
        }
    }

    @Override
    @Transactional
    public void changePassword(ChangePasswordDTO changeDTO) {
        Long currentUserId = SecurityUtils.getCurrentUserId();

        User user = userMapper.selectById(currentUserId);
        if (user == null) {
            throw new BusinessException(BusinessStatusEnum.USER_NOT_FOUND.getCode(), "用户不存在");
        }

        // 验证新密码和确认密码
        if (!changeDTO.getNewPassword().equals(changeDTO.getConfirmPassword())) {
            throw new BusinessException(BusinessStatusEnum.PARAM_ERROR.getCode(), "两次密码不一致");
        }

        // 更新密码
        user.setPassword(changeDTO.getNewPassword());
        userMapper.updateById(user);
    }

    // ==================== 收货地址 ====================

    @Override
    public List<AddressVO> getAddressList() {
        Long currentUserId = SecurityUtils.getCurrentUserId();

        List<UserAddress> addresses = addressMapper.selectByUserId(currentUserId);

        return addresses.stream().map(address -> {
            AddressVO vo = new AddressVO();
            BeanUtils.copyProperties(address, vo);
            return vo;
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void addAddress(AddressDTO addressDTO) {
        Long currentUserId = SecurityUtils.getCurrentUserId();

        // 如果是默认地址，取消其他默认地址
        if (addressDTO.getIsDefault() == 1) {
            addressMapper.clearDefaultAddress(currentUserId);
        }

        UserAddress address = new UserAddress();
        BeanUtils.copyProperties(addressDTO, address);
        address.setUserId(currentUserId);
        address.setCreateTime(LocalDateTime.now());

        addressMapper.insert(address);
    }

    @Override
    @Transactional
    public void updateAddress(AddressDTO addressDTO) {
        Long currentUserId = SecurityUtils.getCurrentUserId();

        UserAddress address = addressMapper.selectById(addressDTO.getId());
        if (address == null || !address.getUserId().equals(currentUserId)) {
            throw new BusinessException(BusinessStatusEnum.NOT_FOUND.getCode(), "地址不存在");
        }

        // 如果是默认地址，取消其他默认地址
        if (addressDTO.getIsDefault() == 1) {
            addressMapper.clearDefaultAddress(currentUserId);
        }

        BeanUtils.copyProperties(addressDTO, address);
        address.setUpdateTime(LocalDateTime.now());

        addressMapper.updateById(address);
    }

    @Override
    @Transactional
    public void deleteAddress(Long id) {
        Long currentUserId = SecurityUtils.getCurrentUserId();

        UserAddress address = addressMapper.selectById(id);
        if (address == null || !address.getUserId().equals(currentUserId)) {
            throw new BusinessException(BusinessStatusEnum.NOT_FOUND.getCode(), "地址不存在");
        }

        addressMapper.deleteById(id);
    }

    @Override
    @Transactional
    public void setDefaultAddress(Long id) {
        Long currentUserId = SecurityUtils.getCurrentUserId();

        UserAddress address = addressMapper.selectById(id);
        if (address == null || !address.getUserId().equals(currentUserId)) {
            throw new BusinessException(BusinessStatusEnum.NOT_FOUND.getCode(), "地址不存在");
        }

        // 取消其他默认地址
        addressMapper.clearDefaultAddress(currentUserId);

        // 设置新的默认地址
        address.setIsDefault(1);
        addressMapper.updateById(address);
    }

    // ==================== 信用管理 ====================

    @Override
    public CreditInfoVO getCreditInfo() {
        Long currentUserId = SecurityUtils.getCurrentUserId();

        User user = userMapper.selectById(currentUserId);
        if (user == null) {
            throw new BusinessException(BusinessStatusEnum.USER_NOT_FOUND.getCode(), "用户不存在");
        }

        CreditInfoVO vo = new CreditInfoVO();
        vo.setCurrentScore(user.getCreditScore());
        vo.setCreditLevel(getCreditLevel(user.getCreditScore()));
        vo.setCreditBadge(getCreditBadge(user.getCreditScore()));

        // 计算下一等级所需分数
        int nextLevelScore = getNextLevelScore(user.getCreditScore());
        vo.setNextLevelScore(nextLevelScore);

        // 计算进度百分比
        int progress = calculateProgress(user.getCreditScore());
        vo.setProgressPercent(progress);

        // 统计总加分和总扣分
        Map<String, Object> stats = creditRecordMapper.statisticsByUserId(currentUserId);
        vo.setTotalAdded(((Number) stats.get("total_added")).intValue());
        vo.setTotalDeducted(((Number) stats.get("total_deducted")).intValue());

        // 获取信用规则
        vo.setRules(getCreditRules());

        return vo;
    }

    @Override
    public PageInfo<CreditHistoryVO> getCreditHistory(Integer pageNum, Integer pageSize) {
        Long currentUserId = SecurityUtils.getCurrentUserId();

        PageHelper.startPage(pageNum, pageSize);
        List<CreditRecord> records = creditRecordMapper.selectByUserId(currentUserId);

        List<CreditHistoryVO> vos = records.stream().map(record -> {
            CreditHistoryVO vo = new CreditHistoryVO();
            BeanUtils.copyProperties(record, vo);
            return vo;
        }).collect(Collectors.toList());

        return new PageInfo<>(vos);
    }

    @Override
    public List<CreditRuleVO> getCreditRules() {
        List<CreditRule> rules = creditRuleMapper.selectAll();

        return rules.stream().map(rule -> {
            CreditRuleVO vo = new CreditRuleVO();
            BeanUtils.copyProperties(rule, vo);
            return vo;
        }).collect(Collectors.toList());
    }

    // ==================== 装备库 ====================

    @Override
    public PageInfo<EquipmentVO> getEquipmentList(Integer pageNum, Integer pageSize, String keyword, String category) {
        Long currentUserId = SecurityUtils.getCurrentUserId();

        PageHelper.startPage(pageNum, pageSize);
        List<UserEquipment> equipments = equipmentMapper.selectByUserId(currentUserId, keyword, category);

        List<EquipmentVO> vos = equipments.stream().map(equipment -> {
            EquipmentVO vo = new EquipmentVO();
            BeanUtils.copyProperties(equipment, vo);

            // 设置封面图
            if (equipment.getImages() != null) {
                List<String> images = com.alibaba.fastjson.JSON.parseArray(equipment.getImages(), String.class);
                if (!images.isEmpty()) {
                    vo.setCoverImage(images.get(0));
                }
            }

            return vo;
        }).collect(Collectors.toList());

        return new PageInfo<>(vos);
    }

    @Override
    @Transactional
    public void addEquipment(EquipmentDTO equipmentDTO) {
        Long currentUserId = SecurityUtils.getCurrentUserId();

        UserEquipment equipment = new UserEquipment();
        BeanUtils.copyProperties(equipmentDTO, equipment);
        equipment.setUserId(currentUserId);

        // 处理图片JSON
        if (equipmentDTO.getImages() != null) {
            equipment.setImages(com.alibaba.fastjson.JSON.toJSONString(equipmentDTO.getImages()));
        }

        equipment.setCreateTime(LocalDateTime.now());
        equipmentMapper.insert(equipment);
    }

    @Override
    @Transactional
    public void updateEquipment(EquipmentDTO equipmentDTO) {
        Long currentUserId = SecurityUtils.getCurrentUserId();

        UserEquipment equipment = equipmentMapper.selectById(equipmentDTO.getId());
        if (equipment == null || !equipment.getUserId().equals(currentUserId)) {
            throw new BusinessException(BusinessStatusEnum.NOT_FOUND.getCode(), "装备不存在");
        }

        BeanUtils.copyProperties(equipmentDTO, equipment);

        // 处理图片JSON
        if (equipmentDTO.getImages() != null) {
            equipment.setImages(com.alibaba.fastjson.JSON.toJSONString(equipmentDTO.getImages()));
        }

        equipment.setUpdateTime(LocalDateTime.now());
        equipmentMapper.updateById(equipment);
    }

    @Override
    @Transactional
    public void deleteEquipment(Long id) {
        Long currentUserId = SecurityUtils.getCurrentUserId();

        UserEquipment equipment = equipmentMapper.selectById(id);
        if (equipment == null || !equipment.getUserId().equals(currentUserId)) {
            throw new BusinessException(BusinessStatusEnum.NOT_FOUND.getCode(), "装备不存在");
        }

        equipmentMapper.deleteById(id);
    }

    @Override
    public EquipmentDetailVO getEquipmentDetail(Long id) {
        Long currentUserId = SecurityUtils.getCurrentUserId();

        UserEquipment equipment = equipmentMapper.selectById(id);
        if (equipment == null || !equipment.getUserId().equals(currentUserId)) {
            throw new BusinessException(BusinessStatusEnum.NOT_FOUND.getCode(), "装备不存在");
        }

        EquipmentDetailVO vo = new EquipmentDetailVO();
        BeanUtils.copyProperties(equipment, vo);

        // 解析图片
        if (equipment.getImages() != null) {
            List<String> images = com.alibaba.fastjson.JSON.parseArray(equipment.getImages(), String.class);
            vo.setImages(images);
            if (!images.isEmpty()) {
                vo.setCoverImage(images.get(0));
            }
        }

        return vo;
    }

    @Override
    public String uploadEquipmentImage(MultipartFile file) {
        try {
            // 生成文件名
            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            Path uploadPath = Paths.get(equipmentUploadPath);

            // 创建目录
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // 保存文件
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath);

            // 返回访问URL
            return "/uploads/equipment/" + fileName;

        } catch (IOException e) {
            log.error("装备图片上传失败", e);
            throw new BusinessException("图片上传失败");
        }
    }

    // ==================== 心愿单 ====================

    @Override
    public PageInfo<WishVO> getWishList(Integer pageNum, Integer pageSize) {
        Long currentUserId = SecurityUtils.getCurrentUserId();

        PageHelper.startPage(pageNum, pageSize);
        List<UserWish> wishes = wishMapper.selectByUserId(currentUserId);

        List<WishVO> vos = wishes.stream().map(wish -> {
            WishVO vo = new WishVO();
            BeanUtils.copyProperties(wish, vo);
            return vo;
        }).collect(Collectors.toList());

        return new PageInfo<>(vos);
    }

    @Override
    @Transactional
    public void addWish(WishDTO wishDTO) {
        Long currentUserId = SecurityUtils.getCurrentUserId();

        UserWish wish = new UserWish();
        BeanUtils.copyProperties(wishDTO, wish);
        wish.setUserId(currentUserId);
        wish.setCreateTime(LocalDateTime.now());

        wishMapper.insert(wish);
    }

    @Override
    @Transactional
    public void deleteWish(Long id) {
        Long currentUserId = SecurityUtils.getCurrentUserId();

        UserWish wish = wishMapper.selectById(id);
        if (wish == null || !wish.getUserId().equals(currentUserId)) {
            throw new BusinessException(BusinessStatusEnum.NOT_FOUND.getCode(), "心愿不存在");
        }

        wishMapper.deleteById(id);
    }

    // ==================== 收藏关注 ====================

    @Override
    public PageInfo<FavoriteVO> getFavoriteList(Integer pageNum, Integer pageSize, Integer type) {
        Long currentUserId = SecurityUtils.getCurrentUserId();

        PageHelper.startPage(pageNum, pageSize);
        List<UserFavorite> favorites = favoriteMapper.selectByUserId(currentUserId, type);

        List<FavoriteVO> vos = favorites.stream().map(favorite -> {
            FavoriteVO vo = new FavoriteVO();
            BeanUtils.copyProperties(favorite, vo);

            // 根据类型获取目标信息
            if (favorite.getType() == 1) { // 商品
                // TODO: 查询商品信息
            } else if (favorite.getType() == 2) { // 帖子
                // TODO: 查询帖子信息
            } else if (favorite.getType() == 3) { // 用户/商家
                // TODO: 查询用户信息
            }

            return vo;
        }).collect(Collectors.toList());

        return new PageInfo<>(vos);
    }

    @Override
    @Transactional
    public void addFavorite(FavoriteDTO favoriteDTO) {
        Long currentUserId = SecurityUtils.getCurrentUserId();

        // 检查是否已收藏
        UserFavorite exist = favoriteMapper.selectByUserAndTarget(currentUserId,
                favoriteDTO.getType(), favoriteDTO.getTargetId());
        if (exist != null) {
            throw new BusinessException(BusinessStatusEnum.FORBIDDEN.getCode(), "已经收藏过了");
        }

        UserFavorite favorite = new UserFavorite();
        BeanUtils.copyProperties(favoriteDTO, favorite);
        favorite.setUserId(currentUserId);
        favorite.setCreateTime(LocalDateTime.now());

        favoriteMapper.insert(favorite);
    }

    @Override
    @Transactional
    public void removeFavorite(Integer type, Long targetId) {
        Long currentUserId = SecurityUtils.getCurrentUserId();

        favoriteMapper.deleteByUserAndTarget(currentUserId, type, targetId);
    }

    @Override
    public boolean checkFavorite(Integer type, Long targetId) {
        Long currentUserId = SecurityUtils.getCurrentUserId();

        UserFavorite favorite = favoriteMapper.selectByUserAndTarget(currentUserId, type, targetId);
        return favorite != null;
    }

    @Override
    public PageInfo<FollowVO> getFollowList(Integer pageNum, Integer pageSize) {
        Long currentUserId = SecurityUtils.getCurrentUserId();

        PageHelper.startPage(pageNum, pageSize);
        List<UserFollow> follows = followMapper.selectByUserId(currentUserId);

        List<FollowVO> vos = follows.stream().map(follow -> {
            FollowVO vo = new FollowVO();
            BeanUtils.copyProperties(follow, vo);

            // 获取目标用户信息
            User targetUser = userMapper.selectById(follow.getTargetUserId());
            if (targetUser != null) {
                vo.setTargetUsername(targetUser.getUsername());
                vo.setTargetNickname(targetUser.getNickname());
                vo.setTargetAvatar(targetUser.getAvatar());
                vo.setTargetRole(targetUser.getRole());
                vo.setTargetCreditLevel(getCreditLevel(targetUser.getCreditScore()));
            }

            // 检查是否互相关注
            UserFollow mutual = followMapper.selectByUserAndTarget(targetUser.getId(), currentUserId);
            vo.setIsMutual(mutual != null);

            return vo;
        }).collect(Collectors.toList());

        return new PageInfo<>(vos);
    }

    @Override
    @Transactional
    public void addFollow(Long targetUserId) {
        Long currentUserId = SecurityUtils.getCurrentUserId();

        if (currentUserId.equals(targetUserId)) {
            throw new BusinessException(BusinessStatusEnum.FORBIDDEN.getCode(), "不能关注自己");
        }

        // 检查是否已关注
        UserFollow exist = followMapper.selectByUserAndTarget(currentUserId, targetUserId);
        if (exist != null) {
            throw new BusinessException(BusinessStatusEnum.FORBIDDEN.getCode(), "已经关注过了");
        }

        // 检查目标用户是否存在
        User targetUser = userMapper.selectById(targetUserId);
        if (targetUser == null) {
            throw new BusinessException(BusinessStatusEnum.USER_NOT_FOUND.getCode(), "用户不存在");
        }

        UserFollow follow = new UserFollow();
        follow.setUserId(currentUserId);
        follow.setTargetUserId(targetUserId);
        follow.setCreateTime(LocalDateTime.now());

        followMapper.insert(follow);
    }

    @Override
    @Transactional
    public void removeFollow(Long targetUserId) {
        Long currentUserId = SecurityUtils.getCurrentUserId();

        followMapper.deleteByUserAndTarget(currentUserId, targetUserId);
    }

    @Override
    public boolean checkFollow(Long targetUserId) {
        Long currentUserId = SecurityUtils.getCurrentUserId();

        UserFollow follow = followMapper.selectByUserAndTarget(currentUserId, targetUserId);
        return follow != null;
    }

    // ==================== 私有辅助方法 ====================

    /**
     * 获取信用等级
     */
    private String getCreditLevel(Integer score) {
        if (score == null) return "未知";
        if (score >= 800) return "优秀";
        if (score >= 700) return "良好";
        if (score >= 600) return "一般";
        if (score >= 400) return "较差";
        return "极差";
    }

    /**
     * 获取信用徽章
     */
    private String getCreditBadge(Integer score) {
        if (score == null) return "unknown";
        if (score >= 800) return "diamond";
        if (score >= 700) return "gold";
        if (score >= 600) return "silver";
        if (score >= 400) return "bronze";
        return "iron";
    }

    /**
     * 获取下一等级所需分数
     */
    private Integer getNextLevelScore(Integer currentScore) {
        if (currentScore < 400) return 400;
        if (currentScore < 600) return 600;
        if (currentScore < 700) return 700;
        if (currentScore < 800) return 800;
        return 1000; // 满级
    }

    /**
     * 计算等级进度百分比
     */
    private Integer calculateProgress(Integer currentScore) {
        if (currentScore >= 800) return 100;
        if (currentScore >= 700) return (currentScore - 700) * 100 / 100;
        if (currentScore >= 600) return (currentScore - 600) * 100 / 100;
        if (currentScore >= 400) return (currentScore - 400) * 100 / 200;
        return currentScore * 100 / 400;
    }
}