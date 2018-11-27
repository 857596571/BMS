package com.common.util;

import lombok.Data;

import java.io.Serializable;

/**
 * 响应消息模版类
 * @param <T>
 */
@Data
public class R<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final int NO_LOGIN = -1;

    public static final int SUCCESS = 0;

    public static final int FAIL = 1;

    public static final int NO_PERMISSION = 2;

    private String msg = "success";

    private int code = SUCCESS;

    private T data;

    public boolean isOk() {
        return this.code == SUCCESS;
    }

    public R() {
        super();
    }

    public R(int code) {
        super();
        this.code = code;
    }

    public R(int code, String msg) {
        super();
        this.code = code;
        this.msg = msg;
    }

    public R(T data) {
        super();
        this.data = data;
    }

    public R(T data, String msg) {
        super();
        this.data = data;
        this.msg = msg;
    }
    public R(int code, String msg, T data) {
        super();
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    public R(Throwable e) {
        super();
        this.msg = e.getMessage();
        this.code = FAIL;
    }

    /**
     * 成功方法
     * @return 消息模版
     */
    public static R success() {
        return new R();
    }

    /**
     * 成功方法，带参数
     * @param data 参数
     * @param <T> 类型
     * @return 消息模版
     */
    public static <T> R<T> success(T data) {
        return new R(data);
    }

    /**
     * 成功方法，带消息，参数
     * @param data 参数
     * @param <T> 类型
     * @return 消息模版
     */
    public static <T> R<T> success(T data, String msg) {
        return new R(data, msg);
    }

    /**
     * 错误方法
     * @return 消息模版
     */
    public static R error() {
        return new R(FAIL);
    }

    /**
     * 错误方法
     * @param msg 消息内容
     * @return 消息模版
     */
    public static R error(String msg) {
        return new R(FAIL, msg);
    }

    /**
     * 错误方法
     * @param code 编码
     * @param msg 消息内容
     * @return 消息模版
     */
    public static R error(int code, String msg) {
        return new R(code, msg);
    }

    /**
     * 错误方法
     * @param code 编码
     * @param message 消息内容
     * @param t
     * @param <T> 类型
     * @return 消息模版
     */
    public static <T> R<T> error(int code, String message, T t) {
        return new R(code, message, t);
    }

}
