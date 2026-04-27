package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.aliyun.sdk.service.oss2.models.RequestModel;

import static java.util.Objects.requireNonNull;

/**
 * The request for the ListDataPipelineConfigurations operation.
 */
public final class ListDataPipelineConfigurationsRequest extends RequestModel {

    private ListDataPipelineConfigurationsRequest(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String maxResults() {
        return parameters.get("maxResults");
    }

    public String prefix() {
        return parameters.get("prefix");
    }

    public String nextToken() {
        return parameters.get("nextToken");
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends RequestModel.Builder<Builder> {

        private Builder() {
            super();
        }

        private Builder(ListDataPipelineConfigurationsRequest request) {
            super(request);
        }

        public Builder maxResults(Integer value) {
            this.parameters.put("maxResults", value != null ? String.valueOf(value) : null);
            return this;
        }

        public Builder prefix(String value) {
            this.parameters.put("prefix", value);
            return this;
        }

        public Builder nextToken(String value) {
            this.parameters.put("nextToken", value);
            return this;
        }

        public ListDataPipelineConfigurationsRequest build() {
            return new ListDataPipelineConfigurationsRequest(this);
        }
    }
}
