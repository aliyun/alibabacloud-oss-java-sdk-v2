package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the GetAccessPointForObjectProcess operation.
 */
public final class GetAccessPointForObjectProcessResult extends ResultModel { 

    /**
    * The container that stores the information about an Object FC Access Point.
    */
    public GetAccessPointForObjectProcessResultXml accessPointForObjectProcessResult() {
        return (GetAccessPointForObjectProcessResultXml)innerBody;
    } 
     

    GetAccessPointForObjectProcessResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        public GetAccessPointForObjectProcessResult build() {
            return new GetAccessPointForObjectProcessResult(this);
        }

        private Builder() {
            super();
        }

        private Builder(GetAccessPointForObjectProcessResult result) {
            super(result);
        }
    }
}
