package com.aliyun.sdk.service.oss2;

import com.aliyun.sdk.service.oss2.credentials.StaticCredentialsProvider;
import com.aliyun.sdk.service.oss2.exceptions.ServiceException;
import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.paginator.*;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.aliyun.sdk.service.oss2.internal.ClientImplMockTest.findCause;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class ClientPaginatorTest  extends TestBase {

    @Test
    public void iterateObjects_ListObjectsV2() {
        OSSClient client = getDefaultClient();

        String bucket = genBucketName();

        client.putBucket(PutBucketRequest.newBuilder()
                .bucket(bucket)
                .build());

        List<String> keys = Arrays.asList(
                "1/1.txt", "1/2.txt", "1/3.txt", "1/4.txt",
                "2/2-1.txt", "2/2-2.txt",
                "3/3-1.txt"
        );

        for (String key : keys) {
            client.putObject(PutObjectRequest.newBuilder()
                    .bucket(bucket)
                    .key(key)
                    .build());
        }

        ListObjectsV2Iterable paginator = new ListObjectsV2Iterable(
                client,
                ListObjectsV2Request.newBuilder()
                        .bucket(bucket)
                        .maxKeys(1L)
                        .build());

        List<String> keysResult = new ArrayList<>();
        for (ListObjectsV2Result result : paginator) {
            assertThat(result.contents()).hasSize(1);
            keysResult.addAll(result.contents().stream().map(ObjectSummary::key).collect(Collectors.toList()));
        }
        assertThat(keysResult).isEqualTo(keys);

        // reuse paginator
        List<String> keysResultAgain = new ArrayList<>();
        for (ListObjectsV2Result result : paginator) {
            assertThat(result.contents()).hasSize(1);
            keysResultAgain.addAll(result.contents().stream().map(ObjectSummary::key).collect(Collectors.toList()));
        }
        assertThat(keysResultAgain).isEqualTo(keys);

        // set prefix
        paginator = new ListObjectsV2Iterable(
                client,
                ListObjectsV2Request.newBuilder()
                        .bucket(bucket)
                        .prefix("2/")
                        .build());

        keysResult = new ArrayList<>();
        for (ListObjectsV2Result result : paginator) {
            assertThat(result.contents()).hasSize(2);
            keysResult.addAll(result.contents().stream().map(ObjectSummary::key).collect(Collectors.toList()));
        }
        assertThat(keysResult).hasSize(2);
        assertThat(keysResult.get(0)).isEqualTo("2/2-1.txt");
        assertThat(keysResult.get(1)).isEqualTo("2/2-2.txt");

        // list and returns empty
        paginator = new ListObjectsV2Iterable(
                client,
                ListObjectsV2Request.newBuilder()
                        .bucket(bucket)
                        .prefix("non-exist-prefix")
                        .build());

        keysResult = new ArrayList<>();
        for (ListObjectsV2Result result : paginator) {
            assertThat(result.contents()).hasSize(0);
        }

        // use client’s api
        paginator = client.listObjectsV2Paginator(
                ListObjectsV2Request.newBuilder()
                        .bucket(bucket)
                        .build());

        keysResult = new ArrayList<>();
        for (ListObjectsV2Result result : paginator) {
            keysResult.addAll(result.contents().stream().map(ObjectSummary::key).collect(Collectors.toList()));
        }
        assertThat(keysResult).isEqualTo(keys);
    }

    @Test
    public void iterateObjectsFail_ListObjectsV2() {
        OSSClient client = getDefaultClient();

        String bucket = genBucketName() + "non-exist";

        ListObjectsV2Iterable paginator = new ListObjectsV2Iterable(
                client,
                ListObjectsV2Request.newBuilder()
                        .bucket(bucket)
                        .build());

        try {
            for (ListObjectsV2Result ignore : paginator) {
                fail("should not here");
            }
        } catch (Exception e) {
            ServiceException serr = findCause(e, ServiceException.class);
            assertThat(serr).isNotNull();
            assertThat(serr.errorCode()).isEqualTo("NoSuchBucket");
        }
    }


    @Test
    public void iterateBuckets_ListBuckets() {
        OSSClient client = getDefaultClient();

        // Create test buckets
        String bucketPrefix = genBucketName() +  "-iter";
        String bucket1 = bucketPrefix + "-paginator-1";
        String bucket2 = bucketPrefix + "-paginator-2";
        String bucket3 = bucketPrefix + "-paginator-3";

        List<String> bucketNames = Arrays.asList(bucket1, bucket2, bucket3);

        // Create buckets
        for (String bucketName : bucketNames) {
            client.putBucket(PutBucketRequest.newBuilder()
                    .bucket(bucketName)
                    .build());
        }

        try {
            // Test pagination with maxKeys = 1
            ListBucketsIterable paginator = new ListBucketsIterable(
                    client,
                    ListBucketsRequest.newBuilder()
                            .prefix(bucketPrefix)
                            .maxKeys(1L)
                            .build());

            List<String> bucketNamesResult = new ArrayList<>();
            for (ListBucketsResult result : paginator) {
                // With maxKeys=1, each page should contain at most 1 bucket
                assertThat(result.buckets()).hasSizeLessThanOrEqualTo(1);
                bucketNamesResult.addAll(result.buckets().stream()
                        .map(BucketSummary::name)
                        .collect(Collectors.toList()));
            }

            // Verify that all our buckets are in the result
            assertThat(bucketNamesResult).containsAll(bucketNames);

            // Reuse paginator
            List<String> bucketNamesResultAgain = new ArrayList<>();
            for (ListBucketsResult result : paginator) {
                bucketNamesResultAgain.addAll(result.buckets().stream()
                        .map(BucketSummary::name)
                        .collect(Collectors.toList()));
            }
            assertThat(bucketNamesResultAgain).containsAll(bucketNames);

            // Test with prefix filtering
            paginator = new ListBucketsIterable(
                    client,
                    ListBucketsRequest.newBuilder()
                            .prefix(bucket1)
                            .build());

            bucketNamesResult = new ArrayList<>();
            for (ListBucketsResult result : paginator) {
                bucketNamesResult.addAll(result.buckets().stream()
                        .map(BucketSummary::name)
                        .collect(Collectors.toList()));
            }

            // Should contain only bucket1
            assertThat(bucketNamesResult).contains(bucket1);
            assertThat(bucketNamesResult).doesNotContain(bucket2, bucket3);

            // Test with non-existent prefix
            paginator = new ListBucketsIterable(
                    client,
                    ListBucketsRequest.newBuilder()
                            .prefix("non-existent-prefix-" + System.currentTimeMillis())
                            .build());

            bucketNamesResult = new ArrayList<>();
            for (ListBucketsResult result : paginator) {
                bucketNamesResult.addAll(result.buckets().stream()
                        .map(BucketSummary::name)
                        .collect(Collectors.toList()));
            }

            // Should be empty
            assertThat(bucketNamesResult).isEmpty();

            // use client’s api
            paginator = client.listBucketsPaginator(
                    ListBucketsRequest.newBuilder()
                            .prefix(bucketPrefix)
                            .build());

            bucketNamesResult = new ArrayList<>();
            for (ListBucketsResult result : paginator) {
                // With maxKeys=1, each page should contain at most 1 bucket
                bucketNamesResult.addAll(result.buckets().stream()
                        .map(BucketSummary::name)
                        .collect(Collectors.toList()));
            }
            assertThat(bucketNamesResult).containsAll(bucketNames);

        } finally {
            // Clean up test buckets
            for (String bucketName : bucketNames) {
                client.deleteBucket(DeleteBucketRequest.newBuilder()
                        .bucket(bucketName)
                        .build());
            }
        }
    }

    @Test
    public void iterateBucketsFail_ListBuckets() {
        try (OSSClient client = OSSClient.newBuilder()
                .region(region())
                .endpoint(endpoint())
                .credentialsProvider(new StaticCredentialsProvider("ak", "sk"))
                .build()) {

            ListBucketsIterable paginator = new ListBucketsIterable(
                    client,
                    ListBucketsRequest.newBuilder()
                            .build());
            for (ListBucketsResult ignore : paginator) {
                fail("should not here");
            }

        } catch (Exception e) {
            ServiceException serr = findCause(e, ServiceException.class);
            assertThat(serr).isNotNull();
            assertThat(serr.errorCode()).isEqualTo("InvalidAccessKeyId");
        }
    }

    @Test
    public void iterateObjects_ListObjects() {
        OSSClient client = getDefaultClient();

        String bucket = genBucketName();

        client.putBucket(PutBucketRequest.newBuilder()
                .bucket(bucket)
                .build());

        List<String> keys = Arrays.asList(
                "1/1.txt", "1/2.txt", "1/3.txt", "1/4.txt",
                "2/2-1.txt", "2/2-2.txt",
                "3/3-1.txt"
        );

        for (String key : keys) {
            client.putObject(PutObjectRequest.newBuilder()
                    .bucket(bucket)
                    .key(key)
                    .build());
        }

        ListObjectsIterable paginator = new ListObjectsIterable(
                client,
                ListObjectsRequest.newBuilder()
                        .bucket(bucket)
                        .maxKeys(1L)
                        .build());

        List<String> keysResult = new ArrayList<>();
        for (ListObjectsResult result : paginator) {
            assertThat(result.contents()).hasSize(1);
            keysResult.addAll(result.contents().stream().map(ObjectSummary::key).collect(Collectors.toList()));
        }
        assertThat(keysResult).isEqualTo(keys);

        // reuse paginator
        List<String> keysResultAgain = new ArrayList<>();
        for (ListObjectsResult result : paginator) {
            assertThat(result.contents()).hasSize(1);
            keysResultAgain.addAll(result.contents().stream().map(ObjectSummary::key).collect(Collectors.toList()));
        }
        assertThat(keysResultAgain).isEqualTo(keys);

        // set prefix
        paginator = new ListObjectsIterable(
                client,
                ListObjectsRequest.newBuilder()
                        .bucket(bucket)
                        .prefix("2/")
                        .build());

        keysResult = new ArrayList<>();
        for (ListObjectsResult result : paginator) {
            assertThat(result.contents()).hasSize(2);
            keysResult.addAll(result.contents().stream().map(ObjectSummary::key).collect(Collectors.toList()));
        }
        assertThat(keysResult).hasSize(2);
        assertThat(keysResult.get(0)).isEqualTo("2/2-1.txt");
        assertThat(keysResult.get(1)).isEqualTo("2/2-2.txt");

        // list and returns empty
        paginator = new ListObjectsIterable(
                client,
                ListObjectsRequest.newBuilder()
                        .bucket(bucket)
                        .prefix("non-exist-prefix")
                        .build());

        keysResult = new ArrayList<>();
        for (ListObjectsResult result : paginator) {
            assertThat(result.contents()).hasSize(0);
        }

        // use client's api
        paginator = client.listObjectsPaginator(
                ListObjectsRequest.newBuilder()
                        .bucket(bucket)
                        .build());

        keysResult = new ArrayList<>();
        for (ListObjectsResult result : paginator) {
            keysResult.addAll(result.contents().stream().map(ObjectSummary::key).collect(Collectors.toList()));
        }
        assertThat(keysResult).isEqualTo(keys);
    }

    @Test
    public void iterateObjectsFail_ListObjects() {
        OSSClient client = getDefaultClient();

        String bucket = genBucketName() + "non-exist";

        ListObjectsIterable paginator = new ListObjectsIterable(
                client,
                ListObjectsRequest.newBuilder()
                        .bucket(bucket)
                        .build());

        try {
            for (ListObjectsResult ignore : paginator) {
                fail("should not here");
            }
        } catch (Exception e) {
            ServiceException serr = findCause(e, ServiceException.class);
            assertThat(serr).isNotNull();
            assertThat(serr.errorCode()).isEqualTo("NoSuchBucket");
        }
    }


    @Test
    public void iterateObjects_ListMultipartUploads() {
        OSSClient client = getDefaultClient();

        String bucket = genBucketName();

        client.putBucket(PutBucketRequest.newBuilder()
                .bucket(bucket)
                .build());

        List<String> keys = Arrays.asList(
                "1/1.txt", "1/2.txt", "1/3.txt", "1/4.txt",
                "2/2-1.txt", "2/2-2.txt",
                "3/3-1.txt"
        );

        List<String> uploadIds = new ArrayList<>();

        // Initiate multipart uploads
        for (String key : keys) {
            InitiateMultipartUploadResult result = client.initiateMultipartUpload(
                    InitiateMultipartUploadRequest.newBuilder()
                            .bucket(bucket)
                            .key(key)
                            .build());
            uploadIds.add(result.initiateMultipartUpload().uploadId());
        }

        // Upload parts for each multipart upload
        List<String> eTags = new ArrayList<>();
        for (int i = 0; i < keys.size(); i++) {
            byte[] data = new byte[1024];
            new java.util.Random().nextBytes(data);

            UploadPartResult uploadPartResult = client.uploadPart(
                    UploadPartRequest.newBuilder()
                            .bucket(bucket)
                            .key(keys.get(i))
                            .uploadId(uploadIds.get(i))
                            .partNumber(1L)
                            .body(BinaryData.fromBytes(data))
                            .build());

            eTags.add(uploadPartResult.eTag());
        }

        ListMultipartUploadsIterable paginator = new ListMultipartUploadsIterable(
                client,
                ListMultipartUploadsRequest.newBuilder()
                        .bucket(bucket)
                        .maxUploads(1L)
                        .build());

        List<String> keysResult = new ArrayList<>();
        for (ListMultipartUploadsResult result : paginator) {
            assertThat(result.uploads()).hasSizeLessThanOrEqualTo(1);
            keysResult.addAll(result.uploads().stream().map(Upload::key).collect(Collectors.toList()));
        }
        assertThat(keysResult).containsExactlyInAnyOrderElementsOf(keys);

        // reuse paginator
        List<String> keysResultAgain = new ArrayList<>();
        for (ListMultipartUploadsResult result : paginator) {
            keysResultAgain.addAll(result.uploads().stream().map(Upload::key).collect(Collectors.toList()));
        }
        assertThat(keysResultAgain).containsExactlyInAnyOrderElementsOf(keys);

        // set prefix
        paginator = new ListMultipartUploadsIterable(
                client,
                ListMultipartUploadsRequest.newBuilder()
                        .bucket(bucket)
                        .prefix("2/")
                        .build());

        keysResult = new ArrayList<>();
        for (ListMultipartUploadsResult result : paginator) {
            keysResult.addAll(result.uploads().stream().map(Upload::key).collect(Collectors.toList()));
        }
        assertThat(keysResult).hasSize(2);
        assertThat(keysResult).contains("2/2-1.txt", "2/2-2.txt");

        // list and returns empty
        paginator = new ListMultipartUploadsIterable(
                client,
                ListMultipartUploadsRequest.newBuilder()
                        .bucket(bucket)
                        .prefix("non-exist-prefix")
                        .build());

        keysResult = new ArrayList<>();
        for (ListMultipartUploadsResult result : paginator) {
            keysResult.addAll(result.uploads().stream().map(Upload::key).collect(Collectors.toList()));
        }
        assertThat(keysResult).isEmpty();

        // use client's api
        paginator = client.listMultipartUploadsPaginator(
                ListMultipartUploadsRequest.newBuilder()
                        .bucket(bucket)
                        .build());

        keysResult = new ArrayList<>();
        for (ListMultipartUploadsResult result : paginator) {
            keysResult.addAll(result.uploads().stream().map(Upload::key).collect(Collectors.toList()));
        }
        assertThat(keysResult).containsExactlyInAnyOrderElementsOf(keys);

        // Complete multipart uploads to clean up
        for (int i = 0; i < keys.size(); i++) {
            List<Part> parts = new ArrayList<>();
            parts.add(Part.newBuilder()
                    .eTag(eTags.get(i))
                    .partNumber(1L)
                    .build());

            client.completeMultipartUpload(CompleteMultipartUploadRequest.newBuilder()
                    .bucket(bucket)
                    .key(keys.get(i))
                    .uploadId(uploadIds.get(i))
                    .completeMultipartUpload(CompleteMultipartUpload.newBuilder()
                            .parts(parts)
                            .build())
                    .build());
        }
    }

    @Test
    public void iterateObjectsFail_ListMultipartUploads() {
        OSSClient client = getDefaultClient();

        String bucket = genBucketName() + "non-exist";

        ListMultipartUploadsIterable paginator = new ListMultipartUploadsIterable(
                client,
                ListMultipartUploadsRequest.newBuilder()
                        .bucket(bucket)
                        .build());

        try {
            for (ListMultipartUploadsResult ignore : paginator) {
                fail("should not here");
            }
        } catch (Exception e) {
            ServiceException serr = findCause(e, ServiceException.class);
            assertThat(serr).isNotNull();
            assertThat(serr.errorCode()).isEqualTo("NoSuchBucket");
        }
    }


    @Test
    public void iterateObjects_ListParts() {
        OSSClient client = getDefaultClient();

        String bucket = genBucketName();
        String key = "test-key";
        int partCount = 5;

        client.putBucket(PutBucketRequest.newBuilder()
                .bucket(bucket)
                .build());

        // Initiate multipart upload
        InitiateMultipartUploadResult initiateResult = client.initiateMultipartUpload(
                InitiateMultipartUploadRequest.newBuilder()
                        .bucket(bucket)
                        .key(key)
                        .build());
        String uploadId = initiateResult.initiateMultipartUpload().uploadId();

        // Upload parts
        List<String> eTags = new ArrayList<>();
        for (int i = 1; i <= partCount; i++) {
            byte[] data = new byte[102400];
            new java.util.Random().nextBytes(data);

            UploadPartResult uploadPartResult = client.uploadPart(
                    UploadPartRequest.newBuilder()
                            .bucket(bucket)
                            .key(key)
                            .uploadId(uploadId)
                            .partNumber((long) i)
                            .body(BinaryData.fromBytes(data))
                            .build());

            eTags.add(uploadPartResult.eTag());
        }

        ListPartsIterable paginator = new ListPartsIterable(
                client,
                ListPartsRequest.newBuilder()
                        .bucket(bucket)
                        .key(key)
                        .uploadId(uploadId)
                        .maxParts(2L) // Set max parts to 2 to test pagination
                        .build());

        // Test pagination
        List<Long> partNumbersResult = new ArrayList<>();
        for (ListPartsResult result : paginator) {
            // Each page should have at most 2 parts
            assertThat(result.parts()).hasSizeLessThanOrEqualTo(2);
            partNumbersResult.addAll(result.parts().stream().map(Part::partNumber).collect(Collectors.toList()));
        }

        // Verify all parts are returned
        assertThat(partNumbersResult).hasSize(partCount);
        for (long i = 1; i <= partCount; i++) {
            assertThat(partNumbersResult).contains(i);
        }

        // Reuse paginator
        List<Long> partNumbersResultAgain = new ArrayList<>();
        for (ListPartsResult result : paginator) {
            partNumbersResultAgain.addAll(result.parts().stream().map(Part::partNumber).collect(Collectors.toList()));
        }
        assertThat(partNumbersResultAgain).hasSize(partCount);
        assertThat(partNumbersResultAgain).containsExactlyElementsOf(partNumbersResult);

        // List and returns empty with non-exist upload id
        ListPartsIterable emptyPaginator = new ListPartsIterable(
                client,
                ListPartsRequest.newBuilder()
                        .bucket(bucket)
                        .key(key)
                        .uploadId("non-exist-upload-id")
                        .build());

        boolean hasResults = false;
        try {
            for (ListPartsResult result : emptyPaginator) {
                hasResults = true;
                assertThat(result.parts()).hasSize(0);
            }
        } catch (Exception e) {
            ServiceException serr = findCause(e, ServiceException.class);
            assertThat(serr).isNotNull();
            assertThat(serr.errorCode()).isEqualTo("NoSuchUpload");
        }
        if (hasResults) {
            fail("Should have thrown NoSuchUpload exception");
        }

        // use client's api
        paginator = client.listPartsPaginator(
                ListPartsRequest.newBuilder()
                        .bucket(bucket)
                        .key(key)
                        .uploadId(uploadId)
                        .build());

        // Test pagination
       partNumbersResult = new ArrayList<>();
        for (ListPartsResult result : paginator) {
            partNumbersResult.addAll(result.parts().stream().map(Part::partNumber).collect(Collectors.toList()));
        }
        // Verify all parts are returned
        assertThat(partNumbersResult).hasSize(partCount);
        for (long i = 1; i <= partCount; i++) {
            assertThat(partNumbersResult).contains(i);
        }

        // Complete multipart upload to clean up
        List<Part> parts = new ArrayList<>();
        for (int i = 1; i <= partCount; i++) {
            parts.add(Part.newBuilder()
                    .eTag(eTags.get(i - 1))
                    .partNumber((long) i)
                    .build());
        }

        client.completeMultipartUpload(CompleteMultipartUploadRequest.newBuilder()
                .bucket(bucket)
                .key(key)
                .uploadId(uploadId)
                .completeMultipartUpload(CompleteMultipartUpload.newBuilder()
                        .parts(parts)
                        .build())
                .build());
    }

    @Test
    public void iterateObjectsFail_ListParts() {
        OSSClient client = getDefaultClient();


        String key = "test-key";
        String uploadId = "non-exist-upload-id";

        ListPartsIterable paginator = new ListPartsIterable(
                client,
                ListPartsRequest.newBuilder()
                        .bucket(bucketName)
                        .key(key)
                        .uploadId(uploadId)
                        .build());

        try {
            for (ListPartsResult ignore : paginator) {
                fail("should not here");
            }
        } catch (Exception e) {
            ServiceException serr = findCause(e, ServiceException.class);
            assertThat(serr).isNotNull();
            assertThat(serr.errorCode()).isEqualTo("NoSuchUpload");
        }
    }

    @Test
    public void iterateObjects_ListObjectVersions() {
        OSSClient client = getDefaultClient();

        String bucket = genBucketName();

        // Enable versioning for the bucket
        client.putBucket(PutBucketRequest.newBuilder()
                .bucket(bucket)
                .build());

        client.putBucketVersioning(PutBucketVersioningRequest.newBuilder()
                .bucket(bucket)
                .versioningConfiguration(VersioningConfiguration.newBuilder()
                        .status("Enabled")
                        .build())
                .build());

        List<String> keys = Arrays.asList(
                "1/1.txt", "1/2.txt", "1/3.txt", "1/4.txt",
                "2/2-1.txt", "2/2-2.txt",
                "3/3-1.txt"
        );

        // Put multiple versions of objects
        List<String> versionIds = new ArrayList<>();
        for (String key : keys) {
            PutObjectRequest request = PutObjectRequest.newBuilder()
                    .bucket(bucket)
                    .key(key)
                    .body(BinaryData.fromString(key + " content v1"))
                    .build();
            PutObjectResult result1 = client.putObject(request);
            versionIds.add(result1.versionId());

            // Put a second version
            PutObjectRequest request2 = PutObjectRequest.newBuilder()
                    .bucket(bucket)
                    .key(key)
                    .body(BinaryData.fromString(key + " content v2"))
                    .build();
            PutObjectResult result2 = client.putObject(request2);
            versionIds.add(result2.versionId());
        }

        ListObjectVersionsIterable paginator = new ListObjectVersionsIterable(
                client,
                ListObjectVersionsRequest.newBuilder()
                        .bucket(bucket)
                        .maxKeys(2L)
                        .build());

        List<String> keysResult = new ArrayList<>();
        List<String> versionIdsResult = new ArrayList<>();
        for (ListObjectVersionsResult result : paginator) {
            assertThat(result.versions()).hasSizeLessThanOrEqualTo(2);
            keysResult.addAll(result.versions().stream().map(ObjectVersion::key).collect(Collectors.toList()));
            versionIdsResult.addAll(result.versions().stream().map(ObjectVersion::versionId).collect(Collectors.toList()));
        }

        // We should have 2 versions for each key
        assertThat(keysResult).hasSize(keys.size() * 2);
        assertThat(versionIdsResult).hasSize(versionIds.size());

        // reuse paginator
        List<String> keysResultAgain = new ArrayList<>();
        List<String> versionIdsResultAgain = new ArrayList<>();
        for (ListObjectVersionsResult result : paginator) {
            keysResultAgain.addAll(result.versions().stream().map(ObjectVersion::key).collect(Collectors.toList()));
            versionIdsResultAgain.addAll(result.versions().stream().map(ObjectVersion::versionId).collect(Collectors.toList()));
        }
        assertThat(keysResultAgain).isEqualTo(keysResult);
        assertThat(versionIdsResultAgain).isEqualTo(versionIdsResult);

        // set prefix
        paginator = new ListObjectVersionsIterable(
                client,
                ListObjectVersionsRequest.newBuilder()
                        .bucket(bucket)
                        .prefix("2/")
                        .build());

        keysResult = new ArrayList<>();
        versionIdsResult = new ArrayList<>();
        for (ListObjectVersionsResult result : paginator) {
            keysResult.addAll(result.versions().stream().map(ObjectVersion::key).collect(Collectors.toList()));
            versionIdsResult.addAll(result.versions().stream().map(ObjectVersion::versionId).collect(Collectors.toList()));
        }

        assertThat(keysResult).hasSize(4); // 2 keys with 2 versions each
        assertThat(keysResult).allMatch(key -> key.startsWith("2/"));

        // list and returns empty
        paginator = new ListObjectVersionsIterable(
                client,
                ListObjectVersionsRequest.newBuilder()
                        .bucket(bucket)
                        .prefix("non-exist-prefix")
                        .build());

        keysResult = new ArrayList<>();
        versionIdsResult = new ArrayList<>();
        for (ListObjectVersionsResult result : paginator) {
            keysResult.addAll(result.versions().stream().map(ObjectVersion::key).collect(Collectors.toList()));
            versionIdsResult.addAll(result.versions().stream().map(ObjectVersion::versionId).collect(Collectors.toList()));
        }
        assertThat(keysResult).isEmpty();
        assertThat(versionIdsResult).isEmpty();

        // Clean up all object versions
        // First, list all versions to get all version IDs
        ListObjectVersionsResult allVersionsResult = client.listObjectVersions(
                ListObjectVersionsRequest.newBuilder()
                        .bucket(bucket)
                        .build());

        // Delete all versions explicitly
        for (ObjectVersion version : allVersionsResult.versions()) {
            client.deleteObject(DeleteObjectRequest.newBuilder()
                    .bucket(bucket)
                    .key(version.key())
                    .versionId(version.versionId())
                    .build());
        }

        // Delete all delete markers
        for (DeleteMarkerEntry deleteMarker : allVersionsResult.deleteMarkers()) {
            client.deleteObject(DeleteObjectRequest.newBuilder()
                    .bucket(bucket)
                    .key(deleteMarker.key())
                    .versionId(deleteMarker.versionId())
                    .build());
        }
    }

    @Test
    public void iterateObjectsFail_ListObjectVersions() {
        OSSClient client = getDefaultClient();

        String bucket = genBucketName() + "non-exist";

        ListObjectVersionsIterable paginator = new ListObjectVersionsIterable(
                client,
                ListObjectVersionsRequest.newBuilder()
                        .bucket(bucket)
                        .build());

        try {
            for (ListObjectVersionsResult ignore : paginator) {
                fail("should not here");
            }
        } catch (Exception e) {
            ServiceException serr = findCause(e, ServiceException.class);
            assertThat(serr).isNotNull();
            assertThat(serr.errorCode()).isEqualTo("NoSuchBucket");
        }
    }
}
