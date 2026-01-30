package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the GetMetaQueryStatus operation.
 */
public final class GetMetaQueryStatusResult extends ResultModel { 

    /**
     * The container that stores the metadata information.
     */
    public MetaQueryStatus metaQueryStatus() {
        return (MetaQueryStatus)innerBody;
    } 
     

    GetMetaQueryStatusResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        public GetMetaQueryStatusResult build() {
            return new GetMetaQueryStatusResult(this);
        }

        private Builder() {
            super();
        }

        private Builder(GetMetaQueryStatusResult result) {
            super(result);
        }
    }
}
