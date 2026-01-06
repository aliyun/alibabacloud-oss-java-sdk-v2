package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the GetBucketWorm operation.
 */
public final class GetBucketWormResult extends ResultModel { 

    /**
     * The container that stores the information about retention policies of the bucket.
     */
    public WormConfiguration wormConfiguration() {
        return (WormConfiguration)innerBody;
    } 
     

    GetBucketWormResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        public GetBucketWormResult build() {
            return new GetBucketWormResult(this);
        }

        private Builder() {
            super();
        }

        private Builder(GetBucketWormResult result) {
            super(result);
        }
    }
}
