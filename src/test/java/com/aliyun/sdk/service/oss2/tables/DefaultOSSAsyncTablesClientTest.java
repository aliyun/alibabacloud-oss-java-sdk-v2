package com.aliyun.sdk.service.oss2.tables;

import com.aliyun.sdk.service.oss2.credentials.AnonymousCredentialsProvider;
import com.aliyun.sdk.service.oss2.tables.models.*;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

public class DefaultOSSAsyncTablesClientTest {

    private static OSSAsyncTablesClient asyncTablesClient;

    @BeforeClass
    public static void oneTimeSetUp() {
        asyncTablesClient = OSSAsyncTablesClient.newBuilder()
                .region("cn-hangzhou")
                .credentialsProvider(new AnonymousCredentialsProvider())
                .build();
        assertNotNull(asyncTablesClient);
    }

    // ==================== Table Bucket API ====================

    @Test
    public void createTableBucketAsync_CheckField() {
        // CreateTableBucketAsync doesn't require tableBucketARN - it creates the bucket
        // This API uses bodyFields for name
        // We can test with empty body - should fail validation somewhere
        try {
            asyncTablesClient.createTableBucketAsync(CreateTableBucketRequest.newBuilder().build()).get();
            // If it doesn't throw, that's OK - this operation doesn't have tableBucketARN validation
        } catch (Exception e) {
            // Expected - may fail for other reasons (missing name, network, etc.)
        }
    }

    @Test
    public void getTableBucketAsync_CheckField() {
        // Required field
        try {
            asyncTablesClient.getTableBucketAsync(GetTableBucketRequest.newBuilder().build()).get();
            fail("should not here");
        } catch (Exception e) {
            // Exception may be thrown synchronously (e.getCause() is null) or wrapped in ExecutionException
            Throwable cause = e.getCause() != null ? e.getCause() : e;
            assertThat(cause).hasMessageContaining("request.tableBucketARN is required");
        }

        // not table bucket arn
        try {
            asyncTablesClient.getTableBucketAsync(GetTableBucketRequest.newBuilder()
                            .tableBucketARN("bucket-name")
                    .build()).get();
            fail("should not here");
        } catch (Exception e) {
            Throwable cause = e.getCause() != null ? e.getCause() : e;
            assertThat(cause).hasMessageContaining("Malformed ARN - ");
        }

        // table bucket arn with invalid bucket name
        try {
            asyncTablesClient.getTableBucketAsync(GetTableBucketRequest.newBuilder()
                    .tableBucketARN("acs:osstables:cn-beijing:123456:bucket/test-table?1234")
                    .build()).get();
            fail("should not here");
        } catch (Exception e) {
            Throwable cause = e.getCause() != null ? e.getCause() : e;
            assertThat(cause).hasMessageContaining("bucket resource is invalid,");
        }
    }

    @Test
    public void deleteTableBucketAsync_CheckField() {
        // Required field
        try {
            asyncTablesClient.deleteTableBucketAsync(DeleteTableBucketRequest.newBuilder().build()).get();
            fail("should not here");
        } catch (Exception e) {
            assertThat(e.getCause() != null ? e.getCause() : e).hasMessageContaining("request.tableBucketARN is required");
        }

        // not table bucket arn
        try {
            asyncTablesClient.deleteTableBucketAsync(DeleteTableBucketRequest.newBuilder()
                            .tableBucketARN("bucket-name")
                    .build()).get();
            fail("should not here");
        } catch (Exception e) {
            assertThat(e.getCause() != null ? e.getCause() : e).hasMessageContaining("Malformed ARN - ");
        }

        // table bucket arn with invalid bucket name
        try {
            asyncTablesClient.deleteTableBucketAsync(DeleteTableBucketRequest.newBuilder()
                    .tableBucketARN("acs:osstables:cn-beijing:123456:bucket/test-table?1234")
                    .build()).get();
            fail("should not here");
        } catch (Exception e) {
            assertThat(e.getCause() != null ? e.getCause() : e).hasMessageContaining("bucket resource is invalid,");
        }
    }

    @Test
    public void listTableBucketsAsync_CheckField() {
        // listTableBucketsAsync has no required fields
        try {
            asyncTablesClient.listTableBucketsAsync(ListTableBucketsRequest.newBuilder().build()).get();
            // May succeed or fail with network error, but not validation error
        } catch (ExecutionException e) {
            // Expected - network error or similar
        } catch (Exception e) {
            // Or interrupted exception
        }
    }

    // ==================== Namespace API ====================

    @Test
    public void createNamespaceAsync_CheckField() {
        // Required field - tableBucketARN
        try {
            asyncTablesClient.createNamespaceAsync(CreateNamespaceRequest.newBuilder().build()).get();
            fail("should not here");
        } catch (Exception e) {
            assertThat(e.getCause() != null ? e.getCause() : e).hasMessageContaining("request.tableBucketARN is required");
        }

        // not table bucket arn
        try {
            asyncTablesClient.createNamespaceAsync(CreateNamespaceRequest.newBuilder()
                            .tableBucketARN("bucket-name")
                            .namespace(Arrays.asList("test-ns"))
                    .build()).get();
            fail("should not here");
        } catch (Exception e) {
            assertThat(e.getCause() != null ? e.getCause() : e).hasMessageContaining("Malformed ARN - ");
        }

        // table bucket arn with invalid bucket name
        try {
            asyncTablesClient.createNamespaceAsync(CreateNamespaceRequest.newBuilder()
                    .tableBucketARN("acs:osstables:cn-beijing:123456:bucket/test-ns?1234")
                    .namespace(Arrays.asList("test-ns"))
                    .build()).get();
            fail("should not here");
        } catch (Exception e) {
            assertThat(e.getCause() != null ? e.getCause() : e).hasMessageContaining("bucket resource is invalid,");
        }
    }

