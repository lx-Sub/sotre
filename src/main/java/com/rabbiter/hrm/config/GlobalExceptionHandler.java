package com.rabbiter.hrm.config;

import com.rabbiter.hrm.dto.ResponseDTO;
import com.rabbiter.hrm.enums.BusinessStatusEnum;
import com.rabbiter.hrm.exception.BusinessException;
import com.rabbiter.hrm.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理业务异常 - 返回规范的 ResponseDTO 格式
     */
    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)  // 业务异常返回200，前端通过code判断业务状态
    public ResponseDTO handleBusinessException(BusinessException e, HttpServletRequest request) {
        log.error("业务异常: code={}, message={}, path={}",
                e.getCode(), e.getMessage(), request.getRequestURI());

        // 使用 BusinessException 的 code 和 message 构建 ResponseDTO
        return new ResponseDTO(e.getCode(), e.getMessage());
    }

    /**
     * 处理参数验证异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseDTO handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        log.error("参数验证失败: {}", errors);
        return new ResponseDTO(
                BusinessStatusEnum.PARAM_ERROR.getCode(),
                "参数验证失败: " + errors.toString()
        );
    }

    /**
     * 处理 ServiceException
     */
    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO handleServiceException(ServiceException e) {
        log.error("服务异常: {}", e.getMessage());
        return new ResponseDTO(BusinessStatusEnum.ERROR.getCode(), e.getMessage());
    }

    /**
     * 处理 MyBatis 异常
     */
    @ExceptionHandler(MyBatisSystemException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseDTO handleMyBatisException(MyBatisSystemException e) {
        String message = e.getMessage();
        log.error("MyBatis异常: ", e);

        // 数据库异常信息脱敏
        message = maskDatabaseErrorMessage(message);

        return new ResponseDTO(
                BusinessStatusEnum.DATABASE_ERROR.getCode(),
                message
        );
    }

    /**
     * 处理所有未捕获的异常
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseDTO handleException(Exception e, HttpServletRequest request) {
        log.error("系统异常: path={}, error={}", request.getRequestURI(), e.getMessage(), e);

        return new ResponseDTO(
                BusinessStatusEnum.ERROR.getCode(),
                "系统繁忙，请稍后重试"
        );
    }

    /**
     * 数据库异常信息脱敏
     */
    private String maskDatabaseErrorMessage(String message) {
        if (message == null) return "数据库异常";

        if (message.contains("(using password: YES)")) {
            if (!message.contains("'root'@'")) {
                return "数据库连接失败";
            } else if (message.contains("'root'@'localhost'")) {
                return "数据库密码错误";
            }
        } else if(message.contains("Table") && message.contains("doesn't exist")) {
            return "数据表不存在";
        } else if (message.contains("Unknown database")) {
            return "数据库不存在";
        } else if (message.contains("edis")) {
            return "Redis连接失败";
        } else if (message.contains("Failed to obtain JDBC Connection")) {
            return "数据库连接失败";
        } else if (message.contains("SQLSyntaxErrorException")) {
            return "SQL语法错误";
        }

        return "数据库操作异常";
    }
}