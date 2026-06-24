package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import static java.util.Objects.requireNonNull;

/**
 * OSS user-defined metadata entry inside {@code <OSSUserMeta>} of a {@link File} response.
 *
 * <p>XML form: {@code <UserMeta><Key>k</Key><Value>v</Value></UserMeta>}.
 */
@JacksonXmlRootElement(localName = "UserMeta")
public final class UserMeta {

    @JacksonXmlProperty(localName = "Key")
    private String key;

    @JacksonXmlProperty(localName = "Value")
    private String value;

    public UserMeta() {
    }

    private UserMeta(Builder builder) {
        this.key = builder.key;
        this.value = builder.value;
    }

    /**
     * User-defined metadata key.
     */
    public String key() {
        return this.key;
    }

    /**
     * User-defined metadata value.
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

        private Builder(UserMeta from) {
            this.key = from.key;
            this.value = from.value;
        }

        /**
         * User-defined metadata key.
         */
        public Builder key(String value) {
            requireNonNull(value);
            this.key = value;
            return this;
        }

        /**
         * User-defined metadata value.
         */
        public Builder value(String value) {
            requireNonNull(value);
            this.value = value;
            return this;
        }

        public UserMeta build() {
            return new UserMeta(this);
        }
    }
}
