package com.aliyun.sdk.service.oss2.encryption.internal;

import com.aliyun.sdk.service.oss2.encryption.CipherData;
import com.aliyun.sdk.service.oss2.encryption.ContentCipher;
import com.aliyun.sdk.service.oss2.encryption.ContentCipherBuilder;
import com.aliyun.sdk.service.oss2.encryption.CryptoConfiguration;
import com.aliyun.sdk.service.oss2.encryption.EncryptionConfiguration;
import com.aliyun.sdk.service.oss2.encryption.Envelope;
import com.aliyun.sdk.service.oss2.encryption.MasterCipher;
import com.aliyun.sdk.service.oss2.encryption.RsaMasterCipher;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

public class CipherBinaryDataTest {

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

    private static final String PUBLIC_KEY_PEM_X509 =
            "-----BEGIN PUBLIC KEY-----\n"
                    + "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC4PMUWCK9kNTa8fHuZgc1aXfcz\n"
                    + "rO1D6tyOkyhjdxa7LnRE5nXURTjla3YWbjqgPXHOKbcd6SSY0RWq8mZ5zLyqvUJP\n"
                    + "9QsuY5k5Ic1sumHCJDIYfxu5qnHx28Zm6I/QWnNzXRioiK9+KfyOcKoHnkEbezu9\n"
                    + "3XPCYYXuBzuuPHTC/wIDAQAB\n"
                    + "-----END PUBLIC KEY-----";

    private ContentCipherBuilder cipherBuilder;
    private byte[] plainBytes;

    @BeforeEach
    void setUp() {
        RSAPublicKey pub = RsaMasterCipher.parsePublicKeyFromPem(PUBLIC_KEY_PEM_X509);
        RSAPrivateKey priv = RsaMasterCipher.parsePrivateKeyFromPemPKCS1(PRIVATE_KEY_PEM_PKCS1);
        MasterCipher masterCipher = RsaMasterCipher.of(new KeyPair(pub, priv), null);

        EncryptionConfiguration encConfig = EncryptionConfiguration.newBuilder()
                .masterCipher(masterCipher)
                .build();

        CryptoConfiguration cryptoConfig = CryptoConfiguration.DEFAULT;
        cipherBuilder = new AesCtrContentCipherBuilder(encConfig.masterCipher(), cryptoConfig);

        plainBytes = new byte[1024];
        new Random(42).nextBytes(plainBytes);
    }

    @Test
    void toStream_producesEncryptedContent_decryptable() {
        ContentCipher encCipher = cipherBuilder.create();
        BinaryData original = BinaryData.fromBytes(plainBytes);
        CipherBinaryData encBody = new CipherBinaryData(original, encCipher);

        byte[] encrypted = readAll(encBody.toStream());
        assertThat(encrypted).isNotEqualTo(plainBytes);
        assertThat(encrypted.length).isEqualTo(plainBytes.length);

        // Decrypt and verify
        byte[] decrypted = decryptBytes(encCipher, encrypted);
        assertThat(decrypted).isEqualTo(plainBytes);
    }

    @Test
    void toBytes_producesEncryptedContent_decryptable() {
        ContentCipher encCipher = cipherBuilder.create();
        BinaryData original = BinaryData.fromBytes(plainBytes);
        CipherBinaryData encBody = new CipherBinaryData(original, encCipher);

        byte[] encrypted = encBody.toBytes();
        assertThat(encrypted).isNotEqualTo(plainBytes);
        assertThat(encrypted.length).isEqualTo(plainBytes.length);

        byte[] decrypted = decryptBytes(encCipher, encrypted);
        assertThat(decrypted).isEqualTo(plainBytes);
    }

    @Test
    void toByteBuffer_producesEncryptedContent_decryptable() {
        ContentCipher encCipher = cipherBuilder.create();
        BinaryData original = BinaryData.fromBytes(plainBytes);
        CipherBinaryData encBody = new CipherBinaryData(original, encCipher);

        ByteBuffer buf = encBody.toByteBuffer();
        assertThat(buf.isReadOnly()).isTrue();

        byte[] encrypted = new byte[buf.remaining()];
        buf.get(encrypted);
        assertThat(encrypted).isNotEqualTo(plainBytes);

        byte[] decrypted = decryptBytes(encCipher, encrypted);
        assertThat(decrypted).isEqualTo(plainBytes);
    }

    @Test
    void toByteChannel_producesEncryptedContent_decryptable() throws Exception {
        ContentCipher encCipher = cipherBuilder.create();
        BinaryData original = BinaryData.fromBytes(plainBytes);
        CipherBinaryData encBody = new CipherBinaryData(original, encCipher);

        ReadableByteChannel channel = encBody.toByteChannel();
        InputStream is = Channels.newInputStream(channel);
        byte[] encrypted = readAll(is);
        assertThat(encrypted).isNotEqualTo(plainBytes);

        byte[] decrypted = decryptBytes(encCipher, encrypted);
        assertThat(decrypted).isEqualTo(plainBytes);
    }

    @Test
    void toString_producesEncryptedString() {
        ContentCipher encCipher = cipherBuilder.create();
        String plainText = "Hello, CipherBinaryData!";
        BinaryData original = BinaryData.fromString(plainText);
        CipherBinaryData encBody = new CipherBinaryData(original, encCipher);

        String encrypted = encBody.toString();
        assertThat(encrypted).isNotEqualTo(plainText);
    }

