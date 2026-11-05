package com.aliyun.sdk.service.oss2;

import com.aliyun.sdk.service.oss2.exceptions.ServiceException;
import com.aliyun.sdk.service.oss2.models.*;
import org.junit.Assert;
import org.junit.Test;
import java.util.Arrays;

public class ClientMetaQueryTest extends TestBase {

    @Test
    public void testMetaQueryLifecycle() {
        OSSClient client = getDefaultClient();

        // 1. OpenMetaQuery
        OpenMetaQueryResult openResult = client.openMetaQuery(
                OpenMetaQueryRequest.newBuilder()
                        .bucket(bucketName)
                        .mode("basic")
                        .metaQueryOpenRequest(MetaQueryOpenRequest.newBuilder()
                                .filters(MetaQueryFilters.newBuilder()
                                        .filter(Arrays.asList("Size > 1024, FileModifiedTime > 2025-06-03T09:20:47.999Z"))
                                        .build())
                                .build())
                        .build());

        Assert.assertNotNull(openResult);
        Assert.assertEquals(200, openResult.statusCode());

        // 2. GetMetaQueryStatus
        GetMetaQueryStatusResult statusResult = client.getMetaQueryStatus(
                GetMetaQueryStatusRequest.newBuilder()
                        .bucket(bucketName)
                        .build());

        Assert.assertNotNull(statusResult);
        Assert.assertEquals(200, statusResult.statusCode());
        Assert.assertNotNull(statusResult.status());

        // 3. DoMetaQuery
        DoMetaQueryResult doResult = client.doMetaQuery(
                DoMetaQueryRequest.newBuilder()
                        .bucket(bucketName)
                        .mode("basic")
                        .metaQuery(MetaQuery.newBuilder()
                                .maxResults(5)
                                .query("{\"Field\": \"Size\",\"Value\": \"0\",\"Operation\": \"gt\"}")
                                .sort("Size")
                                .order(OrderType.ASC.toString())
                                .build())
                        .build());

        Assert.assertNotNull(doResult);
        Assert.assertEquals(200, doResult.statusCode());


        // 4. CloseMetaQuery
        CloseMetaQueryResult closeResult = client.closeMetaQuery(
                CloseMetaQueryRequest.newBuilder()
                        .bucket(bucketName)
                        .build());

        Assert.assertNotNull(closeResult);
        Assert.assertEquals(200, closeResult.statusCode());
    }

    /**
     * Opening MetaQuery twice on the same bucket makes the service return 400 with a
     * well-formed <Error> body (Code=MetaQueryAlreadyExist). The SDK must lift Code/Message
     * out of that body, so errorCode is "MetaQueryAlreadyExist" (NOT the "BadErrorResponse"
     * fallback) and the message is the service message (NOT "Not found tag <Error>").
     * This guards against the tree-flattening regression in the xml error parser.
     */
    @Test
    public void testOpenMetaQueryAlreadyExist() {
        OSSClient client = getDefaultClient();
        // Use a dedicated bucket so the leftover MetaQuery state cannot disturb other tests.
        String bucket = genBucketName();
        createBucket(bucket);

        OpenMetaQueryRequest openRequest = OpenMetaQueryRequest.newBuilder()
                .bucket(bucket)
                .mode("basic")
                .metaQueryOpenRequest(MetaQueryOpenRequest.newBuilder()
                        .filters(MetaQueryFilters.newBuilder()
                                .filter(Arrays.asList("Size > 1024"))
                                .build())
                        .build())
                .build();

        try {
            // First open creates the not-ended MetaQuery; ignore if it already exists.
            try {
                client.openMetaQuery(openRequest);
            } catch (Exception ignored) {
            }

            // Second open must be rejected with 400 MetaQueryAlreadyExist, and the error
            // body must be parsed into a proper errorCode (guards against the flattening
            // regression that would degrade it to "BadErrorResponse" / "Not found tag <Error>").
            try {
                client.openMetaQuery(openRequest);
                Assert.fail("Expected MetaQueryAlreadyExist ServiceException");
            } catch (ServiceException e) {
                Assert.assertEquals(400, e.statusCode());
                Assert.assertEquals("MetaQueryAlreadyExist", e.errorCode());
                Assert.assertNotEquals("BadErrorResponse", e.errorCode());
                Assert.assertFalse("error body should be parsed, not fall back: " + e.getMessage(),
                        e.getMessage().contains("Not found tag <Error>"));
            } catch (Exception e) {
                Assert.fail("Unexpected exception: " + e.getMessage());
            }
        } finally {
            try {
                client.closeMetaQuery(CloseMetaQueryRequest.newBuilder()
                        .bucket(bucket)
                        .build());
            } catch (Exception ignored) {
            }
            try {
                client.deleteBucket(DeleteBucketRequest.newBuilder()
                        .bucket(bucket)
                        .build());
            } catch (Exception ignored) {
            }
        }
    }
}