package com.aliyun.sdk.service.oss2.dataprocess;

import com.aliyun.sdk.service.oss2.OSSClient;
import com.aliyun.sdk.service.oss2.dataprocess.models.*;
import com.aliyun.sdk.service.oss2.models.ListObjectsV2Request;
import com.aliyun.sdk.service.oss2.models.ListObjectsV2Result;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Integration tests for DeleteFileMeta operation via OSSDataProcessAsyncClient.
 *
 * <p>Mirrors {@link ClientDeleteFileMetaTest} for the asynchronous client.
 */
public class ClientDeleteFileMetaAsyncTest extends TestBaseDataProcess {

    private static String dfDatasetName;

    @BeforeClass
    public static void setUpDataset() throws Exception {
        dfDatasetName = genDatasetName();
        CreateDatasetResult result = getDataProcessAsyncClient().createDatasetAsync(
                CreateDatasetRequest.newBuilder()
                        .bucket(testBucketName)
                        .datasetName(dfDatasetName)
                        .build()).get();
        Assert.assertNotNull(result);
        Assert.assertEquals(200, result.statusCode());
    }

    @AfterClass
    public static void tearDownDataset() {
        if (getDataProcessAsyncClient() != null && dfDatasetName != null) {
            try {
                getDataProcessAsyncClient().deleteDatasetAsync(
                        DeleteDatasetRequest.newBuilder()
                                .bucket(testBucketName)
                                .datasetName(dfDatasetName)
                                .build()).get();
            } catch (Exception ignored) {
            }
        }
    }

    @Test
    public void testDeleteFileMeta() throws Exception {
        OSSDataProcessAsyncClient client = getDataProcessAsyncClient();
        OSSClient ossClient = getDefaultClient();

        // List the first object in the test bucket and use its key as the target URI
        ListObjectsV2Result listResult = ossClient.listObjectsV2(
                ListObjectsV2Request.newBuilder()
                        .bucket(testBucketName)
                        .maxKeys(1L)
                        .build());
        Assert.assertNotNull(listResult);
        Assert.assertEquals(200, listResult.statusCode());
        Assert.assertNotNull("bucket should contain at least one object", listResult.contents());
        Assert.assertFalse("bucket should contain at least one object", listResult.contents().isEmpty());

        String firstObjectKey = listResult.contents().get(0).key();
        Assert.assertNotNull("first object key should not be null", firstObjectKey);

        // Delete file meta for the first object
        DeleteFileMetaResult result = client.deleteFileMetaAsync(
                DeleteFileMetaRequest.newBuilder()
                        .bucket(testBucketName)
                        .datasetName(dfDatasetName)
                        .uri("oss://" + testBucketName + "/" + firstObjectKey)
                        .build()).get();

        Assert.assertNotNull(result);
        Assert.assertTrue("Expected 200 or 204 for deleteFileMeta",
                result.statusCode() == 200 || result.statusCode() == 204);
    }

    @Test
    public void testDeleteFileMetaWithInvalidUri() throws Exception {
        OSSDataProcessAsyncClient client = getDataProcessAsyncClient();

        try {
            client.deleteFileMetaAsync(
                    DeleteFileMetaRequest.newBuilder()
                            .bucket(testBucketName)
                            .datasetName(dfDatasetName)
                            .uri("invalid-uri")
                            .build()).get();
            // May succeed or throw depending on server validation
        } catch (Exception e) {
            // Expected - server may reject invalid URI format
            Assert.assertNotNull("Exception should have a message", e.getMessage());
        }
    }
}
