package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.aliyun.sdk.service.oss2.models.ResultModel;

import java.util.List;

/**
 * The result for the ListDataPipelineConfigurations operation.
 */
public final class ListDataPipelineConfigurationsResult extends ResultModel {

    public List<DataPipelineConfiguration> dataPipelineConfigurations() {
        ListDataPipelineConfigurationsResponseBody body = (ListDataPipelineConfigurationsResponseBody) innerBody;
        return body != null ? body.dataPipelineConfigurations() : null;
    }

    public String nextToken() {
        ListDataPipelineConfigurationsResponseBody body = (ListDataPipelineConfigurationsResponseBody) innerBody;
        return body != null ? body.nextToken() : null;
    }

    ListDataPipelineConfigurationsResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        public ListDataPipelineConfigurationsResult build() {
            return new ListDataPipelineConfigurationsResult(this);
        }

        private Builder() {
            super();
        }

        private Builder(ListDataPipelineConfigurationsResult result) {
            super(result);
        }
    }
}
