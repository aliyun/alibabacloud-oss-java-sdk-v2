package com.aliyun.sdk.service.oss2.models;

import org.junit.Test;

import static org.junit.Assert.*;

public class DeleteBucketRequestTest {

    @Test
    public void testEmptyBuilder() {
        DeleteBucketRequest request = DeleteBucketRequest.newBuilder().build();
        assertNotNull(request);
        assertEquals(0, request.headers().size());
        assertEquals(0, request.parameters().size());
        assertNull(request.bucket());
    }

    @Test
    public void testFullBuilder() {
        DeleteBucketRequest request = DeleteBucketRequest.newBuilder()
                .bucket("test-bucket")
                .header("x-header", "value")
                .parameter("key", "value")
                .build();

        assertNotNull(request);
        assertEquals("test-bucket", request.bucket());
        assertEquals(1, request.headers().size());
        assertEquals("value", request.headers().get("x-header"));
        assertEquals(1, request.parameters().size());
        assertEquals("value", request.parameters().get("key"));

        // Test toBuilder
        DeleteBucketRequest.Builder builder = request.toBuilder();
        DeleteBucketRequest rebuilt = builder
                .bucket("new-bucket")
                .build();

        assertEquals("new-bucket", rebuilt.bucket());
        assertEquals(1, rebuilt.headers().size());
        assertEquals("value", rebuilt.headers().get("x-header"));
        assertEquals(1, rebuilt.parameters().size());
        assertEquals("value", rebuilt.parameters().get("key"));
    }
}
