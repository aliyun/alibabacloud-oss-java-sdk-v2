package com.aliyun.sdk.service.oss2.vectors.models;

import com.aliyun.sdk.service.oss2.models.RequestModel;
import java.util.List;
import static java.util.Objects.requireNonNull;

/**
 * The request for the DeleteVectors operation.
 */
public final class DeleteVectorsRequest extends RequestModel {
    private final String bucket;
    private final DeleteVectorsConfiguration deleteVectorsConfiguration;

    private DeleteVectorsRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
        this.deleteVectorsConfiguration = builder.deleteVectorsConfiguration;
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
    public DeleteVectorsConfiguration deleteVectorsConfiguration() {
        return deleteVectorsConfiguration;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends RequestModel.Builder<Builder> {
        private String bucket;
        private DeleteVectorsConfiguration deleteVectorsConfiguration;

        private Builder() {
            super();
            this.deleteVectorsConfiguration = new DeleteVectorsConfiguration();
        }

        private Builder(DeleteVectorsRequest request) {
            super(request);
            this.bucket = request.bucket;
            this.deleteVectorsConfiguration = request.deleteVectorsConfiguration;
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
            this.deleteVectorsConfiguration = this.deleteVectorsConfiguration.toBuilder().indexName(value).build();
            return this;
        }

        /**
         * The list of vector keys to delete.
         */
        public Builder keys(List<String> value) {
            this.deleteVectorsConfiguration = this.deleteVectorsConfiguration.toBuilder().keys(value).build();
            return this;
        }

        /**
         * The request body schema.
         */
        public Builder deleteVectorsConfiguration(DeleteVectorsConfiguration deleteVectorsConfiguration) {
            requireNonNull(deleteVectorsConfiguration);
            this.deleteVectorsConfiguration = deleteVectorsConfiguration;
            return this;
        }

        public DeleteVectorsRequest build() {
            return new DeleteVectorsRequest(this);
        }
    }
}