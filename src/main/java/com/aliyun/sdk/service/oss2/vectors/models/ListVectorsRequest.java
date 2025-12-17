package com.aliyun.sdk.service.oss2.vectors.models;

import com.aliyun.sdk.service.oss2.models.RequestModel;
import static java.util.Objects.requireNonNull;

/**
 * The request for the ListVectors operation.
 */
public final class ListVectorsRequest extends VectorRequestModel {
    private final String bucket;

    private ListVectorsRequest(Builder builder) {
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

    /**
     * The name of the index.
     */
    public String indexName() {
        return (String)this.bodyFields.get("indexName");
    }

    /**
     * The maximum number of vectors to return.
     */
    public Integer maxResults() {
        return (Integer)this.bodyFields.get("maxResults");
    }

    /**
     * The token for the next page of vectors.
     */
    public String nextToken() {
        return (String)this.bodyFields.get("nextToken");
    }

    /**
     * Whether to return vector data.
     */
    public Boolean returnData() {
        return (Boolean)this.bodyFields.get("returnData");
    }

    /**
     * Whether to return vector metadata.
     */
    public Boolean returnMetadata() {
        return (Boolean)this.bodyFields.get("returnMetadata");
    }

    /**
     * Number of concurrent segments.
     */
    public Integer segmentCount() {
        return (Integer)this.bodyFields.get("segmentCount");
    }

    /**
     * Current segment index.
     */
    public Integer segmentIndex() {
        return (Integer)this.bodyFields.get("segmentIndex");
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends VectorRequestModel.Builder<Builder> {
        private String bucket;

        private Builder() {
            super();
        }

        private Builder(ListVectorsRequest request) {
            super(request);
            this.bucket = request.bucket;
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
            requireNonNull(value);
            this.bodyFields.put("indexName", value);
            return this;
        }

        /**
         * The maximum number of vectors to return.
         */
        public Builder maxResults(Integer value) {
            requireNonNull(value);
            this.bodyFields.put("maxResults", value);
            return this;
        }

        /**
         * The token for the next page of vectors.
         */
        public Builder nextToken(String value) {
            requireNonNull(value);
            this.bodyFields.put("nextToken", value);
            return this;
        }

        /**
         * Whether to return vector data.
         */
        public Builder returnData(Boolean value) {
            requireNonNull(value);
            this.bodyFields.put("returnData", value);
            return this;
        }

        /**
         * Whether to return vector metadata.
         */
        public Builder returnMetadata(Boolean value) {
            requireNonNull(value);
            this.bodyFields.put("returnMetadata", value);
            return this;
        }

        /**
         * Number of concurrent segments.
         */
        public Builder segmentCount(Integer value) {
            requireNonNull(value);
            this.bodyFields.put("segmentCount", value);
            return this;
        }

        /**
         * Current segment index.
         */
        public Builder segmentIndex(Integer value) {
            requireNonNull(value);
            this.bodyFields.put("segmentIndex", value);
            return this;
        }

        public ListVectorsRequest build() {
            return new ListVectorsRequest(this);
        }
    }
}