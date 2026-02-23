package com.rabbiter.hrm.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.rabbiter.hrm.annotation.ExcelColumn;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * <p>
 * 报销单实体类
 * </p>
 *
 * @Author
 * @since 2025-01-23
 */
@TableName("hrm_expense")
@ApiModel(value = "Expense对象", description = "报销单信息")
public class Expense implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("报销单ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("员工姓名")
    @TableField("name")
    private String name;


    @ApiModelProperty("报销日期")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @TableField("date")
    private Date date;

    @ApiModelProperty("报销类型")
    @TableField("type")
    private String type;

    @ApiModelProperty("报销金额")
    @TableField("amount")
    private BigDecimal amount;

    @ApiModelProperty("报销说明")
    @TableField("description")
    private String description;

    @ApiModelProperty("报销状态（0:待审核，1:已审核，2:已支付，3:已拒绝）")
    @TableField("status")
    private Integer status;

    @ApiModelProperty("是否删除（0:未删除，1:已删除）")
    @TableField("is_deleted")
    private Integer isDeleted;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty("创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Timestamp createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty("更新时间")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Timestamp updateTime;

    public Expense(Integer id, String name,  Date date,
                   String type, BigDecimal amount, String description, Integer status,
                   Integer isDeleted, Timestamp createTime, Timestamp updateTime) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.status = status;
        this.isDeleted = isDeleted;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Expense() {
    }

    @Override
    public String toString() {
        return "Expense{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", date=" + date +
                ", type='" + type + '\'' +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", isDeleted=" + isDeleted +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }

    // Getters and Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer expenseId) {
        this.id = expenseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Date getDate() {
        return date;
    }

    public void setExpenseDate(Date expenseDate) {
        this.date = expenseDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String expenseType) {
        this.type = expenseType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
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

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }
}