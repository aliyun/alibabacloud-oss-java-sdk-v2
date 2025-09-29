package com.aliyun.sdk.service.oss2.vectors.models;

import com.aliyun.sdk.service.oss2.models.RequestModel;
import java.util.List;
import static java.util.Objects.requireNonNull;

/**
 * The request for the GetVectors operation.
 */
public final class GetVectorsRequest extends RequestModel {
    private final String bucket;
    private final GetVectorsConfiguration getVectorsConfiguration;

    private GetVectorsRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
        this.getVectorsConfiguration = builder.getVectorsConfiguration;
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

    /**
     * The request body schema.
     */
    public GetVectorsConfiguration getVectorsConfiguration() {
        return getVectorsConfiguration;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends RequestModel.Builder<Builder> {
        private String bucket;
        private GetVectorsConfiguration getVectorsConfiguration;

        private Builder() {
            super();
            this.getVectorsConfiguration = new GetVectorsConfiguration();
        }

        private Builder(GetVectorsRequest request) {
            super(request);
            this.bucket = request.bucket;
            this.getVectorsConfiguration = request.getVectorsConfiguration;
        }

        /**
         * The name of the bucket.
         */
        public Builder bucket(String value) {
            requireNonNull(value);
            this.bucket = value;
            return this;
        }

        /**
         * The name of the index.
         */
        public Builder indexName(String value) {
            this.getVectorsConfiguration = this.getVectorsConfiguration.toBuilder().indexName(value).build();
            return this;
        }

        /**
         * The list of vector keys to retrieve.
         */
        public Builder keys(List<String> value) {
            this.getVectorsConfiguration = this.getVectorsConfiguration.toBuilder().keys(value).build();
            return this;
        }

        /**
         * Whether to return vector data.
         */
        public Builder returnData(Boolean value) {
            this.getVectorsConfiguration = this.getVectorsConfiguration.toBuilder().returnData(value).build();
            return this;
        }

        /**
         * Whether to return vector metadata.
         */
        public Builder returnMetadata(Boolean value) {
            this.getVectorsConfiguration = this.getVectorsConfiguration.toBuilder().returnMetadata(value).build();
            return this;
        }

        /**
         * The request body schema.
         */
        public Builder getVectorsConfiguration(GetVectorsConfiguration getVectorsConfiguration) {
            requireNonNull(getVectorsConfiguration);
            this.getVectorsConfiguration = getVectorsConfiguration;
            return this;
        }

        public GetVectorsRequest build() {
            return new GetVectorsRequest(this);
        }
    }
}