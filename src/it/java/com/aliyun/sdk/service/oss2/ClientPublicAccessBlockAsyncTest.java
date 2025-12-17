package com.aliyun.sdk.service.oss2;

import com.aliyun.sdk.service.oss2.models.*;
import org.junit.Assert;
import org.junit.Test;

public class ClientPublicAccessBlockAsyncTest extends TestBase {

    @Test
    public void testPublicAccessBlockOperations() throws Exception {
        OSSAsyncClient client = getDefaultAsyncClient();

        // putPublicAccessBlock
        PublicAccessBlockConfiguration config = PublicAccessBlockConfiguration.newBuilder()
                .blockPublicAccess(true)
                .build();

        PutPublicAccessBlockResult putResult = client.putPublicAccessBlockAsync(PutPublicAccessBlockRequest.newBuilder()
                .publicAccessBlockConfiguration(config)
                .build()).get();
        Assert.assertNotNull(putResult);
        Assert.assertEquals(200, putResult.statusCode());

        waitForCacheExpiration(5);

        // getPublicAccessBlock
        GetPublicAccessBlockResult getResult = client.getPublicAccessBlockAsync(GetPublicAccessBlockRequest.newBuilder()
                .build()).get();
        Assert.assertNotNull(getResult);
        Assert.assertEquals(200, getResult.statusCode());
        Assert.assertNotNull(getResult.publicAccessBlockConfiguration());
        Assert.assertEquals(true, getResult.publicAccessBlockConfiguration().blockPublicAccess());

        // deletePublicAccessBlock
        DeletePublicAccessBlockResult deleteResult = client.deletePublicAccessBlockAsync(DeletePublicAccessBlockRequest.newBuilder()
                .build()).get();
        Assert.assertNotNull(deleteResult);
        Assert.assertEquals(204, deleteResult.statusCode());

        waitForCacheExpiration(5);

    }

    @Test
    public void testPublicAccessBlockDisable() throws Exception {
        OSSAsyncClient client = getDefaultAsyncClient();
        // putPublicAccessBlock with false value
        PublicAccessBlockConfiguration config = PublicAccessBlockConfiguration.newBuilder()
                .blockPublicAccess(false)
                .build();

        PutPublicAccessBlockResult putResult = client.putPublicAccessBlockAsync(PutPublicAccessBlockRequest.newBuilder()
                .publicAccessBlockConfiguration(config)
                .build()).get();
        Assert.assertNotNull(putResult);
        Assert.assertEquals(200, putResult.statusCode());

        waitForCacheExpiration(5);

        // getPublicAccessBlock
        GetPublicAccessBlockResult getResult = client.getPublicAccessBlockAsync(GetPublicAccessBlockRequest.newBuilder()
                .build()).get();
        Assert.assertNotNull(getResult);
        Assert.assertEquals(200, getResult.statusCode());
        Assert.assertNotNull(getResult.publicAccessBlockConfiguration());
        Assert.assertEquals(false, getResult.publicAccessBlockConfiguration().blockPublicAccess());

        // deletePublicAccessBlock
        DeletePublicAccessBlockResult deleteResult = client.deletePublicAccessBlockAsync(DeletePublicAccessBlockRequest.newBuilder()
                .build()).get();
        Assert.assertNotNull(deleteResult);
        Assert.assertEquals(204, deleteResult.statusCode());

    }
}