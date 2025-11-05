package com.aliyun.sdk.service.oss2;

import com.aliyun.sdk.service.oss2.models.*;
import org.junit.Assert;
import org.junit.Test;

public class ClientPublicAccessBlockTest extends TestBase {

    @Test
    public void testPublicAccessBlockOperations() throws Exception {
        try (OSSClient client = getOssClient()) {
            // putPublicAccessBlock
            PublicAccessBlockConfiguration config = PublicAccessBlockConfiguration.newBuilder()
                    .blockPublicAccess(true)
                    .build();

            PutPublicAccessBlockResult putResult = client.putPublicAccessBlock(PutPublicAccessBlockRequest.newBuilder()
                    .publicAccessBlockConfiguration(config)
                    .build());

            Assert.assertNotNull(putResult);
            Assert.assertEquals(200, putResult.statusCode());

            waitForCacheExpiration(5);

            // getPublicAccessBlock
            GetPublicAccessBlockResult getResult = client.getPublicAccessBlock(GetPublicAccessBlockRequest.newBuilder()
                    .build());

            Assert.assertNotNull(getResult);
            Assert.assertEquals(200, getResult.statusCode());
            Assert.assertNotNull(getResult.publicAccessBlockConfiguration());
            Assert.assertEquals(true, getResult.publicAccessBlockConfiguration().blockPublicAccess());

            // deletePublicAccessBlock
            DeletePublicAccessBlockResult deleteResult = client.deletePublicAccessBlock(DeletePublicAccessBlockRequest.newBuilder()
                    .build());

            Assert.assertNotNull(deleteResult);
            Assert.assertEquals(204, deleteResult.statusCode());

            waitForCacheExpiration(5);
        }
    }

    @Test
    public void testPublicAccessBlockDisable() throws Exception {
        try (OSSClient client = getOssClient()) {
            // putPublicAccessBlock with false value
            PublicAccessBlockConfiguration config = PublicAccessBlockConfiguration.newBuilder()
                    .blockPublicAccess(false)
                    .build();

            PutPublicAccessBlockResult putResult = client.putPublicAccessBlock(PutPublicAccessBlockRequest.newBuilder()
                    .publicAccessBlockConfiguration(config)
                    .build());

            Assert.assertNotNull(putResult);
            Assert.assertEquals(200, putResult.statusCode());

            waitForCacheExpiration(5);

            // getPublicAccessBlock
            GetPublicAccessBlockResult getResult = client.getPublicAccessBlock(GetPublicAccessBlockRequest.newBuilder()
                    .build());

            Assert.assertNotNull(getResult);
            Assert.assertEquals(200, getResult.statusCode());
            Assert.assertNotNull(getResult.publicAccessBlockConfiguration());
            Assert.assertEquals(false, getResult.publicAccessBlockConfiguration().blockPublicAccess());

            // deletePublicAccessBlock
            DeletePublicAccessBlockResult deleteResult = client.deletePublicAccessBlock(DeletePublicAccessBlockRequest.newBuilder()
                    .build());

            Assert.assertNotNull(deleteResult);
            Assert.assertEquals(204, deleteResult.statusCode());
        }
    }
}