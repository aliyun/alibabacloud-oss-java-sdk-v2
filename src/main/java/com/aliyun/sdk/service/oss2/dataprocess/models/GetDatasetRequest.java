package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.aliyun.sdk.service.oss2.models.RequestModel;
import com.aliyun.sdk.service.oss2.utils.ConvertUtils;

import static java.util.Objects.requireNonNull;

/**
 * The request for the GetDataset operation.
 */
public final class GetDatasetRequest extends RequestModel {
    private final String bucket;

    private GetDatasetRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String bucket() {
        return bucket;
    }

    public String datasetName() {
        return parameters.get("datasetName");
    }

    public Boolean withStatistics() {
        return ConvertUtils.toBoolOrNull(parameters.get("withStatistics"));
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends RequestModel.Builder<Builder> {
        private String bucket;

        private Builder() {
            super();
        }

        private Builder(GetDatasetRequest request) {
            super(request);
            this.bucket = request.bucket;
        }

        public Builder bucket(String value) {
            requireNonNull(value);
            this.bucket = value;
            return this;
        }

        public Builder datasetName(String value) {
            requireNonNull(value);
            this.parameters.put("datasetName", value);
            return this;
        }

        public Builder withStatistics(Boolean value) {
            this.parameters.put("withStatistics", value.toString());
            return this;
        }

        public GetDatasetRequest build() {
            return new GetDatasetRequest(this);
        }
    }
}