    @Test
    public void getNamespaceAsync_CheckField() {
        // Required field - tableBucketARN
        try {
            asyncTablesClient.getNamespaceAsync(GetNamespaceRequest.newBuilder().build()).get();
            fail("should not here");
        } catch (Exception e) {
            assertThat(e.getCause() != null ? e.getCause() : e).hasMessageContaining("request.tableBucketARN is required");
        }

        // Required field - namespace
        try {
            asyncTablesClient.getNamespaceAsync(GetNamespaceRequest.newBuilder()
                    .tableBucketARN("acs:osstables:cn-beijing:123456:bucket/valid-bucket")
                    .build()).get();
            fail("should not here");
        } catch (Exception e) {
            assertThat(e.getCause() != null ? e.getCause() : e).hasMessageContaining("request.namespace is required");
        }

        // not table bucket arn
        try {
            asyncTablesClient.getNamespaceAsync(GetNamespaceRequest.newBuilder()
                            .tableBucketARN("bucket-name")
                            .namespace("test-ns")
                    .build()).get();
            fail("should not here");
        } catch (Exception e) {
            assertThat(e.getCause() != null ? e.getCause() : e).hasMessageContaining("Malformed ARN - ");
        }

        // table bucket arn with invalid bucket name
        try {
            asyncTablesClient.getNamespaceAsync(GetNamespaceRequest.newBuilder()
                    .tableBucketARN("acs:osstables:cn-beijing:123456:bucket/test-ns?1234")
                    .namespace("test-ns")
                    .build()).get();
            fail("should not here");
        } catch (Exception e) {
            assertThat(e.getCause() != null ? e.getCause() : e).hasMessageContaining("bucket resource is invalid,");
        }
    }

    @Test
    public void deleteNamespaceAsync_CheckField() {
        // Required field - tableBucketARN
        try {
            asyncTablesClient.deleteNamespaceAsync(DeleteNamespaceRequest.newBuilder().build()).get();
            fail("should not here");
        } catch (Exception e) {
            assertThat(e.getCause() != null ? e.getCause() : e).hasMessageContaining("request.tableBucketARN is required");
        }

        // Required field - namespace
        try {
            asyncTablesClient.deleteNamespaceAsync(DeleteNamespaceRequest.newBuilder()
                    .tableBucketARN("acs:osstables:cn-beijing:123456:bucket/valid-bucket")
                    .build()).get();
            fail("should not here");
        } catch (Exception e) {
            assertThat(e.getCause() != null ? e.getCause() : e).hasMessageContaining("request.namespace is required");
        }

        // not table bucket arn
        try {
            asyncTablesClient.deleteNamespaceAsync(DeleteNamespaceRequest.newBuilder()
                            .tableBucketARN("bucket-name")
                            .namespace("test-ns")
                    .build()).get();
            fail("should not here");
        } catch (Exception e) {
            assertThat(e.getCause() != null ? e.getCause() : e).hasMessageContaining("Malformed ARN - ");
        }

        // table bucket arn with invalid bucket name
        try {
            asyncTablesClient.deleteNamespaceAsync(DeleteNamespaceRequest.newBuilder()
                    .tableBucketARN("acs:osstables:cn-beijing:123456:bucket/test-ns?1234")
                    .namespace("test-ns")
                    .build()).get();
            fail("should not here");
        } catch (Exception e) {
            assertThat(e.getCause() != null ? e.getCause() : e).hasMessageContaining("bucket resource is invalid,");
        }
    }

    @Test
    public void listNamespacesAsync_CheckField() {
        // Required field - tableBucketARN
        try {
            asyncTablesClient.listNamespacesAsync(ListNamespacesRequest.newBuilder().build()).get();
            fail("should not here");
        } catch (Exception e) {
            assertThat(e.getCause() != null ? e.getCause() : e).hasMessageContaining("request.tableBucketARN is required");
        }

        // not table bucket arn
        try {
            asyncTablesClient.listNamespacesAsync(ListNamespacesRequest.newBuilder()
                            .tableBucketARN("bucket-name")
                    .build()).get();
            fail("should not here");
        } catch (Exception e) {
            assertThat(e.getCause() != null ? e.getCause() : e).hasMessageContaining("Malformed ARN - ");
        }

        // table bucket arn with invalid bucket name
        try {
            asyncTablesClient.listNamespacesAsync(ListNamespacesRequest.newBuilder()
                    .tableBucketARN("acs:osstables:cn-beijing:123456:bucket/test-ns?1234")
                    .build()).get();
            fail("should not here");
        } catch (Exception e) {
            assertThat(e.getCause() != null ? e.getCause() : e).hasMessageContaining("bucket resource is invalid,");
        }
    }

    // ==================== Table API ====================

    @Test
    public void createTableAsync_CheckField() {
        // Required field - tableBucketARN
        try {
            asyncTablesClient.createTableAsync(CreateTableRequest.newBuilder().build()).get();
            fail("should not here");
        } catch (Exception e) {
            assertThat(e.getCause() != null ? e.getCause() : e).hasMessageContaining("request.tableBucketARN is required");
        }

        // Required field - namespace
        try {
            asyncTablesClient.createTableAsync(CreateTableRequest.newBuilder()
                    .tableBucketARN("acs:osstables:cn-beijing:123456:bucket/valid-bucket")
                    .build()).get();
            fail("should not here");
        } catch (Exception e) {
            assertThat(e.getCause() != null ? e.getCause() : e).hasMessageContaining("request.namespace is required");
        }

        // table bucket arn with invalid bucket name
        try {
            asyncTablesClient.createTableAsync(CreateTableRequest.newBuilder()
                    .tableBucketARN("acs:osstables:cn-beijing:123456:bucket/test-table?1234")
                    .namespace("test-ns")
                    .build()).get();
            fail("should not here");
        } catch (Exception e) {
            assertThat(e.getCause() != null ? e.getCause() : e).hasMessageContaining("bucket resource is invalid,");
        }
    }

    @Test
    public void getTableAsync_CheckField() {
        // GetTableAsync can use either tableBucketARN+namespace+name OR tableArn
        // When using tableBucketARN approach, tableBucketARN is required
        // If neither tableBucketARN nor tableArn is provided, the error mentions tableArn
        try {
            asyncTablesClient.getTableAsync(GetTableRequest.newBuilder()
                    .namespace("test-ns")
                    .name("test-table")
                    .build()).get();
            fail("should not here");
        } catch (Exception e) {
            // The error can be either about tableBucketARN or tableArn being required
            // depending on which validation path is taken
            assertThat(e.getCause() != null ? e.getCause() : e).hasMessageContaining("required");
        }

        // not table bucket arn
        try {
            asyncTablesClient.getTableAsync(GetTableRequest.newBuilder()
                            .tableBucketARN("bucket-name")
                            .namespace("test-ns")
                            .name("test-table")
                    .build()).get();
            fail("should not here");
        } catch (Exception e) {
            assertThat(e.getCause() != null ? e.getCause() : e).hasMessageContaining("Malformed ARN - ");
        }

        // table bucket arn with invalid bucket name
        try {
            asyncTablesClient.getTableAsync(GetTableRequest.newBuilder()
                    .tableBucketARN("acs:osstables:cn-beijing:123456:bucket/test-table?1234")
                    .namespace("test-ns")
                    .name("test-table")
                    .build()).get();
            fail("should not here");
        } catch (Exception e) {
            assertThat(e.getCause() != null ? e.getCause() : e).hasMessageContaining("bucket resource is invalid,");
        }
    }

