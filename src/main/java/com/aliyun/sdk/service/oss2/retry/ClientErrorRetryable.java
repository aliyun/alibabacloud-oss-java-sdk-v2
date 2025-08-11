package com.aliyun.sdk.service.oss2.retry;

import com.aliyun.sdk.service.oss2.exceptions.CredentialsFetchException;
import com.aliyun.sdk.service.oss2.exceptions.InconsistentException;
import com.aliyun.sdk.service.oss2.transport.RequestException;
import com.aliyun.sdk.service.oss2.transport.ResponseException;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Helper class to determine if a client-side error is retryable.
 */
public class ClientErrorRetryable implements ErrorRetryable {
    /**
     * A set containing all exception types that are considered retryable.
     */
    private final Set<Class<? extends Exception>> exceptions = new HashSet<>(Arrays.asList(
            RequestException.class,
            ResponseException.class,
            InconsistentException.class,
            CredentialsFetchException.class
    ));

    /**
     * Checks whether the given error is of a retryable type.
     *
     * @param error The error to be checked
     * @return true if the error is retryable, false otherwise
     */
    @Override
    public boolean isErrorRetryable(Throwable error) {
        for (Class<? extends Exception> e : exceptions) {
            if (e.isInstance(error)) {
                return true;
            }
        }
        return false;
    }
}