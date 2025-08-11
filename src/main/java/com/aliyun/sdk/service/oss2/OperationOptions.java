package com.aliyun.sdk.service.oss2;

import com.aliyun.sdk.service.oss2.types.AuthMethodType;

import java.time.Duration;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

/**
 * An operation options
 */
public class OperationOptions {
    private static final OperationOptions DEFAULT = newBuilder().build();
    private final Integer retryMaxAttempts;
    private final Duration readWriteTimeout;
    private final AuthMethodType authMethod;

    /**
     * Creates an options for each operation call.
     */
    private OperationOptions(Builder builder) {
        this.retryMaxAttempts = builder.retryMaxAttempts;
        this.readWriteTimeout = builder.readWriteTimeout;
        this.authMethod = builder.authMethod;
    }

    /**
     * Creates a defaulted OperationOptions.
     *
     * @return an OperationOptions instances
     */
    public static OperationOptions defaults() {
        return DEFAULT;
    }

    /**
     * Creates a new OperationOptions builder.
     *
     * @return an OperationOptions.Builder
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Returns an Optional containing the maximum number attempts.
     * If no retryMaxAttempts was set in this OperationOptions's builder, then the Optional is empty.
     *
     * @return an Optional containing this retryMaxAttempts
     */
    public Optional<Integer> retryMaxAttempts() {
        return Optional.ofNullable(retryMaxAttempts);
    }

    /**
     * Returns an Optional containing the read write timeout duration.
     * If no readWriteTimeout was set in this OperationOptions's builder, then the Optional is empty.
     *
     * @return an Optional containing this readWriteTimeout
     */
    public Optional<Duration> readWriteTimeout() {
        return Optional.ofNullable(readWriteTimeout);
    }

    /**
     * Returns an Optional containing the way in which it is signed
     *
     * @return an Optional containing the authentication method
     */
    public Optional<AuthMethodType> authMethod() {
        return Optional.ofNullable(authMethod);
    }

    /**
     * A builder of OperationOptions.
     * Builders are created by invoking newBuilder.
     * Each of the setter methods modifies the state of the builder and returns the same instance.
     */
    public static class Builder {
        private Integer retryMaxAttempts;
        private Duration readWriteTimeout;
        private AuthMethodType authMethod;

        protected Builder() {
        }

        /**
         * Sets the maximum number attempts an API client will call an operation that fails with a retryable error.
         *
         * @param retryMaxAttempts the maximum number attempts
         * @return this builder
         */
        public Builder retryMaxAttempts(Integer retryMaxAttempts) {
            requireNonNull(retryMaxAttempts);
            this.retryMaxAttempts = retryMaxAttempts;
            return this;
        }

        /**
         * Sets the read write timeout duration。
         *
         * @param readWriteTimeout the duration of the readwrite timeout
         * @return this builder
         */
        public Builder readWriteTimeout(Duration readWriteTimeout) {
            requireNonNull(readWriteTimeout);
            this.readWriteTimeout = readWriteTimeout;
            return this;
        }

        /**
         * Sets the way in which it is signed
         *
         * @param authMethod the authentication method
         * @return this builder
         */
        public Builder authMethod(AuthMethodType authMethod) {
            requireNonNull(authMethod);
            this.authMethod = authMethod;
            return this;
        }

        /**
         * Returns a new instance built from the current state of this builder.
         *
         * @return a new OperationOptions
         */
        public OperationOptions build() {
            return new OperationOptions(this);
        }
    }
}
