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

    @Test
    public void testSimpleQueryBasic() {
        OSSDataProcessClient client = getDataProcessClient();
        String dsName = "test-dataset";

        SimpleQuery query = SimpleQuery.newBuilder()
                .field("Filename")
                .value("test-media")
                .operation("prefix")
                .build();

        SimpleQueryResult result = client.simpleQuery(
                SimpleQueryRequest.newBuilder()
                        .bucket(testBucketName)
                        .datasetName(dsName)
                        .query(query)
                        .maxResults(10)
                        .build());

        Assert.assertNotNull(result);
        Assert.assertEquals(200, result.statusCode());

        // Verify files are returned
        Assert.assertNotNull("files should not be null", result.files());
        Assert.assertFalse("files should not be empty", result.files().isEmpty());

        // Verify basic fields of the first file
        File f = result.files().get(0);
        Assert.assertNotNull("URI should not be null", f.uri());
        Assert.assertNotNull("Filename should not be null", f.filename());
        Assert.assertTrue("Filename should start with test-media", f.filename().startsWith("test-media"));
        Assert.assertNotNull("MediaType should not be null", f.mediaType());
        Assert.assertNotNull("ContentType should not be null", f.contentType());
        Assert.assertNotNull("Size should not be null", f.size());
        Assert.assertTrue("Size should be > 0", f.size() > 0);
    }

    @Test
    public void testSimpleQueryWithAggregations() {
        OSSDataProcessClient client = getDataProcessClient();
        String dsName = "test-dataset";

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
                        .datasetName(dsName)
                        .query(query)
                        .aggregations(Collections.singletonList(aggregation))
                        .maxResults(10)
                        .build());

        Assert.assertNotNull(result);
        Assert.assertEquals(200, result.statusCode());

        // Aggregation-only query does not return files
        Assert.assertTrue("files should be null or empty when using aggregations",
                result.files() == null || result.files().isEmpty());

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
        String dsName = "test-dataset";

        SimpleQuery query = SimpleQuery.newBuilder()
                .field("Filename")
                .value("test-media")
                .operation("prefix")
                .build();

        SimpleQueryResult result = client.simpleQuery(
                SimpleQueryRequest.newBuilder()
                        .bucket(testBucketName)
                        .datasetName(dsName)
                        .query(query)
                        .sort("Filename")
                        .order("asc")
                        .maxResults(10)
                        .withoutTotalHits(false)
                        .build());

        Assert.assertNotNull(result);
        Assert.assertEquals(200, result.statusCode());

        // Verify files are returned and sorted by Filename ascending
        Assert.assertNotNull("files should not be null", result.files());
        Assert.assertFalse("files should not be empty", result.files().isEmpty());

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
        String dsName = "test-dataset";

        SimpleQuery query = SimpleQuery.newBuilder()
                .field("Filename")
                .value("test-media")
                .operation("prefix")
                .build();

        SimpleQueryResult result = client.simpleQuery(
                SimpleQueryRequest.newBuilder()
                        .bucket(testBucketName)
                        .datasetName(dsName)
                        .query(query)
                        .withFields(Arrays.asList("Filename", "Size", "ContentType"))
                        .maxResults(10)
                        .build());

        Assert.assertNotNull(result);
        Assert.assertEquals(200, result.statusCode());

        // Verify files
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
        String dsName = "test-dataset";

        SemanticQueryResult result = client.semanticQuery(
                SemanticQueryRequest.newBuilder()
                        .bucket(testBucketName)
                        .datasetName(dsName)
                        .query("雪景")
                        .maxResults(10)
                        .build());

        Assert.assertNotNull(result);
        Assert.assertEquals(200, result.statusCode());

        // Verify files are returned
        Assert.assertNotNull("files should not be null", result.files());
        Assert.assertFalse("files should not be empty", result.files().isEmpty());

        // Verify basic fields
        File f = result.files().get(0);
        Assert.assertNotNull("URI should not be null", f.uri());
        Assert.assertNotNull("Filename should not be null", f.filename());
        Assert.assertNotNull("MediaType should not be null", f.mediaType());
        Assert.assertNotNull("Size should not be null", f.size());
    }

    @Test
    public void testSemanticQueryWithMediaTypes() {
        OSSDataProcessClient client = getDataProcessClient();
        String dsName = "test-dataset";

        SemanticQueryResult result = client.semanticQuery(
                SemanticQueryRequest.newBuilder()
                        .bucket(testBucketName)
                        .datasetName(dsName)
                        .query("雪景")
                        .mediaTypes(Arrays.asList("image"))
                        .withFields(Arrays.asList("Filename", "Size", "MediaType"))
                        .maxResults(10)
                        .build());

        Assert.assertNotNull(result);
        Assert.assertEquals(200, result.statusCode());

        // Verify files are returned
        Assert.assertNotNull("files should not be null", result.files());
        Assert.assertFalse("files should not be empty", result.files().isEmpty());

        // Verify all returned files are images
        for (File f : result.files()) {
            Assert.assertEquals("MediaType should be image", "image", f.mediaType());
        }
    }

    @Test
    public void testSimpleQueryResponseContent() {
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
                            .datasetName(dsName)
                            .query(query)
                            .aggregations(Collections.singletonList(aggregation))
                            .maxResults(10)
                            .build());

            Assert.assertNotNull(result);
            Assert.assertEquals(200, result.statusCode());

            System.out.println("=== SimpleQuery Response ===");
            System.out.println("statusCode: " + result.statusCode());
            System.out.println("nextToken: " + result.nextToken());
            System.out.println("totalHits: " + result.totalHits());

            java.util.List<File> files = result.files();
            System.out.println("files: " + (files == null ? "null" : "size=" + files.size()));
            if (files != null) {
                for (int i = 0; i < files.size(); i++) {
                    File f = files.get(i);
                    System.out.println("  file[" + i + "]: URI=" + f.uri()
                            + ", Filename=" + f.filename()
                            + ", Size=" + f.size()
                            + ", ContentType=" + f.contentType()
                            + ", MediaType=" + f.mediaType());
                }
            }

            java.util.List<AggregationInfo> aggs = result.aggregations();
            System.out.println("aggregations: " + (aggs == null ? "null" : "size=" + aggs.size()));
            if (aggs != null) {
                for (int i = 0; i < aggs.size(); i++) {
                    AggregationInfo agg = aggs.get(i);
                    System.out.println("  agg[" + i + "]: Field=" + agg.field()
                            + ", Operation=" + agg.operation()
                            + ", Value=" + agg.value()
                            + ", Groups=" + (agg.groups() == null ? "null" : "size=" + agg.groups().size()));
                }
            }
            System.out.println("=== End SimpleQuery Response ===");
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
    public void testSemanticQueryResponseContent() {
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
            SemanticQueryResult result = client.semanticQuery(
                    SemanticQueryRequest.newBuilder()
                            .bucket(testBucketName)
                            .datasetName(dsName)
                            .query("雪景")
                            .maxResults(10)
                            .build());

            Assert.assertNotNull(result);
            Assert.assertEquals(200, result.statusCode());

            System.out.println("=== SemanticQuery Response ===");
            System.out.println("statusCode: " + result.statusCode());

            java.util.List<File> files = result.files();
            System.out.println("files: " + (files == null ? "null" : "size=" + files.size()));
            if (files != null) {
                for (int i = 0; i < files.size(); i++) {
                    File f = files.get(i);
                    System.out.println("  file[" + i + "]: URI=" + f.uri()
                            + ", Filename=" + f.filename()
                            + ", Size=" + f.size()
                            + ", ContentType=" + f.contentType()
                            + ", MediaType=" + f.mediaType());
                }
            }
            System.out.println("=== End SemanticQuery Response ===");
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

    /**
     * Setup: create a fixed "test-dataset" with WorkflowParameters.
     * This test only creates the dataset and does NOT delete it.
     * Run query tests manually after data is indexed.
     */
    @Test
    public void setupTestDataset() {
        OSSDataProcessClient client = getDataProcessClient();
        String dsName = "test-dataset";

        // Delete if already exists
        try {
            client.deleteDataset(DeleteDatasetRequest.newBuilder()
                    .bucket(testBucketName)
                    .datasetName(dsName)
                    .build());
            System.out.println("Deleted existing dataset: " + dsName);
        } catch (Exception ignored) {
        }

        List<WorkflowParameter> workflowParams = Arrays.asList(
                WorkflowParameter.newBuilder()
                        .name("VideoInsightEnable")
                        .value("true")
                        .build(),
                WorkflowParameter.newBuilder()
                        .name("ImageInsightEnable")
                        .value("true")
                        .build()
        );

        CreateDatasetResult createResult = client.createDataset(
                CreateDatasetRequest.newBuilder()
                        .bucket(testBucketName)
                        .datasetName(dsName)
                        .description("test dataset with insights enabled")
                        .workflowParameters(workflowParams)
                        .build());

        Assert.assertNotNull(createResult);
        Assert.assertEquals(200, createResult.statusCode());

        System.out.println("=== Dataset Created ===");
        System.out.println("bucket: " + testBucketName);
        System.out.println("datasetName: " + createResult.dataset().datasetName());
        System.out.println("description: " + createResult.dataset().description());

        // Verify via get
        GetDatasetResult getResult = client.getDataset(
                GetDatasetRequest.newBuilder()
                        .bucket(testBucketName)
                        .datasetName(dsName)
                        .build());

        System.out.println("--- GetDataset verify ---");
        System.out.println("datasetName: " + getResult.dataset().datasetName());
        System.out.println("workflowParameters: " + getResult.dataset().workflowParameters());
        if (getResult.dataset().workflowParameters() != null
                && getResult.dataset().workflowParameters().workflowParameters() != null) {
            for (WorkflowParameter p : getResult.dataset().workflowParameters().workflowParameters()) {
                System.out.println("  " + p.name() + " = " + p.value());
            }
        }
        System.out.println("=== Done. Dataset NOT deleted. Index your files then run query tests. ===");
    }

    /**
     * Query the fixed "test-dataset" after data has been indexed.
     */
    @Test
    public void queryTestDataset() {
        OSSDataProcessClient client = getDataProcessClient();
        String dsName = "test-dataset";

        // SimpleQuery
        SimpleQuery query = SimpleQuery.newBuilder()
                .field("Filename")
                .value("test-media")
                .operation("prefix")
                .build();

        Aggregation aggregation = Aggregation.newBuilder()
                .field("Size")
                .operation("sum")
                .build();

        SimpleQueryResult sqResult = client.simpleQuery(
                SimpleQueryRequest.newBuilder()
                        .bucket(testBucketName)
                        .datasetName(dsName)
                        .query(query)
                        .aggregations(Collections.singletonList(aggregation))
                        .maxResults(20)
                        .build());

        System.out.println("=== SimpleQuery on test-dataset ===");
        System.out.println("statusCode: " + sqResult.statusCode());
        System.out.println("nextToken: " + sqResult.nextToken());
        System.out.println("totalHits: " + sqResult.totalHits());

        java.util.List<File> sqFiles = sqResult.files();
        System.out.println("files: " + (sqFiles == null ? "null" : "size=" + sqFiles.size()));
        if (sqFiles != null) {
            for (int i = 0; i < sqFiles.size(); i++) {
                File f = sqFiles.get(i);
                System.out.println("  file[" + i + "]: URI=" + f.uri()
                        + ", Filename=" + f.filename()
                        + ", Size=" + f.size()
                        + ", ContentType=" + f.contentType()
                        + ", MediaType=" + f.mediaType());
            }
        }

        java.util.List<AggregationInfo> aggs = sqResult.aggregations();
        System.out.println("aggregations: " + (aggs == null ? "null" : "size=" + aggs.size()));
        if (aggs != null) {
            for (int i = 0; i < aggs.size(); i++) {
                AggregationInfo agg = aggs.get(i);
                System.out.println("  agg[" + i + "]: Field=" + agg.field()
                        + ", Operation=" + agg.operation()
                        + ", Value=" + agg.value());
            }
        }

        // SemanticQuery
        SemanticQueryResult semResult = client.semanticQuery(
                SemanticQueryRequest.newBuilder()
                        .bucket(testBucketName)
                        .datasetName(dsName)
                        .query("雪景")
                        .maxResults(20)
                        .build());

        System.out.println("=== SemanticQuery on test-dataset ===");
        System.out.println("statusCode: " + semResult.statusCode());

        java.util.List<File> semFiles = semResult.files();
        System.out.println("files: " + (semFiles == null ? "null" : "size=" + semFiles.size()));
        if (semFiles != null) {
            for (int i = 0; i < semFiles.size(); i++) {
                File f = semFiles.get(i);
                System.out.println("  file[" + i + "]: URI=" + f.uri()
                        + ", Filename=" + f.filename()
                        + ", Size=" + f.size()
                        + ", ContentType=" + f.contentType()
                        + ", MediaType=" + f.mediaType());
            }
        }
        System.out.println("=== End query tests ===");
    }

    /**
     * Debug: SimpleQuery on test-dataset, print full File structure.
     */
    @Test
    public void debugSimpleQueryFullFile() {
        OSSDataProcessClient client = getDataProcessClient();
        String dsName = "test-dataset";

        SimpleQuery query = SimpleQuery.newBuilder()
                .field("Filename")
                .value("test-media")
                .operation("prefix")
                .build();

        SimpleQueryResult result = client.simpleQuery(
                SimpleQueryRequest.newBuilder()
                        .bucket(testBucketName)
                        .datasetName(dsName)
                        .query(query)
                        .maxResults(5)
                        .build());

        System.out.println("=== debugSimpleQueryFullFile ===");
        System.out.println("statusCode: " + result.statusCode());
        System.out.println("nextToken: " + result.nextToken());
        System.out.println("totalHits: " + result.totalHits());

        java.util.List<File> files = result.files();
        System.out.println("files: " + (files == null ? "null" : "count=" + files.size()));
        if (files != null) {
            for (int i = 0; i < files.size(); i++) {
                System.out.println("--- file[" + i + "] ---");
                printFileDetail(files.get(i));
            }
        }
        System.out.println("=== end ===");
    }

    /**
     * Debug: SemanticQuery on test-dataset, print full File structure.
     */
    @Test
    public void debugSemanticQueryFullFile() {
        OSSDataProcessClient client = getDataProcessClient();
        String dsName = "test-dataset";

        SemanticQueryResult result = client.semanticQuery(
                SemanticQueryRequest.newBuilder()
                        .bucket(testBucketName)
                        .datasetName(dsName)
                        .query("雪景")
                        .maxResults(5)
                        .build());

        System.out.println("=== debugSemanticQueryFullFile ===");
        System.out.println("statusCode: " + result.statusCode());

        java.util.List<File> files = result.files();
        System.out.println("files: " + (files == null ? "null" : "count=" + files.size()));
        if (files != null) {
            for (int i = 0; i < files.size(); i++) {
                System.out.println("--- file[" + i + "] ---");
                printFileDetail(files.get(i));
            }
        }
        System.out.println("=== end ===");
    }

    private void printFileDetail(File f) {
        System.out.println("  OwnerId=" + f.ownerId());
        System.out.println("  DatasetName=" + f.datasetName());
        System.out.println("  ObjectType=" + f.objectType());
        System.out.println("  ObjectId=" + f.objectId());
        System.out.println("  UpdateTime=" + f.updateTime());
        System.out.println("  CreateTime=" + f.createTime());
        System.out.println("  URI=" + f.uri());
        System.out.println("  OSSURI=" + f.ossUri());
        System.out.println("  Filename=" + f.filename());
        System.out.println("  MediaType=" + f.mediaType());
        System.out.println("  ContentType=" + f.contentType());
        System.out.println("  Size=" + f.size());
        System.out.println("  FileHash=" + f.fileHash());
        System.out.println("  FileModifiedTime=" + f.fileModifiedTime());
        System.out.println("  FileCreateTime=" + f.fileCreateTime());
        System.out.println("  FileAccessTime=" + f.fileAccessTime());
        System.out.println("  ProduceTime=" + f.produceTime());
        System.out.println("  LatLong=" + f.latLong());
        System.out.println("  Timezone=" + f.timezone());
        System.out.println("  Addresses=" + (f.addresses() == null ? "null" : "size=" + f.addresses().size()));
        System.out.println("  TravelClusterId=" + f.travelClusterId());
        System.out.println("  Orientation=" + f.orientation());
        System.out.println("  Figures=" + (f.figures() == null ? "null" : "size=" + f.figures().size()));
        System.out.println("  FigureCount=" + f.figureCount());
        System.out.println("  Labels=" + (f.labels() == null ? "null" : "size=" + f.labels().size()));
        System.out.println("  Title=" + f.title());
        System.out.println("  ImageWidth=" + f.imageWidth());
        System.out.println("  ImageHeight=" + f.imageHeight());
        System.out.println("  EXIF=" + f.exif());
        System.out.println("  ImageScore=" + f.imageScore());
        System.out.println("  CroppingSuggestions=" + (f.croppingSuggestions() == null ? "null" : "size=" + f.croppingSuggestions().size()));
        System.out.println("  OCRContents=" + (f.ocrContents() == null ? "null" : "size=" + f.ocrContents().size()));
        if (f.ocrContents() != null) {
            for (int i = 0; i < f.ocrContents().size(); i++) {
                OCRContents ocr = f.ocrContents().get(i);
                System.out.println("    ocr[" + i + "]: Language=" + ocr.language()
                        + ", Contents=" + ocr.contents()
                        + ", Confidence=" + ocr.confidence());
            }
        }
        System.out.println("  VideoWidth=" + f.videoWidth());
        System.out.println("  VideoHeight=" + f.videoHeight());
        System.out.println("  VideoStreams=" + (f.videoStreams() == null ? "null" : "size=" + f.videoStreams().size()));
        System.out.println("  Subtitles=" + (f.subtitles() == null ? "null" : "size=" + f.subtitles().size()));
        System.out.println("  AudioStreams=" + (f.audioStreams() == null ? "null" : "size=" + f.audioStreams().size()));
        System.out.println("  Artist=" + f.artist());
        System.out.println("  AlbumArtist=" + f.albumArtist());
        System.out.println("  AudioCovers=" + (f.audioCovers() == null ? "null" : "size=" + f.audioCovers().size()));
        System.out.println("  Composer=" + f.composer());
        System.out.println("  Performer=" + f.performer());
        System.out.println("  Language=" + f.language());
        System.out.println("  Album=" + f.album());
        System.out.println("  PageCount=" + f.pageCount());
        System.out.println("  ETag=" + f.eTag());
        System.out.println("  CacheControl=" + f.cacheControl());
        System.out.println("  ContentDisposition=" + f.contentDisposition());
        System.out.println("  ContentEncoding=" + f.contentEncoding());
        System.out.println("  ContentLanguage=" + f.contentLanguage());
        System.out.println("  AccessControlAllowOrigin=" + f.accessControlAllowOrigin());
        System.out.println("  AccessControlRequestMethod=" + f.accessControlRequestMethod());
        System.out.println("  ServerSideEncryptionCustomerAlgorithm=" + f.serverSideEncryptionCustomerAlgorithm());
        System.out.println("  ServerSideEncryption=" + f.serverSideEncryption());
        System.out.println("  ServerSideDataEncryption=" + f.serverSideDataEncryption());
        System.out.println("  ServerSideEncryptionKeyId=" + f.serverSideEncryptionKeyId());
        System.out.println("  OSSStorageClass=" + f.ossStorageClass());
        System.out.println("  OSSCRC64=" + f.ossCrc64());
        System.out.println("  ObjectACL=" + f.objectAcl());
        System.out.println("  ContentMd5=" + f.contentMd5());
        System.out.println("  OSSUserMeta=" + f.ossUserMeta());
        System.out.println("  OSSTaggingCount=" + f.ossTaggingCount());
        System.out.println("  OSSTagging=" + f.ossTagging());
        System.out.println("  OSSExpiration=" + f.ossExpiration());
        System.out.println("  OSSVersionId=" + f.ossVersionId());
        System.out.println("  OSSDeleteMarker=" + f.ossDeleteMarker());
        System.out.println("  OSSObjectType=" + f.ossObjectType());
        System.out.println("  CustomId=" + f.customId());
        System.out.println("  CustomLabels=" + f.customLabels());
        System.out.println("  StreamCount=" + f.streamCount());
        System.out.println("  ProgramCount=" + f.programCount());
        System.out.println("  FormatName=" + f.formatName());
        System.out.println("  FormatLongName=" + f.formatLongName());
        System.out.println("  StartTime=" + f.startTime());
        System.out.println("  Bitrate=" + f.bitrate());
        System.out.println("  Duration=" + f.duration());
        System.out.println("  SemanticTypes=" + f.semanticTypes());
        System.out.println("  Elements=" + (f.elements() == null ? "null" : "size=" + f.elements().size()));
        System.out.println("  SceneElements=" + (f.sceneElements() == null ? "null" : "size=" + f.sceneElements().size()));
        System.out.println("  OCRTexts=" + f.ocrTexts());
        System.out.println("  Reason=" + f.reason());
        System.out.println("  ObjectStatus=" + f.objectStatus());
        if (f.insights() != null) {
            Insights ins = f.insights();
            System.out.println("  Insights.Video=" + (ins.video() == null ? "null"
                    : "{Caption=" + ins.video().caption() + ", Description=" + ins.video().description() + "}"));
            System.out.println("  Insights.Image=" + (ins.image() == null ? "null"
                    : "{Caption=" + ins.image().caption() + ", Description=" + ins.image().description() + "}"));
        } else {
            System.out.println("  Insights=null");
        }
    }
}
