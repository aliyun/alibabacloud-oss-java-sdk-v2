package com.aliyun.sdk.service.oss2;

import com.aliyun.sdk.service.oss2.exceptions.ServiceException;
import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import org.junit.Assert;
import org.junit.Test;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import static com.aliyun.sdk.service.oss2.internal.ClientImplMockTest.findCause;
import static org.assertj.core.api.Assertions.assertThat;

public class ClientSealAppendObjectTest extends TestBase {

    private String genObjectName() {
        long val = new Random().nextInt(5000);
        return OJBJECT_NAME_PREFIX + val;
    }

    @Test
    public void testSealAppendObjectOperations() {
        OSSClient client = getDefaultClient();
        String appendObjectName = genObjectName() + "-append";

        try {
            // Append object first part
            AppendObjectResult appendResult1 = client.appendObject(AppendObjectRequest.newBuilder()
                    .bucket(bucketName)
                    .key(appendObjectName)
                    .body(BinaryData.fromStream(new ByteArrayInputStream("Hello, ".getBytes(StandardCharsets.UTF_8))))
                    .position(0L)
                    .build());
            Assert.assertNotNull(appendResult1);
            Assert.assertEquals(200, appendResult1.statusCode());
            Assert.assertEquals(7L, appendResult1.nextAppendPosition().longValue());

            // Append object second part
            AppendObjectResult appendResult2 = client.appendObject(AppendObjectRequest.newBuilder()
                    .bucket(bucketName)
                    .key(appendObjectName)
                    .body(BinaryData.fromStream(new ByteArrayInputStream("OSS!".getBytes(StandardCharsets.UTF_8))))
                    .position(7L)
                    .build());
            Assert.assertNotNull(appendResult2);
            Assert.assertEquals(200, appendResult2.statusCode());
            Assert.assertEquals(11L, appendResult2.nextAppendPosition().longValue());

            // Seal append object
            SealAppendObjectResult sealResult = client.sealAppendObject(SealAppendObjectRequest.newBuilder()
                    .bucket(bucketName)
                    .key(appendObjectName)
                    .position("11")
                    .build());
            Assert.assertNotNull(sealResult);
            Assert.assertEquals(200, sealResult.statusCode());
            Assert.assertNotNull(sealResult.sealedTime());
            assertThat(sealResult.sealedTime()).isNotEmpty();

            // Head object on sealed object - should return x-oss-sealed-time
            HeadObjectResult headResult = client.headObject(HeadObjectRequest.newBuilder()
                    .bucket(bucketName)
                    .key(appendObjectName)
                    .build());
            Assert.assertNotNull(headResult);
            Assert.assertEquals(200, headResult.statusCode());
            Assert.assertNotNull(headResult.sealedTime());
            assertThat(headResult.sealedTime()).isNotEmpty();

            // Get object on sealed object - should return x-oss-sealed-time
            GetObjectResult getResult = client.getObject(GetObjectRequest.newBuilder()
                    .bucket(bucketName)
                    .key(appendObjectName)
                    .build());
            Assert.assertNotNull(getResult);
            Assert.assertEquals(200, getResult.statusCode());
            Assert.assertNotNull(getResult.sealedTime());
            assertThat(getResult.sealedTime()).isNotEmpty();

        } catch (Exception ec) {
            ServiceException serr = findCause(ec, ServiceException.class);
            Assert.assertEquals(400, serr.statusCode());
            Assert.assertEquals("OperationNotSupported", serr.errorCode());
            Assert.assertEquals("SealAppendable is not supported.", serr.errorMessage());
            System.out.println("SealAppendable is not supported in this environment. Test passed.");

        } finally {
            // Clean up
            client.deleteObject(DeleteObjectRequest.newBuilder()
                    .bucket(bucketName)
                    .key(appendObjectName)
                    .build());
        }
    }

    @Test
    public void testUnsealedAppendObjectOperations() {
        OSSClient client = getDefaultClient();
        String appendObjectName = genObjectName() + "-unsealed-append";

        try {
            // Append object first part
            AppendObjectResult appendResult1 = client.appendObject(AppendObjectRequest.newBuilder()
                    .bucket(bucketName)
                    .key(appendObjectName)
                    .body(BinaryData.fromStream(new ByteArrayInputStream("Hello, ".getBytes(StandardCharsets.UTF_8))))
                    .position(0L)
                    .build());
            Assert.assertNotNull(appendResult1);
            Assert.assertEquals(200, appendResult1.statusCode());
            Assert.assertEquals(7L, appendResult1.nextAppendPosition().longValue());

            // Head object on sealed object
            HeadObjectResult headResult = client.headObject(HeadObjectRequest.newBuilder()
                    .bucket(bucketName)
                    .key(appendObjectName)
                    .build());
            Assert.assertNotNull(headResult);
            Assert.assertEquals(200, headResult.statusCode());
            Assert.assertNull(headResult.sealedTime());


            // Get object on sealed object
            GetObjectResult getResult = client.getObject(GetObjectRequest.newBuilder()
                    .bucket(bucketName)
                    .key(appendObjectName)
                    .build());
            Assert.assertNotNull(getResult);
            Assert.assertEquals(200, getResult.statusCode());
            Assert.assertNull(getResult.sealedTime());
        } finally {
            // Clean up
            client.deleteObject(DeleteObjectRequest.newBuilder()
                    .bucket(bucketName)
                    .key(appendObjectName)
                    .build());
        }
    }
}
