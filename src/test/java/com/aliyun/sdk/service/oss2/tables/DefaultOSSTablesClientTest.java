package com.aliyun.sdk.service.oss2.tables;

import com.aliyun.sdk.service.oss2.credentials.AnonymousCredentialsProvider;
import com.aliyun.sdk.service.oss2.tables.models.*;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

public class DefaultOSSTablesClientTest {

    private static OSSTablesClient tablesClient;

    @BeforeClass
    public static void oneTimeSetUp() {
        tablesClient = OSSTablesClient.newBuilder()
                .region("cn-hangzhou")
                .credentialsProvider(new AnonymousCredentialsProvider())
                .build();
        assertNotNull(tablesClient);
    }

    // ==================== Table Bucket API ====================

    @Test
    public void createTableBucket_CheckField() {
        // CreateTableBucket doesn't require tableBucketARN - it creates the bucket
        // This API uses bodyFields for name
        // We can test with empty body - should fail validation somewhere
        try {
            tablesClient.createTableBucket(CreateTableBucketRequest.newBuilder().build());
            // If it doesn't throw, that's OK - this operation doesn't have tableBucketARN validation
        } catch (Exception e) {
            // Expected - may fail for other reasons (missing name, network, etc.)
        }
    }

    @Test
    public void getTableBucket_CheckField() {
        // Required field
        try {
            tablesClient.getTableBucket(GetTableBucketRequest.newBuilder().build());
            fail("should not here");
        } catch (Exception e) {
            assertThat(e).hasMessageContaining("request.tableBucketARN is required");
        }

        // not table bucket arn
        try {
            tablesClient.getTableBucket(GetTableBucketRequest.newBuilder()
                            .tableBucketARN("bucket-name")
                    .build());
            fail("should not here");
        } catch (Exception e) {
            assertThat(e).hasMessageContaining("Malformed ARN - ");
        }

        // table bucket arn with invalid bucket name
        try {
            tablesClient.getTableBucket(GetTableBucketRequest.newBuilder()
                    .tableBucketARN("acs:osstables:cn-beijing:123456:bucket/test-table?1234")
                    .build());
            fail("should not here");
        } catch (Exception e) {
            assertThat(e).hasMessageContaining("bucket resource is invalid,");
        }
    }

    @Test
    public void deleteTableBucket_CheckField() {
        // Required field
        try {
            tablesClient.deleteTableBucket(DeleteTableBucketRequest.newBuilder().build());
            fail("should not here");
        } catch (Exception e) {
            assertThat(e).hasMessageContaining("request.tableBucketARN is required");
        }

        // not table bucket arn
        try {
            tablesClient.deleteTableBucket(DeleteTableBucketRequest.newBuilder()
                            .tableBucketARN("bucket-name")
                    .build());
            fail("should not here");
        } catch (Exception e) {
            assertThat(e).hasMessageContaining("Malformed ARN - ");
        }

        // table bucket arn with invalid bucket name
        try {
            tablesClient.deleteTableBucket(DeleteTableBucketRequest.newBuilder()
                    .tableBucketARN("acs:osstables:cn-beijing:123456:bucket/test-table?1234")
                    .build());
            fail("should not here");
        } catch (Exception e) {
            assertThat(e).hasMessageContaining("bucket resource is invalid,");
        }
    }

    @Test
    public void listTableBuckets_CheckField() {
        // listTableBuckets has no required fields
    }

    // ==================== Namespace API ====================

    @Test
    public void createNamespace_CheckField() {
        // Required field - tableBucketARN
        try {
            tablesClient.createNamespace(CreateNamespaceRequest.newBuilder()
                    .namespace(Arrays.asList("test-ns"))
                    .build());
            fail("should not here");
        } catch (Exception e) {
            assertThat(e).hasMessageContaining("request.tableBucketARN is required");
        }

        // not table bucket arn
        try {
            tablesClient.createNamespace(CreateNamespaceRequest.newBuilder()
                    .tableBucketARN("bucket-name")
                    .namespace(Arrays.asList("test-ns"))
                    .build());
            fail("should not here");
        } catch (Exception e) {
            assertThat(e).hasMessageContaining("Malformed ARN - ");
        }

        // table bucket arn with invalid bucket name
        try {
            tablesClient.createNamespace(CreateNamespaceRequest.newBuilder()
                    .tableBucketARN("acs:osstables:cn-beijing:123456:bucket/test-table?1234")
                    .namespace(Arrays.asList("test-ns"))
                    .build());
            fail("should not here");
        } catch (Exception e) {
            assertThat(e).hasMessageContaining("bucket resource is invalid,");
        }
    }

    @Test
    public void getNamespace_CheckField() {
        // Required field - tableBucketARN
        try {
            tablesClient.getNamespace(GetNamespaceRequest.newBuilder()
                    .namespace("test-ns")
                    .build());
            fail("should not here");
        } catch (Exception e) {
            assertThat(e).hasMessageContaining("request.tableBucketARN is required");
        }

        // Required field - namespace
        try {
            tablesClient.getNamespace(GetNamespaceRequest.newBuilder()
                    .tableBucketARN("acs:osstables:cn-beijing:123456:bucket/valid-bucket")
                    .build());
            fail("should not here");
        } catch (Exception e) {
            assertThat(e).hasMessageContaining("request.namespace is required");
        }

        // not table bucket arn
        try {
            tablesClient.getNamespace(GetNamespaceRequest.newBuilder()
                    .tableBucketARN("bucket-name")
                    .namespace("test-ns")
                    .build());
            fail("should not here");
        } catch (Exception e) {
            assertThat(e).hasMessageContaining("Malformed ARN - ");
        }

        // table bucket arn with invalid bucket name
        try {
            tablesClient.getNamespace(GetNamespaceRequest.newBuilder()
                    .tableBucketARN("acs:osstables:cn-beijing:123456:bucket/test-table?1234")
                    .namespace("test-ns")
                    .build());
            fail("should not here");
        } catch (Exception e) {
            assertThat(e).hasMessageContaining("bucket resource is invalid,");
        }
    }

