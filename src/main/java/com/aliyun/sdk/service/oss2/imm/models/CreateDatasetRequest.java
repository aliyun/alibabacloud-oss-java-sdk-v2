package com.aliyun.sdk.service.oss2.imm.models;

import com.aliyun.sdk.service.oss2.models.RequestModel;

import static java.util.Objects.requireNonNull;

/**
 * The request for the CreateDataset operation.
 */
public final class CreateDatasetRequest extends RequestModel {
    private final String bucket;

    private CreateDatasetRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The name of the bucket.
     */
    public String bucket() {
        return bucket;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends RequestModel.Builder<Builder> {
        private String bucket;

        private Builder() {
            super();
        }

        private Builder(CreateDatasetRequest request) {
            super(request);
            this.bucket = request.bucket;
        }

        /**
         * The name of the bucket to create.
         */
        public Builder bucket(String value) {
            requireNonNull(value);
            this.bucket = value;
            return this;
        }

        public CreateDatasetRequest build() {
            return new CreateDatasetRequest(this);
        }
    }
}

