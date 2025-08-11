package com.aliyun.sdk.service.oss2.retry;

import com.aliyun.sdk.service.oss2.Defaults;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * A standard retry strategy implementation that combines error retry checkers and backoff algorithms.
 */
public class StandardRetryer implements Retryer {
    private final int maxAttempts;
    private final List<ErrorRetryable> errorRetryable;
    private final BackoffDelayer backoffDelayer;

    private StandardRetryer(Builder builder) {
        this.maxAttempts = Optional.ofNullable(builder.maxAttempts).orElse(Defaults.MAX_ATTEMPTS);
        this.errorRetryable = Optional.ofNullable(builder.errorRetryable).orElse(
                Arrays.asList(new HTTPStatusCodeRetryable(),
                        new ServiceErrorCodeRetryable(),
                        new ClientErrorRetryable())
        );
        this.backoffDelayer = Optional.ofNullable(builder.backoffDelayer).orElse(
                new FullJitterBackoff(Defaults.BASE_DELAY, Defaults.MAX_BACKOFF)
        );
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Checks whether the given error is retryable by any registered checker.
     *
     * @param error The error to be checked
     * @return true if the error should be retried, false otherwise
     */
    @Override
    public boolean isErrorRetryable(Throwable error) {
        for (ErrorRetryable retryable : this.errorRetryable) {
            if (retryable.isErrorRetryable(error)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the maximum number of retry attempts.
     *
     * @return The maximum retry attempts
     */
    @Override
    public int maxAttempts() {
        return this.maxAttempts;
    }

    /**
     * Calculates the delay duration before the next retry attempt.
     *
     * @param attempt The current retry attempt number (starting from 1)
     * @param error   The error that caused the retry
     * @return The calculated delay duration
     */
    @Override
    public Duration retryDelay(int attempt, Throwable error) {
        return this.backoffDelayer.backoffDelay(attempt, error);
    }

    public static class Builder {
        private Integer maxAttempts;
        private List<ErrorRetryable> errorRetryable;
        private BackoffDelayer backoffDelayer;

        private Builder() {
            super();
        }

        /**
         * Specifies the maximum number of retry attempts allowed.
         */
        public Builder maxAttempts(Integer value) {
            this.maxAttempts = value;
            return this;
        }

        /**
         * Specifies the maximum allowed backoff duration to prevent excessive delays.
         */
        public Builder errorRetryable(List<ErrorRetryable> value) {
            this.errorRetryable = value;
            return this;
        }

        /**
         * Specifies the backoff algorithm used to calculate retry delay durations.
         */
        public Builder backoffDelayer(BackoffDelayer value) {
            this.backoffDelayer = value;
            return this;
        }

        public StandardRetryer build() {
            return new StandardRetryer(this);
        }
    }
}