    @Test
    public void deleteNamespace_CheckField() {
        // Required field - tableBucketARN
        try {
            tablesClient.deleteNamespace(DeleteNamespaceRequest.newBuilder()
                    .namespace("test-ns")
                    .build());
            fail("should not here");
        } catch (Exception e) {
            assertThat(e).hasMessageContaining("request.tableBucketARN is required");
        }

        // Required field - namespace
        try {
            tablesClient.deleteNamespace(DeleteNamespaceRequest.newBuilder()
                    .tableBucketARN("acs:osstables:cn-beijing:123456:bucket/valid-bucket")
                    .build());
            fail("should not here");
        } catch (Exception e) {
            assertThat(e).hasMessageContaining("request.namespace is required");
        }

        // not table bucket arn
        try {
            tablesClient.deleteNamespace(DeleteNamespaceRequest.newBuilder()
                    .tableBucketARN("bucket-name")
                    .namespace("test-ns")
                    .build());
            fail("should not here");
        } catch (Exception e) {
            assertThat(e).hasMessageContaining("Malformed ARN - ");
        }

        // table bucket arn with invalid bucket name
        try {
            tablesClient.deleteNamespace(DeleteNamespaceRequest.newBuilder()
                    .tableBucketARN("acs:osstables:cn-beijing:123456:bucket/test-table?1234")
                    .namespace("test-ns")
                    .build());
            fail("should not here");
        } catch (Exception e) {
            assertThat(e).hasMessageContaining("bucket resource is invalid,");
        }
    }

    @Test
    public void listNamespaces_CheckField() {
        // Required field - tableBucketARN
        try {
            tablesClient.listNamespaces(ListNamespacesRequest.newBuilder().build());
            fail("should not here");
        } catch (Exception e) {
            assertThat(e).hasMessageContaining("request.tableBucketARN is required");
        }

        // not table bucket arn
        try {
            tablesClient.listNamespaces(ListNamespacesRequest.newBuilder()
                            .tableBucketARN("bucket-name")
                    .build());
            fail("should not here");
        } catch (Exception e) {
            assertThat(e).hasMessageContaining("Malformed ARN - ");
        }

        // table bucket arn with invalid bucket name
        try {
            tablesClient.listNamespaces(ListNamespacesRequest.newBuilder()
                    .tableBucketARN("acs:osstables:cn-beijing:123456:bucket/test-table?1234")
                    .build());
            fail("should not here");
        } catch (Exception e) {
            assertThat(e).hasMessageContaining("bucket resource is invalid,");
        }
    }

    // ==================== Table API ====================

    @Test
    public void createTable_CheckField() {
        // Required field - tableBucketARN
        try {
            tablesClient.createTable(CreateTableRequest.newBuilder()
                    .namespace("test-ns")
                    .build());
            fail("should not here");
        } catch (Exception e) {
            assertThat(e).hasMessageContaining("request.tableBucketARN is required");
        }

        // Required field - namespace
        try {
            tablesClient.createTable(CreateTableRequest.newBuilder()
                    .tableBucketARN("acs:osstables:cn-beijing:123456:bucket/valid-bucket")
                    .build());
            fail("should not here");
        } catch (Exception e) {
            assertThat(e).hasMessageContaining("request.namespace is required");
        }

        // not table bucket arn
        try {
            tablesClient.createTable(CreateTableRequest.newBuilder()
                    .tableBucketARN("bucket-name")
                    .namespace("test-ns")
                    .build());
            fail("should not here");
        } catch (Exception e) {
            assertThat(e).hasMessageContaining("Malformed ARN - ");
        }

        // table bucket arn with invalid bucket name
        try {
            tablesClient.createTable(CreateTableRequest.newBuilder()
                    .tableBucketARN("acs:osstables:cn-beijing:123456:bucket/test-table?1234")
                    .namespace("test-ns")
                    .build());
            fail("should not here");
        } catch (Exception e) {
            assertThat(e).hasMessageContaining("bucket resource is invalid,");
        }
    }

    @Test
    public void getTable_CheckField() {
        // GetTable can use either tableBucketARN+namespace+name OR tableArn
        // When using tableBucketARN approach, tableBucketARN is required
        // If neither tableBucketARN nor tableArn is provided, the error mentions tableArn
        try {
            tablesClient.getTable(GetTableRequest.newBuilder()
                    .namespace("test-ns")
                    .name("test-table")
                    .build());
            fail("should not here");
        } catch (Exception e) {
            // The error can be either about tableBucketARN or tableArn being required
            // depending on which validation path is taken
            assertThat(e).hasMessageContaining("required");
        }

        // not table bucket arn
        try {
            tablesClient.getTable(GetTableRequest.newBuilder()
                            .tableBucketARN("bucket-name")
                            .namespace("test-ns")
                            .name("test-table")
                    .build());
            fail("should not here");
        } catch (Exception e) {
            assertThat(e).hasMessageContaining("Malformed ARN - ");
        }

        // table bucket arn with invalid bucket name
        try {
            tablesClient.getTable(GetTableRequest.newBuilder()
                    .tableBucketARN("acs:osstables:cn-beijing:123456:bucket/test-table?1234")
                    .namespace("test-ns")
                    .name("test-table")
                    .build());
            fail("should not here");
        } catch (Exception e) {
            assertThat(e).hasMessageContaining("bucket resource is invalid,");
        }
    }

