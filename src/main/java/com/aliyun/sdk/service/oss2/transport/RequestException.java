package com.aliyun.sdk.service.oss2.transport;

/**
 * Request Error Class that encapsulates exceptions occurring during request execution
 */
public class RequestException extends RuntimeException {

    public RequestException(String msg) {
        super(msg);
    }

    public RequestException(String msg, Throwable cause) {
        super(msg, cause);
    }
}