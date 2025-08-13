package com.aliyun.sdk.service.oss2.retry;

import com.aliyun.sdk.service.oss2.exceptions.ServiceException;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Determines whether a service error is retryable based on its error code.
 */
public class ServiceErrorCodeRetryable implements ErrorRetryable {
    /**
     * A set of error that are considered retryable.
     */
    private final static Set<String> errorCodes = new HashSet<>(Arrays.asList("RequestTimeTooSkewed", "BadRequest"));

    /**
     * Checks whether the given error has a retryable error code.
     *
     * @param error The error to be checked
     * @return true if the error code is retryable, false otherwise
     */
    @Override
    public boolean isErrorRetryable(Throwable error) {
        if (error instanceof ServiceException) {
            return errorCodes.contains(((ServiceException) error).errorCode());
        }
        return false;
    }
}
