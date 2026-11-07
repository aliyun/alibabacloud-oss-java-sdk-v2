package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * The XML body for the UpdateJobStatus response.
 */
@JacksonXmlRootElement(localName = "UpdateJobStatusResult")
public final class UpdateJobStatusResultBody {

    @JacksonXmlProperty(localName = "JobId")
    private String jobId;

    @JacksonXmlProperty(localName = "Status")
    private String status;

    @JacksonXmlProperty(localName = "StatusUpdateReason")
    private String statusUpdateReason;

    public UpdateJobStatusResultBody() {
    }

    private UpdateJobStatusResultBody(Builder builder) {
        this.jobId = builder.jobId;
        this.status = builder.status;
        this.statusUpdateReason = builder.statusUpdateReason;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The job ID.
     */
    public String jobId() {
        return this.jobId;
    }

    /**
     * The updated status.
     */
    public String status() {
        return this.status;
    }

    /**
     * The reason for the status update.
     */
    public String statusUpdateReason() {
        return this.statusUpdateReason;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private String jobId;
        private String status;
        private String statusUpdateReason;

        private Builder() {
            super();
        }

        private Builder(UpdateJobStatusResultBody from) {
            this.jobId = from.jobId;
            this.status = from.status;
            this.statusUpdateReason = from.statusUpdateReason;
        }

        public Builder jobId(String value) {
            this.jobId = value;
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

        public UpdateJobStatusResultBody build() {
            return new UpdateJobStatusResultBody(this);
        }
    }
}
