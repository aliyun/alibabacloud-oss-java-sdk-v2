package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * The operation type for a batch job. Only one operation type can be specified.
 */
@JacksonXmlRootElement(localName = "Operation")
public final class JobOperation {

    @JacksonXmlProperty(localName = "PutObjectTagging")
    private JobPutObjectTagging putObjectTagging;

    @JacksonXmlProperty(localName = "DeleteObjectTagging")
    private JobDeleteObjectTagging deleteObjectTagging;

    @JacksonXmlProperty(localName = "PutObjectAcl")
    private JobPutObjectAcl putObjectAcl;

    @JacksonXmlProperty(localName = "RestoreObject")
    private JobRestoreObject restoreObject;

    public JobOperation() {
    }

    private JobOperation(Builder builder) {
        this.putObjectTagging = builder.putObjectTagging;
        this.deleteObjectTagging = builder.deleteObjectTagging;
        this.putObjectAcl = builder.putObjectAcl;
        this.restoreObject = builder.restoreObject;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public JobPutObjectTagging putObjectTagging() {
        return this.putObjectTagging;
    }

    public JobDeleteObjectTagging deleteObjectTagging() {
        return this.deleteObjectTagging;
    }

    public JobPutObjectAcl putObjectAcl() {
        return this.putObjectAcl;
    }

    public JobRestoreObject restoreObject() {
        return this.restoreObject;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private JobPutObjectTagging putObjectTagging;
        private JobDeleteObjectTagging deleteObjectTagging;
        private JobPutObjectAcl putObjectAcl;
        private JobRestoreObject restoreObject;

        private Builder() {
            super();
        }

        private Builder(JobOperation from) {
            this.putObjectTagging = from.putObjectTagging;
            this.deleteObjectTagging = from.deleteObjectTagging;
            this.putObjectAcl = from.putObjectAcl;
            this.restoreObject = from.restoreObject;
        }

        public Builder putObjectTagging(JobPutObjectTagging value) {
            this.putObjectTagging = value;
            return this;
        }

        public Builder deleteObjectTagging(JobDeleteObjectTagging value) {
            this.deleteObjectTagging = value;
            return this;
        }

        public Builder putObjectAcl(JobPutObjectAcl value) {
            this.putObjectAcl = value;
            return this;
        }

        public Builder restoreObject(JobRestoreObject value) {
            this.restoreObject = value;
            return this;
        }

        public JobOperation build() {
            return new JobOperation(this);
        }
    }
}
