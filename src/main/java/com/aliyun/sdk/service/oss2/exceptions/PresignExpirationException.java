package com.aliyun.sdk.service.oss2.exceptions;

public class PresignExpirationException extends RuntimeException {

    static final String msg = "Expires should be not greater than 604800 s (seven days)";

    public PresignExpirationException() {
        super(msg);
    }
}
