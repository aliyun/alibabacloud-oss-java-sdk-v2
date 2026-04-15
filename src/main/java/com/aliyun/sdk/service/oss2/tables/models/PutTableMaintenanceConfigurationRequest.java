package com.aliyun.sdk.service.oss2.tables.models;

import static java.util.Objects.requireNonNull;

public final class PutTableMaintenanceConfigurationRequest extends TableRequestModel {
    private final String tableBucketARN;
    private final String namespace;
    private final String name;
    private final String type;

    private PutTableMaintenanceConfigurationRequest(Builder builder) {
        super(builder);
        this.tableBucketARN = builder.tableBucketARN;
        this.namespace = builder.namespace;
        this.name = builder.name;
        this.type = builder.type;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String tableBucketARN() {
        return tableBucketARN;
    }

    public String namespace() {
        return namespace;
    }

    public String name() {
        return name;
    }

    public String type() {
        return type;
    }

    public TableMaintenanceConfigurationValue value() {
        return (TableMaintenanceConfigurationValue) this.bodyFields.get("value");
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends TableRequestModel.Builder<Builder> {
        private String tableBucketARN;
        private String namespace;
        private String name;
        private String type;

        private Builder() {
            super();
        }

        private Builder(PutTableMaintenanceConfigurationRequest from) {
            super(from);
            this.tableBucketARN = from.tableBucketARN;
            this.namespace = from.namespace;
            this.name = from.name;
            this.type = from.type;
        }

        public Builder tableBucketARN(String v) {
            requireNonNull(v);
            this.tableBucketARN = v;
            return this;
        }

        public Builder namespace(String v) {
            requireNonNull(v);
            this.namespace = v;
            return this;
        }

        public Builder name(String v) {
            requireNonNull(v);
            this.name = v;
            return this;
        }

        public Builder type(String v) {
            requireNonNull(v);
            this.type = v;
            return this;
        }

        public Builder value(TableMaintenanceConfigurationValue v) {
            this.bodyFields.put("value", v);
            return this;
        }

        public PutTableMaintenanceConfigurationRequest build() {
            return new PutTableMaintenanceConfigurationRequest(this);
        }
    }
}
