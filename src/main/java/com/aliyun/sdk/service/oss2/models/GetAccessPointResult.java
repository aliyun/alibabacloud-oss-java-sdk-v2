package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the GetAccessPoint operation.
 */
public final class GetAccessPointResult extends ResultModel { 

    /**
    * The container that stores the information about an access point.
    */
    public GetAccessPointResultXml accessPointResult() {
        return (GetAccessPointResultXml)innerBody;
    } 
     

    GetAccessPointResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        public GetAccessPointResult build() {
            return new GetAccessPointResult(this);
        }

        private Builder() {
            super();
        }

        private Builder(GetAccessPointResult result) {
            super(result);
        }
    }
}
