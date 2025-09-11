package com.aliyun.sdk.service.oss2.vectors;

import com.aliyun.sdk.service.oss2.TestBase;
import com.aliyun.sdk.service.oss2.exceptions.ServiceException;
import com.aliyun.sdk.service.oss2.vectors.models.*;
import org.junit.Assert;
import org.junit.Test;
import java.util.UUID;
import static org.assertj.core.api.Assertions.assertThat;

public class ClientVectorBucketsTest extends TestBase {

    private static final String VECTOR_BUCKET_NAME_PREFIX = "java-sdk-test-vector-bucket-";

    private String genVectorBucketName() {
        return VECTOR_BUCKET_NAME_PREFIX + UUID.randomUUID().toString().substring(0, 8);
    }

    @Test
    public void testVectorBucketOperations() {
        OSSVectorsClient vectorsClient = getVectorsClient();
        String bucketName = genVectorBucketName();

        // Put vector bucket
        PutVectorBucketResult putResult = vectorsClient.putVectorBucket(PutVectorBucketRequest.newBuilder()
                .bucket(bucketName)
                .build());
        Assert.assertNotNull(putResult);
        Assert.assertEquals(200, putResult.statusCode());

        try {

            // Get vector bucket
            GetVectorBucketResult getResult = vectorsClient.getVectorBucket(GetVectorBucketRequest.newBuilder()
                    .bucket(bucketName)
                    .build());
            Assert.assertNotNull(getResult);
            Assert.assertEquals(200, getResult.statusCode());
            Assert.assertEquals(bucketName, getResult.bucketInfo().getBucketInfo().name());

            // List vector buckets
            ListVectorBucketsResult listResult = vectorsClient.listVectorBuckets(ListVectorBucketsRequest.newBuilder()
                    .prefix(bucketName)
                    .build());
            Assert.assertNotNull(listResult);
            Assert.assertEquals(200, listResult.statusCode());
            assertThat(listResult.buckets()).isNotNull();
            boolean found = listResult.buckets().stream()
                    .anyMatch(bucket -> bucketName.equals(bucket.name()));
            Assert.assertTrue("Created bucket should be in the list", found);

            // Delete vector bucket
            DeleteVectorBucketResult deleteResult = vectorsClient.deleteVectorBucket(DeleteVectorBucketRequest.newBuilder()
                    .bucket(bucketName)
                    .build());
            Assert.assertNotNull(deleteResult);
            Assert.assertEquals(204, deleteResult.statusCode());
        } finally {
            // Ensure cleanup
            try {
                vectorsClient.deleteVectorBucket(DeleteVectorBucketRequest.newBuilder()
                        .bucket(bucketName)
                        .build());
            } catch (Exception e) {
                // Ignore cleanup errors
            }
        }
    }

    @Test
    public void testListVectorBucketsWithPagination() {
        OSSVectorsClient vectorsClient = getVectorsClient();
        String bucketPrefix = VECTOR_BUCKET_NAME_PREFIX + "pagination-";
        String bucketName1 = bucketPrefix + "1";
        String bucketName2 = bucketPrefix + "2";

        // Create two vector buckets
        vectorsClient.putVectorBucket(PutVectorBucketRequest.newBuilder()
                .bucket(bucketName1)
                .build());

        vectorsClient.putVectorBucket(PutVectorBucketRequest.newBuilder()
                .bucket(bucketName2)
                .build());

        try {
            waitForCacheExpiration(3);

            // List vector buckets with maxKeys = 1
            ListVectorBucketsResult listResult = vectorsClient.listVectorBuckets(ListVectorBucketsRequest.newBuilder()
                    .prefix(bucketPrefix)
                    .maxKeys(1)
                    .build());

            Assert.assertNotNull(listResult);
            Assert.assertEquals(200, listResult.statusCode());
            assertThat(listResult.buckets()).isNotNull();
            Assert.assertEquals(1, listResult.buckets().size());
            Assert.assertTrue(listResult.isTruncated());

            // List next page using marker
            ListVectorBucketsResult nextListResult = vectorsClient.listVectorBuckets(ListVectorBucketsRequest.newBuilder()
                    .prefix(bucketPrefix)
                    .marker(listResult.nextMarker())
                    .maxKeys(1)
                    .build());

            Assert.assertNotNull(nextListResult);
            Assert.assertEquals(200, nextListResult.statusCode());
            assertThat(nextListResult.buckets()).isNotNull();
            Assert.assertEquals(1, nextListResult.buckets().size());
        } finally {
            // Cleanup
            try {
                vectorsClient.deleteVectorBucket(DeleteVectorBucketRequest.newBuilder()
                        .bucket(bucketName1)
                        .build());
            } catch (Exception e) {
                // Ignore
            }

            try {
                vectorsClient.deleteVectorBucket(DeleteVectorBucketRequest.newBuilder()
                        .bucket(bucketName2)
                        .build());
            } catch (Exception e) {
                // Ignore
            }
        }
    }

    @Test
    public void testVectorBucketNotFound() {
        OSSVectorsClient vectorsClient = getVectorsClient();
        String nonExistentBucket = "non-existent-bucket-" + UUID.randomUUID().toString();

        try {
            vectorsClient.getVectorBucket(GetVectorBucketRequest.newBuilder()
                    .bucket(nonExistentBucket)
                    .build());
            Assert.fail("Expected ServiceException for non-existent bucket");
        } catch (Exception e) {
            ServiceException serviceException = findCause(e, ServiceException.class);
            Assert.assertNotNull(serviceException);
            Assert.assertEquals(404, serviceException.statusCode());
        }
    }

    private static <T extends Throwable> T findCause(Throwable throwable, Class<T> type) {
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

