package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the PutBucketAccessMonitor operation.
 */
public final class PutBucketAccessMonitorResult extends ResultModel { 

    PutBucketAccessMonitorResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        public PutBucketAccessMonitorResult build() {
            return new PutBucketAccessMonitorResult(this);
        }

        private Builder() {
            super();
        }

        private Builder(PutBucketAccessMonitorResult result) {
            super(result);
        }
    }
}
