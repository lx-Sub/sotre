package com.rabbiter.hrm.mapper;

import com.rabbiter.hrm.entity.CreditRecord;
import com.rabbiter.hrm.entity.CreditAppeal;
import com.rabbiter.hrm.entity.User;
import com.rabbiter.hrm.vo.CreditHistoryVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

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

    // 在 CreditRecordMapper.java 中添加以下方法

    /**
     * 统计用户信用分变更趋势
     */
    List<Map<String, Object>> statisticsUserCredit(@Param("userId") Long userId,
                                                   @Param("startDate") LocalDateTime startDate,
                                                   @Param("endDate") LocalDateTime endDate);

    /**
     * 统计信用分变更类型分布
     */
    List<Map<String, Object>> statisticsByType(@Param("startDate") LocalDateTime startDate,
                                               @Param("endDate") LocalDateTime endDate);

    /**
     * 查询信用分最低的用户
     */
    List<User> selectLowestCreditUsers(@Param("limit") Integer limit);

    /**
     * 根据业务ID查询信用记录
     */
    List<CreditRecord> selectByBusinessId(@Param("businessId") Long businessId,
                                          @Param("type") String type);

    /**
     * 批量插入信用记录
     */
    void batchInsert(List<CreditRecord> records);

    /**
     * 根据类型统计数量
     */
    int countByType(@Param("type") String type);

    /**
     * 统计待处理的信用申诉
     */
    int countPendingAppeals();
}