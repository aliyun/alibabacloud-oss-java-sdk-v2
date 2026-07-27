package com.aliyun.sdk.service.oss2.agentic;

import com.aliyun.sdk.service.oss2.TestBase;
import com.aliyun.sdk.service.oss2.agentic.models.*;
import com.aliyun.sdk.service.oss2.agentic.paginator.ListAgenticBucketsIterable;
import com.aliyun.sdk.service.oss2.credentials.CredentialsProvider;
import com.aliyun.sdk.service.oss2.credentials.StaticCredentialsProvider;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import java.util.Random;

public class TestBaseAgentic extends TestBase {
    protected static final String AGENTIC_BUCKET_NAME_PREFIX = "oss-sdk-test-java-ab-";

    protected static String agenticBucketNamePrefix;
    protected static String agenticBucketName;
    protected static OSSAgenticBucketClient agenticClient;

    @BeforeClass
    public static void oneTimeSetUp() {
        agenticBucketNamePrefix = genAgenticBucketNamePrefix();
        agenticBucketName = genAgenticBucketName();
        agenticClient = newAgenticClient();
        createAgenticBucket(agenticClient, agenticBucketName);
    }

    @AfterClass
    public static void oneTimeSetDown() {
        cleanAgenticBuckets(agenticBucketNamePrefix);
    }

    public static String genAgenticBucketNamePrefix() {
        long val = new Random().nextInt(500);
        return AGENTIC_BUCKET_NAME_PREFIX + val;
    }

    public static String genAgenticBucketName() {
        return agenticBucketNamePrefix;
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

    public static OSSAsyncAgenticBucketClient newAgenticAsyncClient() {
        CredentialsProvider provider = new StaticCredentialsProvider(accessKeyId(), accessKeySecret());
        return OSSAsyncAgenticBucketClient.newBuilder()
                .region(region())
                .endpoint(endpoint())
                .accountId(accountId())
                .credentialsProvider(provider)
                .build();
    }

    public static OSSAsyncAgenticBucketClient newInvalidAkAgenticAsyncClient() {
        CredentialsProvider provider = new StaticCredentialsProvider("invalid-ak", "invalid-sk");
        return OSSAsyncAgenticBucketClient.newBuilder()
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
        // Disable the bucket before deletion (required: 409 AgenticBucketNotDisabled)
        try {
            client.putAgenticBucketStatus(PutAgenticBucketStatusRequest.newBuilder()
                    .bucket(bucket)
                    .agenticBucketStatus(AgenticBucketStatus.newBuilder()
                            .status("Disabled")
                            .build())
                    .build());
        } catch (Exception ignore) {
        }
        try {
            client.deleteAgenticBucket(DeleteAgenticBucketRequest.newBuilder()
                    .bucket(bucket).build());
        } catch (Exception ignore) {
        }
    }

    public static void cleanAgenticBuckets(String prefix) {
        OSSAgenticBucketClient client = newAgenticClient();
        ListAgenticBucketsIterable iterable = client.listAgenticBucketsPaginator(
                ListAgenticBucketsRequest.newBuilder().build());
        for (ListAgenticBucketsResult result : iterable) {
            if (result.agenticBuckets() != null) {
                for (AgenticBucketSummary bucket : result.agenticBuckets()) {
                    if (bucket.name() != null && bucket.name().startsWith(prefix)) {
                        cleanAgenticBucket(bucket.name());
                    }
                }
            }
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
