package com.common.utils.http;

/**
 * 返回结果类
 */
public class Result {

    private static final ResponseMessage RESPONSE_MESSAGE_SUCCESS = new ResponseMessage(ResponseMessageCodeEnum.SUCCESS.getCode(), "");

    /**
     * 成功方法
     * @return 消息模版
     */
    public static ResponseMessage success() {
        return RESPONSE_MESSAGE_SUCCESS;
    }

    /**
     * 成功方法，带
     * @param t 参数
     * @param <T> 类型
     * @return 消息模版
     */
    public static <T> ResponseMessage<T> success(T t) {
        return new ResponseMessage(ResponseMessageCodeEnum.SUCCESS.getCode(), "", t);
    }

    /**
     * 错误方法
     * @return 消息模版
     */
    public static ResponseMessage error() {
        return error(ResponseMessageCodeEnum.ERROR.getCode(), null);
    }

    /**
     * 错误方法
     * @param message 消息内容
     * @return 消息模版
     */
    public static ResponseMessage error(String message) {
        return error(ResponseMessageCodeEnum.ERROR.getCode(), message);
    }

    /**
     * 错误方法
     * @param code 编码
     * @param message 消息内容
     * @return 消息模版
     */
    public static ResponseMessage error(Object code, String message) {
        return error(code, message, null);
    }

    /**
     * 错误方法
     * @param code 编码
     * @param message 消息内容
     * @param t
     * @param <T> 类型
     * @return 消息模版
     */
    public static <T> ResponseMessage<T> error(Object code, String message, T t) {
        return new ResponseMessage(code, message, t);
    }
}
