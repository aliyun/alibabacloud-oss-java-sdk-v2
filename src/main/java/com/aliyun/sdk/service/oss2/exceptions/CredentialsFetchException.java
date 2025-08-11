package com.aliyun.sdk.service.oss2.exceptions;

/**
 * Credential Fetch Error Class
 */
public class CredentialsFetchException extends RuntimeException {

    static final String fmt = "Fetch Credentials raised an exception: %s";

    public CredentialsFetchException(Throwable cause) {
        super(String.format(fmt, cause), cause);
    }
}