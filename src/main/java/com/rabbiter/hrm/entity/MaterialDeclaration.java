package com.rabbiter.hrm.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@TableName("material_declaration")
public class MaterialDeclaration implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id; // 主键ID
    @TableField("materialName")
    private String materialName; // 材料名称
    @TableField("quantity")
    private Integer quantity; // 数量
    @TableField("approver")
    private String approver; // 审批人
    @TableField("applyDate")
    private LocalDate applyDate; // 申请日期
    @TableField("description")
    private String description; // 备注
    private Integer status; // 审批状态: 0 - 未审批, 1 - 审批通过, -1 - 审批失败
    private String fileUrl; // 文件上传路径
    private LocalDateTime createTime; // 创建时间
    private LocalDateTime updateTime; // 更新时间

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getApprover() {
        return approver;
    }

    public void setApprover(String approver) {
        this.approver = approver;
    }

    public LocalDate getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(LocalDate applyDate) {
        this.applyDate = applyDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "MaterialDeclaration{" +
                "id=" + id +
                ", materialName='" + materialName + '\'' +
                ", quantity=" + quantity +
                ", approver='" + approver + '\'' +
                ", applyDate=" + applyDate +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", fileUrl='" + fileUrl + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}

