package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

/**
 * The XML body for the ListJobs response.
 */
@JacksonXmlRootElement(localName = "ListJobsResult")
public final class ListJobsResultBody {

    @JacksonXmlProperty(localName = "NextToken")
    private String nextToken;

    @JacksonXmlElementWrapper(localName = "Jobs")
    @JacksonXmlProperty(localName = "JobListDescriptor")
    private List<JobListDescriptor> jobs;

    public ListJobsResultBody() {
    }

    private ListJobsResultBody(Builder builder) {
        this.nextToken = builder.nextToken;
        this.jobs = builder.jobs;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The token for the next page of results.
     */
    public String nextToken() {
        return this.nextToken;
    }

    /**
     * The list of job descriptors.
     */
    public List<JobListDescriptor> jobs() {
        return this.jobs;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private String nextToken;
        private List<JobListDescriptor> jobs;

        private Builder() {
            super();
        }

        private Builder(ListJobsResultBody from) {
            this.nextToken = from.nextToken;
            this.jobs = from.jobs;
        }

        public Builder nextToken(String value) {
            this.nextToken = value;
            return this;
        }

        public Builder jobs(List<JobListDescriptor> value) {
            this.jobs = value;
            return this;
        }

        public ListJobsResultBody build() {
            return new ListJobsResultBody(this);
        }
    }
}
