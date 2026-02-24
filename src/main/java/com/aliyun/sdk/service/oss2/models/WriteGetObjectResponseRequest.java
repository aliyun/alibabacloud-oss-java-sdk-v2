package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.transport.BinaryData;
import static java.util.Objects.requireNonNull;
import java.io.InputStream;

/**
 * The request for the WriteGetObjectResponse operation.
 */
public final class WriteGetObjectResponseRequest extends RequestModel {
    private final String requestRoute;
    private final String requestToken;
    private final String fwdStatus;
    private final String fwdHeaderAcceptRanges;
    private final String fwdHeaderCacheControl;
    private final String fwdHeaderContentDisposition;
    private final String fwdHeaderContentEncoding;
    private final String fwdHeaderContentLanguage;
    private final String fwdHeaderContentRange;
    private final String fwdHeaderContentType;
    private final String fwdHeaderEtag;
    private final String fwdHeaderExpires;
    private final String fwdHeaderLastModified;
    private final BinaryData body;

    private WriteGetObjectResponseRequest(Builder builder) {
        super(builder);
        this.requestRoute = builder.requestRoute;
        this.requestToken = builder.requestToken;
        this.fwdStatus = builder.fwdStatus;
        this.fwdHeaderAcceptRanges = builder.fwdHeaderAcceptRanges;
        this.fwdHeaderCacheControl = builder.fwdHeaderCacheControl;
        this.fwdHeaderContentDisposition = builder.fwdHeaderContentDisposition;
        this.fwdHeaderContentEncoding = builder.fwdHeaderContentEncoding;
        this.fwdHeaderContentLanguage = builder.fwdHeaderContentLanguage;
        this.fwdHeaderContentRange = builder.fwdHeaderContentRange;
        this.fwdHeaderContentType = builder.fwdHeaderContentType;
        this.fwdHeaderEtag = builder.fwdHeaderEtag;
        this.fwdHeaderExpires = builder.fwdHeaderExpires;
        this.fwdHeaderLastModified = builder.fwdHeaderLastModified;
        this.body = builder.body;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Gets the request route.
     */
    public String requestRoute() {
        return requestRoute;
    }

    /**
     * Gets the request token.
     */
    public String requestToken() {
        return requestToken;
    }

    /**
     * Gets the forward status.
     */
    public String fwdStatus() {
        return fwdStatus;
    }

    /**
     * Gets the forward header accept ranges.
     */
    public String fwdHeaderAcceptRanges() {
        return fwdHeaderAcceptRanges;
    }

    /**
     * Gets the forward header cache control.
     */
    public String fwdHeaderCacheControl() {
        return fwdHeaderCacheControl;
    }

    /**
     * Gets the forward header content disposition.
     */
    public String fwdHeaderContentDisposition() {
        return fwdHeaderContentDisposition;
    }

    /**
     * Gets the forward header content encoding.
     */
    public String fwdHeaderContentEncoding() {
        return fwdHeaderContentEncoding;
    }

    /**
     * Gets the forward header content language.
     */
    public String fwdHeaderContentLanguage() {
        return fwdHeaderContentLanguage;
    }

    /**
     * Gets the forward header content range.
     */
    public String fwdHeaderContentRange() {
        return fwdHeaderContentRange;
    }

    /**
     * Gets the forward header content type.
     */
    public String fwdHeaderContentType() {
        return fwdHeaderContentType;
    }

    /**
     * Gets the forward header etag.
     */
    public String fwdHeaderEtag() {
        return fwdHeaderEtag;
    }

    /**
     * Gets the forward header expires.
     */
    public String fwdHeaderExpires() {
        return fwdHeaderExpires;
    }

    /**
     * Gets the forward header last modified.
     */
    public String fwdHeaderLastModified() {
        return fwdHeaderLastModified;
    }

    /**
     * Gets the body.
     */
    public BinaryData body() {
        return body;
    }

    /**
     * Gets the body as string.
     */
    public String bodyAsString() {
        return this.body != null ? this.body.toString() : null;
    }

    /**
     * Gets the body as input stream.
     */
    public InputStream bodyAsStream() {
        return this.body != null ? this.body.toStream() : null;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends RequestModel.Builder<Builder> {
        private String requestRoute;
        private String requestToken;
        private String fwdStatus;
        private String fwdHeaderAcceptRanges;
        private String fwdHeaderCacheControl;
        private String fwdHeaderContentDisposition;
        private String fwdHeaderContentEncoding;
        private String fwdHeaderContentLanguage;
        private String fwdHeaderContentRange;
        private String fwdHeaderContentType;
        private String fwdHeaderEtag;
        private String fwdHeaderExpires;
        private String fwdHeaderLastModified;
        private BinaryData body;

        private Builder() {
            super();
        }

        private Builder(WriteGetObjectResponseRequest request) {
            super(request);
            this.requestRoute = request.requestRoute;
            this.requestToken = request.requestToken;
            this.fwdStatus = request.fwdStatus;
            this.fwdHeaderAcceptRanges = request.fwdHeaderAcceptRanges;
            this.fwdHeaderCacheControl = request.fwdHeaderCacheControl;
            this.fwdHeaderContentDisposition = request.fwdHeaderContentDisposition;
            this.fwdHeaderContentEncoding = request.fwdHeaderContentEncoding;
            this.fwdHeaderContentLanguage = request.fwdHeaderContentLanguage;
            this.fwdHeaderContentRange = request.fwdHeaderContentRange;
            this.fwdHeaderContentType = request.fwdHeaderContentType;
            this.fwdHeaderEtag = request.fwdHeaderEtag;
            this.fwdHeaderExpires = request.fwdHeaderExpires;
            this.fwdHeaderLastModified = request.fwdHeaderLastModified;
            this.body = request.body;
        }

        public static Builder newBuilder() {
            return new Builder();
        }

        /**
         * Sets the request route.
         */
        public Builder requestRoute(String value) {
            requireNonNull(value);
            this.requestRoute = value;
            return this;
        }

        /**
         * Sets the request token.
         */
        public Builder requestToken(String value) {
            requireNonNull(value);
            this.requestToken = value;
            return this;
        }

        /**
         * Sets the forward status.
         */
        public Builder fwdStatus(String value) {
            requireNonNull(value);
            this.fwdStatus = value;
            return this;
        }

        /**
         * Sets the forward header accept ranges.
         */
        public Builder fwdHeaderAcceptRanges(String value) {
            this.fwdHeaderAcceptRanges = value;
            return this;
        }

        /**
         * Sets the forward header cache control.
         */
        public Builder fwdHeaderCacheControl(String value) {
            this.fwdHeaderCacheControl = value;
            return this;
        }

        /**
         * Sets the forward header content disposition.
         */
        public Builder fwdHeaderContentDisposition(String value) {
            this.fwdHeaderContentDisposition = value;
            return this;
        }

        /**
         * Sets the forward header content encoding.
         */
        public Builder fwdHeaderContentEncoding(String value) {
            this.fwdHeaderContentEncoding = value;
            return this;
        }

        /**
         * Sets the forward header content language.
         */
        public Builder fwdHeaderContentLanguage(String value) {
            this.fwdHeaderContentLanguage = value;
            return this;
        }

        /**
         * Sets the forward header content range.
         */
        public Builder fwdHeaderContentRange(String value) {
            this.fwdHeaderContentRange = value;
            return this;
        }

        /**
         * Sets the forward header content type.
         */
        public Builder fwdHeaderContentType(String value) {
            this.fwdHeaderContentType = value;
            return this;
        }

        /**
         * Sets the forward header etag.
         */
        public Builder fwdHeaderEtag(String value) {
            this.fwdHeaderEtag = value;
            return this;
        }

        /**
         * Sets the forward header expires.
         */
        public Builder fwdHeaderExpires(String value) {
            this.fwdHeaderExpires = value;
            return this;
        }

        /**
         * Sets the forward header last modified.
         */
        public Builder fwdHeaderLastModified(String value) {
            this.fwdHeaderLastModified = value;
            return this;
        }

        /**
         * Sets the body as string.
         */
        public Builder body(String value) {
            this.body = BinaryData.fromString(value);
            return this;
        }

        /**
         * Sets the body as input stream.
         */
        public Builder body(InputStream value) {
            this.body = BinaryData.fromStream(value);
            return this;
        }

        /**
         * Sets the body as binary data.
         */
        public Builder body(BinaryData value) {
            this.body = value;
            return this;
        }

        public WriteGetObjectResponseRequest build() {
            return new WriteGetObjectResponseRequest(this);
        }
    }

    /**
     * Constructs a new WriteGetObjectResponseRequest with the specified route, token, status and body.
     *
     * @param requestRoute The request route.
     * @param requestToken The request token.
     * @param fwdStatus The forward status.
     * @param body The body as input stream.
     */
    public WriteGetObjectResponseRequest(String requestRoute, String requestToken, String fwdStatus, InputStream body) {
        this(new Builder()
                .requestRoute(requestRoute)
                .requestToken(requestToken)
                .fwdStatus(fwdStatus)
                .body(body));
    }

    /**
     * Constructs a new WriteGetObjectResponseRequest with the specified route, token, status and body.
     *
     * @param requestRoute The request route.
     * @param requestToken The request token.
     * @param fwdStatus The forward status.
     * @param body The body as string.
     */
    public WriteGetObjectResponseRequest(String requestRoute, String requestToken, String fwdStatus, String body) {
        this(new Builder()
                .requestRoute(requestRoute)
                .requestToken(requestToken)
                .fwdStatus(fwdStatus)
                .body(body));
    }

    /**
     * Constructs a new WriteGetObjectResponseRequest with the specified route, token, status and body.
     *
     * @param requestRoute The request route.
     * @param requestToken The request token.
     * @param fwdStatus The forward status.
     * @param body The body as binary data.
     */
    public WriteGetObjectResponseRequest(String requestRoute, String requestToken, String fwdStatus, BinaryData body) {
        this(new Builder()
                .requestRoute(requestRoute)
                .requestToken(requestToken)
                .fwdStatus(fwdStatus)
                .body(body));
    }
}