package com.aliyun.sdk.service.oss2.vectors.models;

import com.aliyun.sdk.service.oss2.models.RequestModel;
import com.aliyun.sdk.service.oss2.vectors.models.internal.GetVectorsRequestJson;
import java.util.List;
import static java.util.Objects.requireNonNull;

/**
 * The request for the GetVectors operation.
 */
public final class GetVectorsRequest extends RequestModel {
    private final String bucket;
    private final GetVectorsRequestJson getVectorsRequestJson;

    private GetVectorsRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
        this.getVectorsRequestJson = builder.getVectorsRequestJson;
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
    public GetVectorsRequestJson getVectorsRequestJson() {
        return getVectorsRequestJson;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends RequestModel.Builder<Builder> {
        private String bucket;
        private GetVectorsRequestJson getVectorsRequestJson;

        private Builder() {
            super();
            this.getVectorsRequestJson = new GetVectorsRequestJson();
        }

        private Builder(GetVectorsRequest request) {
            super(request);
            this.bucket = request.bucket;
            this.getVectorsRequestJson = request.getVectorsRequestJson;
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
            this.getVectorsRequestJson.indexName = value;
            return this;
        }

        /**
         * The list of vector keys to retrieve.
         */
        public Builder keys(List<String> value) {
            this.getVectorsRequestJson.keys = value;
            return this;
        }

        /**
         * Whether to return vector data.
         */
        public Builder returnData(Boolean value) {
            this.getVectorsRequestJson.returnData = value;
            return this;
        }

        /**
         * Whether to return vector metadata.
         */
        public Builder returnMetadata(Boolean value) {
            this.getVectorsRequestJson.returnMetadata = value;
            return this;
        }

        /**
         * The request body schema.
         */
        public Builder getVectorsRequestJson(GetVectorsRequestJson getVectorsRequestJson) {
            requireNonNull(getVectorsRequestJson);
            this.getVectorsRequestJson = getVectorsRequestJson;
            return this;
        }

        public GetVectorsRequest build() {
            return new GetVectorsRequest(this);
        }
    }
}
