package com.aliyun.sdk.service.oss2.imm.models;

import com.aliyun.sdk.service.oss2.models.ResultModel;

import java.util.List;

/**
 * The result for the SemanticQuery operation.
 */
public final class SemanticQueryResult extends ResultModel {

    public List<File> files() {
        SemanticQueryResponseBody body = (SemanticQueryResponseBody) innerBody;
        return body != null ? body.files() : null;
    }

    SemanticQueryResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        public SemanticQueryResult build() {
            return new SemanticQueryResult(this);
        }

        private Builder() {
            super();
        }

        private Builder(SemanticQueryResult result) {
            super(result);
        }
    }
}
