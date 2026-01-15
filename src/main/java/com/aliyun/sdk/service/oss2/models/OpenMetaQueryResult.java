package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the OpenMetaQuery operation.
 */
public final class OpenMetaQueryResult extends ResultModel { 

    OpenMetaQueryResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        public OpenMetaQueryResult build() {
            return new OpenMetaQueryResult(this);
        }

        private Builder() {
            super();
        }

        private Builder(OpenMetaQueryResult result) {
            super(result);
        }
    }
}
