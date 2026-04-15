package com.aliyun.sdk.service.oss2.tables.models;

import static java.util.Objects.requireNonNull;

public final class GetTableMaintenanceJobStatusRequest extends TableRequestModel {
    private final String tableBucketARN;
    private final String namespace;
    private final String name;
    private final String tableARN;

    private GetTableMaintenanceJobStatusRequest(Builder builder) {
        super(builder);
        this.tableBucketARN = builder.tableBucketARN;
        this.namespace = builder.namespace;
        this.name = builder.name;
        this.tableARN = builder.tableARN;
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

    public String tableARN() {
        return tableARN;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends TableRequestModel.Builder<Builder> {
        private String tableBucketARN;
        private String namespace;
        private String name;
        private String tableARN;

        private Builder() {
            super();
        }

        private Builder(GetTableMaintenanceJobStatusRequest from) {
            super(from);
            this.tableBucketARN = from.tableBucketARN;
            this.namespace = from.namespace;
            this.name = from.name;
            this.tableARN = from.tableARN;
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

        public Builder tableARN(String v) {
            this.tableARN = v;
            return this;
        }

        public GetTableMaintenanceJobStatusRequest build() {
            return new GetTableMaintenanceJobStatusRequest(this);
        }
    }
}
