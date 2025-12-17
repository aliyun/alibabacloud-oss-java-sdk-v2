package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the ListUserDataRedundancyTransition operation.
 */
public final class ListUserDataRedundancyTransitionResult extends ResultModel { 

    /**
     * The container for listed redundancy type conversion tasks.
     */
    public ListBucketDataRedundancyTransition listBucketDataRedundancyTransition() {
        return (ListBucketDataRedundancyTransition)innerBody;
    } 

    ListUserDataRedundancyTransitionResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        public ListUserDataRedundancyTransitionResult build() {
            return new ListUserDataRedundancyTransitionResult(this);
        }

        private Builder() {
            super();
        }

        private Builder(ListUserDataRedundancyTransitionResult result) {
            super(result);
        }
    }
}