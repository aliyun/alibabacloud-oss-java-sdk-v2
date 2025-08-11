package com.aliyun.sdk.service.oss2.paginator;

import java.util.Optional;

import static java.util.Objects.requireNonNull;

public class PaginatorOptions {
    private static final PaginatorOptions DEFAULT = PaginatorOptions.newBuilder().build();

    /**
     * The maximum number of items in the response.
     */
    private final Long limit;

    /**
     * Creates an options for a paginator.
     */
    private PaginatorOptions(Builder builder) {
        this.limit = builder.limit;
    }

    /**
     * Creates a defaulted PaginatorOptions.
     *
     * @return an PaginatorOptions instances
     */
    public static PaginatorOptions defaults() {
        return DEFAULT;
    }

    /**
     * Creates a new PaginatorOptions builder.
     *
     * @return an PaginatorOptions.Builder
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Returns an Optional containing the maximum number of items in the response.
     * If no limit was set in this PaginatorOptions's builder, then the Optional is empty.
     *
     * @return an Optional containing this limit
     */
    public Optional<Long> limit() {
        return Optional.ofNullable(limit);
    }

    /**
     * A builder of OperationOptions.
     * Builders are created by invoking newBuilder.
     * Each of the setter methods modifies the state of the builder and returns the same instance.
     */
    public static class Builder {
        private Long limit;

        protected Builder() {
        }

        /**
         * Sets The maximum number of items in the response.
         *
         * @param value the maximum number attempts
         * @return this builder
         */
        public Builder limit(Long value) {
            requireNonNull(value);
            this.limit = value;
            return this;
        }

        /**
         * Returns a new instance built from the current state of this builder.
         *
         * @return a new OperationOptions
         */
        public PaginatorOptions build() {
            return new PaginatorOptions(this);
        }
    }

}
