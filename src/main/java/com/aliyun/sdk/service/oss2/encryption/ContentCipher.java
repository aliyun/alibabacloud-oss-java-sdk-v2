package com.aliyun.sdk.service.oss2.encryption;

import java.io.InputStream;

/**
 * Per-object cipher wrapper for encrypting or decrypting content.
 * <p>
 * Each instance is bound to a specific cipher mode (encrypt or decrypt) and holds
 * the per-object key material. Supports {@link #clone()} for multipart upload and
 * {@link #seekTo(long)} for range get and multipart part positioning.
 *
 * @see com.aliyun.sdk.service.oss2.encryption.internal.AesCtrContentCipher
 */
public interface ContentCipher {

    /**
     * Wraps the given input stream with encryption, producing ciphertext as it is read.
     */
    InputStream encryptContent(InputStream body);

    /**
     * Wraps the given input stream with decryption, producing plaintext as it is read.
     */
    InputStream decryptContent(InputStream body);

    /**
     * Encrypts the given plaintext byte array.
     */
    byte[] encryptBytes(byte[] plaintext);

    /**
     * Decrypts the given ciphertext byte array.
     */
    byte[] decryptBytes(byte[] ciphertext);

    /**
     * Returns the per-object key material.
     */
    CipherData getCipherData();

    /**
     * Returns a deep copy of this cipher with an independent internal cipher state.
     * Used to create per-part ciphers in multipart upload.
     */
    ContentCipher clone();

    /**
     * Advances the cipher stream to the given byte offset by adjusting the IV.
     * Used for range get and multipart part positioning.
     *
     * @param offset the byte offset to seek to (must be a multiple of {@link #getAlignLen()})
     */
    void seekTo(long offset);

    /**
     * Returns the block alignment length in bytes (e.g. 16 for AES).
     */
    int getAlignLen();
}
