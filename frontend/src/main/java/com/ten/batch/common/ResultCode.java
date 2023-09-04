package com.ten.batch.common;

import java.io.Serializable;

/**
 * 名称：ResultCode
 * 描述：公共返回码
 * <p>
 * 成功：10000
 * 失败：10001
 * 未登录：10002
 * 未授权：10003
 * 抛出异常：99999
 *
 * @version 1.0
 * @author: Zhang BoRU
 * @datetime: 2022-04-25 14:21
 */
public enum ResultCode implements Serializable {
    //---系统成功返回码-----
    SUCCESS(true, 10000, "操作成功！"),

    //---系统错误返回码-----
    FAIL(false, 10001, "操作失败"),
    UNAUTHENTICATED(false, 10002, "您还未登录"),
    UNAUTHORISE(false, 10003, "权限不足"),
    PRAMSISEMPTYOERROR(false, 10004, "核心参数为空或错误"),
    QRCODECREATEERROR(false, 10005, "二维码生成失败"),
    DATABASEERROR(false, 10006, "数据库错误"),
    SERVER_ERROR(false, 99999, "抱歉，系统繁忙，请稍后重试！"),

    //---用户操作返回码  2xxxx----
    MOBILEORPASSWORDERROR(false, 20001, "用户名或密码错误"),
    USERISNOTEXIST(false, 20002, "用户不存在"),
    PERMISSIONISNOTEXIST(false, 20003, "权限不存在"),
    ROLEISNOTEXIST(false, 20004, "角色不存在"),
    MOBILEISERROR(false, 20005, "手机号格式错误"),
    USERNAMEORPASSWORDISNULL(false, 20006, "用户名或密码为空"),
    USERUPLOADFILEISNOTEXIST(false, 20007, "上传文件的用户不存在"),
    DOMLOADFILENOTEXIST(false, 20008, "下载的文件不存在");

    //---其他操作返回码----

    //操作是否成功
    boolean success;
    //操作代码
    int code;
    //提示信息
    String message;
    private static final long serialVersionUID = 2132112312L;

    ResultCode(boolean success, int code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }

    public boolean success() {
        return success;
    }

    public int code() {
        return code;
    }

    public String message() {
        return message;
    }
}
