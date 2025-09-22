package com.aliyun.sdk.service.oss2.vectors.models;

import com.aliyun.sdk.service.oss2.models.RequestModel;
import java.util.List;
import java.util.Map;
import static java.util.Objects.requireNonNull;

/**
 * The request for the PutVectors operation.
 */
public final class PutVectorsRequest extends RequestModel {
    private final String bucket;
    private final String indexName;
    private final List<Map<String, Object>> vectors;

    private PutVectorsRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
        this.indexName = builder.indexName;
        this.vectors = builder.vectors;
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
     * The list of vectors to put.
     */
    public List<Map<String, Object>> vectors() {
        return vectors;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends RequestModel.Builder<Builder> {
        private String bucket;
        private String indexName;
        private List<Map<String, Object>> vectors;

        private Builder() {
            super();
        }

        private Builder(PutVectorsRequest request) {
            super(request);
            this.bucket = request.bucket;
            this.indexName = request.indexName;
            this.vectors = request.vectors;
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
         * The list of vectors to put.
         */
        public Builder vectors(List<Map<String, Object>> value) {
            this.vectors = value;
            return this;
        }

        public PutVectorsRequest build() {
            return new PutVectorsRequest(this);
        }
    }
}
