package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import java.util.List;

/**
 * Configuration for filtering data pipeline sources.
 */
@JacksonXmlRootElement(localName = "FilterConfiguration")
public final class DataPipelineSourceFilterConfiguration {
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "PrefixSet")
    private List<String> prefixSet;

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "ObjectMediaTypes")
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
