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
    private final SimpleQuery query;
    private final List<Aggregation> aggregations;
    private final List<String> withFields;

    private SimpleQueryRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
        this.query = builder.query;
        this.aggregations = builder.aggregations;
        this.withFields = builder.withFields;
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

    public SimpleQuery query() {
        return query;
    }

    public String sort() {
        return parameters.get("sort");
    }

    public String order() {
        return parameters.get("order");
    }

    public List<Aggregation> aggregations() {
        return aggregations;
    }

    public List<String> withFields() {
        return withFields;
    }

    public Boolean withoutTotalHits() {
        return ConvertUtils.toBoolOrNull(parameters.get("withoutTotalHits"));
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends RequestModel.Builder<Builder> {
        private String bucket;
        private SimpleQuery query;
        private List<Aggregation> aggregations;
        private List<String> withFields;

        private Builder() {
            super();
        }

        private Builder(SimpleQueryRequest request) {
            super(request);
            this.bucket = request.bucket;
            this.query = request.query;
            this.aggregations = request.aggregations;
            this.withFields = request.withFields;
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
            this.query = value;
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
            this.aggregations = value;
            return this;
        }

        public Builder withFields(List<String> value) {
            this.withFields = value;
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
