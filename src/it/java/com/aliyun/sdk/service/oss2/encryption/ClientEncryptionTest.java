package com.aliyun.sdk.service.oss2.encryption;

import com.aliyun.sdk.service.oss2.TestBase;
import com.aliyun.sdk.service.oss2.internal.TestUtils;
import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

public class ClientEncryptionTest extends TestBase {

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

    // Different RSA key pair for GetObject_DifferentKey test
    private static final String OTHER_PRIVATE_KEY_PEM_PKCS1 =
            "-----BEGIN RSA PRIVATE KEY-----\n"
                    + "MIICWwIBAAKBgQCokfiAVXXf5ImFzKDw+XO/UByW6mse2QsIgz3ZwBtMNu59fR5z\n"
                    + "ttSx+8fB7vR4CN3bTztrP9A6bjoN0FFnhlQ3vNJC5MFO1PByrE/MNd5AAfSVba93\n"
                    + "I6sx8NSk5MzUCA4NJzAUqYOEWGtGBcom6kEF6MmR1EKib1Id8hpooY5xaQIDAQAB\n"
                    + "AoGAOPUZgkNeEMinrw31U3b2JS5sepG6oDG2CKpPu8OtdZMaAkzEfVTJiVoJpP2Y\n"
                    + "nPZiADhFW3e0ZAnak9BPsSsySRaSNmR465cG9tbqpXFKh9Rp/sCPo4Jq2n65yood\n"
                    + "JBrnGr6/xhYvNa14sQ6xjjfSgRNBSXD1XXNF4kALwgZyCAECQQDV7t4bTx9FbEs5\n"
                    + "36nAxPsPM6aACXaOkv6d9LXI7A0J8Zf42FeBV6RK0q7QG5iNNd1WJHSXIITUizVF\n"
                    + "6aX5NnvFAkEAybeXNOwUvYtkgxF4s28s6gn11c5HZw4/a8vZm2tXXK/QfTQrJVXp\n"
                    + "VwxmSr0FAajWAlcYN/fGkX1pWA041CKFVQJAG08ozzekeEpAuByTIOaEXgZr5MBQ\n"
                    + "gBbHpgZNBl8Lsw9CJSQI15wGfv6yDiLXsH8FyC9TKs+d5Tv4Cvquk0efOQJAd9OC\n"
                    + "lCKFs48hdyaiz9yEDsc57PdrvRFepVdj/gpGzD14mVerJbOiOF6aSV19ot27u4on\n"
                    + "Td/3aifYs0CveHzFPQJAWb4LCDwqLctfzziG7/S7Z74gyq5qZF4FUElOAZkz718E\n"
                    + "yZvADwuz/4aK0od0lX9c4Jp7Mo5vQ4TvdoBnPuGoyw==\n"
                    + "-----END RSA PRIVATE KEY-----";

    private static final String OTHER_PUBLIC_KEY_PEM_X509 =
            "-----BEGIN PUBLIC KEY-----\n"
                    + "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCokfiAVXXf5ImFzKDw+XO/UByW\n"
                    + "6mse2QsIgz3ZwBtMNu59fR5zttSx+8fB7vR4CN3bTztrP9A6bjoN0FFnhlQ3vNJC\n"
                    + "5MFO1PByrE/MNd5AAfSVba93I6sx8NSk5MzUCA4NJzAUqYOEWGtGBcom6kEF6MmR\n"
                    + "1EKib1Id8hpooY5xaQIDAQAB\n"
                    + "-----END PUBLIC KEY-----";

    // Compat key for cross-SDK decryption (same as C++ SDK's kCompatPrivateKey)
    private static final String COMPAT_PRIVATE_KEY_PEM_PKCS1 = OTHER_PRIVATE_KEY_PEM_PKCS1;

    private OSSEncryptionClient createEncryptionClient() {
        RSAPublicKey pub = RsaMasterCipher.parsePublicKeyFromPem(PUBLIC_KEY_PEM_X509);
        RSAPrivateKey priv = RsaMasterCipher.parsePrivateKeyFromPemPKCS1(PRIVATE_KEY_PEM_PKCS1);
        MasterCipher masterCipher = RsaMasterCipher.of(new KeyPair(pub, priv), null);

        EncryptionConfiguration encConfig = EncryptionConfiguration.newBuilder()
                .masterCipher(masterCipher)
                .build();

        return new OSSEncryptionClient(getDefaultClient(), encConfig);
    }

