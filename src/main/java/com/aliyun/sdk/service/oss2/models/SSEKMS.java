package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import static java.util.Objects.requireNonNull;

/**
 * The container that stores the customer master key (CMK) used for SSE-KMS encryption.
 */
@JacksonXmlRootElement(localName = "SSEKMS")
public final class SSEKMS {
    @JacksonXmlProperty(localName = "KeyId")
    private String keyId;

    public SSEKMS() {
    }

    private SSEKMS(Builder builder) {
        this.keyId = builder.keyId;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The ID of the key that is managed by Key Management Service (KMS).
     */
    public String keyId() {
        return this.keyId;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private String keyId;

        private Builder() {
            super();
        }


        private Builder(SSEKMS from) {
            this.keyId = from.keyId;
        }

        /**
         * The ID of the key that is managed by Key Management Service (KMS).
         */
        public Builder keyId(String value) {
            requireNonNull(value);
            this.keyId = value;
            return this;
        }

        public SSEKMS build() {
            return new SSEKMS(this);
        }
    }
}
