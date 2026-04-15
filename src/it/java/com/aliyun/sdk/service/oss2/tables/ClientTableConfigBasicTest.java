package com.aliyun.sdk.service.oss2.tables;

import com.aliyun.sdk.service.oss2.tables.models.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


public class ClientTableConfigBasicTest extends TestBaseTables {

    @Test
    public void testGetTableMetadataLocationLifecycle() {
        OSSTablesClient client = getTablesClient();
        String namespaceName = "test_ns_" + System.currentTimeMillis();
        String tableName = "test_table_" + System.currentTimeMillis();
        String bucketName = genTableBucketName();
        String tableBucketARN = null;

        try {
            // 1. Create a table bucket
            CreateTableBucketResult createBucketResult = client.createTableBucket(
                    CreateTableBucketRequest.newBuilder()
                            .name(bucketName)
                            .build());

            Assert.assertNotNull(createBucketResult);
            Assert.assertEquals(200, createBucketResult.statusCode());
            Assert.assertNotNull(createBucketResult.arn());
            System.out.printf("Successfully created table bucket with ARN: %s%n", createBucketResult.arn());

            tableBucketARN = createBucketResult.arn();

            // 2. Create a namespace
            CreateNamespaceResult createNsResult = client.createNamespace(
                    CreateNamespaceRequest.newBuilder()
                            .tableBucketARN(tableBucketARN)
                            .namespace(java.util.Arrays.asList(namespaceName))
                            .build());

            Assert.assertNotNull(createNsResult);
            Assert.assertEquals(200, createNsResult.statusCode());

            // 3. Create a table
            List<SchemaField> fields = new ArrayList<>();
            fields.add(SchemaField.newBuilder().name("id").type("long").required(true).build());
            fields.add(SchemaField.newBuilder().name("data").type("string").required(false).build());
            IcebergSchema icebergSchema = IcebergSchema.newBuilder().fields(fields).build();
            IcebergMetadata icebergMetadata = IcebergMetadata.newBuilder().schema(icebergSchema).build();
            TableMetadata metadata = TableMetadata.newBuilder().iceberg(icebergMetadata).build();

            CreateTableResult createTableResult = client.createTable(
                    CreateTableRequest.newBuilder()
                            .tableBucketARN(tableBucketARN)
                            .namespace(namespaceName)
                            .name(tableName)
                            .format("ICEBERG")
                            .metadata(metadata)
                            .build());

            Assert.assertNotNull(createTableResult);
            Assert.assertEquals(200, createTableResult.statusCode());
            Assert.assertNotNull(createTableResult.tableARN());
            System.out.printf("Successfully created table with ARN: %s%n", createTableResult.tableARN());

            // 4. Get table metadata location
            GetTableMetadataLocationResult getMetadataResult = client.getTableMetadataLocation(
                    GetTableMetadataLocationRequest.newBuilder()
                            .tableBucketARN(tableBucketARN)
                            .namespace(namespaceName)
                            .name(tableName)
                            .build());

            Assert.assertNotNull(getMetadataResult);
            Assert.assertEquals(200, getMetadataResult.statusCode());
            Assert.assertNotNull(getMetadataResult.metadataLocation());
            Assert.assertNotNull(getMetadataResult.versionToken());
            System.out.printf("Successfully retrieved metadata location: %s%n", getMetadataResult.metadataLocation());
            System.out.printf("Version token: %s%n", getMetadataResult.versionToken());
            System.out.printf("Warehouse location: %s%n", getMetadataResult.warehouseLocation());

        } finally {
            // Cleanup: Delete the table
            if (tableBucketARN != null) {
                try {
                    client.deleteTable(DeleteTableRequest.newBuilder()
                            .tableBucketARN(tableBucketARN)
                            .namespace(namespaceName)
                            .name(tableName)
                            .build());
                    System.out.printf("Successfully deleted table%n");
                } catch (Exception e) {
                    System.out.printf("Cleanup: Could not delete table: %s%n", e.getMessage());
                }

                // Cleanup: Delete the namespace
                try {
                    client.deleteNamespace(DeleteNamespaceRequest.newBuilder()
                            .tableBucketARN(tableBucketARN)
                            .namespace(namespaceName)
                            .build());
                    System.out.printf("Successfully deleted namespace%n");
                } catch (Exception e) {
                    System.out.printf("Cleanup: Could not delete namespace: %s%n", e.getMessage());
                }

                // Cleanup: Delete the table bucket
                try {
                    DeleteTableBucketResult deleteResult = client.deleteTableBucket(
                            DeleteTableBucketRequest.newBuilder()
                                    .tableBucketARN(tableBucketARN)
                                    .build());
                    Assert.assertNotNull(deleteResult);
                    Assert.assertEquals(204, deleteResult.statusCode());
                    System.out.printf("Successfully deleted table bucket%n");
                } catch (Exception e) {
                    System.out.printf("Cleanup: Could not delete table bucket: %s%n", e.getMessage());
                }
            }
        }
    }

    @Test
    public void testGetTableMetadataLocationOnNonExistentTable() {
        OSSTablesClient client = getTablesClient();

        try {
            GetTableMetadataLocationResult getResult = client.getTableMetadataLocation(
                    GetTableMetadataLocationRequest.newBuilder()
                            .tableBucketARN("acs:osstables:cn-hangzhou:123456789012:bucket/nonexistent-bucket-test-12345")
                            .namespace("nonexistent-namespace")
                            .name("nonexistent-table")
                            .build());

            Assert.fail("Expected exception when getting metadata location from non-existent table");
        } catch (Exception e) {
            Assert.assertNotNull(e);
            System.out.printf("Successfully caught expected exception for non-existent table: %s%n", e.getMessage());
        }
    }

