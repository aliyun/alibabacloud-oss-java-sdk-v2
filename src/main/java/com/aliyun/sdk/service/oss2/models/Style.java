package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import static java.util.Objects.requireNonNull;

/**
 * The container that stores the content of the style.
 */
@JacksonXmlRootElement(localName = "Style")
public final class Style {
    @JacksonXmlProperty(localName = "Content")
    private String content;

    public Style() {}

    private Style(Builder builder) {
        this.content = builder.content;
    }

    /**
     * The content of the style.
     */
    public String content() {
        return this.content;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private String content;

        /**
         * The content of the style.
         */
        public Builder content(String value) {
            requireNonNull(value);
            this.content = value;
            return this;
        }

        private Builder() {
        }

        private Builder(Style from) {
            this.content = from.content;
        }

        public Style build() {
            return new Style(this);
        }
    }
}