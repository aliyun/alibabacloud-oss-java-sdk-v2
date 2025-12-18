package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * A collection of authorized entities. The usage is similar to the `Principal` element in a bucket policy. You can specify an Alibaba Cloud account, a RAM user, or a RAM role. If this element is empty or not configured, overwrites are prohibited for all objects that match the prefix and suffix conditions.
 */
@JacksonXmlRootElement(localName = "Principals")
public final class OverwritePrincipals {
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "Principal")
    private List<String> principals;

    public OverwritePrincipals() {
    }

    private OverwritePrincipals(Builder builder) {
        this.principals = builder.principals;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * A collection of authorized entities. The usage is similar to the `Principal` element in a bucket policy. You can specify an Alibaba Cloud account, a RAM user, or a RAM role. If this element is empty or not configured, overwrites are prohibited for all objects that match the prefix and suffix conditions.
     */
    public List<String> principals() {
        return this.principals;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private List<String> principals;

        private Builder() {
            super();
        }


        private Builder(OverwritePrincipals from) {
            this.principals = from.principals;
        }

        /**
         * A collection of authorized entities. The usage is similar to the `Principal` element in a bucket policy. You can specify an Alibaba Cloud account, a RAM user, or a RAM role. If this element is empty or not configured, overwrites are prohibited for all objects that match the prefix and suffix conditions.
         */
        public Builder principals(List<String> value) {
            requireNonNull(value);
            this.principals = value;
            return this;
        }

        public OverwritePrincipals build() {
            return new OverwritePrincipals(this);
        }
    }
}
