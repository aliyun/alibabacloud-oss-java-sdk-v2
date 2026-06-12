package com.aliyun.sdk.service.oss2.encryption;

/**
 * Abstract interface for the master key that wraps/unwraps data encryption keys.
 * <p>
 * This is a pure key wrapping interface: {@code encrypt()} and {@code decrypt()}
 * operate on raw byte arrays without any knowledge of content encryption keys,
 * initialization vectors, or encryption algorithms.
 * <p>
 * Implementations hold the actual key material (e.g. RSA key pair) and are
 * responsible for the cryptographic operations.
 *
 * @see RsaMasterCipher
 */
public interface MasterCipher {

    /**
     * Encrypts the given plaintext bytes using the master key.
     *
     * @param plaintext the bytes to encrypt (typically a DEK or IV)
     * @return the encrypted bytes
     */
    byte[] encrypt(byte[] plaintext);

    /**
     * Decrypts the given ciphertext bytes using the master key.
     *
     * @param ciphertext the bytes to decrypt
     * @return the decrypted plaintext bytes
     */
    byte[] decrypt(byte[] ciphertext);

    /**
     * Returns the key wrap algorithm identifier, e.g. {@code "RSA/NONE/PKCS1Padding"}.
     */
    String getWrapAlgorithm();

    /**
     * Returns the material description as a JSON string, used for key rotation.
     */
    String getMatDesc();
}
