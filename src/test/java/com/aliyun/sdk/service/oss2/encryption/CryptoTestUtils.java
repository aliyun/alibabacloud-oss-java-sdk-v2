package com.aliyun.sdk.service.oss2.encryption;

import com.aliyun.sdk.service.oss2.encryption.crypto.CryptoScheme;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.SecureRandom;

public class CryptoTestUtils {

    public static SecretKey generateCEK() throws NoSuchAlgorithmException {
        KeyGenerator generator;
        final String keygenAlgo = CryptoScheme.AES_CTR.getKeyGeneratorAlgorithm();
        final int keyLength = CryptoScheme.AES_CTR.getKeyLengthInBits();
        generator = KeyGenerator.getInstance(keygenAlgo);
        generator.init(keyLength, new SecureRandom());
        SecretKey secretKey = generator.generateKey();
        for (int retry = 0; retry < 9; retry++) {
            secretKey = generator.generateKey();
            if (secretKey.getEncoded()[0] != 0)
                return secretKey;
        }
        throw new NoSuchAlgorithmException();
    }

    public static byte[] generateIV() {
        final byte[] iv = new byte[CryptoScheme.AES_CTR.getContentCipherIVLength()];
        new SecureRandom().nextBytes(iv);
        for (int i = 8; i < 12; i++) {
            iv[i] = 0;
        }
        return iv;
    }

    @SuppressWarnings({"deprecation"})
    public static Provider getBouncyCastleProvider() {
        try {
            Class<?> clz = Class.forName("org.bouncycastle.jce.provider.BouncyCastleProvider");
            return (Provider) clz.newInstance();
        } catch (Exception e) {
            return null;
        }
    }

}
