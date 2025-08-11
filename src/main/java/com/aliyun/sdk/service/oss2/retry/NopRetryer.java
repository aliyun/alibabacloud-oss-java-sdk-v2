package com.aliyun.sdk.service.oss2.retry;

import java.time.Duration;

/**
 * A no-operation retry strategy that never performs any retries.
 */
public class NopRetryer implements Retryer {
    /**
     * Always returns false, indicating the error should not be retried.
     *
     * @param error The error to be checked
     * @return Always returns false
     */
    @Override
    public boolean isErrorRetryable(Throwable error) {
        return false;
    }

    /**
     * Returns the maximum number of retry attempts, which is always 1 (no retries).
     *
     * @return Always returns 1
     */
    @Override
    public int maxAttempts() {
        return 1;
    }

    /**
     * Returns the delay duration before the next retry attempt, which is always zero seconds.
     *
     * @param attempt The current retry attempt number
     * @param error   The error that caused the retry
     * @return A zero-duration delay
     */
    @Override
    public Duration retryDelay(int attempt, Throwable error) {
        return Duration.ofSeconds(0);
    }
}