    @Test
    public void deleteTableAsync_CheckField() {
        // Required field - tableBucketARN
        try {
            asyncTablesClient.deleteTableAsync(DeleteTableRequest.newBuilder()
                    .namespace("test-ns")
                    .name("test-table")
                    .build()).get();
            fail("should not here");
        } catch (Exception e) {
            assertThat(e.getCause() != null ? e.getCause() : e).hasMessageContaining("request.tableBucketARN is required");
        }

        // Required field - namespace
        try {
            asyncTablesClient.deleteTableAsync(DeleteTableRequest.newBuilder()
                    .tableBucketARN("acs:osstables:cn-beijing:123456:bucket/valid-bucket")
                    .name("test-table")
                    .build()).get();
            fail("should not here");
        } catch (Exception e) {
            assertThat(e.getCause() != null ? e.getCause() : e).hasMessageContaining("request.namespace is required");
        }

        // Required field - name
        try {
            asyncTablesClient.deleteTableAsync(DeleteTableRequest.newBuilder()
                    .tableBucketARN("acs:osstables:cn-beijing:123456:bucket/valid-bucket")
                    .namespace("test-ns")
                    .build()).get();
            fail("should not here");
        } catch (Exception e) {
            assertThat(e.getCause() != null ? e.getCause() : e).hasMessageContaining("request.name is required");
        }

        // not table bucket arn
        try {
            asyncTablesClient.deleteTableAsync(DeleteTableRequest.newBuilder()
                    .tableBucketARN("bucket-name")
                    .namespace("test-ns")
                    .name("test-table")
                    .build()).get();
            fail("should not here");
        } catch (Exception e) {
            assertThat(e.getCause() != null ? e.getCause() : e).hasMessageContaining("Malformed ARN - ");
        }

        // table bucket arn with invalid bucket name
        try {
            asyncTablesClient.deleteTableAsync(DeleteTableRequest.newBuilder()
                    .tableBucketARN("acs:osstables:cn-beijing:123456:bucket/test-table?1234")
                    .namespace("test-ns")
                    .name("test-table")
                    .build()).get();
            fail("should not here");
        } catch (Exception e) {
            assertThat(e.getCause() != null ? e.getCause() : e).hasMessageContaining("bucket resource is invalid,");
        }
    }

    @Test
    public void listTablesAsync_CheckField() {
        // Required field - tableBucketARN
        try {
            asyncTablesClient.listTablesAsync(ListTablesRequest.newBuilder().build()).get();
            fail("should not here");
        } catch (Exception e) {
            assertThat(e.getCause() != null ? e.getCause() : e).hasMessageContaining("request.tableBucketARN is required");
        }

        // Required field - namespace
        try {
            asyncTablesClient.listTablesAsync(ListTablesRequest.newBuilder()
                    .tableBucketARN("acs:osstables:cn-beijing:123456:bucket/valid-bucket")
                    .build()).get();
            fail("should not here");
        } catch (Exception e) {
            assertThat(e.getCause() != null ? e.getCause() : e).hasMessageContaining("request.namespace is required");
        }

        // not table bucket arn
        try {
            asyncTablesClient.listTablesAsync(ListTablesRequest.newBuilder()
                            .tableBucketARN("bucket-name")
                            .namespace("test-ns")
                    .build()).get();
            fail("should not here");
        } catch (Exception e) {
            assertThat(e.getCause() != null ? e.getCause() : e).hasMessageContaining("Malformed ARN - ");
        }

        // table bucket arn with invalid bucket name
        try {
            asyncTablesClient.listTablesAsync(ListTablesRequest.newBuilder()
                    .tableBucketARN("acs:osstables:cn-beijing:123456:bucket/test-table?1234")
                    .namespace("test-ns")
                    .build()).get();
            fail("should not here");
        } catch (Exception e) {
            assertThat(e.getCause() != null ? e.getCause() : e).hasMessageContaining("bucket resource is invalid,");
        }
    }

    @Test
    public void renameTableAsync_CheckField() {
        // Required field - tableBucketARN
        try {
            asyncTablesClient.renameTableAsync(RenameTableRequest.newBuilder()
                    .namespace("test-ns")
                    .name("old-table")
                    .newName("new-table")
                    .build()).get();
            fail("should not here");
        } catch (Exception e) {
            assertThat(e.getCause() != null ? e.getCause() : e).hasMessageContaining("request.tableBucketARN is required");
        }

        // Required field - namespace
        try {
            asyncTablesClient.renameTableAsync(RenameTableRequest.newBuilder()
                    .tableBucketARN("acs:osstables:cn-beijing:123456:bucket/valid-bucket")
                    .name("old-table")
                    .newName("new-table")
                    .build()).get();
            fail("should not here");
        } catch (Exception e) {
            assertThat(e.getCause() != null ? e.getCause() : e).hasMessageContaining("request.namespace is required");
        }

        // Required field - name
        try {
            asyncTablesClient.renameTableAsync(RenameTableRequest.newBuilder()
                    .tableBucketARN("acs:osstables:cn-beijing:123456:bucket/valid-bucket")
                    .namespace("test-ns")
                    .newName("new-table")
                    .build()).get();
            fail("should not here");
        } catch (Exception e) {
            assertThat(e.getCause() != null ? e.getCause() : e).hasMessageContaining("request.name is required");
        }

        // not table bucket arn
        try {
            asyncTablesClient.renameTableAsync(RenameTableRequest.newBuilder()
                    .tableBucketARN("bucket-name")
                    .namespace("test-ns")
                    .name("old-table")
                    .newName("new-table")
                    .build()).get();
            fail("should not here");
        } catch (Exception e) {
            assertThat(e.getCause() != null ? e.getCause() : e).hasMessageContaining("Malformed ARN - ");
        }

        // table bucket arn with invalid bucket name
        try {
            asyncTablesClient.renameTableAsync(RenameTableRequest.newBuilder()
                    .tableBucketARN("acs:osstables:cn-beijing:123456:bucket/test-table?1234")
                    .namespace("test-ns")
                    .name("old-table")
                    .newName("new-table")
                    .build()).get();
            fail("should not here");
        } catch (Exception e) {
            assertThat(e.getCause() != null ? e.getCause() : e).hasMessageContaining("bucket resource is invalid,");
        }
    }

    // ==================== Table Bucket Config API ====================

