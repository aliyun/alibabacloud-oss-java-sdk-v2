package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * Restore object operation for batch job.
 */
@JacksonXmlRootElement(localName = "RestoreObject")
public final class JobRestoreObject {

    @JacksonXmlProperty(localName = "Days")
    private Long days;

    @JacksonXmlProperty(localName = "Tier")
    private String tier;

    public JobRestoreObject() {
    }

    private JobRestoreObject(Builder builder) {
        this.days = builder.days;
        this.tier = builder.tier;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The number of days for which the object is restored.
     */
    public Long days() {
        return this.days;
    }

    /**
     * The restoration priority. Valid values: Standard, Bulk.
     */
    public String tier() {
        return this.tier;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private Long days;
        private String tier;

        private Builder() {
            super();
        }

        private Builder(JobRestoreObject from) {
            this.days = from.days;
            this.tier = from.tier;
        }

        /**
         * The number of days for which the object is restored.
         */
        public Builder days(Long value) {
            this.days = value;
            return this;
        }

        /**
         * The restoration priority. Valid values: Standard, Bulk.
         */
        public Builder tier(String value) {
            this.tier = value;
            return this;
        }

        public JobRestoreObject build() {
            return new JobRestoreObject(this);
        }
    }
}
