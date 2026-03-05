package com.aliyun.sdk.service.oss2.imm.models;

import com.aliyun.sdk.service.oss2.models.ResultModel;

import java.util.List;

/**
 * The result for the SemanticQuery operation.
 */
public final class SemanticQueryResult extends ResultModel {
    private final List<File> files;

    SemanticQueryResult(Builder builder) {
        super(builder);
        this.files = builder.files;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public List<File> files() {
        return files;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {
        private List<File> files;

        private Builder() {
            super();
        }

        private Builder(SemanticQueryResult result) {
            super(result);
            this.files = result.files;
        }

        public Builder files(List<File> value) {
            this.files = value;
            return this;
        }

        public SemanticQueryResult build() {
            return new SemanticQueryResult(this);
        }
    }
}