    private String genObjectName() {
        return OJBJECT_NAME_PREFIX + "encryption-" + new Random().nextInt(10000);
    }

    @Test
    public void testPutGet_smallObject() {
        OSSEncryptionClient encClient = createEncryptionClient();
        String objectName = genObjectName();

        byte[] data = "Hello, client-side encryption test!".getBytes();

        // Put encrypted object
        PutObjectResult putResult = encClient.putObject(PutObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .body(BinaryData.fromStream(new ByteArrayInputStream(data)))
                .build());
        assertThat(putResult.statusCode()).isEqualTo(200);

        // Verify object has encryption headers via headObject
        HeadObjectResult headResult = getDefaultClient().headObject(HeadObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .build());
        assertThat(headResult.metadata()).containsKey("client-side-encryption-key");
        assertThat(headResult.metadata()).containsKey("client-side-encryption-start");
        assertThat(headResult.metadata().get("client-side-encryption-cek-alg")).isEqualTo("AES/CTR/NoPadding");

        // Get and decrypt
        GetObjectResult getResult = encClient.getObject(GetObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .build());
        assertThat(getResult.statusCode()).isEqualTo(200);

        byte[] decrypted = readStream(getResult.body());
        assertThat(decrypted).isEqualTo(data);

        // Cleanup
        getDefaultClient().deleteObject(DeleteObjectRequest.newBuilder()
                .bucket(bucketName).key(objectName).build());
    }

    @Test
    public void testPutGet_variousSizes() {
        OSSEncryptionClient encClient = createEncryptionClient();
        int[] sizes = {1, 15, 16, 17, 100, 255, 256, 1023, 1024, 1025, 4096, 65536};

        for (int size : sizes) {
            String objectName = genObjectName() + "-" + size;
            byte[] data = TestUtils.generateTestData(size);

            encClient.putObject(PutObjectRequest.newBuilder()
                    .bucket(bucketName)
                    .key(objectName)
                    .body(BinaryData.fromStream(new ByteArrayInputStream(data)))
                    .build());

            GetObjectResult getResult = encClient.getObject(GetObjectRequest.newBuilder()
                    .bucket(bucketName)
                    .key(objectName)
                    .build());

            byte[] decrypted = readStream(getResult.body());
            assertThat(decrypted).as("size=%d", size).isEqualTo(data);

            // Cleanup
            getDefaultClient().deleteObject(DeleteObjectRequest.newBuilder()
                    .bucket(bucketName).key(objectName).build());
        }
    }

    @Test
    public void testPutGet_withRange() {
        OSSEncryptionClient encClient = createEncryptionClient();
        String objectName = genObjectName() + "-range";

        byte[] data = TestUtils.generateTestData(512);

        encClient.putObject(PutObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .body(BinaryData.fromStream(new ByteArrayInputStream(data)))
                .build());

        // Range read: bytes 10-50
        GetObjectResult getResult = encClient.getObject(GetObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .range("bytes=10-50")
                .build());

        byte[] decrypted = readStream(getResult.body());
        byte[] expected = Arrays.copyOfRange(data, 10, 51);
        assertThat(decrypted).isEqualTo(expected);

        // Range read: bytes 100- (to end)
        getResult = encClient.getObject(GetObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .range("bytes=100-")
                .build());

        decrypted = readStream(getResult.body());
        expected = Arrays.copyOfRange(data, 100, data.length);
        assertThat(decrypted).isEqualTo(expected);

        // Cleanup
        getDefaultClient().deleteObject(DeleteObjectRequest.newBuilder()
                .bucket(bucketName).key(objectName).build());
    }

