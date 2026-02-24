package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the CreateAccessPointForObjectProcess operation.
 */
public final class CreateAccessPointForObjectProcessResult extends ResultModel { 

    /**
    * The container that stores the information about an Object FC Access Point.
    */
    public CreateAccessPointForObjectProcessResultXml accessPointForObjectProcessResult() {
        return (CreateAccessPointForObjectProcessResultXml)innerBody;
    } 
     

    CreateAccessPointForObjectProcessResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        public CreateAccessPointForObjectProcessResult build() {
            return new CreateAccessPointForObjectProcessResult(this);
        }

        private Builder() {
            super();
        }

        private Builder(CreateAccessPointForObjectProcessResult result) {
            super(result);
        }
    }
}
