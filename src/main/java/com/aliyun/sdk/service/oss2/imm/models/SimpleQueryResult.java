package com.aliyun.sdk.service.oss2.imm.models;

import com.aliyun.sdk.service.oss2.models.ResultModel;

import java.util.List;

/**
 * The result for the SimpleQuery operation.
 */
public final class SimpleQueryResult extends ResultModel {

    public String nextToken() {
        SimpleQueryResponseBody body = (SimpleQueryResponseBody) innerBody;
        return body != null ? body.nextToken() : null;
    }

    public List<File> files() {
        SimpleQueryResponseBody body = (SimpleQueryResponseBody) innerBody;
        return body != null ? body.files() : null;
    }

    public List<AggregationInfo> aggregations() {
        SimpleQueryResponseBody body = (SimpleQueryResponseBody) innerBody;
        return body != null ? body.aggregations() : null;
    }

    public Long totalHits() {
        SimpleQueryResponseBody body = (SimpleQueryResponseBody) innerBody;
        return body != null ? body.totalHits() : null;
    }

    SimpleQueryResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        public SimpleQueryResult build() {
            return new SimpleQueryResult(this);
        }

        private Builder() {
            super();
        }

        private Builder(SimpleQueryResult result) {
            super(result);
        }
    }
}
