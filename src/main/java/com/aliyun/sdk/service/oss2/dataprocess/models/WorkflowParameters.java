package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

@JacksonXmlRootElement(localName = "WorkflowParameters")
public final class WorkflowParameters {

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "WorkflowParameter")
    private List<WorkflowParameter> workflowParameters;

    public WorkflowParameters() {
    }

    private WorkflowParameters(Builder builder) {
        this.workflowParameters = builder.workflowParameters;
    }

    public List<WorkflowParameter> workflowParameters() {
        return this.workflowParameters;
    }


    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private List<WorkflowParameter> workflowParameters;

        public Builder workflowParameters(List<WorkflowParameter> value) {
            this.workflowParameters = value;
            return this;
        }


        private Builder() {
            super();
        }

        private Builder(WorkflowParameters from) {
            this.workflowParameters = from.workflowParameters;
        }

        public WorkflowParameters build() {
            return new WorkflowParameters(this);
        }
    }
}
