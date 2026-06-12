package com.aliyun.sdk.service.oss2.encryption;

import org.junit.Assert;
import org.junit.Test;

import javax.crypto.Cipher;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class RSAEncryptionUnitTest {

    private static final String PUBLIC_KEY_PEM_X509 =
            "-----BEGIN PUBLIC KEY-----\n"
                    + "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC4PMUWCK9kNTa8fHuZgc1aXfcz\n"
                    + "rO1D6tyOkyhjdxa7LnRE5nXURTjla3YWbjqgPXHOKbcd6SSY0RWq8mZ5zLyqvUJP\n"
                    + "9QsuY5k5Ic1sumHCJDIYfxu5qnHx28Zm6I/QWnNzXRioiK9+KfyOcKoHnkEbezu9\n"
                    + "3XPCYYXuBzuuPHTC/wIDAQAB\n"
                    + "-----END PUBLIC KEY-----";

    private static final String PRIVATE_KEY_PEM_PKCS8 =
            "-----BEGIN PRIVATE KEY-----\n"
                    + "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBALg8xRYIr2Q1Nrx8\n"
                    + "e5mBzVpd9zOs7UPq3I6TKGN3FrsudETmddRFOOVrdhZuOqA9cc4ptx3pJJjRFary\n"
                    + "ZnnMvKq9Qk/1Cy5jmTkhzWy6YcIkMhh/G7mqcfHbxmboj9Bac3NdGKiIr34p/I5w\n"
                    + "qgeeQRt7O73dc8Jhhe4HO648dML/AgMBAAECgYAeuqwYv7wZV7LYf17SPM82mmOn\n"
                    + "79jnMh41faAH7w4QjHACKfpPPHh/84uPtDT9EyKPQ2/ygjeDcaROjf2/pQF2pIUr\n"
                    + "YyB5TPsvLWZQ4FY6GBXKhXf5A7oT2Qors9zuPeJIUzhx2pjFYs614Pj+bCLg9JlW\n"
                    + "aF5oqRE+nPNdEqkzIQJBANt7kTn2xg9G6tHzyQ7TAACujmORKpUmrmoUUA3BhvQA\n"
                    + "8A5A8yp07Yh4klL5gXO7rrhaE53sKGuLXyqrxxZFVFECQQDW4/+SoyPi9Yl6yksC\n"
                    + "9RGSIJ/Jv5HUj1qyc1SoiE5wk2OiAth/HmQ8txgEJEcwCoo2Q18f9t4N0nibvLRB\n"
                    + "+l5PAkAbVnnRUXZ8AqZO/mGFsixm6Vcc+cDnEQladys9e2R20gMUk2x2VlgbzoDT\n"
                    + "Svaf1rm9hqK44ehq9NImu3yxvnLxAkBu+0vIONdU5Qi+0PFSsq0DcjP0Jysyw2LN\n"
                    + "HQFRFSyluYlQZ/XWGSUdslYF9ZKKfjcJdVwQjxf5vYSqshfKp3rDAkB43xSmURn6\n"
                    + "UcG5LbTnFW40MYsy9S7J9LRk//1cjspddnimpVDR8On/cIgx5jETN8mnHYQcuRJI\n"
                    + "t+jBhBc4kRJr\n"
                    + "-----END PRIVATE KEY-----";

    private static final String PRIVATE_KEY_PEM_PKCS1 =
            "-----BEGIN RSA PRIVATE KEY-----\n"
                    + "MIICWwIBAAKBgQC4PMUWCK9kNTa8fHuZgc1aXfczrO1D6tyOkyhjdxa7LnRE5nXU\n"
                    + "RTjla3YWbjqgPXHOKbcd6SSY0RWq8mZ5zLyqvUJP9QsuY5k5Ic1sumHCJDIYfxu5\n"
                    + "qnHx28Zm6I/QWnNzXRioiK9+KfyOcKoHnkEbezu93XPCYYXuBzuuPHTC/wIDAQAB\n"
                    + "AoGAHrqsGL+8GVey2H9e0jzPNppjp+/Y5zIeNX2gB+8OEIxwAin6Tzx4f/OLj7Q0\n"
                    + "/RMij0Nv8oI3g3GkTo39v6UBdqSFK2MgeUz7Ly1mUOBWOhgVyoV3+QO6E9kKK7Pc\n"
                    + "7j3iSFM4cdqYxWLOteD4/mwi4PSZVmheaKkRPpzzXRKpMyECQQDbe5E59sYPRurR\n"
                    + "88kO0wAAro5jkSqVJq5qFFANwYb0APAOQPMqdO2IeJJS+YFzu664WhOd7Chri18q\n"
                    + "q8cWRVRRAkEA1uP/kqMj4vWJespLAvURkiCfyb+R1I9asnNUqIhOcJNjogLYfx5k\n"
                    + "PLcYBCRHMAqKNkNfH/beDdJ4m7y0QfpeTwJAG1Z50VF2fAKmTv5hhbIsZulXHPnA\n"
                    + "5xEJWncrPXtkdtIDFJNsdlZYG86A00r2n9a5vYaiuOHoavTSJrt8sb5y8QJAbvtL\n"
                    + "yDjXVOUIvtDxUrKtA3Iz9CcrMsNizR0BURUspbmJUGf11hklHbJWBfWSin43CXVc\n"
                    + "EI8X+b2EqrIXyqd6wwJAeN8UplEZ+lHBuS205xVuNDGLMvUuyfS0ZP/9XI7KXXZ4\n"
                    + "pqVQ0fDp/3CIMeYxEzfJpx2EHLkSSLfowYQXOJESaw==\n"
                    + "-----END RSA PRIVATE KEY-----";

    private final String PLAIN_TEXT = "kdnsknshiwonrjsn23e1vdjknvlsfnsl34ihsohnqm92u32jns.msl082mjk73643dns";

    @Test
    public void testUsePrivateKeyPKCS8() {
        try {
            final RSAPrivateKey privateKey = RsaMasterCipher.parsePrivateKeyFromPemPKCS8(PRIVATE_KEY_PEM_PKCS8);
            final RSAPublicKey publicKey = RsaMasterCipher.parsePublicKeyFromPem(PUBLIC_KEY_PEM_X509);

            KeyPair keyPair = new KeyPair(publicKey, privateKey);

            byte[] encryptedData = encrypt(keyPair.getPublic(), PLAIN_TEXT.getBytes());
            byte[] decryptedData = decrypt(keyPair.getPrivate(), encryptedData);
            String decryptedStr = new String(decryptedData);
            assertEquals(PLAIN_TEXT, decryptedStr);
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void testUsePrivateKeyPKCS1() {
        try {
            final RSAPrivateKey privateKey = RsaMasterCipher.parsePrivateKeyFromPemPKCS1(PRIVATE_KEY_PEM_PKCS1);
            final RSAPublicKey publicKey = RsaMasterCipher.parsePublicKeyFromPem(PUBLIC_KEY_PEM_X509);

            KeyPair keyPair = new KeyPair(publicKey, privateKey);

            byte[] encryptedData = encrypt(keyPair.getPublic(), PLAIN_TEXT.getBytes());
            byte[] decryptedData = decrypt(keyPair.getPrivate(), encryptedData);
            String decryptedStr = new String(decryptedData);
            Assert.assertEquals(PLAIN_TEXT, decryptedStr);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void testUsePrivateKeyPKCS1AndPKCS8() {
        try {
            final RSAPrivateKey privateKeyPKCS1 = RsaMasterCipher.parsePrivateKeyFromPemPKCS1(PRIVATE_KEY_PEM_PKCS1);
            final RSAPublicKey publicKey = RsaMasterCipher.parsePublicKeyFromPem(PUBLIC_KEY_PEM_X509);
            final RSAPrivateKey privateKeyPKCS8 = RsaMasterCipher.parsePrivateKeyFromPemPKCS8(PRIVATE_KEY_PEM_PKCS8);

            // encrypt by public key
            byte[] encryptedData = encrypt(publicKey, PLAIN_TEXT.getBytes());

            // decrypt by private key pkcs1
            byte[] decryptedData = decrypt(privateKeyPKCS1, encryptedData);
            String decryptedStrPKCS1 = new String(decryptedData);
            Assert.assertEquals(PLAIN_TEXT, decryptedStrPKCS1);

            // decrypt by private key pkcs8
            decryptedData = decrypt(privateKeyPKCS8, encryptedData);
            String decryptedStrPKCS8 = new String(decryptedData);
            Assert.assertEquals(decryptedStrPKCS1, decryptedStrPKCS8);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void testRsaMasterCipherRoundTrip() {
        Map<String, String> desc = new HashMap<>();
        desc.put("key", "value");

        RSAPublicKey pub = RsaMasterCipher.parsePublicKeyFromPem(PUBLIC_KEY_PEM_X509);
        RSAPrivateKey priv = RsaMasterCipher.parsePrivateKeyFromPemPKCS1(PRIVATE_KEY_PEM_PKCS1);
        RsaMasterCipher masterCipher = RsaMasterCipher.of(new KeyPair(pub, priv), desc);

        byte[] plaintext = PLAIN_TEXT.getBytes();
        byte[] encrypted = masterCipher.encrypt(plaintext);
        byte[] decrypted = masterCipher.decrypt(encrypted);

        assertArrayEquals(plaintext, decrypted);
        assertEquals("RSA/NONE/PKCS1Padding", masterCipher.getWrapAlgorithm());
        Assert.assertNotNull(masterCipher.getMatDesc());
    }

    @Test
    public void testRsaMasterCipherKeyRotation() {
        RSAPublicKey pub = RsaMasterCipher.parsePublicKeyFromPem(PUBLIC_KEY_PEM_X509);
        RSAPrivateKey priv = RsaMasterCipher.parsePrivateKeyFromPemPKCS1(PRIVATE_KEY_PEM_PKCS1);
        RsaMasterCipher masterCipher = RsaMasterCipher.of(new KeyPair(pub, priv), null);

        // Create a second key pair for rotation (same key, different description)
        KeyPair keyPair2 = new KeyPair(pub, priv);
        Map<String, String> desc2 = new HashMap<>();
        desc2.put("version", "2");
        masterCipher.addKeyPair(keyPair2, desc2);

        // Encrypt with primary, decrypt should work
        byte[] plaintext = "rotation test data".getBytes();
        byte[] encrypted = masterCipher.encrypt(plaintext);
        byte[] decrypted = masterCipher.decrypt(encrypted);
        assertArrayEquals(plaintext, decrypted);
    }

    private byte[] encrypt(PublicKey publicKey, byte[] plainData) throws Exception {
        if (publicKey == null) {
            throw new Exception("public key is null.");
        }

        Provider provider = CryptoTestUtils.getBouncyCastleProvider();
        Assert.assertNotNull(provider);
        Cipher cipher = Cipher.getInstance("RSA/NONE/PKCS1Padding", provider);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(plainData);
    }

    private byte[] decrypt(PrivateKey privateKey, byte[] cipherData) throws Exception {
        if (privateKey == null) {
            throw new Exception("private key is null.");
        }
        Provider provider = CryptoTestUtils.getBouncyCastleProvider();
        Assert.assertNotNull(provider);
        Cipher cipher = Cipher.getInstance("RSA/NONE/PKCS1Padding", provider);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(cipherData);
    }

}
