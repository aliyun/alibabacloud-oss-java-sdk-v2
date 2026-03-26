package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import static java.util.Objects.requireNonNull;

/**
 * The container that stores the object-level retention policy configuration.
 */
@JacksonXmlRootElement(localName = "ObjectWormConfiguration")
public final class ObjectWormConfiguration {
    @JacksonXmlProperty(localName = "ObjectWormEnabled")
    private String objectWormEnabled;

    @JacksonXmlProperty(localName = "Rule")
    private ObjectWormConfigurationRule rule;

    public ObjectWormConfiguration() {}

    private ObjectWormConfiguration(Builder builder) {
        this.objectWormEnabled = builder.objectWormEnabled;
        this.rule = builder.rule;
    }

    /**
     * Whether to enable object-level retention policy.
     */
    public String objectWormEnabled() {
        return this.objectWormEnabled;
    }

    /**
     * The container that stores the object-level retention policy.
     */
    public ObjectWormConfigurationRule rule() {
        return this.rule;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private String objectWormEnabled;
        private ObjectWormConfigurationRule rule;

        /**
         * Whether to enable object-level retention policy.
         */
        public Builder objectWormEnabled(String value) {
            this.objectWormEnabled = value;
            return this;
        }

        /**
         * The container that stores the object-level retention policy.
         */
        public Builder rule(ObjectWormConfigurationRule value) {
            this.rule = value;
            return this;
        }

        private Builder() {
            super();
        }

        private Builder(ObjectWormConfiguration from) {
            this.objectWormEnabled = from.objectWormEnabled;
            this.rule = from.rule;
        }

        public ObjectWormConfiguration build() {
            return new ObjectWormConfiguration(this);
        }
    }
}
