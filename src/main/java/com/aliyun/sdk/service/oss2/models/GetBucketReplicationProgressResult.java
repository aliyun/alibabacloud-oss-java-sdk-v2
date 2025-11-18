package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the GetBucketReplicationProgress operation.
 */
public final class GetBucketReplicationProgressResult extends ResultModel { 

    /**
     * The container that is used to store the progress of data replication tasks.
     */
    public ReplicationProgress replicationProgress() {
        return (ReplicationProgress)innerBody;
    } 
     

    GetBucketReplicationProgressResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        public GetBucketReplicationProgressResult build() {
            return new GetBucketReplicationProgressResult(this);
        }

        private Builder() {
            super();
        }

        private Builder(GetBucketReplicationProgressResult result) {
            super(result);
        }
    }
}
