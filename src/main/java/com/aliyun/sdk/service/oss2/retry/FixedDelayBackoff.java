package com.aliyun.sdk.service.oss2.retry;

import java.time.Duration;

/**
 * Implements a retry backoff strategy with a fixed delay.
 */
public class FixedDelayBackoff implements BackoffDelayer {
    /**
     * The fixed delay applied on each retry attempt.
     */
    private final Duration delay;

    public FixedDelayBackoff(Duration delay) {
        this.delay = delay;
    }

    /**
     * Returns the fixed delay regardless of the retry attempt count.
     *
     * @param attempt The current retry attempt number
     * @param error   The error that caused the retry
     * @return The fixed delay duration
     */
    @Override
    public Duration backoffDelay(int attempt, Throwable error) {
        return delay;
    }

    @Override
    public String toString() {
        return String.format("<FixedDelayBackoff, delay: '%s'>", delay);
    }
}