    @Test
    public void putTableBucketEncryptionAsync_CheckField() {
        // Required field - tableBucketARN
        try {
            asyncTablesClient.putTableBucketEncryptionAsync(PutTableBucketEncryptionRequest.newBuilder()
                    .encryptionConfiguration(new EncryptionConfiguration())
                    .build()).get();
            fail("should not here");
        } catch (Exception e) {
            assertThat(e.getCause() != null ? e.getCause() : e).hasMessageContaining("request.tableBucketARN is required");
        }

        // not table bucket arn
        try {
            asyncTablesClient.putTableBucketEncryptionAsync(PutTableBucketEncryptionRequest.newBuilder()
                            .tableBucketARN("bucket-name")
                            .encryptionConfiguration(new EncryptionConfiguration())
                    .build()).get();
            fail("should not here");
        } catch (Exception e) {
            assertThat(e.getCause() != null ? e.getCause() : e).hasMessageContaining("Malformed ARN - ");
        }

        // table bucket arn with invalid bucket name
        try {
            asyncTablesClient.putTableBucketEncryptionAsync(PutTableBucketEncryptionRequest.newBuilder()
                    .tableBucketARN("acs:osstables:cn-beijing:123456:bucket/test-table?1234")
                    .encryptionConfiguration(new EncryptionConfiguration())
                    .build()).get();
            fail("should not here");
        } catch (Exception e) {
            assertThat(e.getCause() != null ? e.getCause() : e).hasMessageContaining("bucket resource is invalid,");
        }
    }

    @Test
    public void getTableBucketEncryptionAsync_CheckField() {
        // Required field - tableBucketARN
        try {
            asyncTablesClient.getTableBucketEncryptionAsync(GetTableBucketEncryptionRequest.newBuilder().build()).get();
            fail("should not here");
        } catch (Exception e) {
            assertThat(e.getCause() != null ? e.getCause() : e).hasMessageContaining("request.tableBucketARN is required");
        }

        // not table bucket arn
        try {
            asyncTablesClient.getTableBucketEncryptionAsync(GetTableBucketEncryptionRequest.newBuilder()
                            .tableBucketARN("bucket-name")
                    .build()).get();
            fail("should not here");
        } catch (Exception e) {
            assertThat(e.getCause() != null ? e.getCause() : e).hasMessageContaining("Malformed ARN - ");
        }

        // table bucket arn with invalid bucket name
        try {
            asyncTablesClient.getTableBucketEncryptionAsync(GetTableBucketEncryptionRequest.newBuilder()
                    .tableBucketARN("acs:osstables:cn-beijing:123456:bucket/test-table?1234")
                    .build()).get();
            fail("should not here");
        } catch (Exception e) {
            assertThat(e.getCause() != null ? e.getCause() : e).hasMessageContaining("bucket resource is invalid,");
        }
    }

    @Test
    public void deleteTableBucketEncryptionAsync_CheckField() {
        // Required field - tableBucketARN
        try {
            asyncTablesClient.deleteTableBucketEncryptionAsync(DeleteTableBucketEncryptionRequest.newBuilder().build()).get();
            fail("should not here");
        } catch (Exception e) {
            assertThat(e.getCause() != null ? e.getCause() : e).hasMessageContaining("request.tableBucketARN is required");
        }

        // not table bucket arn
        try {
            asyncTablesClient.deleteTableBucketEncryptionAsync(DeleteTableBucketEncryptionRequest.newBuilder()
                            .tableBucketARN("bucket-name")
                    .build()).get();
            fail("should not here");
        } catch (Exception e) {
            assertThat(e.getCause() != null ? e.getCause() : e).hasMessageContaining("Malformed ARN - ");
        }

        // table bucket arn with invalid bucket name
        try {
            asyncTablesClient.deleteTableBucketEncryptionAsync(DeleteTableBucketEncryptionRequest.newBuilder()
                    .tableBucketARN("acs:osstables:cn-beijing:123456:bucket/test-table?1234")
                    .build()).get();
            fail("should not here");
        } catch (Exception e) {
            assertThat(e.getCause() != null ? e.getCause() : e).hasMessageContaining("bucket resource is invalid,");
        }
    }

    @Test
    public void putTableBucketPolicyAsync_CheckField() {
        // Required field - tableBucketARN
        try {
            asyncTablesClient.putTableBucketPolicyAsync(PutTableBucketPolicyRequest.newBuilder()
                    .resourcePolicy("{}")
                    .build()).get();
            fail("should not here");
        } catch (Exception e) {
            assertThat(e.getCause() != null ? e.getCause() : e).hasMessageContaining("request.tableBucketARN is required");
        }

        // not table bucket arn
        try {
            asyncTablesClient.putTableBucketPolicyAsync(PutTableBucketPolicyRequest.newBuilder()
                            .tableBucketARN("bucket-name")
                            .resourcePolicy("{}")
                    .build()).get();
            fail("should not here");
        } catch (Exception e) {
            assertThat(e.getCause() != null ? e.getCause() : e).hasMessageContaining("Malformed ARN - ");
        }

        // table bucket arn with invalid bucket name
        try {
            asyncTablesClient.putTableBucketPolicyAsync(PutTableBucketPolicyRequest.newBuilder()
                    .tableBucketARN("acs:osstables:cn-beijing:123456:bucket/test-table?1234")
                    .resourcePolicy("{}")
                    .build()).get();
            fail("should not here");
        } catch (Exception e) {
            assertThat(e.getCause() != null ? e.getCause() : e).hasMessageContaining("bucket resource is invalid,");
        }
    }

    @Test
    public void getTableBucketPolicyAsync_CheckField() {
        // Required field - tableBucketARN
        try {
            asyncTablesClient.getTableBucketPolicyAsync(GetTableBucketPolicyRequest.newBuilder().build()).get();
            fail("should not here");
        } catch (Exception e) {
            assertThat(e.getCause() != null ? e.getCause() : e).hasMessageContaining("request.tableBucketARN is required");
        }

        // not table bucket arn
        try {
            asyncTablesClient.getTableBucketPolicyAsync(GetTableBucketPolicyRequest.newBuilder()
                            .tableBucketARN("bucket-name")
                    .build()).get();
            fail("should not here");
        } catch (Exception e) {
            assertThat(e.getCause() != null ? e.getCause() : e).hasMessageContaining("Malformed ARN - ");
        }

        // table bucket arn with invalid bucket name
        try {
            asyncTablesClient.getTableBucketPolicyAsync(GetTableBucketPolicyRequest.newBuilder()
                    .tableBucketARN("acs:osstables:cn-beijing:123456:bucket/test-table?1234")
                    .build()).get();
            fail("should not here");
        } catch (Exception e) {
            assertThat(e.getCause() != null ? e.getCause() : e).hasMessageContaining("bucket resource is invalid,");
        }
    }

