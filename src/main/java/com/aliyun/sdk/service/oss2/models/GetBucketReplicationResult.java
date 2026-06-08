package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the GetBucketReplication operation.
 */
public final class GetBucketReplicationResult extends ResultModel { 

    /**
     * The container that stores data replication configurations.
     */
    public ReplicationConfiguration replicationConfiguration() {
        return (ReplicationConfiguration)innerBody;
    } 
     

    GetBucketReplicationResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        public GetBucketReplicationResult build() {
            return new GetBucketReplicationResult(this);
        }

        private Builder() {
            super();
        }

        private Builder(GetBucketReplicationResult result) {
            super(result);
        }
    }
}
