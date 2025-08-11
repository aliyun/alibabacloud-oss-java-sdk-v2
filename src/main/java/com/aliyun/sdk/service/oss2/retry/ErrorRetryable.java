package com.aliyun.sdk.service.oss2.retry;

/**
 * Abstract base class used to determine whether a given error is retryable.
 */
public interface ErrorRetryable {
    /**
     * Determines whether the specified error can be retried.
     *
     * @param error The error that occurred
     * @return true if the error should be retried, false otherwise
     */
    boolean isErrorRetryable(Throwable error);
}