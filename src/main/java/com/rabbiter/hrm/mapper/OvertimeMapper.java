package com.rabbiter.hrm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rabbiter.hrm.entity.Overtime;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 加班表 Mapper 接口
 * </p>
 *
 * @Author
 * @since 2024-03-28
 */
@Mapper
public interface OvertimeMapper extends BaseMapper<Overtime> {

}
