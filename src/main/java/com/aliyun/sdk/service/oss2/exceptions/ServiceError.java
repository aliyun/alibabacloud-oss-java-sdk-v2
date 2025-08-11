package com.aliyun.sdk.service.oss2.exceptions;

import com.aliyun.sdk.service.oss2.utils.MapUtils;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;

/**
 * Service Error Class that encapsulates detailed error information returned by the OSS service
 */
public class ServiceError extends RuntimeException {
    /**
     * Error message format template used to generate structured server-side error descriptions
     */
    public static final String ERROR_FMT = "Error returned by Service.\n" +
            "Http Status Code: %d.\n" +
            "Error Code: %s.\n" +
            "Request Id: %s.\n" +
            "Message: %s.\n" +
            "EC: %s.\n" +
            "Timestamp: %s.\n" +
            "Request Endpoint: %s.";

    private final int statusCode;
    private final Map<String, String> headers;
    private final Map<String, String> errorFields;
    private final String requestTarget;
    private final byte[] snapshot;
    private final Instant timestamp;

    private ServiceError(Builder builder) {
        super(String.format(ERROR_FMT,
                builder.statusCode,
                toErrorCode(builder.errorFields),
                toRequestId(builder.errorFields, builder.headers),
                toErrorMessage(builder.errorFields),
                toEc(builder.errorFields, builder.headers),
                builder.timestamp,
                builder.requestTarget));
        this.statusCode = builder.statusCode;
        this.headers = Optional.ofNullable(builder.headers).orElse(MapUtils.caseInsensitiveMap());
        this.errorFields = Optional.ofNullable(builder.errorFields).orElse(MapUtils.caseSensitiveMap());
        this.snapshot = builder.snapshot;
        this.timestamp = Optional.ofNullable(builder.timestamp).orElse(Instant.now());
        this.requestTarget = Optional.ofNullable(builder.requestTarget).orElse("");
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    private static String toErrorCode(Map<String, String> errorFields) {
        if (errorFields == null) {
            return "BadErrorResponse";
        }
        return errorFields.getOrDefault("Code", "BadErrorResponse");
    }

    private static String toErrorMessage(Map<String, String> errorFields) {
        if (errorFields == null) {
            return "";
        }
        return errorFields.getOrDefault("Message", "");
    }

    private static String toRequestId(Map<String, String> errorFields, Map<String, String> headers) {
        String value = null;
        if (errorFields != null) {
            value = errorFields.get("RequestId");
        }

        if (value == null && headers != null) {
            value = headers.get("x-oss-request-id");
        }
        return Optional.ofNullable(value).orElse("");
    }

    private static String toEc(Map<String, String> errorFields, Map<String, String> headers) {
        String value = null;
        if (errorFields != null) {
            value = errorFields.get("EC");
        }

        if (value == null && headers != null) {
            value = headers.get("x-oss-ec");
        }
        return Optional.ofNullable(value).orElse("");
    }

    /**
     * HTTP status code of the error response
     */
    public int statusCode() {
        return this.statusCode;
    }

    /**
     * Response headers associated with the error
     */
    public Map<String, String> headers() {
        return this.headers;
    }

    /**
     * information for errors, extract from response body
     */
    public Map<String, String> errorFields() {
        return this.errorFields;
    }

    /**
     * Target endpoint of the failed request
     */
    public String requestTarget() {
        return this.requestTarget;
    }

    /**
     * Snapshot of the raw response body
     */
    public byte[] snapshot() {
        return this.snapshot;
    }

    /**
     * Timestamp when the error occurred
     */
    public Instant timestamp() {
        return this.timestamp;
    }

    /**
     * Error code returned by the service
     */
    public String errorCode() {
        return toErrorCode(this.errorFields);
    }

    /**
     * Error message describing the failure
     */
    public String errorMessage() {
        return toErrorMessage(this.errorFields);
    }

    /**
     * Unique ID identifying the failed request
     */
    public String requestId() {
        return toRequestId(this.errorFields, this.headers);
    }

    /**
     * Extended error code (EC)
     */
    public String ec() {
        return toEc(this.errorFields, this.headers);
    }

    public static class Builder {
        private int statusCode;
        private Map<String, String> headers;
        private Map<String, String> errorFields;
        private String requestTarget;
        private byte[] snapshot;
        private Instant timestamp;

        private Builder() {
        }

        private Builder(ServiceError from) {
            this.statusCode = from.statusCode;
            this.headers = from.headers;
            this.errorFields = from.errorFields;
            this.requestTarget = from.requestTarget;
            this.snapshot = from.snapshot;
            this.timestamp = from.timestamp;
        }

        /**
         * Specifies HTTP status code of the error response
         */
        public Builder statusCode(int value) {
            this.statusCode = value;
            return this;
        }

        /**
         * Specifies response headers associated with the error
         */
        public Builder headers(Map<String, String> value) {
            //requireNonNull(value);
            this.headers = value;
            return this;
        }

        /**
         * Specifies information for errors
         */
        public Builder errorFields(Map<String, String> value) {
            //requireNonNull(value);
            this.errorFields = value;
            return this;
        }

        /**
         * Specifies target endpoint of the failed request
         */
        public Builder requestTarget(String value) {
            //requireNonNull(value);
            this.requestTarget = value;
            return this;
        }

        /**
         * Specifies snapshot of the raw response body
         */
        public Builder snapshot(byte[] value) {
            //requireNonNull(value);
            this.snapshot = value;
            return this;
        }

        /**
         * Specifies timestamp when the error occurred
         */
        public Builder timestamp(Instant value) {
            //requireNonNull(value);
            this.timestamp = value;
            return this;
        }

        public ServiceError build() {
            return new ServiceError(this);
        }
    }
}
