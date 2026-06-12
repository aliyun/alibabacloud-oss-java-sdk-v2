package com.aliyun.sdk.service.oss2.encryption.internal;

import com.aliyun.sdk.service.oss2.encryption.CipherData;
import com.aliyun.sdk.service.oss2.encryption.ContentCipher;
import com.aliyun.sdk.service.oss2.encryption.crypto.CryptoCipher;
import com.aliyun.sdk.service.oss2.encryption.crypto.CryptoScheme;
import com.aliyun.sdk.service.oss2.encryption.internal.CipherInputStream;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import java.io.InputStream;
import java.security.Provider;
import java.util.Arrays;

/**
 * AES-CTR implementation of {@link ContentCipher}.
 */
public final class AesCtrContentCipher implements ContentCipher {

    private final CipherData cipherData;
    private final CryptoScheme scheme;
    private final SecretKey cek;
    private byte[] iv;
    private final int cipherMode;
    private final Provider provider;
    private CryptoCipher internalCipher;

    AesCtrContentCipher(CipherData cipherData, CryptoScheme scheme, SecretKey cek,
                        byte[] iv, int cipherMode, Provider provider) {
        this.cipherData = cipherData;
        this.scheme = scheme;
        this.cek = cek;
        this.iv = Arrays.copyOf(iv, iv.length);
        this.cipherMode = cipherMode;
        this.provider = provider;
    }

    @Override
    public InputStream encryptContent(InputStream body) {
        ensureCipher(Cipher.ENCRYPT_MODE);
        return new CipherInputStream(body, internalCipher);
    }

    @Override
    public InputStream decryptContent(InputStream body) {
        ensureCipher(Cipher.DECRYPT_MODE);
        return new CipherInputStream(body, internalCipher);
    }

    @Override
    public byte[] encryptBytes(byte[] plaintext) {
        ensureCipher(Cipher.ENCRYPT_MODE);
        return doCrypt(plaintext);
    }

    @Override
    public byte[] decryptBytes(byte[] ciphertext) {
        ensureCipher(Cipher.DECRYPT_MODE);
        return doCrypt(ciphertext);
    }

    @Override
    public CipherData getCipherData() {
        return cipherData;
    }

    @Override
    public AesCtrContentCipher clone() {
        return new AesCtrContentCipher(cipherData, scheme, cek, iv, cipherMode, provider);
    }

    @Override
    public void seekTo(long offset) {
        long blockOffset = offset / CryptoScheme.BLOCK_SIZE;
        if (blockOffset > 0) {
            iv = CryptoScheme.incrementBlocks(Arrays.copyOf(iv, iv.length), blockOffset);
        }
        internalCipher = null; // Force recreation with new IV on next use
    }

    @Override
    public int getAlignLen() {
        return CryptoScheme.BLOCK_SIZE;
    }

    // ==================== Private helpers ====================

    private void ensureCipher(int expectedMode) {
        if (cipherMode != expectedMode) {
            throw new IllegalStateException(
                    "ContentCipher was created for " + (cipherMode == Cipher.ENCRYPT_MODE ? "encryption" : "decryption")
                            + " but " + (expectedMode == Cipher.ENCRYPT_MODE ? "encrypt" : "decrypt") + " was called");
        }
        if (internalCipher == null) {
            internalCipher = scheme.createCryptoCipher(cek, iv, cipherMode, provider);
        }
    }

    private byte[] doCrypt(byte[] input) {
        byte[] result = internalCipher.update(input, 0, input.length);
        byte[] tail;
        try {
            tail = internalCipher.doFinal();
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException("Cipher operation failed: " + e.getMessage(), e);
        }
        if (tail == null || tail.length == 0) {
            return result != null ? result : new byte[0];
        }
        if (result == null || result.length == 0) {
            return tail;
        }
        byte[] combined = new byte[result.length + tail.length];
        System.arraycopy(result, 0, combined, 0, result.length);
        System.arraycopy(tail, 0, combined, result.length, tail.length);
        return combined;
    }
}
