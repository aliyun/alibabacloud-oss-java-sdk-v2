package com.aliyun.sdk.service.oss2.retry;

import java.time.Duration;

/**
 * A interface class defining the backoff delay strategy for retries.
 */
public interface BackoffDelayer {
    /**
     * Calculates the delay before the next retry based on the current attempt count and error.
     *
     * @param attempt The current attempt number
     * @param error   The error that caused the retry
     * @return The duration to wait before the next retry
     */
    Duration backoffDelay(int attempt, Throwable error);
}