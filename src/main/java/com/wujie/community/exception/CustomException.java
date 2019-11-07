package com.wujie.community.exception;

//自定义运行时异常
public class CustomException extends RuntimeException {
    //文本内容
    private String message;
    //错误标识
    private Integer code;

    public CustomException(ICustomizeErrorCode errorCode) {
        this.code=errorCode.getCode();
        this.message=errorCode.getMessage();
    }

    @Override
    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }
}
