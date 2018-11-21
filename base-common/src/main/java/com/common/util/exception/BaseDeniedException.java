package com.common.util.exception;

/**
 * 403 授权拒绝
 */
public class BaseDeniedException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public BaseDeniedException() {
    }

    public BaseDeniedException(String message) {
        super(message);
    }

    public BaseDeniedException(Throwable cause) {
        super(cause);
    }

    public BaseDeniedException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseDeniedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
