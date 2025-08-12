package com.aliyun.sdk.service.oss2;


import com.aliyun.sdk.service.oss2.credentials.CredentialsProvider;
import com.aliyun.sdk.service.oss2.credentials.StaticCredentialsProvider;
import com.aliyun.sdk.service.oss2.exceptions.ServiceError;
import com.aliyun.sdk.service.oss2.internal.TestUtils;
import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.progress.ProgressListener;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.transport.ByteArrayBinaryData;
import com.aliyun.sdk.service.oss2.transport.StringBinaryData;
import com.aliyun.sdk.service.oss2.utils.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Random;

import static com.aliyun.sdk.service.oss2.internal.ClientImplMockTest.findCause;

public class ClientObjectTest extends TestBase {

    @Test
    public void testObjectOperations() {
        OSSClient client = getDefaultClient();
        String objectName = genObjectName();

        class MockProgressListener implements ProgressListener {
            public long total;
            public long incTotal;
            public long transferred;
            public boolean finished;

            public MockProgressListener() {
                this.total = 0;
                this.incTotal = 0;
                this.transferred = 0;
                this.finished = false;
            }

            @Override
            public void onProgress(long increment, long transferred, long total) {
                this.incTotal += increment;
                this.total = total;
                this.transferred = transferred;

                int rate;
                if (total > 0) {
                    rate = (int) (100.0 * ((double) transferred / (double) total));
                } else {
                    rate = 0;
                }

                System.out.println("\r" + rate + "% ");


            }

            @Override
            public void onFinish() {
                this.finished = true;
            }
        }

        byte[] content = TestUtils.generateTestData(10 * 1024 + 123);
        MockProgressListener progListener = new MockProgressListener();
        // put object
        PutObjectResult putResult = client.putObject(PutObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .progressListener(progListener)
                .body(new ByteArrayBinaryData(content))
                .build());
        Assert.assertNotNull(putResult);
        Assert.assertEquals(200, putResult.statusCode());

        // get object
        GetObjectResult getResult = client.getObject(GetObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .build());
        Assert.assertNotNull(getResult);
        Assert.assertEquals(200, getResult.statusCode());

        // get object meta
        GetObjectMetaResult metaResult = client.getObjectMeta(GetObjectMetaRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .build());
        Assert.assertNotNull(metaResult);
        Assert.assertEquals(200, metaResult.statusCode());
        Assert.assertEquals(Long.valueOf(content.length), metaResult.contentLength());

        // copy object
        String copyObjectName = genObjectName() + "-copy";
        CopyObjectResult copyResult = client.copyObject(CopyObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(copyObjectName)
                .sourceBucket(bucketName)
                .sourceKey(objectName)
                .build());
        Assert.assertNotNull(copyResult);
        Assert.assertEquals(200, copyResult.statusCode());

        // append object
        String appendObjectName = genObjectName() + "-append";
        AppendObjectResult appendResult1 = client.appendObject(AppendObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(appendObjectName)
                .body(new ByteArrayBinaryData(" more content".getBytes(StandardCharsets.UTF_8)))
                .position(0L)
                .build());
        Assert.assertNotNull(appendResult1);
        Assert.assertEquals(200, appendResult1.statusCode());
        Assert.assertEquals(13L, appendResult1.nextAppendPosition().longValue());

        // append
        AppendObjectResult appendResult2 = client.appendObject(AppendObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(appendObjectName)
                .body(new ByteArrayBinaryData(" more more content".getBytes(StandardCharsets.UTF_8)))
                .position(13L)
                .build());
        Assert.assertNotNull(appendResult2);
        Assert.assertEquals(200, appendResult2.statusCode());
        Assert.assertEquals(31L, appendResult2.nextAppendPosition().longValue());

        // head object
        HeadObjectResult headResult = client.headObject(HeadObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(appendObjectName)
                .build());
        Assert.assertNotNull(headResult);
        Assert.assertEquals(200, headResult.statusCode());
        Assert.assertEquals(Long.valueOf(31L), headResult.contentLength());

        // delete object
        DeleteObjectResult deleteResult = client.deleteObject(DeleteObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .build());
        Assert.assertNotNull(deleteResult);
        Assert.assertEquals(204, deleteResult.statusCode());

        // delete copy object
        DeleteObjectResult deleteCopyResult = client.deleteObject(DeleteObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(copyObjectName)
                .build());
        Assert.assertNotNull(deleteCopyResult);
        Assert.assertEquals(204, deleteCopyResult.statusCode());

        // delete append object
        DeleteObjectResult deleteAppendResult = client.deleteObject(DeleteObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(appendObjectName)
                .build());
        Assert.assertNotNull(deleteAppendResult);
        Assert.assertEquals(204, deleteAppendResult.statusCode());

        // put cold object
        String coldObjectName = genObjectName() + "-cold";
        client.putObject(PutObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(coldObjectName)
                .storageClass("ColdArchive")
                .body(new ByteArrayBinaryData("cold content".getBytes(StandardCharsets.UTF_8)))
                .build());

        // restore object
        RestoreObjectResult restoreResult = client.restoreObject(RestoreObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(coldObjectName)
                .build());
        Assert.assertNotNull(restoreResult);
        Assert.assertEquals(202, restoreResult.statusCode());

        // clean restored object
        try {
            CleanRestoredObjectResult cleanResult = client.cleanRestoredObject(CleanRestoredObjectRequest.newBuilder()
                    .bucket(bucketName)
                    .key(coldObjectName)
                    .build());
            Assert.assertNotNull(cleanResult);
            Assert.assertEquals(200, cleanResult.statusCode());
        } catch (Exception ec) {
            ServiceError serr = findCause(ec, ServiceError.class);
            Assert.assertEquals(409, serr.statusCode());
            Assert.assertEquals("ArchiveRestoreNotFinished", serr.errorCode());
            Assert.assertEquals(24, serr.requestId().length());
            Assert.assertEquals("The archive file's restore is not finished.", serr.errorMessage());
            Assert.assertEquals("0016-00000719", serr.ec());
        }

        // delete cold object
        client.deleteObject(DeleteObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(coldObjectName)
                .build());
    }

