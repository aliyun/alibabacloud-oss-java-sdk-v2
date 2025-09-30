package com.aliyun.sdk.service.oss2.vectors;

import com.aliyun.sdk.service.oss2.TestBase;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import java.util.ArrayList;
import java.util.List;

public class TestBaseVectors extends TestBase {
    private static final String VECTOR_BUCKET_NAME_PREFIX = "test-vector-";

    private static OSSVectorsClient vectorClient;
    private static List<String> createdBuckets = new ArrayList<>();
    protected static String vectorBucketName;


    @BeforeClass
    public static void oneTimeSetUp() {
        vectorClient = getVectorsClient();
        vectorBucketName = genVectorBucketName();
        createVectorBucket(vectorBucketName);
    }

    @AfterClass
    public static void oneTimeSetDown() {
        // Clean up all created vector buckets
        cleanVectorBuckets(VECTOR_BUCKET_NAME_PREFIX);
    }


    protected static String genVectorBucketName() {
        String bucketName = VECTOR_BUCKET_NAME_PREFIX + System.currentTimeMillis() + "-" +
                (int) (Math.random() * 10000);
        createdBuckets.add(bucketName);
        return bucketName;
    }

    protected static OSSVectorsClient getVectorClient() {
        return vectorClient;
    }
}