    @Test
    public void deleteTable_CheckField() {
        // Required field - tableBucketARN
        try {
            tablesClient.deleteTable(DeleteTableRequest.newBuilder()
                    .namespace("test-ns")
                    .name("test-table")
                    .build());
            fail("should not here");
        } catch (Exception e) {
            assertThat(e).hasMessageContaining("request.tableBucketARN is required");
        }

        // Required field - namespace
        try {
            tablesClient.deleteTable(DeleteTableRequest.newBuilder()
                    .tableBucketARN("acs:osstables:cn-beijing:123456:bucket/valid-bucket")
                    .name("test-table")
                    .build());
            fail("should not here");
        } catch (Exception e) {
            assertThat(e).hasMessageContaining("request.namespace is required");
        }

        // Required field - name
        try {
            tablesClient.deleteTable(DeleteTableRequest.newBuilder()
                    .tableBucketARN("acs:osstables:cn-beijing:123456:bucket/valid-bucket")
                    .namespace("test-ns")
                    .build());
            fail("should not here");
        } catch (Exception e) {
            assertThat(e).hasMessageContaining("request.name is required");
        }

        // not table bucket arn
        try {
            tablesClient.deleteTable(DeleteTableRequest.newBuilder()
                    .tableBucketARN("bucket-name")
                    .namespace("test-ns")
                    .name("test-table")
                    .build());
            fail("should not here");
        } catch (Exception e) {
            assertThat(e).hasMessageContaining("Malformed ARN - ");
        }

        // table bucket arn with invalid bucket name
        try {
            tablesClient.deleteTable(DeleteTableRequest.newBuilder()
                    .tableBucketARN("acs:osstables:cn-beijing:123456:bucket/test-table?1234")
                    .namespace("test-ns")
                    .name("test-table")
                    .build());
            fail("should not here");
        } catch (Exception e) {
            assertThat(e).hasMessageContaining("bucket resource is invalid,");
        }
    }

    @Test
    public void listTables_CheckField() {
        // Required field - tableBucketARN
        try {
            tablesClient.listTables(ListTablesRequest.newBuilder().build());
            fail("should not here");
        } catch (Exception e) {
            assertThat(e).hasMessageContaining("request.tableBucketARN is required");
        }

        // Required field - namespace
        try {
            tablesClient.listTables(ListTablesRequest.newBuilder()
                    .tableBucketARN("acs:osstables:cn-beijing:123456:bucket/valid-bucket")
                    .build());
            fail("should not here");
        } catch (Exception e) {
            assertThat(e).hasMessageContaining("request.namespace is required");
        }

        // not table bucket arn
        try {
            tablesClient.listTables(ListTablesRequest.newBuilder()
                            .tableBucketARN("bucket-name")
                            .namespace("test-ns")
                    .build());
            fail("should not here");
        } catch (Exception e) {
            assertThat(e).hasMessageContaining("Malformed ARN - ");
        }

        // table bucket arn with invalid bucket name
        try {
            tablesClient.listTables(ListTablesRequest.newBuilder()
                    .tableBucketARN("acs:osstables:cn-beijing:123456:bucket/test-table?1234")
                    .namespace("test-ns")
                    .build());
            fail("should not here");
        } catch (Exception e) {
            assertThat(e).hasMessageContaining("bucket resource is invalid,");
        }
    }

    @Test
    public void renameTable_CheckField() {
        // Required field - tableBucketARN
        try {
            tablesClient.renameTable(RenameTableRequest.newBuilder()
                    .namespace("test-ns")
                    .name("old-table")
                    .newName("new-table")
                    .build());
            fail("should not here");
        } catch (Exception e) {
            assertThat(e).hasMessageContaining("request.tableBucketARN is required");
        }

        // Required field - namespace
        try {
            tablesClient.renameTable(RenameTableRequest.newBuilder()
                    .tableBucketARN("acs:osstables:cn-beijing:123456:bucket/valid-bucket")
                    .name("old-table")
                    .newName("new-table")
                    .build());
            fail("should not here");
        } catch (Exception e) {
            assertThat(e).hasMessageContaining("request.namespace is required");
        }

        // Required field - name
        try {
            tablesClient.renameTable(RenameTableRequest.newBuilder()
                    .tableBucketARN("acs:osstables:cn-beijing:123456:bucket/valid-bucket")
                    .namespace("test-ns")
                    .newName("new-table")
                    .build());
            fail("should not here");
        } catch (Exception e) {
            assertThat(e).hasMessageContaining("request.name is required");
        }

        // not table bucket arn
        try {
            tablesClient.renameTable(RenameTableRequest.newBuilder()
                    .tableBucketARN("bucket-name")
                    .namespace("test-ns")
                    .name("old-table")
                    .newName("new-table")
                    .build());
            fail("should not here");
        } catch (Exception e) {
            assertThat(e).hasMessageContaining("Malformed ARN - ");
        }

        // table bucket arn with invalid bucket name
        try {
            tablesClient.renameTable(RenameTableRequest.newBuilder()
                    .tableBucketARN("acs:osstables:cn-beijing:123456:bucket/test-table?1234")
                    .namespace("test-ns")
                    .name("old-table")
                    .newName("new-table")
                    .build());
            fail("should not here");
        } catch (Exception e) {
            assertThat(e).hasMessageContaining("bucket resource is invalid,");
        }
    }

