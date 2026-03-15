package com.aliyun.sdk.service.oss2.dataprocess;

import com.aliyun.sdk.service.oss2.dataprocess.models.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

/**
 * Integration tests for SimpleQuery and SemanticQuery operations via OSSDataProcessClient.
 */
public class ClientQueryTest extends TestBaseDataProcess {

    @Test
    public void testSimpleQueryBasic() {
        OSSDataProcessClient client = getDataProcessClient();
        String dsName = genDatasetName();

        // Create a dataset for querying
        CreateDatasetResult createResult = client.createDataset(
                CreateDatasetRequest.newBuilder()
                        .bucket(testBucketName)
                        .datasetName(dsName)
                        .build());

        Assert.assertNotNull(createResult);
        Assert.assertEquals(200, createResult.statusCode());

        try {
            // Execute a simple query on the dataset (empty dataset, should return empty results)
            SimpleQuery query = SimpleQuery.newBuilder()
                    .field("Filename")
                    .value("*")
                    .operation("match")
                    .build();

            SimpleQueryResult queryResult = client.simpleQuery(
                    SimpleQueryRequest.newBuilder()
                            .bucket(testBucketName)
                            .datasetName(dsName)
                            .query(query)
                            .maxResults(10)
                            .build());

            Assert.assertNotNull(queryResult);
            Assert.assertEquals(200, queryResult.statusCode());
            // Empty dataset should return empty or null file list
        } finally {
            try {
                client.deleteDataset(DeleteDatasetRequest.newBuilder()
                        .bucket(testBucketName)
                        .datasetName(dsName)
                        .build());
            } catch (Exception ignored) {
            }
        }
    }

    @Test
    public void testSimpleQueryWithAggregations() {
        OSSDataProcessClient client = getDataProcessClient();
        String dsName = genDatasetName();

        // Create a dataset for querying
        CreateDatasetResult createResult = client.createDataset(
                CreateDatasetRequest.newBuilder()
                        .bucket(testBucketName)
                        .datasetName(dsName)
                        .build());

        Assert.assertNotNull(createResult);
        Assert.assertEquals(200, createResult.statusCode());

        try {
            SimpleQuery query = SimpleQuery.newBuilder()
                    .field("Filename")
                    .value("*")
                    .operation("match")
                    .build();

            Aggregation aggregation = Aggregation.newBuilder()
                    .field("Size")
                    .operation("sum")
                    .build();

            SimpleQueryResult queryResult = client.simpleQuery(
                    SimpleQueryRequest.newBuilder()
                            .bucket(testBucketName)
                            .datasetName(dsName)
                            .query(query)
                            .aggregations(Collections.singletonList(aggregation))
                            .maxResults(10)
                            .build());

            Assert.assertNotNull(queryResult);
            Assert.assertEquals(200, queryResult.statusCode());
        } finally {
            try {
                client.deleteDataset(DeleteDatasetRequest.newBuilder()
                        .bucket(testBucketName)
                        .datasetName(dsName)
                        .build());
            } catch (Exception ignored) {
            }
        }
    }

    @Test
    public void testSimpleQueryWithSortAndOrder() {
        OSSDataProcessClient client = getDataProcessClient();
        String dsName = genDatasetName();

        CreateDatasetResult createResult = client.createDataset(
                CreateDatasetRequest.newBuilder()
                        .bucket(testBucketName)
                        .datasetName(dsName)
                        .build());

        Assert.assertNotNull(createResult);
        Assert.assertEquals(200, createResult.statusCode());

        try {
            SimpleQuery query = SimpleQuery.newBuilder()
                    .field("Filename")
                    .value("*")
                    .operation("match")
                    .build();

            SimpleQueryResult queryResult = client.simpleQuery(
                    SimpleQueryRequest.newBuilder()
                            .bucket(testBucketName)
                            .datasetName(dsName)
                            .query(query)
                            .sort("Filename")
                            .order("asc")
                            .maxResults(5)
                            .withoutTotalHits(false)
                            .build());

            Assert.assertNotNull(queryResult);
            Assert.assertEquals(200, queryResult.statusCode());
        } finally {
            try {
                client.deleteDataset(DeleteDatasetRequest.newBuilder()
                        .bucket(testBucketName)
                        .datasetName(dsName)
                        .build());
            } catch (Exception ignored) {
            }
        }
    }

