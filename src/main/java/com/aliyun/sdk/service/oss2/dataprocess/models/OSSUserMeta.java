package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * Container for OSS user-defined metadata items returned in a {@link File}.
 *
 * <p>XML form: {@code <OSSUserMeta><UserMeta><Key>k</Key><Value>v</Value></UserMeta>...</OSSUserMeta>}.
 *
 * <p>The outer {@code <OSSUserMeta>} element name is decided by the parent field's
 * {@code @JacksonXmlProperty} annotation in {@link File}. Inside this container, repeated
 * {@code <UserMeta>} elements are emitted directly (no extra wrapper layer), which is why
 * {@link JacksonXmlElementWrapper#useWrapping()} is set to {@code false}.
 */
@JacksonXmlRootElement(localName = "OSSUserMeta")
public final class OSSUserMeta {

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "UserMeta")
    private List<UserMeta> userMeta;

    public OSSUserMeta() {
    }

    private OSSUserMeta(Builder builder) {
        this.userMeta = builder.userMeta;
    }

    /**
     * The user metadata items.
     */
    public List<UserMeta> userMeta() {
        return this.userMeta;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private List<UserMeta> userMeta;

        private Builder() {
            super();
        }

        private Builder(OSSUserMeta from) {
            this.userMeta = from.userMeta;
        }

        /**
         * The user metadata items.
         */
        public Builder userMeta(List<UserMeta> value) {
            requireNonNull(value);
            this.userMeta = value;
            return this;
        }

        public OSSUserMeta build() {
            return new OSSUserMeta(this);
        }
    }
}