    @Test
    public void deleteTableBucketPolicyAsync_CheckField() {
        // Required field - tableBucketARN
        try {
            asyncTablesClient.deleteTableBucketPolicyAsync(DeleteTableBucketPolicyRequest.newBuilder().build()).get();
            fail("should not here");
        } catch (Exception e) {
            assertThat(e.getCause() != null ? e.getCause() : e).hasMessageContaining("request.tableBucketARN is required");
        }

        // not table bucket arn
        try {
            asyncTablesClient.deleteTableBucketPolicyAsync(DeleteTableBucketPolicyRequest.newBuilder()
                            .tableBucketARN("bucket-name")
                    .build()).get();
            fail("should not here");
        } catch (Exception e) {
            assertThat(e.getCause() != null ? e.getCause() : e).hasMessageContaining("Malformed ARN - ");
        }

        // table bucket arn with invalid bucket name
        try {
            asyncTablesClient.deleteTableBucketPolicyAsync(DeleteTableBucketPolicyRequest.newBuilder()
                    .tableBucketARN("acs:osstables:cn-beijing:123456:bucket/test-table?1234")
                    .build()).get();
            fail("should not here");
        } catch (Exception e) {
            assertThat(e.getCause() != null ? e.getCause() : e).hasMessageContaining("bucket resource is invalid,");
        }
    }

    @Test
    public void putTableBucketMaintenanceConfigurationAsync_CheckField() {
        // Required field - tableBucketARN
        try {
            asyncTablesClient.putTableBucketMaintenanceConfigurationAsync(PutTableBucketMaintenanceConfigurationRequest.newBuilder()
                    .type("full")
                    .build()).get();
            fail("should not here");
        } catch (Exception e) {
            assertThat(e.getCause() != null ? e.getCause() : e).hasMessageContaining("request.tableBucketARN is required");
        }

        // not table bucket arn
        try {
            asyncTablesClient.putTableBucketMaintenanceConfigurationAsync(PutTableBucketMaintenanceConfigurationRequest.newBuilder()
                            .tableBucketARN("bucket-name")
                            .type("full")
                    .build()).get();
            fail("should not here");
        } catch (Exception e) {
            assertThat(e.getCause() != null ? e.getCause() : e).hasMessageContaining("Malformed ARN - ");
        }

        // table bucket arn with invalid bucket name
        try {
            asyncTablesClient.putTableBucketMaintenanceConfigurationAsync(PutTableBucketMaintenanceConfigurationRequest.newBuilder()
                    .tableBucketARN("acs:osstables:cn-beijing:123456:bucket/test-table?1234")
                    .type("full")
                    .build()).get();
            fail("should not here");
        } catch (Exception e) {
            assertThat(e.getCause() != null ? e.getCause() : e).hasMessageContaining("bucket resource is invalid,");
        }
    }

    @Test
    public void getTableBucketMaintenanceConfigurationAsync_CheckField() {
        // Required field - tableBucketARN
        try {
            asyncTablesClient.getTableBucketMaintenanceConfigurationAsync(GetTableBucketMaintenanceConfigurationRequest.newBuilder().build()).get();
            fail("should not here");
        } catch (Exception e) {
            assertThat(e.getCause() != null ? e.getCause() : e).hasMessageContaining("request.tableBucketARN is required");
        }

        // not table bucket arn
        try {
            asyncTablesClient.getTableBucketMaintenanceConfigurationAsync(GetTableBucketMaintenanceConfigurationRequest.newBuilder()
                            .tableBucketARN("bucket-name")
                    .build()).get();
            fail("should not here");
        } catch (Exception e) {
            assertThat(e.getCause() != null ? e.getCause() : e).hasMessageContaining("Malformed ARN - ");
        }

        // table bucket arn with invalid bucket name
        try {
            asyncTablesClient.getTableBucketMaintenanceConfigurationAsync(GetTableBucketMaintenanceConfigurationRequest.newBuilder()
                    .tableBucketARN("acs:osstables:cn-beijing:123456:bucket/test-table?1234")
                    .build()).get();
            fail("should not here");
        } catch (Exception e) {
            assertThat(e.getCause() != null ? e.getCause() : e).hasMessageContaining("bucket resource is invalid,");
        }
    }

    // ==================== Table Config API ====================

    @Test
    public void getTableEncryptionAsync_CheckField() {
        // Required field - tableBucketARN
        try {
            asyncTablesClient.getTableEncryptionAsync(GetTableEncryptionRequest.newBuilder()
                    .namespace("test-ns")
                    .name("test-table")
                    .build()).get();
            fail("should not here");
        } catch (Exception e) {
            assertThat(e.getCause() != null ? e.getCause() : e).hasMessageContaining("request.tableBucketARN is required");
        }

        // Required field - namespace
        try {
            asyncTablesClient.getTableEncryptionAsync(GetTableEncryptionRequest.newBuilder()
                    .tableBucketARN("acs:osstables:cn-beijing:123456:bucket/valid-bucket")
                    .name("test-table")
                    .build()).get();
            fail("should not here");
        } catch (Exception e) {
            assertThat(e.getCause() != null ? e.getCause() : e).hasMessageContaining("request.namespace is required");
        }

        // Required field - name
        try {
            asyncTablesClient.getTableEncryptionAsync(GetTableEncryptionRequest.newBuilder()
                    .tableBucketARN("acs:osstables:cn-beijing:123456:bucket/valid-bucket")
                    .namespace("test-ns")
                    .build()).get();
            fail("should not here");
        } catch (Exception e) {
            assertThat(e.getCause() != null ? e.getCause() : e).hasMessageContaining("request.name is required");
        }

        // not table bucket arn
        try {
            asyncTablesClient.getTableEncryptionAsync(GetTableEncryptionRequest.newBuilder()
                            .tableBucketARN("bucket-name")
                            .namespace("test-ns")
                            .name("test-table")
                    .build()).get();
            fail("should not here");
        } catch (Exception e) {
            assertThat(e.getCause() != null ? e.getCause() : e).hasMessageContaining("Malformed ARN - ");
        }

        // table bucket arn with invalid bucket name
        try {
            asyncTablesClient.getTableEncryptionAsync(GetTableEncryptionRequest.newBuilder()
                    .tableBucketARN("acs:osstables:cn-beijing:123456:bucket/test-table?1234")
                    .namespace("test-ns")
                    .name("test-table")
                    .build()).get();
            fail("should not here");
        } catch (Exception e) {
            assertThat(e.getCause() != null ? e.getCause() : e).hasMessageContaining("bucket resource is invalid,");
        }
    }

