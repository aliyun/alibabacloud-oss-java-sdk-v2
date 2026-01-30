package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.utils.ConvertUtils;

/**
 * The result for the CloseMetaQuery operation.
 */
public final class CloseMetaQueryResult extends ResultModel { 

    CloseMetaQueryResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        public CloseMetaQueryResult build() {
            return new CloseMetaQueryResult(this);
        }

        private Builder() {
            super();
        }

        private Builder(CloseMetaQueryResult result) {
            super(result);
        }
    }
}
