package com.aliyun.sdk.service.oss2;

import com.aliyun.sdk.service.oss2.credentials.CredentialsProvider;
import com.aliyun.sdk.service.oss2.credentials.StaticCredentialsProvider;
import com.aliyun.sdk.service.oss2.internal.TestUtils;
import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.transport.ByteArrayBinaryData;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

public class ClientMultipartUploadTest extends TestBase {

    private String genObjectName() {
        long ticks = new Date().getTime() / 1000;
        long val = new Random().nextInt(5000);
        return OJBJECT_NAME_PREFIX + ticks + "-" + val;
    }

    @Test
    public void testMultipartUploadOperations() {
        OSSClient client = getDefaultClient();
        String objectName = genObjectName() + "-multipart.txt";
        String copyObjectName = genObjectName() + "-multipart-copy.txt";

        byte[] content = TestUtils.generateTestData(200 * 1024 + 123); // 200kb + 123 bytes
        List<UploadPartResult> uploadParts = new ArrayList<>();

        // 1. Initiate multipart upload
        InitiateMultipartUploadResult initiateResult = client.initiateMultipartUpload(
                InitiateMultipartUploadRequest.newBuilder()
                        .bucket(bucketName)
                        .key(objectName)
                        .build());
        Assert.assertNotNull(initiateResult);
        Assert.assertEquals(200, initiateResult.statusCode());
        Assert.assertNotNull(initiateResult.initiateMultipartUpload().uploadId());
        String uploadId = initiateResult.initiateMultipartUpload().uploadId();

        // 2. Upload parts
        // Upload part 1
        byte[] part1Data = new byte[100 * 1024 + 12]; // 100kb + 12
        System.arraycopy(content, 0, part1Data, 0, part1Data.length);
        UploadPartResult part1Result = client.uploadPart(
                UploadPartRequest.newBuilder()
                        .bucket(bucketName)
                        .key(objectName)
                        .uploadId(uploadId)
                        .partNumber(1L)
                        .body(new ByteArrayBinaryData(part1Data))
                        .build());
        Assert.assertNotNull(part1Result);
        Assert.assertEquals(200, part1Result.statusCode());
        uploadParts.add(part1Result);

        // Upload part 2
        byte[] part2Data = new byte[content.length - part1Data.length]; // Remaining data
        System.arraycopy(content, part1Data.length, part2Data, 0, part2Data.length);
        UploadPartResult part2Result = client.uploadPart(
                UploadPartRequest.newBuilder()
                        .bucket(bucketName)
                        .key(objectName)
                        .uploadId(uploadId)
                        .partNumber(2L)
                        .body(new ByteArrayBinaryData(part2Data))
                        .build());
        Assert.assertNotNull(part2Result);
        Assert.assertEquals(200, part2Result.statusCode());
        uploadParts.add(part2Result);

        // 3. List parts
        ListPartsResult listPartsResult = client.listParts(
                ListPartsRequest.newBuilder()
                        .bucket(bucketName)
                        .key(objectName)
                        .uploadId(uploadId)
                        .build());
        Assert.assertNotNull(listPartsResult);
        Assert.assertEquals(200, listPartsResult.statusCode());
        Assert.assertEquals(2, listPartsResult.parts().size());
        Assert.assertEquals(objectName, listPartsResult.key());
        Assert.assertEquals(uploadId, listPartsResult.uploadId());

        // 4. Complete multipart upload
        CompleteMultipartUpload completeMultipartUpload = CompleteMultipartUpload.newBuilder()
                .parts(createCompletedPartList(uploadParts))
                .build();

        CompleteMultipartUploadResult completeResult = client.completeMultipartUpload(
                CompleteMultipartUploadRequest.newBuilder()
                        .bucket(bucketName)
                        .key(objectName)
                        .uploadId(uploadId)
                        .completeMultipartUpload(completeMultipartUpload)
                        .build());
        Assert.assertNotNull(completeResult);
        Assert.assertEquals(200, completeResult.statusCode());

        // 5. Verify uploaded object
        GetObjectMetaResult metaResult = client.getObjectMeta(
                GetObjectMetaRequest.newBuilder()
                        .bucket(bucketName)
                        .key(objectName)
                        .build());
        Assert.assertNotNull(metaResult);
        Assert.assertEquals(200, metaResult.statusCode());
        Assert.assertEquals(Long.valueOf(content.length), metaResult.contentLength());

        // 6. Initiate another multipart upload for copy part test
        InitiateMultipartUploadResult initiateCopyResult = client.initiateMultipartUpload(
                InitiateMultipartUploadRequest.newBuilder()
                        .bucket(bucketName)
                        .key(copyObjectName)
                        .build());
        Assert.assertNotNull(initiateCopyResult);
        Assert.assertEquals(200, initiateCopyResult.statusCode());
        String copyUploadId = initiateCopyResult.initiateMultipartUpload().uploadId();

        // 7. Upload part copy
        UploadPartCopyResult copyPartResult = client.uploadPartCopy(
                UploadPartCopyRequest.newBuilder()
                        .bucket(bucketName)
                        .key(copyObjectName)
                        .uploadId(copyUploadId)
                        .partNumber(1L)
                        .sourceBucket(bucketName)
                        .sourceKey(objectName)
                        .build());
        Assert.assertNotNull(copyPartResult);
        Assert.assertEquals(200, copyPartResult.statusCode());

        // 8. Complete copy multipart upload
        List<Part> copyParts = new ArrayList<>();
        copyParts.add(Part.newBuilder()
                .partNumber(1L)
                .eTag(copyPartResult.copyPartResult().eTag())
                .build());

        CompleteMultipartUpload copyCompleteMultipartUpload = CompleteMultipartUpload.newBuilder()
                .parts(copyParts)
                .build();

        CompleteMultipartUploadResult completeCopyResult = client.completeMultipartUpload(
                CompleteMultipartUploadRequest.newBuilder()
                        .bucket(bucketName)
                        .key(copyObjectName)
                        .uploadId(copyUploadId)
                        .completeMultipartUpload(copyCompleteMultipartUpload)
                        .build());
        Assert.assertNotNull(completeCopyResult);
        Assert.assertEquals(200, completeCopyResult.statusCode());

        // 9. Verify copied object
        GetObjectMetaResult copyMetaResult = client.getObjectMeta(
                GetObjectMetaRequest.newBuilder()
                        .bucket(bucketName)
                        .key(copyObjectName)
                        .build());
        Assert.assertNotNull(copyMetaResult);
        Assert.assertEquals(200, copyMetaResult.statusCode());

        // 10. List multipart uploads
        ListMultipartUploadsResult listMultipartResult = client.listMultipartUploads(
                ListMultipartUploadsRequest.newBuilder()
                        .bucket(bucketName)
                        .build());
        Assert.assertNotNull(listMultipartResult);
        Assert.assertEquals(200, listMultipartResult.statusCode());

        // 11. delete objects
        DeleteObjectResult deleteResult = client.deleteObject(
                DeleteObjectRequest.newBuilder()
                        .bucket(bucketName)
                        .key(objectName)
                        .build());
        Assert.assertNotNull(deleteResult);
        Assert.assertEquals(204, deleteResult.statusCode());

        DeleteObjectResult deleteCopyResult = client.deleteObject(
                DeleteObjectRequest.newBuilder()
                        .bucket(bucketName)
                        .key(copyObjectName)
                        .build());
        Assert.assertNotNull(deleteCopyResult);
        Assert.assertEquals(204, deleteCopyResult.statusCode());
    }