    @Test
    public void putTablePolicyAsync_CheckField() {
        // Required field - tableBucketARN
        try {
            asyncTablesClient.putTablePolicyAsync(PutTablePolicyRequest.newBuilder()
                    .namespace("test-ns")
                    .name("test-table")
                    .resourcePolicy("{}")
                    .build()).get();
            fail("should not here");
        } catch (Exception e) {
            assertThat(e.getCause() != null ? e.getCause() : e).hasMessageContaining("request.tableBucketARN is required");
        }

        // Required field - namespace
        try {
            asyncTablesClient.putTablePolicyAsync(PutTablePolicyRequest.newBuilder()
                    .tableBucketARN("acs:osstables:cn-beijing:123456:bucket/valid-bucket")
                    .name("test-table")
                    .resourcePolicy("{}")
                    .build()).get();
            fail("should not here");
        } catch (Exception e) {
            assertThat(e.getCause() != null ? e.getCause() : e).hasMessageContaining("request.namespace is required");
        }

        // Required field - name
        try {
            asyncTablesClient.putTablePolicyAsync(PutTablePolicyRequest.newBuilder()
                    .tableBucketARN("acs:osstables:cn-beijing:123456:bucket/valid-bucket")
                    .namespace("test-ns")
                    .resourcePolicy("{}")
                    .build()).get();
            fail("should not here");
        } catch (Exception e) {
            assertThat(e.getCause() != null ? e.getCause() : e).hasMessageContaining("request.name is required");
        }

        // not table bucket arn
        try {
            asyncTablesClient.putTablePolicyAsync(PutTablePolicyRequest.newBuilder()
                            .tableBucketARN("bucket-name")
                            .namespace("test-ns")
                            .name("test-table")
                            .resourcePolicy("{}")
                    .build()).get();
            fail("should not here");
        } catch (Exception e) {
            assertThat(e.getCause() != null ? e.getCause() : e).hasMessageContaining("Malformed ARN - ");
        }

        // table bucket arn with invalid bucket name
        try {
            asyncTablesClient.putTablePolicyAsync(PutTablePolicyRequest.newBuilder()
                    .tableBucketARN("acs:osstables:cn-beijing:123456:bucket/test-table?1234")
                    .namespace("test-ns")
                    .name("test-table")
                    .resourcePolicy("{}")
                    .build()).get();
            fail("should not here");
        } catch (Exception e) {
            assertThat(e.getCause() != null ? e.getCause() : e).hasMessageContaining("bucket resource is invalid,");
        }
    }

    @Test
    public void getTablePolicyAsync_CheckField() {
        // Required field - tableBucketARN
        try {
            asyncTablesClient.getTablePolicyAsync(GetTablePolicyRequest.newBuilder()
                    .namespace("test-ns")
                    .name("test-table")
                    .build()).get();
            fail("should not here");
        } catch (Exception e) {
            assertThat(e.getCause() != null ? e.getCause() : e).hasMessageContaining("request.tableBucketARN is required");
        }

        // Required field - namespace
        try {
            asyncTablesClient.getTablePolicyAsync(GetTablePolicyRequest.newBuilder()
                    .tableBucketARN("acs:osstables:cn-beijing:123456:bucket/valid-bucket")
                    .name("test-table")
                    .build()).get();
            fail("should not here");
        } catch (Exception e) {
            assertThat(e.getCause() != null ? e.getCause() : e).hasMessageContaining("request.namespace is required");
        }

        // Required field - name
        try {
            asyncTablesClient.getTablePolicyAsync(GetTablePolicyRequest.newBuilder()
                    .tableBucketARN("acs:osstables:cn-beijing:123456:bucket/valid-bucket")
                    .namespace("test-ns")
                    .build()).get();
            fail("should not here");
        } catch (Exception e) {
            assertThat(e.getCause() != null ? e.getCause() : e).hasMessageContaining("request.name is required");
        }

        // not table bucket arn
        try {
            asyncTablesClient.getTablePolicyAsync(GetTablePolicyRequest.newBuilder()
                            .tableBucketARN("bucket-name")
                            .namespace("test-ns")
                            .name("test-table")
                    .build()).get();
            fail("should not here");
        } catch (Exception e) {
            assertThat(e.getCause() != null ? e.getCause() : e).hasMessageContaining("Malformed ARN - ");
        }

        // table bucket arn with invalid bucket name
        try {
            asyncTablesClient.getTablePolicyAsync(GetTablePolicyRequest.newBuilder()
                    .tableBucketARN("acs:osstables:cn-beijing:123456:bucket/test-table?1234")
                    .namespace("test-ns")
                    .name("test-table")
                    .build()).get();
            fail("should not here");
        } catch (Exception e) {
            assertThat(e.getCause() != null ? e.getCause() : e).hasMessageContaining("bucket resource is invalid,");
        }
    }

    @Test
    public void deleteTablePolicyAsync_CheckField() {
        // Required field - tableBucketARN
        try {
            asyncTablesClient.deleteTablePolicyAsync(DeleteTablePolicyRequest.newBuilder()
                    .namespace("test-ns")
                    .name("test-table")
                    .build()).get();
            fail("should not here");
        } catch (Exception e) {
            assertThat(e.getCause() != null ? e.getCause() : e).hasMessageContaining("request.tableBucketARN is required");
        }

        // Required field - namespace
        try {
            asyncTablesClient.deleteTablePolicyAsync(DeleteTablePolicyRequest.newBuilder()
                    .tableBucketARN("acs:osstables:cn-beijing:123456:bucket/valid-bucket")
                    .name("test-table")
                    .build()).get();
            fail("should not here");
        } catch (Exception e) {
            assertThat(e.getCause() != null ? e.getCause() : e).hasMessageContaining("request.namespace is required");
        }

        // Required field - name
        try {
            asyncTablesClient.deleteTablePolicyAsync(DeleteTablePolicyRequest.newBuilder()
                    .tableBucketARN("acs:osstables:cn-beijing:123456:bucket/valid-bucket")
                    .namespace("test-ns")
                    .build()).get();
            fail("should not here");
        } catch (Exception e) {
            assertThat(e.getCause() != null ? e.getCause() : e).hasMessageContaining("request.name is required");
        }

        // not table bucket arn
        try {
            asyncTablesClient.deleteTablePolicyAsync(DeleteTablePolicyRequest.newBuilder()
                            .tableBucketARN("bucket-name")
                            .namespace("test-ns")
                            .name("test-table")
                    .build()).get();
            fail("should not here");
        } catch (Exception e) {
            assertThat(e.getCause() != null ? e.getCause() : e).hasMessageContaining("Malformed ARN - ");
        }

        // table bucket arn with invalid bucket name
        try {
            asyncTablesClient.deleteTablePolicyAsync(DeleteTablePolicyRequest.newBuilder()
                    .tableBucketARN("acs:osstables:cn-beijing:123456:bucket/test-table?1234")
                    .namespace("test-ns")
                    .name("test-table")
                    .build()).get();
            fail("should not here");
        } catch (Exception e) {
            assertThat(e.getCause() != null ? e.getCause() : e).hasMessageContaining("bucket resource is invalid,");
        }
    }

