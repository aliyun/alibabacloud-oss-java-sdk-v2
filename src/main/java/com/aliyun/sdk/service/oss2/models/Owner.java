package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import static java.util.Objects.requireNonNull;

/**
 * The container for the information about the bucket owner.
 */
@JacksonXmlRootElement(localName = "Owner")
public final class Owner {
    @JacksonXmlProperty(localName = "ID")
    private String id;

    @JacksonXmlProperty(localName = "DisplayName")
    private String displayName;

    public Owner() {
    }

    private Owner(Builder builder) {
        this.id = builder.id;
        this.displayName = builder.displayName;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The user ID of the bucket owner.
     */
    public String id() {
        return this.id;
    }

    /**
     * The name of the bucket owner, which is the same as the user ID.
     */
    public String displayName() {
        return this.displayName;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private String id;
        private String displayName;

        private Builder() {
            super();
        }

        private Builder(Owner from) {
            this.id = from.id;
            this.displayName = from.displayName;
        }

        /**
         * The user ID of the bucket owner.
         */
        public Builder id(String value) {
            requireNonNull(value);
            this.id = value;
            return this;
        }

        /**
         * The name of the bucket owner, which is the same as the user ID.
         */
        public Builder displayName(String value) {
            requireNonNull(value);
            this.displayName = value;
            return this;
        }

        public Owner build() {
            return new Owner(this);
        }
    }
}