    @Test
    public void testGetObject_checkBodyType() throws Exception {
        OSSClient client = getDefaultClient();
        String objectNamePrefix = genObjectName();
        String objectName;
        byte[] content;

        // empty
        objectName = objectNamePrefix + "empty";
        PutObjectResult putResult = client.putObject(PutObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .build());
        Assert.assertNotNull(putResult);
        Assert.assertEquals(200, putResult.statusCode());

        try (GetObjectResult getResult = client.getObject(GetObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .build())) {
            Assert.assertNotNull(getResult);
            Assert.assertEquals(200, putResult.statusCode());
            assertThat(getResult.contentLength()).isEqualTo(0L);
            assertThat(getResult.body()).isNotNull();
            assertThat(getResult.body().markSupported()).isTrue();
            assertThat(getResult.body()).isInstanceOf(ByteArrayInputStream.class);
            // test close multi times
            getResult.close();
        }

        // <=32K, is non stream mode
        objectName = objectNamePrefix + "32K";
        content = TestUtils.generateTestData(32 * 1024);
        putResult = client.putObject(PutObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .body(BinaryData.fromBytes(content))
                .build());
        Assert.assertNotNull(putResult);
        Assert.assertEquals(200, putResult.statusCode());

        try (GetObjectResult getResult = client.getObject(GetObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .build())) {
            Assert.assertNotNull(getResult);
            Assert.assertEquals(200, putResult.statusCode());
            assertThat(getResult.contentLength()).isEqualTo(32*1024L);
            assertThat(getResult.body()).isNotNull();
            assertThat(getResult.body()).isInstanceOf(ByteArrayInputStream.class);
            assertThat(getResult.body().markSupported()).isTrue();
            assertThat(IOUtils.toByteArray(getResult.body())).isEqualTo(content);
        }

        // delete cold object
        client.deleteObject(DeleteObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .build());

        // >32K is stream mode
        objectName = objectNamePrefix + "32K+1";
        content = TestUtils.generateTestData(32 * 1024 + 1);
        putResult = client.putObject(PutObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .body(BinaryData.fromBytes(content))
                .build());
        Assert.assertNotNull(putResult);
        Assert.assertEquals(200, putResult.statusCode());

        try (GetObjectResult getResult = client.getObject(GetObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .build())) {
            Assert.assertNotNull(getResult);
            Assert.assertEquals(200, putResult.statusCode());
            assertThat(getResult.contentLength()).isEqualTo(32 * 1024L + 1);
            assertThat(getResult.body()).isNotNull();
            assertThat(getResult.body()).isNotInstanceOf(ByteArrayInputStream.class);
            assertThat(getResult.body().markSupported()).isFalse();
            assertThat(IOUtils.toByteArray(getResult.body())).isEqualTo(content);

        }

        // delete cold object
        client.deleteObject(DeleteObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .build());

        // 64K is stream mode
        objectName = objectNamePrefix + "64K";
        content = TestUtils.generateTestData(64 * 1024);
        putResult = client.putObject(PutObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .body(BinaryData.fromBytes(content))
                .build());
        Assert.assertNotNull(putResult);
        Assert.assertEquals(200, putResult.statusCode());

        try (GetObjectResult getResult = client.getObject(GetObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .build())) {
            Assert.assertNotNull(getResult);
            Assert.assertEquals(200, putResult.statusCode());
            assertThat(getResult.contentLength()).isEqualTo(64 * 1024L);
            assertThat(getResult.body()).isNotNull();
            assertThat(getResult.body()).isNotInstanceOf(ByteArrayInputStream.class);
            assertThat(getResult.body().markSupported()).isFalse();
            assertThat(IOUtils.toByteArray(getResult.body())).isEqualTo(content);
        }

        // delete cold object
        client.deleteObject(DeleteObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .build());
    }

