package com.aliyun.sdk.service.oss2.retry;

import java.time.Duration;

/**
 * Defines the core behaviors of a retry strategy, including whether an error is retryable,
 * the maximum number of attempts, and the delay between retries.
 */
public interface Retryer {
    /**
     * Determines whether the specified error can be retried.
     *
     * @param error The error to be checked
     * @return true if the error should be retried, false otherwise
     */
    boolean isErrorRetryable(Throwable error);

    /**
     * Returns the maximum number of retry attempts allowed.
     *
     * @return The maximum number of retry attempts
     */
    int maxAttempts();

    /**
     * Calculates the delay duration before the next retry attempt.
     *
     * @param attempt The current retry attempt number (starting from 1)
     * @param error   The error that caused the retry
     * @return The duration to wait before the next retry
     */
    Duration retryDelay(int attempt, Throwable error);
}