    @Test
    public void testUpdateTableMetadataLocationLifecycle() {
        OSSTablesClient client = getTablesClient();
        String namespaceName = "test_ns_upd_" + System.currentTimeMillis();
        String tableName = "test_table_upd_" + System.currentTimeMillis();
        String bucketName = genTableBucketName();
        String tableBucketARN = null;

        try {
            CreateTableBucketResult createBucketResult = client.createTableBucket(
                    CreateTableBucketRequest.newBuilder()
                            .name(bucketName)
                            .build());

            Assert.assertNotNull(createBucketResult);
            Assert.assertEquals(200, createBucketResult.statusCode());
            Assert.assertNotNull(createBucketResult.arn());
            System.out.printf("Successfully created table bucket with ARN: %s%n", createBucketResult.arn());

            tableBucketARN = createBucketResult.arn();

            CreateNamespaceResult createNsResult = client.createNamespace(
                    CreateNamespaceRequest.newBuilder()
                            .tableBucketARN(tableBucketARN)
                            .namespace(java.util.Arrays.asList(namespaceName))
                            .build());

            Assert.assertNotNull(createNsResult);
            Assert.assertEquals(200, createNsResult.statusCode());

            List<SchemaField> fields = new ArrayList<>();
            fields.add(SchemaField.newBuilder().name("id").type("long").required(true).build());
            fields.add(SchemaField.newBuilder().name("data").type("string").required(false).build());
            IcebergSchema icebergSchema = IcebergSchema.newBuilder().fields(fields).build();
            IcebergMetadata icebergMetadata = IcebergMetadata.newBuilder().schema(icebergSchema).build();
            TableMetadata metadata = TableMetadata.newBuilder().iceberg(icebergMetadata).build();

            CreateTableResult createTableResult = client.createTable(
                    CreateTableRequest.newBuilder()
                            .tableBucketARN(tableBucketARN)
                            .namespace(namespaceName)
                            .name(tableName)
                            .format("ICEBERG")
                            .metadata(metadata)
                            .build());

            Assert.assertNotNull(createTableResult);
            Assert.assertEquals(200, createTableResult.statusCode());
            Assert.assertNotNull(createTableResult.tableARN());
            System.out.printf("Successfully created table with ARN: %s%n", createTableResult.tableARN());

            GetTableMetadataLocationResult getMetadataResult = client.getTableMetadataLocation(
                    GetTableMetadataLocationRequest.newBuilder()
                            .tableBucketARN(tableBucketARN)
                            .namespace(namespaceName)
                            .name(tableName)
                            .build());

            Assert.assertNotNull(getMetadataResult);
            Assert.assertEquals(200, getMetadataResult.statusCode());
            Assert.assertNotNull(getMetadataResult.metadataLocation());
            Assert.assertNotNull(getMetadataResult.versionToken());
            System.out.printf("Current metadata location: %s%n", getMetadataResult.metadataLocation());
            System.out.printf("Current version token: %s%n", getMetadataResult.versionToken());

            String currentVersionToken = getMetadataResult.versionToken();
            String currentMetadataLocation = getMetadataResult.metadataLocation();

            UpdateTableMetadataLocationResult updateResult = client.updateTableMetadataLocation(
                    UpdateTableMetadataLocationRequest.newBuilder()
                            .tableBucketARN(tableBucketARN)
                            .namespace(namespaceName)
                            .name(tableName)
                            .versionToken(currentVersionToken)
                            .metadataLocation(currentMetadataLocation)
                            .build());

            Assert.assertNotNull(updateResult);
            Assert.assertEquals(200, updateResult.statusCode());
            Assert.assertNotNull(updateResult.metadataLocation());
            Assert.assertNotNull(updateResult.versionToken());
            Assert.assertEquals(tableName, updateResult.name());
            Assert.assertNotNull(updateResult.namespace());
            Assert.assertTrue(updateResult.namespace().contains(namespaceName));
            System.out.printf("Successfully updated metadata location: %s%n", updateResult.metadataLocation());
            System.out.printf("New version token: %s%n", updateResult.versionToken());

        } finally {
            if (tableBucketARN != null) {
                try {
                    client.deleteTable(DeleteTableRequest.newBuilder()
                            .tableBucketARN(tableBucketARN)
                            .namespace(namespaceName)
                            .name(tableName)
                            .build());
                    System.out.printf("Successfully deleted table%n");
                } catch (Exception e) {
                    System.out.printf("Cleanup: Could not delete table: %s%n", e.getMessage());
                }

                try {
                    client.deleteNamespace(DeleteNamespaceRequest.newBuilder()
                            .tableBucketARN(tableBucketARN)
                            .namespace(namespaceName)
                            .build());
                    System.out.printf("Successfully deleted namespace%n");
                } catch (Exception e) {
                    System.out.printf("Cleanup: Could not delete namespace: %s%n", e.getMessage());
                }

                try {
                    DeleteTableBucketResult deleteResult = client.deleteTableBucket(
                            DeleteTableBucketRequest.newBuilder()
                                    .tableBucketARN(tableBucketARN)
                                    .build());
                    Assert.assertNotNull(deleteResult);
                    Assert.assertEquals(204, deleteResult.statusCode());
                    System.out.printf("Successfully deleted table bucket%n");
                } catch (Exception e) {
                    System.out.printf("Cleanup: Could not delete table bucket: %s%n", e.getMessage());
                }
            }
        }
    }

    @Test
    public void testUpdateTableMetadataLocationOnNonExistentTable() {
        OSSTablesClient client = getTablesClient();

        try {
            UpdateTableMetadataLocationResult updateResult = client.updateTableMetadataLocation(
                    UpdateTableMetadataLocationRequest.newBuilder()
                            .tableBucketARN("acs:osstables:cn-hangzhou:123456789012:bucket/nonexistent-bucket-test-12345")
                            .namespace("nonexistent-namespace")
                            .name("nonexistent-table")
                            .versionToken("invalid-token")
                            .metadataLocation("oss://data-bucket/metadata/nonexistent.metadata.json")
                            .build());

            Assert.fail("Expected exception when updating metadata location on non-existent table");
        } catch (Exception e) {
            Assert.assertNotNull(e);
            System.out.printf("Successfully caught expected exception for non-existent table: %s%n", e.getMessage());
        }
    }

