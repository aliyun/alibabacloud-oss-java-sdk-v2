package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the GetBucketWebsite operation.
 */
public final class GetBucketWebsiteResult extends ResultModel { 

    /**
     * The containers of the website configuration.
     */
    public WebsiteConfiguration websiteConfiguration() {
        return (WebsiteConfiguration)innerBody;
    } 
     

    GetBucketWebsiteResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        public GetBucketWebsiteResult build() {
            return new GetBucketWebsiteResult(this);
        }

        private Builder() {
            super();
        }

        private Builder(GetBucketWebsiteResult result) {
            super(result);
        }
    }
}
