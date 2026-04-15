package com.aliyun.sdk.service.oss2.tables.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EncryptionConfiguration {

    @JsonProperty("sseAlgorithm")
    private String sseAlgorithm;

    @JsonProperty("kmsKeyArn")
    private String kmsKeyArn;

    public EncryptionConfiguration() {
    }

    private EncryptionConfiguration(Builder builder) {
        this.sseAlgorithm = builder.sseAlgorithm;
        this.kmsKeyArn = builder.kmsKeyArn;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public String sseAlgorithm() {
        return sseAlgorithm;
    }

    public String kmsKeyArn() {
        return kmsKeyArn;
    }

    public static class Builder {
        private String sseAlgorithm;
        private String kmsKeyArn;

        private Builder() {
        }

        private Builder(EncryptionConfiguration model) {
            this.sseAlgorithm = model.sseAlgorithm;
            this.kmsKeyArn = model.kmsKeyArn;
        }

        public Builder sseAlgorithm(String sseAlgorithm) {
            this.sseAlgorithm = sseAlgorithm;
            return this;
        }

        public Builder kmsKeyArn(String kmsKeyArn) {
            this.kmsKeyArn = kmsKeyArn;
            return this;
        }

        public EncryptionConfiguration build() {
            return new EncryptionConfiguration(this);
        }
    }
}