    @Test
    public void testMultipartUpload() {
        OSSEncryptionClient encClient = createEncryptionClient();
        String objectName = genObjectName() + "-multipart";

        long partSize = 102400; // 100KB, must be multiple of 16
        byte[] part1Data = TestUtils.generateTestData((int) partSize);
        byte[] part2Data = TestUtils.generateTestData((int) partSize);

        // Initiate
        InitiateMultipartUploadResult initResult = encClient.initiateMultipartUpload(
                InitiateMultipartUploadRequest.newBuilder()
                        .bucket(bucketName)
                        .key(objectName)
                        .csePartSize(partSize)
                        .cseDataSize(partSize * 2)
                        .build());
        assertThat(initResult).isNotNull();
        assertThat(initResult.cseContext()).isNotNull();
        InitiateMultipartUploadResult.CSEContext ctx = initResult.cseContext();
        String uploadId = initResult.initiateMultipartUpload().uploadId();

        // Upload part 1
        UploadPartResult part1Result = encClient.uploadPart(ctx, UploadPartRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .uploadId(uploadId)
                .partNumber(1L)
                .body(BinaryData.fromStream(new ByteArrayInputStream(part1Data)))
                .build());

        // Upload part 2
        UploadPartResult part2Result = encClient.uploadPart(ctx, UploadPartRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .uploadId(uploadId)
                .partNumber(2L)
                .body(BinaryData.fromStream(new ByteArrayInputStream(part2Data)))
                .build());

        // List parts to get ETags
        ListPartsResult listPartsResult = encClient.listParts(ListPartsRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .uploadId(uploadId)
                .build());
        assertThat(listPartsResult.parts()).hasSize(2);

        // Complete
        List<Part> parts = Arrays.asList(
                Part.newBuilder().partNumber(1L).eTag(part1Result.eTag()).build(),
                Part.newBuilder().partNumber(2L).eTag(part2Result.eTag()).build()
        );
        CompleteMultipartUpload completeMultipartUpload = CompleteMultipartUpload.newBuilder()
                .parts(parts)
                .build();

        encClient.completeMultipartUpload(
                CompleteMultipartUploadRequest.newBuilder()
                        .bucket(bucketName)
                        .key(objectName)
                        .uploadId(uploadId)
                        .completeMultipartUpload(completeMultipartUpload)
                        .build());

        // Verify: download and decrypt, compare with concatenated original
        byte[] expectedData = new byte[part1Data.length + part2Data.length];
        System.arraycopy(part1Data, 0, expectedData, 0, part1Data.length);
        System.arraycopy(part2Data, 0, expectedData, part1Data.length, part2Data.length);

        GetObjectResult getResult = encClient.getObject(GetObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .build());

        byte[] decrypted = readStream(getResult.body());
        assertThat(decrypted).isEqualTo(expectedData);

        // Cleanup
        getDefaultClient().deleteObject(DeleteObjectRequest.newBuilder()
                .bucket(bucketName).key(objectName).build());
    }

    @Test
    public void testEncryptedObject_notReadableByPlainClient() {
        OSSEncryptionClient encClient = createEncryptionClient();
        String objectName = genObjectName() + "-encrypted-check";

        String plainText = "This data should be encrypted at rest";
        byte[] data = plainText.getBytes();

        encClient.putObject(PutObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .body(BinaryData.fromStream(new ByteArrayInputStream(data)))
                .build());

        // Read with plain client - should get encrypted bytes
        GetObjectResult plainResult = getDefaultClient().getObject(GetObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .build());

        byte[] rawBytes = readStream(plainResult.body());
        String rawString = new String(rawBytes);

        // Raw bytes should NOT match the original plaintext
        assertThat(rawBytes).isNotEqualTo(data);
        assertThat(rawString).isNotEqualTo(plainText);

        // Cleanup
        getDefaultClient().deleteObject(DeleteObjectRequest.newBuilder()
                .bucket(bucketName).key(objectName).build());
    }

    // ==================== Large Object ====================

    @Test
    public void testPutGet_LargeObject() {
        OSSEncryptionClient encClient = createEncryptionClient();
        String objectName = genObjectName() + "-large";

        byte[] data = TestUtils.generateTestData(1024 * 1024 + 100); // 1MB + 100 bytes

        encClient.putObject(PutObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .body(BinaryData.fromStream(new ByteArrayInputStream(data)))
                .build());

        GetObjectResult getResult = encClient.getObject(GetObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .build());

        byte[] decrypted = readStream(getResult.body());
        assertThat(decrypted).isEqualTo(data);

        // Cleanup
        getDefaultClient().deleteObject(DeleteObjectRequest.newBuilder()
                .bucket(bucketName).key(objectName).build());
    }

    // ==================== Range Get (with header assertions) ====================

    @Test
    public void testGetObject_RangeFromStart() {
        OSSEncryptionClient encClient = createEncryptionClient();
        String objectName = genObjectName() + "-range-start";
        String content = "0123456789abcdefghijklmnopqrstuvwxyz";

        encClient.putObject(PutObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .body(BinaryData.fromStream(new ByteArrayInputStream(content.getBytes())))
                .build());

        GetObjectResult getResult = encClient.getObject(GetObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .range("bytes=0-9")
                .build());

        assertThat(getResult.contentLength()).isEqualTo(10L);
        assertThat(getResult.contentRange()).isEqualTo("bytes 0-9/" + content.length());

        byte[] decrypted = readStream(getResult.body());
        assertThat(new String(decrypted)).isEqualTo(content.substring(0, 10));

        getDefaultClient().deleteObject(DeleteObjectRequest.newBuilder()
                .bucket(bucketName).key(objectName).build());
    }

