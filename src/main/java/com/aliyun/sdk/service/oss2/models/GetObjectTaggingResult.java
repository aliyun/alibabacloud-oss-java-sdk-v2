package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the GetObjectTagging operation.
 */
public final class GetObjectTaggingResult extends ResultModel {

    GetObjectTaggingResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The container that stores the returned tag of the bucket.
     */
    public Tagging tagging() {
        return (Tagging) innerBody;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        private Builder() {
            super();
        }

        private Builder(GetObjectTaggingResult result) {
            super(result);
        }

        public GetObjectTaggingResult build() {
            return new GetObjectTaggingResult(this);
        }
    }
}
