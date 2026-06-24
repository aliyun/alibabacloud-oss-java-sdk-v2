package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.aliyun.sdk.service.oss2.models.RequestModel;

import java.util.List;

import static java.util.Objects.requireNonNull;

public final class UpdateSmartClusterRequest extends RequestModel {
    private final String bucket;

    private UpdateSmartClusterRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
    }

    public static Builder newBuilder() { return new Builder(); }

    public String bucket() { return bucket; }
    public String datasetName() { return parameters.get("datasetName"); }
    public String objectId() { return parameters.get("objectId"); }
    public String name() { return parameters.get("name"); }
    public String description() { return parameters.get("description"); }
    public String rules() { return parameters.get("rules"); }
    public String rule() { return parameters.get("rule"); }
    public String notification() { return parameters.get("notification"); }

    public Builder toBuilder() { return new Builder(this); }

    public static class Builder extends RequestModel.Builder<Builder> {
        private String bucket;

        private Builder() { super(); }

        private Builder(UpdateSmartClusterRequest request) {
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

        public Builder objectId(String value) {
            requireNonNull(value);
            this.parameters.put("objectId", value);
            return this;
        }

        public Builder name(String value) {
            this.parameters.put("name", value);
            return this;
        }

        public Builder description(String value) {
            this.parameters.put("description", value);
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

        public Builder rule(SmartClusterRule value) {
            this.parameters.put("rule", DataProcessParamHelper.toSmartClusterRule(value));
            return this;
        }

        public Builder rule(String value) {
            this.parameters.put("rule", value);
            return this;
        }

        public Builder notification(String value) {
            this.parameters.put("notification", value);
            return this;
        }

        public UpdateSmartClusterRequest build() { return new UpdateSmartClusterRequest(this); }
    }
}