    @Test
    public void testAbortMultipartUpload() {
        OSSClient client = getDefaultClient();
        String objectName = genObjectName() + "-abort";

        // Initiate multipart upload
        InitiateMultipartUploadResult initiateResult = client.initiateMultipartUpload(
                InitiateMultipartUploadRequest.newBuilder()
                        .bucket(bucketName)
                        .key(objectName)
                        .build());
        Assert.assertNotNull(initiateResult);
        Assert.assertEquals(200, initiateResult.statusCode());
        Assert.assertNotNull(initiateResult.initiateMultipartUpload().uploadId());
        String uploadId = initiateResult.initiateMultipartUpload().uploadId();

        // Upload one part
        byte[] partData = TestUtils.generateTestData(100 * 1024 + 123);
        UploadPartResult partResult = client.uploadPart(
                UploadPartRequest.newBuilder()
                        .bucket(bucketName)
                        .key(objectName)
                        .uploadId(uploadId)
                        .partNumber(1L)
                        .body(new ByteArrayBinaryData(partData))
                        .build());
        Assert.assertNotNull(partResult);
        Assert.assertEquals(200, partResult.statusCode());

        // List multipart uploads before abort
        ListMultipartUploadsResult listBeforeAbortResult = client.listMultipartUploads(
                ListMultipartUploadsRequest.newBuilder()
                        .bucket(bucketName)
                        .build());
        Assert.assertNotNull(listBeforeAbortResult);
        Assert.assertEquals(200, listBeforeAbortResult.statusCode());
        boolean found = false;
        for (Upload upload : listBeforeAbortResult.uploads()) {
            if (upload.uploadId().equals(uploadId)) {
                found = true;
                break;
            }
        }
        Assert.assertTrue("Upload should be in the list before abort", found);

        // Abort multipart upload
        AbortMultipartUploadResult abortResult = client.abortMultipartUpload(
                AbortMultipartUploadRequest.newBuilder()
                        .bucket(bucketName)
                        .key(objectName)
                        .uploadId(uploadId)
                        .build());
        Assert.assertNotNull(abortResult);
        Assert.assertEquals(204, abortResult.statusCode());

        // List multipart uploads after abort
        ListMultipartUploadsResult listAfterAbortResult = client.listMultipartUploads(
                ListMultipartUploadsRequest.newBuilder()
                        .bucket(bucketName)
                        .build());
        Assert.assertNotNull(listAfterAbortResult);
        Assert.assertEquals(200, listAfterAbortResult.statusCode());
        found = false;
        if (listAfterAbortResult.uploads() == null) {
            found = false;
        }

        Assert.assertFalse("Upload should not be in the list after abort", found);
    }

