package com.aliyun.sdk.service.oss2.encryption;

import com.aliyun.sdk.service.oss2.OSSClient;
import com.aliyun.sdk.service.oss2.OperationOptions;
import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class OSSEncryptionClientTest {

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

    private StubOSSClient stubClient;
    private OSSEncryptionClient encClient;

    @BeforeEach
    void setUp() {
        RSAPublicKey pub = RsaMasterCipher.parsePublicKeyFromPem(PUBLIC_KEY_PEM_X509);
        RSAPrivateKey priv = RsaMasterCipher.parsePrivateKeyFromPemPKCS1(PRIVATE_KEY_PEM_PKCS1);
        MasterCipher masterCipher = RsaMasterCipher.of(new KeyPair(pub, priv), null);

        EncryptionConfiguration encConfig = EncryptionConfiguration.newBuilder()
                .masterCipher(masterCipher)
                .build();

        stubClient = new StubOSSClient();
        encClient = new OSSEncryptionClient(stubClient, encConfig);
    }

    @Test
    void putGet_roundTrip_smallData() {
        String plainText = "Hello, client-side encryption!";
        byte[] plainBytes = plainText.getBytes();

        // putObject
        PutObjectRequest putReq = PutObjectRequest.newBuilder()
                .bucket("test-bucket")
                .key("test-object")
                .body(BinaryData.fromStream(new ByteArrayInputStream(plainBytes), (long) plainBytes.length))
                .build();

        encClient.putObject(putReq);

        // Verify stored object has crypto headers
        StubOSSClient.StoredObject stored = stubClient.getStored("test-bucket", "test-object");
        assertThat(stored).isNotNull();
        assertThat(stored.headers).containsKey("x-oss-meta-client-side-encryption-key");
        assertThat(stored.headers).containsKey("x-oss-meta-client-side-encryption-start");
        assertThat(stored.headers).containsKey("x-oss-meta-client-side-encryption-cek-alg");
        assertThat(stored.headers).containsKey("x-oss-meta-client-side-encryption-wrap-alg");
        assertThat(stored.headers.get("x-oss-meta-client-side-encryption-cek-alg")).isEqualTo("AES/CTR/NoPadding");
        assertThat(stored.headers.get("x-oss-meta-client-side-encryption-unencrypted-content-length"))
                .isEqualTo(String.valueOf(plainBytes.length));

        // Encrypted body should differ from plain body
        assertThat(stored.body).isNotEqualTo(plainBytes);

        // getObject - should decrypt
        GetObjectRequest getReq = GetObjectRequest.newBuilder()
                .bucket("test-bucket")
                .key("test-object")
                .build();

        GetObjectResult result = encClient.getObject(getReq);
        String decrypted = readStream(result.body());
        assertThat(decrypted).isEqualTo(plainText);
    }

    @Test
    void putGet_variousSizes() {
        int[] sizes = {0, 1, 15, 16, 17, 31, 32, 33, 100, 255, 256, 1023, 1024, 1025, 4096};
        for (int size : sizes) {
            byte[] data = new byte[size];
            new Random(size).nextBytes(data);
            String key = "obj-" + size;

            encClient.putObject(PutObjectRequest.newBuilder()
                    .bucket("b")
                    .key(key)
                    .body(BinaryData.fromStream(new ByteArrayInputStream(data), (long) data.length))
                    .build());

            GetObjectResult result = encClient.getObject(GetObjectRequest.newBuilder()
                    .bucket("b")
                    .key(key)
                    .build());

            byte[] decrypted = readBytes(result.body());
            assertThat(decrypted).as("size=%d", size).isEqualTo(data);
        }
    }

    @Test
    void putGet_withRange() {
        byte[] plainBytes = new byte[256];
        for (int i = 0; i < plainBytes.length; i++) {
            plainBytes[i] = (byte) (i & 0xFF);
        }

        encClient.putObject(PutObjectRequest.newBuilder()
                .bucket("b")
                .key("range-test")
                .body(BinaryData.fromStream(new ByteArrayInputStream(plainBytes), (long) plainBytes.length))
                .build());

        // Range read: bytes 10-20 (not block-aligned, discardCount = 10)
        GetObjectResult result = encClient.getObject(GetObjectRequest.newBuilder()
                .bucket("b")
                .key("range-test")
                .range("bytes=10-20")
                .build());

        byte[] decrypted = readBytes(result.body());
        byte[] expected = Arrays.copyOfRange(plainBytes, 10, 21);
        assertThat(decrypted).isEqualTo(expected);

        // Content-Length should be adjusted: 11 bytes (21 - 10 discard)
        assertThat(result.contentLength()).isEqualTo(11L);
        // Content-Range should be adjusted: start shifted by discardCount
        assertThat(result.contentRange()).isEqualTo("bytes 10-20/256");
    }

    @Test
    void putGet_withRange_caseInsensitiveHeaders() {
        byte[] plainBytes = new byte[256];
        for (int i = 0; i < plainBytes.length; i++) {
            plainBytes[i] = (byte) (i & 0xFF);
        }

        // Use a stub that returns lowercase header keys
        LowercaseHeaderStubClient lowercaseStub = new LowercaseHeaderStubClient();
        RSAPublicKey pub = RsaMasterCipher.parsePublicKeyFromPem(PUBLIC_KEY_PEM_X509);
        RSAPrivateKey priv = RsaMasterCipher.parsePrivateKeyFromPemPKCS1(PRIVATE_KEY_PEM_PKCS1);
        MasterCipher masterCipher = RsaMasterCipher.of(new KeyPair(pub, priv), null);
        EncryptionConfiguration encConfig = EncryptionConfiguration.newBuilder()
                .masterCipher(masterCipher)
                .build();
        OSSEncryptionClient encClient = new OSSEncryptionClient(lowercaseStub, encConfig);

        encClient.putObject(PutObjectRequest.newBuilder()
                .bucket("b")
                .key("lowercase-test")
                .body(BinaryData.fromStream(new ByteArrayInputStream(plainBytes), (long) plainBytes.length))
                .build());

        // Range read: bytes=10-20 (not block-aligned, discardCount = 10)
        GetObjectResult result = encClient.getObject(GetObjectRequest.newBuilder()
                .bucket("b")
                .key("lowercase-test")
                .range("bytes=10-20")
                .build());

        byte[] decrypted = readBytes(result.body());
        byte[] expected = Arrays.copyOfRange(plainBytes, 10, 21);
        assertThat(decrypted).isEqualTo(expected);

        // Server returned lowercase "content-length" / "content-range"
        // Encryption client should still adjust them correctly
        assertThat(result.contentLength()).isEqualTo(11L);
        assertThat(result.contentRange()).isEqualTo("bytes 10-20/256");

        // Direct header access should work with any case (case-insensitive map)
        assertThat(result.headers().get("content-length")).isEqualTo("11");
        assertThat(result.headers().get("Content-Length")).isEqualTo("11");
        assertThat(result.headers().get("CONTENT-LENGTH")).isEqualTo("11");
        assertThat(result.headers().get("content-range")).isEqualTo("bytes 10-20/256");
        assertThat(result.headers().get("Content-Range")).isEqualTo("bytes 10-20/256");
    }

    @Test
    void putGet_withAlignedRange_noHeaderAdjustment() {
        byte[] plainBytes = new byte[256];
        for (int i = 0; i < plainBytes.length; i++) {
            plainBytes[i] = (byte) (i & 0xFF);
        }

        encClient.putObject(PutObjectRequest.newBuilder()
                .bucket("b")
                .key("aligned-range-test")
                .body(BinaryData.fromStream(new ByteArrayInputStream(plainBytes), (long) plainBytes.length))
                .build());

        // Range read: bytes=16-31 (block-aligned, discardCount = 0)
        GetObjectResult result = encClient.getObject(GetObjectRequest.newBuilder()
                .bucket("b")
                .key("aligned-range-test")
                .range("bytes=16-31")
                .build());

        byte[] decrypted = readBytes(result.body());
        byte[] expected = Arrays.copyOfRange(plainBytes, 16, 32);
        assertThat(decrypted).isEqualTo(expected);

        // Content-Length: 16 bytes, no adjustment needed
        assertThat(result.contentLength()).isEqualTo(16L);
        assertThat(result.contentRange()).isEqualTo("bytes 16-31/256");
    }

    @Test
    void multipart_partSizeNotAligned_throwsException() {
        assertThatThrownBy(() -> encClient.initiateMultipartUpload(
                InitiateMultipartUploadRequest.newBuilder()
                        .bucket("b")
                        .key("k")
                        .csePartSize(100L)  // not 16-byte aligned
                        .cseDataSize(200L)
                        .build()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("16");
    }

    @Test
    void multipart_missingPartSize_throwsException() {
        assertThatThrownBy(() -> encClient.initiateMultipartUpload(
                InitiateMultipartUploadRequest.newBuilder()
                        .bucket("b")
                        .key("k")
                        .cseDataSize(200L)
                        .build()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void multipart_roundTrip() {
        long partSize = 160; // 10 * 16 bytes, aligned
        byte[] part1 = new byte[(int) partSize];
        byte[] part2 = new byte[(int) partSize];
        new Random(1).nextBytes(part1);
        new Random(2).nextBytes(part2);

        InitiateMultipartUploadResult initResult = encClient.initiateMultipartUpload(
                InitiateMultipartUploadRequest.newBuilder()
                        .bucket("b")
                        .key("multipart-obj")
                        .csePartSize(partSize)
                        .cseDataSize(partSize * 2)
                        .build());
        assertThat(initResult).isNotNull();
        assertThat(initResult.cseContext()).isNotNull();
        assertThat(initResult.cseContext().partSize()).isEqualTo(partSize);
        String uploadId = initResult.initiateMultipartUpload().uploadId();

        encClient.uploadPart(initResult.cseContext(), UploadPartRequest.newBuilder()
                .bucket("b").key("multipart-obj").uploadId(uploadId).partNumber(1L)
                .body(BinaryData.fromStream(new ByteArrayInputStream(part1), (long) part1.length))
                .build());

        encClient.uploadPart(initResult.cseContext(), UploadPartRequest.newBuilder()
                .bucket("b").key("multipart-obj").uploadId(uploadId).partNumber(2L)
                .body(BinaryData.fromStream(new ByteArrayInputStream(part2), (long) part2.length))
                .build());

        encClient.completeMultipartUpload(CompleteMultipartUploadRequest.newBuilder()
                .bucket("b").key("multipart-obj").uploadId(uploadId)
                .build());

        // Verify: download and decrypt
        GetObjectResult result = encClient.getObject(GetObjectRequest.newBuilder()
                .bucket("b").key("multipart-obj").build());
        byte[] decrypted = readBytes(result.body());
        byte[] expected = new byte[part1.length + part2.length];
        System.arraycopy(part1, 0, expected, 0, part1.length);
        System.arraycopy(part2, 0, expected, part1.length, part2.length);
        assertThat(decrypted).isEqualTo(expected);
    }

    @Test
    void unwrap_returnsInnerClient() {
        assertThat(encClient.unwrap()).isSameAs(stubClient);
    }

    // ==================== Helpers ====================

    private static String readStream(InputStream is) {
        try {
            return new String(readBytes(is));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static byte[] readBytes(InputStream is) {
        try {
            ByteArrayOutputStream buf = new ByteArrayOutputStream();
            byte[] tmp = new byte[1024];
            int n;
            while ((n = is.read(tmp)) != -1) {
                buf.write(tmp, 0, n);
            }
            return buf.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // ==================== Stub OSSClient ====================

    /**
     * Minimal in-memory OSSClient stub for testing encryption client.
     */
    static class StubOSSClient implements OSSClient {

        final Map<String, StoredObject> objects = new ConcurrentHashMap<>();
        final Map<String, MultipartState> multipartStates = new ConcurrentHashMap<>();
        private int uploadIdCounter = 0;

        static class StoredObject {
            final byte[] body;
            final Map<String, String> headers;

            StoredObject(byte[] body, Map<String, String> headers) {
                this.body = body;
                this.headers = headers;
            }
        }

        static class MultipartState {
            final List<byte[]> parts = new ArrayList<>();
            final Map<String, String> headers;

            MultipartState(Map<String, String> headers) {
                this.headers = headers;
            }
        }

        StoredObject getStored(String bucket, String key) {
            return objects.get(bucket + "/" + key);
        }

        @Override
        public PutObjectResult putObject(PutObjectRequest request, OperationOptions options) {
            byte[] body = readBytes(request.body());
            Map<String, String> headers = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
            if (request.metadata() != null) {
                request.metadata().forEach((k, v) -> headers.put("x-oss-meta-" + k, v));
            }
            // Also copy any headers set directly
            if (request.headers() != null) {
                headers.putAll(request.headers());
            }
            objects.put(request.bucket() + "/" + request.key(), new StoredObject(body, headers));
            return PutObjectResult.newBuilder()
                    .statusCode(200)
                    .headers(MapUtils.caseInsensitiveMap())
                    .build();
        }

        @Override
        public GetObjectResult getObject(GetObjectRequest request, OperationOptions options) {
            StoredObject obj = objects.get(request.bucket() + "/" + request.key());
            if (obj == null) {
                throw new RuntimeException("Object not found: " + request.bucket() + "/" + request.key());
            }

            byte[] body = obj.body;
            Map<String, String> respHeaders = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
            respHeaders.putAll(obj.headers);
            respHeaders.put("Content-Length", String.valueOf(body.length));

            // Handle range
            if (request.range() != null) {
                String range = request.range();
                if (range.startsWith("bytes=")) {
                    String rangeSpec = range.substring(6);
                    String[] parts = rangeSpec.split("-", 2);
                    int start = parts[0].isEmpty() ? 0 : Integer.parseInt(parts[0]);
                    int end = parts[1].isEmpty() ? body.length - 1 : Integer.parseInt(parts[1]);
                    body = Arrays.copyOfRange(body, start, end + 1);
                    respHeaders.put("Content-Length", String.valueOf(body.length));
                    respHeaders.put("Content-Range",
                            "bytes " + start + "-" + end + "/" + obj.body.length);
                }
            }

            return GetObjectResult.newBuilder()
                    .statusCode(200)
                    .headers(respHeaders)
                    .innerBody(new ByteArrayInputStream(body))
                    .build();
        }

        @Override
        public InitiateMultipartUploadResult initiateMultipartUpload(
                InitiateMultipartUploadRequest request, OperationOptions options) {
            String uploadId = "upload-" + (++uploadIdCounter);
            Map<String, String> headers = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
            if (request.metadata() != null) {
                request.metadata().forEach((k, v) -> headers.put("x-oss-meta-" + k, v));
            }
            if (request.headers() != null) {
                headers.putAll(request.headers());
            }
            multipartStates.put(uploadId, new MultipartState(headers));

            InitiateMultipartUpload imu = InitiateMultipartUpload.newBuilder()
                    .bucket(request.bucket())
                    .key(request.key())
                    .uploadId(uploadId)
                    .build();

            return InitiateMultipartUploadResult.newBuilder()
                    .statusCode(200)
                    .headers(MapUtils.caseInsensitiveMap())
                    .innerBody(imu)
                    .build();
        }

        @Override
        public UploadPartResult uploadPart(UploadPartRequest request, OperationOptions options) {
            MultipartState state = multipartStates.get(request.uploadId());
            if (state == null) {
                throw new RuntimeException("Upload not found: " + request.uploadId());
            }
            byte[] body = readBytes(request.body());
            // Store part at correct index
            int partIdx = (int) (request.partNumber() - 1);
            while (state.parts.size() <= partIdx) {
                state.parts.add(null);
            }
            state.parts.set(partIdx, body);

            return UploadPartResult.newBuilder()
                    .statusCode(200)
                    .headers(MapUtils.caseInsensitiveMap())
                    .build();
        }

        @Override
        public CompleteMultipartUploadResult completeMultipartUpload(
                CompleteMultipartUploadRequest request, OperationOptions options) {
            MultipartState state = multipartStates.remove(request.uploadId());
            if (state == null) {
                throw new RuntimeException("Upload not found: " + request.uploadId());
            }
            // Concatenate parts
            ByteArrayOutputStream buf = new ByteArrayOutputStream();
            for (byte[] part : state.parts) {
                if (part != null) {
                    buf.write(part, 0, part.length);
                }
            }
            objects.put(request.bucket() + "/" + request.key(),
                    new StoredObject(buf.toByteArray(), state.headers));

            return CompleteMultipartUploadResult.newBuilder()
                    .statusCode(200)
                    .headers(MapUtils.caseInsensitiveMap())
                    .build();
        }

        @Override
        public AbortMultipartUploadResult abortMultipartUpload(
                AbortMultipartUploadRequest request, OperationOptions options) {
            multipartStates.remove(request.uploadId());
            return AbortMultipartUploadResult.newBuilder()
                    .statusCode(204)
                    .headers(MapUtils.caseInsensitiveMap())
                    .build();
        }

        @Override
        public void close() {
        }

        private static byte[] readBytes(BinaryData body) {
            if (body == null) return new byte[0];
            try (InputStream is = body.toStream()) {
                ByteArrayOutputStream buf = new ByteArrayOutputStream();
                byte[] tmp = new byte[1024];
                int n;
                while ((n = is.read(tmp)) != -1) {
                    buf.write(tmp, 0, n);
                }
                return buf.toByteArray();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Stub that returns lowercase header keys to test case-insensitive handling.
     */
    static class LowercaseHeaderStubClient extends StubOSSClient {
        @Override
        public GetObjectResult getObject(GetObjectRequest request, OperationOptions options) {
            StoredObject obj = objects.get(request.bucket() + "/" + request.key());
            if (obj == null) {
                throw new RuntimeException("Object not found: " + request.bucket() + "/" + request.key());
            }

            byte[] body = obj.body;
            Map<String, String> respHeaders = MapUtils.caseInsensitiveMap();
            respHeaders.putAll(obj.headers);
            respHeaders.put("content-length", String.valueOf(body.length));

            // Handle range
            if (request.range() != null) {
                String range = request.range();
                if (range.startsWith("bytes=")) {
                    String rangeSpec = range.substring(6);
                    String[] parts = rangeSpec.split("-", 2);
                    int start = parts[0].isEmpty() ? 0 : Integer.parseInt(parts[0]);
                    int end = parts[1].isEmpty() ? body.length - 1 : Integer.parseInt(parts[1]);
                    body = Arrays.copyOfRange(body, start, end + 1);
                    respHeaders.put("content-length", String.valueOf(body.length));
                    respHeaders.put("content-range",
                            "bytes " + start + "-" + end + "/" + obj.body.length);
                }
            }

            return GetObjectResult.newBuilder()
                    .statusCode(200)
                    .headers(respHeaders)
                    .innerBody(new ByteArrayInputStream(body))
                    .build();
        }
    }
}
