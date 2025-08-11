package com.aliyun.sdk.service.oss2.encryption;

import org.junit.Assert;
import org.junit.Test;

import java.security.SecureRandom;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class CryptoConfigurationTest {
    @Test
    public void testConstruction() {
        CryptoConfiguration cryptoConfig = new CryptoConfiguration();
        assertEquals(ContentCryptoMode.AES_CTR_MODE, cryptoConfig.getContentCryptoMode());
        assertEquals(CryptoStorageMethod.ObjectMetadata, cryptoConfig.getStorageMethod());
        assertEquals(SecureRandom.class.getName(), cryptoConfig.getSecureRandom().getClass().getName());
        assertNull(cryptoConfig.getContentCryptoProvider());

        cryptoConfig = new CryptoConfiguration(
                ContentCryptoMode.AES_CTR_MODE,
                CryptoStorageMethod.ObjectMetadata,
                new SecureRandom(),
                CryptoTestUtils.getBouncyCastleProvider());

        assertEquals(ContentCryptoMode.AES_CTR_MODE, cryptoConfig.getContentCryptoMode());
        assertEquals(CryptoStorageMethod.ObjectMetadata, cryptoConfig.getStorageMethod());
        assertEquals(SecureRandom.class.getName(), cryptoConfig.getSecureRandom().getClass().getName());
        assertEquals("BC", cryptoConfig.getContentCryptoProvider().getName());
    }

    @Test
    public void testRandom() {
        CryptoConfiguration cryptoConfig = new CryptoConfiguration();
        Assert.assertNotNull(cryptoConfig.getSecureRandom());

        cryptoConfig = new CryptoConfiguration().withSecureRandom(null);
        Assert.assertNull(cryptoConfig.getSecureRandom());

        cryptoConfig = new CryptoConfiguration();
        cryptoConfig.setSecureRandom(null);
        Assert.assertNull(cryptoConfig.getSecureRandom());
    }

    @Test
    public void testProvider() {
        CryptoConfiguration cryptoConfig = new CryptoConfiguration();
        assertNull(cryptoConfig.getContentCryptoProvider());

        cryptoConfig = new CryptoConfiguration().withContentCryptoProvider(CryptoTestUtils.getBouncyCastleProvider());
        assertEquals("BC", cryptoConfig.getContentCryptoProvider().getName());

        cryptoConfig = new CryptoConfiguration();
        cryptoConfig.setContentCryptoProvider(CryptoTestUtils.getBouncyCastleProvider());
        assertEquals("BC", cryptoConfig.getContentCryptoProvider().getName());
    }
}