    @Test
    public void testGetTableEncryptionLifecycle() {
        OSSTablesClient client = getTablesClient();
        String namespaceName = "test_ns_enc_" + System.currentTimeMillis();
        String tableName = "test_table_enc_" + System.currentTimeMillis();
        String bucketName = genTableBucketName();
        String tableBucketARN = null;

        try {
            CreateTableBucketResult createBucketResult = client.createTableBucket(
                    CreateTableBucketRequest.newBuilder()
                            .name(bucketName)
                            .build());

            Assert.assertNotNull(createBucketResult);
            Assert.assertEquals(200, createBucketResult.statusCode());
            Assert.assertNotNull(createBucketResult.arn());
            System.out.printf("Successfully created table bucket with ARN: %s%n", createBucketResult.arn());

            tableBucketARN = createBucketResult.arn();

            CreateNamespaceResult createNsResult = client.createNamespace(
                    CreateNamespaceRequest.newBuilder()
                            .tableBucketARN(tableBucketARN)
                            .namespace(java.util.Arrays.asList(namespaceName))
                            .build());

            Assert.assertNotNull(createNsResult);
            Assert.assertEquals(200, createNsResult.statusCode());

            List<SchemaField> fields = new ArrayList<>();
            fields.add(SchemaField.newBuilder().name("id").type("long").required(true).build());
            fields.add(SchemaField.newBuilder().name("data").type("string").required(false).build());
            IcebergSchema icebergSchema = IcebergSchema.newBuilder().fields(fields).build();
            IcebergMetadata icebergMetadata = IcebergMetadata.newBuilder().schema(icebergSchema).build();
            TableMetadata metadata = TableMetadata.newBuilder().iceberg(icebergMetadata).build();

            CreateTableResult createTableResult = client.createTable(
                    CreateTableRequest.newBuilder()
                            .tableBucketARN(tableBucketARN)
                            .namespace(namespaceName)
                            .name(tableName)
                            .format("ICEBERG")
                            .metadata(metadata)
                            .build());

            Assert.assertNotNull(createTableResult);
            Assert.assertEquals(200, createTableResult.statusCode());
            Assert.assertNotNull(createTableResult.tableARN());
            System.out.printf("Successfully created table with ARN: %s%n", createTableResult.tableARN());

            GetTableEncryptionResult getEncryptionResult = client.getTableEncryption(
                    GetTableEncryptionRequest.newBuilder()
                            .tableBucketARN(tableBucketARN)
                            .namespace(namespaceName)
                            .name(tableName)
                            .build());

            Assert.assertNotNull(getEncryptionResult);
            Assert.assertEquals(200, getEncryptionResult.statusCode());
            Assert.assertNotNull(getEncryptionResult.encryptionConfiguration());
            System.out.printf("Successfully retrieved table encryption, sseAlgorithm: %s%n",
                    getEncryptionResult.encryptionConfiguration().sseAlgorithm());

        } finally {
            if (tableBucketARN != null) {
                try {
                    client.deleteTable(DeleteTableRequest.newBuilder()
                            .tableBucketARN(tableBucketARN)
                            .namespace(namespaceName)
                            .name(tableName)
                            .build());
                    System.out.printf("Successfully deleted table%n");
                } catch (Exception e) {
                    System.out.printf("Cleanup: Could not delete table: %s%n", e.getMessage());
                }

                try {
                    client.deleteNamespace(DeleteNamespaceRequest.newBuilder()
                            .tableBucketARN(tableBucketARN)
                            .namespace(namespaceName)
                            .build());
                    System.out.printf("Successfully deleted namespace%n");
                } catch (Exception e) {
                    System.out.printf("Cleanup: Could not delete namespace: %s%n", e.getMessage());
                }

                try {
                    DeleteTableBucketResult deleteResult = client.deleteTableBucket(
                            DeleteTableBucketRequest.newBuilder()
                                    .tableBucketARN(tableBucketARN)
                                    .build());
                    Assert.assertNotNull(deleteResult);
                    Assert.assertEquals(204, deleteResult.statusCode());
                    System.out.printf("Successfully deleted table bucket%n");
                } catch (Exception e) {
                    System.out.printf("Cleanup: Could not delete table bucket: %s%n", e.getMessage());
                }
            }
        }
    }

    @Test
    public void testGetTableEncryptionOnNonExistentTable() {
        OSSTablesClient client = getTablesClient();
        try {
            GetTableEncryptionResult getResult = client.getTableEncryption(
                    GetTableEncryptionRequest.newBuilder()
                            .tableBucketARN("acs:osstables:cn-hangzhou:123456789012:bucket/nonexistent-bucket-test-12345")
                            .namespace("nonexistent-namespace")
                            .name("nonexistent-table")
                            .build());

            Assert.fail("Expected exception when getting encryption from non-existent table");
        } catch (Exception e) {
            Assert.assertNotNull(e);
            System.out.printf("Successfully caught expected exception for non-existent table: %s%n", e.getMessage());
        }
    }

    @Test
    public void testPutTablePolicyLifecycle() {
        OSSTablesClient client = getTablesClient();
        String namespaceName = "test_ns_pol_" + System.currentTimeMillis();
        String tableName = "test_table_pol_" + System.currentTimeMillis();
        String bucketName = genTableBucketName();
        String tableBucketARN = null;

        try {
            CreateTableBucketResult createBucketResult = client.createTableBucket(
                    CreateTableBucketRequest.newBuilder()
                            .name(bucketName)
                            .build());

            Assert.assertNotNull(createBucketResult);
            Assert.assertEquals(200, createBucketResult.statusCode());
            Assert.assertNotNull(createBucketResult.arn());
            System.out.printf("Successfully created table bucket with ARN: %s%n", createBucketResult.arn());

            tableBucketARN = createBucketResult.arn();

            CreateNamespaceResult createNsResult = client.createNamespace(
                    CreateNamespaceRequest.newBuilder()
                            .tableBucketARN(tableBucketARN)
                            .namespace(java.util.Arrays.asList(namespaceName))
                            .build());

            Assert.assertNotNull(createNsResult);
            Assert.assertEquals(200, createNsResult.statusCode());

            List<SchemaField> fields = new ArrayList<>();
            fields.add(SchemaField.newBuilder().name("id").type("long").required(true).build());
            fields.add(SchemaField.newBuilder().name("data").type("string").required(false).build());
            IcebergSchema icebergSchema = IcebergSchema.newBuilder().fields(fields).build();
            IcebergMetadata icebergMetadata = IcebergMetadata.newBuilder().schema(icebergSchema).build();
            TableMetadata metadata = TableMetadata.newBuilder().iceberg(icebergMetadata).build();

            CreateTableResult createTableResult = client.createTable(
                    CreateTableRequest.newBuilder()
                            .tableBucketARN(tableBucketARN)
                            .namespace(namespaceName)
                            .name(tableName)
                            .format("ICEBERG")
                            .metadata(metadata)
                            .build());

            Assert.assertNotNull(createTableResult);
            Assert.assertEquals(200, createTableResult.statusCode());
            Assert.assertNotNull(createTableResult.tableARN());
            System.out.printf("Successfully created table with ARN: %s%n", createTableResult.tableARN());

            String tableARN = createTableResult.tableARN();
            String resourcePolicy = "{\"Version\":\"1\",\"Statement\":[{\"Action\":[\"oss:GetTable\"],\"Effect\":\"Allow\",\"Principal\":[\"1234567890\"],\"Resource\":[\"" + tableARN + "\"]}]}";

            PutTablePolicyResult putResult = client.putTablePolicy(
                    PutTablePolicyRequest.newBuilder()
                            .tableBucketARN(tableBucketARN)
                            .namespace(namespaceName)
                            .name(tableName)
                            .resourcePolicy(resourcePolicy)
                            .build());

            Assert.assertNotNull(putResult);
            Assert.assertEquals(200, putResult.statusCode());
            System.out.printf("Successfully put table policy%n");

        } finally {
            if (tableBucketARN != null) {
                try {
                    client.deleteTable(DeleteTableRequest.newBuilder()
                            .tableBucketARN(tableBucketARN)
                            .namespace(namespaceName)
                            .name(tableName)
                            .build());
                    System.out.printf("Successfully deleted table%n");
                } catch (Exception e) {
                    System.out.printf("Cleanup: Could not delete table: %s%n", e.getMessage());
                }

                try {
                    client.deleteNamespace(DeleteNamespaceRequest.newBuilder()
                            .tableBucketARN(tableBucketARN)
                            .namespace(namespaceName)
                            .build());
                    System.out.printf("Successfully deleted namespace%n");
                } catch (Exception e) {
                    System.out.printf("Cleanup: Could not delete namespace: %s%n", e.getMessage());
                }

                try {
                    DeleteTableBucketResult deleteResult = client.deleteTableBucket(
                            DeleteTableBucketRequest.newBuilder()
                                    .tableBucketARN(tableBucketARN)
                                    .build());
                    Assert.assertNotNull(deleteResult);
                    Assert.assertEquals(204, deleteResult.statusCode());
                    System.out.printf("Successfully deleted table bucket%n");
                } catch (Exception e) {
                    System.out.printf("Cleanup: Could not delete table bucket: %s%n", e.getMessage());
                }
            }
        }
    }

