package com.aliyun.sdk.service.oss2.encryption.crypto;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.security.Provider;

public abstract class CryptoScheme {
    public static final int BLOCK_SIZE = 16;
    public static final CryptoScheme AES_CTR = new AesCtr();

    // Enable bouncy castle provider
    static {
        CryptoRuntime.enableBouncyCastle();
    }

    /**
     * Increment the rightmost 64 bits of a 16-byte counter by the specified delta.
     * Both the specified delta and the resultant value must stay within the
     * capacity of 64 bits. (Package private for testing purposes.)
     *
     * @param counter    a 16-byte counter.
     * @param blockDelta the number of blocks (16-byte) to increment
     */
    public static byte[] incrementBlocks(byte[] counter, long blockDelta) {
        if (blockDelta == 0)
            return counter;
        if (counter == null || counter.length != 16) {
            throw new IllegalArgumentException(
                    "Counter must be 16 bytes, got: " + (counter == null ? "null" : counter.length));
        }

        // Read rightmost 64 bits as long (big-endian)
        long val = 0;
        for (int i = 8; i < 16; i++) {
            val = (val << 8) | (counter[i] & 0xFFL);
        }
        val += blockDelta;

        // Write back (big-endian)
        for (int i = 15; i >= 8; i--) {
            counter[i] = (byte) (val & 0xFF);
            val >>>= 8;
        }
        return counter;
    }

    public static CryptoScheme fromCEKAlgo(String cekAlgo) {
        if (AES_CTR.getContentCipherAlgorithm().equals(cekAlgo)) {
            return AES_CTR;
        }
        throw new UnsupportedOperationException("Unsupported content encryption scheme: " + cekAlgo);
    }

    public abstract String getKeyGeneratorAlgorithm();

    public abstract int getKeyLengthInBits();

    public abstract String getContentCipherAlgorithm();

    public abstract int getContentCipherIVLength();

    public abstract byte[] adjustIV(byte[] iv, long dataStartPos);

    /**
     * This is a factory method to create CryptoCipher.
     */
    public CryptoCipher newCryptoCipher(Cipher cipher, SecretKey cek, int cipherMode) {
        return new CryptoCipher(cipher, this, cek, cipherMode);
    }

    public CryptoCipher createCryptoCipher(SecretKey cek, byte[] iv, int cipherMode, Provider provider) {
        try {
            Cipher cipher = null;
            if (provider != null) {
                cipher = Cipher.getInstance(getContentCipherAlgorithm(), provider);
            } else {
                cipher = Cipher.getInstance(getContentCipherAlgorithm());
            }
            cipher.init(cipherMode, cek, new IvParameterSpec(iv));
            return newCryptoCipher(cipher, cek, cipherMode);
        } catch (Exception e) {
            throw new RuntimeException("Unable to build cipher: " + e.getMessage(), e);
        }
    }
}
