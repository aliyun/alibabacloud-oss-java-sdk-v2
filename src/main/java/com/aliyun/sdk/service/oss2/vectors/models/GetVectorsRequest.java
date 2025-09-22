package com.aliyun.sdk.service.oss2.vectors.models;

import com.aliyun.sdk.service.oss2.models.RequestModel;
import java.util.List;
import static java.util.Objects.requireNonNull;

/**
 * The request for the GetVectors operation.
 */
public final class GetVectorsRequest extends RequestModel {
    private final String bucket;
    private final String indexName;
    private final List<String> keys;
    private final Boolean returnData;
    private final Boolean returnMetadata;

    private GetVectorsRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
        this.indexName = builder.indexName;
        this.keys = builder.keys;
        this.returnData = builder.returnData;
        this.returnMetadata = builder.returnMetadata;
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
     * The list of vector keys to retrieve.
     */
    public List<String> keys() {
        return keys;
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

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends RequestModel.Builder<Builder> {
        private String bucket;
        private String indexName;
        private List<String> keys;
        private Boolean returnData;
        private Boolean returnMetadata;

        private Builder() {
            super();
        }

        private Builder(GetVectorsRequest request) {
            super(request);
            this.bucket = request.bucket;
            this.indexName = request.indexName;
            this.keys = request.keys;
            this.returnData = request.returnData;
            this.returnMetadata = request.returnMetadata;
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
         * The list of vector keys to retrieve.
         */
        public Builder keys(List<String> value) {
            this.keys = value;
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

        public GetVectorsRequest build() {
            return new GetVectorsRequest(this);
        }
    }
}
