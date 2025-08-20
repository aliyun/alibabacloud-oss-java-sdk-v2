package com.aliyun.sdk.service.oss2;

import com.aliyun.sdk.service.oss2.credentials.CredentialsProvider;
import com.aliyun.sdk.service.oss2.credentials.StaticCredentialsProvider;
import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.paginator.ListBucketsIterable;
import com.aliyun.sdk.service.oss2.paginator.ListObjectVersionsIterable;
import com.aliyun.sdk.service.oss2.paginator.ListObjectsV2Iterable;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import java.io.*;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class TestBase {

    protected static final String BUCKET_NAME_PREFIX = "java-sdk-test-bucket-";
    protected static final String OJBJECT_NAME_PREFIX = "java-sdk-test-object-";
    protected static final long DELETE_OBJECTS_ONETIME_LIMIT = 1000;
    // OSS test configuration
    public static String OSS_TEST_REGION = null;
    public static String OSS_TEST_ENDPOINT = null;
    public static String OSS_TEST_ACCESS_KEY_ID = null;
    public static String OSS_TEST_ACCESS_KEY_SECRET = null;
    public static String OSS_TEST_RAM_ROLE_ARN = null;
    public static String OSS_TEST_RAM_UID = null;
    // payer
    public static String OSS_TEST_PAYER_ACCESS_KEY_ID = null;
    public static String OSS_TEST_PAYER_ACCESS_KEY_SECRET = null;
    public static String OSS_TEST_PAYER_UID = null;
    // path style
    public static String OSS_TEST_PATHSTYLE_BUCKET = null;
    public static String OSS_TEST_PATHSTYLE_REGION = null;
    protected static OSSClient instanceSync_;

    protected static OSSAsyncClient instanceAsync_;

    //protected static Client instance_;


    protected static String bucketNamePrefix;

    protected static String bucketName;

    public static String region() {
        return Optional.ofNullable(OSS_TEST_REGION).orElse(System.getenv().get("OSS_TEST_REGION"));
    }

    public static String endpoint() {
        return Optional.ofNullable(OSS_TEST_ENDPOINT).orElse(System.getenv().get("OSS_TEST_ENDPOINT"));
    }

    public static String accessKeyId() {
        return Optional.ofNullable(OSS_TEST_ACCESS_KEY_ID).orElse(System.getenv().get("OSS_TEST_ACCESS_KEY_ID"));
    }

    public static String accessKeySecret() {
        return Optional.ofNullable(OSS_TEST_ACCESS_KEY_SECRET).orElse(System.getenv().get("OSS_TEST_ACCESS_KEY_SECRET"));
    }

    public static String ramRoleArn() {
        return Optional.ofNullable(OSS_TEST_RAM_ROLE_ARN).orElse(System.getenv().get("OSS_TEST_RAM_ROLE_ARN"));
    }

    public static String accountId() {
        return Optional.ofNullable(OSS_TEST_RAM_UID).orElse(System.getenv().get("OSS_TEST_RAM_UID"));
    }

    @BeforeClass
    public static void oneTimeSetUp() {
        bucketNamePrefix = genBucketNamePrefix();
        bucketName = genBucketName();
        createBucket(bucketName);
    }

    @AfterClass
    public static void oneTimeSetDown() {
        cleanBuckets(bucketNamePrefix);
    }

    public static String genBucketNamePrefix() {
        long ticks = Instant.now().getEpochSecond();
        long val = new Random().nextInt(500);
        return BUCKET_NAME_PREFIX + ticks + "-" + val;
    }

    public static void waitForCacheExpiration(int durationSeconds) {
        try {
            Thread.sleep((long) durationSeconds * 1000);
        } catch (Exception ignore) {
        }
    }

    public static void cleanBuckets(String prefix) {
        ListBucketsIterable iterable = new ListBucketsIterable(
                getDefaultClient(),
                ListBucketsRequest.newBuilder()
                        .prefix(prefix)
                        .build());

        iterable.forEach(result -> {
            for (BucketSummary bucket : result.buckets()) {
                cleanBucket(bucket.name(), bucket.location());
            }
        });
    }

    public static String genBucketName() {
        long val = new Random().nextInt(5000);
        return bucketNamePrefix + "-" + val;
    }

    public static void createBucket(String bucket) {
        getDefaultClient().putBucket(PutBucketRequest.newBuilder()
                .bucket(bucket)
                .createBucketConfiguration(CreateBucketConfiguration.newBuilder()
                        .storageClass("Standard")
                        .build())
                .build());
        waitForCacheExpiration(1);
    }

    public static void cleanBucket(String bucket, String region) {
        OSSClient client = getDefaultClient();
        cleanObjects(client, bucket);
        cleanParts(client, bucket);
        client.deleteBucket(DeleteBucketRequest.newBuilder().bucket(bucket).build());
    }

    private static void cleanObjects(OSSClient client, String bucketName) {
        ListObjectVersionsIterable iterable = client.listObjectVersionsPaginator(
                ListObjectVersionsRequest.newBuilder()
                        .bucket(bucketName)
                        .build());
        for (ListObjectVersionsResult result : iterable) {
            //TODO use delete objects
            List<DeleteObject> delObjects = new ArrayList<>();
            for (ObjectVersion object : result.versions()) {
                delObjects.add(DeleteObject.newBuilder()
                        .key(object.key())
                        .versionId(object.versionId())
                        .build());
            }

            for (DeleteMarkerEntry entry : result.deleteMarkers()) {
                delObjects.add(DeleteObject.newBuilder()
                        .key(entry.key())
                        .versionId(entry.versionId())
                        .build());
            }

            if (!delObjects.isEmpty()) {
                client.deleteMultipleObjects(DeleteMultipleObjectsRequest.newBuilder()
                        .bucket(bucketName)
                        .deleteObjects(delObjects)
                        .build());
            }
        }
    }

    private static void cleanParts(OSSClient client, String bucketName) {

        ListMultipartUploadsResult result = client.listMultipartUploads(ListMultipartUploadsRequest.newBuilder()
                .bucket(bucketName)
                .maxUploads(1000L)
                .build());
        for (Upload upload: result.uploads()) {
            client.abortMultipartUpload(AbortMultipartUploadRequest.newBuilder()
                    .bucket(bucketName)
                    .key(upload.key())
                    .uploadId(upload.uploadId())
                    .build());
        }
    }

    public static OSSClient getDefaultClient() {
        if (instanceSync_ == null) {

            CredentialsProvider provider = new StaticCredentialsProvider(accessKeyId(), accessKeySecret());
            instanceSync_ = OSSClient.newBuilder()
                    .region(region())
                    .endpoint(endpoint())
                    .useApacheHttpClient4(false)
                    .credentialsProvider(provider)
                    .build();
        }
        return instanceSync_;
    }

    public static OSSAsyncClient getDefaultAsyncClient() {
        if (instanceAsync_ == null) {

            CredentialsProvider provider = new StaticCredentialsProvider(accessKeyId(), accessKeySecret());
            instanceAsync_ = OSSAsyncClient.newBuilder()
                    .region(region())
                    .endpoint(endpoint())
                    .credentialsProvider(provider)
                    .build();
        }
        return instanceAsync_;
    }

    public static OSSClient getOssClient() {
        CredentialsProvider provider = new StaticCredentialsProvider(accessKeyId(), accessKeySecret());
        return OSSClient.newBuilder()
                .region(region())
                .endpoint(endpoint())
                .credentialsProvider(provider)
                .build();
    }

    public static boolean compareFile(String fileNameLeft, String fileNameRight) throws IOException {

        try (FileInputStream fisLeft = new FileInputStream(fileNameLeft); FileInputStream fisRight = new FileInputStream(fileNameRight)) {

            int len1 = fisLeft.available();
            int len2 = fisRight.available();

            if (len1 == len2) {
                byte[] data1 = new byte[len1];
                byte[] data2 = new byte[len2];

                int got1 = fisLeft.read(data1);
                int got2 = fisRight.read(data2);

                if (got1 != got2) {
                    return false;
                }

                for (int i = 0; i < len1; i++) {
                    if (data1[i] != data2[i]) {
                        return false;
                    }
                }

                return true;
            } else {
                return false;
            }
        }
    }

    public static boolean compareFileWithRange(String fileNameLeft, long start, long end, String fileNameRight) throws IOException {
        long comlen = end - start + 1;

        try (FileInputStream fisLeft = new FileInputStream(fileNameLeft); FileInputStream fisRight = new FileInputStream(fileNameRight)) {

            int len1 = fisLeft.available();
            int len2 = fisRight.available();

            if (len1 < comlen) {
                return false;
            }

            long n = fisLeft.skip(start);

            if (n != start) {
                return false;
            }

            if (comlen == len2) {
                byte[] data1 = new byte[len2];
                byte[] data2 = new byte[len2];

                int got1 = fisLeft.read(data1);
                int got2 = fisRight.read(data2);

                if (got1 != got2) {
                    return false;
                }

                for (int i = 0; i < len2; i++) {
                    if (data1[i] != data2[i]) {
                        return false;
                    }
                }

                return true;
            } else {
                return false;
            }
        }
    }

    public static File createSampleFile(String fileName, long size) throws IOException {
        File file = File.createTempFile(fileName, "");
        file.deleteOnExit();
        String context = "abcdefghijklmnopqrstuvwxyz0123456789011234567890\n";

        Writer writer = new OutputStreamWriter(new FileOutputStream(file));
        for (int i = 0; i < size / context.length(); i++) {
            writer.write(context);
        }
        writer.close();

        return file;
    }

    public static String calculateMD5(InputStream in) {
        try (DigestInputStream ds = new DigestInputStream(in, MessageDigest.getInstance("MD5"))) {
            while (ds.read() != -1) {
            }
            byte[] md5bytes = ds.getMessageDigest().digest();

            StringBuilder sb = new StringBuilder();
            for (byte md5byte : md5bytes) {
                String hexBiChars = String.format("%02x", md5byte);
                sb.append(hexBiChars);
            }
            return sb.toString();
        } catch (Exception e) {
            return null;
        }
    }

    public static String calculateMD5(File file) {
        try {
            return calculateMD5(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            return null;
        }
    }
}
