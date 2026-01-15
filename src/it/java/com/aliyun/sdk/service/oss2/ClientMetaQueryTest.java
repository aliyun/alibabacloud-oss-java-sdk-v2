package com.aliyun.sdk.service.oss2;

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
                                .order(MetaQuery.SortOrder.asc)
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
}