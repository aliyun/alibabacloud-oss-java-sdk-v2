package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the CreateSelectObjectMeta operation.
 */
public final class CreateSelectObjectMetaResult extends ResultModel {

    CreateSelectObjectMetaResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The container that stores the select meta status.
     */
    public SelectMetaStatus selectMetaStatus() {
        return (SelectMetaStatus) innerBody;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        private Builder() {
            super();
        }

        private Builder(CreateSelectObjectMetaResult result) {
            super(result);
        }

        public CreateSelectObjectMetaResult build() {
            return new CreateSelectObjectMetaResult(this);
        }
    }
}
