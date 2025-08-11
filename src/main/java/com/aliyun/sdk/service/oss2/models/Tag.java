package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import static java.util.Objects.requireNonNull;

/**
 * The container used to store the tag that you want to configure.
 */
@JacksonXmlRootElement(localName = "Tag")
public final class Tag {
    @JacksonXmlProperty(localName = "Key")
    private String key;

    @JacksonXmlProperty(localName = "Value")
    private String value;

    public Tag() {
    }

    private Tag(Builder builder) {
        this.key = builder.key;
        this.value = builder.value;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The key of a tag. *   A tag key can be up to 64 bytes in length.*   A tag key cannot start with `http://`, `https://`, or `Aliyun`.*   A tag key must be UTF-8 encoded.*   A tag key cannot be left empty.
     */
    public String key() {
        return this.key;
    }

    /**
     * The value of the tag that you want to add or modify. *   A tag value can be up to 128 bytes in length.*   A tag value must be UTF-8 encoded.*   The tag value can be left empty.
     */
    public String value() {
        return this.value;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private String key;
        private String value;

        private Builder() {
            super();
        }

        private Builder(Tag from) {
            this.key = from.key;
            this.value = from.value;
        }

        /**
         * The key of a tag. *   A tag key can be up to 64 bytes in length.*   A tag key cannot start with `http://`, `https://`, or `Aliyun`.*   A tag key must be UTF-8 encoded.*   A tag key cannot be left empty.
         */
        public Builder key(String value) {
            requireNonNull(value);
            this.key = value;
            return this;
        }

        /**
         * The value of the tag that you want to add or modify. *   A tag value can be up to 128 bytes in length.*   A tag value must be UTF-8 encoded.*   The tag value can be left empty.
         */
        public Builder value(String value) {
            requireNonNull(value);
            this.value = value;
            return this;
        }

        public Tag build() {
            return new Tag(this);
        }
    }
}
