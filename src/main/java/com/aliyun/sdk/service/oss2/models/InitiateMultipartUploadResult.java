package com.aliyun.sdk.service.oss2.models;

import java.util.Objects;

/**
 * The result for the InitiateMultipartUpload operation.
 */
public final class InitiateMultipartUploadResult extends ResultModel {

    private final CSEContext cseContext;

    InitiateMultipartUploadResult(Builder builder) {
        super(builder);
        this.cseContext = builder.cseContext;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The container that stores the copy result.
     */
    public InitiateMultipartUpload initiateMultipartUpload() {
        return (InitiateMultipartUpload) innerBody;
    }

    /**
     * Returns the client-side encryption context for this multipart upload session,
     * or {@code null} if this result was not created by an encryption client.
     */
    public CSEContext cseContext() {
        return cseContext;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    /**
     * Client-side encryption (CSE) context for a multipart upload session.
     * <p>
     * Holds encryption state for a single in-progress encrypted multipart upload.
     * Returned by {@code OSSEncryptionClient.initiateMultipartUpload} and passed to
     * {@code OSSEncryptionClient.uploadPart} — the user holds this context, not the client.
     * <p>
     * The internal cipher object is typed as {@code Object} to hide implementation details
     * from the public API.
     */
    public static final class CSEContext {

        private final long partSize;
        private final long dataSize;
        private final Object contentCipher;

        public CSEContext(long partSize, long dataSize, Object contentCipher) {
            this.partSize = partSize;
            this.dataSize = dataSize;
            this.contentCipher = Objects.requireNonNull(contentCipher, "contentCipher");
        }

        public long partSize() {
            return partSize;
        }

        public long dataSize() {
            return dataSize;
        }

        /**
         * Returns the internal cipher object. For use by {@code OSSEncryptionClient} only.
         */
        public Object contentCipher() {
            return contentCipher;
        }
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        private CSEContext cseContext;

        private Builder() {
            super();
        }

        private Builder(InitiateMultipartUploadResult result) {
            super(result);
            this.cseContext = result.cseContext;
        }

        public Builder cseContext(CSEContext value) {
            this.cseContext = value;
            return this;
        }

        public InitiateMultipartUploadResult build() {
            return new InitiateMultipartUploadResult(this);
        }
    }
}