    // ==================== Table Bucket Config API ====================

    @Test
    public void putTableBucketEncryption_CheckField() {
        // Required field - tableBucketARN
        try {
            tablesClient.putTableBucketEncryption(PutTableBucketEncryptionRequest.newBuilder().build());
            fail("should not here");
        } catch (Exception e) {
            assertThat(e).hasMessageContaining("request.tableBucketARN is required");
        }

        // not table bucket arn
        try {
            tablesClient.putTableBucketEncryption(PutTableBucketEncryptionRequest.newBuilder()
                            .tableBucketARN("bucket-name")
                    .build());
            fail("should not here");
        } catch (Exception e) {
            assertThat(e).hasMessageContaining("Malformed ARN - ");
        }

        // table bucket arn with invalid bucket name
        try {
            tablesClient.putTableBucketEncryption(PutTableBucketEncryptionRequest.newBuilder()
                    .tableBucketARN("acs:osstables:cn-beijing:123456:bucket/test-table?1234")
                    .build());
            fail("should not here");
        } catch (Exception e) {
            assertThat(e).hasMessageContaining("bucket resource is invalid,");
        }
    }

    @Test
    public void getTableBucketEncryption_CheckField() {
        // Required field - tableBucketARN
        try {
            tablesClient.getTableBucketEncryption(GetTableBucketEncryptionRequest.newBuilder().build());
            fail("should not here");
        } catch (Exception e) {
            assertThat(e).hasMessageContaining("request.tableBucketARN is required");
        }

        // not table bucket arn
        try {
            tablesClient.getTableBucketEncryption(GetTableBucketEncryptionRequest.newBuilder()
                            .tableBucketARN("bucket-name")
                    .build());
            fail("should not here");
        } catch (Exception e) {
            assertThat(e).hasMessageContaining("Malformed ARN - ");
        }

        // table bucket arn with invalid bucket name
        try {
            tablesClient.getTableBucketEncryption(GetTableBucketEncryptionRequest.newBuilder()
                    .tableBucketARN("acs:osstables:cn-beijing:123456:bucket/test-table?1234")
                    .build());
            fail("should not here");
        } catch (Exception e) {
            assertThat(e).hasMessageContaining("bucket resource is invalid,");
        }
    }

    @Test
    public void deleteTableBucketEncryption_CheckField() {
        // Required field - tableBucketARN
        try {
            tablesClient.deleteTableBucketEncryption(DeleteTableBucketEncryptionRequest.newBuilder().build());
            fail("should not here");
        } catch (Exception e) {
            assertThat(e).hasMessageContaining("request.tableBucketARN is required");
        }

        // not table bucket arn
        try {
            tablesClient.deleteTableBucketEncryption(DeleteTableBucketEncryptionRequest.newBuilder()
                            .tableBucketARN("bucket-name")
                    .build());
            fail("should not here");
        } catch (Exception e) {
            assertThat(e).hasMessageContaining("Malformed ARN - ");
        }

        // table bucket arn with invalid bucket name
        try {
            tablesClient.deleteTableBucketEncryption(DeleteTableBucketEncryptionRequest.newBuilder()
                    .tableBucketARN("acs:osstables:cn-beijing:123456:bucket/test-table?1234")
                    .build());
            fail("should not here");
        } catch (Exception e) {
            assertThat(e).hasMessageContaining("bucket resource is invalid,");
        }
    }

    @Test
    public void putTableBucketPolicy_CheckField() {
        // Required field - tableBucketARN
        try {
            tablesClient.putTableBucketPolicy(PutTableBucketPolicyRequest.newBuilder().build());
            fail("should not here");
        } catch (Exception e) {
            assertThat(e).hasMessageContaining("request.tableBucketARN is required");
        }

        // not table bucket arn
        try {
            tablesClient.putTableBucketPolicy(PutTableBucketPolicyRequest.newBuilder()
                            .tableBucketARN("bucket-name")
                    .build());
            fail("should not here");
        } catch (Exception e) {
            assertThat(e).hasMessageContaining("Malformed ARN - ");
        }

        // table bucket arn with invalid bucket name
        try {
            tablesClient.putTableBucketPolicy(PutTableBucketPolicyRequest.newBuilder()
                    .tableBucketARN("acs:osstables:cn-beijing:123456:bucket/test-table?1234")
                    .build());
            fail("should not here");
        } catch (Exception e) {
            assertThat(e).hasMessageContaining("bucket resource is invalid,");
        }
    }

    @Test
    public void getTableBucketPolicy_CheckField() {
        // Required field - tableBucketARN
        try {
            tablesClient.getTableBucketPolicy(GetTableBucketPolicyRequest.newBuilder().build());
            fail("should not here");
        } catch (Exception e) {
            assertThat(e).hasMessageContaining("request.tableBucketARN is required");
        }

        // not table bucket arn
        try {
            tablesClient.getTableBucketPolicy(GetTableBucketPolicyRequest.newBuilder()
                            .tableBucketARN("bucket-name")
                    .build());
            fail("should not here");
        } catch (Exception e) {
            assertThat(e).hasMessageContaining("Malformed ARN - ");
        }

        // table bucket arn with invalid bucket name
        try {
            tablesClient.getTableBucketPolicy(GetTableBucketPolicyRequest.newBuilder()
                    .tableBucketARN("acs:osstables:cn-beijing:123456:bucket/test-table?1234")
                    .build());
            fail("should not here");
        } catch (Exception e) {
            assertThat(e).hasMessageContaining("bucket resource is invalid,");
        }
    }

