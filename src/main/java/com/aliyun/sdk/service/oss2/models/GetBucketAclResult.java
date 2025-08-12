package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the GetBucketAcl operation.
 */
public final class GetBucketAclResult extends ResultModel { 

    /**
     * The container that stores the ACL information.
     */
    public AccessControlPolicy accessControlPolicy() {
        return (AccessControlPolicy)innerBody;
    } 
     

    GetBucketAclResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        public GetBucketAclResult build() {
            return new GetBucketAclResult(this);
        }

        private Builder() {
            super();
        }

        private Builder(GetBucketAclResult result) {
            super(result);
        }
    }
}
