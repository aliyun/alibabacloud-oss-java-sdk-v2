package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the CreateAccessPoint operation.
 */
public final class CreateAccessPointResult extends ResultModel { 

    /**
    * The container that stores the information about an access point.
    */
    public CreateAccessPointResultXml accessPointResult() {
        return (CreateAccessPointResultXml)innerBody;
    } 
     

    CreateAccessPointResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        public CreateAccessPointResult build() {
            return new CreateAccessPointResult(this);
        }

        private Builder() {
            super();
        }

        private Builder(CreateAccessPointResult result) {
            super(result);
        }
    }
}
