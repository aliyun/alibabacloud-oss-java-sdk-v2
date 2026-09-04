package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import static java.util.Objects.requireNonNull;

/**
 * The report configuration for a batch job.
 */
@JacksonXmlRootElement(localName = "Report")
public final class JobReport {

    @JacksonXmlProperty(localName = "Bucket")
    private String bucket;

    @JacksonXmlProperty(localName = "Enabled")
    private Boolean enabled;

    @JacksonXmlProperty(localName = "Prefix")
    private String prefix;

    @JacksonXmlProperty(localName = "ReportScope")
    private String reportScope;

    public JobReport() {
    }

    private JobReport(Builder builder) {
        this.bucket = builder.bucket;
        this.enabled = builder.enabled;
        this.prefix = builder.prefix;
        this.reportScope = builder.reportScope;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The bucket where the report is stored.
     */
    public String bucket() {
        return this.bucket;
    }

    /**
     * Specifies whether to generate a report.
     */
    public Boolean enabled() {
        return this.enabled;
    }

    /**
     * The prefix of the report output.
     */
    public String prefix() {
        return this.prefix;
    }

    /**
     * The scope of the report. Valid values: AllTasks, FailedTasksOnly.
     */
    public String reportScope() {
        return this.reportScope;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private String bucket;
        private Boolean enabled;
        private String prefix;
        private String reportScope;

        private Builder() {
            super();
        }

        private Builder(JobReport from) {
            this.bucket = from.bucket;
            this.enabled = from.enabled;
            this.prefix = from.prefix;
            this.reportScope = from.reportScope;
        }

        /**
         * The bucket where the report is stored.
         */
        public Builder bucket(String value) {
            requireNonNull(value);
            this.bucket = value;
            return this;
        }

        /**
         * Specifies whether to generate a report.
         */
        public Builder enabled(Boolean value) {
            requireNonNull(value);
            this.enabled = value;
            return this;
        }

        /**
         * The prefix of the report output.
         */
        public Builder prefix(String value) {
            this.prefix = value;
            return this;
        }

        /**
         * The scope of the report. Valid values: AllTasks, FailedTasksOnly.
         */
        public Builder reportScope(String value) {
            this.reportScope = value;
            return this;
        }

        public JobReport build() {
            return new JobReport(this);
        }
    }
}