    @Test
    public void deleteTableBucketPolicy_CheckField() {
        // Required field - tableBucketARN
        try {
            tablesClient.deleteTableBucketPolicy(DeleteTableBucketPolicyRequest.newBuilder().build());
            fail("should not here");
        } catch (Exception e) {
            assertThat(e).hasMessageContaining("request.tableBucketARN is required");
        }

        // not table bucket arn
        try {
            tablesClient.deleteTableBucketPolicy(DeleteTableBucketPolicyRequest.newBuilder()
                            .tableBucketARN("bucket-name")
                    .build());
            fail("should not here");
        } catch (Exception e) {
            assertThat(e).hasMessageContaining("Malformed ARN - ");
        }

        // table bucket arn with invalid bucket name
        try {
            tablesClient.deleteTableBucketPolicy(DeleteTableBucketPolicyRequest.newBuilder()
                    .tableBucketARN("acs:osstables:cn-beijing:123456:bucket/test-table?1234")
                    .build());
            fail("should not here");
        } catch (Exception e) {
            assertThat(e).hasMessageContaining("bucket resource is invalid,");
        }
    }

    @Test
    public void putTableBucketMaintenanceConfiguration_CheckField() {
        // Required field - tableBucketARN
        try {
            tablesClient.putTableBucketMaintenanceConfiguration(PutTableBucketMaintenanceConfigurationRequest.newBuilder().build());
            fail("should not here");
        } catch (Exception e) {
            assertThat(e).hasMessageContaining("request.tableBucketARN is required");
        }

        // not table bucket arn
        try {
            tablesClient.putTableBucketMaintenanceConfiguration(PutTableBucketMaintenanceConfigurationRequest.newBuilder()
                            .tableBucketARN("bucket-name")
                            .type("full")
                    .build());
            fail("should not here");
        } catch (Exception e) {
            assertThat(e).hasMessageContaining("Malformed ARN - ");
        }

        // table bucket arn with invalid bucket name
        try {
            tablesClient.putTableBucketMaintenanceConfiguration(PutTableBucketMaintenanceConfigurationRequest.newBuilder()
                    .tableBucketARN("acs:osstables:cn-beijing:123456:bucket/test-table?1234")
                    .type("full")
                    .build());
            fail("should not here");
        } catch (Exception e) {
            assertThat(e).hasMessageContaining("bucket resource is invalid,");
        }
    }

    @Test
    public void getTableBucketMaintenanceConfiguration_CheckField() {
        // Required field - tableBucketARN
        try {
            tablesClient.getTableBucketMaintenanceConfiguration(GetTableBucketMaintenanceConfigurationRequest.newBuilder().build());
            fail("should not here");
        } catch (Exception e) {
            assertThat(e).hasMessageContaining("request.tableBucketARN is required");
        }

        // not table bucket arn
        try {
            tablesClient.getTableBucketMaintenanceConfiguration(GetTableBucketMaintenanceConfigurationRequest.newBuilder()
                            .tableBucketARN("bucket-name")
                    .build());
            fail("should not here");
        } catch (Exception e) {
            assertThat(e).hasMessageContaining("Malformed ARN - ");
        }

        // table bucket arn with invalid bucket name
        try {
            tablesClient.getTableBucketMaintenanceConfiguration(GetTableBucketMaintenanceConfigurationRequest.newBuilder()
                    .tableBucketARN("acs:osstables:cn-beijing:123456:bucket/test-table?1234")
                    .build());
            fail("should not here");
        } catch (Exception e) {
            assertThat(e).hasMessageContaining("bucket resource is invalid,");
        }
    }

    // ==================== Table Config API ====================

    @Test
    public void getTableEncryption_CheckField() {
        // Required field - tableBucketARN
        try {
            tablesClient.getTableEncryption(GetTableEncryptionRequest.newBuilder()
                    .namespace("test-ns")
                    .name("test-table")
                    .build());
            fail("should not here");
        } catch (Exception e) {
            assertThat(e).hasMessageContaining("request.tableBucketARN is required");
        }

        // Required field - namespace
        try {
            tablesClient.getTableEncryption(GetTableEncryptionRequest.newBuilder()
                    .tableBucketARN("acs:osstables:cn-beijing:123456:bucket/valid-bucket")
                    .name("test-table")
                    .build());
            fail("should not here");
        } catch (Exception e) {
            assertThat(e).hasMessageContaining("request.namespace is required");
        }

        // Required field - name
        try {
            tablesClient.getTableEncryption(GetTableEncryptionRequest.newBuilder()
                    .tableBucketARN("acs:osstables:cn-beijing:123456:bucket/valid-bucket")
                    .namespace("test-ns")
                    .build());
            fail("should not here");
        } catch (Exception e) {
            assertThat(e).hasMessageContaining("request.name is required");
        }

        // not table bucket arn
        try {
            tablesClient.getTableEncryption(GetTableEncryptionRequest.newBuilder()
                    .tableBucketARN("bucket-name")
                    .namespace("test-ns")
                    .name("test-table")
                    .build());
            fail("should not here");
        } catch (Exception e) {
            assertThat(e).hasMessageContaining("Malformed ARN - ");
        }

        // table bucket arn with invalid bucket name
        try {
            tablesClient.getTableEncryption(GetTableEncryptionRequest.newBuilder()
                    .tableBucketARN("acs:osstables:cn-beijing:123456:bucket/test-table?1234")
                    .namespace("test-ns")
                    .name("test-table")
                    .build());
            fail("should not here");
        } catch (Exception e) {
            assertThat(e).hasMessageContaining("bucket resource is invalid,");
        }
    }

