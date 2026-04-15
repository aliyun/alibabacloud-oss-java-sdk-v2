package com.aliyun.sdk.service.oss2.tables.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.util.List;

public class NamespaceSummary {

    @JsonProperty("namespace")
    private List<String> namespace;

    @JsonProperty("createdAt")
    private Instant createdAt;

    @JsonProperty("createdBy")
    private String createdBy;

    @JsonProperty("ownerAccountId")
    private String ownerAccountId;

    @JsonProperty("namespaceId")
    private String namespaceId;

    public NamespaceSummary() {
    }

    private NamespaceSummary(Builder builder) {
        this.namespace = builder.namespace;
        this.createdAt = builder.createdAt;
        this.createdBy = builder.createdBy;
        this.ownerAccountId = builder.ownerAccountId;
        this.namespaceId = builder.namespaceId;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public List<String> namespace() {
        return namespace;
    }

    public Instant createdAt() {
        return createdAt;
    }

    public String createdBy() {
        return createdBy;
    }

    public String ownerAccountId() {
        return ownerAccountId;
    }

    public String namespaceId() {
        return namespaceId;
    }

    public static class Builder {
        private List<String> namespace;
        private Instant createdAt;
        private String createdBy;
        private String ownerAccountId;
        private String namespaceId;

        private Builder() {
        }

        private Builder(NamespaceSummary model) {
            this.namespace = model.namespace;
            this.createdAt = model.createdAt;
            this.createdBy = model.createdBy;
            this.ownerAccountId = model.ownerAccountId;
            this.namespaceId = model.namespaceId;
        }

        public Builder namespace(List<String> namespace) {
            this.namespace = namespace;
            return this;
        }

        public Builder createdAt(Instant createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder createdBy(String createdBy) {
            this.createdBy = createdBy;
            return this;
        }

        public Builder ownerAccountId(String ownerAccountId) {
            this.ownerAccountId = ownerAccountId;
            return this;
        }

        public Builder namespaceId(String namespaceId) {
            this.namespaceId = namespaceId;
            return this;
        }

        public NamespaceSummary build() {
            return new NamespaceSummary(this);
        }
    }
}
