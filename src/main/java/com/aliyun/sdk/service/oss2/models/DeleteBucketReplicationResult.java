package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the DeleteBucketReplication operation.
 */
public final class DeleteBucketReplicationResult extends ResultModel { 

    DeleteBucketReplicationResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        public DeleteBucketReplicationResult build() {
            return new DeleteBucketReplicationResult(this);
        }

        private Builder() {
            super();
        }

        private Builder(DeleteBucketReplicationResult result) {
            super(result);
        }
    }
}
