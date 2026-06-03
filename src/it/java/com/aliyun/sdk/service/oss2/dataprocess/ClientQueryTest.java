package com.aliyun.sdk.service.oss2.dataprocess;

import com.aliyun.sdk.service.oss2.OSSClient;
import com.aliyun.sdk.service.oss2.dataprocess.models.*;
import com.aliyun.sdk.service.oss2.dataprocess.models.File;
import com.aliyun.sdk.service.oss2.models.DeleteObjectRequest;
import com.aliyun.sdk.service.oss2.models.PutObjectRequest;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
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

        // Verify labels parsing (wrapper: Labels -> Label)
        boolean anyFileHasLabels = false;
        for (File file : result.files()) {
            if (file.labels() != null && !file.labels().isEmpty()) {
                anyFileHasLabels = true;
                Label label = file.labels().get(0);
                Assert.assertNotNull("Label.labelName should not be null", label.labelName());
                break;
            }
        }
        Assert.assertTrue("At least one file should have labels", anyFileHasLabels);
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

    // ====================================================================================

    private static final String ROUTING_TAG_KEY = "routing-dataset";
    private static final String OBJECT_KEY_PREFIX = "test-query-obj-";
    private static final String[] SAMPLE_KEYS = {
            OBJECT_KEY_PREFIX + "1.jpg",
            OBJECT_KEY_PREFIX + "2.jpg",
            OBJECT_KEY_PREFIX + "3.jpg"
    };
    private static final long INDEX_WAIT_MS = 20000L;

    /** Default dataset name maintained by OSS: oss_<uid>_<bucket>. */
    private static String queryDatasetName;
    private static OSSClient ossClient;

    /**
     * Prepares a small JPEG dataset for the additional tests below.
     * <p>MetaQuery is already opened in {@code semantic} mode by
     * {@link TestBaseDataProcess#oneTimeSetUp()} so we do NOT re-open it here.
     * No {@code createDataset} is called either: the default dataset
     * {@code oss_<uid>_<bucket>} is implicitly maintained by OSS.</p>
     */
    @BeforeClass
    public static void setUpQueryDataset() throws InterruptedException, IOException {
        queryDatasetName = "oss_" + accountId() + "_" + testBucketName;
        ossClient = getOssClient();

        // Upload sample JPEG objects carrying the routing tag so that they are routed
        // into the default dataset.
        for (int i = 0; i < SAMPLE_KEYS.length; i++) {
            byte[] body = generateJpgBytes(i + 1);
            ossClient.putObject(PutObjectRequest.newBuilder()
                    .bucket(testBucketName)
                    .key(SAMPLE_KEYS[i])
                    .tagging(ROUTING_TAG_KEY + "=" + queryDatasetName)
                    .body(BinaryData.fromBytes(body))
                    .build());
        }

        // Wait briefly for indexing to take effect.
        Thread.sleep(INDEX_WAIT_MS);
    }

    @AfterClass
    public static void tearDownQueryDataset() {
        // Delete uploaded sample objects only. The default dataset is OSS-managed
        // and intentionally preserved across test runs.
        if (ossClient != null) {
            for (String key : SAMPLE_KEYS) {
                try {
                    ossClient.deleteObject(DeleteObjectRequest.newBuilder()
                            .bucket(testBucketName)
                            .key(key)
                            .build());
                } catch (Exception ignored) {
                }
            }
        }
    }

    /**
     * Generates a tiny in-memory JPEG image (16x16) filled with a deterministic color
     * derived from the given seed. Avoids checking binary fixtures into the repo.
     */
    private static byte[] generateJpgBytes(int seed) throws IOException {
        BufferedImage img = new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = img.createGraphics();
        try {
            int r = (seed * 73) & 0xFF;
            int gC = (seed * 151) & 0xFF;
            int b = (seed * 199) & 0xFF;
            g.setColor(new Color(r, gC, b));
            g.fillRect(0, 0, 16, 16);
        } finally {
            g.dispose();
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        if (!ImageIO.write(img, "jpg", baos)) {
            throw new IOException("No JPEG ImageWriter available in the current JRE");
        }
        return baos.toByteArray();
    }

    /**
     * Newly added: simple-query the locally-uploaded JPEG samples by Filename prefix
     * against the default dataset {@code oss_<uid>_<bucket>}.
     */
    @Test
    public void testSimpleQueryByPrefix() {
        OSSDataProcessClient client = getDataProcessClient();

        SimpleQuery query = SimpleQuery.newBuilder()
                .field("Filename")
                .value(OBJECT_KEY_PREFIX)
                .operation("prefix")
                .build();

        SimpleQueryResult result = client.simpleQuery(
                SimpleQueryRequest.newBuilder()
                        .bucket(testBucketName)
                        .datasetName(queryDatasetName)
                        .query(query)
                        .maxResults(10)
                        .build());

        Assert.assertNotNull(result);
        Assert.assertEquals(200, result.statusCode());

        Assert.assertNotNull("files should not be null", result.files());
        Assert.assertFalse("expected uploaded objects to be queryable",
                result.files().isEmpty());

        // Every uploaded key should be present among the returned files.
        List<String> filenames = new ArrayList<>();
        for (File f : result.files()) {
            Assert.assertNotNull("URI should not be null", f.uri());
            Assert.assertNotNull("Filename should not be null", f.filename());
            Assert.assertNotNull("Size should not be null", f.size());
            filenames.add(f.filename());
        }
        for (String key : SAMPLE_KEYS) {
            Assert.assertTrue("uploaded key should appear in query results: " + key,
                    filenames.contains(key));
        }
    }

    /**
     * Newly added: exercise the raw-JSON overload of {@code SimpleQueryRequest#query(String)}
     * with a compound (Operation=and) condition.
     */
    @Test
    public void testSimpleQueryCompoundWithRawJson() {
        OSSDataProcessClient client = getDataProcessClient();

        String rawJson = "{\"Operation\":\"and\",\"SubQueries\":["
                + "{\"Field\":\"Filename\",\"Operation\":\"prefix\",\"Value\":\"" + OBJECT_KEY_PREFIX + "\"},"
                + "{\"Field\":\"Size\",\"Operation\":\"gt\",\"Value\":\"0\"}"
                + "]}";

        SimpleQueryResult result = client.simpleQuery(
                SimpleQueryRequest.newBuilder()
                        .bucket(testBucketName)
                        .datasetName(queryDatasetName)
                        .query(rawJson)
                        .maxResults(10)
                        .build());

        Assert.assertNotNull(result);
        Assert.assertEquals(200, result.statusCode());
        Assert.assertNotNull("files should not be null", result.files());
        Assert.assertFalse("compound query should match uploaded objects (size > 0 with given prefix)",
                result.files().isEmpty());
    }
}
