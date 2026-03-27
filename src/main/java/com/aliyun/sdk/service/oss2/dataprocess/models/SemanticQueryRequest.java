package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.aliyun.sdk.service.oss2.models.RequestModel;
import com.aliyun.sdk.service.oss2.utils.ConvertUtils;

import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * The request for the SemanticQuery operation.
 */
public final class SemanticQueryRequest extends RequestModel {
    private final String bucket;

    private SemanticQueryRequest(Builder builder) {
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

    public String nextToken() {
        return parameters.get("nextToken");
    }

    public Integer maxResults() {
        return ConvertUtils.toIntegerOrNull(parameters.get("maxResults"));
    }

    public String query() {
        return parameters.get("query");
    }

    public String withFields() {
        return parameters.get("withFields");
    }

    public String mediaTypes() {
        return parameters.get("mediaTypes");
    }

    public String sourceUri() {
        return parameters.get("sourceURI");
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends RequestModel.Builder<Builder> {
        private String bucket;

        private Builder() {
            super();
        }

        private Builder(SemanticQueryRequest request) {
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

        public Builder nextToken(String value) {
            this.parameters.put("nextToken", value);
            return this;
        }

        public Builder maxResults(Integer value) {
            this.parameters.put("maxResults", value.toString());
            return this;
        }

        public Builder query(String value) {
            this.parameters.put("query", value);
            return this;
        }

        public Builder withFields(List<String> value) {
            this.parameters.put("withFields", DataProcessParamHelper.toStringList(value));
            return this;
        }

        public Builder withFields(String value) {
            this.parameters.put("withFields", value);
            return this;
        }

        public Builder mediaTypes(List<String> value) {
            this.parameters.put("mediaTypes", DataProcessParamHelper.toStringList(value));
            return this;
        }

        public Builder mediaTypes(String value) {
            this.parameters.put("mediaTypes", value);
            return this;
        }

        public Builder sourceUri(String value) {
            this.parameters.put("sourceURI", value);
            return this;
        }

        public SemanticQueryRequest build() {
            return new SemanticQueryRequest(this);
        }
    }
}
