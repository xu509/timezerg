package com.timezerg.api.controller;

import com.timezerg.api.util.Result;
import com.timezerg.api.util.ResultMessage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by xnx on 2018/5/16.
 */
@ControllerAdvice
public class GExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public Object defaultErrorHandler(HttpServletRequest req,Exception e) throws Exception{
        e.printStackTrace();
        return new Result(ResultMessage.INNER_ERROR);
    }

}
