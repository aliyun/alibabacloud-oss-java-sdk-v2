package com.aliyun.sdk.service.oss2.exceptions;

public class DeserializationException extends RuntimeException {

    static final String fmt = "Deserialization raised an exception: %s";

    public DeserializationException(String s) {
        super(String.format(fmt, s));
    }

    public DeserializationException(String s, Throwable cause) {
        super(String.format(fmt, s), cause);
    }
}