    @Test
    void getLength_delegatesToOriginal() {
        ContentCipher encCipher = cipherBuilder.create();
        BinaryData original = BinaryData.fromStream(
                new ByteArrayInputStream(plainBytes), (long) plainBytes.length);
        CipherBinaryData encBody = new CipherBinaryData(original, encCipher);

        assertThat(encBody.getLength()).isEqualTo((long) plainBytes.length);
    }

    @Test
    void getLength_nullWhenOriginalHasNoLength() {
        ContentCipher encCipher = cipherBuilder.create();
        BinaryData original = BinaryData.fromStream(new ByteArrayInputStream(plainBytes));
        CipherBinaryData encBody = new CipherBinaryData(original, encCipher);

        assertThat(encBody.getLength()).isNull();
    }

    @Test
    void isReplayable_delegatesToOriginal() {
        ContentCipher encCipher = cipherBuilder.create();

        // ByteArrayInputStream supports mark/reset → replayable
        BinaryData replayable = BinaryData.fromStream(new ByteArrayInputStream(plainBytes));
        assertThat(new CipherBinaryData(replayable, encCipher).isReplayable()).isTrue();

        // byte[] based → always replayable
        BinaryData fromBytes = BinaryData.fromBytes(plainBytes);
        assertThat(new CipherBinaryData(fromBytes, encCipher).isReplayable()).isTrue();
    }

    @Test
    void replayability_multipleToStreamCalls_produceSameEncryptedContent() {
        ContentCipher encCipher = cipherBuilder.create();
        BinaryData original = BinaryData.fromBytes(plainBytes);
        CipherBinaryData encBody = new CipherBinaryData(original, encCipher);

        // Call toStream multiple times — each should produce identical encrypted output
        byte[] encrypted1 = readAll(encBody.toStream());
        byte[] encrypted2 = readAll(encBody.toStream());
        byte[] encrypted3 = encBody.toBytes();

        assertThat(encrypted1).isEqualTo(encrypted2);
        assertThat(encrypted2).isEqualTo(encrypted3);

        // All should decrypt to the original
        assertThat(decryptBytes(encCipher, encrypted1)).isEqualTo(plainBytes);
        assertThat(decryptBytes(encCipher, encrypted2)).isEqualTo(plainBytes);
        assertThat(decryptBytes(encCipher, encrypted3)).isEqualTo(plainBytes);
    }

    @Test
    void variousSizes_allConversionsRoundTrip() {
        int[] sizes = {0, 1, 15, 16, 17, 100, 255, 256, 1024, 4096};
        for (int size : sizes) {
            byte[] data = new byte[size];
            new Random(size).nextBytes(data);

            ContentCipher encCipher = cipherBuilder.create();
            BinaryData original = BinaryData.fromBytes(data);
            CipherBinaryData encBody = new CipherBinaryData(original, encCipher);

            // toStream
            byte[] enc1 = readAll(encBody.toStream());
            assertThat(decryptBytes(encCipher, enc1)).as("toStream size=%d", size).isEqualTo(data);

            // toBytes
            byte[] enc2 = encBody.toBytes();
            assertThat(decryptBytes(encCipher, enc2)).as("toBytes size=%d", size).isEqualTo(data);

            // toByteBuffer
            ByteBuffer buf = encBody.toByteBuffer();
            byte[] enc3 = new byte[buf.remaining()];
            buf.get(enc3);
            assertThat(decryptBytes(encCipher, enc3)).as("toByteBuffer size=%d", size).isEqualTo(data);

            // toByteChannel
            try {
                InputStream is = Channels.newInputStream(encBody.toByteChannel());
                byte[] enc4 = readAll(is);
                assertThat(decryptBytes(encCipher, enc4)).as("toByteChannel size=%d", size).isEqualTo(data);
            } catch (Exception e) {
                throw new RuntimeException("toByteChannel failed for size=" + size, e);
            }
        }
    }

    // ==================== Helpers ====================

    private byte[] decryptBytes(ContentCipher encCipher, byte[] encrypted) {
        // Build a decrypt-mode cipher from the same envelope
        CipherData cd = encCipher.getCipherData();
        Envelope envelope = Envelope.newBuilder()
                .cipherKey(com.aliyun.sdk.service.oss2.utils.Base64Utils.encodeToString(cd.encryptedKey()))
                .iv(com.aliyun.sdk.service.oss2.utils.Base64Utils.encodeToString(cd.encryptedIV()))
                .cekAlg(cipherBuilder.getCipherMetadata().cekAlgorithm())
                .wrapAlg(cipherBuilder.getCipherMetadata().wrapAlgorithm())
                .build();
        ContentCipher decCipher = cipherBuilder.fromEnvelope(envelope);
        return decCipher.decryptBytes(encrypted);
    }

    private static byte[] readAll(InputStream is) {
        try {
            ByteArrayOutputStream buf = new ByteArrayOutputStream();
            byte[] tmp = new byte[8192];
            int n;
            while ((n = is.read(tmp)) != -1) {
                buf.write(tmp, 0, n);
            }
            return buf.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
