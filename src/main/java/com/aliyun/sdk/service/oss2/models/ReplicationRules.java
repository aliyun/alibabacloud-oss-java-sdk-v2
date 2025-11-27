package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import java.util.Objects;

/**
 * The container that stores the replication rules for the bucket.
 */
@JacksonXmlRootElement(localName = "ReplicationRules")
public final class ReplicationRules {
    @JacksonXmlProperty(localName = "ID")
    private String id;

    public ReplicationRules() {}

    private ReplicationRules(Builder builder) {
        this.id = builder.id;
    }

    /**
     * The ID of the replication rule.
     */
    public String id() {
        return this.id;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private String id;

        /**
         * The ID of the replication rule.
         */
        public Builder id(String value) {
            this.id = Objects.requireNonNull(value);
            return this;
        }

        public ReplicationRules build() {
            return new ReplicationRules(this);
        }

        private Builder() {}

        private Builder(ReplicationRules from) {
            this.id = from.id;
        }
    }
}