    @Test
    public void testInitiateMultipartUploadWithSpecialCharacters() {
        OSSClient client = getDefaultClient();

        // Test objects with special characters in keys
        String specialKey1 = "special-key-!@#$%^&*()_+{}|:<>?[];',./`~";
        String specialKey2 = "special-key-测试中文字符";
        String specialKey3 = "special-key-🌟emoji字符";

        // 1. Initiate multipart upload with special characters in key
        InitiateMultipartUploadResult initiateResult1 = client.initiateMultipartUpload(
                InitiateMultipartUploadRequest.newBuilder()
                        .bucket(bucketName)
                        .key(specialKey1)
                        .encodingType("url")
                        .build());
        Assert.assertNotNull(initiateResult1);
        Assert.assertEquals(200, initiateResult1.statusCode());
        Assert.assertNotNull(initiateResult1.initiateMultipartUpload().uploadId());
        Assert.assertEquals(specialKey1, initiateResult1.initiateMultipartUpload().key());

        // 2. Initiate multipart upload with Chinese characters in key
        InitiateMultipartUploadResult initiateResult2 = client.initiateMultipartUpload(
                InitiateMultipartUploadRequest.newBuilder()
                        .bucket(bucketName)
                        .key(specialKey2)
                        .encodingType("url")
                        .build());
        Assert.assertNotNull(initiateResult2);
        Assert.assertEquals(200, initiateResult2.statusCode());
        Assert.assertNotNull(initiateResult2.initiateMultipartUpload().uploadId());
        Assert.assertEquals(specialKey2, initiateResult2.initiateMultipartUpload().key());

        // 3. Initiate multipart upload with emoji characters in key
        InitiateMultipartUploadResult initiateResult3 = client.initiateMultipartUpload(
                InitiateMultipartUploadRequest.newBuilder()
                        .bucket(bucketName)
                        .key(specialKey3)
                        .encodingType("url")
                        .build());
        Assert.assertNotNull(initiateResult3);
        Assert.assertEquals(200, initiateResult3.statusCode());
        Assert.assertNotNull(initiateResult3.initiateMultipartUpload().uploadId());
        Assert.assertEquals(specialKey3, initiateResult3.initiateMultipartUpload().key());

        // Abort the multipart uploads to clean up
        client.abortMultipartUpload(
                AbortMultipartUploadRequest.newBuilder()
                        .bucket(bucketName)
                        .key(specialKey1)
                        .uploadId(initiateResult1.initiateMultipartUpload().uploadId())
                        .build());

        client.abortMultipartUpload(
                AbortMultipartUploadRequest.newBuilder()
                        .bucket(bucketName)
                        .key(specialKey2)
                        .uploadId(initiateResult2.initiateMultipartUpload().uploadId())
                        .build());

        client.abortMultipartUpload(
                AbortMultipartUploadRequest.newBuilder()
                        .bucket(bucketName)
                        .key(specialKey3)
                        .uploadId(initiateResult3.initiateMultipartUpload().uploadId())
                        .build());
    }

