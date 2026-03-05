package com.aliyun.sdk.service.oss2.imm.models;

import com.aliyun.sdk.service.oss2.models.RequestModel;

import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * The request for the SemanticQuery operation.
 */
public final class SemanticQueryRequest extends RequestModel {
    private final String bucket;
    private final String datasetName;
    private final String nextToken;
    private final Integer maxResults;
    private final String query;
    private final List<String> withFields;
    private final List<String> mediaTypes;
    private final String sourceUri;

    private SemanticQueryRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
        this.datasetName = builder.datasetName;
        this.nextToken = builder.nextToken;
        this.maxResults = builder.maxResults;
        this.query = builder.query;
        this.withFields = builder.withFields;
        this.mediaTypes = builder.mediaTypes;
        this.sourceUri = builder.sourceUri;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String bucket() {
        return bucket;
    }

    public String datasetName() {
        return datasetName;
    }

    public String nextToken() {
        return nextToken;
    }

    public Integer maxResults() {
        return maxResults;
    }

    public String query() {
        return query;
    }

    public List<String> withFields() {
        return withFields;
    }

    public List<String> mediaTypes() {
        return mediaTypes;
    }

    public String sourceUri() {
        return sourceUri;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends RequestModel.Builder<Builder> {
        private String bucket;
        private String datasetName;
        private String nextToken;
        private Integer maxResults;
        private String query;
        private List<String> withFields;
        private List<String> mediaTypes;
        private String sourceUri;

        private Builder() {
            super();
        }

        private Builder(SemanticQueryRequest request) {
            super(request);
            this.bucket = request.bucket;
            this.datasetName = request.datasetName;
            this.nextToken = request.nextToken;
            this.maxResults = request.maxResults;
            this.query = request.query;
            this.withFields = request.withFields;
            this.mediaTypes = request.mediaTypes;
            this.sourceUri = request.sourceUri;
        }

        public Builder bucket(String value) {
            requireNonNull(value);
            this.bucket = value;
            return this;
        }

        public Builder datasetName(String value) {
            requireNonNull(value);
            this.datasetName = value;
            return this;
        }

        public Builder nextToken(String value) {
            this.nextToken = value;
            return this;
        }

        public Builder maxResults(Integer value) {
            this.maxResults = value;
            return this;
        }

        public Builder query(String value) {
            this.query = value;
            return this;
        }

        public Builder withFields(List<String> value) {
            this.withFields = value;
            return this;
        }

        public Builder mediaTypes(List<String> value) {
            this.mediaTypes = value;
            return this;
        }

        public Builder sourceUri(String value) {
            this.sourceUri = value;
            return this;
        }

        public SemanticQueryRequest build() {
            return new SemanticQueryRequest(this);
        }
    }
}
