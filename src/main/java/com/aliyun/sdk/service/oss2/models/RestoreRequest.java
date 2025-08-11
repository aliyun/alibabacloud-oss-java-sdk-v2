package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import static java.util.Objects.requireNonNull;

/**
 * The container that stores information about the RestoreObject request.
 */
@JacksonXmlRootElement(localName = "RestoreRequest")
public final class RestoreRequest {
    @JacksonXmlProperty(localName = "Days")
    private Long days;

    @JacksonXmlProperty(localName = "JobParameters")
    private JobParameters jobParameters;

    public RestoreRequest() {
    }

    private RestoreRequest(Builder builder) {
        this.days = builder.days;
        this.jobParameters = builder.jobParameters;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The duration in which the object can remain in the restored state. Unit: days. Valid values: 1 to 7.
     */
    public Long days() {
        return this.days;
    }

    /**
     * The container that stores the restoration priority coniguration. This configuration takes effect only when the request is sent to restore Cold Archive objects. If you do not specify the JobParameters parameter, the default restoration priority Standard is used.
     */
    public JobParameters jobParameters() {
        return this.jobParameters;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private Long days;
        private JobParameters jobParameters;

        private Builder() {
            super();
        }

        private Builder(RestoreRequest from) {
            this.days = from.days;
            this.jobParameters = from.jobParameters;
        }

        /**
         * The duration in which the object can remain in the restored state. Unit: days. Valid values: 1 to 7.
         */
        public Builder days(Long value) {
            requireNonNull(value);
            this.days = value;
            return this;
        }

        /**
         * The container that stores the restoration priority coniguration. This configuration takes effect only when the request is sent to restore Cold Archive objects. If you do not specify the JobParameters parameter, the default restoration priority Standard is used.
         */
        public Builder jobParameters(JobParameters value) {
            requireNonNull(value);
            this.jobParameters = value;
            return this;
        }

        public RestoreRequest build() {
            return new RestoreRequest(this);
        }
    }
}
