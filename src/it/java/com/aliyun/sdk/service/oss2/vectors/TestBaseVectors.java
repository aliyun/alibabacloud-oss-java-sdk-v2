package com.aliyun.sdk.service.oss2.vectors;

import com.aliyun.sdk.service.oss2.TestBase;
import com.aliyun.sdk.service.oss2.vectors.models.*;
import com.aliyun.sdk.service.oss2.vectors.paginator.ListVectorBucketsIterable;
import com.aliyun.sdk.service.oss2.vectors.paginator.ListVectorIndexesIterable;
import com.aliyun.sdk.service.oss2.vectors.paginator.ListVectorsIterable;
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

    public static void cleanVectorBuckets(String prefix) {
        ListVectorBucketsIterable iterable = new ListVectorBucketsIterable(
                getVectorsClient(),
                ListVectorBucketsRequest.newBuilder()
                        .prefix(prefix)
                        .build()
        );

        for (ListVectorBucketsResult page : iterable) {
            if (page.buckets() != null) {
                for (VectorBucketSummary bucket : page.buckets()) {
                    System.out.println("Deleting vector bucket: " + bucket.name());
                    String actualBucketName = getBucketNameWithoutPrefix(bucket.name());

                    // Clean all content in the bucket (indexes and vectors)
                    cleanVectorBucketContent(getVectorsClient(), actualBucketName);

                    // Delete the bucket itself
                    getVectorsClient().deleteVectorBucket(
                            DeleteVectorBucketRequest.newBuilder()
                                    .bucket(actualBucketName)
                                    .build()
                    );
                }
            }
        }
    }

    private static void cleanVectorBucketContent(OSSVectorsClient client, String fullBucketName) {
        /*
         * Clean all content in the specified vector bucket (including indexes and vectors)
         */
        // Clean all vector indexes
        cleanVectorIndexes(client, fullBucketName);
    }

    private static void cleanVectorIndexes(OSSVectorsClient client, String bucketName) {
        /*
         * Clean all vector indexes in the specified bucket and their contained vectors
         */
        ListVectorIndexesIterable indexIterable = new ListVectorIndexesIterable(
                client,
                ListVectorIndexesRequest.newBuilder()
                        .bucket(bucketName)
                        .build()
        );

        for (ListVectorIndexesResult pageIndex : indexIterable) {
            if (pageIndex.indexes() != null) {
                for (IndexSummary index : pageIndex.indexes()) {
                    System.out.println("Deleting vector index: " + index.indexName());
                    // Clean all vectors in the index
                    cleanVectors(client, bucketName, index.indexName());

                    // Delete the vector index
                    client.deleteVectorIndex(DeleteVectorIndexRequest.newBuilder()
                            .bucket(bucketName)
                            .indexName(index.indexName())
                            .build());
                }
            }
        }
    }

    private static void cleanVectors(OSSVectorsClient client, String bucketName, String indexName) {
        /*
         * Clean all vectors in the specified index
         */
        ListVectorsIterable vectorIterable = new ListVectorsIterable(
                client,
                ListVectorsRequest.newBuilder()
                        .bucket(bucketName)
                        .indexName(indexName)
                        .build()
        );

        for (ListVectorsResult pageVector : vectorIterable) {
            List<String> keys = new ArrayList<>();
            if (pageVector.vectors() != null) {
                for (VectorsSummary vec : pageVector.vectors()) {
                    keys.add(vec.key());
                }
            }

            System.out.println("Deleting vectors: " + keys);

            // Delete all vectors on the current page
            if (!keys.isEmpty()) {
                client.deleteVectors(DeleteVectorsRequest.newBuilder()
                        .bucket(bucketName)
                        .indexName(indexName)
                        .keys(keys)
                        .build());
            }
        }
    }

    protected static OSSVectorsClient getVectorClient() {
        return vectorClient;
    }
}