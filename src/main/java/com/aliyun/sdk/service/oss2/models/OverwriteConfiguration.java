package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * The structure for the overwrite protection configuration.
 */
@JacksonXmlRootElement(localName = "OverwriteConfiguration")
public final class OverwriteConfiguration {
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "Rule")
    private List<OverwriteRule> rules;

    public OverwriteConfiguration() {
    }

    private OverwriteConfiguration(Builder builder) {
        this.rules = builder.rules;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * List of overwrite protection rules. A bucket can have a maximum of 100 rules.
     */
    public List<OverwriteRule> rules() {
        return this.rules;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private List<OverwriteRule> rules;

        private Builder() {
            super();
        }


        private Builder(OverwriteConfiguration from) {
            this.rules = from.rules;
        }

        /**
         * List of overwrite protection rules. A bucket can have a maximum of 100 rules.
         */
        public Builder rules(List<OverwriteRule> value) {
            requireNonNull(value);
            this.rules = value;
            return this;
        }

        public OverwriteConfiguration build() {
            return new OverwriteConfiguration(this);
        }
    }
}
