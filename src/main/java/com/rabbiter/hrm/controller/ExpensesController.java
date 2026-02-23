package com.rabbiter.hrm.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.rabbiter.hrm.service.ExpenseService;
import com.rabbiter.hrm.entity.Expense;
import com.rabbiter.hrm.dto.ResponseDTO;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 报销控制器
 * </p>
 *
 * @Author
 * @since 2024-03-29
 */
@RestController
@RequestMapping("/expenses")
public class ExpensesController {

    @Resource
    private ExpenseService expenseService;

    @ApiOperation("新增报销单")
    @PostMapping
    public ResponseDTO add(@RequestBody Expense expense) {
        return this.expenseService.add(expense);
    }

    @ApiOperation("逻辑删除报销单")
    @DeleteMapping("/{id}")
    public ResponseDTO delete(@PathVariable Integer id) {
        return this.expenseService.deleteById(id);
    }

    @ApiOperation("批量删除报销单")
    @DeleteMapping("/batch/{ids}")
    public ResponseDTO deleteBatch(@PathVariable List<Integer> ids) {
        return this.expenseService.deleteBatch(ids);
    }

    @ApiOperation("编辑更新报销单")
    @PutMapping
    public ResponseDTO edit(@RequestBody Expense expense) {
        return this.expenseService.edit(expense);
    }

    @ApiOperation("查询报销单")
    @GetMapping("/{id}")
    public ResponseDTO findById(@PathVariable Integer id) {
        return this.expenseService.findById(id);
    }

    @ApiOperation("分页条件查询报销单")
    @GetMapping
    public ResponseDTO list(@RequestParam(defaultValue = "1") Integer current, @RequestParam(defaultValue = "10") Integer size, String name, Integer deptId, String month) {
        return this.expenseService.list(current, size, name, deptId, month);
    }

    @ApiOperation("报销单导出")
    @GetMapping("/export")
    public ResponseDTO export(HttpServletResponse response) throws IOException {
        return this.expenseService.export(response);
    }

    @ApiOperation("报销单导入")
    @PostMapping("/import")
    public ResponseDTO imp(MultipartFile file) throws IOException {
        return this.expenseService.imp(file);
    }

    @ApiOperation("根据员工ID查询报销单")
    @GetMapping("/employee/{id}")
    public ResponseDTO findByEmployeeId(@PathVariable Integer id) {
        return this.expenseService.findByEmployeeId(id);
    }

    @ApiOperation("根据员工ID和日期查询报销单")
    @GetMapping("/employee/{id}/{date}")
    public ResponseDTO findByEmployeeIdAndDate(@PathVariable Integer id, @PathVariable String date) {
        return this.expenseService.findByEmployeeIdAndDate(id, date);
    }

    @ApiOperation("更新报销单")
    @PutMapping("/update")
    public ResponseDTO updateExpense(@RequestBody Expense expense) {
        return this.expenseService.updateExpense(expense);
    }

    @ApiOperation("获取所有报销单")
    @GetMapping("/all")
    public ResponseDTO findAll() {
        return this.expenseService.findAll();
    }

}

