package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 * The container that stores the create select object meta request configuration.
 */
public final class SelectMetaRequest {
    @JacksonXmlProperty(localName = "InputSerialization")
    private InputSerialization inputSerialization;

    @JacksonXmlProperty(localName = "OverwriteIfExists")
    private Boolean overwriteIfExists;

    public SelectMetaRequest() {
    }

    private SelectMetaRequest(Builder builder) {
        this.inputSerialization = builder.inputSerialization;
        this.overwriteIfExists = builder.overwriteIfExists;
    }

    public InputSerialization inputSerialization() {
        return this.inputSerialization;
    }

    public Boolean overwriteIfExists() {
        return this.overwriteIfExists;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private InputSerialization inputSerialization;
        private Boolean overwriteIfExists;

        private Builder() {
        }

        private Builder(SelectMetaRequest from) {
            this.inputSerialization = from.inputSerialization;
            this.overwriteIfExists = from.overwriteIfExists;
        }

        public Builder inputSerialization(InputSerialization value) {
            this.inputSerialization = value;
            return this;
        }

        public Builder overwriteIfExists(Boolean value) {
            this.overwriteIfExists = value;
            return this;
        }

        public SelectMetaRequest build() {
            return new SelectMetaRequest(this);
        }
    }
}
