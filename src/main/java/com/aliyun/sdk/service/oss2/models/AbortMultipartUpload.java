package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.time.Instant;

import static java.util.Objects.requireNonNull;

/**
 * The delete operation that you want OSS to perform on the parts that are uploaded in incomplete multipart upload tasks when the parts expire.
 */
@JacksonXmlRootElement(localName = "AbortMultipartUpload")
public final class AbortMultipartUpload {
    @JacksonXmlProperty(localName = "Days")
    private Integer days;

    @JacksonXmlProperty(localName = "CreatedBeforeDate")
    private Instant createdBeforeDate;

    public AbortMultipartUpload() {
    }

    private AbortMultipartUpload(Builder builder) {
        this.days = builder.days;
        this.createdBeforeDate = builder.createdBeforeDate;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The number of days from when the objects were last modified to when the lifecycle rule takes effect.
     */
    public Integer days() {
        return this.days;
    }

    /**
     * The date based on which the lifecycle rule takes effect. OSS performs the specified operation on data whose last modified date is earlier than this date. Specify the time in the ISO 8601 standard. The time must be at 00:00:00 in UTC.
     */
    public Instant createdBeforeDate() {
        return this.createdBeforeDate;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private Integer days;
        private Instant createdBeforeDate;

        private Builder() {
            super();
        }

        private Builder(AbortMultipartUpload from) {
            this.days = from.days;
            this.createdBeforeDate = from.createdBeforeDate;
        }

        /**
         * The number of days from when the objects were last modified to when the lifecycle rule takes effect.
         */
        public Builder days(Integer value) {
            requireNonNull(value);
            this.days = value;
            return this;
        }

        /**
         * The date based on which the lifecycle rule takes effect. OSS performs the specified operation on data whose last modified date is earlier than this date. Specify the time in the ISO 8601 standard. The time must be at 00:00:00 in UTC.
         */
        public Builder createdBeforeDate(Instant value) {
            requireNonNull(value);
            this.createdBeforeDate = value;
            return this;
        }

        public AbortMultipartUpload build() {
            return new AbortMultipartUpload(this);
        }
    }
}