    @Test
    public void testGetObject_abortBody() throws Exception {
        OSSClient client = getDefaultClient();
        String objectNamePrefix = genObjectName();
        String objectName;
        byte[] content;

        // <=32K, is non stream mode
        objectName = objectNamePrefix + "32K";
        content = TestUtils.generateTestData(32 * 1024);
        PutObjectResult putResult = client.putObject(PutObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .body(BinaryData.fromBytes(content))
                .build());
        Assert.assertNotNull(putResult);
        Assert.assertEquals(200, putResult.statusCode());

        try (GetObjectResult getResult = client.getObject(GetObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .build())) {
            Assert.assertNotNull(getResult);
            Assert.assertEquals(200, putResult.statusCode());
            assertThat(getResult.contentLength()).isEqualTo(32*1024L);
            assertThat(getResult.body()).isNotNull();
            assertThat(getResult.body()).isInstanceOf(ByteArrayInputStream.class);
            assertThat(getResult.body().markSupported()).isTrue();

            // no effect
            getResult.abort();
            assertThat(IOUtils.toByteArray(getResult.body())).isEqualTo(content);
        }

        // 64K is stream mode
        objectName = objectNamePrefix + "64K";
        content = TestUtils.generateTestData(64 * 1024);
        putResult = client.putObject(PutObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .body(BinaryData.fromBytes(content))
                .build());
        Assert.assertNotNull(putResult);
        Assert.assertEquals(200, putResult.statusCode());

        try (GetObjectResult getResult = client.getObject(GetObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .build())) {
            Assert.assertNotNull(getResult);
            Assert.assertEquals(200, putResult.statusCode());
            assertThat(getResult.contentLength()).isEqualTo(64 * 1024L);
            assertThat(getResult.body()).isNotNull();
            assertThat(getResult.body()).isNotInstanceOf(ByteArrayInputStream.class);
            assertThat(getResult.body().markSupported()).isFalse();

            try {
                // effect, throws error
                getResult.abort();
                assertThat(IOUtils.toByteArray(getResult.body())).isEqualTo(content);
            } catch (Exception e) {
                assertThat(e.toString()).containsAnyOf("socket closed", "ConnectionClosedException", "Stream already closed");
            }
        }
    }

    @Test
    public void testUploadObject_addContentType () throws Exception {
        OSSClient client = getDefaultClient();
        String objectNamePrefix = genObjectName();
        String objectName;

        // put object
        objectName = objectNamePrefix + "put-object.txt";
        PutObjectResult putResult = client.putObject(PutObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .build());
        Assert.assertNotNull(putResult);
        Assert.assertEquals(200, putResult.statusCode());

        try (GetObjectResult getResult = client.getObject(GetObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .build())) {
            Assert.assertNotNull(getResult);
            Assert.assertEquals(200, putResult.statusCode());
            assertThat(getResult.contentLength()).isEqualTo(0L);
            assertThat(getResult.body()).isNotNull();
            assertThat(getResult.body().markSupported()).isTrue();
            assertThat(getResult.body()).isInstanceOf(ByteArrayInputStream.class);
            assertThat(getResult.contentType()).isEqualTo("text/plain");
        }

        // append object
        objectName = objectNamePrefix + "append-object.jpg";
        AppendObjectResult appendResult = client.appendObject(AppendObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .position(0L)
                .body(BinaryData.fromString("hello world"))
                .build());
        Assert.assertNotNull(putResult);
        Assert.assertEquals(200, putResult.statusCode());

        try (GetObjectResult getResult = client.getObject(GetObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .build())) {
            Assert.assertNotNull(getResult);
            Assert.assertEquals(200, putResult.statusCode());
            assertThat(getResult.contentLength()).isEqualTo(11L);
            assertThat(getResult.body()).isNotNull();
            assertThat(getResult.body().markSupported()).isTrue();
            assertThat(getResult.body()).isInstanceOf(ByteArrayInputStream.class);
            assertThat(getResult.contentType()).isEqualTo("image/jpeg");
        }
    }

