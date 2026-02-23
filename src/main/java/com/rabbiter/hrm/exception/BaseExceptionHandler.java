package com.rabbiter.hrm.exception;

import com.rabbiter.hrm.dto.Response;
import com.rabbiter.hrm.dto.ResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
@ControllerAdvice
public class BaseExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(BaseExceptionHandler.class);

    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    public ResponseDTO handle(ServiceException exception) {
        logger.info(exception.getMessage());
        // Response.error 应该返回 ResponseDTO 类型，确保该方法返回的类型是 JSON 格式
        return Response.error(exception.getCode(), exception.getMessage());
    }
}

