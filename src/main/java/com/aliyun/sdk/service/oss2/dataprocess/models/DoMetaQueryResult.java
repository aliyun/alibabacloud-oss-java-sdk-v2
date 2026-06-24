package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.aliyun.sdk.service.oss2.models.ResultModel;

import java.util.List;

public final class DoMetaQueryResult extends ResultModel {

    public String nextToken() {
        DoMetaQueryResponseBody body = (DoMetaQueryResponseBody) innerBody;
        return body != null ? body.nextToken() : null;
    }

    public List<File> files() {
        DoMetaQueryResponseBody body = (DoMetaQueryResponseBody) innerBody;
        return body != null ? body.files() : null;
    }

    public List<AggregationInfo> aggregations() {
        DoMetaQueryResponseBody body = (DoMetaQueryResponseBody) innerBody;
        return body != null ? body.aggregations() : null;
    }

    public Long totalHits() {
        DoMetaQueryResponseBody body = (DoMetaQueryResponseBody) innerBody;
        return body != null ? body.totalHits() : null;
    }

    DoMetaQueryResult(Builder builder) { super(builder); }

    public static Builder newBuilder() { return new Builder(); }
    public Builder toBuilder() { return new Builder(this); }

    public static class Builder extends ResultModel.Builder<Builder> {
        public DoMetaQueryResult build() { return new DoMetaQueryResult(this); }
        private Builder() { super(); }
        private Builder(DoMetaQueryResult result) { super(result); }
    }
}
