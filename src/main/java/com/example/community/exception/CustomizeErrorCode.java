package com.example.community.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode {

    QUESTION_NOT_FOUND(2001,"你找的问题不存在，请换一个问题"),
    TARGET_PARAM_NOT_FOUND(2002,"未选中任何问题和评论"),
    NO_LOGIN(2003,"当前操作需要登录，请登录后重试"),
    SYS_ERROR(2004,"服务出错了"),
    TYPE_PARAM_WRONG(2005,"评论类型错误或不存在"),
    COMMENT_NOT_FOUND(2006,"你回复的评论不存在"),
    CONTENT_IS_EMPTY(2007,"输入内容不能为空");
    private String message;
    private Integer code;

    CustomizeErrorCode(Integer code,String message) {
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
