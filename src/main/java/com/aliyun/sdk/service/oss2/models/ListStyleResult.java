package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the ListStyle operation.
 */
public final class ListStyleResult extends ResultModel { 

    /**
     * The container that was used to query the information about image styles.
     */
    public StyleList styleList() {
        return (StyleList)innerBody;
    } 
     

    ListStyleResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        public ListStyleResult build() {
            return new ListStyleResult(this);
        }

        private Builder() {
            super();
        }

        private Builder(ListStyleResult result) {
            super(result);
        }
    }
}
