package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import static java.util.Objects.requireNonNull;

/**
 * The manifest specification for a batch job.
 */
@JacksonXmlRootElement(localName = "Spec")
public final class JobManifestSpec {

    @JacksonXmlProperty(localName = "Fields")
    private String fields;

    @JacksonXmlProperty(localName = "Format")
    private String format;

    public JobManifestSpec() {
    }

    private JobManifestSpec(Builder builder) {
        this.fields = builder.fields;
        this.format = builder.format;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The CSV fields definition.
     */
    public String fields() {
        return this.fields;
    }

    /**
     * The manifest format. Valid values: OSS_BatchOperations_CSV_20250611, OSS_InventoryReport_CSV_20250611.
     */
    public String format() {
        return this.format;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private String fields;
        private String format;

        private Builder() {
            super();
        }

        private Builder(JobManifestSpec from) {
            this.fields = from.fields;
            this.format = from.format;
        }

        /**
         * The CSV fields definition.
         */
        public Builder fields(String value) {
            this.fields = value;
            return this;
        }

        /**
         * The manifest format. Valid values: OSS_BatchOperations_CSV_20250611, OSS_InventoryReport_CSV_20250611.
         */
        public Builder format(String value) {
            requireNonNull(value);
            this.format = value;
            return this;
        }

        public JobManifestSpec build() {
            return new JobManifestSpec(this);
        }
    }
}
