package com.aliyun.sdk.service.oss2.dataprocess;

import com.aliyun.sdk.service.oss2.dataprocess.models.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Integration tests for SimpleQuery and SemanticQuery operations via OSSDataProcessClient.
 */
public class ClientQueryTest extends TestBaseDataProcess {

    private static final String QUERY_DATASET = "test-dataset";

    @Test
    public void testSimpleQueryBasic() {
        OSSDataProcessClient client = getDataProcessClient();

        SimpleQuery query = SimpleQuery.newBuilder()
                .field("Filename")
                .value("test-media")
                .operation("prefix")
                .build();

        SimpleQueryResult result = client.simpleQuery(
                SimpleQueryRequest.newBuilder()
                        .bucket(testBucketName)
                        .datasetName(QUERY_DATASET)
                        .query(query)
                        .maxResults(10)
                        .build());

        Assert.assertNotNull(result);
        Assert.assertEquals(200, result.statusCode());

        // Verify files are returned
        Assert.assertNotNull("files should not be null", result.files());
        Assert.assertFalse("files should not be empty", result.files().isEmpty());

        File f = result.files().get(0);
        Assert.assertNotNull("URI should not be null", f.uri());
        Assert.assertNotNull("Filename should not be null", f.filename());
        Assert.assertNotNull("MediaType should not be null", f.mediaType());
        Assert.assertNotNull("ContentType should not be null", f.contentType());
        Assert.assertNotNull("Size should not be null", f.size());
        Assert.assertTrue("Size should be > 0", f.size() > 0);
    }

    @Test
    public void testSimpleQueryWithAggregations() {
        OSSDataProcessClient client = getDataProcessClient();

        SimpleQuery query = SimpleQuery.newBuilder()
                .field("Filename")
                .value("test-media")
                .operation("prefix")
                .build();

        Aggregation aggregation = Aggregation.newBuilder()
                .field("Size")
                .operation("sum")
                .build();

        SimpleQueryResult result = client.simpleQuery(
                SimpleQueryRequest.newBuilder()
                        .bucket(testBucketName)
                        .datasetName(QUERY_DATASET)
                        .query(query)
                        .aggregations(Collections.singletonList(aggregation))
                        .maxResults(10)
                        .build());

        Assert.assertNotNull(result);
        Assert.assertEquals(200, result.statusCode());

        // Verify aggregations
        Assert.assertNotNull("aggregations should not be null", result.aggregations());
        Assert.assertFalse("aggregations should not be empty", result.aggregations().isEmpty());

        AggregationInfo agg = result.aggregations().get(0);
        Assert.assertEquals("Size", agg.field());
        Assert.assertEquals("sum", agg.operation());
        Assert.assertNotNull("aggregation value should not be null", agg.value());
    }

    @Test
    public void testSimpleQueryWithSortAndOrder() {
        OSSDataProcessClient client = getDataProcessClient();

        SimpleQuery query = SimpleQuery.newBuilder()
                .field("Filename")
                .value("test-media")
                .operation("prefix")
                .build();

        SimpleQueryResult result = client.simpleQuery(
                SimpleQueryRequest.newBuilder()
                        .bucket(testBucketName)
                        .datasetName(QUERY_DATASET)
                        .query(query)
                        .sort("Filename")
                        .order("asc")
                        .maxResults(10)
                        .withoutTotalHits(false)
                        .build());

        Assert.assertNotNull(result);
        Assert.assertEquals(200, result.statusCode());

        Assert.assertNotNull("files should not be null", result.files());
        Assert.assertFalse("files should not be empty", result.files().isEmpty());

        // Verify sorted by Filename ascending
        java.util.List<File> files = result.files();
        for (int i = 1; i < files.size(); i++) {
            String prev = files.get(i - 1).filename();
            String curr = files.get(i).filename();
            Assert.assertTrue("files should be sorted by Filename asc: " + prev + " <= " + curr,
                    prev.compareTo(curr) <= 0);
        }
    }

    @Test
    public void testSimpleQueryWithFields() {
        OSSDataProcessClient client = getDataProcessClient();

        SimpleQuery query = SimpleQuery.newBuilder()
                .field("Filename")
                .value("test-media")
                .operation("prefix")
                .build();

        SimpleQueryResult result = client.simpleQuery(
                SimpleQueryRequest.newBuilder()
                        .bucket(testBucketName)
                        .datasetName(QUERY_DATASET)
                        .query(query)
                        .withFields(Arrays.asList("Filename", "Size", "ContentType"))
                        .maxResults(10)
                        .build());

        Assert.assertNotNull(result);
        Assert.assertEquals(200, result.statusCode());

        Assert.assertNotNull("files should not be null", result.files());
        Assert.assertFalse("files should not be empty", result.files().isEmpty());

        // Verify requested fields are populated
        File f = result.files().get(0);
        Assert.assertNotNull("Filename should not be null", f.filename());
        Assert.assertNotNull("Size should not be null", f.size());
        Assert.assertNotNull("ContentType should not be null", f.contentType());
    }

    @Test
    public void testSemanticQueryBasic() {
        OSSDataProcessClient client = getDataProcessClient();

        SemanticQueryResult result = client.semanticQuery(
                SemanticQueryRequest.newBuilder()
                        .bucket(testBucketName)
                        .datasetName(QUERY_DATASET)
                        .query("雪景")
                        .maxResults(10)
                        .build());

        Assert.assertNotNull(result);
        Assert.assertEquals(200, result.statusCode());

        Assert.assertNotNull("files should not be null", result.files());
        Assert.assertFalse("files should not be empty", result.files().isEmpty());

        File f = result.files().get(0);
        Assert.assertNotNull("URI should not be null", f.uri());
        Assert.assertNotNull("Filename should not be null", f.filename());
        Assert.assertNotNull("MediaType should not be null", f.mediaType());
        Assert.assertNotNull("Size should not be null", f.size());
    }

    @Test
    public void testSemanticQueryWithMediaTypes() {
        OSSDataProcessClient client = getDataProcessClient();

        SemanticQueryResult result = client.semanticQuery(
                SemanticQueryRequest.newBuilder()
                        .bucket(testBucketName)
                        .datasetName(QUERY_DATASET)
                        .query("雪景")
                        .mediaTypes(Arrays.asList("image"))
                        .withFields(Arrays.asList("Filename", "Size", "MediaType"))
                        .maxResults(10)
                        .build());

        Assert.assertNotNull(result);
        Assert.assertEquals(200, result.statusCode());

        Assert.assertNotNull("files should not be null", result.files());
        Assert.assertFalse("files should not be empty", result.files().isEmpty());

        // Verify all returned files are images
        for (File f : result.files()) {
            Assert.assertEquals("MediaType should be image", "image", f.mediaType());
        }
    }

}
