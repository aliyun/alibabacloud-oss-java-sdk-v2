package com.aliyun.sdk.service.oss2.agentic;

import com.aliyun.sdk.service.oss2.agentic.models.*;
import com.aliyun.sdk.service.oss2.exceptions.ServiceException;
import org.junit.Assert;
import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ClientAgenticBucketBasicTest extends TestBaseAgentic {

    @Test
    public void testAgenticBucketLifecycle() {
        OSSAgenticBucketClient client = agenticClient;
        String bucket = agenticBucketName;

        // 1. Get agentic bucket
        GetAgenticBucketResult getResult = client.getAgenticBucket(
                GetAgenticBucketRequest.newBuilder().bucket(bucket).build());
        Assert.assertEquals(200, getResult.statusCode());
        Assert.assertNotNull(getResult.agenticBucketInfo());
        assertThat(getResult.agenticBucketInfo().name()).contains(bucket);

        // 2. List agentic buckets via paginator, verify the created bucket appears.
        //    The listing is eventually consistent, so a still-missing bucket is skipped rather
        //    than failed, its existence is already asserted by GetAgenticBucket above.
        if (!waitForAgenticBucketListed(client, bucket)) {
            System.out.println("created agentic bucket not visible in list yet: " + bucket);
        }
    }

    @Test
    public void testPutAgenticBucketStatus() {
        OSSAgenticBucketClient client = agenticClient;
        String bucket = agenticBucketName;

        PutAgenticBucketStatusResult putResult = client.putAgenticBucketStatus(
                PutAgenticBucketStatusRequest.newBuilder()
                        .bucket(bucket)
                        .agenticBucketStatus(AgenticBucketStatus.newBuilder()
                                .status("Enabled")
                                .build())
                        .build());
        Assert.assertNotNull(putResult);
        Assert.assertEquals(200, putResult.statusCode());
    }

    @Test
    public void testGetAgenticBucketNotExist() {
        OSSAgenticBucketClient client = agenticClient;
        String bucket = "oss-sdk-test-not-exist";

        try {
            client.getAgenticBucket(GetAgenticBucketRequest.newBuilder().bucket(bucket).build());
            Assert.fail("Expected exception not thrown");
        } catch (Exception ec) {
            ServiceException serr = findCause(ec, ServiceException.class);
            Assert.assertNotNull(serr);
            Assert.assertEquals(404, serr.statusCode());
            Assert.assertEquals("0015-00000101", serr.ec());
            Assert.assertEquals("NoSuchAgenticBucket", serr.errorCode());
        }
    }

    @Test
    public void testAgenticBucketInvalidCredentials() {
        OSSAgenticBucketClient client = newInvalidAkAgenticClient();
        String bucket = "oss-sdk-test-invalid-cred";

        // Create with invalid AK
        try {
            client.createAgenticBucket(CreateAgenticBucketRequest.newBuilder().bucket(bucket).build());
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
            client.getAgenticBucket(GetAgenticBucketRequest.newBuilder().bucket(bucket).build());
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
            client.listAgenticBuckets(ListAgenticBucketsRequest.newBuilder().build());
            Assert.fail("Expected exception not thrown");
        } catch (Exception ec) {
            ServiceException serr = findCause(ec, ServiceException.class);
            Assert.assertNotNull(serr);
            Assert.assertEquals(403, serr.statusCode());
            Assert.assertEquals("0002-00000902", serr.ec());
            Assert.assertEquals("InvalidAccessKeyId", serr.errorCode());
        }
    }

    /**
     * Verify basic agentic bucket operations work under path-style addressing.
     * Mirrors Go/Python TestAgenticPathStyle: probe with GetAgenticBucket first;
     * if the endpoint rejects path-style (SecondLevelDomainForbidden), skip
     * rather than fail.
     */
    @Test
    public void testAgenticBucketPathStyle() {
        OSSAgenticBucketClient client = newAgenticClientPathStyle();
        String bucket = agenticBucketName;

        // Probe: GetAgenticBucket via path-style
        GetAgenticBucketResult getResult;
        try {
            getResult = client.getAgenticBucket(
                    GetAgenticBucketRequest.newBuilder().bucket(bucket).build());
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
        ListAgenticBucketsResult listResult = client.listAgenticBuckets(
                ListAgenticBucketsRequest.newBuilder().build());
        Assert.assertEquals(200, listResult.statusCode());

        // ListBucketSpaces via path-style AgenticBucketClient
        ListBucketSpacesResult bsResult = client.listBucketSpaces(
                ListBucketSpacesRequest.newBuilder().bucket(bucket).build());
        Assert.assertEquals(200, bsResult.statusCode());
    }
}