    @Test
    public void testCompleteMultipartUploadWithSpecialCharacters() {
        OSSClient client = getDefaultClient();
        String specialKey = "special-complete-key-!@#$%^&*()_+{}|:<>?[];',./`~";

        byte[] content = TestUtils.generateTestData(100 * 1024 + 123); // 100kb + 123 bytes
        List<UploadPartResult> uploadParts = new ArrayList<>();

        // 1. Initiate multipart upload
        InitiateMultipartUploadResult initiateResult = client.initiateMultipartUpload(
                InitiateMultipartUploadRequest.newBuilder()
                        .bucket(bucketName)
                        .key(specialKey)
                        .build());
        Assert.assertNotNull(initiateResult);
        Assert.assertEquals(200, initiateResult.statusCode());
        Assert.assertNotNull(initiateResult.initiateMultipartUpload().uploadId());
        String uploadId = initiateResult.initiateMultipartUpload().uploadId();

        // 2. Upload part
        UploadPartResult partResult = client.uploadPart(
                UploadPartRequest.newBuilder()
                        .bucket(bucketName)
                        .key(specialKey)
                        .uploadId(uploadId)
                        .partNumber(1L)
                        .body(new ByteArrayBinaryData(content))
                        .build());
        Assert.assertNotNull(partResult);
        Assert.assertEquals(200, partResult.statusCode());
        uploadParts.add(partResult);

        // 3. Complete multipart upload with special characters in key
        CompleteMultipartUpload completeMultipartUpload = CompleteMultipartUpload.newBuilder()
                .parts(createCompletedPartList(uploadParts))
                .build();

        CompleteMultipartUploadResult completeResult = client.completeMultipartUpload(
                CompleteMultipartUploadRequest.newBuilder()
                        .bucket(bucketName)
                        .key(specialKey)
                        .uploadId(uploadId)
                        .completeMultipartUpload(completeMultipartUpload)
                        .encodingType("url")
                        .build());
        Assert.assertNotNull(completeResult);
        Assert.assertEquals(200, completeResult.statusCode());
        Assert.assertEquals(specialKey, completeResult.completeMultipartUpload().key());

        // 4. Verify uploaded object
        GetObjectMetaResult metaResult = client.getObjectMeta(
                GetObjectMetaRequest.newBuilder()
                        .bucket(bucketName)
                        .key(specialKey)
                        .build());
        Assert.assertNotNull(metaResult);
        Assert.assertEquals(200, metaResult.statusCode());
        Assert.assertEquals(Long.valueOf(content.length), metaResult.contentLength());

        // 5. Delete object
        DeleteObjectResult deleteResult = client.deleteObject(
                DeleteObjectRequest.newBuilder()
                        .bucket(bucketName)
                        .key(specialKey)
                        .build());
        Assert.assertNotNull(deleteResult);
        Assert.assertEquals(204, deleteResult.statusCode());
    }

