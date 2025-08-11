package com.aliyun.sdk.service.oss2.exceptions;

public class CredentialsException extends RuntimeException {

    public CredentialsException(String s) {
        super(s);
    }

    public CredentialsException(String s, Throwable cause) {
        super(s, cause);
    }
}
