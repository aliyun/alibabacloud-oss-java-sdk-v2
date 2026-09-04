package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the ListJobs operation.
 */
public final class ListJobsResult extends ResultModel {

    ListJobsResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The response body containing the list of jobs.
     */
    public ListJobsResultBody listJobsResult() {
        return (ListJobsResultBody) innerBody;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        private Builder() {
            super();
        }

        private Builder(ListJobsResult result) {
            super(result);
        }

        public ListJobsResult build() {
            return new ListJobsResult(this);
        }
    }
}
