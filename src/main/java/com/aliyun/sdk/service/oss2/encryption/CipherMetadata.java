package com.aliyun.sdk.service.oss2.encryption;

/**
 * Algorithm descriptors associated with a {@link ContentCipherBuilder}.
 */
public final class CipherMetadata {

    private final String wrapAlgorithm;
    private final String cekAlgorithm;
    private final String matDesc;

    private CipherMetadata(Builder builder) {
        this.wrapAlgorithm = builder.wrapAlgorithm;
        this.cekAlgorithm = builder.cekAlgorithm;
        this.matDesc = builder.matDesc;
    }

    /**
     * Returns the key wrap algorithm, e.g. {@code "RSA/NONE/PKCS1Padding"}.
     */
    public String wrapAlgorithm() {
        return wrapAlgorithm;
    }

    /**
     * Returns the content encryption algorithm, e.g. {@code "AES/CTR/NoPadding"}.
     */
    public String cekAlgorithm() {
        return cekAlgorithm;
    }

    /**
     * Returns the material description as a JSON string.
     */
    public String matDesc() {
        return matDesc;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        private String wrapAlgorithm;
        private String cekAlgorithm;
        private String matDesc;

        private Builder() {}

        public Builder wrapAlgorithm(String value) {
            this.wrapAlgorithm = value;
            return this;
        }

        public Builder cekAlgorithm(String value) {
            this.cekAlgorithm = value;
            return this;
        }

        public Builder matDesc(String value) {
            this.matDesc = value;
            return this;
        }

        public CipherMetadata build() {
            return new CipherMetadata(this);
        }
    }
}
