package com.ten.batch.common;


import lombok.Getter;

/**
 * 名称：CommonException
 * 描述：自定义异常类
 *
 * @version 1.0
 * @author: Zhang BoRU
 * @datetime: 2022-04-25 16:01
 */
@Getter
public class CommonException extends RuntimeException {
    private ResultCode resultCode;

    public CommonException(ResultCode resultCode) {
        this.resultCode = resultCode;
    }
}
