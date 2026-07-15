package com.aliyun.sdk.service.oss2.agentic;

import com.aliyun.sdk.service.oss2.TestBase;
import com.aliyun.sdk.service.oss2.agentic.models.*;
import com.aliyun.sdk.service.oss2.credentials.CredentialsProvider;
import com.aliyun.sdk.service.oss2.credentials.StaticCredentialsProvider;
import org.junit.AfterClass;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TestBaseAgentic extends TestBase {
    protected static final String AGENTIC_BUCKET_NAME_PREFIX = "java-sdk-test-ab-";

    private static final List<String> createdBuckets = new ArrayList<>();

    @AfterClass
    public static void oneTimeSetDown() {
        for (String bucket : createdBuckets) {
            cleanAgenticBucket(bucket);
        }
        createdBuckets.clear();
    }

    protected static String genAgenticBucketName() {
        String bucketName = AGENTIC_BUCKET_NAME_PREFIX + System.currentTimeMillis() + "-" +
                new Random().nextInt(10000);
        createdBuckets.add(bucketName);
        return bucketName;
    }

    public static OSSAgenticBucketClient newAgenticClient() {
        CredentialsProvider provider = new StaticCredentialsProvider(accessKeyId(), accessKeySecret());
        return OSSAgenticBucketClient.newBuilder()
                .region(region())
                .endpoint(endpoint())
                .accountId(accountId())
                .credentialsProvider(provider)
                .build();
    }

    public static OSSAgenticBucketClient newInvalidAkAgenticClient() {
        CredentialsProvider provider = new StaticCredentialsProvider("invalid-ak", "invalid-sk");
        return OSSAgenticBucketClient.newBuilder()
                .region(region())
                .endpoint(endpoint())
                .accountId(accountId())
                .credentialsProvider(provider)
                .build();
    }

    public static void createAgenticBucket(OSSAgenticBucketClient client, String bucket) {
        client.createAgenticBucket(CreateAgenticBucketRequest.newBuilder()
                .bucket(bucket)
                .createAgenticBucketConfiguration(CreateAgenticBucketConfiguration.newBuilder()
                        .storageClass("Standard")
                        .dataRedundancyType("LRS")
                        .build())
                .build());
        waitForCacheExpiration(1);
    }

    // Best-effort cleanup: remove attached properties before deleting the bucket.
    public static void cleanAgenticBucket(String bucket) {
        OSSAgenticBucketClient client = newAgenticClient();
        try {
            client.deleteAgenticBucketPolicy(DeleteAgenticBucketPolicyRequest.newBuilder()
                    .bucket(bucket).build());
        } catch (Exception ignore) {
        }
        try {
            client.deleteAgenticBucketEncryption(DeleteAgenticBucketEncryptionRequest.newBuilder()
                    .bucket(bucket).build());
        } catch (Exception ignore) {
        }
        try {
            client.deleteAgenticBucketPublicAccessBlock(DeleteAgenticBucketPublicAccessBlockRequest.newBuilder()
                    .bucket(bucket).build());
        } catch (Exception ignore) {
        }
        try {
            client.deleteAgenticBucket(DeleteAgenticBucketRequest.newBuilder()
                    .bucket(bucket).build());
        } catch (Exception ignore) {
        }
    }

    protected static <T extends Throwable> T findCause(Throwable throwable, Class<T> type) {
        Throwable cause = throwable;
        while (cause != null) {
            if (type.isInstance(cause)) {
                return (T) cause;
            }
            cause = cause.getCause();
        }
        return null;
    }
}
