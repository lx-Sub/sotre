package com.rabbiter.hrm.mapper;

import com.rabbiter.hrm.entity.ExchangeOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 交换订单Mapper接口
 * @author 李鑫
 * @date 2026/2/24
 */
@Mapper
public interface ExchangeOrderMapper {

    /**
     * 根据ID查询交换订单
     */
    ExchangeOrder selectById(@Param("id") Long id);

    /**
     * 根据交换单号查询
     */
    ExchangeOrder selectByExchangeNo(@Param("exchangeNo") String exchangeNo);

    /**
     * 根据条件查询交换订单
     */
    List<ExchangeOrder> selectByCondition(@Param("exchangeNo") String exchangeNo,
                                          @Param("status") Integer status);

    /**
     * 根据用户ID查询交换订单
     */
    List<ExchangeOrder> selectByUserId(@Param("userId") Long userId);

    /**
     * 根据发起方ID查询
     */
    List<ExchangeOrder> selectByInitiatorId(@Param("initiatorId") Long initiatorId);

    /**
     * 根据接收方ID查询
     */
    List<ExchangeOrder> selectByReceiverId(@Param("receiverId") Long receiverId);

    /**
     * 查询待处理的交换订单
     */
    List<ExchangeOrder> selectPending();

    /**
     * 查询所有交换订单
     */
    List<ExchangeOrder> selectAll();

    /**
     * 插入交换订单
     */
    void insert(ExchangeOrder exchangeOrder);

    /**
     * 更新交换订单
     */
    void updateById(ExchangeOrder exchangeOrder);

    /**
     * 更新交换订单状态
     */
    void updateStatus(@Param("id") Long id, @Param("status") Integer status);

    /**
     * 更新发起方发货信息
     */
    void updateInitiatorShipping(@Param("id") Long id,
                                 @Param("initiatorLogisticsNo") String logisticsNo,
                                 @Param("initiatorShipTime") java.time.LocalDateTime shipTime);

    /**
     * 更新接收方发货信息
     */
    void updateReceiverShipping(@Param("id") Long id,
                                @Param("receiverLogisticsNo") String logisticsNo,
                                @Param("receiverShipTime") java.time.LocalDateTime shipTime);

    /**
     * 更新收货信息
     */
    void updateReceiveInfo(@Param("id") Long id,
                           @Param("initiatorReceiveTime") java.time.LocalDateTime initiatorReceiveTime,
                           @Param("receiverReceiveTime") java.time.LocalDateTime receiverReceiveTime,
                           @Param("completeTime") java.time.LocalDateTime completeTime);

    /**
     * 统计用户交换订单数
     */
    int countByUserId(@Param("userId") Long userId);

    /**
     * 统计待处理交换订单数
     */
    int countPending();

    /**
     * 删除交换订单
     */
    void deleteById(@Param("id") Long id);
}