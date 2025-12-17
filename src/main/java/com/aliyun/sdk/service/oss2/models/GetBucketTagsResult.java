package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the GetBucketTags operation.
 */
public final class GetBucketTagsResult extends ResultModel { 

    /**
     * The container that stores the returned tags of the bucket. If no tags are configured for the bucket, an XML message body is returned in which the Tagging element is empty.
     */
    public Tagging tagging() {
        return (Tagging)innerBody;
    } 
     

    GetBucketTagsResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        public GetBucketTagsResult build() {
            return new GetBucketTagsResult(this);
        }

        private Builder() {
            super();
        }

        private Builder(GetBucketTagsResult result) {
            super(result);
        }
    }
}
