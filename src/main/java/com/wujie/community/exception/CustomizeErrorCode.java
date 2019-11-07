package com.wujie.community.exception;

//自定枚举异常实现类
public enum CustomizeErrorCode implements ICustomizeErrorCode {

    //枚举值
    QUESTION_NOT_FOUND(2001,"您找的问题不存在，换个试试？"),
    TARGET_PARAM_NOT_FOUND(2002,"未选中任何问题或评论进行回复"),
    NO_LOGIN(2003,"未登录不能进行评论，请先登录！"),
    SYS_ERROR(2004,"服务器太热了 大人,让小官稍作休息会儿~"),
    TYPE_PARAM_WRONG(2005,"评论类型错误或不存在"),
    COMMENT_NOT_FOUND(2006,"回复的评论已经不存在，换个试试？"),
    CONTENT_IS_EMPTY(2007,"输入的回复内容不能为空"),
    READ_NOTIFICATION_FAIL(2008,"兄弟你这是读别人的信息呢？"),
    NOTIFICATION_NOT_FOUND(2009,"消息莫非是不翼而飞了？");

    private String message;
    private Integer code;

    CustomizeErrorCode(Integer code, String message) {
        this.message = message;
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }
}
