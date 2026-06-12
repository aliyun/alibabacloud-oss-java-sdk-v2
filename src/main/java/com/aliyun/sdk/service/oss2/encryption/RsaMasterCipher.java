package com.aliyun.sdk.service.oss2.encryption;

import com.aliyun.sdk.service.oss2.encryption.crypto.CryptoRuntime;
import com.aliyun.sdk.service.oss2.encryption.internal.CryptoUtils;
import com.aliyun.sdk.service.oss2.utils.Base64Utils;

import javax.crypto.Cipher;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.Provider;
import java.security.SecureRandom;
import java.security.Security;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * A {@link MasterCipher} implementation that wraps/unwraps data keys with RSA PKCS#1 v1.5.
 * <p>
 * PEM keys are parsed once at construction and reused. Either key may be {@code null}:
 * pass {@code null} publicKey for decrypt-only, or {@code null} privateKey for encrypt-only.
 * <p>
 * Additional key pairs can be registered via {@link #addKeyPair(KeyPair, Map)} for
 * decryption key rotation: if the primary key fails to decrypt, each registered
 * key is tried in insertion order.
 */
public final class RsaMasterCipher implements MasterCipher {

    public static final String WRAP_ALGORITHM = "RSA/NONE/PKCS1Padding";

    static {
        CryptoRuntime.enableBouncyCastle();
    }

    private final RSAPublicKey publicKey;
    private final RSAPrivateKey privateKey;
    private final String matDesc;

    // Additional key pairs for decryption key rotation (insertion order)
    private final LinkedHashMap<KeyPair, String> additionalKeyPairs = new LinkedHashMap<>();

    private RsaMasterCipher(RSAPublicKey publicKey, RSAPrivateKey privateKey,
                            Map<String, String> description) {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
        this.matDesc = (description == null || description.isEmpty())
                ? null
                : CryptoUtils.toJsonString(description);
    }

    /**
     * Creates an RSA master cipher from PEM-encoded keys.
     *
     * @param publicKeyPem  X.509 PEM public key, or {@code null} for decrypt-only
     * @param privateKeyPem PKCS#8 PEM private key, or {@code null} for encrypt-only
     * @param description   material description for key identification; may be {@code null}
     */
    public static RsaMasterCipher of(String publicKeyPem, String privateKeyPem,
                                     Map<String, String> description) {
        RSAPublicKey pub = (publicKeyPem != null && !publicKeyPem.isEmpty())
                ? parsePublicKeyFromPem(publicKeyPem) : null;
        RSAPrivateKey priv = (privateKeyPem != null && !privateKeyPem.isEmpty())
                ? parsePrivateKeyFromPemPKCS8(privateKeyPem) : null;
        return new RsaMasterCipher(pub, priv, description);
    }

    /**
     * Creates an RSA master cipher from a {@link KeyPair}.
     *
     * @param keyPair     the RSA key pair
     * @param description material description; may be {@code null}
     */
    public static RsaMasterCipher of(KeyPair keyPair, Map<String, String> description) {
        RSAPublicKey pub = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey priv = (RSAPrivateKey) keyPair.getPrivate();
        return new RsaMasterCipher(pub, priv, description);
    }

    /**
     * Adds an additional key pair for decryption key rotation.
     *
     * @param keyPair     the RSA key pair
     * @param description material description for this key pair
     */
    public void addKeyPair(KeyPair keyPair, Map<String, String> description) {
        String desc = (description == null || description.isEmpty())
                ? null
                : CryptoUtils.toJsonString(description);
        additionalKeyPairs.put(keyPair, desc);
    }

    @Override
    public byte[] encrypt(byte[] plaintext) {
        if (publicKey == null) {
            throw new IllegalStateException("Public key is not configured for encryption");
        }
        try {
            Cipher cipher = Cipher.getInstance(WRAP_ALGORITHM, getBCProvider());
            cipher.init(Cipher.ENCRYPT_MODE, publicKey, new SecureRandom());
            return cipher.doFinal(plaintext);
        } catch (Exception e) {
            throw new RuntimeException("Unable to encrypt with RSA public key: " + e.getMessage(), e);
        }
    }

    @Override
    public byte[] decrypt(byte[] ciphertext) {
        // Try primary key first
        if (privateKey != null) {
            try {
                return doDecrypt(privateKey, ciphertext);
            } catch (Exception ignored) {
                // Fall through to additional key pairs
            }
        }

        // Try additional key pairs (for key rotation)
        for (KeyPair kp : additionalKeyPairs.keySet()) {
            try {
                return doDecrypt((Key) kp.getPrivate(), ciphertext);
            } catch (Exception ignored) {
                // Try next
            }
        }

        throw new RuntimeException("Unable to decrypt with any available RSA private key");
    }

    private byte[] doDecrypt(Key key, byte[] ciphertext) throws Exception {
        Cipher cipher = Cipher.getInstance(WRAP_ALGORITHM, getBCProvider());
        cipher.init(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(ciphertext);
    }

    private static Provider getBCProvider() {
        Provider provider = Security.getProvider("BC");
        if (provider == null) {
            throw new RuntimeException("BouncyCastle provider not available");
        }
        return provider;
    }

    @Override
    public String getWrapAlgorithm() {
        return WRAP_ALGORITHM;
    }

    @Override
    public String getMatDesc() {
        return matDesc;
    }

    // ==================== PEM Parsing Utilities ====================

    /**
     * Parses an RSA public key from an X.509 PEM string.
     */
    public static RSAPublicKey parsePublicKeyFromPem(String pem) {
        try {
            String adjusted = pem
                    .replace("-----BEGIN PUBLIC KEY-----", "")
                    .replace("-----BEGIN RSA PUBLIC KEY-----", "")
                    .replace("-----END PUBLIC KEY-----", "")
                    .replace("-----END RSA PUBLIC KEY-----", "")
                    .replace("\n", "");

            byte[] buffer = Base64Utils.decodeString(adjusted);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
            return (RSAPublicKey) keyFactory.generatePublic(keySpec);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse RSA public key from PEM: " + e.getMessage(), e);
        }
    }

    /**
     * Parses an RSA private key from a PKCS#8 PEM string.
     */
    public static RSAPrivateKey parsePrivateKeyFromPemPKCS8(String pem) {
        try {
            String adjusted = pem
                    .replace("-----BEGIN PRIVATE KEY-----", "")
                    .replace("-----BEGIN RSA PRIVATE KEY-----", "")
                    .replace("-----END PRIVATE KEY-----", "")
                    .replace("-----END RSA PRIVATE KEY-----", "")
                    .replace("\n", "");

            byte[] buffer = Base64Utils.decodeString(adjusted);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse RSA private key from PKCS#8 PEM: " + e.getMessage(), e);
        }
    }

    /**
     * Parses an RSA private key from a PKCS#1 PEM string.
     * <p>
     * Requires Bouncy Castle provider.
     */
    public static RSAPrivateKey parsePrivateKeyFromPemPKCS1(String pem) {
        try {
            String adjusted = pem
                    .replace("-----BEGIN PRIVATE KEY-----", "")
                    .replace("-----BEGIN RSA PRIVATE KEY-----", "")
                    .replace("-----END PRIVATE KEY-----", "")
                    .replace("-----END RSA PRIVATE KEY-----", "")
                    .replace("\n", "");

            CryptoRuntime.enableBouncyCastle();

            byte[] buffer = Base64Utils.decodeString(adjusted);
            RSAPrivateKeySpec keySpec = CryptoRuntime.convertPemPKCS1ToPrivateKey(buffer);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse RSA private key from PKCS#1 PEM: " + e.getMessage(), e);
        }
    }
}
