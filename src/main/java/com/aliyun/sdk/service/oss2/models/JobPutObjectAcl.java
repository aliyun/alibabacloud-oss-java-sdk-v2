package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import static java.util.Objects.requireNonNull;

/**
 * Put object ACL operation for batch job.
 */
@JacksonXmlRootElement(localName = "PutObjectAcl")
public final class JobPutObjectAcl {

    @JacksonXmlProperty(localName = "ObjectAcl")
    private String objectAcl;

    public JobPutObjectAcl() {
    }

    private JobPutObjectAcl(Builder builder) {
        this.objectAcl = builder.objectAcl;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The ACL for the object. Valid values: default, private, public-read, public-read-write.
     */
    public String objectAcl() {
        return this.objectAcl;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private String objectAcl;

        private Builder() {
            super();
        }

        private Builder(JobPutObjectAcl from) {
            this.objectAcl = from.objectAcl;
        }

        /**
         * The ACL for the object. Valid values: default, private, public-read, public-read-write.
         */
        public Builder objectAcl(String value) {
            requireNonNull(value);
            this.objectAcl = value;
            return this;
        }

        public JobPutObjectAcl build() {
            return new JobPutObjectAcl(this);
        }
    }
}