    @Test
    public void putTablePolicy_CheckField() {
        // Required field - tableBucketARN
        try {
            tablesClient.putTablePolicy(PutTablePolicyRequest.newBuilder()
                    .namespace("test-ns")
                    .name("test-table")
                    .build());
            fail("should not here");
        } catch (Exception e) {
            assertThat(e).hasMessageContaining("request.tableBucketARN is required");
        }

        // Required field - namespace
        try {
            tablesClient.putTablePolicy(PutTablePolicyRequest.newBuilder()
                    .tableBucketARN("acs:osstables:cn-beijing:123456:bucket/valid-bucket")
                    .name("test-table")
                    .build());
            fail("should not here");
        } catch (Exception e) {
            assertThat(e).hasMessageContaining("request.namespace is required");
        }

        // Required field - name
        try {
            tablesClient.putTablePolicy(PutTablePolicyRequest.newBuilder()
                    .tableBucketARN("acs:osstables:cn-beijing:123456:bucket/valid-bucket")
                    .namespace("test-ns")
                    .build());
            fail("should not here");
        } catch (Exception e) {
            assertThat(e).hasMessageContaining("request.name is required");
        }

        // not table bucket arn
        try {
            tablesClient.putTablePolicy(PutTablePolicyRequest.newBuilder()
                    .tableBucketARN("bucket-name")
                    .namespace("test-ns")
                    .name("test-table")
                    .build());
            fail("should not here");
        } catch (Exception e) {
            assertThat(e).hasMessageContaining("Malformed ARN - ");
        }

        // table bucket arn with invalid bucket name
        try {
            tablesClient.putTablePolicy(PutTablePolicyRequest.newBuilder()
                    .tableBucketARN("acs:osstables:cn-beijing:123456:bucket/test-table?1234")
                    .namespace("test-ns")
                    .name("test-table")
                    .build());
            fail("should not here");
        } catch (Exception e) {
            assertThat(e).hasMessageContaining("bucket resource is invalid,");
        }
    }

    @Test
    public void getTablePolicy_CheckField() {
        // Required field - tableBucketARN
        try {
            tablesClient.getTablePolicy(GetTablePolicyRequest.newBuilder()
                    .namespace("test-ns")
                    .name("test-table")
                    .build());
            fail("should not here");
        } catch (Exception e) {
            assertThat(e).hasMessageContaining("request.tableBucketARN is required");
        }

        // Required field - namespace
        try {
            tablesClient.getTablePolicy(GetTablePolicyRequest.newBuilder()
                    .tableBucketARN("acs:osstables:cn-beijing:123456:bucket/valid-bucket")
                    .name("test-table")
                    .build());
            fail("should not here");
        } catch (Exception e) {
            assertThat(e).hasMessageContaining("request.namespace is required");
        }

        // Required field - name
        try {
            tablesClient.getTablePolicy(GetTablePolicyRequest.newBuilder()
                    .tableBucketARN("acs:osstables:cn-beijing:123456:bucket/valid-bucket")
                    .namespace("test-ns")
                    .build());
            fail("should not here");
        } catch (Exception e) {
            assertThat(e).hasMessageContaining("request.name is required");
        }

        // not table bucket arn
        try {
            tablesClient.getTablePolicy(GetTablePolicyRequest.newBuilder()
                    .tableBucketARN("bucket-name")
                    .namespace("test-ns")
                    .name("test-table")
                    .build());
            fail("should not here");
        } catch (Exception e) {
            assertThat(e).hasMessageContaining("Malformed ARN - ");
        }

        // table bucket arn with invalid bucket name
        try {
            tablesClient.getTablePolicy(GetTablePolicyRequest.newBuilder()
                    .tableBucketARN("acs:osstables:cn-beijing:123456:bucket/test-table?1234")
                    .namespace("test-ns")
                    .name("test-table")
                    .build());
            fail("should not here");
        } catch (Exception e) {
            assertThat(e).hasMessageContaining("bucket resource is invalid,");
        }
    }

    @Test
    public void deleteTablePolicy_CheckField() {
        // Required field - tableBucketARN
        try {
            tablesClient.deleteTablePolicy(DeleteTablePolicyRequest.newBuilder()
                    .namespace("test-ns")
                    .name("test-table")
                    .build());
            fail("should not here");
        } catch (Exception e) {
            assertThat(e).hasMessageContaining("request.tableBucketARN is required");
        }

        // Required field - namespace
        try {
            tablesClient.deleteTablePolicy(DeleteTablePolicyRequest.newBuilder()
                    .tableBucketARN("acs:osstables:cn-beijing:123456:bucket/valid-bucket")
                    .name("test-table")
                    .build());
            fail("should not here");
        } catch (Exception e) {
            assertThat(e).hasMessageContaining("request.namespace is required");
        }

        // Required field - name
        try {
            tablesClient.deleteTablePolicy(DeleteTablePolicyRequest.newBuilder()
                    .tableBucketARN("acs:osstables:cn-beijing:123456:bucket/valid-bucket")
                    .namespace("test-ns")
                    .build());
            fail("should not here");
        } catch (Exception e) {
            assertThat(e).hasMessageContaining("request.name is required");
        }

        // not table bucket arn
        try {
            tablesClient.deleteTablePolicy(DeleteTablePolicyRequest.newBuilder()
                    .tableBucketARN("bucket-name")
                    .namespace("test-ns")
                    .name("test-table")
                    .build());
            fail("should not here");
        } catch (Exception e) {
            assertThat(e).hasMessageContaining("Malformed ARN - ");
        }

        // table bucket arn with invalid bucket name
        try {
            tablesClient.deleteTablePolicy(DeleteTablePolicyRequest.newBuilder()
                    .tableBucketARN("acs:osstables:cn-beijing:123456:bucket/test-table?1234")
                    .namespace("test-ns")
                    .name("test-table")
                    .build());
            fail("should not here");
        } catch (Exception e) {
            assertThat(e).hasMessageContaining("bucket resource is invalid,");
        }
    }

