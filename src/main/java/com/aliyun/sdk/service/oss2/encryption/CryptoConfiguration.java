package com.aliyun.sdk.service.oss2.encryption;

import java.io.Serializable;
import java.security.Provider;
import java.security.SecureRandom;

/**
 * The crypto configuration used to configure secure random generator and content crypto provider.
 */
public class CryptoConfiguration implements Cloneable, Serializable {
    private static final long serialVersionUID = -610360281849259785L;
    private static final SecureRandom SRAND = new SecureRandom();
    /**
     * Default crypto configuration.
     */
    public static final CryptoConfiguration DEFAULT = new CryptoConfiguration();
    private Provider contentCryptoProvider;
    private SecureRandom secureRandom;

    public CryptoConfiguration() {
        this.secureRandom = SRAND;
        this.contentCryptoProvider = null;
    }

    public CryptoConfiguration(SecureRandom secureRandom,
                               Provider contentCryptoProvider) {
        this.secureRandom = secureRandom;
        this.contentCryptoProvider = contentCryptoProvider;
    }

    /**
     * Sets the secure random to the specified secure random generator, and returns the updated
     * CryptoConfiguration object.
     *
     * @param secureRandom the secure random generator.
     * @return The updated CryptoConfiguration object.
     */
    public CryptoConfiguration withSecureRandom(SecureRandom secureRandom) {
        this.secureRandom = secureRandom;
        return this;
    }

    /**
     * Gets the secure random to the specified secure random generator.
     *
     * @return secureRandom
     * the secure random generator.
     */
    public SecureRandom getSecureRandom() {
        return secureRandom;
    }

    /**
     * Sets the secure random to the specified secure random generator, and returns the updated
     * CryptoConfiguration object.
     *
     * @param secureRandom the secure random generator.
     */
    public void setSecureRandom(SecureRandom secureRandom) {
        this.secureRandom = secureRandom;
    }

    /**
     * Sets the content crypto provider the specified provider, and returns the updated
     * CryptoConfiguration object.
     *
     * @param contentCryptoProvider The provider to be used for crypto content.
     * @return The updated CryptoConfiguration object.
     */
    public CryptoConfiguration withContentCryptoProvider(Provider contentCryptoProvider) {
        this.contentCryptoProvider = contentCryptoProvider;
        return this;
    }

    /**
     * Gets the content crypto provider
     *
     * @return the provider to be used for crypto content.
     */
    public Provider getContentCryptoProvider() {
        return this.contentCryptoProvider;
    }

    /**
     * Sets the content crypto provider the specified provider.
     *
     * @param contentCryptoProvider The provider to be used for crypto content.
     */
    public void setContentCryptoProvider(Provider contentCryptoProvider) {
        this.contentCryptoProvider = contentCryptoProvider;
    }

    @Override
    public CryptoConfiguration clone() {
        CryptoConfiguration config = new CryptoConfiguration();
        config.setSecureRandom(secureRandom);
        config.setContentCryptoProvider(contentCryptoProvider);
        return config;
    }
}
