package com.rabbiter.hrm.enums;

import org.apache.poi.ss.formula.functions.T;

public enum BusinessStatusEnum implements BaseEnum<T> {

    FILE_NOT_EXIST(400, "文件不存在"),
    FILE_READ_ERROR(500, "文件读取失败"),
    FILE_WRITE_ERROR(600, "文件写入失败"),
    FILE_UPLOAD_ERROR(700, "文件上传失败"),
    TOKEN_NOT_EXIST(800, "token不存在，请重新登录"),
    TOKEN_INVALID(900, "token无效，请重新登录"),
    DATA_IMPORT_ERROR(1000, "数据导入失败"),
    BATCH_DELETE_ERROR(1100, "批量删除失败"),
    STAFF_NOT_EXIST(1200,"没有此员工，请重新登录"),
    USER_EXISTS(1201, "员工不存在"),
    STAFF_STATUS_ERROR(1300, "状态异常，请联系管理员"),
    STAFF_NOT_TOKEN_ERROR(1400, "取不到token"),
    SUCCESS(200, "success"),
    ERROR(500, "error"),
    DATABASE_ERROR(501, "数据库错误"),

    // 通用错误码 (1000-1999)
    PARAM_ERROR(400, "参数错误"),
    UNAUTHORIZED(401, "未授权"),
    FORBIDDEN(403, "禁止访问"),
    NOT_FOUND(404, "资源不存在"),

    // 业务错误码 (2000-2999)
    USER_NOT_FOUND(2000, "用户不存在"),
    USER_FROZEN(2001, "用户已冻结"),
    MERCHANT_NOT_FOUND(2100, "未找到商家申请"),
    MERCHANT_AUDITED(2101, "商家已审核"),
    CREDIT_OUT_OF_RANGE(2200, "信用分超出有效范围"),
    VERIFICATION_NOT_FOUND(2300, "认证信息不存在"),
    ORDER_NOT_FOUND(2400, "订单不存在");
    private final Integer code;
    private final String message;

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    BusinessStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
