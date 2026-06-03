package com.aliyun.sdk.service.oss2.dataprocess;

import com.aliyun.sdk.service.oss2.TestBase;
import com.aliyun.sdk.service.oss2.credentials.CredentialsProvider;
import com.aliyun.sdk.service.oss2.credentials.StaticCredentialsProvider;
import com.aliyun.sdk.service.oss2.dataprocess.models.*;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;

import java.util.Arrays;
import java.util.Optional;

public class TestBaseDataProcess extends TestBase {

    protected static final String DATASET_NAME_PREFIX = "test-dp-dataset-";

    public static String OSS_TEST_DATAPROCESS_BUCKET = null;
    public static String OSS_TEST_DATAPROCESS_ENDPOINT = null;

    private static OSSDataProcessClient dataProcessClient;
    private static OSSDataProcessAsyncClient dataProcessAsyncClient;
    protected static String testBucketName;
    protected static String testDatasetName;

    public static String dataProcessBucket() {
        return Optional.ofNullable(OSS_TEST_DATAPROCESS_BUCKET)
                .orElse(System.getenv().get("OSS_TEST_DATAPROCESS_BUCKET"));
    }

    public static String dataProcessEndpoint() {
        String ep = Optional.ofNullable(OSS_TEST_DATAPROCESS_ENDPOINT)
                .orElseGet(() -> System.getenv().get("OSS_TEST_DATAPROCESS_ENDPOINT"));
        return ep != null ? ep : endpoint();
    }

    public static String apiKey() {
        return Optional.ofNullable(API_KEY).orElse(System.getenv().get("API_KEY"));
    }

    public static String modelType() {
        return Optional.ofNullable(MODEL_TYPE).orElse(System.getenv().get("MODEL_TYPE"));
    }

    public static String dimension() {
        return Optional.ofNullable(DIMENSION).orElse(System.getenv().get("DIMENSION"));
    }

    public static String roleName() {
        return Optional.ofNullable(ROLE_NAME).orElse(System.getenv().get("ROLE_NAME"));
    }


    @BeforeClass
    public static void oneTimeSetUp() {
        TestBase.oneTimeSetUp();
        testBucketName = dataProcessBucket();
        if (testBucketName == null || testBucketName.isEmpty()) {
            throw new IllegalStateException(
                    "OSS_TEST_DATAPROCESS_BUCKET environment variable is required. " +
                    "Please set it to a bucket that has DataProcess/IMM service enabled.");
        }
        dataProcessClient = createDataProcessClient();
        dataProcessAsyncClient = createDataProcessAsyncClient();
        testDatasetName = genDatasetName();

        // Prerequisite: all dataprocess Client* integration tests depend on MetaQuery being open.
        // Default to semantic mode so other tests (Dataset/Query/SmartCluster/DataPipeline/DeleteFileMeta)
        // can run uniformly. ClientMetaQueryTest will close & reopen with basic mode for its own cases.
        openMetaQueryQuietly("semantic");
    }

    @AfterClass
    public static void oneTimeSetDown() {
        // Close MetaQuery if still open (best-effort).
        closeMetaQueryQuietly();


        // Clean up created datasets
        if (dataProcessClient != null && testDatasetName != null) {
            try {
                dataProcessClient.deleteDataset(DeleteDatasetRequest.newBuilder()
                        .bucket(testBucketName)
                        .datasetName(testDatasetName)
                        .build());
            } catch (Exception ignored) {
            }
        }
        TestBase.oneTimeSetDown();
    }

    /**
     * Opens MetaQuery on the test bucket with the given mode in a best-effort manner.
     * Swallows exceptions (e.g. already opened with the same/different mode) so that
     * test setup remains resilient.
     */
    protected static void openMetaQueryQuietly(String mode) {
        if (dataProcessClient == null || testBucketName == null || testBucketName.isEmpty()) {
            return;
        }
        try {
            dataProcessClient.openMetaQuery(
                    OpenMetaQueryRequest.newBuilder()
                            .bucket(testBucketName)
                            .mode(mode)
                            .metaQueryBody(MetaQueryOpenBody.newBuilder()
//                                    .routeRule(RouteRule.newBuilder()
//                                            .type("OSSTag")
//                                            .autoCreateDataset("true")
//                                            .ossTagKey("test-routing-dataset:dataset-name")
//                                            .build())
//                                    .indexOptions(IndexOptions.newBuilder()
//                                            .ignoreObjectDelete("true")
//                                            .build())
                                    .build())
                            .build());
        } catch (Exception ignored) {
        }
    }

    /**
     * Closes MetaQuery on the test bucket in a best-effort manner.
     */
    protected static void closeMetaQueryQuietly() {
        if (dataProcessClient == null || testBucketName == null || testBucketName.isEmpty()) {
            return;
        }
        try {
            dataProcessClient.closeMetaQuery(
                    CloseMetaQueryRequest.newBuilder()
                            .bucket(testBucketName)
                            .build());
        } catch (Exception ignored) {
        }
    }

    protected static String genDatasetName() {
        return DATASET_NAME_PREFIX + System.currentTimeMillis() + "-" + (int) (Math.random() * 10000);
    }

    protected static OSSDataProcessClient createDataProcessClient() {
        CredentialsProvider provider = new StaticCredentialsProvider(accessKeyId(), accessKeySecret());
        return OSSDataProcessClient.newBuilder()
                .region(region())
                .endpoint(dataProcessEndpoint())
                .credentialsProvider(provider)
                .build();
    }

    protected static OSSDataProcessClient getDataProcessClient() {
        return dataProcessClient;
    }

    protected static OSSDataProcessAsyncClient createDataProcessAsyncClient() {
        CredentialsProvider provider = new StaticCredentialsProvider(accessKeyId(), accessKeySecret());
        return OSSDataProcessAsyncClient.newBuilder()
                .region(region())
                .endpoint(dataProcessEndpoint())
                .credentialsProvider(provider)
                .build();
    }

    protected static OSSDataProcessAsyncClient getDataProcessAsyncClient() {
        return dataProcessAsyncClient;
    }
}