    @Test
    public void testPutTablePolicyOnNonExistentTable() {
        OSSTablesClient client = getTablesClient();

        try {
            PutTablePolicyResult putResult = client.putTablePolicy(
                    PutTablePolicyRequest.newBuilder()
                            .tableBucketARN("acs:osstables:cn-hangzhou:123456789012:bucket/nonexistent-bucket-test-12345")
                            .namespace("nonexistent-namespace")
                            .name("nonexistent-table")
                            .resourcePolicy("{\"Version\":\"1\",\"Statement\":[]}")
                            .build());

            Assert.fail("Expected exception when putting policy on non-existent table");
        } catch (Exception e) {
            Assert.assertNotNull(e);
            System.out.printf("Successfully caught expected exception for non-existent table: %s%n", e.getMessage());
        }
    }

    @Test
    public void testGetTablePolicyLifecycle() {
        OSSTablesClient client = getTablesClient();
        String namespaceName = "test_ns_gtp_" + System.currentTimeMillis();
        String tableName = "test_table_gtp_" + System.currentTimeMillis();
        String bucketName = genTableBucketName();
        String tableBucketARN = null;

        try {
            CreateTableBucketResult createBucketResult = client.createTableBucket(
                    CreateTableBucketRequest.newBuilder()
                            .name(bucketName)
                            .build());

            Assert.assertNotNull(createBucketResult);
            Assert.assertEquals(200, createBucketResult.statusCode());
            Assert.assertNotNull(createBucketResult.arn());
            System.out.printf("Successfully created table bucket with ARN: %s%n", createBucketResult.arn());

            tableBucketARN = createBucketResult.arn();

            CreateNamespaceResult createNsResult = client.createNamespace(
                    CreateNamespaceRequest.newBuilder()
                            .tableBucketARN(tableBucketARN)
                            .namespace(java.util.Arrays.asList(namespaceName))
                            .build());

            Assert.assertNotNull(createNsResult);
            Assert.assertEquals(200, createNsResult.statusCode());

            List<SchemaField> fields = new ArrayList<>();
            fields.add(SchemaField.newBuilder().name("id").type("long").required(true).build());
            fields.add(SchemaField.newBuilder().name("data").type("string").required(false).build());
            IcebergSchema icebergSchema = IcebergSchema.newBuilder().fields(fields).build();
            IcebergMetadata icebergMetadata = IcebergMetadata.newBuilder().schema(icebergSchema).build();
            TableMetadata metadata = TableMetadata.newBuilder().iceberg(icebergMetadata).build();

            CreateTableResult createTableResult = client.createTable(
                    CreateTableRequest.newBuilder()
                            .tableBucketARN(tableBucketARN)
                            .namespace(namespaceName)
                            .name(tableName)
                            .format("ICEBERG")
                            .metadata(metadata)
                            .build());

            Assert.assertNotNull(createTableResult);
            Assert.assertEquals(200, createTableResult.statusCode());
            Assert.assertNotNull(createTableResult.tableARN());
            System.out.printf("Successfully created table with ARN: %s%n", createTableResult.tableARN());

            String tableARN = createTableResult.tableARN();
            String resourcePolicy = "{\"Version\":\"1\",\"Statement\":[{\"Action\":[\"oss:GetTable\"],\"Effect\":\"Allow\",\"Principal\":[\"1234567890\"],\"Resource\":[\"" + tableARN + "\"]}]}";

            PutTablePolicyResult putResult = client.putTablePolicy(
                    PutTablePolicyRequest.newBuilder()
                            .tableBucketARN(tableBucketARN)
                            .namespace(namespaceName)
                            .name(tableName)
                            .resourcePolicy(resourcePolicy)
                            .build());

            Assert.assertNotNull(putResult);
            Assert.assertEquals(200, putResult.statusCode());
            System.out.printf("Successfully put table policy%n");

            GetTablePolicyResult getResult = client.getTablePolicy(
                    GetTablePolicyRequest.newBuilder()
                            .tableBucketARN(tableBucketARN)
                            .namespace(namespaceName)
                            .name(tableName)
                            .build());

            Assert.assertNotNull(getResult);
            Assert.assertEquals(200, getResult.statusCode());
            Assert.assertNotNull(getResult.resourcePolicy());
            System.out.printf("Successfully retrieved table policy: %s%n", getResult.resourcePolicy());

        } finally {
            if (tableBucketARN != null) {
                try {
                    client.deleteTable(DeleteTableRequest.newBuilder()
                            .tableBucketARN(tableBucketARN)
                            .namespace(namespaceName)
                            .name(tableName)
                            .build());
                    System.out.printf("Successfully deleted table%n");
                } catch (Exception e) {
                    System.out.printf("Cleanup: Could not delete table: %s%n", e.getMessage());
                }

                try {
                    client.deleteNamespace(DeleteNamespaceRequest.newBuilder()
                            .tableBucketARN(tableBucketARN)
                            .namespace(namespaceName)
                            .build());
                    System.out.printf("Successfully deleted namespace%n");
                } catch (Exception e) {
                    System.out.printf("Cleanup: Could not delete namespace: %s%n", e.getMessage());
                }

                try {
                    DeleteTableBucketResult deleteResult = client.deleteTableBucket(
                            DeleteTableBucketRequest.newBuilder()
                                    .tableBucketARN(tableBucketARN)
                                    .build());
                    Assert.assertNotNull(deleteResult);
                    Assert.assertEquals(204, deleteResult.statusCode());
                    System.out.printf("Successfully deleted table bucket%n");
                } catch (Exception e) {
                    System.out.printf("Cleanup: Could not delete table bucket: %s%n", e.getMessage());
                }
            }
        }
    }

    @Test
    public void testGetTablePolicyOnNonExistentTable() {
        OSSTablesClient client = getTablesClient();

        try {
            GetTablePolicyResult getResult = client.getTablePolicy(
                    GetTablePolicyRequest.newBuilder()
                            .tableBucketARN("acs:osstables:cn-hangzhou:123456789012:bucket/nonexistent-bucket-test-12345")
                            .namespace("nonexistent-namespace")
                            .name("nonexistent-table")
                            .build());

            Assert.fail("Expected exception when getting policy from non-existent table");
        } catch (Exception e) {
            Assert.assertNotNull(e);
            System.out.printf("Successfully caught expected exception for non-existent table: %s%n", e.getMessage());
        }
    }

