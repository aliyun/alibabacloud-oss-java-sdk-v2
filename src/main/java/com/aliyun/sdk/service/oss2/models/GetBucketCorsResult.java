package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the GetBucketCors operation.
 */
public final class GetBucketCorsResult extends ResultModel {

    GetBucketCorsResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The container that stores CORS configuration.
     */
    public CORSConfiguration corsConfiguration() {
        return (CORSConfiguration) innerBody;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        private Builder() {
            super();
        }

        private Builder(GetBucketCorsResult result) {
            super(result);
        }

        public GetBucketCorsResult build() {
            return new GetBucketCorsResult(this);
        }
    }
}
