package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the GetAccessPointConfigForObjectProcess operation.
 */
public final class GetAccessPointConfigForObjectProcessResult extends ResultModel { 

    /**
    * The container that stores the configuration information about an Object FC Access Point.
    */
    public GetAccessPointConfigForObjectProcessResultXml accessPointConfigForObjectProcessResult() {
        return (GetAccessPointConfigForObjectProcessResultXml)innerBody;
    } 
     

    GetAccessPointConfigForObjectProcessResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        public GetAccessPointConfigForObjectProcessResult build() {
            return new GetAccessPointConfigForObjectProcessResult(this);
        }

        private Builder() {
            super();
        }

        private Builder(GetAccessPointConfigForObjectProcessResult result) {
            super(result);
        }
    }
}
