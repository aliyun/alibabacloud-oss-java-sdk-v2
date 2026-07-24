package com.aliyun.sdk.service.oss2.agentic;

import com.aliyun.sdk.service.oss2.agentic.models.*;
import org.junit.Assert;
import org.junit.Test;

public class ClientAgenticBucketSpaceTest extends TestBaseAgentic {

    @Test
    public void testListBucketSpaces() {
        OSSAgenticBucketClient client = agenticClient;
        String bucket = agenticBucketName;

        ListBucketSpacesResult listResult = client.listBucketSpaces(
                ListBucketSpacesRequest.newBuilder().bucket(bucket).build());
        Assert.assertNotNull(listResult);
        Assert.assertEquals(200, listResult.statusCode());
    }
}
