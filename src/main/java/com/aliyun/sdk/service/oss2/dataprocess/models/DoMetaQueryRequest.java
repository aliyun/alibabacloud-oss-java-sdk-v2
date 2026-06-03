package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.aliyun.sdk.service.oss2.models.RequestModel;

import static java.util.Objects.requireNonNull;

public final class DoMetaQueryRequest extends RequestModel {
    private final String bucket;
    private final MetaQueryDoBody metaQueryBody;

    private DoMetaQueryRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
        this.metaQueryBody = builder.metaQueryBody;
    }

    public static Builder newBuilder() { return new Builder(); }

    public String bucket() { return bucket; }
    public String mode() { return parameters.get("mode"); }
    public MetaQueryDoBody metaQueryBody() { return metaQueryBody; }

    public Builder toBuilder() { return new Builder(this); }

    public static class Builder extends RequestModel.Builder<Builder> {
        private String bucket;
        private MetaQueryDoBody metaQueryBody;

        private Builder() { super(); }

        private Builder(DoMetaQueryRequest request) {
            super(request);
            this.bucket = request.bucket;
            this.metaQueryBody = request.metaQueryBody;
        }

        public Builder bucket(String value) {
            requireNonNull(value);
            this.bucket = value;
            return this;
        }

        public Builder mode(String value) {
            requireNonNull(value);
            this.parameters.put("mode", value);
            return this;
        }

        public Builder metaQueryBody(MetaQueryDoBody value) {
            this.metaQueryBody = value;
            return this;
        }

        public DoMetaQueryRequest build() { return new DoMetaQueryRequest(this); }
    }
}
