package com.aliyun.sdk.service.oss2.retry;

import java.time.Duration;
import java.util.Random;

/**
 * Implements a full jitter backoff strategy for retry delays.
 * [0.0, 1.0) * min(2 ^ attempts * baseDealy, maxBackoff)
 */
public class FullJitterBackoff implements BackoffDelayer {
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

    public FullJitterBackoff(Duration baseDelay, Duration maxBackoff) {
        this.baseDelay = baseDelay;
        this.maxBackoff = maxBackoff;
        this.random = new Random();
    }

    /**
     * Calculates the fully randomized jitter delay for the current retry attempt.
     *
     * @param attempt The current retry attempt number
     * @param error   The error that caused the retry
     * @return The calculated jitter delay duration
     */
    @Override
    public Duration backoffDelay(int attempt, Throwable error) {
        attempt = Math.min(attempt, RETRIES_ATTEMPTED_CEILING);
        int ceil = (int) Math.min(baseDelay.multipliedBy(1L << attempt).toMillis(), this.maxBackoff.toMillis());
        return Duration.ofMillis(this.random.nextInt(ceil));
    }

    @Override
    public String toString() {
        return String.format("<FullJitterBackoff, base delay: '%s', max backoff: '%s'>", baseDelay, maxBackoff);
    }
}