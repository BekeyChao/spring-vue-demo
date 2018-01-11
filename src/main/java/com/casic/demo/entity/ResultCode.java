package com.casic.demo.entity;

/**
 * 响应码枚举，参考HTTP状态码的语义
 * Created by bekey on 2017/12/10.
 */
public enum ResultCode {
    /**
     * 成功
     */
    SUCCESS(200),
    /**
     * 失败
     */
    FAIL(400),
    /**
     * 未认证（签名错误,未登陆）
     */
    UNAUTHORIZED(401),
    /**
     * 拒绝访问
     */
    FORBIDDEN(403),
    /**
     * 接口不存在
     */
    NOT_FOUND(404),
    /**
     *  服务器内部错误
     */
    INTERNAL_SERVER_ERROR(500);

    private int code;

    ResultCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }
}