    @Test
    public void testDeleteTablePolicyLifecycle() {
        OSSTablesClient client = getTablesClient();
        String namespaceName = "test_ns_dtp_" + System.currentTimeMillis();
        String tableName = "test_table_dtp_" + System.currentTimeMillis();
        String bucketName = genTableBucketName();
        String tableBucketARN = null;

        try {
            CreateTableBucketResult createBucketResult = client.createTableBucket(
                    CreateTableBucketRequest.newBuilder()
                            .name(bucketName)
                            .build());

            Assert.assertNotNull(createBucketResult);
            Assert.assertEquals(200, createBucketResult.statusCode());
            Assert.assertNotNull(createBucketResult.arn());
            System.out.printf("Successfully created table bucket with ARN: %s%n", createBucketResult.arn());

            tableBucketARN = createBucketResult.arn();

            CreateNamespaceResult createNsResult = client.createNamespace(
                    CreateNamespaceRequest.newBuilder()
                            .tableBucketARN(tableBucketARN)
                            .namespace(java.util.Arrays.asList(namespaceName))
                            .build());

            Assert.assertNotNull(createNsResult);
            Assert.assertEquals(200, createNsResult.statusCode());

            List<SchemaField> fields = new ArrayList<>();
            fields.add(SchemaField.newBuilder().name("id").type("long").required(true).build());
            fields.add(SchemaField.newBuilder().name("data").type("string").required(false).build());
            IcebergSchema icebergSchema = IcebergSchema.newBuilder().fields(fields).build();
            IcebergMetadata icebergMetadata = IcebergMetadata.newBuilder().schema(icebergSchema).build();
            TableMetadata metadata = TableMetadata.newBuilder().iceberg(icebergMetadata).build();

            CreateTableResult createTableResult = client.createTable(
                    CreateTableRequest.newBuilder()
                            .tableBucketARN(tableBucketARN)
                            .namespace(namespaceName)
                            .name(tableName)
                            .format("ICEBERG")
                            .metadata(metadata)
                            .build());

            Assert.assertNotNull(createTableResult);
            Assert.assertEquals(200, createTableResult.statusCode());
            Assert.assertNotNull(createTableResult.tableARN());
            System.out.printf("Successfully created table with ARN: %s%n", createTableResult.tableARN());

            String tableARN = createTableResult.tableARN();
            String resourcePolicy = "{\"Version\":\"1\",\"Statement\":[{\"Action\":[\"oss:GetTable\"],\"Effect\":\"Allow\",\"Principal\":[\"1234567890\"],\"Resource\":[\"" + tableARN + "\"]}]}";

            PutTablePolicyResult putResult = client.putTablePolicy(
                    PutTablePolicyRequest.newBuilder()
                            .tableBucketARN(tableBucketARN)
                            .namespace(namespaceName)
                            .name(tableName)
                            .resourcePolicy(resourcePolicy)
                            .build());

            Assert.assertNotNull(putResult);
            Assert.assertEquals(200, putResult.statusCode());
            System.out.printf("Successfully put table policy%n");

            DeleteTablePolicyResult deleteResult = client.deleteTablePolicy(
                    DeleteTablePolicyRequest.newBuilder()
                            .tableBucketARN(tableBucketARN)
                            .namespace(namespaceName)
                            .name(tableName)
                            .build());

            Assert.assertNotNull(deleteResult);
            Assert.assertEquals(204, deleteResult.statusCode());
            System.out.printf("Successfully deleted table policy%n");

        } finally {
            if (tableBucketARN != null) {
                try {
                    client.deleteTable(DeleteTableRequest.newBuilder()
                            .tableBucketARN(tableBucketARN)
                            .namespace(namespaceName)
                            .name(tableName)
                            .build());
                    System.out.printf("Successfully deleted table%n");
                } catch (Exception e) {
                    System.out.printf("Cleanup: Could not delete table: %s%n", e.getMessage());
                }

                try {
                    client.deleteNamespace(DeleteNamespaceRequest.newBuilder()
                            .tableBucketARN(tableBucketARN)
                            .namespace(namespaceName)
                            .build());
                    System.out.printf("Successfully deleted namespace%n");
                } catch (Exception e) {
                    System.out.printf("Cleanup: Could not delete namespace: %s%n", e.getMessage());
                }

                try {
                    DeleteTableBucketResult deleteResult = client.deleteTableBucket(
                            DeleteTableBucketRequest.newBuilder()
                                    .tableBucketARN(tableBucketARN)
                                    .build());
                    Assert.assertNotNull(deleteResult);
                    Assert.assertEquals(204, deleteResult.statusCode());
                    System.out.printf("Successfully deleted table bucket%n");
                } catch (Exception e) {
                    System.out.printf("Cleanup: Could not delete table bucket: %s%n", e.getMessage());
                }
            }
        }
    }

    @Test
    public void testDeleteTablePolicyOnNonExistentTable() {
        OSSTablesClient client = getTablesClient();

        try {
            DeleteTablePolicyResult deleteResult = client.deleteTablePolicy(
                    DeleteTablePolicyRequest.newBuilder()
                            .tableBucketARN("acs:osstables:cn-hangzhou:123456789012:bucket/nonexistent-bucket-test-12345")
                            .namespace("nonexistent-namespace")
                            .name("nonexistent-table")
                            .build());

            Assert.fail("Expected exception when deleting policy from non-existent table");
        } catch (Exception e) {
            Assert.assertNotNull(e);
            System.out.printf("Successfully caught expected exception for non-existent table: %s%n", e.getMessage());
        }
    }

