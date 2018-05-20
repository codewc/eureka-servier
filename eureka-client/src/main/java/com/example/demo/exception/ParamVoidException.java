package com.example.demo.exception;

/**
 * 接口参数错误异常
 */
public class ParamVoidException extends  RuntimeException{

    public ParamVoidException() {
        super();
    }

    public ParamVoidException(String message) {
        super(message);
    }

    public ParamVoidException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParamVoidException(Throwable cause) {
        super(cause);
    }

    protected ParamVoidException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