    @Test
    public void putTableMaintenanceConfiguration_CheckField() {
        // Required field - tableBucketARN
        try {
            tablesClient.putTableMaintenanceConfiguration(PutTableMaintenanceConfigurationRequest.newBuilder()
                    .namespace("test-ns")
                    .name("test-table")
                    .build());
            fail("should not here");
        } catch (Exception e) {
            assertThat(e).hasMessageContaining("request.tableBucketARN is required");
        }

        // Required field - namespace
        try {
            tablesClient.putTableMaintenanceConfiguration(PutTableMaintenanceConfigurationRequest.newBuilder()
                    .tableBucketARN("acs:osstables:cn-beijing:123456:bucket/valid-bucket")
                    .name("test-table")
                    .build());
            fail("should not here");
        } catch (Exception e) {
            assertThat(e).hasMessageContaining("request.namespace is required");
        }

        // Required field - name
        try {
            tablesClient.putTableMaintenanceConfiguration(PutTableMaintenanceConfigurationRequest.newBuilder()
                    .tableBucketARN("acs:osstables:cn-beijing:123456:bucket/valid-bucket")
                    .namespace("test-ns")
                    .build());
            fail("should not here");
        } catch (Exception e) {
            assertThat(e).hasMessageContaining("request.name is required");
        }

        // not table bucket arn
        try {
            tablesClient.putTableMaintenanceConfiguration(PutTableMaintenanceConfigurationRequest.newBuilder()
                    .tableBucketARN("bucket-name")
                    .namespace("test-ns")
                    .name("test-table")
                    .type("full")
                    .build());
            fail("should not here");
        } catch (Exception e) {
            assertThat(e).hasMessageContaining("Malformed ARN - ");
        }

        // table bucket arn with invalid bucket name
        try {
            tablesClient.putTableMaintenanceConfiguration(PutTableMaintenanceConfigurationRequest.newBuilder()
                    .tableBucketARN("acs:osstables:cn-beijing:123456:bucket/test-table?1234")
                    .namespace("test-ns")
                    .name("test-table")
                    .type("full")
                    .build());
            fail("should not here");
        } catch (Exception e) {
            assertThat(e).hasMessageContaining("bucket resource is invalid,");
        }
    }

    @Test
    public void getTableMaintenanceConfiguration_CheckField() {
        // Required field - tableBucketARN
        try {
            tablesClient.getTableMaintenanceConfiguration(GetTableMaintenanceConfigurationRequest.newBuilder()
                    .namespace("test-ns")
                    .name("test-table")
                    .build());
            fail("should not here");
        } catch (Exception e) {
            assertThat(e).hasMessageContaining("request.tableBucketARN is required");
        }

        // Required field - namespace
        try {
            tablesClient.getTableMaintenanceConfiguration(GetTableMaintenanceConfigurationRequest.newBuilder()
                    .tableBucketARN("acs:osstables:cn-beijing:123456:bucket/valid-bucket")
                    .name("test-table")
                    .build());
            fail("should not here");
        } catch (Exception e) {
            assertThat(e).hasMessageContaining("request.namespace is required");
        }

        // Required field - name
        try {
            tablesClient.getTableMaintenanceConfiguration(GetTableMaintenanceConfigurationRequest.newBuilder()
                    .tableBucketARN("acs:osstables:cn-beijing:123456:bucket/valid-bucket")
                    .namespace("test-ns")
                    .build());
            fail("should not here");
        } catch (Exception e) {
            assertThat(e).hasMessageContaining("request.name is required");
        }

        // not table bucket arn
        try {
            tablesClient.getTableMaintenanceConfiguration(GetTableMaintenanceConfigurationRequest.newBuilder()
                    .tableBucketARN("bucket-name")
                    .namespace("test-ns")
                    .name("test-table")
                    .build());
            fail("should not here");
        } catch (Exception e) {
            assertThat(e).hasMessageContaining("Malformed ARN - ");
        }

        // table bucket arn with invalid bucket name
        try {
            tablesClient.getTableMaintenanceConfiguration(GetTableMaintenanceConfigurationRequest.newBuilder()
                    .tableBucketARN("acs:osstables:cn-beijing:123456:bucket/test-table?1234")
                    .namespace("test-ns")
                    .name("test-table")
                    .build());
            fail("should not here");
        } catch (Exception e) {
            assertThat(e).hasMessageContaining("bucket resource is invalid,");
        }
    }