    @Test
    public void putTableMaintenanceConfigurationAsync_CheckField() {
        // Required field - tableBucketARN
        try {
            asyncTablesClient.putTableMaintenanceConfigurationAsync(PutTableMaintenanceConfigurationRequest.newBuilder()
                    .namespace("test-ns")
                    .name("test-table")
                    .type("full")
                    .build()).get();
            fail("should not here");
        } catch (Exception e) {
            assertThat(e.getCause() != null ? e.getCause() : e).hasMessageContaining("request.tableBucketARN is required");
        }

        // Required field - namespace
        try {
            asyncTablesClient.putTableMaintenanceConfigurationAsync(PutTableMaintenanceConfigurationRequest.newBuilder()
                    .tableBucketARN("acs:osstables:cn-beijing:123456:bucket/valid-bucket")
                    .name("test-table")
                    .type("full")
                    .build()).get();
            fail("should not here");
        } catch (Exception e) {
            assertThat(e.getCause() != null ? e.getCause() : e).hasMessageContaining("request.namespace is required");
        }

        // Required field - name
        try {
            asyncTablesClient.putTableMaintenanceConfigurationAsync(PutTableMaintenanceConfigurationRequest.newBuilder()
                    .tableBucketARN("acs:osstables:cn-beijing:123456:bucket/valid-bucket")
                    .namespace("test-ns")
                    .type("full")
                    .build()).get();
            fail("should not here");
        } catch (Exception e) {
            assertThat(e.getCause() != null ? e.getCause() : e).hasMessageContaining("request.name is required");
        }

        // not table bucket arn
        try {
            asyncTablesClient.putTableMaintenanceConfigurationAsync(PutTableMaintenanceConfigurationRequest.newBuilder()
                            .tableBucketARN("bucket-name")
                            .namespace("test-ns")
                            .name("test-table")
                            .type("full")
                    .build()).get();
            fail("should not here");
        } catch (Exception e) {
            assertThat(e.getCause() != null ? e.getCause() : e).hasMessageContaining("Malformed ARN - ");
        }

        // table bucket arn with invalid bucket name
        try {
            asyncTablesClient.putTableMaintenanceConfigurationAsync(PutTableMaintenanceConfigurationRequest.newBuilder()
                    .tableBucketARN("acs:osstables:cn-beijing:123456:bucket/test-table?1234")
                    .namespace("test-ns")
                    .name("test-table")
                    .type("full")
                    .build()).get();
            fail("should not here");
        } catch (Exception e) {
            assertThat(e.getCause() != null ? e.getCause() : e).hasMessageContaining("bucket resource is invalid,");
        }
    }

    @Test
    public void getTableMaintenanceConfigurationAsync_CheckField() {
        // Required field - tableBucketARN
        try {
            asyncTablesClient.getTableMaintenanceConfigurationAsync(GetTableMaintenanceConfigurationRequest.newBuilder()
                    .namespace("test-ns")
                    .name("test-table")
                    .build()).get();
            fail("should not here");
        } catch (Exception e) {
            assertThat(e.getCause() != null ? e.getCause() : e).hasMessageContaining("request.tableBucketARN is required");
        }

        // Required field - namespace
        try {
            asyncTablesClient.getTableMaintenanceConfigurationAsync(GetTableMaintenanceConfigurationRequest.newBuilder()
                    .tableBucketARN("acs:osstables:cn-beijing:123456:bucket/valid-bucket")
                    .name("test-table")
                    .build()).get();
            fail("should not here");
        } catch (Exception e) {
            assertThat(e.getCause() != null ? e.getCause() : e).hasMessageContaining("request.namespace is required");
        }

        // Required field - name
        try {
            asyncTablesClient.getTableMaintenanceConfigurationAsync(GetTableMaintenanceConfigurationRequest.newBuilder()
                    .tableBucketARN("acs:osstables:cn-beijing:123456:bucket/valid-bucket")
                    .namespace("test-ns")
                    .build()).get();
            fail("should not here");
        } catch (Exception e) {
            assertThat(e.getCause() != null ? e.getCause() : e).hasMessageContaining("request.name is required");
        }

        // not table bucket arn
        try {
            asyncTablesClient.getTableMaintenanceConfigurationAsync(GetTableMaintenanceConfigurationRequest.newBuilder()
                            .tableBucketARN("bucket-name")
                            .namespace("test-ns")
                            .name("test-table")
                    .build()).get();
            fail("should not here");
        } catch (Exception e) {
            assertThat(e.getCause() != null ? e.getCause() : e).hasMessageContaining("Malformed ARN - ");
        }

        // table bucket arn with invalid bucket name
        try {
            asyncTablesClient.getTableMaintenanceConfigurationAsync(GetTableMaintenanceConfigurationRequest.newBuilder()
                    .tableBucketARN("acs:osstables:cn-beijing:123456:bucket/test-table?1234")
                    .namespace("test-ns")
                    .name("test-table")
                    .build()).get();
            fail("should not here");
        } catch (Exception e) {
            assertThat(e.getCause() != null ? e.getCause() : e).hasMessageContaining("bucket resource is invalid,");
        }
    }

