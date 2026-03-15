package com.aliyun.sdk.service.oss2.dataprocess;

import com.aliyun.sdk.service.oss2.TestBase;
import com.aliyun.sdk.service.oss2.credentials.CredentialsProvider;
import com.aliyun.sdk.service.oss2.credentials.StaticCredentialsProvider;
import com.aliyun.sdk.service.oss2.dataprocess.models.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import java.util.Optional;

public class TestBaseDataProcess extends TestBase {

    protected static final String DATASET_NAME_PREFIX = "test-dp-dataset-";

    public static String OSS_TEST_DATAPROCESS_BUCKET = null;
    public static String OSS_TEST_DATAPROCESS_ENDPOINT = null;

    private static OSSDataProcessClient dataProcessClient;
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
        testDatasetName = genDatasetName();
    }

    @AfterClass
    public static void oneTimeSetDown() {
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
}
