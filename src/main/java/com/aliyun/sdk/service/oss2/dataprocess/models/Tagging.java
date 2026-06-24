package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import static java.util.Objects.requireNonNull;

/**
 * OSS object tag entry inside {@code <OSSTagging>} of a {@link File} response.
 *
 * <p>XML form: {@code <Tagging><Key>k</Key><Value>v</Value></Tagging>}.
 */
@JacksonXmlRootElement(localName = "Tagging")
public final class Tagging {

    @JacksonXmlProperty(localName = "Key")
    private String key;

    @JacksonXmlProperty(localName = "Value")
    private String value;

    public Tagging() {
    }

    private Tagging(Builder builder) {
        this.key = builder.key;
        this.value = builder.value;
    }

    /**
     * Object tag key.
     */
    public String key() {
        return this.key;
    }

    /**
     * Object tag value.
     */
    public String value() {
        return this.value;
    }

    public static Builder newBuilder() {
        return new Builder();
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

        private Builder(Tagging from) {
            this.key = from.key;
            this.value = from.value;
        }

        /**
         * Object tag key.
         */
        public Builder key(String value) {
            requireNonNull(value);
            this.key = value;
            return this;
        }

        /**
         * Object tag value.
         */
        public Builder value(String value) {
            requireNonNull(value);
            this.value = value;
            return this;
        }

        public Tagging build() {
            return new Tagging(this);
        }
    }
}
