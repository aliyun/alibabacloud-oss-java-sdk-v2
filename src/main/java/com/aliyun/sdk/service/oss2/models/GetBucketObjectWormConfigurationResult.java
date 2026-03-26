package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the GetBucketObjectWormConfiguration operation.
 */
public final class GetBucketObjectWormConfigurationResult extends ResultModel {
    private final ObjectWormConfiguration objectWormConfiguration;

    private GetBucketObjectWormConfigurationResult(Builder builder) {
        super(builder);
        this.objectWormConfiguration = builder.objectWormConfiguration;
    }

    /**
     * The container that stores the object-level retention policy configuration.
     */
    public ObjectWormConfiguration objectWormConfiguration() {
        return objectWormConfiguration;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {
        private ObjectWormConfiguration objectWormConfiguration;

        /**
         * The container that stores the object-level retention policy configuration.
         */
        public Builder objectWormConfiguration(ObjectWormConfiguration value) {
            this.objectWormConfiguration = value;
            return this;
        }

        public GetBucketObjectWormConfigurationResult build() {
            return new GetBucketObjectWormConfigurationResult(this);
        }

        private Builder() {
            super();
        }

        private Builder(GetBucketObjectWormConfigurationResult result) {
            super(result);
            this.objectWormConfiguration = result.objectWormConfiguration;
        }
    }
}
