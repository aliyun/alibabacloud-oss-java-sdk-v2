package com.aliyun.sdk.service.oss2;

import com.aliyun.sdk.service.oss2.exceptions.ServiceException;
import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import org.junit.Assert;
import org.junit.Test;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import static com.aliyun.sdk.service.oss2.internal.ClientImplMockTest.findCause;
import static org.assertj.core.api.Assertions.assertThat;

public class ClientSealAppendObjectAsyncTest extends TestBase {

    private String genObjectName() {
        long val = new Random().nextInt(5000);
        return OJBJECT_NAME_PREFIX + val;
    }

    @Test
    public void testSealAppendObjectOperations() throws ExecutionException, InterruptedException {
        OSSAsyncClient client = getDefaultAsyncClient();
        String appendObjectName = genObjectName() + "-append";

        try {
            // Append object first part
            AppendObjectResult appendResult1 = client.appendObjectAsync(AppendObjectRequest.newBuilder()
                    .bucket(bucketName)
                    .key(appendObjectName)
                    .body(BinaryData.fromStream(new ByteArrayInputStream("Hello, ".getBytes(StandardCharsets.UTF_8))))
                    .position(0L)
                    .build()).get();
            Assert.assertNotNull(appendResult1);
            Assert.assertEquals(200, appendResult1.statusCode());
            Assert.assertEquals(7L, appendResult1.nextAppendPosition().longValue());

            // Append object second part
            AppendObjectResult appendResult2 = client.appendObjectAsync(AppendObjectRequest.newBuilder()
                    .bucket(bucketName)
                    .key(appendObjectName)
                    .body(BinaryData.fromStream(new ByteArrayInputStream("OSS!".getBytes(StandardCharsets.UTF_8))))
                    .position(7L)
                    .build()).get();
            Assert.assertNotNull(appendResult2);
            Assert.assertEquals(200, appendResult2.statusCode());
            Assert.assertEquals(11L, appendResult2.nextAppendPosition().longValue());

            // Seal append object
            SealAppendObjectResult sealResult = client.sealAppendObjectAsync(SealAppendObjectRequest.newBuilder()
                    .bucket(bucketName)
                    .key(appendObjectName)
                    .position("11")
                    .build()).get();
            Assert.assertNotNull(sealResult);
            Assert.assertEquals(200, sealResult.statusCode());
            Assert.assertNotNull(sealResult.sealedTime());
            assertThat(sealResult.sealedTime()).isNotEmpty();

            // Head object on sealed object - should return x-oss-sealed-time
            HeadObjectResult headResult = client.headObjectAsync(HeadObjectRequest.newBuilder()
                    .bucket(bucketName)
                    .key(appendObjectName)
                    .build()).get();
            Assert.assertNotNull(headResult);
            Assert.assertEquals(200, headResult.statusCode());
            Assert.assertNotNull(headResult.sealedTime());
            assertThat(headResult.sealedTime()).isNotEmpty();

            // Get object on sealed object - should return x-oss-sealed-time
            GetObjectResult getResult = client.getObjectAsync(GetObjectRequest.newBuilder()
                    .bucket(bucketName)
                    .key(appendObjectName)
                    .build()).get();
            Assert.assertNotNull(getResult);
            Assert.assertEquals(200, getResult.statusCode());
            Assert.assertNotNull(getResult.sealedTime());
            assertThat(getResult.sealedTime()).isNotEmpty();

        } catch (ExecutionException ec) {
            ServiceException serr = findCause(ec, ServiceException.class);
            Assert.assertEquals(400, serr.statusCode());
            Assert.assertEquals("OperationNotSupported", serr.errorCode());
            Assert.assertEquals("SealAppendable is not supported.", serr.errorMessage());
            System.out.println("SealAppendable is not supported in this environment. Test passed.");

        } finally {
            // Clean up
            client.deleteObjectAsync(DeleteObjectRequest.newBuilder()
                    .bucket(bucketName)
                    .key(appendObjectName)
                    .build()).get();
        }
    }

    @Test
    public void testUnsealedAppendObjectOperations() throws ExecutionException, InterruptedException {
        OSSAsyncClient client = getDefaultAsyncClient();
        String appendObjectName = genObjectName() + "-unsealed-append";

        try {
            // Append object first part
            AppendObjectResult appendResult1 = client.appendObjectAsync(AppendObjectRequest.newBuilder()
                    .bucket(bucketName)
                    .key(appendObjectName)
                    .body(BinaryData.fromStream(new ByteArrayInputStream("Hello, ".getBytes(StandardCharsets.UTF_8))))
                    .position(0L)
                    .build()).get();
            Assert.assertNotNull(appendResult1);
            Assert.assertEquals(200, appendResult1.statusCode());
            Assert.assertEquals(7L, appendResult1.nextAppendPosition().longValue());

            // Head object on sealed object
            HeadObjectResult headResult = client.headObjectAsync(HeadObjectRequest.newBuilder()
                    .bucket(bucketName)
                    .key(appendObjectName)
                    .build()).get();
            Assert.assertNotNull(headResult);
            Assert.assertEquals(200, headResult.statusCode());
            Assert.assertNull(headResult.sealedTime());

            // Get object on sealed object
            GetObjectResult getResult = client.getObjectAsync(GetObjectRequest.newBuilder()
                    .bucket(bucketName)
                    .key(appendObjectName)
                    .build()).get();
            Assert.assertNotNull(getResult);
            Assert.assertEquals(200, getResult.statusCode());
            Assert.assertNull(getResult.sealedTime());
        } finally {
            // Clean up
            client.deleteObjectAsync(DeleteObjectRequest.newBuilder()
                    .bucket(bucketName)
                    .key(appendObjectName)
                    .build()).get();
        }
    }
}