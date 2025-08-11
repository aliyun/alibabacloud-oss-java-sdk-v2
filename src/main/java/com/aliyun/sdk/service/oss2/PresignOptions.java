package com.aliyun.sdk.service.oss2;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

public class PresignOptions {
    private static final PresignOptions DEFAULT = newBuilder().build();
    private final Instant expiration;

    /**
     * Creates an options for each presign call.
     */
    private PresignOptions(Builder builder) {
        this.expiration = builder.expiration;
    }

    /**
     * Creates a defaulted PresignOptions.
     *
     * @return an PresignOptions instances
     */
    public static PresignOptions defaults() {
        return DEFAULT;
    }

    /**
     * Creates a new PresignOptions builder.
     *
     * @return an PresignOptions.Builder
     */
    public static Builder newBuilder() {
        return new PresignOptions.Builder();
    }

    /**
     * Returns the expiration time for the generated presign url
     *
     * @return an Optional containing the expiration time.
     */
    public Optional<Instant> expiration() {
        return Optional.ofNullable(expiration);
    }

    /**
     * A builder of PresignOptions.
     * Builders are created by invoking newBuilder.
     * Each of the setter methods modifies the state of the builder and returns the same instance.
     */
    public static class Builder {
        private Instant expiration;

        protected Builder() {
        }

        /**
         * Sets the expiration time for the generated presign url。
         *
         * @param value the expiration time
         * @return this builder
         */
        public Builder expiration(Instant value) {
            requireNonNull(value);
            this.expiration = value;
            return this;
        }

        /**
         * Set the interval of expiration time, how long has it expired
         * The minimum precision of the unit is seconds.
         *
         * @param value the interval of expiration time.
         * @return this builder
         */
        public Builder expiration(Duration value) {
            requireNonNull(value);
            this.expiration = Instant.now().plusSeconds(value.getSeconds());;
            return this;
        }

        /**
         * Returns a new instance built from the current state of this builder.
         *
         * @return a new PresignOptions
         */
        public PresignOptions build() {
            return new PresignOptions(this);
        }
    }

}
