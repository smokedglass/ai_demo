package com.ten.batch.handle;

import com.ten.batch.common.CommonException;
import com.ten.batch.common.Result;
import com.ten.batch.common.ResultCode;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 名称：BaseExceptionHandler
 * 功能描述：自定义的公共异常处理器
 *
 * @version 1.0
 * @author: zhangboru
 * @datetime: 2023/08/28 14:23
 */
@ControllerAdvice
public class BaseExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(HttpServletRequest request, HttpServletResponse response, Exception e) {
        e.printStackTrace();
        if (e instanceof CommonException) {
            // 类型转换
            CommonException ce = (CommonException) e;
            Result result = new Result(ce.getResultCode());
            return result;
        } else {
            Result result = new Result(ResultCode.SERVER_ERROR);
            return result;
        }
    }
}