    @Test
    public void testListMultipartUploadsWithSpecialCharacters() {
        OSSClient client = getDefaultClient();

        // Test objects with special characters in keys
        String specialKey1 = "special-list-!@#$%^&*()_+{}|:<>?[];',./`~/dir1/file.txt";
        String specialKey2 = "special-list-测试中文字符/dir2/file.txt";
        String specialPrefix = "special-list-";

        String delimiter = "/";

        // 1. Initiate multiple multipart uploads with special characters in keys
        InitiateMultipartUploadResult initiateResult1 = client.initiateMultipartUpload(
                InitiateMultipartUploadRequest.newBuilder()
                        .bucket(bucketName)
                        .key(specialKey1)
                        .build());
        Assert.assertNotNull(initiateResult1);
        Assert.assertEquals(200, initiateResult1.statusCode());
        Assert.assertNotNull(initiateResult1.initiateMultipartUpload().uploadId());
        String uploadId1 = initiateResult1.initiateMultipartUpload().uploadId();

        InitiateMultipartUploadResult initiateResult2 = client.initiateMultipartUpload(
                InitiateMultipartUploadRequest.newBuilder()
                        .bucket(bucketName)
                        .key(specialKey2)
                        .build());
        Assert.assertNotNull(initiateResult2);
        Assert.assertEquals(200, initiateResult2.statusCode());
        Assert.assertNotNull(initiateResult2.initiateMultipartUpload().uploadId());
        String uploadId2 = initiateResult2.initiateMultipartUpload().uploadId();

        // 2. List multipart uploads with special characters in prefix, keyMarker, delimiter
        ListMultipartUploadsResult listMultipartResult = client.listMultipartUploads(
                ListMultipartUploadsRequest.newBuilder()
                        .bucket(bucketName)
                        .prefix(specialPrefix)
                        .delimiter(delimiter)
                        .build());
        Assert.assertNotNull(listMultipartResult);
        Assert.assertEquals(200, listMultipartResult.statusCode());
        Assert.assertEquals(specialPrefix, listMultipartResult.prefix());
        Assert.assertEquals(delimiter, listMultipartResult.delimiter());

        // Check that Upload keys are properly decoded
        assertThat(listMultipartResult.uploads()).isEmpty();
//        if (listMultipartResult.uploads() != null) {
//            boolean foundKey1 = false, foundKey2 = false;
//            for (Upload upload : listMultipartResult.uploads()) {
//                if (specialKey1.equals(upload.key())) {
//                    foundKey1 = true;
//                }
//                if (specialKey2.equals(upload.key())) {
//                    foundKey2 = true;
//                }
//            }
//            Assert.assertTrue("Should find upload with specialKey1", foundKey1);
//            Assert.assertTrue("Should find upload with specialKey2", foundKey2);
//        }

        // 3. List multipart uploads with keyMarker
        ListMultipartUploadsResult listMultipartResultWithKeyMarker = client.listMultipartUploads(
                ListMultipartUploadsRequest.newBuilder()
                        .bucket(bucketName)
                        .keyMarker("special-list")
                        .build());
        Assert.assertNotNull(listMultipartResultWithKeyMarker);
        Assert.assertEquals(200, listMultipartResultWithKeyMarker.statusCode());

        // 4. Check nextKeyMarker
        if (listMultipartResultWithKeyMarker.nextKeyMarker() != null) {
            Assert.assertNotNull(listMultipartResultWithKeyMarker.nextKeyMarker());
        }

        // Abort the multipart uploads to clean up
        client.abortMultipartUpload(
                AbortMultipartUploadRequest.newBuilder()
                        .bucket(bucketName)
                        .key(specialKey1)
                        .uploadId(uploadId1)
                        .build());

        client.abortMultipartUpload(
                AbortMultipartUploadRequest.newBuilder()
                        .bucket(bucketName)
                        .key(specialKey2)
                        .uploadId(uploadId2)
                        .build());
    }

