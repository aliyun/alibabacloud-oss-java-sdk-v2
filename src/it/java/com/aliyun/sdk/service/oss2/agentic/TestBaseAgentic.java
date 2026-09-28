package com.aliyun.sdk.service.oss2.agentic;

import com.aliyun.sdk.service.oss2.OSSClient;
import com.aliyun.sdk.service.oss2.TestBase;
import com.aliyun.sdk.service.oss2.agentic.models.*;
import com.aliyun.sdk.service.oss2.agentic.paginator.ListAgenticBucketsIterable;
import com.aliyun.sdk.service.oss2.agentic.paginator.ListBucketSpacesIterable;
import com.aliyun.sdk.service.oss2.credentials.CredentialsProvider;
import com.aliyun.sdk.service.oss2.credentials.StaticCredentialsProvider;
import com.aliyun.sdk.service.oss2.exceptions.ServiceException;
import com.aliyun.sdk.service.oss2.models.DeleteBucketRequest;
import com.aliyun.sdk.service.oss2.models.DeleteObjectRequest;
import com.aliyun.sdk.service.oss2.models.ListObjectsV2Request;
import com.aliyun.sdk.service.oss2.models.ListObjectsV2Result;
import com.aliyun.sdk.service.oss2.models.ObjectSummary;
import com.aliyun.sdk.service.oss2.paginator.ListObjectsV2Iterable;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;

import java.util.Random;

/**
 * Shared helpers for the agentic integration tests: client factories, name builders and the
 * prefix based reaper that bounds the backlog left by the two-phase agentic bucket lifecycle.
 * <p>
 * The service requires PutAgenticBucketStatus(Disabled) before DeleteAgenticBucket, and the bucket
 * only becomes deletable roughly 24 hours later. A run therefore cannot delete the buckets it
 * creates; it only marks them Disabled and reclaims the ones left behind by earlier runs whose
 * readiness window has elapsed.
 */
public class TestBaseAgentic extends TestBase {
    /**
     * The 'ab' / 'bs' markers are what the reaper filters on. The prefixes are kept short on
     * purpose: the resolved name {bucket}-{accountId}-{region}-ab-apsr becomes a DNS host label
     * and must stay within 63 characters, which leaves 23 characters for prefix plus random part
     * (63 - 1 - 16 for the account id - 1 - 14 for the longest region - 8 for '-ab-apsr').
     */
    protected static final String AGENTIC_BUCKET_NAME_PREFIX = getAgenticBucketNamePrefix();
    protected static final String BUCKET_SPACE_NAME_PREFIX = getBucketSpacePrefix();

    private static String getAgenticBucketNamePrefix() {
        String val = System.getenv("OSS_TEST_BUCKET_PREFIX");
        if (val != null && !val.isEmpty()) {
            return val + "ab-";
        }
        return "oss-sdk-test-ab-";
    }

    private static String getBucketSpacePrefix() {
        String val = System.getenv("OSS_TEST_BUCKET_PREFIX");
        if (val != null && !val.isEmpty()) {
            return val + "bs-";
        }
        return "oss-sdk-test-bs-";
    }

    /** The tail the service appends to an agentic bucket name. */
    protected static final String AGENTIC_BUCKET_SUFFIX = "ab-apsr";

    /** The tail the service appends to a bucket space name. */
    protected static final String BUCKET_SPACE_SUFFIX = "bs-apsr";

    private static final String LETTERS = "abcdefghijklmnopqrstuvwxyz";
    private static final int RANDOM_NAME_LENGTH = 6;
    protected static final int LIST_RETRY_TIMES = 10;
    protected static final int LIST_RETRY_INTERVAL_SECONDS = 3;

    protected static String agenticBucketName;
    protected static OSSAgenticBucketClient agenticClient;

    @BeforeClass
    public static void oneTimeSetUp() {
        agenticBucketName = genAgenticBucketName();
        agenticClient = newAgenticClient();
        createAgenticBucket(agenticClient, agenticBucketName);
    }

    @AfterClass
    public static void oneTimeSetDown() {
        // A bucket left Enabled can never be reclaimed, so disable this run's bucket even when the
        // scenario failed. Only then reap the backlog of the previous runs.
        disableAgenticBucketQuietly(agenticBucketName);
        reapDisabledAgenticBuckets();
        if (agenticClient != null) {
            try {
                agenticClient.close();
            } catch (Exception ignore) {
            }
        }
    }

