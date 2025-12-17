package com.aliyun.sdk.service.oss2.models.internal;

import com.fasterxml.jackson.annotation.JsonProperty;
import static java.util.Objects.requireNonNull;

/**
 * The result for the AsyncProcessObject operation.
 */
public final class AsyncProcessJson {
    @JsonProperty("EventId")
    private String eventId;

    @JsonProperty("TaskId")
    private String taskId;

    @JsonProperty("RequestId")
    private String processRequestId;

    public AsyncProcessJson() {
    }

    private AsyncProcessJson(Builder builder) {
        this.eventId = builder.eventId;
        this.taskId = builder.taskId;
        this.processRequestId = builder.processRequestId;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The event ID of the async process.
     */
    public String eventId() {
        return this.eventId;
    }

    /**
     * The task ID of the async process.
     */
    public String taskId() {
        return this.taskId;
    }

    /**
     * The request ID of the async process.
     */
    public String processRequestId() {
        return this.processRequestId;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private String eventId;
        private String taskId;
        private String processRequestId;

        private Builder() {
            super();
        }

        private Builder(AsyncProcessJson from) {
            this.eventId = from.eventId;
            this.taskId = from.taskId;
            this.processRequestId = from.processRequestId;
        }

        /**
         * The event ID of the async process.
         */
        public Builder eventId(String value) {
            requireNonNull(value);
            this.eventId = value;
            return this;
        }

        /**
         * The task ID of the async process.
         */
        public Builder taskId(String value) {
            requireNonNull(value);
            this.taskId = value;
            return this;
        }

        /**
         * The request ID of the async process.
         */
        public Builder processRequestId(String value) {
            requireNonNull(value);
            this.processRequestId = value;
            return this;
        }

        public AsyncProcessJson build() {
            return new AsyncProcessJson(this);
        }
    }
}