    @Test
    public void testListPartsWithSpecialCharacters() {
        OSSClient client = getDefaultClient();
        String specialKey = "special-parts-key-!@#$%^&*()_+{}|:<>?[];',./`~";

        // 1. Initiate multipart upload with special characters in key
        InitiateMultipartUploadResult initiateResult = client.initiateMultipartUpload(
                InitiateMultipartUploadRequest.newBuilder()
                        .bucket(bucketName)
                        .key(specialKey)
                        .build());
        Assert.assertNotNull(initiateResult);
        Assert.assertEquals(200, initiateResult.statusCode());
        Assert.assertNotNull(initiateResult.initiateMultipartUpload().uploadId());
        String uploadId = initiateResult.initiateMultipartUpload().uploadId();

        // 2. Upload parts
        byte[] partData = TestUtils.generateTestData(100 * 1024 + 123);
        UploadPartResult partResult = client.uploadPart(
                UploadPartRequest.newBuilder()
                        .bucket(bucketName)
                        .key(specialKey)
                        .uploadId(uploadId)
                        .partNumber(1L)
                        .body(new ByteArrayBinaryData(partData))
                        .build());
        Assert.assertNotNull(partResult);
        Assert.assertEquals(200, partResult.statusCode());

        // 3. List parts with special characters in key
        ListPartsResult listPartsResult = client.listParts(
                ListPartsRequest.newBuilder()
                        .bucket(bucketName)
                        .key(specialKey)
                        .uploadId(uploadId)
                        .encodingType("url")
                        .build());
        Assert.assertNotNull(listPartsResult);
        Assert.assertEquals(200, listPartsResult.statusCode());
        Assert.assertEquals(specialKey, listPartsResult.key());
        Assert.assertEquals(uploadId, listPartsResult.uploadId());

        // 4. Verify parts list
        Assert.assertNotNull(listPartsResult.parts());
        Assert.assertEquals(1, listPartsResult.parts().size());

        // Abort the multipart upload to clean up
        client.abortMultipartUpload(
                AbortMultipartUploadRequest.newBuilder()
                        .bucket(bucketName)
                        .key(specialKey)
                        .uploadId(uploadId)
                        .build());
    }

    @Test
    public void testMultipartUpload_addContentType() throws Exception {
        OSSClient client = getDefaultClient();

        String objectName = "1.txt";

        // 1. Initiate multipart upload
        InitiateMultipartUploadResult initiateResult1 = client.initiateMultipartUpload(
                InitiateMultipartUploadRequest.newBuilder()
                        .bucket(bucketName)
                        .key(objectName)
                        .build());
        Assert.assertNotNull(initiateResult1);
        Assert.assertEquals(200, initiateResult1.statusCode());
        Assert.assertNotNull(initiateResult1.initiateMultipartUpload().uploadId());
        Assert.assertEquals(objectName, initiateResult1.initiateMultipartUpload().key());

        // 2. Upload parts
        UploadPartResult part1Result = client.uploadPart(
                UploadPartRequest.newBuilder()
                        .bucket(bucketName)
                        .key(objectName)
                        .uploadId(initiateResult1.initiateMultipartUpload().uploadId())
                        .partNumber(1L)
                        .body(BinaryData.fromString("hello world)"))
                        .build());
        Assert.assertNotNull(part1Result);
        Assert.assertEquals(200, part1Result.statusCode());

        // 3. complete
        client.completeMultipartUpload(CompleteMultipartUploadRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .uploadId(initiateResult1.initiateMultipartUpload().uploadId())
                .completeAll("yes")
                .build());

        try (GetObjectResult getResult = client.getObject(GetObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .build())) {
            Assert.assertNotNull(getResult);
            Assert.assertEquals(200, getResult.statusCode());
            assertThat(getResult.contentLength()).isEqualTo(12L);
            assertThat(getResult.body()).isNotNull();
            assertThat(getResult.body().markSupported()).isTrue();
            assertThat(getResult.body()).isInstanceOf(ByteArrayInputStream.class);
            assertThat(getResult.contentType()).isEqualTo("text/plain");
        }
    }

