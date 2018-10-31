package com.common.utils.http;

import lombok.Getter;

/**
 * 响应消息枚举
 */
@Getter
public enum ResultCodeEnum {

    SUCCESS(0, "成功"),
    ERROR(1, "失败"),;

    private int code;
    private String desc;


    ResultCodeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
