package com.aliyun.sdk.service.oss2;

import com.aliyun.sdk.service.oss2.credentials.CredentialsProvider;
import com.aliyun.sdk.service.oss2.credentials.StaticCredentialsProvider;
import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.IOUtils;
import org.junit.Assert;
import org.junit.Test;

public class ClientMiscTest extends TestBase {

    @Test
    public void testInvokeOperation() {
        Assert.assertTrue(true);
    }

    @Test
    public void useApache4HttpClientSendRequest() throws Exception {
        CredentialsProvider provider = new StaticCredentialsProvider(accessKeyId(), accessKeySecret());
        try (OSSClient client = OSSClient.newBuilder()
                .region(region())
                .endpoint(endpoint())
                .useApacheHttpClient4(true)
                .credentialsProvider(provider)
                .build()) {

            client.getBucketInfo(GetBucketInfoRequest.newBuilder().bucket(bucketName).build());

            String objectName = "1.txt";
            byte[] content = "hello world".getBytes();

            // put object
            PutObjectResult putResult = client.putObject(PutObjectRequest.newBuilder()
                    .bucket(bucketName)
                    .key(objectName)
                    .body(BinaryData.fromBytes(content))
                    .build());
            Assert.assertNotNull(putResult);
            Assert.assertEquals(200, putResult.statusCode());

            // get object
            try (GetObjectResult getResult = client.getObject(GetObjectRequest.newBuilder()
                    .bucket(bucketName)
                    .key(objectName)
                    .build())) {
                Assert.assertNotNull(getResult);
                byte[] gotData = IOUtils.toByteArray(getResult.body());
                Assert.assertEquals(200, getResult.statusCode());
                Assert.assertArrayEquals(content, gotData);
            }

            // get object meta
            GetObjectMetaResult metaResult = client.getObjectMeta(GetObjectMetaRequest.newBuilder()
                    .bucket(bucketName)
                    .key(objectName)
                    .build());
            Assert.assertNotNull(metaResult);
            Assert.assertEquals(200, metaResult.statusCode());
            Assert.assertEquals(Long.valueOf(content.length), metaResult.contentLength());

            client.deleteObject(DeleteObjectRequest.newBuilder()
                    .bucket(bucketName)
                    .key(objectName)
                    .build());
        }
    }
}
