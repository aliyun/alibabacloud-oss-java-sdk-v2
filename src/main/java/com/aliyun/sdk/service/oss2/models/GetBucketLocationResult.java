package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the GetBucketLocation operation.
 */
public final class GetBucketLocationResult extends ResultModel {

    GetBucketLocationResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The region in which the bucket resides.Examples: oss-cn-hangzhou, oss-cn-shanghai, oss-cn-qingdao, oss-cn-beijing, oss-cn-zhangjiakou, oss-cn-hongkong, oss-cn-shenzhen, oss-us-west-1, oss-us-east-1, and oss-ap-southeast-1.For more information about the regions in which buckets reside, see [Regions and endpoints](~~31837~~).
     */
    public String locationConstraint() {
        return (String) innerBody;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        private Builder() {
            super();
        }

        private Builder(GetBucketLocationResult result) {
            super(result);
        }

        public GetBucketLocationResult build() {
            return new GetBucketLocationResult(this);
        }
    }
}
