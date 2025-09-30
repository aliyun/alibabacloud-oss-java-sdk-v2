package com.aliyun.sdk.service.oss2.vectors;

import com.aliyun.sdk.service.oss2.vectors.models.*;
import org.junit.Assert;
import org.junit.Test;
import java.util.HashMap;
import java.util.Map;

public class ClientVectorIndexTest extends TestBaseVectors {

    @Test
    public void testVectorIndexLifecycle() {
        OSSVectorsClient vectorsClient = getVectorClient();
        String bucketName = genVectorBucketName();

        // 1. Create bucket for testing
        PutVectorBucketResult createBucketResult = vectorsClient.putVectorBucket(
                PutVectorBucketRequest.newBuilder()
                        .bucket(bucketName)
                        .build());
        Assert.assertNotNull(createBucketResult);
        Assert.assertEquals(200, createBucketResult.statusCode());

        try {
            // 2. Put (Create) a vector index
            String indexName = "testIndexForIntegration";
            int dimension = 3;
            String distanceMetric = "cosine";
            String dataType = "float32";

            Map<String, Object> metadata = new HashMap<>();
            metadata.put("nonFilterableMetadataKeys", new String[]{"key1", "key2"});

            PutVectorIndexResult putResult = vectorsClient.putVectorIndex(
                    PutVectorIndexRequest.newBuilder()
                            .bucket(bucketName)
                            .dataType(dataType)
                            .dimension(dimension)
                            .distanceMetric(distanceMetric)
                            .indexName(indexName)
                            .metadata(metadata)
                            .build());

            // Assert successful creation
            Assert.assertNotNull(putResult);
            Assert.assertEquals(200, putResult.statusCode());

            // 3. Get the created vector index
            GetVectorIndexResult getResult = vectorsClient.getVectorIndex(
                    GetVectorIndexRequest.newBuilder()
                            .bucket(bucketName)
                            .indexName(indexName)
                            .build());

            // Assert successful retrieval
            Assert.assertNotNull(getResult);
            Assert.assertEquals(200, getResult.statusCode());
            Assert.assertNotNull(getResult.index());

            // Assert retrieved index details match the created ones
            Assert.assertEquals(indexName, getResult.index().get("indexName"));
            Assert.assertEquals(dimension, getResult.index().get("dimension"));
            Assert.assertEquals(distanceMetric, getResult.index().get("distanceMetric"));

            // 4. List vector indexes and verify our index is included
            ListVectorIndexesResult listResult = vectorsClient.listVectorIndexes(
                    ListVectorIndexesRequest.newBuilder()
                            .bucket(bucketName)
                            .build());

            // Assert successful listing
            Assert.assertNotNull(listResult);
            Assert.assertEquals(200, listResult.statusCode());
            Assert.assertNotNull(listResult.indexes());
            Assert.assertEquals(1, listResult.indexes().size());

            // Find our specific index in the list
            Map<String, Object> foundIndex = null;
            for (Map<String, Object> index : listResult.indexes()) {
                if (indexName.equals(index.get("indexName"))) {
                    foundIndex = index;
                    break;
                }
            }

            Assert.assertNotNull("Index '" + indexName + "' not found in the list", foundIndex);
            Assert.assertEquals(dimension, foundIndex.get("dimension"));
            Assert.assertEquals(distanceMetric, foundIndex.get("distanceMetric"));

            // 5. Delete the vector index
            DeleteVectorIndexResult deleteResult = vectorsClient.deleteVectorIndex(
                    DeleteVectorIndexRequest.newBuilder()
                            .bucket(bucketName)
                            .indexName(indexName)
                            .build());

            // Assert successful deletion (Delete operations often return 204 No Content)
            Assert.assertNotNull(deleteResult);
            Assert.assertEquals(204, deleteResult.statusCode());

        } finally {
            // 6. Cleanup: Delete the test bucket
            DeleteVectorBucketResult deleteBucketResult = vectorsClient.deleteVectorBucket(
                    DeleteVectorBucketRequest.newBuilder()
                            .bucket(bucketName)
                            .build());
            Assert.assertNotNull(deleteBucketResult);
            Assert.assertEquals(204, deleteBucketResult.statusCode());

        }
    }
}

