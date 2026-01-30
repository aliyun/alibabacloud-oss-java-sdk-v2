package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import java.util.List;
import static java.util.Objects.requireNonNull;

/**
 * Multimedia metadata search criteria. Only used for vector retrieval.
 */
@JacksonXmlRootElement(localName = "MediaTypes")
public final class MetaQueryMediaTypes {

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "MediaType")
    private List<String> mediaTypes;

    public MetaQueryMediaTypes() {}

    private MetaQueryMediaTypes(Builder builder) {
        this.mediaTypes = builder.mediaTypes;
    }

    /**
     * Gets the list of media types to select for search.
     */
    public List<String> mediaTypes() {
        return this.mediaTypes;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private List<String> mediaTypes;

        /**
         * Sets the list of media types to select for search.
         */
        public Builder mediaTypes(List<String> value) {
            requireNonNull(value);
            this.mediaTypes = value;
            return this;
        }

        private Builder() {
            super();
        }

        private Builder(MetaQueryMediaTypes from) {
            this.mediaTypes = from.mediaTypes;
        }

        public MetaQueryMediaTypes build() {
            return new MetaQueryMediaTypes(this);
        }
    }
}