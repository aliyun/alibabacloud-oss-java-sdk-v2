package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

/**
 * Configuration for filtering data pipeline sources.
 */
@JacksonXmlRootElement(localName = "FilterConfiguration")
public final class DataPipelineSourceFilterConfiguration {
    @JacksonXmlElementWrapper(localName = "PrefixSet")
    @JacksonXmlProperty(localName = "Prefix")
    private List<String> prefixSet;

    @JacksonXmlElementWrapper(localName = "ObjectMediaTypes")
    @JacksonXmlProperty(localName = "ObjectMediaType")
    private List<String> objectMediaTypes;

    public DataPipelineSourceFilterConfiguration() {
    }

    private DataPipelineSourceFilterConfiguration(Builder builder) {
        this.prefixSet = builder.prefixSet;
        this.objectMediaTypes = builder.objectMediaTypes;
    }

    public List<String> prefixSet() {
        return prefixSet;
    }

    public List<String> objectMediaTypes() {
        return objectMediaTypes;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private List<String> prefixSet;
        private List<String> objectMediaTypes;

        public Builder prefixSet(List<String> value) {
            this.prefixSet = value;
            return this;
        }

        public Builder objectMediaTypes(List<String> value) {
            this.objectMediaTypes = value;
            return this;
        }

        private Builder() {
            super();
        }

        private Builder(DataPipelineSourceFilterConfiguration from) {
            this.prefixSet = from.prefixSet;
            this.objectMediaTypes = from.objectMediaTypes;
        }

        public DataPipelineSourceFilterConfiguration build() {
            return new DataPipelineSourceFilterConfiguration(this);
        }
    }
}
