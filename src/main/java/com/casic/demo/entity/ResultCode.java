package com.casic.demo.entity;

/**
 * 响应码枚举，参考HTTP状态码的语义
 * Created by bekey on 2017/12/10.
 */
public enum ResultCode {
    SUCCESS(200),//成功
    FAIL(400),//失败
    UNAUTHORIZED(401),//未认证（签名错误）
    NOT_FOUND(404),//接口不存在
    INTERNAL_SERVER_ERROR(500);//服务器内部错误

    private int code;

    private ResultCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }
}