    @Test
    public void testPutTableMaintenanceConfigurationLifecycle() {
        OSSTablesClient client = getTablesClient();
        String namespaceName = "test_ns_ptmc_" + System.currentTimeMillis();
        String tableName = "test_table_ptmc_" + System.currentTimeMillis();
        String bucketName = genTableBucketName();
        String tableBucketARN = null;

        try {
            CreateTableBucketResult createBucketResult = client.createTableBucket(
                    CreateTableBucketRequest.newBuilder()
                            .name(bucketName)
                            .build());

            Assert.assertNotNull(createBucketResult);
            Assert.assertEquals(200, createBucketResult.statusCode());
            Assert.assertNotNull(createBucketResult.arn());
            System.out.printf("Successfully created table bucket with ARN: %s%n", createBucketResult.arn());

            tableBucketARN = createBucketResult.arn();

            CreateNamespaceResult createNsResult = client.createNamespace(
                    CreateNamespaceRequest.newBuilder()
                            .tableBucketARN(tableBucketARN)
                            .namespace(java.util.Arrays.asList(namespaceName))
                            .build());

            Assert.assertNotNull(createNsResult);
            Assert.assertEquals(200, createNsResult.statusCode());

            List<SchemaField> fields = new ArrayList<>();
            fields.add(SchemaField.newBuilder().name("id").type("long").required(true).build());
            fields.add(SchemaField.newBuilder().name("data").type("string").required(false).build());
            IcebergSchema icebergSchema = IcebergSchema.newBuilder().fields(fields).build();
            IcebergMetadata icebergMetadata = IcebergMetadata.newBuilder().schema(icebergSchema).build();
            TableMetadata metadata = TableMetadata.newBuilder().iceberg(icebergMetadata).build();

            CreateTableResult createTableResult = client.createTable(
                    CreateTableRequest.newBuilder()
                            .tableBucketARN(tableBucketARN)
                            .namespace(namespaceName)
                            .name(tableName)
                            .format("ICEBERG")
                            .metadata(metadata)
                            .build());

            Assert.assertNotNull(createTableResult);
            Assert.assertEquals(200, createTableResult.statusCode());
            Assert.assertNotNull(createTableResult.tableARN());
            System.out.printf("Successfully created table with ARN: %s%n", createTableResult.tableARN());

            IcebergCompactionSettings compactionSettings = IcebergCompactionSettings.newBuilder()
                    .targetFileSizeMB(100)
                    .strategy("auto")
                    .build();
            TableMaintenanceSettings settings = TableMaintenanceSettings.newBuilder()
                    .icebergCompaction(compactionSettings)
                    .build();
            TableMaintenanceConfigurationValue value = TableMaintenanceConfigurationValue.newBuilder()
                    .status("enabled")
                    .settings(settings)
                    .build();

            PutTableMaintenanceConfigurationResult putResult = client.putTableMaintenanceConfiguration(
                    PutTableMaintenanceConfigurationRequest.newBuilder()
                            .tableBucketARN(tableBucketARN)
                            .namespace(namespaceName)
                            .name(tableName)
                            .type("icebergCompaction")
                            .value(value)
                            .build());

            Assert.assertNotNull(putResult);
            Assert.assertEquals(204, putResult.statusCode());
            System.out.printf("Successfully put table maintenance configuration (icebergCompaction)%n");

            // Test icebergSnapshotManagement type
            IcebergSnapshotManagementSettings snapshotSettings = IcebergSnapshotManagementSettings.newBuilder()
                    .minSnapshotsToKeep(5)
                    .maxSnapshotAgeHours(168)
                    .build();
            TableMaintenanceSettings snapshotMaintenanceSettings = TableMaintenanceSettings.newBuilder()
                    .icebergSnapshotManagement(snapshotSettings)
                    .build();
            TableMaintenanceConfigurationValue snapshotValue = TableMaintenanceConfigurationValue.newBuilder()
                    .status("enabled")
                    .settings(snapshotMaintenanceSettings)
                    .build();

            PutTableMaintenanceConfigurationResult putSnapshotResult = client.putTableMaintenanceConfiguration(
                    PutTableMaintenanceConfigurationRequest.newBuilder()
                            .tableBucketARN(tableBucketARN)
                            .namespace(namespaceName)
                            .name(tableName)
                            .type("icebergSnapshotManagement")
                            .value(snapshotValue)
                            .build());

            Assert.assertNotNull(putSnapshotResult);
            Assert.assertEquals(204, putSnapshotResult.statusCode());
            System.out.printf("Successfully put table maintenance configuration (icebergSnapshotManagement)%n");

        } finally {
            if (tableBucketARN != null) {
                try {
                    client.deleteTable(DeleteTableRequest.newBuilder()
                            .tableBucketARN(tableBucketARN)
                            .namespace(namespaceName)
                            .name(tableName)
                            .build());
                    System.out.printf("Successfully deleted table%n");
                } catch (Exception e) {
                    System.out.printf("Cleanup: Could not delete table: %s%n", e.getMessage());
                }

                try {
                    client.deleteNamespace(DeleteNamespaceRequest.newBuilder()
                            .tableBucketARN(tableBucketARN)
                            .namespace(namespaceName)
                            .build());
                    System.out.printf("Successfully deleted namespace%n");
                } catch (Exception e) {
                    System.out.printf("Cleanup: Could not delete namespace: %s%n", e.getMessage());
                }

                try {
                    DeleteTableBucketResult deleteResult = client.deleteTableBucket(
                            DeleteTableBucketRequest.newBuilder()
                                    .tableBucketARN(tableBucketARN)
                                    .build());
                    Assert.assertNotNull(deleteResult);
                    Assert.assertEquals(204, deleteResult.statusCode());
                    System.out.printf("Successfully deleted table bucket%n");
                } catch (Exception e) {
                    System.out.printf("Cleanup: Could not delete table bucket: %s%n", e.getMessage());
                }
            }
        }
    }

    @Test
    public void testPutTableMaintenanceConfigurationOnNonExistentTable() {
        OSSTablesClient client = getTablesClient();

        // Test icebergCompaction type
        IcebergCompactionSettings compactionSettings = IcebergCompactionSettings.newBuilder()
                .targetFileSizeMB(100)
                .strategy("auto")
                .build();
        TableMaintenanceSettings compactionMaintenanceSettings = TableMaintenanceSettings.newBuilder()
                .icebergCompaction(compactionSettings)
                .build();
        TableMaintenanceConfigurationValue compactionValue = TableMaintenanceConfigurationValue.newBuilder()
                .status("enabled")
                .settings(compactionMaintenanceSettings)
                .build();

        try {
            PutTableMaintenanceConfigurationResult putResult = client.putTableMaintenanceConfiguration(
                    PutTableMaintenanceConfigurationRequest.newBuilder()
                            .tableBucketARN("acs:osstables:cn-hangzhou:123456789012:bucket/nonexistent-bucket-test-12345")
                            .namespace("nonexistent-namespace")
                            .name("nonexistent-table")
                            .type("icebergCompaction")
                            .value(compactionValue)
                            .build());

            Assert.fail("Expected exception when putting maintenance configuration to non-existent table");
        } catch (Exception e) {
            Assert.assertNotNull(e);
            System.out.printf("Successfully caught expected exception for non-existent table (icebergCompaction): %s%n", e.getMessage());
        }

        // Test icebergSnapshotManagement type
        IcebergSnapshotManagementSettings snapshotSettings = IcebergSnapshotManagementSettings.newBuilder()
                .minSnapshotsToKeep(5)
                .maxSnapshotAgeHours(168)
                .build();
        TableMaintenanceSettings snapshotMaintenanceSettings = TableMaintenanceSettings.newBuilder()
                .icebergSnapshotManagement(snapshotSettings)
                .build();
        TableMaintenanceConfigurationValue snapshotValue = TableMaintenanceConfigurationValue.newBuilder()
                .status("enabled")
                .settings(snapshotMaintenanceSettings)
                .build();

        try {
            PutTableMaintenanceConfigurationResult putResult = client.putTableMaintenanceConfiguration(
                    PutTableMaintenanceConfigurationRequest.newBuilder()
                            .tableBucketARN("acs:osstables:cn-hangzhou:123456789012:bucket/nonexistent-bucket-test-12345")
                            .namespace("nonexistent-namespace")
                            .name("nonexistent-table")
                            .type("icebergSnapshotManagement")
                            .value(snapshotValue)
                            .build());

            Assert.fail("Expected exception when putting maintenance configuration to non-existent table");
        } catch (Exception e) {
            Assert.assertNotNull(e);
            System.out.printf("Successfully caught expected exception for non-existent table (icebergSnapshotManagement): %s%n", e.getMessage());
        }
    }

