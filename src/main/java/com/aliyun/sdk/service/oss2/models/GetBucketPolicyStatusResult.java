package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the GetBucketPolicyStatus operation.
 */
public final class GetBucketPolicyStatusResult extends ResultModel { 

    /**
     * The container that stores public access information.
     */
    public PolicyStatus policyStatus() {
        return (PolicyStatus)innerBody;
    } 
     

    GetBucketPolicyStatusResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        public GetBucketPolicyStatusResult build() {
            return new GetBucketPolicyStatusResult(this);
        }

        private Builder() {
            super();
        }

        private Builder(GetBucketPolicyStatusResult result) {
            super(result);
        }
    }
}
