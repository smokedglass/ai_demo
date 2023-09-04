package com.ten.batch.common;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 名称：Result
 * 描述：数据响应对象
 * {
 * success ：是否成功
 * code    ：返回码
 * message ：返回信息
 * //返回数据
 * data：  ：{
 * <p>
 * }
 * }
 *
 * @version 1.0
 * @author: Zhang BoRU
 * @datetime: 2022-04-25 14:21
 */
@Data
@NoArgsConstructor
public class Result implements Serializable {
    private static final long serialVersionUID = -1213213123L;
    /**
     * 是否成功
     */
    private boolean success;
    /**
     * 返回码
     */
    private Integer code;
    /**
     * 返回信息
     */
    private String message;
    /**
     * 返回数据
     */
    private Object data;

    public Result(ResultCode code) {
        this.success = code.success;
        this.code = code.code;
        this.message = code.message;
    }

    public Result(ResultCode code, Object data) {
        this.success = code.success;
        this.code = code.code;
        this.message = code.message;
        this.data = data;
    }

    public Result(Integer code, String message, boolean success) {
        this.code = code;
        this.message = message;
        this.success = success;
    }

    public static Result SUCCESS() {
        return new Result(ResultCode.SUCCESS);
    }

    public static Result ERROR() {
        return new Result(ResultCode.SERVER_ERROR);
    }

    public static Result FAIL() {
        return new Result(ResultCode.FAIL);
    }
}
