package com.aliyun.sdk.service.oss2.encryption;

import java.util.Arrays;

/**
 * Per-object key material for content encryption/decryption.
 * <p>
 * Contains both the plaintext data encryption key (DEK) and IV, as well as
 * their encrypted counterparts produced by the {@link MasterCipher}.
 */
public final class CipherData {

    private final byte[] key;
    private final byte[] iv;
    private final byte[] encryptedKey;
    private final byte[] encryptedIV;

    private CipherData(Builder builder) {
        this.key = builder.key != null ? Arrays.copyOf(builder.key, builder.key.length) : null;
        this.iv = builder.iv != null ? Arrays.copyOf(builder.iv, builder.iv.length) : null;
        this.encryptedKey = builder.encryptedKey != null ? Arrays.copyOf(builder.encryptedKey, builder.encryptedKey.length) : null;
        this.encryptedIV = builder.encryptedIV != null ? Arrays.copyOf(builder.encryptedIV, builder.encryptedIV.length) : null;
    }

    /**
     * Returns a copy of the plaintext data encryption key.
     */
    public byte[] key() {
        return key != null ? Arrays.copyOf(key, key.length) : null;
    }

    /**
     * Returns a copy of the plaintext initialization vector.
     */
    public byte[] iv() {
        return iv != null ? Arrays.copyOf(iv, iv.length) : null;
    }

    /**
     * Returns a copy of the encrypted data encryption key.
     */
    public byte[] encryptedKey() {
        return encryptedKey != null ? Arrays.copyOf(encryptedKey, encryptedKey.length) : null;
    }

    /**
     * Returns a copy of the encrypted initialization vector.
     */
    public byte[] encryptedIV() {
        return encryptedIV != null ? Arrays.copyOf(encryptedIV, encryptedIV.length) : null;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private byte[] key;
        private byte[] iv;
        private byte[] encryptedKey;
        private byte[] encryptedIV;

        private Builder() {}

        private Builder(CipherData from) {
            this.key = from.key;
            this.iv = from.iv;
            this.encryptedKey = from.encryptedKey;
            this.encryptedIV = from.encryptedIV;
        }

        public Builder key(byte[] value) {
            this.key = value;
            return this;
        }

        public Builder iv(byte[] value) {
            this.iv = value;
            return this;
        }

        public Builder encryptedKey(byte[] value) {
            this.encryptedKey = value;
            return this;
        }

        public Builder encryptedIV(byte[] value) {
            this.encryptedIV = value;
            return this;
        }

        public CipherData build() {
            return new CipherData(this);
        }
    }
}
