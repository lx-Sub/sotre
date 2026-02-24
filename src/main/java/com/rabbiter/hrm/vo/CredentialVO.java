package com.rabbiter.hrm.vo;

import lombok.Data;
import java.time.LocalDateTime;


@Data
public class CredentialVO {
    private Long id;
    private String type;           // 资质类型：business_license-营业执照，brand_authorization-品牌授权
    private String fileUrl;         // 文件URL
    private String fileName;        // 文件名
    private LocalDateTime uploadTime; // 上传时间（对应数据库的 apply_time）
    private Integer status;         // 状态
}