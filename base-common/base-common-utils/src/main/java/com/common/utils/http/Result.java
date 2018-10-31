package com.common.utils.http;

import lombok.Data;

/**
 * 响应消息模版类
 * @param <T>
 */
@Data
public class Result<T> {

    private long code;
    private String msg;
    private T data;

    public Result() {
    }

    public Result(int code) {
        this.code = code;
    }

    public Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result(int code, T data) {
        this.code = code;
        this.data = data;
    }

    public Result(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public boolean isOk() {
        return this.code == ResultCodeEnum.SUCCESS.getCode();
    }

    /**
     * 成功方法
     * @return 消息模版
     */
    public static Result success() {
        return new Result(ResultCodeEnum.SUCCESS.getCode());
    }

    /**
     * 成功方法，带参数
     * @param t 参数
     * @param <T> 类型
     * @return 消息模版
     */
    public static <T> Result<T> success(T t) {
        return new Result(ResultCodeEnum.SUCCESS.getCode(), t);
    }

    /**
     * 成功方法，带消息，参数
     * @param t 参数
     * @param <T> 类型
     * @return 消息模版
     */
    public static <T> Result<T> success(String msg, T t) {
        return new Result(ResultCodeEnum.SUCCESS.getCode(), msg, t);
    }

    /**
     * 错误方法
     * @return 消息模版
     */
    public static Result error() {
        return new Result(ResultCodeEnum.ERROR.getCode());
    }

    /**
     * 错误方法
     * @param message 消息内容
     * @return 消息模版
     */
    public static Result error(String message) {
        return new Result(ResultCodeEnum.ERROR.getCode(), message);
    }

    /**
     * 错误方法
     * @param code 编码
     * @param message 消息内容
     * @return 消息模版
     */
    public static Result error(int code, String message) {
        return new Result(code, message);
    }

    /**
     * 错误方法
     * @param code 编码
     * @param message 消息内容
     * @param t
     * @param <T> 类型
     * @return 消息模版
     */
    public static <T> Result<T> error(int code, String message, T t) {
        return new Result(code, message, t);
    }
}
