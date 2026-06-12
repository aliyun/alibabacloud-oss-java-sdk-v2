package com.aliyun.sdk.service.oss2.encryption;

/**
 * Factory that creates {@link ContentCipher} instances for envelope encryption.
 * <p>
 * On upload: {@link #create()} generates fresh key material (CEK + IV), wraps
 * them via the {@link MasterCipher}, and returns an encrypting ContentCipher.
 * <p>
 * On download: {@link #fromEnvelope(Envelope)} restores key material from
 * stored headers, unwraps via the MasterCipher, and returns a decrypting ContentCipher.
 *
 * @see com.aliyun.sdk.service.oss2.encryption.internal.AesCtrContentCipherBuilder
 */
public interface ContentCipherBuilder {

    /**
     * Creates a new ContentCipher for encryption with freshly generated key material.
     */
    ContentCipher create();

    /**
     * Creates a ContentCipher for decryption from a stored crypto envelope.
     *
     * @param envelope the envelope parsed from object metadata headers
     */
    ContentCipher fromEnvelope(Envelope envelope);

    /**
     * Returns the cipher metadata (wrap algorithm, CEK algorithm, material description).
     */
    CipherMetadata getCipherMetadata();

    /**
     * Returns the block alignment length in bytes (16 for AES).
     */
    int getAlignLen();
}
