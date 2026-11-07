package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import static java.util.Objects.requireNonNull;

/**
 * The manifest configuration for a batch job.
 */
@JacksonXmlRootElement(localName = "Manifest")
public final class JobManifest {

    @JacksonXmlProperty(localName = "Location")
    private JobManifestLocation location;

    @JacksonXmlProperty(localName = "Spec")
    private JobManifestSpec spec;

    public JobManifest() {
    }

    private JobManifest(Builder builder) {
        this.location = builder.location;
        this.spec = builder.spec;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public JobManifestLocation location() {
        return this.location;
    }

    public JobManifestSpec spec() {
        return this.spec;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private JobManifestLocation location;
        private JobManifestSpec spec;

        private Builder() {
            super();
        }

        private Builder(JobManifest from) {
            this.location = from.location;
            this.spec = from.spec;
        }

        public Builder location(JobManifestLocation value) {
            requireNonNull(value);
            this.location = value;
            return this;
        }

        public Builder spec(JobManifestSpec value) {
            requireNonNull(value);
            this.spec = value;
            return this;
        }

        public JobManifest build() {
            return new JobManifest(this);
        }
    }
}
