package com.aliyun.sdk.service.oss2.retry;

import com.aliyun.sdk.service.oss2.exceptions.ServiceException;

/**
 * Determines whether a service error is retryable based on its HTTP status code.
 */
public class HTTPStatusCodeRetryable implements ErrorRetryable {
    /**
     * A set of HTTP status codes that are considered retryable (non-5xx).
     */
    private static final int[] statusCodes = {401, 408, 429};

    /**
     * Checks whether the given error is retryable based on its HTTP status code.
     *
     * @param error The error to be checked
     * @return true if the error should be retried, false otherwise
     */
    @Override
    public boolean isErrorRetryable(Throwable error) {
        if (error instanceof ServiceException) {
            int statusCode = ((ServiceException) error).statusCode();
            if (statusCode >= 500) {
                return true;
            }
            for (int code : statusCodes) {
                if (code == statusCode) {
                    return true;
                }
            }
        }
        return false;
    }
}
