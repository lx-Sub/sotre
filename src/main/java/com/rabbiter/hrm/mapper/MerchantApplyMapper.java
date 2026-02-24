package com.rabbiter.hrm.mapper;


import com.rabbiter.hrm.entity.MerchantApply;
import com.rabbiter.hrm.vo.CredentialVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface MerchantApplyMapper {

    /**
     * 根据用户ID查询商家申请
     */
    MerchantApply selectByUserId(@Param("userId") Long userId);

    /**
     * 查询待审核的商家申请列表
     */
    List<MerchantApply> selectPending();

    /**
     * 查询所有商家申请
     */
    List<MerchantApply> selectAll();

    /**
     * 根据ID查询
     */
    MerchantApply selectById(@Param("id") Long id);

    /**
     * 插入商家申请
     */
    void insert(MerchantApply merchantApply);

    /**
     * 更新商家申请
     */
    void updateById(MerchantApply merchantApply);

    /**
     * 查询商家的资质文件
     */
    List<CredentialVO> selectCredentials(@Param("userId") Long userId);

    /**
     * 统计待审核数量
     */
    int countPending();
}