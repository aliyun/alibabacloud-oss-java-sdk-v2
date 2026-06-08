package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the PutBucketReplication operation.
 */
public final class PutBucketReplicationResult extends ResultModel { 

    /**
     * Get the replication rule ID.
     */
    public String replicationRuleId() {
        String value = headers.get("x-oss-replication-rule-id");
        return value;
    }
     

    PutBucketReplicationResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        public PutBucketReplicationResult build() {
            return new PutBucketReplicationResult(this);
        }

        private Builder() {
            super();
        }

        private Builder(PutBucketReplicationResult result) {
            super(result);
        }
    }
}