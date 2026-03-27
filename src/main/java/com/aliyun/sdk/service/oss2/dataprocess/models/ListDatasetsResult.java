package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.aliyun.sdk.service.oss2.models.ResultModel;

import java.util.List;

/**
 * The result for the ListDatasets operation.
 */
public final class ListDatasetsResult extends ResultModel {

    public String nextToken() {
        ListDatasetsResponseBody body = (ListDatasetsResponseBody) innerBody;
        return body != null ? body.nextToken() : null;
    }

    public List<Dataset> datasets() {
        ListDatasetsResponseBody body = (ListDatasetsResponseBody) innerBody;
        return body != null ? body.datasets() : null;
    }

    ListDatasetsResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        public ListDatasetsResult build() {
            return new ListDatasetsResult(this);
        }

        private Builder() {
            super();
        }

        private Builder(ListDatasetsResult result) {
            super(result);
        }
    }
}
