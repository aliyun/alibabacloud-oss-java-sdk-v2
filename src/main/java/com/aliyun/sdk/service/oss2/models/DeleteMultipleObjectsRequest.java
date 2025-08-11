package com.aliyun.sdk.service.oss2.models;

import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * The request for the DeleteMultipleObjects operation.
 */
public final class DeleteMultipleObjectsRequest extends RequestModel {
    private final String bucket;
    private final Boolean quiet;
    private final List<DeleteObject> deleteObjects;

    private DeleteMultipleObjectsRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
        this.deleteObjects = builder.deleteObjects;
        this.quiet = builder.quiet;
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
     * The encoding type of the object names in the response. Valid value: url
     */
    public String encodingType() {
        String value = parameters.get("encoding-type");
        return value;
    }

    /**
     * The list that stores information about you want to delete objects.
     */
    public List<DeleteObject> deleteObjects() {
        return deleteObjects;
    }

    /**
     * The quiet mode flag.
     */
    public Boolean quiet() {
        return quiet;
    }

    /**
     * To indicate that the requester is aware that the request and data download will incur costs.
     */
    public String requestPayer() {
        String value = headers.get("x-oss-request-payer");
        return value;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends RequestModel.Builder<Builder> {
        private String bucket;
        private Boolean quiet;
        private List<DeleteObject> deleteObjects;

        private Builder() {
            super();
        }

        private Builder(DeleteMultipleObjectsRequest request) {
            super(request);
            this.bucket = request.bucket;
            this.quiet = request.quiet;
            this.deleteObjects = request.deleteObjects;
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
         * The encoding type of the object names in the response. Valid value: url
         */
        public Builder encodingType(String value) {
            requireNonNull(value);
            this.parameters.put("encoding-type", value);
            return this;
        }

        /**
         * The objects to be deleted.
         */
        public Builder deleteObjects(List<DeleteObject> value) {
            requireNonNull(value);
            this.deleteObjects = value;
            return this;
        }

        /**
         * Specifies whether to enable the Quiet return mode.
         */
        public Builder quiet(boolean value) {
            this.quiet = value;
            return this;
        }

        /**
         * To indicate that the requester is aware that the request and data download will incur costs.
         */
        public Builder requestPayer(String value) {
            requireNonNull(value);
            this.headers.put("x-oss-request-payer", value);
            return this;
        }

        public DeleteMultipleObjectsRequest build() {
            return new DeleteMultipleObjectsRequest(this);
        }
    }

}
