package com.rabbiter.hrm.exception;

/**
 * 业务异常类
 * 用于处理业务逻辑中的预期异常
 *
 * @author: 李鑫
 * @date: 2026/2/24 14:06
 */
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * 错误码
     */
    private Integer code;

    /**
     * 错误信息
     */
    private String message;

    /**
     * 默认构造函数
     */
    public BusinessException() {
        super();
        this.code = 500;
        this.message = "业务异常";
    }

    /**
     * 只传错误信息的构造函数
     * @param message 错误信息
     */
    public BusinessException(String message) {
        super(message);
        this.code = 500;
        this.message = message;
    }

    /**
     * 传错误码和错误信息的构造函数
     * @param code 错误码
     * @param message 错误信息
     */
    public BusinessException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    /**
     * 传错误信息和异常的构造函数
     * @param message 错误信息
     * @param cause 异常原因
     */
    public BusinessException(String message, Throwable cause) {
        super(message, cause);
        this.code = 500;
        this.message = message;
    }

    /**
     * 传错误码、错误信息和异常的构造函数
     * @param code 错误码
     * @param message 错误信息
     * @param cause 异常原因
     */
    public BusinessException(Integer code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.message = message;
    }

    /**
     * 获取错误码
     */
    public Integer getCode() {
        return code;
    }

    /**
     * 获取错误信息
     */
    @Override
    public String getMessage() {
        return message;
    }

    /**
     * 链式调用设置错误码
     */
    public BusinessException setCode(Integer code) {
        this.code = code;
        return this;
    }

    /**
     * 链式调用设置错误信息
     */
    public BusinessException setMessage(String message) {
        this.message = message;
        return this;
    }

    @Override
    public String toString() {
        return "BusinessException{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}