    /**
     * Fixed-length random suffix: names must not be prefixes of one another, otherwise the reaper
     * would also match the bucket of a concurrently running job.
     */
    public static String genAgenticBucketName() {
        return AGENTIC_BUCKET_NAME_PREFIX + randStr(RANDOM_NAME_LENGTH);
    }

    public static String genBucketSpaceName() {
        return BUCKET_SPACE_NAME_PREFIX + randStr(RANDOM_NAME_LENGTH);
    }

    private static String randStr(int length) {
        Random random = new Random();
        StringBuilder builder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            builder.append(LETTERS.charAt(random.nextInt(LETTERS.length())));
        }
        return builder.toString();
    }

    /**
     * Resolves a short name to the server side full name {bucket}-{accountId}-{region}-{suffix}.
     */
    public static String buildFullName(String bucket, String suffix) {
        return bucket + "-" + accountId() + "-" + region() + "-" + suffix;
    }

    /**
     * Strips the resolved tail so a listed physical name can be handed back to a client that
     * re-expands short names.
     */
    public static String toShortName(String fullName, String suffix) {
        String tail = "-" + accountId() + "-" + region() + "-" + suffix;
        if (fullName.endsWith(tail)) {
            return fullName.substring(0, fullName.length() - tail.length());
        }
        return fullName;
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

    public static OSSAgenticBucketClient newAgenticClientPathStyle() {
        CredentialsProvider provider = new StaticCredentialsProvider(accessKeyId(), accessKeySecret());
        return OSSAgenticBucketClient.newBuilder()
                .region(region())
                .endpoint(endpoint())
                .accountId(accountId())
                .credentialsProvider(provider)
                .usePathStyle(true)
                .build();
    }

    public static OSSAsyncAgenticBucketClient newAgenticAsyncClientPathStyle() {
        CredentialsProvider provider = new StaticCredentialsProvider(accessKeyId(), accessKeySecret());
        return OSSAsyncAgenticBucketClient.newBuilder()
                .region(region())
                .endpoint(endpoint())
                .accountId(accountId())
                .credentialsProvider(provider)
                .usePathStyle(true)
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

    /**
     * A newly created agentic bucket only shows up in ListAgenticBuckets after a while, so poll.
     * Returns false when it is still missing; the caller skips instead of failing, the existence of
     * the bucket is already asserted by GetAgenticBucket.
     */
    public static boolean waitForAgenticBucketListed(OSSAgenticBucketClient client, String bucket) {
        for (int i = 0; i < LIST_RETRY_TIMES; i++) {
            ListAgenticBucketsIterable iterable = client.listAgenticBucketsPaginator(
                    ListAgenticBucketsRequest.newBuilder().build());
            for (ListAgenticBucketsResult page : iterable) {
                Assert.assertEquals(200, page.statusCode());
                if (page.agenticBuckets() == null) {
                    continue;
                }
                for (AgenticBucketSummary summary : page.agenticBuckets()) {
                    if (summary.name() != null && summary.name().contains(bucket)) {
                        return true;
                    }
                }
            }
            waitForCacheExpiration(LIST_RETRY_INTERVAL_SECONDS);
        }
        return false;
    }

    /** Best-effort: the bucket of the current run must not be left Enabled. */
    public static void disableAgenticBucketQuietly(String bucket) {
        if (bucket == null) {
            return;
        }
        try (OSSAgenticBucketClient client = newAgenticClient()) {
            client.putAgenticBucketStatus(PutAgenticBucketStatusRequest.newBuilder()
                    .bucket(bucket)
                    .agenticBucketStatus(AgenticBucketStatus.newBuilder()
                            .status("Disabled")
                            .build())
                    .build());
        } catch (Exception ignore) {
        }
    }

    /**
     * Reclaims the agentic buckets left behind by the previous runs: only the ones already Disabled
     * are touched, an Enabled one may belong to a concurrently running job. Best-effort, every
     * error is swallowed so that teardown never fails.
     */
    public static void reapDisabledAgenticBuckets() {
        try (OSSAgenticBucketClient client = newAgenticClient()) {
            ListAgenticBucketsIterable iterable = client.listAgenticBucketsPaginator(
                    ListAgenticBucketsRequest.newBuilder().build());
            for (ListAgenticBucketsResult result : iterable) {
                if (result.agenticBuckets() == null) {
                    continue;
                }
                for (AgenticBucketSummary bucket : result.agenticBuckets()) {
                    if (bucket.name() == null || !bucket.name().startsWith(AGENTIC_BUCKET_NAME_PREFIX)) {
                        continue;
                    }
                    reapDisabledAgenticBucket(client, toShortName(bucket.name(), AGENTIC_BUCKET_SUFFIX));
                }
            }
        } catch (Exception ignore) {
        }
    }

    private static void reapDisabledAgenticBucket(OSSAgenticBucketClient client, String bucket) {
        // The list summary carries no status, so fetch it: anything not Disabled is off limits.
        String status = null;
        try {
            GetAgenticBucketResult result = client.getAgenticBucket(
                    GetAgenticBucketRequest.newBuilder().bucket(bucket).build());
            if (result.agenticBucketInfo() != null) {
                status = result.agenticBucketInfo().status();
            }
        } catch (Exception ignore) {
        }
        if (!"Disabled".equals(status)) {
            return;
        }
        detachAgenticBucketProperties(client, bucket);
        reapBucketSpaces(client, bucket);
        // Answers 409 AgenticBucketNotReady until the readiness window has elapsed.
        try {
            client.deleteAgenticBucket(DeleteAgenticBucketRequest.newBuilder().bucket(bucket).build());
        } catch (Exception ignore) {
        }
    }

    private static void detachAgenticBucketProperties(OSSAgenticBucketClient client, String bucket) {
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
    }

    /** Empties and deletes every bucket space of a Disabled agentic bucket. Best-effort. */
    private static void reapBucketSpaces(OSSAgenticBucketClient client, String bucket) {
        try {
            ListBucketSpacesIterable iterable = client.listBucketSpacesPaginator(
                    ListBucketSpacesRequest.newBuilder().bucket(bucket).build());
            for (ListBucketSpacesResult result : iterable) {
                if (result.bucketSpaces() == null) {
                    continue;
                }
                for (BucketSpaceSummary space : result.bucketSpaces()) {
                    if (space.name() == null) {
                        continue;
                    }
                    // A non-empty bucket space cannot be deleted, and an agentic bucket that still
                    // owns a bucket space cannot be deleted either.
                    cleanBucketSpaceObjects(space.name());
                    deleteBucketSpaceQuietly(space.name());
                }
            }
        } catch (Exception ignore) {
        }
    }

    /** Empties a bucket space, a non-empty one cannot be deleted. Best-effort. */
    public static void cleanBucketSpaceObjects(String spaceFullName) {
        try {
            OSSClient client = getDefaultClient();
            ListObjectsV2Iterable iterable = client.listObjectsV2Paginator(
                    ListObjectsV2Request.newBuilder().bucket(spaceFullName).build());
            for (ListObjectsV2Result result : iterable) {
                if (result.contents() == null) {
                    continue;
                }
                for (ObjectSummary object : result.contents()) {
                    try {
                        client.deleteObject(DeleteObjectRequest.newBuilder()
                                .bucket(spaceFullName)
                                .key(object.key())
                                .build());
                    } catch (Exception ignore) {
                    }
                }
            }
        } catch (Exception ignore) {
        }
    }

    /** Deletes a bucket space by its full name. Best-effort. */
    public static void deleteBucketSpaceQuietly(String spaceFullName) {
        try {
            getDefaultClient().deleteBucket(DeleteBucketRequest.newBuilder()
                    .bucket(spaceFullName).build());
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

    /**
     * Checks if the exception chain contains a SecondLevelDomainForbidden error,
     * which indicates the endpoint does not support path-style addressing.
     */
    protected static boolean isSecondLevelDomainForbidden(Throwable throwable) {
        ServiceException serr = findCause(throwable, ServiceException.class);
        return serr != null && "SecondLevelDomainForbidden".equals(serr.errorCode());
    }
}
