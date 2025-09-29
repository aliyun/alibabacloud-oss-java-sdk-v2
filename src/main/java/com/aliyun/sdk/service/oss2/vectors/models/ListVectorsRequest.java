package com.aliyun.sdk.service.oss2.vectors.models;

import com.aliyun.sdk.service.oss2.models.RequestModel;
import static java.util.Objects.requireNonNull;

/**
 * The request for the ListVectors operation.
 */
public final class ListVectorsRequest extends RequestModel {
    private final String bucket;
    private final ListVectorsConfiguration listVectorsConfiguration;

    private ListVectorsRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
        this.listVectorsConfiguration = builder.listVectorsConfiguration;
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
    public ListVectorsConfiguration listVectorsConfiguration() {
        return listVectorsConfiguration;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends RequestModel.Builder<Builder> {
        private String bucket;
        private ListVectorsConfiguration listVectorsConfiguration;

        private Builder() {
            super();
            this.listVectorsConfiguration = new ListVectorsConfiguration();
        }

        private Builder(ListVectorsRequest request) {
            super(request);
            this.bucket = request.bucket;
            this.listVectorsConfiguration = request.listVectorsConfiguration;
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
            this.listVectorsConfiguration = this.listVectorsConfiguration.toBuilder().indexName(value).build();
            return this;
        }

        /**
         * The maximum number of vectors to return.
         */
        public Builder maxResults(Integer value) {
            this.listVectorsConfiguration = this.listVectorsConfiguration.toBuilder().maxResults(value).build();
            return this;
        }

        /**
         * The token for the next page of vectors.
         */
        public Builder nextToken(String value) {
            this.listVectorsConfiguration = this.listVectorsConfiguration.toBuilder().nextToken(value).build();
            return this;
        }

        /**
         * Whether to return vector data.
         */
        public Builder returnData(Boolean value) {
            this.listVectorsConfiguration = this.listVectorsConfiguration.toBuilder().returnData(value).build();
            return this;
        }

        /**
         * Whether to return vector metadata.
         */
        public Builder returnMetadata(Boolean value) {
            this.listVectorsConfiguration = this.listVectorsConfiguration.toBuilder().returnMetadata(value).build();
            return this;
        }

        /**
         * Number of concurrent segments.
         */
        public Builder segmentCount(Integer value) {
            this.listVectorsConfiguration = this.listVectorsConfiguration.toBuilder().segmentCount(value).build();
            return this;
        }

        /**
         * Current segment index.
         */
        public Builder segmentIndex(Integer value) {
            this.listVectorsConfiguration = this.listVectorsConfiguration.toBuilder().segmentIndex(value).build();
            return this;
        }

        /**
         * The request body schema.
         */
        public Builder listVectorsConfiguration(ListVectorsConfiguration listVectorsConfiguration) {
            requireNonNull(listVectorsConfiguration);
            this.listVectorsConfiguration = listVectorsConfiguration;
            return this;
        }

        public ListVectorsRequest build() {
            return new ListVectorsRequest(this);
        }
    }
}