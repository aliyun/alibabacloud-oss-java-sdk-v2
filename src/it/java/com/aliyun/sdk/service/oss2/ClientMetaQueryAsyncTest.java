package com.aliyun.sdk.service.oss2;

import com.aliyun.sdk.service.oss2.models.*;
import org.junit.Assert;
import org.junit.Test;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

public class ClientMetaQueryAsyncTest extends TestBase {

    @Test
    public void testMetaQueryAsyncLifecycle() throws Exception {
        OSSAsyncClient client = getDefaultAsyncClient();

        // 1. OpenMetaQuery
        CompletableFuture<OpenMetaQueryResult> openFuture = client.openMetaQueryAsync(
                OpenMetaQueryRequest.newBuilder()
                        .bucket(bucketName)
                        .mode("basic")
                        .metaQueryOpenRequest(MetaQueryOpenRequest.newBuilder()
                                .filters(MetaQueryFilters.newBuilder()
                                        .filter(Arrays.asList("Size > 1024, FileModifiedTime > 2025-06-03T09:20:47.999Z","Filename prefix (YWEvYmIv)"))
                                        .build())
                                .build())
                        .build());

        OpenMetaQueryResult openResult = openFuture.get();

        Assert.assertNotNull(openResult);
        Assert.assertEquals(200, openResult.statusCode());

        // 2. GetMetaQueryStatus
        CompletableFuture<GetMetaQueryStatusResult> statusFuture = client.getMetaQueryStatusAsync(
                GetMetaQueryStatusRequest.newBuilder()
                        .bucket(bucketName)
                        .build());

        GetMetaQueryStatusResult statusResult = statusFuture.get();

        Assert.assertNotNull(statusResult);
        Assert.assertEquals(200, statusResult.statusCode());
        Assert.assertNotNull(statusResult.status());

        // 3. DoMetaQuery
        CompletableFuture<DoMetaQueryResult> doFuture = client.doMetaQueryAsync(
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

        DoMetaQueryResult doResult = doFuture.get();

        Assert.assertNotNull(doResult);
        Assert.assertEquals(200, doResult.statusCode());


        // 4. CloseMetaQuery
        CompletableFuture<CloseMetaQueryResult> closeFuture = client.closeMetaQueryAsync(
                CloseMetaQueryRequest.newBuilder()
                        .bucket(bucketName)
                        .build());

        CloseMetaQueryResult closeResult = closeFuture.get();

        Assert.assertNotNull(closeResult);
        Assert.assertEquals(200, closeResult.statusCode());
    }
}