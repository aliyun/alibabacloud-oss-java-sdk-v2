package com.aliyun.sdk.service.oss2.transport;

/**
 * Response Error Class that encapsulates exceptions occurring during response processing
 */
public class ResponseException extends RuntimeException {

    public ResponseException(String msg) {
        super(msg);
    }

    public ResponseException(String msg, Throwable cause) {
        super(msg, cause);
    }
}