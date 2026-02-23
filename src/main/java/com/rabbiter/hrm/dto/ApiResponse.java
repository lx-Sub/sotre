package com.rabbiter.hrm.dto;

/**
 * @Type ApiResponse.java
 * @Desc
 * @Author lixin
 * @Date 2025/1/23 11:11
 * @Version
 */
public class ApiResponse {
    private int code;
    private String message;
    private Object data;

    public ApiResponse(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    // getters and setters
}


