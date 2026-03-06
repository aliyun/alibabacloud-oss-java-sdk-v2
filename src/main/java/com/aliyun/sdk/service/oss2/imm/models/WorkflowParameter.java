package com.aliyun.sdk.service.oss2.imm.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "WorkflowParameter")
public final class WorkflowParameter {

    @JacksonXmlProperty(localName = "Name")
    private String name;

    @JacksonXmlProperty(localName = "Value")
    private String value;

    public WorkflowParameter() {
    }

    private WorkflowParameter(Builder builder) {
        this.name = builder.name;
        this.value = builder.value;
    }

    public String name() {
        return this.name;
    }

    public String value() {
        return this.value;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private String name;
        private String value;

        public Builder name(String value) {
            this.name = value;
            return this;
        }

        public Builder value(String value) {
            this.value = value;
            return this;
        }

        private Builder() {
            super();
        }

        private Builder(WorkflowParameter from) {
            this.name = from.name;
            this.value = from.value;
        }

        public WorkflowParameter build() {
            return new WorkflowParameter(this);
        }
    }
}
