package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 * The summary descriptor of a batch operation job in a list.
 */
public final class JobListDescriptor {

    @JacksonXmlProperty(localName = "CreationTime")
    private Long creationTime;

    @JacksonXmlProperty(localName = "Description")
    private String description;

    @JacksonXmlProperty(localName = "JobId")
    private String jobId;

    @JacksonXmlProperty(localName = "Operation")
    private String operation;

    @JacksonXmlProperty(localName = "Priority")
    private Long priority;

    @JacksonXmlProperty(localName = "ProgressSummary")
    private JobProgressSummary progressSummary;

    @JacksonXmlProperty(localName = "Status")
    private String status;

    @JacksonXmlProperty(localName = "TerminationDate")
    private Long terminationDate;

    public JobListDescriptor() {
    }

    private JobListDescriptor(Builder builder) {
        this.creationTime = builder.creationTime;
        this.description = builder.description;
        this.jobId = builder.jobId;
        this.operation = builder.operation;
        this.priority = builder.priority;
        this.progressSummary = builder.progressSummary;
        this.status = builder.status;
        this.terminationDate = builder.terminationDate;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Long creationTime() {
        return this.creationTime;
    }

    public String description() {
        return this.description;
    }

    public String jobId() {
        return this.jobId;
    }

    public String operation() {
        return this.operation;
    }

    public Long priority() {
        return this.priority;
    }

    public JobProgressSummary progressSummary() {
        return this.progressSummary;
    }

    public String status() {
        return this.status;
    }

    public Long terminationDate() {
        return this.terminationDate;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private Long creationTime;
        private String description;
        private String jobId;
        private String operation;
        private Long priority;
        private JobProgressSummary progressSummary;
        private String status;
        private Long terminationDate;

        private Builder() {
            super();
        }

        private Builder(JobListDescriptor from) {
            this.creationTime = from.creationTime;
            this.description = from.description;
            this.jobId = from.jobId;
            this.operation = from.operation;
            this.priority = from.priority;
            this.progressSummary = from.progressSummary;
            this.status = from.status;
            this.terminationDate = from.terminationDate;
        }

        public Builder creationTime(Long value) {
            this.creationTime = value;
            return this;
        }

        public Builder description(String value) {
            this.description = value;
            return this;
        }

        public Builder jobId(String value) {
            this.jobId = value;
            return this;
        }

        public Builder operation(String value) {
            this.operation = value;
            return this;
        }

        public Builder priority(Long value) {
            this.priority = value;
            return this;
        }

        public Builder progressSummary(JobProgressSummary value) {
            this.progressSummary = value;
            return this;
        }

        public Builder status(String value) {
            this.status = value;
            return this;
        }

        public Builder terminationDate(Long value) {
            this.terminationDate = value;
            return this;
        }

        public JobListDescriptor build() {
            return new JobListDescriptor(this);
        }
    }
}
