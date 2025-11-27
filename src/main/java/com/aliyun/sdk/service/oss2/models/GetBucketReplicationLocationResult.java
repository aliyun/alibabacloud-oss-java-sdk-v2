package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the GetBucketReplicationLocation operation.
 */
public final class GetBucketReplicationLocationResult extends ResultModel { 

    /**
     * The container that stores the region in which the destination bucket can be located.
     */
    public ReplicationLocation replicationLocation() {
        return (ReplicationLocation)innerBody;
    } 
     

    GetBucketReplicationLocationResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        public GetBucketReplicationLocationResult build() {
            return new GetBucketReplicationLocationResult(this);
        }

        private Builder() {
            super();
        }

        private Builder(GetBucketReplicationLocationResult result) {
            super(result);
        }
    }
}
