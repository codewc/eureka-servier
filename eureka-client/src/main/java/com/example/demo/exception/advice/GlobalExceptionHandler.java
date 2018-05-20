package com.example.demo.exception.advice;

import com.example.demo.common.JsonData;
import com.example.demo.exception.ParamVoidException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 统一自定义异常处理
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    public static final String DEFAULT_ERROR_MSG = "服务器执行错误,请稍候再试";

    @ResponseBody
    @ExceptionHandler(ParamVoidException.class)
    public JsonData showException(ParamVoidException e) {
        log.error("参数错误：",e);
        return JsonData.fail(e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public JsonData showException(Exception e) {
        log.error("系统错误：",e);
        return JsonData.fail(DEFAULT_ERROR_MSG);
    }
}
