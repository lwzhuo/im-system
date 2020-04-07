package com.zhuo.imsystem.http.util;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Object logicExceptionHandler(HttpServletRequest request, Exception e, HttpServletResponse response) {
        //系统级异常，错误码固定为-1，提示语固定为系统繁忙，请稍后再试
        //如果是业务逻辑异常，返回具体的错误码与提示信息
        if (e instanceof CommonException) {
            CommonException commonException = (CommonException) e;
            return new ResponseJson().error(commonException.getErrorMsg(),commonException.getErrorCode());
        } else {
            //对系统级异常进行日志记录
//            logger.error("系统异常:" + e.getMessage(), e);
            e.printStackTrace();
            return new ResponseJson().error();
        }
    }
}
