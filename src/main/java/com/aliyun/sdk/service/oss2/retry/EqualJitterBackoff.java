package com.aliyun.sdk.service.oss2.retry;

import java.time.Duration;
import java.util.Random;

/**
 * Implements an exponential backoff strategy with equal jitter for retry delays.
 */
public class EqualJitterBackoff implements BackoffDelayer {
    /**
     * Max permitted retry times. To prevent exponentialDelay from overflow, there must be 2 ^ retriesAttempted
     * <= 2 ^ 31 - 1, which means retriesAttempted <= 30, so that is the ceil for retriesAttempted.
     */
    private final int RETRIES_ATTEMPTED_CEILING = (int) Math.floor(Math.log(Integer.MAX_VALUE) / Math.log(2));

    /**
     * The base delay used for the first retry attempt.
     */
    private final Duration baseDelay;

    /**
     * The maximum allowed backoff time to prevent excessive delays.
     */
    private final Duration maxBackoff;

    private final Random random;

    public EqualJitterBackoff(Duration baseDelay, Duration maxBackoff) {
        this.baseDelay = baseDelay;
        this.maxBackoff = maxBackoff;
        this.random = new Random();
    }

    /**
     * Calculates the jittered backoff delay for the current attempt.
     *
     * @param attempt The current retry attempt number
     * @param error   The error that caused the retry
     * @return The calculated delay duration
     */
    @Override
    public Duration backoffDelay(int attempt, Throwable error) {
        attempt = Math.min(attempt, RETRIES_ATTEMPTED_CEILING);
        int ceil = (int) Math.min(baseDelay.multipliedBy(1L << attempt).toMillis(), this.maxBackoff.toMillis());
        return Duration.ofMillis((ceil / 2) + random.nextInt((ceil / 2) + 1));
    }

    @Override
    public String toString() {
        return String.format("<EqualJitterBackoff, base delay: '%s', max backoff: '%s'>", baseDelay, maxBackoff);
    }
}
