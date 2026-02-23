package com.rabbiter.hrm.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rabbiter.hrm.dto.ResponseDTO;
import com.rabbiter.hrm.entity.MaterialDeclaration;
import com.rabbiter.hrm.mapper.MaterialDeclarationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
@Service
public class MaterialDeclarationService extends ServiceImpl<MaterialDeclarationMapper, MaterialDeclaration> {

    @Autowired
    private MaterialDeclarationMapper materialDeclarationMapper;


    public ResponseDTO addMaterialDeclaration(MaterialDeclaration materialDeclaration) {
        // 保存申报信息到数据库
        // 此处调用 MyBatis 插入操作
        materialDeclarationMapper.insert(materialDeclaration);  // 需要自定义insert方法
        return new ResponseDTO(200, "新增成功");
    }


    public ResponseDTO getMaterialDeclarations(Integer current, Integer size, String approver, String materialName, String applyDate) {
        // 计算分页偏移量
        Integer offset = (current - 1) * size;

        // 查询材料申报记录
        List<MaterialDeclaration> records = materialDeclarationMapper.findAllByConditions(approver, materialName, applyDate, offset, size);

        return new ResponseDTO(200, "查询成功", records);
    }


    public ResponseDTO uploadFile(MultipartFile file) throws IOException {
        // 动态获取上传路径
        String uploadDir = "/path/to/uploaded/files/";
        String filePath = uploadDir + file.getOriginalFilename();

        // 确保上传目录存在
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs(); // 创建目录
        }

        // 保存文件
        file.transferTo(new File(filePath));

        return new ResponseDTO(200, "文件上传成功", filePath);
    }

    public ResponseDTO edit(MaterialDeclaration MaterialDeclaration) {
        // 更新报销单
        int result = materialDeclarationMapper.updateById(MaterialDeclaration);
        if (result > 0) {
            return new ResponseDTO(200, "更新报销单成功", null);
        } else {
            return new ResponseDTO(500, "更新报销单失败", null);
        }
    }
    /**
     * 批量删除报销单
     *
     * @param ids 报销单ID列表
     * @return ResponseDTO
     */
    public ResponseDTO deleteBatch(List<Long> ids) {
        // 批量删除报销单
        int result = materialDeclarationMapper.deleteBatchIds(ids);
        if (result > 0) {
            return new ResponseDTO(200, "批量删除报销单成功", null);
        } else {
            return new ResponseDTO(500, "批量删除报销单失败", null);
        }
    }
}

