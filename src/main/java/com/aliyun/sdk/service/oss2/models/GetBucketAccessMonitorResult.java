package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the GetBucketAccessMonitor operation.
 */
public final class GetBucketAccessMonitorResult extends ResultModel { 

    /**
     * The container that stores access monitor configuration.
     */
    public AccessMonitorConfiguration accessMonitorConfiguration() {
        return (AccessMonitorConfiguration)innerBody;
    } 
     

    GetBucketAccessMonitorResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        public GetBucketAccessMonitorResult build() {
            return new GetBucketAccessMonitorResult(this);
        }

        private Builder() {
            super();
        }

        private Builder(GetBucketAccessMonitorResult result) {
            super(result);
        }
    }
}
