package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * The XML body for the CreateJob request.
 */
@JacksonXmlRootElement(localName = "CreateJobRequest")
public final class CreateJobRequestBody {

    @JacksonXmlProperty(localName = "ConfirmationRequired")
    private Boolean confirmationRequired;

    @JacksonXmlProperty(localName = "Operation")
    private JobOperation operation;

    @JacksonXmlProperty(localName = "Report")
    private JobReport report;

    @JacksonXmlProperty(localName = "ClientRequestToken")
    private String clientRequestToken;

    @JacksonXmlProperty(localName = "Manifest")
    private JobManifest manifest;

    @JacksonXmlProperty(localName = "KeyPrefixManifestGenerator")
    private KeyPrefixManifestGenerator keyPrefixManifestGenerator;

    @JacksonXmlProperty(localName = "Description")
    private String description;

    @JacksonXmlProperty(localName = "Priority")
    private Long priority;

    @JacksonXmlProperty(localName = "RoleArn")
    private String roleArn;

    public CreateJobRequestBody() {
    }

    private CreateJobRequestBody(Builder builder) {
        this.confirmationRequired = builder.confirmationRequired;
        this.operation = builder.operation;
        this.report = builder.report;
        this.clientRequestToken = builder.clientRequestToken;
        this.manifest = builder.manifest;
        this.keyPrefixManifestGenerator = builder.keyPrefixManifestGenerator;
        this.description = builder.description;
        this.priority = builder.priority;
        this.roleArn = builder.roleArn;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Boolean confirmationRequired() {
        return this.confirmationRequired;
    }

    public JobOperation operation() {
        return this.operation;
    }

    public JobReport report() {
        return this.report;
    }

    public String clientRequestToken() {
        return this.clientRequestToken;
    }

    public JobManifest manifest() {
        return this.manifest;
    }

    public KeyPrefixManifestGenerator keyPrefixManifestGenerator() {
        return this.keyPrefixManifestGenerator;
    }

    public String description() {
        return this.description;
    }

    public Long priority() {
        return this.priority;
    }

    public String roleArn() {
        return this.roleArn;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private Boolean confirmationRequired;
        private JobOperation operation;
        private JobReport report;
        private String clientRequestToken;
        private JobManifest manifest;
        private KeyPrefixManifestGenerator keyPrefixManifestGenerator;
        private String description;
        private Long priority;
        private String roleArn;

        private Builder() {
            super();
        }

        private Builder(CreateJobRequestBody from) {
            this.confirmationRequired = from.confirmationRequired;
            this.operation = from.operation;
            this.report = from.report;
            this.clientRequestToken = from.clientRequestToken;
            this.manifest = from.manifest;
            this.keyPrefixManifestGenerator = from.keyPrefixManifestGenerator;
            this.description = from.description;
            this.priority = from.priority;
            this.roleArn = from.roleArn;
        }

        public Builder confirmationRequired(Boolean value) {
            this.confirmationRequired = value;
            return this;
        }

        public Builder operation(JobOperation value) {
            this.operation = value;
            return this;
        }

        public Builder report(JobReport value) {
            this.report = value;
            return this;
        }

        public Builder clientRequestToken(String value) {
            this.clientRequestToken = value;
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

        public Builder description(String value) {
            this.description = value;
            return this;
        }

        public Builder priority(Long value) {
            this.priority = value;
            return this;
        }

        public Builder roleArn(String value) {
            this.roleArn = value;
            return this;
        }

        public CreateJobRequestBody build() {
            return new CreateJobRequestBody(this);
        }
    }
}
