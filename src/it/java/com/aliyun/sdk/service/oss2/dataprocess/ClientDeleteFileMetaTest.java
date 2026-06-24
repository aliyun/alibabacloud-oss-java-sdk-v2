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
 * Integration tests for DeleteFileMeta operation via OSSDataProcessClient.
 */
public class ClientDeleteFileMetaTest extends TestBaseDataProcess {

    private static String dfDatasetName;

    @BeforeClass
    public static void setUpDataset() {
        dfDatasetName = genDatasetName();
        CreateDatasetResult result = getDataProcessClient().createDataset(
                CreateDatasetRequest.newBuilder()
                        .bucket(testBucketName)
                        .datasetName(dfDatasetName)
                        .build());
        Assert.assertNotNull(result);
        Assert.assertEquals(200, result.statusCode());
    }

    @AfterClass
    public static void tearDownDataset() {
        if (getDataProcessClient() != null && dfDatasetName != null) {
            try {
                getDataProcessClient().deleteDataset(
                        DeleteDatasetRequest.newBuilder()
                                .bucket(testBucketName)
                                .datasetName(dfDatasetName)
                                .build());
            } catch (Exception ignored) {
            }
        }
    }

    @Test
    public void testDeleteFileMeta() {
        OSSDataProcessClient client = getDataProcessClient();
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
        DeleteFileMetaResult result = client.deleteFileMeta(
                DeleteFileMetaRequest.newBuilder()
                        .bucket(testBucketName)
                        .datasetName(dfDatasetName)
                        .uri("oss://" + testBucketName + "/" + firstObjectKey)
                        .build());

        Assert.assertNotNull(result);
        // Either 200 or 204 is acceptable
        Assert.assertTrue("Expected 200 or 204 for deleteFileMeta",
                result.statusCode() == 200 || result.statusCode() == 204);
    }

    @Test
    public void testDeleteFileMetaWithInvalidUri() {
        OSSDataProcessClient client = getDataProcessClient();

        try {
            client.deleteFileMeta(
                    DeleteFileMetaRequest.newBuilder()
                            .bucket(testBucketName)
                            .datasetName(dfDatasetName)
                            .uri("invalid-uri")
                            .build());
            // May succeed or throw depending on server validation
        } catch (Exception e) {
            // Expected - server may reject invalid URI format
            Assert.assertNotNull("Exception should have a message", e.getMessage());
        }
    }
}
