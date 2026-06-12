package com.aliyun.sdk.service.oss2.encryption.internal;

import com.aliyun.sdk.service.oss2.encryption.CipherData;
import com.aliyun.sdk.service.oss2.encryption.CipherMetadata;
import com.aliyun.sdk.service.oss2.encryption.ContentCipher;
import com.aliyun.sdk.service.oss2.encryption.ContentCipherBuilder;
import com.aliyun.sdk.service.oss2.encryption.CryptoConfiguration;
import com.aliyun.sdk.service.oss2.encryption.Envelope;
import com.aliyun.sdk.service.oss2.encryption.MasterCipher;
import com.aliyun.sdk.service.oss2.encryption.crypto.CryptoScheme;
import com.aliyun.sdk.service.oss2.utils.Base64Utils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;

/**
 * AES-CTR implementation of {@link ContentCipherBuilder}.
 */
public final class AesCtrContentCipherBuilder implements ContentCipherBuilder {

    private final MasterCipher masterCipher;
    private final CryptoScheme scheme;
    private final CryptoConfiguration cryptoConfig;
    private final CipherMetadata cipherMetadata;

    public AesCtrContentCipherBuilder(MasterCipher masterCipher, CryptoConfiguration cryptoConfig) {
        this.masterCipher = masterCipher;
        this.cryptoConfig = cryptoConfig != null ? cryptoConfig : CryptoConfiguration.DEFAULT;
        this.scheme = CryptoScheme.AES_CTR;
        this.cipherMetadata = CipherMetadata.newBuilder()
                .wrapAlgorithm(masterCipher.getWrapAlgorithm())
                .cekAlgorithm(scheme.getContentCipherAlgorithm())
                .matDesc(masterCipher.getMatDesc())
                .build();
    }

    @Override
    public ContentCipher create() {
        SecureRandom random = cryptoConfig.getSecureRandom();

        // Generate random IV (16 bytes, clear bytes 8-11 for AES-CTR mode)
        byte[] iv = new byte[scheme.getContentCipherIVLength()];
        random.nextBytes(iv);
        iv[8] = iv[9] = iv[10] = iv[11] = 0;

        // Generate 256-bit AES CEK
        SecretKey cek = generateCEK(random);

        // Wrap CEK and IV via master cipher
        byte[] encryptedKey = masterCipher.encrypt(cek.getEncoded());
        byte[] encryptedIV = masterCipher.encrypt(iv);

        CipherData cipherData = CipherData.newBuilder()
                .key(cek.getEncoded())
                .iv(iv)
                .encryptedKey(encryptedKey)
                .encryptedIV(encryptedIV)
                .build();

        return new AesCtrContentCipher(cipherData, scheme, cek, iv,
                Cipher.ENCRYPT_MODE, cryptoConfig.getContentCryptoProvider());
    }

    @Override
    public ContentCipher fromEnvelope(Envelope envelope) {
        // Unwrap CEK and IV via master cipher
        byte[] encryptedKey = Base64Utils.decodeString(envelope.cipherKey());
        byte[] encryptedIV = Base64Utils.decodeString(envelope.iv());

        byte[] plainKey = masterCipher.decrypt(encryptedKey);
        byte[] plainIV = masterCipher.decrypt(encryptedIV);

        SecretKey cek = new SecretKeySpec(plainKey, "AES");

        CipherData cipherData = CipherData.newBuilder()
                .key(plainKey)
                .iv(plainIV)
                .encryptedKey(encryptedKey)
                .encryptedIV(encryptedIV)
                .build();

        return new AesCtrContentCipher(cipherData, scheme, cek, plainIV,
                Cipher.DECRYPT_MODE, cryptoConfig.getContentCryptoProvider());
    }

    @Override
    public CipherMetadata getCipherMetadata() {
        return cipherMetadata;
    }

    @Override
    public int getAlignLen() {
        return CryptoScheme.BLOCK_SIZE;
    }

    // ==================== Private helpers ====================

    private SecretKey generateCEK(SecureRandom random) {
        try {
            KeyGenerator generator = KeyGenerator.getInstance(scheme.getKeyGeneratorAlgorithm());
            generator.init(scheme.getKeyLengthInBits(), random);
            SecretKey secretKey = generator.generateKey();
            for (int retry = 0; retry < 9; retry++) {
                if (secretKey.getEncoded()[0] != 0) {
                    return secretKey;
                }
                secretKey = generator.generateKey();
            }
            throw new RuntimeException("Failed to generate secret key after retries");
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate CEK: " + e.getMessage(), e);
        }
    }
}