    @Test
    public void getTableMaintenanceJobStatus_CheckField() {
        // Required field - tableBucketARN
        try {
            tablesClient.getTableMaintenanceJobStatus(GetTableMaintenanceJobStatusRequest.newBuilder()
                    .namespace("test-ns")
                    .name("test-table")
                    .build());
            fail("should not here");
        } catch (Exception e) {
            assertThat(e).hasMessageContaining("request.tableBucketARN is required");
        }

        // Required field - namespace
        try {
            tablesClient.getTableMaintenanceJobStatus(GetTableMaintenanceJobStatusRequest.newBuilder()
                    .tableBucketARN("acs:osstables:cn-beijing:123456:bucket/valid-bucket")
                    .name("test-table")
                    .build());
            fail("should not here");
        } catch (Exception e) {
            assertThat(e).hasMessageContaining("request.namespace is required");
        }

        // Required field - name
        try {
            tablesClient.getTableMaintenanceJobStatus(GetTableMaintenanceJobStatusRequest.newBuilder()
                    .tableBucketARN("acs:osstables:cn-beijing:123456:bucket/valid-bucket")
                    .namespace("test-ns")
                    .build());
            fail("should not here");
        } catch (Exception e) {
            assertThat(e).hasMessageContaining("request.name is required");
        }

        // not table bucket arn
        try {
            tablesClient.getTableMaintenanceJobStatus(GetTableMaintenanceJobStatusRequest.newBuilder()
                    .tableBucketARN("bucket-name")
                    .namespace("test-ns")
                    .name("test-table")
                    .build());
            fail("should not here");
        } catch (Exception e) {
            assertThat(e).hasMessageContaining("Malformed ARN - ");
        }

        // table bucket arn with invalid bucket name
        try {
            tablesClient.getTableMaintenanceJobStatus(GetTableMaintenanceJobStatusRequest.newBuilder()
                    .tableBucketARN("acs:osstables:cn-beijing:123456:bucket/test-table?1234")
                    .namespace("test-ns")
                    .name("test-table")
                    .build());
            fail("should not here");
        } catch (Exception e) {
            assertThat(e).hasMessageContaining("bucket resource is invalid,");
        }
    }

    @Test
    public void getTableMetadataLocation_CheckField() {
        // Required field - tableBucketARN
        try {
            tablesClient.getTableMetadataLocation(GetTableMetadataLocationRequest.newBuilder()
                    .namespace("test-ns")
                    .name("test-table")
                    .build());
            fail("should not here");
        } catch (Exception e) {
            assertThat(e).hasMessageContaining("request.tableBucketARN is required");
        }

        // Required field - namespace
        try {
            tablesClient.getTableMetadataLocation(GetTableMetadataLocationRequest.newBuilder()
                    .tableBucketARN("acs:osstables:cn-beijing:123456:bucket/valid-bucket")
                    .name("test-table")
                    .build());
            fail("should not here");
        } catch (Exception e) {
            assertThat(e).hasMessageContaining("request.namespace is required");
        }

        // Required field - name
        try {
            tablesClient.getTableMetadataLocation(GetTableMetadataLocationRequest.newBuilder()
                    .tableBucketARN("acs:osstables:cn-beijing:123456:bucket/valid-bucket")
                    .namespace("test-ns")
                    .build());
            fail("should not here");
        } catch (Exception e) {
            assertThat(e).hasMessageContaining("request.name is required");
        }

        // not table bucket arn
        try {
            tablesClient.getTableMetadataLocation(GetTableMetadataLocationRequest.newBuilder()
                    .tableBucketARN("bucket-name")
                    .namespace("test-ns")
                    .name("test-table")
                    .build());
            fail("should not here");
        } catch (Exception e) {
            assertThat(e).hasMessageContaining("Malformed ARN - ");
        }

        // table bucket arn with invalid bucket name
        try {
            tablesClient.getTableMetadataLocation(GetTableMetadataLocationRequest.newBuilder()
                    .tableBucketARN("acs:osstables:cn-beijing:123456:bucket/test-table?1234")
                    .namespace("test-ns")
                    .name("test-table")
                    .build());
            fail("should not here");
        } catch (Exception e) {
            assertThat(e).hasMessageContaining("bucket resource is invalid,");
        }
    }

    @Test
    public void updateTableMetadataLocation_CheckField() {
        // Required field - tableBucketARN
        try {
            tablesClient.updateTableMetadataLocation(UpdateTableMetadataLocationRequest.newBuilder()
                    .namespace("test-ns")
                    .name("test-table")
                    .build());
            fail("should not here");
        } catch (Exception e) {
            assertThat(e).hasMessageContaining("request.tableBucketARN is required");
        }

        // Required field - namespace
        try {
            tablesClient.updateTableMetadataLocation(UpdateTableMetadataLocationRequest.newBuilder()
                    .tableBucketARN("acs:osstables:cn-beijing:123456:bucket/valid-bucket")
                    .name("test-table")
                    .build());
            fail("should not here");
        } catch (Exception e) {
            assertThat(e).hasMessageContaining("request.namespace is required");
        }

        // Required field - name
        try {
            tablesClient.updateTableMetadataLocation(UpdateTableMetadataLocationRequest.newBuilder()
                    .tableBucketARN("acs:osstables:cn-beijing:123456:bucket/valid-bucket")
                    .namespace("test-ns")
                    .build());
            fail("should not here");
        } catch (Exception e) {
            assertThat(e).hasMessageContaining("request.name is required");
        }

        // not table bucket arn
        try {
            tablesClient.updateTableMetadataLocation(UpdateTableMetadataLocationRequest.newBuilder()
                    .tableBucketARN("bucket-name")
                    .namespace("test-ns")
                    .name("test-table")
                    .build());
            fail("should not here");
        } catch (Exception e) {
            assertThat(e).hasMessageContaining("Malformed ARN - ");
        }

        // table bucket arn with invalid bucket name
        try {
            tablesClient.updateTableMetadataLocation(UpdateTableMetadataLocationRequest.newBuilder()
                    .tableBucketARN("acs:osstables:cn-beijing:123456:bucket/test-table?1234")
                    .namespace("test-ns")
                    .name("test-table")
                    .build());
            fail("should not here");
        } catch (Exception e) {
            assertThat(e).hasMessageContaining("bucket resource is invalid,");
        }
    }

}
