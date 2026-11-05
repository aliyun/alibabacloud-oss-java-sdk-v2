package com.aliyun.sdk.service.oss2.internal.serde;

/**
 * Thrown when serialization or deserialization fails.
 */
public class SerdeException extends RuntimeException {
    public SerdeException(String message, Throwable cause) {
        super(message, cause);
    }
}
