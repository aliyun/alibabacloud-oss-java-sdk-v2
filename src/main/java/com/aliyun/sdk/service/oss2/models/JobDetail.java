package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;

/**
 * The detailed information about a batch operation job.
 */
public final class JobDetail {

    @JacksonXmlProperty(localName = "ConfirmationRequired")
    private Boolean confirmationRequired;

    @JacksonXmlProperty(localName = "CreationTime")
    private Long creationTime;

    @JacksonXmlProperty(localName = "Description")
    private String description;

    @JacksonXmlProperty(localName = "JobId")
    private String jobId;

    @JacksonXmlProperty(localName = "Operation")
    private JobOperation operation;

    @JacksonXmlProperty(localName = "Priority")
    private Long priority;

    @JacksonXmlProperty(localName = "Report")
    private JobReport report;

    @JacksonXmlProperty(localName = "Manifest")
    private JobManifest manifest;

    @JacksonXmlProperty(localName = "KeyPrefixManifestGenerator")
    private KeyPrefixManifestGenerator keyPrefixManifestGenerator;

    @JacksonXmlProperty(localName = "RoleArn")
    private String roleArn;

    @JacksonXmlProperty(localName = "Status")
    private String status;

    @JacksonXmlProperty(localName = "StatusUpdateReason")
    private String statusUpdateReason;

    @JacksonXmlProperty(localName = "TerminationDate")
    private Long terminationDate;

    @JacksonXmlProperty(localName = "ProgressSummary")
    private JobProgressSummary progressSummary;

    @JacksonXmlElementWrapper(localName = "FailureReasons")
    @JacksonXmlProperty(localName = "JobFailure")
    private List<JobFailure> failureReasons;

    public JobDetail() {
    }

    private JobDetail(Builder builder) {
        this.confirmationRequired = builder.confirmationRequired;
        this.creationTime = builder.creationTime;
        this.description = builder.description;
        this.jobId = builder.jobId;
        this.operation = builder.operation;
        this.priority = builder.priority;
        this.report = builder.report;
        this.manifest = builder.manifest;
        this.keyPrefixManifestGenerator = builder.keyPrefixManifestGenerator;
        this.roleArn = builder.roleArn;
        this.status = builder.status;
        this.statusUpdateReason = builder.statusUpdateReason;
        this.terminationDate = builder.terminationDate;
        this.progressSummary = builder.progressSummary;
        this.failureReasons = builder.failureReasons;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Boolean confirmationRequired() {
        return this.confirmationRequired;
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

    public JobOperation operation() {
        return this.operation;
    }

    public Long priority() {
        return this.priority;
    }

    public JobReport report() {
        return this.report;
    }

    public JobManifest manifest() {
        return this.manifest;
    }

    public KeyPrefixManifestGenerator keyPrefixManifestGenerator() {
        return this.keyPrefixManifestGenerator;
    }

    public String roleArn() {
        return this.roleArn;
    }

    public String status() {
        return this.status;
    }

    public String statusUpdateReason() {
        return this.statusUpdateReason;
    }

    public Long terminationDate() {
        return this.terminationDate;
    }

    public JobProgressSummary progressSummary() {
        return this.progressSummary;
    }

    public List<JobFailure> failureReasons() {
        return this.failureReasons;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private Boolean confirmationRequired;
        private Long creationTime;
        private String description;
        private String jobId;
        private JobOperation operation;
        private Long priority;
        private JobReport report;
        private JobManifest manifest;
        private KeyPrefixManifestGenerator keyPrefixManifestGenerator;
        private String roleArn;
        private String status;
        private String statusUpdateReason;
        private Long terminationDate;
        private JobProgressSummary progressSummary;
        private List<JobFailure> failureReasons;

        private Builder() {
            super();
        }

        private Builder(JobDetail from) {
            this.confirmationRequired = from.confirmationRequired;
            this.creationTime = from.creationTime;
            this.description = from.description;
            this.jobId = from.jobId;
            this.operation = from.operation;
            this.priority = from.priority;
            this.report = from.report;
            this.manifest = from.manifest;
            this.keyPrefixManifestGenerator = from.keyPrefixManifestGenerator;
            this.roleArn = from.roleArn;
            this.status = from.status;
            this.statusUpdateReason = from.statusUpdateReason;
            this.terminationDate = from.terminationDate;
            this.progressSummary = from.progressSummary;
            this.failureReasons = from.failureReasons;
        }

        public Builder confirmationRequired(Boolean value) {
            this.confirmationRequired = value;
            return this;
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

        public Builder operation(JobOperation value) {
            this.operation = value;
            return this;
        }

        public Builder priority(Long value) {
            this.priority = value;
            return this;
        }

        public Builder report(JobReport value) {
            this.report = value;
            return this;
        }

        public Builder manifest(JobManifest value) {
            this.manifest = value;
            return this;
        }

        public Builder keyPrefixManifestGenerator(KeyPrefixManifestGenerator value) {
            this.keyPrefixManifestGenerator = value;
            return this;
        }

        public Builder roleArn(String value) {
            this.roleArn = value;
            return this;
        }

        public Builder status(String value) {
            this.status = value;
            return this;
        }

        public Builder statusUpdateReason(String value) {
            this.statusUpdateReason = value;
            return this;
        }

        public Builder terminationDate(Long value) {
            this.terminationDate = value;
            return this;
        }

        public Builder progressSummary(JobProgressSummary value) {
            this.progressSummary = value;
            return this;
        }

        public Builder failureReasons(List<JobFailure> value) {
            this.failureReasons = value;
            return this;
        }

        public JobDetail build() {
            return new JobDetail(this);
        }
    }
}