    @Test
    public void testGetTableMaintenanceConfigurationLifecycle() {
        OSSTablesClient client = getTablesClient();
        String namespaceName = "test_ns_gtmc_" + System.currentTimeMillis();
        String tableName = "test_table_gtmc_" + System.currentTimeMillis();
        String bucketName = genTableBucketName();
        String tableBucketARN = null;

        try {
            CreateTableBucketResult createBucketResult = client.createTableBucket(
                    CreateTableBucketRequest.newBuilder()
                            .name(bucketName)
                            .build());

            Assert.assertNotNull(createBucketResult);
            Assert.assertEquals(200, createBucketResult.statusCode());
            Assert.assertNotNull(createBucketResult.arn());
            System.out.printf("Successfully created table bucket with ARN: %s%n", createBucketResult.arn());

            tableBucketARN = createBucketResult.arn();

            CreateNamespaceResult createNsResult = client.createNamespace(
                    CreateNamespaceRequest.newBuilder()
                            .tableBucketARN(tableBucketARN)
                            .namespace(java.util.Arrays.asList(namespaceName))
                            .build());

            Assert.assertNotNull(createNsResult);
            Assert.assertEquals(200, createNsResult.statusCode());

            List<SchemaField> fields = new ArrayList<>();
            fields.add(SchemaField.newBuilder().name("id").type("long").required(true).build());
            fields.add(SchemaField.newBuilder().name("data").type("string").required(false).build());
            IcebergSchema icebergSchema = IcebergSchema.newBuilder().fields(fields).build();
            IcebergMetadata icebergMetadata = IcebergMetadata.newBuilder().schema(icebergSchema).build();
            TableMetadata metadata = TableMetadata.newBuilder().iceberg(icebergMetadata).build();

            CreateTableResult createTableResult = client.createTable(
                    CreateTableRequest.newBuilder()
                            .tableBucketARN(tableBucketARN)
                            .namespace(namespaceName)
                            .name(tableName)
                            .format("ICEBERG")
                            .metadata(metadata)
                            .build());

            Assert.assertNotNull(createTableResult);
            Assert.assertEquals(200, createTableResult.statusCode());
            Assert.assertNotNull(createTableResult.tableARN());
            System.out.printf("Successfully created table with ARN: %s%n", createTableResult.tableARN());

            // Put maintenance configurations for both types
            IcebergCompactionSettings compactionSettings = IcebergCompactionSettings.newBuilder()
                    .targetFileSizeMB(100)
                    .strategy("auto")
                    .build();
            TableMaintenanceSettings compactionMaintenanceSettings = TableMaintenanceSettings.newBuilder()
                    .icebergCompaction(compactionSettings)
                    .build();
            TableMaintenanceConfigurationValue compactionValue = TableMaintenanceConfigurationValue.newBuilder()
                    .status("enabled")
                    .settings(compactionMaintenanceSettings)
                    .build();

            PutTableMaintenanceConfigurationResult putCompactionResult = client.putTableMaintenanceConfiguration(
                    PutTableMaintenanceConfigurationRequest.newBuilder()
                            .tableBucketARN(tableBucketARN)
                            .namespace(namespaceName)
                            .name(tableName)
                            .type("icebergCompaction")
                            .value(compactionValue)
                            .build());

            Assert.assertNotNull(putCompactionResult);
            Assert.assertEquals(204, putCompactionResult.statusCode());
            System.out.printf("Successfully put icebergCompaction configuration%n");

            IcebergSnapshotManagementSettings snapshotSettings = IcebergSnapshotManagementSettings.newBuilder()
                    .minSnapshotsToKeep(5)
                    .maxSnapshotAgeHours(168)
                    .build();
            TableMaintenanceSettings snapshotMaintenanceSettings = TableMaintenanceSettings.newBuilder()
                    .icebergSnapshotManagement(snapshotSettings)
                    .build();
            TableMaintenanceConfigurationValue snapshotValue = TableMaintenanceConfigurationValue.newBuilder()
                    .status("enabled")
                    .settings(snapshotMaintenanceSettings)
                    .build();

            PutTableMaintenanceConfigurationResult putSnapshotResult = client.putTableMaintenanceConfiguration(
                    PutTableMaintenanceConfigurationRequest.newBuilder()
                            .tableBucketARN(tableBucketARN)
                            .namespace(namespaceName)
                            .name(tableName)
                            .type("icebergSnapshotManagement")
                            .value(snapshotValue)
                            .build());

            Assert.assertNotNull(putSnapshotResult);
            Assert.assertEquals(204, putSnapshotResult.statusCode());
            System.out.printf("Successfully put icebergSnapshotManagement configuration%n");

            // Get maintenance configuration
            GetTableMaintenanceConfigurationResult getResult = client.getTableMaintenanceConfiguration(
                    GetTableMaintenanceConfigurationRequest.newBuilder()
                            .tableBucketARN(tableBucketARN)
                            .namespace(namespaceName)
                            .name(tableName)
                            .build());

            Assert.assertNotNull(getResult);
            Assert.assertEquals(200, getResult.statusCode());
            Assert.assertNotNull(getResult.tableARN());
            System.out.printf("Successfully got table maintenance configuration, table ARN: %s%n", getResult.tableARN());
            Assert.assertNotNull(getResult.configuration());
            Assert.assertTrue(getResult.configuration().containsKey("icebergCompaction") || getResult.configuration().containsKey("icebergSnapshotManagement"));
            System.out.printf("Successfully got maintenance configurations: %s%n", getResult.configuration().keySet());

        } finally {
            if (tableBucketARN != null) {
                try {
                    client.deleteTable(DeleteTableRequest.newBuilder()
                            .tableBucketARN(tableBucketARN)
                            .namespace(namespaceName)
                            .name(tableName)
                            .build());
                    System.out.printf("Successfully deleted table%n");
                } catch (Exception e) {
                    System.out.printf("Cleanup: Could not delete table: %s%n", e.getMessage());
                }

                try {
                    client.deleteNamespace(DeleteNamespaceRequest.newBuilder()
                            .tableBucketARN(tableBucketARN)
                            .namespace(namespaceName)
                            .build());
                    System.out.printf("Successfully deleted namespace%n");
                } catch (Exception e) {
                    System.out.printf("Cleanup: Could not delete namespace: %s%n", e.getMessage());
                }

                try {
                    DeleteTableBucketResult deleteResult = client.deleteTableBucket(
                            DeleteTableBucketRequest.newBuilder()
                                    .tableBucketARN(tableBucketARN)
                                    .build());
                    Assert.assertNotNull(deleteResult);
                    Assert.assertEquals(204, deleteResult.statusCode());
                    System.out.printf("Successfully deleted table bucket%n");
                } catch (Exception e) {
                    System.out.printf("Cleanup: Could not delete table bucket: %s%n", e.getMessage());
                }
            }
        }
    }

