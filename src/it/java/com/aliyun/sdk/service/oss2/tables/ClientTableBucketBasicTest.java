package com.aliyun.sdk.service.oss2.tables;

import com.aliyun.sdk.service.oss2.tables.models.*;
import org.junit.Assert;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ClientTableBucketBasicTest extends TestBaseTables {

    @Test
    public void testListTableBuckets() {
        OSSTablesClient client = getTablesClient();

        // List vector buckets with maxKeys = 1
        ListTableBucketsResult result = client.listTableBuckets(ListTableBucketsRequest.newBuilder()
                .maxBuckets(1)
                .build());

        Assert.assertNotNull(result);
        Assert.assertEquals(200, result.statusCode());
        assertThat(result.tableBuckets()).isNotNull();
        assertThat(result.tableBuckets().size()).isEqualTo(1);
        assertThat(result.tableBuckets().get(0).name()).isEqualTo(tableBucketName);

        // GetTableBucket
        TableBucketSummary info = result.tableBuckets().get(0);
        GetTableBucketResult gResult = client.getTableBucket(GetTableBucketRequest.newBuilder()
                .tableBucketARN(info.arn())
                .build());

        Assert.assertEquals(200, result.statusCode());
        assertThat(gResult.name()).isEqualTo(tableBucketName);
        assertThat(gResult.type()).isEqualTo("customer");
    }

    @Test
    public void testCreateTableBucketLifecycle() {
        OSSTablesClient tablesClient = getTablesClient();
        String bucketName = genTableBucketName();
        String tableBucketARN = null;

        try {
            // 1. Create a table bucket
            EncryptionConfiguration encryptionConfig = EncryptionConfiguration.newBuilder()
                    .sseAlgorithm("AES256")
                    .kmsKeyArn(null) // Using null for basic encryption
                    .build();

            CreateTableBucketResult createResult = tablesClient.createTableBucket(
                    CreateTableBucketRequest.newBuilder()
                            .name(bucketName)
                            .encryptionConfiguration(encryptionConfig)
                            .build());

            // Assert successful creation
            Assert.assertNotNull(createResult);
            Assert.assertEquals(200, createResult.statusCode());
            Assert.assertNotNull(createResult.arn());
            System.out.printf("Successfully created table bucket with ARN: %s%%n", createResult.arn());

            tableBucketARN = createResult.arn();


            // 2. Get the created table bucket to verify it exists
            GetTableBucketResult getResult = tablesClient.getTableBucket(
                    GetTableBucketRequest.newBuilder()
                            .tableBucketARN(tableBucketARN)
                            .build());

            // Assert successful retrieval
            Assert.assertNotNull(getResult);
            Assert.assertEquals(200, getResult.statusCode());
            Assert.assertEquals(bucketName, getResult.name());
            Assert.assertNotNull(getResult.arn());

            // 3. List table buckets and verify our bucket is included
            ListTableBucketsResult listResult = tablesClient.listTableBuckets(
                    ListTableBucketsRequest.newBuilder()
                            .prefix(bucketName) // Use prefix to find our specific bucket
                            .build());

            // Assert successful listing
            Assert.assertNotNull(listResult);
            Assert.assertEquals(200, listResult.statusCode());
            Assert.assertNotNull(listResult.tableBuckets());
            Assert.assertTrue("Should contain at least one bucket", listResult.tableBuckets().size() >= 1);

            // Find our bucket in the list
            boolean found = false;
            for (TableBucketSummary summary : listResult.tableBuckets()) {
                if (summary.name().equals(bucketName)) {
                    found = true;
                    Assert.assertEquals(bucketName, summary.name());
                    break;
                }
            }
            Assert.assertTrue("Created bucket should be found in the list", found);

        } finally {
            // 4. Cleanup: Delete the test bucket
            DeleteTableBucketResult deleteResult = tablesClient.deleteTableBucket(
                    DeleteTableBucketRequest.newBuilder()
                            .tableBucketARN(tableBucketARN)
                            .build());
            Assert.assertNotNull(deleteResult);
            Assert.assertEquals(204, deleteResult.statusCode());
        }
    }

    @Test
    public void testCreateTableBucketWithMinimalConfiguration() {
        OSSTablesClient tablesClient = getTablesClient();
        String bucketName = genTableBucketName();
        String tableBucketARN = null;

        try {
            // Create a table bucket with minimal configuration (just the name)
            CreateTableBucketResult createResult = tablesClient.createTableBucket(
                    CreateTableBucketRequest.newBuilder()
                            .name(bucketName)
                            .build());

            // Assert successful creation
            Assert.assertNotNull(createResult);
            Assert.assertEquals(200, createResult.statusCode());
            Assert.assertNotNull(createResult.arn());
            System.out.printf("Successfully created table bucket with minimal config, ARN: %s%%n", createResult.arn());


            tableBucketARN = createResult.arn();

            // Verify the bucket exists by getting it
            GetTableBucketResult getResult = tablesClient.getTableBucket(
                    GetTableBucketRequest.newBuilder()
                            .tableBucketARN(tableBucketARN)
                            .build());

            Assert.assertNotNull(getResult);
            Assert.assertEquals(200, getResult.statusCode());
            Assert.assertEquals(bucketName, getResult.name());
        } finally {
            // Cleanup: Delete the test bucket
            DeleteTableBucketResult deleteResult = tablesClient.deleteTableBucket(
                    DeleteTableBucketRequest.newBuilder()
                            .tableBucketARN(tableBucketARN)
                            .build());
            Assert.assertNotNull(deleteResult);
            Assert.assertEquals(204, deleteResult.statusCode());
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
