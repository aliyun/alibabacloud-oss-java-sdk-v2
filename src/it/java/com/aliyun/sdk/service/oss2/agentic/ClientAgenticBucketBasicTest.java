package com.aliyun.sdk.service.oss2.agentic;

import com.aliyun.sdk.service.oss2.agentic.models.*;
import com.aliyun.sdk.service.oss2.agentic.paginator.ListAgenticBucketsIterable;
import com.aliyun.sdk.service.oss2.exceptions.ServiceException;
import org.junit.Assert;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ClientAgenticBucketBasicTest extends TestBaseAgentic {

    @Test
    public void testAgenticBucketLifecycle() {
        OSSAgenticBucketClient client = newAgenticClient();
        String bucket = genAgenticBucketName();

        try {
            // 1. Create agentic bucket
            CreateAgenticBucketResult createResult = client.createAgenticBucket(
                    CreateAgenticBucketRequest.newBuilder()
                            .bucket(bucket)
                            .createAgenticBucketConfiguration(CreateAgenticBucketConfiguration.newBuilder()
                                    .storageClass("Standard")
                                    .dataRedundancyType("LRS")
                                    .build())
                            .build());
            Assert.assertNotNull(createResult);
            Assert.assertEquals(200, createResult.statusCode());
            waitForCacheExpiration(1);

            // 2. Get agentic bucket
            GetAgenticBucketResult getResult = client.getAgenticBucket(
                    GetAgenticBucketRequest.newBuilder().bucket(bucket).build());
            Assert.assertEquals(200, getResult.statusCode());
            Assert.assertNotNull(getResult.agenticBucketInfo());
            assertThat(getResult.agenticBucketInfo().name()).contains(bucket);

            // 3. List agentic buckets via paginator, verify the created bucket appears
            boolean found = false;
            ListAgenticBucketsIterable iterable = client.listAgenticBucketsPaginator(
                    ListAgenticBucketsRequest.newBuilder().build());
            for (ListAgenticBucketsResult page : iterable) {
                Assert.assertEquals(200, page.statusCode());
                if (page.agenticBuckets() == null) {
                    continue;
                }
                for (AgenticBucketSummary summary : page.agenticBuckets()) {
                    if (summary.name() != null && summary.name().contains(bucket)) {
                        found = true;
                    }
                }
            }
            Assert.assertTrue("created agentic bucket should appear in list", found);
        } finally {
            // 4. Delete agentic bucket
            DeleteAgenticBucketResult deleteResult = client.deleteAgenticBucket(
                    DeleteAgenticBucketRequest.newBuilder().bucket(bucket).build());
            Assert.assertNotNull(deleteResult);
            Assert.assertTrue(deleteResult.statusCode() == 200 || deleteResult.statusCode() == 204);
        }
    }

    @Test
    public void testPutAgenticBucketStatus() {
        OSSAgenticBucketClient client = newAgenticClient();
        String bucket = genAgenticBucketName();

        try {
            createAgenticBucket(client, bucket);

            PutAgenticBucketStatusResult putResult = client.putAgenticBucketStatus(
                    PutAgenticBucketStatusRequest.newBuilder()
                            .bucket(bucket)
                            .agenticBucketStatus(AgenticBucketStatus.newBuilder()
                                    .status("Enabled")
                                    .build())
                            .build());
            Assert.assertNotNull(putResult);
            Assert.assertEquals(200, putResult.statusCode());
        } finally {
            cleanAgenticBucket(bucket);
        }
    }

    @Test
    public void testGetAgenticBucketNotExist() {
        OSSAgenticBucketClient client = newAgenticClient();
        String bucket = genAgenticBucketName();

        try {
            client.getAgenticBucket(GetAgenticBucketRequest.newBuilder().bucket(bucket).build());
            Assert.fail("Expected exception not thrown");
        } catch (Exception ec) {
            ServiceException serr = findCause(ec, ServiceException.class);
            Assert.assertNotNull(serr);
            Assert.assertEquals(404, serr.statusCode());
        }
    }

    @Test
    public void testAgenticBucketInvalidCredentials() {
        OSSAgenticBucketClient client = newInvalidAkAgenticClient();
        String bucket = genAgenticBucketName();

        // Create with invalid AK
        try {
            client.createAgenticBucket(CreateAgenticBucketRequest.newBuilder().bucket(bucket).build());
            Assert.fail("Expected exception not thrown");
        } catch (Exception ec) {
            ServiceException serr = findCause(ec, ServiceException.class);
            Assert.assertNotNull(serr);
            Assert.assertEquals(403, serr.statusCode());
            assertThat(serr.requestId()).isNotEmpty();
        }

        // Get with invalid AK
        try {
            client.getAgenticBucket(GetAgenticBucketRequest.newBuilder().bucket(bucket).build());
            Assert.fail("Expected exception not thrown");
        } catch (Exception ec) {
            ServiceException serr = findCause(ec, ServiceException.class);
            Assert.assertNotNull(serr);
            Assert.assertEquals(403, serr.statusCode());
        }

        // List with invalid AK
        try {
            client.listAgenticBuckets(ListAgenticBucketsRequest.newBuilder().build());
            Assert.fail("Expected exception not thrown");
        } catch (Exception ec) {
            ServiceException serr = findCause(ec, ServiceException.class);
            Assert.assertNotNull(serr);
            Assert.assertEquals(403, serr.statusCode());
        }
    }
}
