package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 * The progress summary of a batch operation job.
 */
public final class JobProgressSummary {

    @JacksonXmlProperty(localName = "NumberOfTasksFailed")
    private Long numberOfTasksFailed;

    @JacksonXmlProperty(localName = "NumberOfTasksSucceeded")
    private Long numberOfTasksSucceeded;

    @JacksonXmlProperty(localName = "Timers")
    private JobTimers timers;

    @JacksonXmlProperty(localName = "TotalNumberOfTasks")
    private Long totalNumberOfTasks;

    public JobProgressSummary() {
    }

    private JobProgressSummary(Builder builder) {
        this.numberOfTasksFailed = builder.numberOfTasksFailed;
        this.numberOfTasksSucceeded = builder.numberOfTasksSucceeded;
        this.timers = builder.timers;
        this.totalNumberOfTasks = builder.totalNumberOfTasks;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The number of tasks that failed.
     */
    public Long numberOfTasksFailed() {
        return this.numberOfTasksFailed;
    }

    /**
     * The number of tasks that succeeded.
     */
    public Long numberOfTasksSucceeded() {
        return this.numberOfTasksSucceeded;
    }

    /**
     * The timer information for the job.
     */
    public JobTimers timers() {
        return this.timers;
    }

    /**
     * The total number of tasks.
     */
    public Long totalNumberOfTasks() {
        return this.totalNumberOfTasks;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private Long numberOfTasksFailed;
        private Long numberOfTasksSucceeded;
        private JobTimers timers;
        private Long totalNumberOfTasks;

        private Builder() {
            super();
        }

        private Builder(JobProgressSummary from) {
            this.numberOfTasksFailed = from.numberOfTasksFailed;
            this.numberOfTasksSucceeded = from.numberOfTasksSucceeded;
            this.timers = from.timers;
            this.totalNumberOfTasks = from.totalNumberOfTasks;
        }

        public Builder numberOfTasksFailed(Long value) {
            this.numberOfTasksFailed = value;
            return this;
        }

        public Builder numberOfTasksSucceeded(Long value) {
            this.numberOfTasksSucceeded = value;
            return this;
        }

        public Builder timers(JobTimers value) {
            this.timers = value;
            return this;
        }

        public Builder totalNumberOfTasks(Long value) {
            this.totalNumberOfTasks = value;
            return this;
        }

        public JobProgressSummary build() {
            return new JobProgressSummary(this);
        }
    }
}
