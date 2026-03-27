package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.aliyun.sdk.service.oss2.models.RequestModel;

import static java.util.Objects.requireNonNull;

/**
 * The request for the ListDatasets operation.
 */
public final class ListDatasetsRequest extends RequestModel {
    private final String bucket;

    private ListDatasetsRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String bucket() {
        return bucket;
    }

    public String maxResults() {
        return parameters.get("maxResults");
    }

    public String nextToken() {
        return parameters.get("nextToken");
    }

    public String prefix() {
        return parameters.get("prefix");
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends RequestModel.Builder<Builder> {
        private String bucket;

        private Builder() {
            super();
        }

        private Builder(ListDatasetsRequest request) {
            super(request);
            this.bucket = request.bucket;
        }

        public Builder bucket(String value) {
            requireNonNull(value);
            this.bucket = value;
            return this;
        }

        public Builder maxResults(Long value) {
            if (value != null) {
                this.parameters.put("maxResults", value.toString());
            }
            return this;
        }

        public Builder nextToken(String value) {
            if (value != null) {
                this.parameters.put("nextToken", value);
            }
            return this;
        }

        public Builder prefix(String value) {
            if (value != null) {
                this.parameters.put("prefix", value);
            }
            return this;
        }

        public ListDatasetsRequest build() {
            return new ListDatasetsRequest(this);
        }
    }
}
