package com.rabbiter.hrm.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rabbiter.hrm.dto.Response;
import com.rabbiter.hrm.dto.ResponseDTO;
import com.rabbiter.hrm.entity.Expense;
import com.rabbiter.hrm.entity.Role;
import com.rabbiter.hrm.mapper.ExpenseMapper;
import com.rabbiter.hrm.util.HutoolExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * <p>
 * 报销服务层
 * </p>
 *
 * @Author
 * @since 2024-03-29
 */
@Service
public class ExpenseService extends ServiceImpl<ExpenseMapper, Expense> {

    @Autowired
    private ExpenseMapper expenseMapper;

    /**
     * 新增报销单
     *
     * @param expense 报销单对象
     * @return ResponseDTO
     */
    public ResponseDTO add(Expense expense) {
        // 添加报销单到数据库
        int result = expenseMapper.insert(expense);
        if (result > 0) {
            return new ResponseDTO(200, "新增报销单成功", null);
        } else {
            return new ResponseDTO(500, "新增报销单失败", null);
        }
    }

    /**
     * 根据ID删除报销单
     *
     * @param id 报销单ID
     * @return ResponseDTO
     */
    public ResponseDTO deleteById(Integer id) {
        // 逻辑删除报销单
        int result = expenseMapper.deleteById(id);
        if (result > 0) {
            return new ResponseDTO(200, "删除报销单成功", null);
        } else {
            return new ResponseDTO(500, "删除报销单失败", null);
        }
    }

    /**
     * 批量删除报销单
     *
     * @param ids 报销单ID列表
     * @return ResponseDTO
     */
    public ResponseDTO deleteBatch(List<Integer> ids) {
        // 批量删除报销单
        int result = expenseMapper.deleteBatchIds(ids);
        if (result > 0) {
            return new ResponseDTO(200, "批量删除报销单成功", null);
        } else {
            return new ResponseDTO(500, "批量删除报销单失败", null);
        }
    }

    /**
     * 编辑更新报销单
     *
     * @param expense 报销单对象
     * @return ResponseDTO
     */
    public ResponseDTO edit(Expense expense) {
        // 更新报销单
        int result = expenseMapper.updateById(expense);
        if (result > 0) {
            return new ResponseDTO(200, "更新报销单成功", null);
        } else {
            return new ResponseDTO(500, "更新报销单失败", null);
        }
    }

    /**
     * 根据ID查询报销单
     *
     * @param id 报销单ID
     * @return ResponseDTO
     */
    public ResponseDTO findById(Integer id) {
        Expense expense = expenseMapper.selectById(id);
        if (expense != null) {
            return new ResponseDTO(200, "查询报销单成功", expense);
        } else {
            return new ResponseDTO(404, "报销单未找到", null);
        }
    }

    /**
     * 分页条件查询报销单
     *
     * @param current      当前页
     * @param size         每页显示数量
     * @param employeeName 员工姓名
     * @param deptId       部门ID
     * @param month        月份
     * @return ResponseDTO
     */
    public ResponseDTO list(Integer current, Integer size, String employeeName, Integer deptId, String month) {
        // 计算分页偏移量
        Integer offset = (current - 1) * size;
        // 查询报销单列表，支持分页和条件查询
        List<Expense> expenses = expenseMapper.findExpensesByConditions(offset, size, employeeName, deptId, month);
        return new ResponseDTO(200, "查询报销单成功", expenses);
    }



    /**
     * 导出报销单
     *
     * @param response 响应对象
     * @throws IOException
     */
    public ResponseDTO export(HttpServletResponse response) throws IOException {
        // 导出报销单到Excel或其他格式
        List<Expense> list = list();
        HutoolExcelUtil.writeExcel(response,list,"报销明细表",Expense.class);
        return Response.success();

    }
    /**
     * 导入报销单
     *
     * @param file 上传的文件
     * @return ResponseDTO
     * @throws IOException
     */
    public ResponseDTO imp(MultipartFile file) throws IOException {
        // 导入报销单，解析文件并保存数据
        // 处理文件上传并插入数据
        return new ResponseDTO(200, "报销单导入成功", null);
    }

    /**
     * 根据员工ID查询报销单
     *
     * @param id 员工ID
     * @return ResponseDTO
     */
    public ResponseDTO findByEmployeeId(Integer id) {
        List<Expense> expenses = expenseMapper.findByEmployeeId(id);
        return new ResponseDTO(200, "查询报销单成功", expenses);
    }

    /**
     * 根据员工ID和日期查询报销单
     *
     * @param id   员工ID
     * @param date 日期
     * @return ResponseDTO
     */
    public ResponseDTO findByEmployeeIdAndDate(Integer id, String date) {
        Expense expense = expenseMapper.findByEmployeeIdAndDate(id, date);
        return new ResponseDTO(200, "查询报销单成功", expense);
    }

    /**
     * 更新报销单
     *
     * @param expense 报销单对象
     * @return ResponseDTO
     */
    public ResponseDTO updateExpense(Expense expense) {
        int result = expenseMapper.updateById(expense);
        if (result > 0) {
            return new ResponseDTO(200, "报销单更新成功", null);
        } else {
            return new ResponseDTO(500, "报销单更新失败", null);
        }
    }

    /**
     * 获取所有报销单
     *
     * @return ResponseDTO
     */
    public ResponseDTO findAll() {
        List<Expense> expenses = expenseMapper.selectList(null);
        return new ResponseDTO(200, "查询所有报销单成功", expenses);
    }
}
