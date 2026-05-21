package com.aliyun.sdk.service.oss2.vectors;

import com.aliyun.sdk.service.oss2.credentials.StaticCredentialsProvider;
import com.aliyun.sdk.service.oss2.exceptions.ServiceException;
import com.aliyun.sdk.service.oss2.vectors.models.*;
import com.aliyun.sdk.service.oss2.vectors.paginator.ListVectorBucketsIterable;
import com.aliyun.sdk.service.oss2.vectors.paginator.ListVectorIndexesIterable;
import com.aliyun.sdk.service.oss2.vectors.paginator.ListVectorsIterable;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.aliyun.sdk.service.oss2.internal.ClientImplMockTest.findCause;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class ClientVectorPaginatorTest extends TestBaseVectors {

    private static final String TEST_INDEX_DATA_TYPE = "float32";
    private static final int TEST_INDEX_DIMENSION = 4;
    private static final String TEST_INDEX_DISTANCE_METRIC = "euclidean";

    @Test
    public void iterateVectorBuckets_ListVectorBuckets() {
        OSSVectorsClient client = getVectorsClient();

        // Create test vector buckets
        String bucketPrefix = "test-vec-pg-" + System.currentTimeMillis();
        String bucket1 = bucketPrefix + "-1";
        String bucket2 = bucketPrefix + "-2";
        String bucket3 = bucketPrefix + "-3";

        List<String> bucketNames = Arrays.asList(bucket1, bucket2, bucket3);

        for (String bucketName : bucketNames) {
            client.putVectorBucket(PutVectorBucketRequest.newBuilder()
                    .bucket(bucketName)
                    .build());
        }

        try {
            waitForCacheExpiration(3);

            // Test pagination with maxKeys = 1
            ListVectorBucketsIterable paginator = new ListVectorBucketsIterable(
                    client,
                    ListVectorBucketsRequest.newBuilder()
                            .prefix(bucketPrefix)
                            .maxKeys(1L)
                            .build());

            List<String> bucketNamesResult = new ArrayList<>();
            for (ListVectorBucketsResult result : paginator) {
                assertThat(result.buckets()).hasSizeLessThanOrEqualTo(1);
                bucketNamesResult.addAll(result.buckets().stream()
                        .map(b -> getBucketNameWithoutPrefix(b.name()))
                        .collect(Collectors.toList()));
            }

            assertThat(bucketNamesResult).containsAll(bucketNames);

            // Reuse paginator
            List<String> bucketNamesResultAgain = new ArrayList<>();
            for (ListVectorBucketsResult result : paginator) {
                bucketNamesResultAgain.addAll(result.buckets().stream()
                        .map(b -> getBucketNameWithoutPrefix(b.name()))
                        .collect(Collectors.toList()));
            }
            assertThat(bucketNamesResultAgain).containsAll(bucketNames);

            // Test with prefix filtering for a single bucket
            paginator = new ListVectorBucketsIterable(
                    client,
                    ListVectorBucketsRequest.newBuilder()
                            .prefix(bucket1)
                            .build());

            bucketNamesResult = new ArrayList<>();
            for (ListVectorBucketsResult result : paginator) {
                bucketNamesResult.addAll(result.buckets().stream()
                        .map(b -> getBucketNameWithoutPrefix(b.name()))
                        .collect(Collectors.toList()));
            }

            assertThat(bucketNamesResult).contains(bucket1);
            assertThat(bucketNamesResult).doesNotContain(bucket2, bucket3);

            // Test with non-existent prefix
            paginator = new ListVectorBucketsIterable(
                    client,
                    ListVectorBucketsRequest.newBuilder()
                            .prefix("non-existent-vec-prefix-" + System.currentTimeMillis())
                            .build());

            bucketNamesResult = new ArrayList<>();
            for (ListVectorBucketsResult result : paginator) {
                bucketNamesResult.addAll(result.buckets().stream()
                        .map(b -> getBucketNameWithoutPrefix(b.name()))
                        .collect(Collectors.toList()));
            }
            assertThat(bucketNamesResult).isEmpty();

            // Use client's paginator api
            paginator = client.listVectorBucketsPaginator(
                    ListVectorBucketsRequest.newBuilder()
                            .prefix(bucketPrefix)
                            .build());

            bucketNamesResult = new ArrayList<>();
            for (ListVectorBucketsResult result : paginator) {
                bucketNamesResult.addAll(result.buckets().stream()
                        .map(b -> getBucketNameWithoutPrefix(b.name()))
                        .collect(Collectors.toList()));
            }
            assertThat(bucketNamesResult).containsAll(bucketNames);

        } finally {
            // Cleanup
            for (String bucketName : bucketNames) {
                try {
                    client.deleteVectorBucket(DeleteVectorBucketRequest.newBuilder()
                            .bucket(bucketName)
                            .build());
                } catch (Exception ignored) {
                }
            }
        }
    }

    @Test
    public void iterateVectorBucketsFail_ListVectorBuckets() {
        try (OSSVectorsClient client = OSSVectorsClient.newBuilder()
                .region(region())
                .endpoint(vectorEndpoint())
                .accountId(accountId())
                .credentialsProvider(new StaticCredentialsProvider("ak", "sk"))
                .build()) {

            ListVectorBucketsIterable paginator = new ListVectorBucketsIterable(
                    client,
                    ListVectorBucketsRequest.newBuilder()
                            .build());
            for (ListVectorBucketsResult ignore : paginator) {
                fail("should not here");
            }

        } catch (Exception e) {
            ServiceException serr = findCause(e, ServiceException.class);
            assertThat(serr).isNotNull();
            assertThat(serr.errorCode()).isEqualTo("InvalidAccessKeyId");
        }
    }

    @Test
    public void iterateVectorIndexes_ListVectorIndexes() {
        OSSVectorsClient client = getVectorsClient();

        String bucketName = genVectorBucketName();
        client.putVectorBucket(PutVectorBucketRequest.newBuilder()
                .bucket(bucketName)
                .build());

        List<String> indexNames = Arrays.asList(
                "idxPg1", "idxPg2", "idxPg3", "idxPg4", "idxPg5"
        );

        try {
            // Try to create multiple indexes; tolerate 409 (already exists) which
            // can happen due to internal retries or per-bucket index limits.
            for (String indexName : indexNames) {
                tryPutVectorIndex(client, bucketName, indexName);
            }


            // Use the actual listed indexes as the ground truth for pagination assertions
            ListVectorIndexesResult fullList = client.listVectorIndexes(
                    ListVectorIndexesRequest.newBuilder()
                            .bucket(bucketName)
                            .build());
            List<String> expectedNames = fullList.indexes().stream()
                    .map(IndexSummary::indexName)
                    .collect(Collectors.toList());
            assertThat(expectedNames).isNotEmpty();

            // Test pagination with maxResults = 1 to force pagination if multiple exist
            ListVectorIndexesIterable paginator = new ListVectorIndexesIterable(
                    client,
                    ListVectorIndexesRequest.newBuilder()
                            .bucket(bucketName)
                            .maxResults(1L)
                            .build());

            List<String> indexNamesResult = new ArrayList<>();
            for (ListVectorIndexesResult result : paginator) {
                assertThat(result.indexes()).hasSizeLessThanOrEqualTo(1);
                indexNamesResult.addAll(result.indexes().stream()
                        .map(IndexSummary::indexName)
                        .collect(Collectors.toList()));
            }
            assertThat(indexNamesResult).containsExactlyInAnyOrderElementsOf(expectedNames);

            // Reuse paginator
            List<String> indexNamesResultAgain = new ArrayList<>();
            for (ListVectorIndexesResult result : paginator) {
                indexNamesResultAgain.addAll(result.indexes().stream()
                        .map(IndexSummary::indexName)
                        .collect(Collectors.toList()));
            }
            assertThat(indexNamesResultAgain).containsExactlyInAnyOrderElementsOf(expectedNames);

            // Use client's paginator api
            paginator = client.listVectorIndexesPaginator(
                    ListVectorIndexesRequest.newBuilder()
                            .bucket(bucketName)
                            .build());

            indexNamesResult = new ArrayList<>();
            for (ListVectorIndexesResult result : paginator) {
                indexNamesResult.addAll(result.indexes().stream()
                        .map(IndexSummary::indexName)
                        .collect(Collectors.toList()));
            }
            assertThat(indexNamesResult).containsExactlyInAnyOrderElementsOf(expectedNames);

        } finally {
            // Cleanup all actually existing indexes
            try {
                ListVectorIndexesResult listResult = client.listVectorIndexes(
                        ListVectorIndexesRequest.newBuilder()
                                .bucket(bucketName)
                                .build());
                for (IndexSummary idx : listResult.indexes()) {
                    try {
                        client.deleteVectorIndex(DeleteVectorIndexRequest.newBuilder()
                                .bucket(bucketName)
                                .indexName(idx.indexName())
                                .build());
                    } catch (Exception ignored) {
                    }
                }
            } catch (Exception ignored) {
            }
            // Cleanup bucket
            try {
                client.deleteVectorBucket(DeleteVectorBucketRequest.newBuilder()
                        .bucket(bucketName)
                        .build());
            } catch (Exception ignored) {
            }
        }
    }

    private void tryPutVectorIndex(OSSVectorsClient client, String bucketName, String indexName) {
        try {
            client.putVectorIndex(PutVectorIndexRequest.newBuilder()
                    .bucket(bucketName)
                    .indexName(indexName)
                    .dataType(TEST_INDEX_DATA_TYPE)
                    .dimension(TEST_INDEX_DIMENSION)
                    .distanceMetric(TEST_INDEX_DISTANCE_METRIC)
                    .build());
        } catch (RuntimeException e) {
            ServiceException se = findCause(e, ServiceException.class);
            if (se == null || se.statusCode() != 409) {
                throw e;
            }
            // Index already exists - treat as idempotent success
        }
    }

    @Test
    public void iterateVectorIndexesFail_ListVectorIndexes() {
        OSSVectorsClient client = getVectorsClient();

        String bucket = genVectorBucketName() + "-non-exist";

        ListVectorIndexesIterable paginator = new ListVectorIndexesIterable(
                client,
                ListVectorIndexesRequest.newBuilder()
                        .bucket(bucket)
                        .build());

        try {
            for (ListVectorIndexesResult ignore : paginator) {
                fail("should not here");
            }
        } catch (Exception e) {
            ServiceException serr = findCause(e, ServiceException.class);
            assertThat(serr).isNotNull();
            assertThat(serr.statusCode()).isEqualTo(400);
        }
    }

    @Test
    public void iterateVectors_ListVectors() {
        OSSVectorsClient client = getVectorsClient();

        String bucketName = genVectorBucketName();
        String indexName = "vectorsPgIndex";

        client.putVectorBucket(PutVectorBucketRequest.newBuilder()
                .bucket(bucketName)
                .build());

        try {
            tryPutVectorIndex(client, bucketName, indexName);

            // Insert multiple vectors
            List<String> vectorKeys = Arrays.asList(
                    "vec-key-1", "vec-key-2", "vec-key-3",
                    "vec-key-4", "vec-key-5"
            );
            List<Map<String, Object>> vectorsToInsert = new ArrayList<>();
            for (int i = 0; i < vectorKeys.size(); i++) {
                Map<String, Object> data = new HashMap<>();
                data.put("float32", Arrays.asList(
                        0.1f * (i + 1), 0.2f * (i + 1), 0.3f * (i + 1), 0.4f * (i + 1)));

                Map<String, Object> metadata = new HashMap<>();
                metadata.put("idx", String.valueOf(i));

                Map<String, Object> vector = new HashMap<>();
                vector.put("data", data);
                vector.put("key", vectorKeys.get(i));
                vector.put("metadata", metadata);
                vectorsToInsert.add(vector);
            }

            client.putVectors(PutVectorsRequest.newBuilder()
                    .bucket(bucketName)
                    .indexName(indexName)
                    .vectors(vectorsToInsert)
                    .build());


            // Test pagination with maxResults = 2
            ListVectorsIterable paginator = new ListVectorsIterable(
                    client,
                    ListVectorsRequest.newBuilder()
                            .bucket(bucketName)
                            .indexName(indexName)
                            .maxResults(2)
                            .build());

            List<String> keysResult = new ArrayList<>();
            for (ListVectorsResult result : paginator) {
                assertThat(result.vectors()).hasSizeLessThanOrEqualTo(2);
                keysResult.addAll(result.vectors().stream()
                        .map(VectorsSummary::key)
                        .collect(Collectors.toList()));
            }
            assertThat(keysResult).containsExactlyInAnyOrderElementsOf(vectorKeys);

            // Reuse paginator
            List<String> keysResultAgain = new ArrayList<>();
            for (ListVectorsResult result : paginator) {
                keysResultAgain.addAll(result.vectors().stream()
                        .map(VectorsSummary::key)
                        .collect(Collectors.toList()));
            }
            assertThat(keysResultAgain).containsExactlyInAnyOrderElementsOf(vectorKeys);

            // Use client's paginator api
            paginator = client.listVectorsPaginator(
                    ListVectorsRequest.newBuilder()
                            .bucket(bucketName)
                            .indexName(indexName)
                            .build());

            keysResult = new ArrayList<>();
            for (ListVectorsResult result : paginator) {
                keysResult.addAll(result.vectors().stream()
                        .map(VectorsSummary::key)
                        .collect(Collectors.toList()));
            }
            assertThat(keysResult).containsExactlyInAnyOrderElementsOf(vectorKeys);

        } finally {
            // Cleanup vectors
            try {
                client.deleteVectors(DeleteVectorsRequest.newBuilder()
                        .bucket(bucketName)
                        .indexName(indexName)
                        .keys(Arrays.asList(
                                "vec-key-1", "vec-key-2", "vec-key-3",
                                "vec-key-4", "vec-key-5"))
                        .build());
            } catch (Exception ignored) {
            }
            // Cleanup index
            try {
                client.deleteVectorIndex(DeleteVectorIndexRequest.newBuilder()
                        .bucket(bucketName)
                        .indexName(indexName)
                        .build());
            } catch (Exception ignored) {
            }
            // Cleanup bucket
            try {
                client.deleteVectorBucket(DeleteVectorBucketRequest.newBuilder()
                        .bucket(bucketName)
                        .build());
            } catch (Exception ignored) {
            }
        }
    }

    @Test
    public void iterateVectorsFail_ListVectors() {
        OSSVectorsClient client = getVectorsClient();

        // Create a real bucket first to focus the failure on the missing index
        String bucketName = genVectorBucketName();
        client.putVectorBucket(PutVectorBucketRequest.newBuilder()
                .bucket(bucketName)
                .build());

        try {
            ListVectorsIterable paginator = new ListVectorsIterable(
                    client,
                    ListVectorsRequest.newBuilder()
                            .bucket(bucketName)
                            .indexName("nonExistIndex")
                            .build());

            try {
                for (ListVectorsResult ignore : paginator) {
                    fail("should not here");
                }
            } catch (Exception e) {
                ServiceException serr = findCause(e, ServiceException.class);
                assertThat(serr).isNotNull();
                assertThat(serr.statusCode()).isEqualTo(404);
            }
        } finally {
            try {
                client.deleteVectorBucket(DeleteVectorBucketRequest.newBuilder()
                        .bucket(bucketName)
                        .build());
            } catch (Exception ignored) {
            }
        }
    }
}
