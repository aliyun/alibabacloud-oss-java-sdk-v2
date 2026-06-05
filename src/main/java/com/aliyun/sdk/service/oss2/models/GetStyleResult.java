package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the GetStyle operation.
 */
public final class GetStyleResult extends ResultModel { 

    /**
     * The container in which the queried image styles are stored.
     */
    public StyleInfo style() {
        return (StyleInfo)innerBody;
    } 
     

    GetStyleResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        public GetStyleResult build() {
            return new GetStyleResult(this);
        }

        private Builder() {
            super();
        }

        private Builder(GetStyleResult result) {
            super(result);
        }
    }
}
