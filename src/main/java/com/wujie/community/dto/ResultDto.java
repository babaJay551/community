package com.wujie.community.dto;

import com.wujie.community.exception.CustomException;
import com.wujie.community.exception.CustomizeErrorCode;
import lombok.Data;

@Data
public class ResultDto<T> {

    private Integer code;
    private String message;
    private T data;

    //封装一个获取错误信息标识跟错误信息的方法
    public static ResultDto errorOf(Integer code, String message){
        ResultDto resultDTO = new ResultDto();
        resultDTO.setCode(code);
        resultDTO.setMessage(message);
        return resultDTO;
    }

    //指定枚举的错误信息标识跟错误信息
    public static ResultDto errorOf(CustomizeErrorCode errorCode) {
        return errorOf(errorCode.getCode(),errorCode.getMessage());
    }
    //指定枚举的错误信息标识跟错误信息
    public static ResultDto errorOf(CustomException e) {
        return errorOf(e.getCode(),e.getMessage());
    }

    //请求成功
    public static ResultDto okOf(){
        ResultDto resultDTO = new ResultDto();
        resultDTO.setCode(200);
        resultDTO.setMessage("请求成功！");
        return resultDTO;
    }

    //请求成功
    public static <T> ResultDto okOf(T t){
        ResultDto resultDTO = new ResultDto();
        resultDTO.setCode(200);
        resultDTO.setMessage("请求成功！");
        resultDTO.setData(t);
        return resultDTO;
    }
}
