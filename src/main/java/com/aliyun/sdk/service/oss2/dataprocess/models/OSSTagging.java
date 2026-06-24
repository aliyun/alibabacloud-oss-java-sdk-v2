package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * Container for OSS object tagging items returned in a {@link File}.
 *
 * <p>XML form: {@code <OSSTagging><Tagging><Key>k</Key><Value>v</Value></Tagging>...</OSSTagging>}.
 *
 * <p>The outer {@code <OSSTagging>} element name is decided by the parent field's
 * {@code @JacksonXmlProperty} annotation in {@link File}. Inside this container, repeated
 * {@code <Tagging>} elements are emitted directly (no extra wrapper layer), which is why
 * {@link JacksonXmlElementWrapper#useWrapping()} is set to {@code false}.
 */
@JacksonXmlRootElement(localName = "OSSTagging")
public final class OSSTagging {

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "Tagging")
    private List<Tagging> tagging;

    public OSSTagging() {
    }

    private OSSTagging(Builder builder) {
        this.tagging = builder.tagging;
    }

    /**
     * The tags.
     */
    public List<Tagging> tagging() {
        return this.tagging;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private List<Tagging> tagging;

        private Builder() {
            super();
        }

        private Builder(OSSTagging from) {
            this.tagging = from.tagging;
        }

        /**
         * The tags.
         */
        public Builder tagging(List<Tagging> value) {
            requireNonNull(value);
            this.tagging = value;
            return this;
        }

        public OSSTagging build() {
            return new OSSTagging(this);
        }
    }
}
