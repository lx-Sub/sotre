package com.rabbiter.hrm.controller;


import com.rabbiter.hrm.dto.ResponseDTO;
import com.rabbiter.hrm.entity.Expense;
import com.rabbiter.hrm.entity.MaterialDeclaration;
import com.rabbiter.hrm.service.MaterialDeclarationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/material-declaration")
@Api(tags = "材料申报管理")
public class MaterialDeclarationController {

    @Resource
    private MaterialDeclarationService materialDeclarationService;

    @ApiOperation("新增材料申报")
    @PostMapping
    public ResponseDTO addMaterialDeclaration(@RequestBody MaterialDeclaration materialDeclaration) {
        return materialDeclarationService.addMaterialDeclaration(materialDeclaration);
    }

    @ApiOperation("获取材料申报列表")
    @GetMapping()
    public ResponseDTO getMaterialDeclarations(@RequestParam Integer current,
                                               @RequestParam Integer size,
                                               @RequestParam(required = false) String applicant,
                                               @RequestParam(required = false) String materialName,
                                               @RequestParam(required = false) String applyDate) {
        return materialDeclarationService.getMaterialDeclarations(current, size, applicant, materialName, applyDate);
    }

    @ApiOperation("文件上传")
    @PostMapping("/upload")
    public ResponseDTO uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        return materialDeclarationService.uploadFile(file);
    }
    @ApiOperation("批量删除报销单")
    @DeleteMapping("/batch/{ids}")
    public ResponseDTO deleteBatch(@PathVariable List<Long> ids) {
        return this.materialDeclarationService.deleteBatch(ids);
    }
    //@ApiOperation("更新报销单")
    //@PutMapping("/update")
    //public ResponseDTO updateExpense(@RequestBody MaterialDeclaration materialDeclaration) {
    //    return this.materialDeclarationService.updateExpense(materialDeclaration);
    //}
    @ApiOperation("编辑更新报销单")
    @PutMapping
    public ResponseDTO edit(@RequestBody MaterialDeclaration materialDeclaration) {
        return this.materialDeclarationService.edit(materialDeclaration);
    }
}


