package com.aliyun.sdk.service.oss2.imm.models;

import com.aliyun.sdk.service.oss2.models.RequestModel;
import com.aliyun.sdk.service.oss2.utils.ConvertUtils;

import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * The request for the SimpleQuery operation.
 */
public final class SimpleQueryRequest extends RequestModel {
    private final String bucket;

    private SimpleQueryRequest(Builder builder) {
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

    public String sort() {
        return parameters.get("sort");
    }

    public String order() {
        return parameters.get("order");
    }

    public String aggregations() {
        return parameters.get("aggregations");
    }

    public String withFields() {
        return parameters.get("withFields");
    }

    public Boolean withoutTotalHits() {
        return ConvertUtils.toBoolOrNull(parameters.get("withoutTotalHits"));
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends RequestModel.Builder<Builder> {
        private String bucket;

        private Builder() {
            super();
        }

        private Builder(SimpleQueryRequest request) {
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

        public Builder query(SimpleQuery value) {
            this.parameters.put("query", ImmParamHelper.toSimpleQuery(value));
            return this;
        }

        public Builder sort(String value) {
            this.parameters.put("sort", value);
            return this;
        }

        public Builder order(String value) {
            this.parameters.put("order", value);
            return this;
        }

        public Builder aggregations(List<Aggregation> value) {
            this.parameters.put("aggregations", ImmParamHelper.toAggregations(value));
            return this;
        }

        public Builder withFields(List<String> value) {
            this.parameters.put("withFields", ImmParamHelper.toStringList(value));
            return this;
        }

        public Builder withoutTotalHits(Boolean value) {
            this.parameters.put("withoutTotalHits", value.toString());
            return this;
        }

        public SimpleQueryRequest build() {
            return new SimpleQueryRequest(this);
        }
    }
}