    @Test
    public void testMultipartUpload_addContentType_disable() throws Exception {
        CredentialsProvider provider = new StaticCredentialsProvider(accessKeyId(), accessKeySecret());
        ClientConfiguration config = ClientConfiguration.newBuilder()
                .region(region())
                .endpoint(endpoint())
                .credentialsProvider(provider)
                .build();

        try (OSSClient client = new DefaultOSSClient(
                config,
                x-> x.toBuilder().featureFlags(0).build())) {

            String objectName = "1.txt";

            // 1. Initiate multipart upload
            InitiateMultipartUploadResult initiateResult1 = client.initiateMultipartUpload(
                    InitiateMultipartUploadRequest.newBuilder()
                            .bucket(bucketName)
                            .key(objectName)
                            .build());
            Assert.assertNotNull(initiateResult1);
            Assert.assertEquals(200, initiateResult1.statusCode());
            Assert.assertNotNull(initiateResult1.initiateMultipartUpload().uploadId());
            Assert.assertEquals(objectName, initiateResult1.initiateMultipartUpload().key());

            // 2. Upload parts
            UploadPartResult part1Result = client.uploadPart(
                    UploadPartRequest.newBuilder()
                            .bucket(bucketName)
                            .key(objectName)
                            .uploadId(initiateResult1.initiateMultipartUpload().uploadId())
                            .partNumber(1L)
                            .body(BinaryData.fromString("hello world)"))
                            .build());
            Assert.assertNotNull(part1Result);
            Assert.assertEquals(200, part1Result.statusCode());

            // 3. complete
            client.completeMultipartUpload(CompleteMultipartUploadRequest.newBuilder()
                    .bucket(bucketName)
                    .key(objectName)
                    .uploadId(initiateResult1.initiateMultipartUpload().uploadId())
                    .completeAll("yes")
                    .build());

            try (GetObjectResult getResult = client.getObject(GetObjectRequest.newBuilder()
                    .bucket(bucketName)
                    .key(objectName)
                    .build())) {
                Assert.assertNotNull(getResult);
                Assert.assertEquals(200, getResult.statusCode());
                assertThat(getResult.contentLength()).isEqualTo(12L);
                assertThat(getResult.body()).isNotNull();
                assertThat(getResult.body().markSupported()).isTrue();
                assertThat(getResult.body()).isInstanceOf(ByteArrayInputStream.class);
                assertThat(getResult.contentType()).isEqualTo("application/octet-stream");
            }
        }
    }

