package com.wujie.community.advice;

import com.alibaba.fastjson.JSON;
import com.wujie.community.dto.ResultDto;
import com.wujie.community.exception.CustomException;
import com.wujie.community.exception.CustomizeErrorCode;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@ControllerAdvice
public class CustomizeExceptionHandler {


    @ExceptionHandler(Exception.class)
    ModelAndView handleControllerException(Throwable ex, Model model,
                                     HttpServletRequest request,
                                     HttpServletResponse response) {
        //获取内容显示类型
        String contentType = request.getContentType();
        if ("application/json".equals(contentType)){
            //创建ResultDTO对象
            ResultDto resultDTO;
            //返回json
            if (ex instanceof CustomException){
                resultDTO= ResultDto.errorOf((CustomException) ex);
            }else{
                resultDTO= ResultDto.errorOf(CustomizeErrorCode.SYS_ERROR);
            }

            //暴力书写json到前端页面
            try {
                //设置显示类型
                response.setContentType("application/json");
                response.setStatus(200);
                response.setCharacterEncoding("utf-8");
                PrintWriter writer = response.getWriter();
                writer.write(JSON.toJSONString(resultDTO));
                //关流
                writer.close();
            } catch (IOException io) {
            }
            return null;

        }else {
//            //错误页面跳转
            if (ex instanceof CustomException){
                model.addAttribute("messages",ex.getMessage());
            }else{
                model.addAttribute("messages",CustomizeErrorCode.SYS_ERROR.getMessage());
            }
            return new ModelAndView("error");
        }

    }


}
