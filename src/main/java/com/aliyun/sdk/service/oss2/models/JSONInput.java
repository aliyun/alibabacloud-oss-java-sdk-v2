package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 * The container that stores the JSON input configuration.
 */
public final class JSONInput {
    @JacksonXmlProperty(localName = "ParseJsonNumberAsString")
    private Boolean parseJsonNumberAsString;

    @JacksonXmlProperty(localName = "Range")
    private String range;

    @JacksonXmlProperty(localName = "Type")
    private String type;

    public JSONInput() {
    }

    private JSONInput(Builder builder) {
        this.parseJsonNumberAsString = builder.parseJsonNumberAsString;
        this.range = builder.range;
        this.type = builder.type;
    }

    public Boolean parseJsonNumberAsString() {
        return this.parseJsonNumberAsString;
    }

    public String range() {
        return this.range;
    }

    public String type() {
        return this.type;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private Boolean parseJsonNumberAsString;
        private String range;
        private String type;

        private Builder() {
        }

        private Builder(JSONInput from) {
            this.parseJsonNumberAsString = from.parseJsonNumberAsString;
            this.range = from.range;
            this.type = from.type;
        }

        public Builder parseJsonNumberAsString(Boolean value) {
            this.parseJsonNumberAsString = value;
            return this;
        }

        public Builder range(String value) {
            this.range = value;
            return this;
        }

        public Builder type(String value) {
            this.type = value;
            return this;
        }

        public JSONInput build() {
            return new JSONInput(this);
        }
    }
}
