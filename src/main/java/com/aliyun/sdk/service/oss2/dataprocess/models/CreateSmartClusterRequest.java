package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.aliyun.sdk.service.oss2.models.RequestModel;

import java.util.List;

import static java.util.Objects.requireNonNull;

public final class CreateSmartClusterRequest extends RequestModel {
    private final String bucket;

    private CreateSmartClusterRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
    }

    public static Builder newBuilder() { return new Builder(); }

    public String bucket() { return bucket; }
    public String datasetName() { return parameters.get("datasetName"); }
    public String name() { return parameters.get("name"); }
    public String description() { return parameters.get("description"); }
    public String clusterType() { return parameters.get("clusterType"); }
    public String rules() { return parameters.get("rules"); }

    public String notification() { return parameters.get("notification"); }

    public Builder toBuilder() { return new Builder(this); }

    public static class Builder extends RequestModel.Builder<Builder> {
        private String bucket;

        private Builder() { super(); }

        private Builder(CreateSmartClusterRequest request) {
            super(request);
            this.bucket = request.bucket;
        }

        public Builder bucket(String value) {
            requireNonNull(value);
            this.bucket = value;
            return this;
        }

        public Builder datasetName(String value) {
            requireNonNull(value);
            this.parameters.put("datasetName", value);
            return this;
        }

        public Builder name(String value) {
            requireNonNull(value);
            this.parameters.put("name", value);
            return this;
        }

        public Builder description(String value) {
            this.parameters.put("description", value);
            return this;
        }

        public Builder clusterType(String value) {
            requireNonNull(value);
            this.parameters.put("clusterType", value);
            return this;
        }

        public Builder rules(List<SmartClusterRule> value) {
            this.parameters.put("rules", DataProcessParamHelper.toSmartClusterRules(value));
            return this;
        }

        public Builder rules(String value) {
            this.parameters.put("rules", value);
            return this;
        }


        public Builder notification(String value) {
            this.parameters.put("notification", value);
            return this;
        }

        public CreateSmartClusterRequest build() { return new CreateSmartClusterRequest(this); }
    }
}
