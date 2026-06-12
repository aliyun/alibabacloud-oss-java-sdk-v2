package com.aliyun.sdk.service.oss2.encryption;

import com.aliyun.sdk.service.oss2.encryption.internal.CryptoHeaders;

import java.util.Map;

/**
 * Crypto envelope extracted from object metadata headers on download.
 * <p>
 * Contains all the information needed to reconstruct the content decryption key.
 */
public final class Envelope {

    private final String iv;
    private final String cipherKey;
    private final String matDesc;
    private final String wrapAlg;
    private final String cekAlg;
    private final String unencryptedMD5;
    private final String unencryptedContentLength;

    private Envelope(Builder builder) {
        this.iv = builder.iv;
        this.cipherKey = builder.cipherKey;
        this.matDesc = builder.matDesc;
        this.wrapAlg = builder.wrapAlg;
        this.cekAlg = builder.cekAlg;
        this.unencryptedMD5 = builder.unencryptedMD5;
        this.unencryptedContentLength = builder.unencryptedContentLength;
    }

    public String iv() {
        return iv;
    }

    public String cipherKey() {
        return cipherKey;
    }

    public String matDesc() {
        return matDesc;
    }

    public String wrapAlg() {
        return wrapAlg;
    }

    public String cekAlg() {
        return cekAlg;
    }

    public String unencryptedMD5() {
        return unencryptedMD5;
    }

    public String unencryptedContentLength() {
        return unencryptedContentLength;
    }

    /**
     * Parses an {@code Envelope} from response headers.
     * <p>
     * Headers are expected to have the {@code x-oss-meta-} prefix.
     *
     * @param headers the response headers
     * @return the parsed envelope
     * @throws RuntimeException if required crypto headers are missing
     */
    public static Envelope fromHeaders(Map<String, String> headers) {
        String b64Key = headers.get(CryptoHeaders.X_OSS_META_CRYPTO_KEY);
        String b64IV = headers.get(CryptoHeaders.X_OSS_META_CRYPTO_IV);
        if (b64Key == null || b64IV == null) {
            throw new RuntimeException("Encrypted CEK or IV not found in response headers");
        }

        String wrapAlg = headers.get(CryptoHeaders.X_OSS_META_CRYPTO_WRAP_ALG);
        if (wrapAlg == null) {
            throw new RuntimeException("Key wrap algorithm not found in response headers");
        }

        return newBuilder()
                .cipherKey(b64Key)
                .iv(b64IV)
                .wrapAlg(wrapAlg)
                .cekAlg(headers.get(CryptoHeaders.X_OSS_META_CRYPTO_CEK_ALG))
                .matDesc(headers.get(CryptoHeaders.X_OSS_META_CRYPTO_MATDESC))
                .unencryptedMD5(headers.get(CryptoHeaders.X_OSS_META_CRYPTO_UNENCRYPTION_CONTENT_MD5))
                .unencryptedContentLength(headers.get(CryptoHeaders.X_OSS_META_CRYPTO_UNENCRYPTION_CONTENT_LENGTH))
                .build();
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        private String iv;
        private String cipherKey;
        private String matDesc;
        private String wrapAlg;
        private String cekAlg;
        private String unencryptedMD5;
        private String unencryptedContentLength;

        private Builder() {}

        public Builder iv(String value) {
            this.iv = value;
            return this;
        }

        public Builder cipherKey(String value) {
            this.cipherKey = value;
            return this;
        }

        public Builder matDesc(String value) {
            this.matDesc = value;
            return this;
        }

        public Builder wrapAlg(String value) {
            this.wrapAlg = value;
            return this;
        }

        public Builder cekAlg(String value) {
            this.cekAlg = value;
            return this;
        }

        public Builder unencryptedMD5(String value) {
            this.unencryptedMD5 = value;
            return this;
        }

        public Builder unencryptedContentLength(String value) {
            this.unencryptedContentLength = value;
            return this;
        }

        public Envelope build() {
            return new Envelope(this);
        }
    }
}
