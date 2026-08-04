package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the DescribeJob operation.
 */
public final class DescribeJobResult extends ResultModel {

    DescribeJobResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The response body containing the job details.
     */
    public DescribeJobResultBody describeJobResult() {
        return (DescribeJobResultBody) innerBody;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        private Builder() {
            super();
        }

        private Builder(DescribeJobResult result) {
            super(result);
        }

        public DescribeJobResult build() {
            return new DescribeJobResult(this);
        }
    }
}
