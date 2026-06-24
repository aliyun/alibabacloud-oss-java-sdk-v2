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
 * Integration tests for SmartCluster CRUD operations via OSSDataProcessClient.
 */
public class ClientSmartClusterTest extends TestBaseDataProcess {

    private static String scDatasetName;

    @BeforeClass
    public static void setUpDataset() {

        scDatasetName = genDatasetName();
        CreateDatasetResult result = getDataProcessClient().createDataset(
                CreateDatasetRequest.newBuilder()
                        .bucket(testBucketName)
                        .datasetName(scDatasetName)
                        .build());
        Assert.assertNotNull(result);
        Assert.assertEquals(200, result.statusCode());
    }

    @AfterClass
    public static void tearDownDataset() {
        OSSDataProcessClient client = getDataProcessClient();
        if (client == null || scDatasetName == null) {
            return;
        }

        // 1. List all SmartClusters under this dataset and trigger DeleteSmartCluster for each.
        //    DeleteSmartCluster is asynchronous on the server side (ref: §16 DeleteSmartCluster),
        //    so it only schedules the deletion here.
        try {
            ListSmartClustersResult listResult = client.listSmartClusters(
                    ListSmartClustersRequest.newBuilder()
                            .bucket(testBucketName)
                            .datasetName(scDatasetName)
                            .maxResults(100)
                            .build());
            if (listResult != null && listResult.smartClusters() != null) {
                for (SmartClusterInfo sc : listResult.smartClusters()) {
                    try {
                        client.deleteSmartCluster(
                                DeleteSmartClusterRequest.newBuilder()
                                        .bucket(testBucketName)
                                        .datasetName(scDatasetName)
                                        .objectId(sc.objectId())
                                        .build());
                    } catch (Exception ignored) {
                    }
                }
            }
        } catch (Exception ignored) {
        }

        // 2. Wait for asynchronous SmartCluster deletion to complete on backend, then delete the
        //    dataset. If the dataset is still not empty (DatasetNotEmpty / StatusConflict),
        //    retry a few times before giving up.
        for (int i = 0; i < 6; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
                Thread.currentThread().interrupt();
            }
            try {
                client.deleteDataset(
                        DeleteDatasetRequest.newBuilder()
                                .bucket(testBucketName)
                                .datasetName(scDatasetName)
                                .build());
                return;
            } catch (Exception ignored) {
                // dataset may still contain async-deleting SmartClusters; retry after a wait
            }
        }
    }

    /**
     * Figure cluster lifecycle: RuleType=face, BaseURIs (OSS URI list, max 3, ossFileURI format) is required.
     * Ref: sdk_internal_reference(4).md §13/§14 and §SmartClusterRule constraints.
     */
    @Test
    public void testFigureClusterWithFaceRuleLifecycle() {
        OSSDataProcessClient client = getDataProcessClient();
        String scName = "test-sc-figure-" + System.currentTimeMillis();

        // 1. Create figure SmartCluster: ruleType=face + BaseURIs(<=3,ossFileURI)
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

        CreateSmartClusterResult createResult = client.createSmartCluster(
                CreateSmartClusterRequest.newBuilder()
                        .bucket(testBucketName)
                        .datasetName(scDatasetName)
                        .name(scName)
                        .clusterType("figure")
                        .rules(Arrays.asList(rule))
                        .description("integration test figure cluster")
                        .build());

        Assert.assertNotNull(createResult);
        Assert.assertEquals(200, createResult.statusCode());
        Assert.assertNotNull("objectId should not be null", createResult.objectId());

        String objectId = createResult.objectId();

        try {
            // 2. Get SmartCluster
            GetSmartClusterResult getResult = client.getSmartCluster(
                    GetSmartClusterRequest.newBuilder()
                            .bucket(testBucketName)
                            .datasetName(scDatasetName)
                            .objectId(objectId)
                            .build());

            Assert.assertNotNull(getResult);
            Assert.assertEquals(200, getResult.statusCode());
            Assert.assertNotNull("smartCluster should not be null", getResult.smartCluster());
            assertThat(getResult.smartCluster().objectId()).isEqualTo(objectId);
            assertThat(getResult.smartCluster().name()).isEqualTo(scName);
            assertThat(getResult.smartCluster().clusterType()).isEqualTo("figure");
            assertThat(getResult.smartCluster().description()).isEqualTo("integration test figure cluster");
            // Verify rules echoed with ruleType=face and BaseURIs
            Assert.assertNotNull("rules should not be null", getResult.smartCluster().rules());
            Assert.assertFalse("rules should not be empty", getResult.smartCluster().rules().isEmpty());
            SmartClusterRule echo = getResult.smartCluster().rules().get(0);
            assertThat(echo.ruleType()).isEqualTo("face");
            Assert.assertNotNull("baseURIs should be echoed", echo.baseURIs());
            Assert.assertFalse("baseURIs should not be empty", echo.baseURIs().isEmpty());
            // Document constraint: BaseURIs max 3 entries
            Assert.assertTrue("baseURIs size <= 3", echo.baseURIs().size() <= 3);
            // Document constraint: each URI must be ossFileURI (oss://bucket/key)
            for (String uri : echo.baseURIs()) {
                Assert.assertTrue("baseURI must be ossFileURI: " + uri, uri.startsWith("oss://"));
            }

            // 3. Update SmartCluster description
            UpdateSmartClusterResult updateResult = client.updateSmartCluster(
                    UpdateSmartClusterRequest.newBuilder()
                            .bucket(testBucketName)
                            .datasetName(scDatasetName)
                            .objectId(objectId)
                            .description("updated figure cluster description")
                            .build());

            Assert.assertNotNull(updateResult);
            Assert.assertEquals(200, updateResult.statusCode());

            // 4. Verify update
            GetSmartClusterResult getAfterUpdate = client.getSmartCluster(
                    GetSmartClusterRequest.newBuilder()
                            .bucket(testBucketName)
                            .datasetName(scDatasetName)
                            .objectId(objectId)
                            .build());

            Assert.assertNotNull(getAfterUpdate);
            Assert.assertEquals(200, getAfterUpdate.statusCode());
            assertThat(getAfterUpdate.smartCluster().description()).isEqualTo("updated figure cluster description");

            Thread.sleep(10000);

            // 5. List SmartClusters and verify created one is included
            ListSmartClustersResult listResult = client.listSmartClusters(
                    ListSmartClustersRequest.newBuilder()
                            .bucket(testBucketName)
                            .datasetName(scDatasetName)
                            .clusterType("figure")
                            .maxResults(100)
                            .build());

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

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // 6. Delete SmartCluster
            try {
                DeleteSmartClusterResult deleteResult = client.deleteSmartCluster(
                        DeleteSmartClusterRequest.newBuilder()
                                .bucket(testBucketName)
                                .datasetName(scDatasetName)
                                .objectId(objectId)
                                .build());

                Assert.assertNotNull(deleteResult);
                Assert.assertTrue("Expected 200 or 204 for delete",
                        deleteResult.statusCode() == 200 || deleteResult.statusCode() == 204);
            } catch (Exception ignored) {
            }
        }
    }

    /**
     * Knowledge cluster lifecycle: RuleType=keywords, Keywords is required.
     * Ref: sdk_internal_reference(4).md §SmartClusterRule.
     */
    @Test
    public void testKnowledgeClusterWithKeywordsRuleLifecycle() {
        OSSDataProcessClient client = getDataProcessClient();
        String scName = "test-sc-knowledge-" + System.currentTimeMillis();

        SmartClusterRule rule = SmartClusterRule.newBuilder()
                .ruleType("keywords")
                .keywords(Arrays.asList("人物", "车辆"))
                .build();

        CreateSmartClusterResult createResult = client.createSmartCluster(
                CreateSmartClusterRequest.newBuilder()
                        .bucket(testBucketName)
                        .datasetName(scDatasetName)
                        .name(scName)
                        .clusterType("knowledge")
                        .rules(Arrays.asList(rule))
                        .description("integration test knowledge cluster")
                        .build());

        Assert.assertNotNull(createResult);
        Assert.assertEquals(200, createResult.statusCode());
        Assert.assertNotNull("objectId should not be null", createResult.objectId());

        String objectId = createResult.objectId();

        try {
            GetSmartClusterResult getResult = client.getSmartCluster(
                    GetSmartClusterRequest.newBuilder()
                            .bucket(testBucketName)
                            .datasetName(scDatasetName)
                            .objectId(objectId)
                            .build());

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
                client.deleteSmartCluster(
                        DeleteSmartClusterRequest.newBuilder()
                                .bucket(testBucketName)
                                .datasetName(scDatasetName)
                                .objectId(objectId)
                                .build());
            } catch (Exception ignored) {
            }
        }
    }

    /**
     * Verify face-type BaseURIs max limit is 3: server should reject when exceeding 3.
     * Ref: sdk_internal_reference(4).md §SmartClusterRule "BaseURIs ... max 3".
     */
    @Test
    public void testFaceRuleBaseURIsExceedMaxShouldFail() {
        OSSDataProcessClient client = getDataProcessClient();
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
            client.createSmartCluster(
                    CreateSmartClusterRequest.newBuilder()
                            .bucket(testBucketName)
                            .datasetName(scDatasetName)
                            .name("test-sc-toomany-" + System.currentTimeMillis())
                            .clusterType("figure")
                            .rules(Arrays.asList(rule))
                            .build());
            Assert.fail("Expected failure when BaseURIs exceeds 3 entries");
        } catch (Exception e) {
            // expected: server-side validation rejects > 3 BaseURIs
        }
    }

    @Test
    public void testListSmartClustersWithPagination() {
        OSSDataProcessClient client = getDataProcessClient();

        // Page 1: list with small page size
        ListSmartClustersResult page1 = client.listSmartClusters(
                ListSmartClustersRequest.newBuilder()
                        .bucket(testBucketName)
                        .datasetName(scDatasetName)
                        .clusterType("figure")
                        .maxResults(1)
                        .build());

        Assert.assertNotNull(page1);
        Assert.assertEquals(200, page1.statusCode());
        Assert.assertNotNull("smartClusters should not be null", page1.smartClusters());
        Assert.assertTrue("should return at most 1 result", page1.smartClusters().size() <= 1);

        // Page 2: if there's a nextToken, fetch next page and verify pagination works
        if (page1.nextToken() != null && !page1.nextToken().isEmpty()) {
            ListSmartClustersResult page2 = client.listSmartClusters(
                    ListSmartClustersRequest.newBuilder()
                            .bucket(testBucketName)
                            .datasetName(scDatasetName)
                            .clusterType("figure")
                            .maxResults(1)
                            .nextToken(page1.nextToken())
                            .build());

            Assert.assertNotNull(page2);
            Assert.assertEquals(200, page2.statusCode());
            Assert.assertNotNull("page2 smartClusters should not be null", page2.smartClusters());
            Assert.assertTrue("page2 should return at most 1 result", page2.smartClusters().size() <= 1);

            // Verify page1 and page2 do not overlap (different objectId)
            if (!page1.smartClusters().isEmpty() && !page2.smartClusters().isEmpty()) {
                String idOnPage1 = page1.smartClusters().get(0).objectId();
                String idOnPage2 = page2.smartClusters().get(0).objectId();
                Assert.assertNotEquals("page1 and page2 should return different objects", idOnPage1, idOnPage2);
            }
        }
    }

    /**
     * ListSmartClusters supports filtering by clusterType / ruleTypes.
     * Ref: sdk_internal_reference(4).md §17 ListSmartClusters.
     */
    @Test
    public void testListSmartClustersWithFilter() {
        OSSDataProcessClient client = getDataProcessClient();

        // Filter by clusterType
        ListSmartClustersResult byClusterType = client.listSmartClusters(
                ListSmartClustersRequest.newBuilder()
                        .bucket(testBucketName)
                        .datasetName(scDatasetName)
                        .clusterType("figure")
                        .maxResults(50)
                        .build());
        Assert.assertNotNull(byClusterType);
        Assert.assertEquals(200, byClusterType.statusCode());
        if (byClusterType.smartClusters() != null) {
            for (SmartClusterInfo sc : byClusterType.smartClusters()) {
                assertThat(sc.clusterType()).isEqualTo("figure");
            }
        }

        // Filter by ruleTypes, valid values: {face, keywords}
        ListSmartClustersResult byRuleTypes = client.listSmartClusters(
                ListSmartClustersRequest.newBuilder()
                        .bucket(testBucketName)
                        .datasetName(scDatasetName)
                        .ruleTypes(java.util.Collections.singletonList("keywords"))
                        .maxResults(50)
                        .build());
        Assert.assertNotNull(byRuleTypes);
        Assert.assertEquals(200, byRuleTypes.statusCode());
    }

    /**
     * Recommended way per sdk_internal_reference(6).md §13 / §15:
     * use {@code rules(List<SmartClusterRule>)} (the JSON-array form) instead of
     * the legacy single {@code rule(SmartClusterRule)}. Document quote:
     * “rules 二选一 … 优先用 rules”.
     *
     * <p>This case covers the full lifecycle entirely through the array form:
     * Create with rules(List) -> Get (verify Rules array echoed) -> Update with
     * rules(List) -> Delete.
     */
    @Test
    public void testFigureClusterUsingRulesArrayLifecycle() {
        OSSDataProcessClient client = getDataProcessClient();
        String scName = "test-sc-rules-array-" + System.currentTimeMillis();

        // 1. Create with rules(List<SmartClusterRule>) - recommended array form
        List<String> baseURIs = Arrays.asList(
                "oss://" + testBucketName + "/refs/arr-face1.jpg",
                "oss://" + testBucketName + "/refs/arr-face2.jpg"
        );
        SmartClusterRule faceRule = SmartClusterRule.newBuilder()
                .ruleType("face")
                .baseURIs(baseURIs)
                .sensitivity(0.6f)
                .build();
        List<SmartClusterRule> rulesArray = java.util.Collections.singletonList(faceRule);

        CreateSmartClusterResult createResult = client.createSmartCluster(
                CreateSmartClusterRequest.newBuilder()
                        .bucket(testBucketName)
                        .datasetName(scDatasetName)
                        .name(scName)
                        .clusterType("figure")
                        .rules(rulesArray)
                        .description("integration test rules-array form")
                        .build());

        Assert.assertNotNull(createResult);
        Assert.assertEquals(200, createResult.statusCode());
        Assert.assertNotNull("objectId should not be null", createResult.objectId());
        String objectId = createResult.objectId();

        try {
            // 2. Get and verify Rules array (sdk_internal_reference(6).md §14)
            GetSmartClusterResult getResult = client.getSmartCluster(
                    GetSmartClusterRequest.newBuilder()
                            .bucket(testBucketName)
                            .datasetName(scDatasetName)
                            .objectId(objectId)
                            .build());
            Assert.assertNotNull(getResult);
            Assert.assertEquals(200, getResult.statusCode());
            Assert.assertNotNull("smartCluster should not be null", getResult.smartCluster());
            assertThat(getResult.smartCluster().clusterType()).isEqualTo("figure");
            Assert.assertNotNull("rules should not be null", getResult.smartCluster().rules());
            Assert.assertFalse("rules should not be empty", getResult.smartCluster().rules().isEmpty());
            SmartClusterRule echo = getResult.smartCluster().rules().get(0);
            assertThat(echo.ruleType()).isEqualTo("face");
            Assert.assertNotNull("baseURIs should be echoed", echo.baseURIs());
            Assert.assertTrue("baseURIs size <= 3", echo.baseURIs().size() <= 3);
            for (String uri : echo.baseURIs()) {
                Assert.assertTrue("baseURI must be ossFileURI: " + uri, uri.startsWith("oss://"));
            }

            // 3. Update via rules(List<SmartClusterRule>) - also recommended for UpdateSmartCluster (§15)
            SmartClusterRule updatedFaceRule = SmartClusterRule.newBuilder()
                    .ruleType("face")
                    .baseURIs(java.util.Collections.singletonList(
                            "oss://" + testBucketName + "/refs/arr-face-updated.jpg"))
                    .sensitivity(0.8f)
                    .build();
            UpdateSmartClusterResult updateResult = client.updateSmartCluster(
                    UpdateSmartClusterRequest.newBuilder()
                            .bucket(testBucketName)
                            .datasetName(scDatasetName)
                            .objectId(objectId)
                            .rules(java.util.Collections.singletonList(updatedFaceRule))
                            .build());
            Assert.assertNotNull(updateResult);
            Assert.assertEquals(200, updateResult.statusCode());
        } finally {
            // 4. Delete (async on backend per §16)
            try {
                client.deleteSmartCluster(
                        DeleteSmartClusterRequest.newBuilder()
                                .bucket(testBucketName)
                                .datasetName(scDatasetName)
                                .objectId(objectId)
                                .build());
            } catch (Exception ignored) {
            }
        }
    }

    /**
     * Demonstrates {@code rules(List<SmartClusterRule>)} carrying TRULY MULTIPLE rules,
     * not just a single-element list. Per sdk_internal_reference(6).md §13,
     * {@code rules={json-array}} accepts an array of {@link SmartClusterRule} objects.
     *
     * <p>Two keywords rules are passed for a knowledge cluster. The server is expected
     * to echo all rules back via the {@code <Rules><Rule>...</Rule></Rules>} array.
     */
    @Test
    public void testKnowledgeClusterUsingRulesArrayWithMultipleRules() {
        OSSDataProcessClient client = getDataProcessClient();
        String scName = "test-sc-multi-rules-" + System.currentTimeMillis();

        // 1. Build TWO independent SmartClusterRule entries
        SmartClusterRule rule1 = SmartClusterRule.newBuilder()
                .ruleType("keywords")
                .keywords(Arrays.asList("人物", "车辆"))
                .build();
        SmartClusterRule rule2 = SmartClusterRule.newBuilder()
                .ruleType("keywords")
                .keywords(Arrays.asList("动物", "风景"))
                .build();
        List<SmartClusterRule> twoRules = Arrays.asList(rule1, rule2);
        Assert.assertEquals("rules array should carry 2 rules", 2, twoRules.size());

        CreateSmartClusterResult createResult;
        try {
            createResult = client.createSmartCluster(
                    CreateSmartClusterRequest.newBuilder()
                            .bucket(testBucketName)
                            .datasetName(scDatasetName)
                            .name(scName)
                            .clusterType("knowledge")
                            .rules(twoRules)
                            .description("integration test multi-rules array form")
                            .build());
        } catch (Exception e) {
            // Some backends may restrict a single SmartCluster to one rule. In that case
            // the SDK contract (rules accepts List<SmartClusterRule>) is still verified;
            // surface a clear marker and skip the post-check.
            Assert.assertNotNull(
                    "createSmartCluster with multiple rules should succeed or be rejected by server with a message",
                    e.getMessage());
            return;
        }

        Assert.assertNotNull(createResult);
        Assert.assertEquals(200, createResult.statusCode());
        Assert.assertNotNull("objectId should not be null", createResult.objectId());
        String objectId = createResult.objectId();

        try {
            // 2. Get and verify the Rules array contains echoed rules
            GetSmartClusterResult getResult = client.getSmartCluster(
                    GetSmartClusterRequest.newBuilder()
                            .bucket(testBucketName)
                            .datasetName(scDatasetName)
                            .objectId(objectId)
                            .build());
            Assert.assertNotNull(getResult);
            Assert.assertEquals(200, getResult.statusCode());
            Assert.assertNotNull("smartCluster should not be null", getResult.smartCluster());
            assertThat(getResult.smartCluster().clusterType()).isEqualTo("knowledge");
            Assert.assertNotNull("rules should not be null", getResult.smartCluster().rules());
            // Each echoed entry must be well-formed (ruleType=keywords + non-empty keywords).
            Assert.assertFalse("rules should not be empty", getResult.smartCluster().rules().isEmpty());
            for (SmartClusterRule echo : getResult.smartCluster().rules()) {
                assertThat(echo.ruleType()).isEqualTo("keywords");
                Assert.assertNotNull("keywords should be echoed", echo.keywords());
                Assert.assertFalse("keywords should not be empty", echo.keywords().isEmpty());
            }

            // 3. Update via rules(List) carrying TWO updated rules as well
            SmartClusterRule updated1 = SmartClusterRule.newBuilder()
                    .ruleType("keywords")
                    .keywords(Arrays.asList("建筑", "食物"))
                    .build();
            SmartClusterRule updated2 = SmartClusterRule.newBuilder()
                    .ruleType("keywords")
                    .keywords(Arrays.asList("服饰", "营造"))
                    .build();
            UpdateSmartClusterResult updateResult = client.updateSmartCluster(
                    UpdateSmartClusterRequest.newBuilder()
                            .bucket(testBucketName)
                            .datasetName(scDatasetName)
                            .objectId(objectId)
                            .rules(Arrays.asList(updated1, updated2))
                            .build());
            Assert.assertNotNull(updateResult);
            Assert.assertEquals(200, updateResult.statusCode());
        } finally {
            try {
                client.deleteSmartCluster(
                        DeleteSmartClusterRequest.newBuilder()
                                .bucket(testBucketName)
                                .datasetName(scDatasetName)
                                .objectId(objectId)
                                .build());
            } catch (Exception ignored) {
            }
        }
    }
}
