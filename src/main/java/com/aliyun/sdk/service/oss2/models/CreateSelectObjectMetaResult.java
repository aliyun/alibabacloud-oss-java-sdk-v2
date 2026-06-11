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
     * The metadata from the CreateSelectObjectMeta binary frame response.
     */
    public SelectObjectMeta selectObjectMeta() {
        return (SelectObjectMeta) innerBody;
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
