package com.aliyun.sdk.service.oss2.vectors.models;

import com.aliyun.sdk.service.oss2.models.RequestModel;
import static java.util.Objects.requireNonNull;

/**
 * The request for the ListVectors operation.
 */
public final class ListVectorsRequest extends RequestModel {
    private final String bucket;
    private final String indexName;
    private final Integer maxResults;
    private final String nextToken;
    private final Boolean returnData;
    private final Boolean returnMetadata;
    private final Integer segmentCount;
    private final Integer segmentIndex;

    private ListVectorsRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
        this.indexName = builder.indexName;
        this.maxResults = builder.maxResults;
        this.nextToken = builder.nextToken;
        this.returnData = builder.returnData;
        this.returnMetadata = builder.returnMetadata;
        this.segmentCount = builder.segmentCount;
        this.segmentIndex = builder.segmentIndex;
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
        return indexName;
    }

    /**
     * The maximum number of vectors to return.
     */
    public Integer maxResults() {
        return maxResults;
    }

    /**
     * The token for the next page of vectors.
     */
    public String nextToken() {
        return nextToken;
    }

    /**
     * Whether to return vector data.
     */
    public Boolean returnData() {
        return returnData;
    }

    /**
     * Whether to return vector metadata.
     */
    public Boolean returnMetadata() {
        return returnMetadata;
    }

    /**
     * Number of concurrent segments.
     */
    public Integer segmentCount() {
        return segmentCount;
    }

    /**
     * Current segment index.
     */
    public Integer segmentIndex() {
        return segmentIndex;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends RequestModel.Builder<Builder> {
        private String bucket;
        private String indexName;
        private Integer maxResults;
        private String nextToken;
        private Boolean returnData;
        private Boolean returnMetadata;
        private Integer segmentCount;
        private Integer segmentIndex;

        private Builder() {
            super();
        }

        private Builder(ListVectorsRequest request) {
            super(request);
            this.bucket = request.bucket;
            this.indexName = request.indexName;
            this.maxResults = request.maxResults;
            this.nextToken = request.nextToken;
            this.returnData = request.returnData;
            this.returnMetadata = request.returnMetadata;
            this.segmentCount = request.segmentCount;
            this.segmentIndex = request.segmentIndex;
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
            this.indexName = value;
            return this;
        }

        /**
         * The maximum number of vectors to return.
         */
        public Builder maxResults(Integer value) {
            this.maxResults = value;
            return this;
        }

        /**
         * The token for the next page of vectors.
         */
        public Builder nextToken(String value) {
            this.nextToken = value;
            return this;
        }

        /**
         * Whether to return vector data.
         */
        public Builder returnData(Boolean value) {
            this.returnData = value;
            return this;
        }

        /**
         * Whether to return vector metadata.
         */
        public Builder returnMetadata(Boolean value) {
            this.returnMetadata = value;
            return this;
        }

        /**
         * Number of concurrent segments.
         */
        public Builder segmentCount(Integer value) {
            this.segmentCount = value;
            return this;
        }

        /**
         * Current segment index.
         */
        public Builder segmentIndex(Integer value) {
            this.segmentIndex = value;
            return this;
        }

        public ListVectorsRequest build() {
            return new ListVectorsRequest(this);
        }
    }
}

