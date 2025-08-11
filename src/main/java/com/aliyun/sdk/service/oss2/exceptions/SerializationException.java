package com.aliyun.sdk.service.oss2.exceptions;

public class SerializationException extends RuntimeException {

    static final String fmt = "Serialization raised an exception: %s";

    public SerializationException(String s) {
        super(String.format(fmt, s));
    }

    public SerializationException(String s, Throwable cause) {
        super(String.format(fmt, s), cause);
    }
}
