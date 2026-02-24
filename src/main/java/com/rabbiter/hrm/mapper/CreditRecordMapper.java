package com.rabbiter.hrm.mapper;

import com.rabbiter.hrm.entity.CreditRecord;
import com.rabbiter.hrm.entity.CreditAppeal;
import com.rabbiter.hrm.vo.CreditHistoryVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface CreditRecordMapper {

    /**
     * 插入信用记录
     */
    void insert(CreditRecord creditRecord);

    /**
     * 查询用户的信用记录
     */
    List<CreditRecord> selectByUserId(@Param("userId") Long userId);

    /**
     * 查询用户最近的信用记录
     * @param userId 用户ID
     * @param limit 限制条数
     */
    List<CreditHistoryVO> selectRecentByUserId(@Param("userId") Long userId,
                                               @Param("limit") Integer limit);

    /**
     * 统计用户违规次数
     */
    int countViolations(@Param("userId") Long userId);

    /**
     * 统计用户申诉次数
     */
    int countAppeals(@Param("userId") Long userId);

    /**
     * 根据ID查询申诉
     */
    CreditAppeal selectAppealById(@Param("id") Long id);

    /**
     * 更新申诉
     */
    void updateAppeal(CreditAppeal creditAppeal);
}