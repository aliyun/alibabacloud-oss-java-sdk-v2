package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the DoMetaQuery operation.
 */
public final class DoMetaQueryResult extends ResultModel { 

    /**
     * The container for the query result.
     */
    public MetaQueryResp metaQuery() {
        return (MetaQueryResp)innerBody;
    } 

    /**
     * Convenience method to access the file list from the query result.
     */
    public MetaQueryFiles files() {
        MetaQueryResp resp = metaQuery();
        return resp != null ? resp.files() : null;
    }

     

    DoMetaQueryResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        public DoMetaQueryResult build() {
            return new DoMetaQueryResult(this);
        }

        private Builder() {
            super();
        }

        private Builder(DoMetaQueryResult result) {
            super(result);
        }
    }
}
