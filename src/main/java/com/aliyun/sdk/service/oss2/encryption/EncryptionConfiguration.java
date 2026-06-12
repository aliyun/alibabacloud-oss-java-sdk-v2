package com.aliyun.sdk.service.oss2.encryption;

import java.util.Objects;

/**
 * Configuration for client-side encryption.
 */
public final class EncryptionConfiguration {

    private final MasterCipher masterCipher;
    private final CryptoConfiguration cryptoConfig;

    private EncryptionConfiguration(Builder builder) {
        this.masterCipher = Objects.requireNonNull(builder.masterCipher, "masterCipher must not be null");
        this.cryptoConfig = builder.cryptoConfig;
    }

    public MasterCipher masterCipher() {
        return masterCipher;
    }

    /**
     * Returns the crypto configuration, or {@code null} if not set
     * (defaults will be used by {@link ContentCipherBuilder}).
     */
    public CryptoConfiguration cryptoConfig() {
        return cryptoConfig;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        private MasterCipher masterCipher;
        private CryptoConfiguration cryptoConfig;

        public Builder masterCipher(MasterCipher value) {
            this.masterCipher = value;
            return this;
        }

        public Builder cryptoConfig(CryptoConfiguration value) {
            this.cryptoConfig = value;
            return this;
        }

        public EncryptionConfiguration build() {
            return new EncryptionConfiguration(this);
        }
    }
}