    @Test
    public void testSimpleQueryWithFields() {
        OSSDataProcessClient client = getDataProcessClient();
        String dsName = genDatasetName();

        CreateDatasetResult createResult = client.createDataset(
                CreateDatasetRequest.newBuilder()
                        .bucket(testBucketName)
                        .datasetName(dsName)
                        .build());

        Assert.assertNotNull(createResult);
        Assert.assertEquals(200, createResult.statusCode());

        try {
            SimpleQuery query = SimpleQuery.newBuilder()
                    .field("Filename")
                    .value("*")
                    .operation("match")
                    .build();

            SimpleQueryResult queryResult = client.simpleQuery(
                    SimpleQueryRequest.newBuilder()
                            .bucket(testBucketName)
                            .datasetName(dsName)
                            .query(query)
                            .withFields(Arrays.asList("Filename", "Size", "ContentType"))
                            .maxResults(10)
                            .build());

            Assert.assertNotNull(queryResult);
            Assert.assertEquals(200, queryResult.statusCode());
        } finally {
            try {
                client.deleteDataset(DeleteDatasetRequest.newBuilder()
                        .bucket(testBucketName)
                        .datasetName(dsName)
                        .build());
            } catch (Exception ignored) {
            }
        }
    }

    @Test
    public void testSemanticQueryBasic() {
        OSSDataProcessClient client = getDataProcessClient();
        String dsName = genDatasetName();

        // Create a dataset for querying
        CreateDatasetResult createResult = client.createDataset(
                CreateDatasetRequest.newBuilder()
                        .bucket(testBucketName)
                        .datasetName(dsName)
                        .build());

        Assert.assertNotNull(createResult);
        Assert.assertEquals(200, createResult.statusCode());

        try {
            SemanticQueryResult queryResult = client.semanticQuery(
                    SemanticQueryRequest.newBuilder()
                            .bucket(testBucketName)
                            .datasetName(dsName)
                            .query("find all images")
                            .maxResults(10)
                            .build());

            Assert.assertNotNull(queryResult);
            Assert.assertEquals(200, queryResult.statusCode());
        } finally {
            try {
                client.deleteDataset(DeleteDatasetRequest.newBuilder()
                        .bucket(testBucketName)
                        .datasetName(dsName)
                        .build());
            } catch (Exception ignored) {
            }
        }
    }

    @Test
    public void testSemanticQueryWithMediaTypes() {
        OSSDataProcessClient client = getDataProcessClient();
        String dsName = genDatasetName();

        CreateDatasetResult createResult = client.createDataset(
                CreateDatasetRequest.newBuilder()
                        .bucket(testBucketName)
                        .datasetName(dsName)
                        .build());

        Assert.assertNotNull(createResult);
        Assert.assertEquals(200, createResult.statusCode());

        try {
            SemanticQueryResult queryResult = client.semanticQuery(
                    SemanticQueryRequest.newBuilder()
                            .bucket(testBucketName)
                            .datasetName(dsName)
                            .query("find all photos")
                            .mediaTypes(Arrays.asList("image"))
                            .withFields(Arrays.asList("Filename", "Size"))
                            .maxResults(10)
                            .build());

            Assert.assertNotNull(queryResult);
            Assert.assertEquals(200, queryResult.statusCode());
        } finally {
            try {
                client.deleteDataset(DeleteDatasetRequest.newBuilder()
                        .bucket(testBucketName)
                        .datasetName(dsName)
                        .build());
            } catch (Exception ignored) {
            }
        }
    }
}
