package com.aliyun.sdk.service.oss2.vectors;

import com.aliyun.sdk.service.oss2.exceptions.ServiceException;
import com.aliyun.sdk.service.oss2.vectors.models.*;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import java.util.UUID;
import static org.assertj.core.api.Assertions.assertThat;

public class ClientVectorBucketsTest extends TestBaseVectors {


    @Test
    public void testVectorBucketOperations() {
        OSSVectorsClient vectorsClient = getVectorsClient();
        String bucketName = genVectorBucketName();
        String fullBucketName = VECTOR_BUCKET_PREFIX + bucketName;

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
            Assert.assertEquals(fullBucketName, getResult.bucketInfo().name());

            // List vector buckets
            ListVectorBucketsResult listResult = vectorsClient.listVectorBuckets(ListVectorBucketsRequest.newBuilder()
                    .prefix(bucketName)
                    .build());
            Assert.assertNotNull(listResult);
            Assert.assertEquals(200, listResult.statusCode());
            assertThat(listResult.buckets()).isNotNull();
            boolean found = listResult.buckets().stream()
                    .anyMatch(bucket -> fullBucketName.equals(bucket.name()));
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
            } catch (Exception ignored) {}
        }
    }

    @Test
    public void testListVectorBucketsWithPagination() {
        OSSVectorsClient vectorsClient = getVectorsClient();
        String bucketPrefix = "test-page";
        String bucketName1 = bucketPrefix + "1" + (int) (Math.random() * 10000);
        String bucketName2 = bucketPrefix + "2" + (int) (Math.random() * 10000);


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
                    .maxKeys(1L)
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
                    .maxKeys(1l)
                    .build());

            Assert.assertNotNull(nextListResult);
            Assert.assertEquals(200, nextListResult.statusCode());
            assertThat(nextListResult.buckets()).isNotNull();
            Assert.assertEquals(1, nextListResult.buckets().size());
        } finally {
            // Cleanup
            vectorsClient.deleteVectorBucket(DeleteVectorBucketRequest.newBuilder()
                    .bucket(bucketName1)
                    .build());

            vectorsClient.deleteVectorBucket(DeleteVectorBucketRequest.newBuilder()
                    .bucket(bucketName2)
                    .build());
        }
    }

    @Test
    public void testVectorBucketNotFound() {
        OSSVectorsClient vectorsClient = getVectorsClient();
        String nonExistentBucket = "non-existent-bucket";

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

