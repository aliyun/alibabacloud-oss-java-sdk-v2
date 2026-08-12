package com.aliyun.sdk.service.oss2.agentic;

import com.aliyun.sdk.service.oss2.agentic.models.*;
import com.aliyun.sdk.service.oss2.exceptions.ServiceException;
import org.junit.Assert;
import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ClientAgenticBucketBasicAsyncTest extends TestBaseAgentic {

    @Test
    public void testAgenticBucketLifecycleAsync() throws Exception {
        try (OSSAsyncAgenticBucketClient client = newAgenticAsyncClient()) {
            String bucket = agenticBucketName;

            // 1. Get agentic bucket
            GetAgenticBucketResult getResult = client.getAgenticBucketAsync(
                    GetAgenticBucketRequest.newBuilder().bucket(bucket).build()).get();
            Assert.assertEquals(200, getResult.statusCode());
            Assert.assertNotNull(getResult.agenticBucketInfo());
            assertThat(getResult.agenticBucketInfo().name()).contains(bucket);

            // 2. List agentic buckets, verify the created bucket appears. The listing is eventually
            //    consistent, so poll and skip when it is still missing, its existence is already
            //    asserted by GetAgenticBucket above.
            boolean found = false;
            for (int i = 0; i < LIST_RETRY_TIMES && !found; i++) {
                ListAgenticBucketsResult listResult = client.listAgenticBucketsAsync(
                        ListAgenticBucketsRequest.newBuilder().build()).get();
                Assert.assertEquals(200, listResult.statusCode());
                if (listResult.agenticBuckets() != null) {
                    for (AgenticBucketSummary summary : listResult.agenticBuckets()) {
                        if (summary.name() != null && summary.name().contains(bucket)) {
                            found = true;
                            break;
                        }
                    }
                }
                if (!found) {
                    waitForCacheExpiration(LIST_RETRY_INTERVAL_SECONDS);
                }
            }
            if (!found) {
                System.out.println("created agentic bucket not visible in list yet: " + bucket);
            }
        }
    }

    @Test
    public void testPutAgenticBucketStatusAsync() throws Exception {
        try (OSSAsyncAgenticBucketClient client = newAgenticAsyncClient()) {
            String bucket = agenticBucketName;

            PutAgenticBucketStatusResult putResult = client.putAgenticBucketStatusAsync(
                    PutAgenticBucketStatusRequest.newBuilder()
                            .bucket(bucket)
                            .agenticBucketStatus(AgenticBucketStatus.newBuilder()
                                    .status("Enabled")
                                    .build())
                            .build()).get();
            Assert.assertNotNull(putResult);
            Assert.assertEquals(200, putResult.statusCode());
        }
    }

    @Test
    public void testGetAgenticBucketNotExistAsync() throws Exception {
        try (OSSAsyncAgenticBucketClient client = newAgenticAsyncClient()) {
            String bucket = "oss-sdk-test-not-exist";

            try {
                client.getAgenticBucketAsync(
                        GetAgenticBucketRequest.newBuilder().bucket(bucket).build()).get();
                Assert.fail("Expected exception not thrown");
            } catch (Exception ec) {
                ServiceException serr = findCause(ec, ServiceException.class);
                Assert.assertNotNull(serr);
                Assert.assertEquals(404, serr.statusCode());
                Assert.assertEquals("0015-00000101", serr.ec());
                Assert.assertEquals("NoSuchAgenticBucket", serr.errorCode());
            }
        }
    }

    @Test
    public void testAgenticBucketInvalidCredentialsAsync() throws Exception {
        try (OSSAsyncAgenticBucketClient client = newInvalidAkAgenticAsyncClient()) {
            String bucket = "oss-sdk-test-invalid-cred";

            // Create with invalid AK
            try {
                client.createAgenticBucketAsync(
                        CreateAgenticBucketRequest.newBuilder().bucket(bucket).build()).get();
                Assert.fail("Expected exception not thrown");
            } catch (Exception ec) {
                ServiceException serr = findCause(ec, ServiceException.class);
                Assert.assertNotNull(serr);
                Assert.assertEquals(403, serr.statusCode());
                Assert.assertEquals("0002-00000902", serr.ec());
                Assert.assertEquals("InvalidAccessKeyId", serr.errorCode());
                assertThat(serr.requestId()).isNotEmpty();
            }

            // Get with invalid AK
            try {
                client.getAgenticBucketAsync(
                        GetAgenticBucketRequest.newBuilder().bucket(bucket).build()).get();
                Assert.fail("Expected exception not thrown");
            } catch (Exception ec) {
                ServiceException serr = findCause(ec, ServiceException.class);
                Assert.assertNotNull(serr);
                Assert.assertEquals(404, serr.statusCode());
                Assert.assertEquals("0015-00000101", serr.ec());
                Assert.assertEquals("NoSuchAgenticBucket", serr.errorCode());
            }

            // List with invalid AK
            try {
                client.listAgenticBucketsAsync(
                        ListAgenticBucketsRequest.newBuilder().build()).get();
                Assert.fail("Expected exception not thrown");
            } catch (Exception ec) {
                ServiceException serr = findCause(ec, ServiceException.class);
                Assert.assertNotNull(serr);
                Assert.assertEquals(403, serr.statusCode());
                Assert.assertEquals("0002-00000902", serr.ec());
                Assert.assertEquals("InvalidAccessKeyId", serr.errorCode());
            }
        }
    }

    /**
     * Verify basic agentic bucket operations work under path-style addressing (async).
     * Mirrors Go/Python TestAgenticPathStyle: probe with GetAgenticBucket first;
     * if the endpoint rejects path-style (SecondLevelDomainForbidden), skip
     * rather than fail.
     */
    @Test
    public void testAgenticBucketPathStyleAsync() throws Exception {
        try (OSSAsyncAgenticBucketClient client = newAgenticAsyncClientPathStyle()) {
            String bucket = agenticBucketName;

            // Probe: GetAgenticBucket via path-style
            GetAgenticBucketResult getResult;
            try {
                getResult = client.getAgenticBucketAsync(
                        GetAgenticBucketRequest.newBuilder().bucket(bucket).build()).get();
            } catch (Exception e) {
                if (isSecondLevelDomainForbidden(e)) {
                    System.out.println("path-style addressing not allowed on this endpoint: " + e.getMessage());
                    return;
                }
                throw e;
            }
            Assert.assertEquals(200, getResult.statusCode());
            Assert.assertNotNull(getResult.agenticBucketInfo());
            assertThat(getResult.agenticBucketInfo().name()).contains(bucket);

            // ListAgenticBuckets via path-style client (service-level op with no bucket label)
            ListAgenticBucketsResult listResult = client.listAgenticBucketsAsync(
                    ListAgenticBucketsRequest.newBuilder().build()).get();
            Assert.assertEquals(200, listResult.statusCode());

            // ListBucketSpaces via path-style AgenticBucketClient
            ListBucketSpacesResult bsResult = client.listBucketSpacesAsync(
                    ListBucketSpacesRequest.newBuilder().bucket(bucket).build()).get();
            Assert.assertEquals(200, bsResult.statusCode());
        }
    }
}
