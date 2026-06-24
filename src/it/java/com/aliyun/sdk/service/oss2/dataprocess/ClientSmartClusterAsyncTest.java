package com.aliyun.sdk.service.oss2.dataprocess;

import com.aliyun.sdk.service.oss2.dataprocess.models.*;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for SmartCluster CRUD operations via OSSDataProcessAsyncClient.
 *
 * <p>Mirrors {@link ClientSmartClusterTest} for the asynchronous client.
 */
public class ClientSmartClusterAsyncTest extends TestBaseDataProcess {

    private static String scDatasetName;

    @BeforeClass
    public static void setUpDataset() throws Exception {
        scDatasetName = genDatasetName();
        CreateDatasetResult result = getDataProcessAsyncClient().createDatasetAsync(
                CreateDatasetRequest.newBuilder()
                        .bucket(testBucketName)
                        .datasetName(scDatasetName)
                        .build()).get();
        Assert.assertNotNull(result);
        Assert.assertEquals(200, result.statusCode());
    }

    @AfterClass
    public static void tearDownDataset() {
        OSSDataProcessAsyncClient client = getDataProcessAsyncClient();
        if (client == null || scDatasetName == null) {
            return;
        }

        // 1. List all SmartClusters under this dataset and trigger DeleteSmartCluster for each.
        //    DeleteSmartCluster is asynchronous on the server side, so it only schedules
        //    the deletion here.
        try {
            ListSmartClustersResult listResult = client.listSmartClustersAsync(
                    ListSmartClustersRequest.newBuilder()
                            .bucket(testBucketName)
                            .datasetName(scDatasetName)
                            .maxResults(100)
                            .build()).get();
            if (listResult != null && listResult.smartClusters() != null) {
                for (SmartClusterInfo sc : listResult.smartClusters()) {
                    try {
                        client.deleteSmartClusterAsync(
                                DeleteSmartClusterRequest.newBuilder()
                                        .bucket(testBucketName)
                                        .datasetName(scDatasetName)
                                        .objectId(sc.objectId())
                                        .build()).get();
                    } catch (Exception ignored) {
                    }
                }
            }
        } catch (Exception ignored) {
        }

        // 2. Wait for asynchronous backend deletion to complete, then delete the dataset.
        //    Retry on DatasetNotEmpty / StatusConflict.
        for (int i = 0; i < 6; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
                Thread.currentThread().interrupt();
            }
            try {
                client.deleteDatasetAsync(
                        DeleteDatasetRequest.newBuilder()
                                .bucket(testBucketName)
                                .datasetName(scDatasetName)
                                .build()).get();
                return;
            } catch (Exception ignored) {
                // dataset may still contain async-deleting SmartClusters; retry after a wait
            }
        }
    }

    /**
     * Figure cluster lifecycle: RuleType=face, BaseURIs (OSS URI list, max 3, ossFileURI format) is required.
     */
    @Test
    public void testFigureClusterWithFaceRuleLifecycle() throws Exception {
        OSSDataProcessAsyncClient client = getDataProcessAsyncClient();
        String scName = "test-sc-figure-async-" + System.currentTimeMillis();

        List<String> baseURIs = Arrays.asList(
                "oss://" + testBucketName + "/refs/face1.jpg",
                "oss://" + testBucketName + "/refs/face2.jpg",
                "oss://" + testBucketName + "/refs/face3.jpg"
        );
        SmartClusterRule rule = SmartClusterRule.newBuilder()
                .ruleType("face")
                .baseURIs(baseURIs)
                .sensitivity(0.7f)
                .build();

        CreateSmartClusterResult createResult = client.createSmartClusterAsync(
                CreateSmartClusterRequest.newBuilder()
                        .bucket(testBucketName)
                        .datasetName(scDatasetName)
                        .name(scName)
                        .clusterType("figure")
                        .rules(Arrays.asList(rule))
                        .description("integration test figure cluster")
                        .build()).get();

        Assert.assertNotNull(createResult);
        Assert.assertEquals(200, createResult.statusCode());
        Assert.assertNotNull("objectId should not be null", createResult.objectId());

        String objectId = createResult.objectId();

        try {
            // 2. Get SmartCluster
            GetSmartClusterResult getResult = client.getSmartClusterAsync(
                    GetSmartClusterRequest.newBuilder()
                            .bucket(testBucketName)
                            .datasetName(scDatasetName)
                            .objectId(objectId)
                            .build()).get();

            Assert.assertNotNull(getResult);
            Assert.assertEquals(200, getResult.statusCode());
            Assert.assertNotNull("smartCluster should not be null", getResult.smartCluster());
            assertThat(getResult.smartCluster().objectId()).isEqualTo(objectId);
            assertThat(getResult.smartCluster().name()).isEqualTo(scName);
            assertThat(getResult.smartCluster().clusterType()).isEqualTo("figure");
            assertThat(getResult.smartCluster().description()).isEqualTo("integration test figure cluster");
            Assert.assertNotNull("rules should not be null", getResult.smartCluster().rules());
            Assert.assertFalse("rules should not be empty", getResult.smartCluster().rules().isEmpty());
            SmartClusterRule echo = getResult.smartCluster().rules().get(0);
            assertThat(echo.ruleType()).isEqualTo("face");
            Assert.assertNotNull("baseURIs should be echoed", echo.baseURIs());
            Assert.assertFalse("baseURIs should not be empty", echo.baseURIs().isEmpty());
            Assert.assertTrue("baseURIs size <= 3", echo.baseURIs().size() <= 3);
            for (String uri : echo.baseURIs()) {
                Assert.assertTrue("baseURI must be ossFileURI: " + uri, uri.startsWith("oss://"));
            }

            // 3. Update SmartCluster description
            UpdateSmartClusterResult updateResult = client.updateSmartClusterAsync(
                    UpdateSmartClusterRequest.newBuilder()
                            .bucket(testBucketName)
                            .datasetName(scDatasetName)
                            .objectId(objectId)
                            .description("updated figure cluster description")
                            .build()).get();

            Assert.assertNotNull(updateResult);
            Assert.assertEquals(200, updateResult.statusCode());

            // 4. Verify update
            GetSmartClusterResult getAfterUpdate = client.getSmartClusterAsync(
                    GetSmartClusterRequest.newBuilder()
                            .bucket(testBucketName)
                            .datasetName(scDatasetName)
                            .objectId(objectId)
                            .build()).get();

            Assert.assertNotNull(getAfterUpdate);
            Assert.assertEquals(200, getAfterUpdate.statusCode());
            assertThat(getAfterUpdate.smartCluster().description()).isEqualTo("updated figure cluster description");

            Thread.sleep(10000);

            // 5. List SmartClusters and verify created one is included
            ListSmartClustersResult listResult = client.listSmartClustersAsync(
                    ListSmartClustersRequest.newBuilder()
                            .bucket(testBucketName)
                            .datasetName(scDatasetName)
                            .clusterType("figure")
                            .maxResults(100)
                            .build()).get();

            Assert.assertNotNull(listResult);
            Assert.assertEquals(200, listResult.statusCode());
            Assert.assertNotNull("smartClusters should not be null", listResult.smartClusters());
            Assert.assertFalse("smartClusters should not be empty", listResult.smartClusters().isEmpty());

            boolean found = false;
            for (SmartClusterInfo sc : listResult.smartClusters()) {
                if (objectId.equals(sc.objectId())) {
                    found = true;
                    break;
                }
            }
            Assert.assertTrue("Created SmartCluster should be found in list", found);

        } finally {
            // 6. Delete SmartCluster
            try {
                DeleteSmartClusterResult deleteResult = client.deleteSmartClusterAsync(
                        DeleteSmartClusterRequest.newBuilder()
                                .bucket(testBucketName)
                                .datasetName(scDatasetName)
                                .objectId(objectId)
                                .build()).get();

                Assert.assertNotNull(deleteResult);
                Assert.assertTrue("Expected 200 or 204 for delete",
                        deleteResult.statusCode() == 200 || deleteResult.statusCode() == 204);
            } catch (Exception ignored) {
            }
        }
    }

    /**
     * Knowledge cluster lifecycle: RuleType=keywords, Keywords is required.
     */
    @Test
    public void testKnowledgeClusterWithKeywordsRuleLifecycle() throws Exception {
        OSSDataProcessAsyncClient client = getDataProcessAsyncClient();
        String scName = "test-sc-knowledge-async-" + System.currentTimeMillis();

        SmartClusterRule rule = SmartClusterRule.newBuilder()
                .ruleType("keywords")
                .keywords(Arrays.asList("人物", "车辆"))
                .build();

        CreateSmartClusterResult createResult = client.createSmartClusterAsync(
                CreateSmartClusterRequest.newBuilder()
                        .bucket(testBucketName)
                        .datasetName(scDatasetName)
                        .name(scName)
                        .clusterType("knowledge")
                        .rules(Arrays.asList(rule))
                        .description("integration test knowledge cluster")
                        .build()).get();

        Assert.assertNotNull(createResult);
        Assert.assertEquals(200, createResult.statusCode());
        Assert.assertNotNull("objectId should not be null", createResult.objectId());

        String objectId = createResult.objectId();

        try {
            GetSmartClusterResult getResult = client.getSmartClusterAsync(
                    GetSmartClusterRequest.newBuilder()
                            .bucket(testBucketName)
                            .datasetName(scDatasetName)
                            .objectId(objectId)
                            .build()).get();

            Assert.assertNotNull(getResult);
            Assert.assertEquals(200, getResult.statusCode());
            Assert.assertNotNull(getResult.smartCluster());
            assertThat(getResult.smartCluster().clusterType()).isEqualTo("knowledge");
            Assert.assertNotNull("rules should not be null", getResult.smartCluster().rules());
            Assert.assertFalse("rules should not be empty", getResult.smartCluster().rules().isEmpty());
            SmartClusterRule echo = getResult.smartCluster().rules().get(0);
            assertThat(echo.ruleType()).isEqualTo("keywords");
            Assert.assertNotNull("keywords should be echoed", echo.keywords());
            Assert.assertFalse("keywords should not be empty", echo.keywords().isEmpty());
        } finally {
            try {
                client.deleteSmartClusterAsync(
                        DeleteSmartClusterRequest.newBuilder()
                                .bucket(testBucketName)
                                .datasetName(scDatasetName)
                                .objectId(objectId)
                                .build()).get();
            } catch (Exception ignored) {
            }
        }
    }

    /**
     * Verify face-type BaseURIs max limit is 3: server should reject when exceeding 3.
     */
    @Test
    public void testFaceRuleBaseURIsExceedMaxShouldFail() throws Exception {
        OSSDataProcessAsyncClient client = getDataProcessAsyncClient();
        List<String> tooManyURIs = Arrays.asList(
                "oss://" + testBucketName + "/refs/f1.jpg",
                "oss://" + testBucketName + "/refs/f2.jpg",
                "oss://" + testBucketName + "/refs/f3.jpg",
                "oss://" + testBucketName + "/refs/f4.jpg"
        );
        SmartClusterRule rule = SmartClusterRule.newBuilder()
                .ruleType("face")
                .baseURIs(tooManyURIs)
                .build();

        try {
            client.createSmartClusterAsync(
                    CreateSmartClusterRequest.newBuilder()
                            .bucket(testBucketName)
                            .datasetName(scDatasetName)
                            .name("test-sc-toomany-async-" + System.currentTimeMillis())
                            .clusterType("figure")
                            .rules(Arrays.asList(rule))
                            .build()).get();
            Assert.fail("Expected failure when BaseURIs exceeds 3 entries");
        } catch (Exception e) {
            // expected: server-side validation rejects > 3 BaseURIs
        }
    }

    @Test
    public void testListSmartClustersWithPagination() throws Exception {
        OSSDataProcessAsyncClient client = getDataProcessAsyncClient();

        // Page 1: list with small page size
        ListSmartClustersResult page1 = client.listSmartClustersAsync(
                ListSmartClustersRequest.newBuilder()
                        .bucket(testBucketName)
                        .datasetName(scDatasetName)
                        .clusterType("figure")
                        .maxResults(1)
                        .build()).get();

        Assert.assertNotNull(page1);
        Assert.assertEquals(200, page1.statusCode());
        Assert.assertNotNull("smartClusters should not be null", page1.smartClusters());
        Assert.assertTrue("should return at most 1 result", page1.smartClusters().size() <= 1);

        // Page 2
        if (page1.nextToken() != null && !page1.nextToken().isEmpty()) {
            ListSmartClustersResult page2 = client.listSmartClustersAsync(
                    ListSmartClustersRequest.newBuilder()
                            .bucket(testBucketName)
                            .datasetName(scDatasetName)
                            .clusterType("figure")
                            .maxResults(1)
                            .nextToken(page1.nextToken())
                            .build()).get();

            Assert.assertNotNull(page2);
            Assert.assertEquals(200, page2.statusCode());
            Assert.assertNotNull("page2 smartClusters should not be null", page2.smartClusters());
            Assert.assertTrue("page2 should return at most 1 result", page2.smartClusters().size() <= 1);

            if (!page1.smartClusters().isEmpty() && !page2.smartClusters().isEmpty()) {
                String idOnPage1 = page1.smartClusters().get(0).objectId();
                String idOnPage2 = page2.smartClusters().get(0).objectId();
                Assert.assertNotEquals("page1 and page2 should return different objects", idOnPage1, idOnPage2);
            }
        }
    }

    /**
     * ListSmartClusters supports filtering by clusterType / ruleTypes.
     */
    @Test
    public void testListSmartClustersWithFilter() throws Exception {
        OSSDataProcessAsyncClient client = getDataProcessAsyncClient();

        // Filter by clusterType
        ListSmartClustersResult byClusterType = client.listSmartClustersAsync(
                ListSmartClustersRequest.newBuilder()
                        .bucket(testBucketName)
                        .datasetName(scDatasetName)
                        .clusterType("figure")
                        .maxResults(50)
                        .build()).get();
        Assert.assertNotNull(byClusterType);
        Assert.assertEquals(200, byClusterType.statusCode());
        if (byClusterType.smartClusters() != null) {
            for (SmartClusterInfo sc : byClusterType.smartClusters()) {
                assertThat(sc.clusterType()).isEqualTo("figure");
            }
        }

        // Filter by ruleTypes, valid values: {face, keywords}
        ListSmartClustersResult byRuleTypes = client.listSmartClustersAsync(
                ListSmartClustersRequest.newBuilder()
                        .bucket(testBucketName)
                        .datasetName(scDatasetName)
                        .ruleTypes(java.util.Collections.singletonList("keywords"))
                        .maxResults(50)
                        .build()).get();
        Assert.assertNotNull(byRuleTypes);
        Assert.assertEquals(200, byRuleTypes.statusCode());
    }
}
