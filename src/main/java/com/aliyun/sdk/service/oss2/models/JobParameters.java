package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import static java.util.Objects.requireNonNull;

/**
 * The container that stores the restoration priority coniguration. This configuration takes effect only when the request is sent to restore Cold Archive objects. If you do not specify the JobParameters parameter, the default restoration priority Standard is used.
 */
@JacksonXmlRootElement(localName = "JobParameters")
public final class JobParameters {
    @JacksonXmlProperty(localName = "Tier")
    private String tier;

    public JobParameters() {
    }

    private JobParameters(Builder builder) {
        this.tier = builder.tier;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The restoration priority. Valid values:*   Expedited: The object is restored within 1 hour.*   Standard: The object is restored within 2 to 5 hours.*   Bulk: The object is restored within 5 to 12 hours.
     */
    public String tier() {
        return this.tier;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private String tier;

        private Builder() {
            super();
        }


        private Builder(JobParameters from) {
            this.tier = from.tier;
        }

        /**
         * The restoration priority. Valid values:*   Expedited: The object is restored within 1 hour.*   Standard: The object is restored within 2 to 5 hours.*   Bulk: The object is restored within 5 to 12 hours.
         */
        public Builder tier(String value) {
            requireNonNull(value);
            this.tier = value;
            return this;
        }

        public JobParameters build() {
            return new JobParameters(this);
        }
    }
}