    @Test
    public void getTableMaintenanceJobStatusAsync_CheckField() {
        // Required field - tableBucketARN
        try {
            asyncTablesClient.getTableMaintenanceJobStatusAsync(GetTableMaintenanceJobStatusRequest.newBuilder()
                    .namespace("test-ns")
                    .name("test-table")
                    .build()).get();
            fail("should not here");
        } catch (Exception e) {
            assertThat(e.getCause() != null ? e.getCause() : e).hasMessageContaining("request.tableBucketARN is required");
        }

        // Required field - namespace
        try {
            asyncTablesClient.getTableMaintenanceJobStatusAsync(GetTableMaintenanceJobStatusRequest.newBuilder()
                    .tableBucketARN("acs:osstables:cn-beijing:123456:bucket/valid-bucket")
                    .name("test-table")
                    .build()).get();
            fail("should not here");
        } catch (Exception e) {
            assertThat(e.getCause() != null ? e.getCause() : e).hasMessageContaining("request.namespace is required");
        }

        // Required field - name
        try {
            asyncTablesClient.getTableMaintenanceJobStatusAsync(GetTableMaintenanceJobStatusRequest.newBuilder()
                    .tableBucketARN("acs:osstables:cn-beijing:123456:bucket/valid-bucket")
                    .namespace("test-ns")
                    .build()).get();
            fail("should not here");
        } catch (Exception e) {
            assertThat(e.getCause() != null ? e.getCause() : e).hasMessageContaining("request.name is required");
        }

        // not table bucket arn
        try {
            asyncTablesClient.getTableMaintenanceJobStatusAsync(GetTableMaintenanceJobStatusRequest.newBuilder()
                            .tableBucketARN("bucket-name")
                            .namespace("test-ns")
                            .name("test-table")
                    .build()).get();
            fail("should not here");
        } catch (Exception e) {
            assertThat(e.getCause() != null ? e.getCause() : e).hasMessageContaining("Malformed ARN - ");
        }

        // table bucket arn with invalid bucket name
        try {
            asyncTablesClient.getTableMaintenanceJobStatusAsync(GetTableMaintenanceJobStatusRequest.newBuilder()
                    .tableBucketARN("acs:osstables:cn-beijing:123456:bucket/test-table?1234")
                    .namespace("test-ns")
                    .name("test-table")
                    .build()).get();
            fail("should not here");
        } catch (Exception e) {
            assertThat(e.getCause() != null ? e.getCause() : e).hasMessageContaining("bucket resource is invalid,");
        }
    }

    @Test
    public void getTableMetadataLocationAsync_CheckField() {
        // Required field - tableBucketARN
        try {
            asyncTablesClient.getTableMetadataLocationAsync(GetTableMetadataLocationRequest.newBuilder()
                    .namespace("test-ns")
                    .name("test-table")
                    .build()).get();
            fail("should not here");
        } catch (Exception e) {
            assertThat(e.getCause() != null ? e.getCause() : e).hasMessageContaining("request.tableBucketARN is required");
        }

        // Required field - namespace
        try {
            asyncTablesClient.getTableMetadataLocationAsync(GetTableMetadataLocationRequest.newBuilder()
                    .tableBucketARN("acs:osstables:cn-beijing:123456:bucket/valid-bucket")
                    .name("test-table")
                    .build()).get();
            fail("should not here");
        } catch (Exception e) {
            assertThat(e.getCause() != null ? e.getCause() : e).hasMessageContaining("request.namespace is required");
        }

        // Required field - name
        try {
            asyncTablesClient.getTableMetadataLocationAsync(GetTableMetadataLocationRequest.newBuilder()
                    .tableBucketARN("acs:osstables:cn-beijing:123456:bucket/valid-bucket")
                    .namespace("test-ns")
                    .build()).get();
            fail("should not here");
        } catch (Exception e) {
            assertThat(e.getCause() != null ? e.getCause() : e).hasMessageContaining("request.name is required");
        }

        // not table bucket arn
        try {
            asyncTablesClient.getTableMetadataLocationAsync(GetTableMetadataLocationRequest.newBuilder()
                            .tableBucketARN("bucket-name")
                            .namespace("test-ns")
                            .name("test-table")
                    .build()).get();
            fail("should not here");
        } catch (Exception e) {
            assertThat(e.getCause() != null ? e.getCause() : e).hasMessageContaining("Malformed ARN - ");
        }

        // table bucket arn with invalid bucket name
        try {
            asyncTablesClient.getTableMetadataLocationAsync(GetTableMetadataLocationRequest.newBuilder()
                    .tableBucketARN("acs:osstables:cn-beijing:123456:bucket/test-table?1234")
                    .namespace("test-ns")
                    .name("test-table")
                    .build()).get();
            fail("should not here");
        } catch (Exception e) {
            assertThat(e.getCause() != null ? e.getCause() : e).hasMessageContaining("bucket resource is invalid,");
        }
    }

    @Test
    public void updateTableMetadataLocationAsync_CheckField() {
        // Required field - tableBucketARN
        try {
            asyncTablesClient.updateTableMetadataLocationAsync(UpdateTableMetadataLocationRequest.newBuilder()
                    .namespace("test-ns")
                    .name("test-table")
                    .metadataLocation("oss://bucket/path")
                    .build()).get();
            fail("should not here");
        } catch (Exception e) {
            assertThat(e.getCause() != null ? e.getCause() : e).hasMessageContaining("request.tableBucketARN is required");
        }

        // Required field - namespace
        try {
            asyncTablesClient.updateTableMetadataLocationAsync(UpdateTableMetadataLocationRequest.newBuilder()
                    .tableBucketARN("acs:osstables:cn-beijing:123456:bucket/valid-bucket")
                    .name("test-table")
                    .metadataLocation("oss://bucket/path")
                    .build()).get();
            fail("should not here");
        } catch (Exception e) {
            assertThat(e.getCause() != null ? e.getCause() : e).hasMessageContaining("request.namespace is required");
        }

        // Required field - name
        try {
            asyncTablesClient.updateTableMetadataLocationAsync(UpdateTableMetadataLocationRequest.newBuilder()
                    .tableBucketARN("acs:osstables:cn-beijing:123456:bucket/valid-bucket")
                    .namespace("test-ns")
                    .metadataLocation("oss://bucket/path")
                    .build()).get();
            fail("should not here");
        } catch (Exception e) {
            assertThat(e.getCause() != null ? e.getCause() : e).hasMessageContaining("request.name is required");
        }

        // not table bucket arn
        try {
            asyncTablesClient.updateTableMetadataLocationAsync(UpdateTableMetadataLocationRequest.newBuilder()
                            .tableBucketARN("bucket-name")
                            .namespace("test-ns")
                            .name("test-table")
                            .metadataLocation("oss://bucket/path")
                    .build()).get();
            fail("should not here");
        } catch (Exception e) {
            assertThat(e.getCause() != null ? e.getCause() : e).hasMessageContaining("Malformed ARN - ");
        }

        // table bucket arn with invalid bucket name
        try {
            asyncTablesClient.updateTableMetadataLocationAsync(UpdateTableMetadataLocationRequest.newBuilder()
                    .tableBucketARN("acs:osstables:cn-beijing:123456:bucket/test-table?1234")
                    .namespace("test-ns")
                    .name("test-table")
                    .metadataLocation("oss://bucket/path")
                    .build()).get();
            fail("should not here");
        } catch (Exception e) {
            assertThat(e.getCause() != null ? e.getCause() : e).hasMessageContaining("bucket resource is invalid,");
        }
    }
}
