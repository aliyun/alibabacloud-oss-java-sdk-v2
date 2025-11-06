package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the GetBucketReferer operation.
 */
public final class GetBucketRefererResult extends ResultModel { 

    /**
     * The container that stores the hotlink protection configurations.
     */
    public RefererConfiguration refererConfiguration() {
        return (RefererConfiguration)innerBody;
    } 
     

    GetBucketRefererResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        public GetBucketRefererResult build() {
            return new GetBucketRefererResult(this);
        }

        private Builder() {
            super();
        }

        private Builder(GetBucketRefererResult result) {
            super(result);
        }
    }
}