    @Test
    public void testGetObject_RangeCrossBlock() {
        OSSEncryptionClient encClient = createEncryptionClient();
        String objectName = genObjectName() + "-range-cross";
        String content = "0123456789abcdefghijklmnopqrstuvwxyz";

        encClient.putObject(PutObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .body(BinaryData.fromStream(new ByteArrayInputStream(content.getBytes())))
                .build());

        GetObjectResult getResult = encClient.getObject(GetObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .range("bytes=0-19")
                .build());

        assertThat(getResult.contentLength()).isEqualTo(20L);
        assertThat(getResult.contentRange()).isEqualTo("bytes 0-19/" + content.length());

        byte[] decrypted = readStream(getResult.body());
        assertThat(new String(decrypted)).isEqualTo(content.substring(0, 20));

        getDefaultClient().deleteObject(DeleteObjectRequest.newBuilder()
                .bucket(bucketName).key(objectName).build());
    }

    @Test
    public void testGetObject_RangeMiddle() {
        OSSEncryptionClient encClient = createEncryptionClient();
        String objectName = genObjectName() + "-range-middle";
        String content = "0123456789abcdefghijklmnopqrstuvwxyz";

        encClient.putObject(PutObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .body(BinaryData.fromStream(new ByteArrayInputStream(content.getBytes())))
                .build());

        GetObjectResult getResult = encClient.getObject(GetObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .range("bytes=10-19")
                .build());

        assertThat(getResult.contentLength()).isEqualTo(10L);
        assertThat(getResult.contentRange()).isEqualTo("bytes 10-19/" + content.length());

        byte[] decrypted = readStream(getResult.body());
        assertThat(new String(decrypted)).isEqualTo(content.substring(10, 20));

        getDefaultClient().deleteObject(DeleteObjectRequest.newBuilder()
                .bucket(bucketName).key(objectName).build());
    }

    @Test
    public void testGetObject_RangeUnaligned() {
        OSSEncryptionClient encClient = createEncryptionClient();
        String objectName = genObjectName() + "-range-unaligned";
        String content = "0123456789abcdefghijklmnopqrstuvwxyz";

        encClient.putObject(PutObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .body(BinaryData.fromStream(new ByteArrayInputStream(content.getBytes())))
                .build());

        GetObjectResult getResult = encClient.getObject(GetObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .range("bytes=5-")
                .build());

        assertThat(getResult.contentLength()).isEqualTo((long) (content.length() - 5));
        assertThat(getResult.contentRange()).isEqualTo(
                "bytes 5-" + (content.length() - 1) + "/" + content.length());

        byte[] decrypted = readStream(getResult.body());
        assertThat(new String(decrypted)).isEqualTo(content.substring(5));

        getDefaultClient().deleteObject(DeleteObjectRequest.newBuilder()
                .bucket(bucketName).key(objectName).build());
    }

    @Test
    public void testGetObject_RangeAligned() {
        OSSEncryptionClient encClient = createEncryptionClient();
        String objectName = genObjectName() + "-range-aligned";
        String content = "0123456789abcdefGHIJKLMNOPQRSTUVWXYZ";

        encClient.putObject(PutObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .body(BinaryData.fromStream(new ByteArrayInputStream(content.getBytes())))
                .build());

        GetObjectResult getResult = encClient.getObject(GetObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .range("bytes=16-31")
                .build());

        assertThat(getResult.contentLength()).isEqualTo(16L);
        assertThat(getResult.contentRange()).isEqualTo("bytes 16-31/" + content.length());

        byte[] decrypted = readStream(getResult.body());
        assertThat(new String(decrypted)).isEqualTo(content.substring(16, 32));

        getDefaultClient().deleteObject(DeleteObjectRequest.newBuilder()
                .bucket(bucketName).key(objectName).build());
    }

    // ==================== Multipart Upload Error Cases ====================