    @Test
    public void testGetTableMaintenanceConfigurationOnNonExistentTable() {
        OSSTablesClient client = getTablesClient();

        try {
            GetTableMaintenanceConfigurationResult getResult = client.getTableMaintenanceConfiguration(
                    GetTableMaintenanceConfigurationRequest.newBuilder()
                            .tableBucketARN("acs:osstables:cn-hangzhou:123456789012:bucket/nonexistent-bucket-test-12345")
                            .namespace("nonexistent-namespace")
                            .name("nonexistent-table")
                            .build());

            Assert.fail("Expected exception when getting maintenance configuration from non-existent table");
        } catch (Exception e) {
            Assert.assertNotNull(e);
            System.out.printf("Successfully caught expected exception for non-existent table: %s%n", e.getMessage());
        }
    }

    @Test
    public void testGetTableMaintenanceJobStatusLifecycle() {
        OSSTablesClient client = getTablesClient();
        String namespaceName = "test_ns_job_" + System.currentTimeMillis();
        String tableName = "test_table_job_" + System.currentTimeMillis();
        String bucketName = genTableBucketName();
        String tableBucketARN = null;

        try {
            // 1. Create a table bucket
            CreateTableBucketResult createBucketResult = client.createTableBucket(
                    CreateTableBucketRequest.newBuilder()
                            .name(bucketName)
                            .build());

            Assert.assertNotNull(createBucketResult);
            Assert.assertEquals(200, createBucketResult.statusCode());
            Assert.assertNotNull(createBucketResult.arn());
            System.out.printf("Successfully created table bucket with ARN: %s%n", createBucketResult.arn());

            tableBucketARN = createBucketResult.arn();

            // 2. Create a namespace
            CreateNamespaceResult createNsResult = client.createNamespace(
                    CreateNamespaceRequest.newBuilder()
                            .tableBucketARN(tableBucketARN)
                            .namespace(java.util.Arrays.asList(namespaceName))
                            .build());

            Assert.assertNotNull(createNsResult);
            Assert.assertEquals(200, createNsResult.statusCode());

            // 3. Create a table
            List<SchemaField> fields = new ArrayList<>();
            fields.add(SchemaField.newBuilder().name("id").type("long").required(true).build());
            fields.add(SchemaField.newBuilder().name("data").type("string").required(false).build());
            IcebergSchema icebergSchema = IcebergSchema.newBuilder().fields(fields).build();
            IcebergMetadata icebergMetadata = IcebergMetadata.newBuilder().schema(icebergSchema).build();
            TableMetadata metadata = TableMetadata.newBuilder().iceberg(icebergMetadata).build();

            CreateTableResult createTableResult = client.createTable(
                    CreateTableRequest.newBuilder()
                            .tableBucketARN(tableBucketARN)
                            .namespace(namespaceName)
                            .name(tableName)
                            .format("ICEBERG")
                            .metadata(metadata)
                            .build());

            Assert.assertNotNull(createTableResult);
            Assert.assertEquals(200, createTableResult.statusCode());
            Assert.assertNotNull(createTableResult.tableARN());
            System.out.printf("Successfully created table with ARN: %s%n", createTableResult.tableARN());

            // 4. Get table maintenance job status (without tableARN header)
            GetTableMaintenanceJobStatusResult getJobStatusResult = client.getTableMaintenanceJobStatus(
                    GetTableMaintenanceJobStatusRequest.newBuilder()
                            .tableBucketARN(tableBucketARN)
                            .namespace(namespaceName)
                            .name(tableName)
                            .build());

            Assert.assertNotNull(getJobStatusResult);
            Assert.assertEquals(200, getJobStatusResult.statusCode());
            Assert.assertNotNull(getJobStatusResult.tableARN());
            System.out.printf("Successfully got table maintenance job status, table ARN: %s%n", getJobStatusResult.tableARN());
            Assert.assertNotNull(getJobStatusResult.jobStatus());
            System.out.printf("Successfully got maintenance job status: %s%n", getJobStatusResult.jobStatus().keySet());

            // 5. Get table maintenance job status with tableARN header (admin user scenario)
            GetTableMaintenanceJobStatusResult getJobStatusResultWithTableARN = client.getTableMaintenanceJobStatus(
                    GetTableMaintenanceJobStatusRequest.newBuilder()
                            .tableBucketARN(tableBucketARN)
                            .namespace(namespaceName)
                            .name(tableName)
                            .tableARN(createTableResult.tableARN())
                            .build());

            Assert.assertNotNull(getJobStatusResultWithTableARN);
            Assert.assertEquals(200, getJobStatusResultWithTableARN.statusCode());
            Assert.assertNotNull(getJobStatusResultWithTableARN.tableARN());
            System.out.printf("Successfully got table maintenance job status with tableARN header, table ARN: %s%n", getJobStatusResultWithTableARN.tableARN());
            Assert.assertNotNull(getJobStatusResultWithTableARN.jobStatus());
            System.out.printf("Successfully got maintenance job status with tableARN header: %s%n", getJobStatusResultWithTableARN.jobStatus().keySet());

        } finally {
            if (tableBucketARN != null) {
                try {
                    client.deleteTable(DeleteTableRequest.newBuilder()
                            .tableBucketARN(tableBucketARN)
                            .namespace(namespaceName)
                            .name(tableName)
                            .build());
                    System.out.printf("Successfully deleted table%n");
                } catch (Exception e) {
                    System.out.printf("Cleanup: Could not delete table: %s%n", e.getMessage());
                }

                try {
                    client.deleteNamespace(DeleteNamespaceRequest.newBuilder()
                            .tableBucketARN(tableBucketARN)
                            .namespace(namespaceName)
                            .build());
                    System.out.printf("Successfully deleted namespace%n");
                } catch (Exception e) {
                    System.out.printf("Cleanup: Could not delete namespace: %s%n", e.getMessage());
                }

                try {
                    DeleteTableBucketResult deleteResult = client.deleteTableBucket(
                            DeleteTableBucketRequest.newBuilder()
                                    .tableBucketARN(tableBucketARN)
                                    .build());
                    Assert.assertNotNull(deleteResult);
                    Assert.assertEquals(204, deleteResult.statusCode());
                    System.out.printf("Successfully deleted table bucket%n");
                } catch (Exception e) {
                    System.out.printf("Cleanup: Could not delete table bucket: %s%n", e.getMessage());
                }
            }
        }
    }

    @Test
    public void testGetTableMaintenanceJobStatusOnNonExistentTable() {
        OSSTablesClient client = getTablesClient();

        try {
            GetTableMaintenanceJobStatusResult getResult = client.getTableMaintenanceJobStatus(
                    GetTableMaintenanceJobStatusRequest.newBuilder()
                            .tableBucketARN("acs:osstables:cn-hangzhou:123456789012:bucket/nonexistent-bucket-test-12345")
                            .namespace("nonexistent-namespace")
                            .name("nonexistent-table")
                            .build());

            Assert.fail("Expected exception when getting maintenance job status from non-existent table");
        } catch (Exception e) {
            Assert.assertNotNull(e);
            System.out.printf("Successfully caught expected exception for non-existent table: %s%n", e.getMessage());
        }
    }
}
