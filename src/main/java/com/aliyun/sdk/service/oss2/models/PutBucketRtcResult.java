package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the PutBucketRtc operation.
 */
public final class PutBucketRtcResult extends ResultModel { 

    PutBucketRtcResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        public PutBucketRtcResult build() {
            return new PutBucketRtcResult(this);
        }

        private Builder() {
            super();
        }

        private Builder(PutBucketRtcResult result) {
            super(result);
        }
    }
}
