package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the InitiateBucketWorm operation.
 */
public final class InitiateBucketWormResult extends ResultModel { 

    /**
     * worm id
     */
    public String wormId() {
        String value = headers.get("x-oss-worm-id");
        return value;
    }
     

    InitiateBucketWormResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        public InitiateBucketWormResult build() {
            return new InitiateBucketWormResult(this);
        }

        private Builder() {
            super();
        }

        private Builder(InitiateBucketWormResult result) {
            super(result);
        }
    }
}
