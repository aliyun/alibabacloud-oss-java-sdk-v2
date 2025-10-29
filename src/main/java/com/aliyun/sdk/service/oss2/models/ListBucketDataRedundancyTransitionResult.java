package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the ListBucketDataRedundancyTransition operation.
 */
public final class ListBucketDataRedundancyTransitionResult extends ResultModel { 

    /**
     * The container for listed redundancy type conversion tasks.
     */
    public ListBucketDataRedundancyTransition listBucketDataRedundancyTransition() {
        return (ListBucketDataRedundancyTransition)innerBody;
    } 
     

    ListBucketDataRedundancyTransitionResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        public ListBucketDataRedundancyTransitionResult build() {
            return new ListBucketDataRedundancyTransitionResult(this);
        }

        private Builder() {
            super();
        }

        private Builder(ListBucketDataRedundancyTransitionResult result) {
            super(result);
        }
    }
}
