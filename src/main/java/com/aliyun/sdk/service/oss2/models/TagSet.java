package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * The container for tags.
 */
@JacksonXmlRootElement(localName = "TagSet")
public final class TagSet {
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "Tag")
    private List<Tag> tags;

    public TagSet() {
    }

    private TagSet(Builder builder) {
        this.tags = builder.tags;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The tags.
     */
    public List<Tag> tags() {
        return this.tags;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private List<Tag> tags;

        private Builder() {
            super();
        }


        private Builder(TagSet from) {
            this.tags = from.tags;
        }

        /**
         * The tags.
         */
        public Builder tags(List<Tag> value) {
            requireNonNull(value);
            this.tags = value;
            return this;
        }

        public TagSet build() {
            return new TagSet(this);
        }
    }
}
