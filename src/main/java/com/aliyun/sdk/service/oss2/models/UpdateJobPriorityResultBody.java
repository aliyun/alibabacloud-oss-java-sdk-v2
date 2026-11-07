package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * The XML body for the UpdateJobPriority response.
 */
@JacksonXmlRootElement(localName = "UpdateJobPriorityResult")
public final class UpdateJobPriorityResultBody {

    @JacksonXmlProperty(localName = "JobId")
    private String jobId;

    @JacksonXmlProperty(localName = "Priority")
    private Long priority;

    public UpdateJobPriorityResultBody() {
    }

    private UpdateJobPriorityResultBody(Builder builder) {
        this.jobId = builder.jobId;
        this.priority = builder.priority;
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
     * The updated priority.
     */
    public Long priority() {
        return this.priority;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private String jobId;
        private Long priority;

        private Builder() {
            super();
        }

        private Builder(UpdateJobPriorityResultBody from) {
            this.jobId = from.jobId;
            this.priority = from.priority;
        }

        public Builder jobId(String value) {
            this.jobId = value;
            return this;
        }

        public Builder priority(Long value) {
            this.priority = value;
            return this;
        }

        public UpdateJobPriorityResultBody build() {
            return new UpdateJobPriorityResultBody(this);
        }
    }
}
