package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the DescribeRegions operation.
 */
public final class DescribeRegionsResult extends ResultModel {

    DescribeRegionsResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The information about the regions.
     */
    public RegionInfoList regionInfoList() {
        return (RegionInfoList) innerBody;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        private Builder() {
            super();
        }

        private Builder(DescribeRegionsResult result) {
            super(result);
        }

        public DescribeRegionsResult build() {
            return new DescribeRegionsResult(this);
        }
    }
}
