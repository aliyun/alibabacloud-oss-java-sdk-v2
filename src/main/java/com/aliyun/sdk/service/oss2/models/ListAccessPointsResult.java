package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the ListAccessPoints operation.
 */
public final class ListAccessPointsResult extends ResultModel { 

    /**
    * The container that stores the information about access points.
    */
    public ListAccessPointsResultXml accessPointsResult() {
        return (ListAccessPointsResultXml)innerBody;
    } 
     

    ListAccessPointsResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        public ListAccessPointsResult build() {
            return new ListAccessPointsResult(this);
        }

        private Builder() {
            super();
        }

        private Builder(ListAccessPointsResult result) {
            super(result);
        }
    }
}
