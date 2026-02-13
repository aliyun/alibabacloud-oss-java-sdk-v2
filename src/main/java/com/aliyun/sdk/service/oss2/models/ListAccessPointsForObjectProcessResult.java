package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the ListAccessPointsForObjectProcess operation.
 */
public final class ListAccessPointsForObjectProcessResult extends ResultModel { 

    /**
    * The container that stores the list of Object FC Access Points.
    */
    public ListAccessPointsForObjectProcessResultXml accessPointsForObjectProcessResult() {
        return (ListAccessPointsForObjectProcessResultXml)innerBody;
    } 
     

    ListAccessPointsForObjectProcessResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        public ListAccessPointsForObjectProcessResult build() {
            return new ListAccessPointsForObjectProcessResult(this);
        }

        private Builder() {
            super();
        }

        private Builder(ListAccessPointsForObjectProcessResult result) {
            super(result);
        }
    }
}
