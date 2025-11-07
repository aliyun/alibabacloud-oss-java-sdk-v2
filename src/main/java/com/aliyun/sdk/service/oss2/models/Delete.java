package com.aliyun.sdk.service.oss2.models;

import java.util.List;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * Container containing the list of objects to be deleted
 */
@JacksonXmlRootElement(localName = "Delete")
public final class Delete {
    @JacksonXmlProperty(localName = "Quiet")
    private final Boolean quiet;
    
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "Object")
    private final List<ObjectIdentifier> objects;

    private Delete(Builder builder) {
        this.quiet = builder.quiet;
        this.objects = builder.objects;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Whether to enable quiet mode
     */
    public Boolean quiet() {
        return quiet;
    }

    /**
     * The list of objects to be deleted
     */
    public List<ObjectIdentifier> objects() {
        return objects;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private Boolean quiet;
        private List<ObjectIdentifier> objects;

        private Builder() {
            super();
        }

        private Builder(Delete delete) {
            this.quiet = delete.quiet;
            this.objects = delete.objects;
        }

        /**
         * Whether to enable quiet mode
         */
        public Builder quiet(Boolean value) {
            this.quiet = value;
            return this;
        }

        /**
         * The list of objects to be deleted
         */
        public Builder objects(List<ObjectIdentifier> value) {
            this.objects = value;
            return this;
        }

        public Delete build() {
            return new Delete(this);
        }
    }
}