    @Test
    public void testMultipartUpload_InvalidPartSize() {
        OSSEncryptionClient encClient = createEncryptionClient();
        String objectName = genObjectName() + "-multipart-invalid";

        try {
            encClient.initiateMultipartUpload(
                    InitiateMultipartUploadRequest.newBuilder()
                            .bucket(bucketName)
                            .key(objectName)
                            .csePartSize(102401L) // not a multiple of 16
                            .cseDataSize(500000L)
                            .build());
            Assert.fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    @Test
    public void testMultipartUpload_MissingDataSize() {
        OSSEncryptionClient encClient = createEncryptionClient();
        String objectName = genObjectName() + "-multipart-nodata";

        try {
            encClient.initiateMultipartUpload(
                    InitiateMultipartUploadRequest.newBuilder()
                            .bucket(bucketName)
                            .key(objectName)
                            .csePartSize(102400L)
                            .build());
            Assert.fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    // ==================== Unencrypted Passthrough ====================

    @Test
    public void testGetObject_UnencryptedPassthrough() {
        OSSEncryptionClient encClient = createEncryptionClient();
        String objectName = genObjectName() + "-unencrypted";
        String content = "plain text content";

        // Upload with plain client (no encryption)
        getDefaultClient().putObject(PutObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .body(BinaryData.fromStream(new ByteArrayInputStream(content.getBytes())))
                .build());

        // Read with encryption client — should passthrough without decryption
        GetObjectResult getResult = encClient.getObject(GetObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .build());

        byte[] decrypted = readStream(getResult.body());
        assertThat(new String(decrypted)).isEqualTo(content);

        // Cleanup
        getDefaultClient().deleteObject(DeleteObjectRequest.newBuilder()
                .bucket(bucketName).key(objectName).build());
    }

    // ==================== Error Cases ====================

    @Test
    public void testPutObject_InvalidRsaKey() {
        try {
            RsaMasterCipher.parsePublicKeyFromPem("invalid");
            Assert.fail("Expected exception for invalid key");
        } catch (Exception e) {
            // expected — invalid PEM format
        }
    }

    @Test
    public void testGetObject_DifferentKey() {
        OSSEncryptionClient encClient = createEncryptionClient();
        String objectName = genObjectName() + "-diff-key";
        String content = "secret data";

        encClient.putObject(PutObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .body(BinaryData.fromStream(new ByteArrayInputStream(content.getBytes())))
                .build());

        // Create client with different key pair
        OSSEncryptionClient otherClient = createEncryptionClientWithKey(
                OTHER_PUBLIC_KEY_PEM_X509, OTHER_PRIVATE_KEY_PEM_PKCS1);

        try {
            GetObjectResult getResult = otherClient.getObject(GetObjectRequest.newBuilder()
                    .bucket(bucketName)
                    .key(objectName)
                    .build());

            byte[] decrypted = readStream(getResult.body());
            // Decrypted content should NOT match original
            assertThat(new String(decrypted)).isNotEqualTo(content);
        } catch (Exception e) {
            // Decryption failure is also acceptable — key mismatch
        }

        // Cleanup
        getDefaultClient().deleteObject(DeleteObjectRequest.newBuilder()
                .bucket(bucketName).key(objectName).build());
    }

    // ==================== User Metadata ====================

    @Test
    public void testPutGet_UserMetadataPreserved() {
        OSSEncryptionClient encClient = createEncryptionClient();
        String objectName = genObjectName() + "-metadata";
        String content = "metadata test";

        Map<String, String> meta = new LinkedHashMap<>();
        meta.put("my-key1", "value1");
        meta.put("my-key2", "value2");

        encClient.putObject(PutObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .body(BinaryData.fromStream(new ByteArrayInputStream(content.getBytes())))
                .metadata(meta)
                .build());

        // Verify via headObject with plain client
        HeadObjectResult headResult = getDefaultClient().headObject(HeadObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .build());

        // User metadata preserved
        assertThat(headResult.metadata().get("my-key1")).isEqualTo("value1");
        assertThat(headResult.metadata().get("my-key2")).isEqualTo("value2");

        // CSE headers present
        assertThat(headResult.metadata()).containsKey("client-side-encryption-key");

        // Cleanup
        getDefaultClient().deleteObject(DeleteObjectRequest.newBuilder()
                .bucket(bucketName).key(objectName).build());
    }

    // ==================== Cross-SDK Compatibility ====================

    @Test
    public void testCrossSdk_DecryptPreEncryptedData() throws Exception {
        String resourceDir = "src/test/resources/";
        byte[] originalData = Files.readAllBytes(Paths.get(resourceDir + "example.jpg"));

        // Compat client with only private key (decrypt-only)
        OSSEncryptionClient compatClient = createEncryptionClientWithKey(
                null, COMPAT_PRIVATE_KEY_PEM_PKCS1);

        // Test cases: pre-encrypted files from C++ SDK and Go SDK
        String[][] testCases = {
                {"cpp-enc-example.jpg",
                        "nyXOp7delQ/MQLjKQMhHLaT0w7u2yQoDLkSnK8MFg/MwYdh4na4/LS8LLbLcM18m8I/ObWUHU775I50sJCpdv+f4e0jLeVRRiDFWe+uo7Puc9j4xHj8YB3QlcIOFQiTxHIB6q+C+RA6lGwqqYVa+n3aV5uWhygyv1MWmESurppg=",
                        "De/S3T8wFjx7QPxAAFl7h7TeI2EsZlfCwox4WhLGng5DK2vNXxULmulMUUpYkdc9umqmDilgSy5Z3Foafw+v4JJThfw68T/9G2gxZLrQTbAlvFPFfPM9Ehk6cY4+8WpY32uN8w5vrHyoSZGr343NxCUGIp6fQ9sSuOLMoJg7hNw="},
                {"go-enc-example.jpg",
                        "F2L5QjyA2s85tPvaGdQ5EKnU/XN5dUWqZfgwcM4gfzPMcDWR93AZGSpeB9VSJBYPdIqhy1cevKEJv+Dv2ckDuDJ7nzijwcBnO5tPl5jXYlWxgzj6t1gMqQr/LENbB5iC8hzGkkoVWjWtSPDB+uE3+qf4V1A0308OqSM3OKxV0VI=",
                        "D+3z6ftLp500eVnvsat5awYdYI/jTeSRlGlmHNrhTm3l1bonYP1v72vGqZhvOpT++9ZXOhdePu82gjhqVfh8Qv2HZsVGeJLzQJRU8kIKc7PRI4SoqpHZh2VYsASvnDtxVy2MQmpJzvG8xr4j3I29EgsEha7NV+2hGq/dolxLHNc="}
        };

        for (String[] tc : testCases) {
            String encFile = tc[0];
            String encKey = tc[1];
            String encIv = tc[2];

            byte[] encData = Files.readAllBytes(Paths.get(resourceDir + encFile));
            String key = genObjectName() + "-compat-" + encFile;

            // Upload pre-encrypted data with plain client + CSE headers
            Map<String, String> meta = new LinkedHashMap<>();
            meta.put("client-side-encryption-key", encKey);
            meta.put("client-side-encryption-start", encIv);
            meta.put("client-side-encryption-cek-alg", "AES/CTR/NoPadding");
            meta.put("client-side-encryption-wrap-alg", "RSA/NONE/PKCS1Padding");

            getDefaultClient().putObject(PutObjectRequest.newBuilder()
                    .bucket(bucketName)
                    .key(key)
                    .body(BinaryData.fromStream(new ByteArrayInputStream(encData)))
                    .metadata(meta)
                    .build());

            // Decrypt with compat client
            GetObjectResult getResult = compatClient.getObject(GetObjectRequest.newBuilder()
                    .bucket(bucketName)
                    .key(key)
                    .build());

            byte[] decrypted = readStream(getResult.body());
            assertThat(decrypted).as("cross-SDK compat: %s", encFile).isEqualTo(originalData);

            // Cleanup
            getDefaultClient().deleteObject(DeleteObjectRequest.newBuilder()
                    .bucket(bucketName).key(key).build());
        }
    }

    // ==================== Helpers ====================

    private OSSEncryptionClient createEncryptionClientWithKey(String pubPem, String privPem) {
        RSAPublicKey pub = pubPem != null
                ? RsaMasterCipher.parsePublicKeyFromPem(pubPem) : null;
        RSAPrivateKey priv = RsaMasterCipher.parsePrivateKeyFromPemPKCS1(privPem);
        KeyPair keyPair = pub != null ? new KeyPair(pub, priv) : new KeyPair(
                RsaMasterCipher.parsePublicKeyFromPem(PUBLIC_KEY_PEM_X509), priv);
        MasterCipher masterCipher = RsaMasterCipher.of(keyPair, null);

        EncryptionConfiguration encConfig = EncryptionConfiguration.newBuilder()
                .masterCipher(masterCipher)
                .build();

        return new OSSEncryptionClient(getDefaultClient(), encConfig);
    }

    // Helper
    private static byte[] readStream(InputStream is) {
        try {
            ByteArrayOutputStream buf = new ByteArrayOutputStream();
            byte[] tmp = new byte[4096];
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