    @Test
    public void testUploadObject_addContentType_disable () throws Exception {
        CredentialsProvider provider = new StaticCredentialsProvider(accessKeyId(), accessKeySecret());
        ClientConfiguration config = ClientConfiguration.newBuilder()
                .region(region())
                .endpoint(endpoint())
                .credentialsProvider(provider)
                .build();

        try (OSSClient client = new DefaultOSSClient(
                config,
                x-> x.toBuilder().featureFlags(0).build())) {

            String objectNamePrefix = genObjectName();
            String objectName;

            // put object
            objectName = objectNamePrefix + "put-object.txt";
            PutObjectResult putResult = client.putObject(PutObjectRequest.newBuilder()
                    .bucket(bucketName)
                    .key(objectName)
                    .build());
            Assert.assertNotNull(putResult);
            Assert.assertEquals(200, putResult.statusCode());

            try (GetObjectResult getResult = client.getObject(GetObjectRequest.newBuilder()
                    .bucket(bucketName)
                    .key(objectName)
                    .build())) {
                Assert.assertNotNull(getResult);
                Assert.assertEquals(200, putResult.statusCode());
                assertThat(getResult.contentLength()).isEqualTo(0L);
                assertThat(getResult.body()).isNotNull();
                assertThat(getResult.body().markSupported()).isTrue();
                assertThat(getResult.body()).isInstanceOf(ByteArrayInputStream.class);
                assertThat(getResult.contentType()).isEqualTo("application/octet-stream");
            }

            // append object
            objectName = objectNamePrefix + "append-object.jpg";
            AppendObjectResult appendResult = client.appendObject(AppendObjectRequest.newBuilder()
                    .bucket(bucketName)
                    .key(objectName)
                    .position(0L)
                    .body(BinaryData.fromString("hello world"))
                    .build());
            Assert.assertNotNull(putResult);
            Assert.assertEquals(200, putResult.statusCode());

            try (GetObjectResult getResult = client.getObject(GetObjectRequest.newBuilder()
                    .bucket(bucketName)
                    .key(objectName)
                    .build())) {
                Assert.assertNotNull(getResult);
                Assert.assertEquals(200, putResult.statusCode());
                assertThat(getResult.contentLength()).isEqualTo(11L);
                assertThat(getResult.body()).isNotNull();
                assertThat(getResult.body().markSupported()).isTrue();
                assertThat(getResult.body()).isInstanceOf(ByteArrayInputStream.class);
                assertThat(getResult.contentType()).isEqualTo("application/octet-stream");
            }
        }
    }

    @Test
    public void testCopyObjectWithSourceVersionIdAndUrlEncoding() {
        OSSClient client = getDefaultClient();
        String sourceObjectName = genObjectName() + "-source object.txt"; // Include space for URL encoding test
        String copyObjectName = genObjectName() + "-copy object.txt";
        String sourceVersionId = null;

        byte[] content = TestUtils.generateTestData(10 * 1024 + 123);

        try {
            // 1. Create a source object
            PutObjectResult putResult = client.putObject(PutObjectRequest.newBuilder()
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

            // 2. Use copyObject to copy source object to target object
            CopyObjectRequest.Builder copyRequestBuilder = CopyObjectRequest.newBuilder()
                    .bucket(bucketName)
                    .key(copyObjectName)
                    .sourceBucket(bucketName)
                    .sourceKey(sourceObjectName);

            // Add sourceVersionId if source object has versionId
            if (sourceVersionId != null) {
                copyRequestBuilder.sourceVersionId(sourceVersionId);
            }

            CopyObjectResult copyResult = client.copyObject(copyRequestBuilder.build());
            Assert.assertNotNull(copyResult);
            Assert.assertEquals(200, copyResult.statusCode());

            // 3. Verify copied object
            GetObjectMetaResult copyMetaResult = client.getObjectMeta(GetObjectMetaRequest.newBuilder()
                    .bucket(bucketName)
                    .key(copyObjectName)
                    .build());
            Assert.assertNotNull(copyMetaResult);
            Assert.assertEquals(200, copyMetaResult.statusCode());
            Assert.assertEquals(Long.valueOf(content.length), copyMetaResult.contentLength());

        } finally {
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

    private String genObjectName() {
        long ticks = new Date().getTime() / 1000;
        long val = new Random().nextInt(5000);
        return OJBJECT_NAME_PREFIX + ticks + "-" + val;
    }
}
