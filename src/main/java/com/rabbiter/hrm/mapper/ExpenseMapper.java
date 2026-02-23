package com.rabbiter.hrm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rabbiter.hrm.entity.Expense;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * <p>
 * 报销单 Mapper 接口
 * </p>
 *
 * @Author
 * @since 2024-03-29
 */
public interface ExpenseMapper extends BaseMapper<Expense> {

    /**
     * 根据员工ID查询报销单
     *
     * @param id 员工ID
     * @return List<Expense>
     */
    @Select("SELECT * FROM hrm_expense WHERE employee_id = #{id} AND is_deleted = 0")
    List<Expense> findByEmployeeId(@Param("id") Integer id);

    /**
     * 根据员工ID和日期查询报销单
     *
     * @param id   员工ID
     * @param date 日期
     * @return Expense
     */
    @Select("SELECT * FROM hrm_expense WHERE employee_id = #{id} AND expense_date = #{date} AND is_deleted = 0")
    Expense findByEmployeeIdAndDate(@Param("id") Integer id, @Param("date") String date);

    /**
     * 根据条件查询报销单
     *
     * @param current      当前页
     * @param size         每页数量
     * @param employeeName 员工姓名
     * @param deptId       部门ID
     * @param month        月份
     * @return List<Expense>
     */
    @Select("<script>" +
            "SELECT * FROM hrm_expense WHERE is_deleted = 0" +
            "<if test='name != null and name != \"\"'> AND name LIKE CONCAT('%', #{name}, '%')</if>" +
            "<if test='deptId != null'> AND dept_id = #{deptId}</if>" +
            "<if test='date != null and date != \"\"'> AND DATE_FORMAT(expense_date, '%Y%m') = #{date}</if>" +
            "LIMIT #{current}, #{size}" +
            "</script>")
    List<Expense> findExpensesByConditions(@Param("current") Integer current,
                                           @Param("size") Integer size,
                                           @Param("name") String name,
                                           @Param("deptId") Integer deptId,
                                           @Param("date") String date);

    /**
     * 逻辑删除报销单
     *
     * @param id 报销单ID
     * @return int
     */
    @Update("UPDATE hrm_expense SET is_deleted = 1 WHERE expense_id = #{id}")
    int deleteById(@Param("id") Integer id);

    /**
     * 批量逻辑删除报销单
     *
     * @param ids 报销单ID列表
     * @return int
     */
    @Update("<script>" +
            "UPDATE hrm_expense SET is_deleted = 1 WHERE id IN " +
            "<foreach item='id' collection='list' open='(' separator=',' close=')'>" +
            "#{id}" +
            "</foreach>" +
            "</script>")
    int deleteBatchIds(@Param("list") List<Integer> ids);

    /**
     * 查找所有报销单
     *
     * @return List<Expense>
     */
    @Select("SELECT * FROM hrm_expense WHERE is_deleted = 0")
    List<Expense> findAll();

}