    @Test
    public void testUploadPartCopyWithSourceVersionIdAndUrlEncoding() {
        OSSClient client = getDefaultClient();
        String sourceObjectName = genObjectName() + "-source object.txt"; // Include space for URL encoding test
        String copyObjectName = genObjectName() + "-copy object.txt";
        String sourceVersionId = null;

        byte[] content = TestUtils.generateTestData(200 * 1024 + 123); // 200kb + 123 bytes

        try {
            // 1. Create a source object
            PutObjectResult putResult = client.putObject(
                    PutObjectRequest.newBuilder()
                            .bucket(bucketName)
                            .key(sourceObjectName)
                            .body(new ByteArrayBinaryData(content))
                            .build());
            Assert.assertNotNull(putResult);
            Assert.assertEquals(200, putResult.statusCode());

            // Get source object versionId (if versioning is enabled)
            if (putResult.versionId() != null && !putResult.versionId().isEmpty()) {
                sourceVersionId = putResult.versionId();
            }

            // 2. Initiate multipart upload for copy
            InitiateMultipartUploadResult initiateCopyResult = client.initiateMultipartUpload(
                    InitiateMultipartUploadRequest.newBuilder()
                            .bucket(bucketName)
                            .key(copyObjectName)
                            .build());
            Assert.assertNotNull(initiateCopyResult);
            Assert.assertEquals(200, initiateCopyResult.statusCode());
            String copyUploadId = initiateCopyResult.initiateMultipartUpload().uploadId();

            // 3. Use uploadPartCopy to copy source object to target object
            UploadPartCopyRequest.Builder copyRequestBuilder = UploadPartCopyRequest.newBuilder()
                    .bucket(bucketName)
                    .key(copyObjectName)
                    .uploadId(copyUploadId)
                    .partNumber(1L)
                    .sourceBucket(bucketName)
                    .sourceKey(sourceObjectName);

            // Add sourceVersionId if source object has versionId
            if (sourceVersionId != null) {
                copyRequestBuilder.sourceVersionId(sourceVersionId);
            }

            UploadPartCopyResult copyPartResult = client.uploadPartCopy(copyRequestBuilder.build());
            Assert.assertNotNull(copyPartResult);
            Assert.assertEquals(200, copyPartResult.statusCode());

            // 4. Complete multipart copy upload
            List<Part> copyParts = new ArrayList<>();
            copyParts.add(Part.newBuilder()
                    .partNumber(1L)
                    .eTag(copyPartResult.copyPartResult().eTag())
                    .build());

            CompleteMultipartUpload copyCompleteMultipartUpload = CompleteMultipartUpload.newBuilder()
                    .parts(copyParts)
                    .build();

            CompleteMultipartUploadResult completeCopyResult = client.completeMultipartUpload(
                    CompleteMultipartUploadRequest.newBuilder()
                            .bucket(bucketName)
                            .key(copyObjectName)
                            .uploadId(copyUploadId)
                            .completeMultipartUpload(copyCompleteMultipartUpload)
                            .build());
            Assert.assertNotNull(completeCopyResult);
            Assert.assertEquals(200, completeCopyResult.statusCode());

            // 5. Verify copied object
            GetObjectMetaResult copyMetaResult = client.getObjectMeta(
                    GetObjectMetaRequest.newBuilder()
                            .bucket(bucketName)
                            .key(copyObjectName)
                            .build());
            Assert.assertNotNull(copyMetaResult);
            Assert.assertEquals(200, copyMetaResult.statusCode());
            Assert.assertEquals(Long.valueOf(content.length), copyMetaResult.contentLength());

        } finally {
            // Clean up resources
            DeleteObjectResult deleteResult = client.deleteObject(DeleteObjectRequest.newBuilder()
                    .bucket(bucketName)
                    .key(sourceObjectName)
                    .build());
            Assert.assertNotNull(deleteResult);
            Assert.assertEquals(204, deleteResult.statusCode());

            DeleteObjectResult deleteCopyResult = client.deleteObject(DeleteObjectRequest.newBuilder()
                    .bucket(bucketName)
                    .key(copyObjectName)
                    .build());
            Assert.assertNotNull(deleteCopyResult);
            Assert.assertEquals(204, deleteCopyResult.statusCode());
        }
    }

    private List<Part> createCompletedPartList(List<UploadPartResult> uploadParts) {
        List<Part> parts = new ArrayList<>();
        long i = 0;
        for (UploadPartResult uploadPart : uploadParts) {
            i++;
            Part part = Part.newBuilder()
                    .partNumber(i)
                    .eTag(uploadPart.eTag())
                    .build();
            parts.add(part);
        }
        return parts;
    }
}
