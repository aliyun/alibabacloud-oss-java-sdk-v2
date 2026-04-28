package com.aliyun.sdk.service.oss2.tables;

import com.aliyun.sdk.service.oss2.credentials.CredentialsProvider;
import com.aliyun.sdk.service.oss2.credentials.StaticCredentialsProvider;
import com.aliyun.sdk.service.oss2.tables.models.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ClientTableTest extends TestBaseTables {

    @Test
    public void testCreateTableLifecycle() {
        OSSTablesClient client = getTablesClient();
        String namespaceName = "test_namespace_" + System.currentTimeMillis();
        String tableName = "test1_table_" + System.currentTimeMillis();
        String bucketName = genTableBucketName();
        String tableBucketARN = null;

        // First create a table bucket to use for namespace operations
        CreateTableBucketResult createBucketResult = client.createTableBucket(
                CreateTableBucketRequest.newBuilder()
                        .name(bucketName)
                        .build());

        Assert.assertNotNull(createBucketResult);
        Assert.assertEquals(200, createBucketResult.statusCode());
        Assert.assertNotNull(createBucketResult.arn());
        System.out.printf("Successfully created table bucket with ARN: %s%n", createBucketResult.arn());

        tableBucketARN = createBucketResult.arn();

        // First create a namespace for the test
        CreateNamespaceResult createNsResult = client.createNamespace(
                CreateNamespaceRequest.newBuilder()
                        .tableBucketARN(tableBucketARN)  // Use newly created tableBucketARN
                        .namespace(java.util.Arrays.asList(namespaceName))  // namespace expects a List<String>
                        .build());

        Assert.assertNotNull(createNsResult);
        Assert.assertEquals(200, createNsResult.statusCode());
        Assert.assertTrue(createNsResult.namespace().contains(namespaceName));

        try {
            // 1. Create a table
            IcebergSchema icebergSchema = new IcebergSchema();
                                
            // Create schema fields
            List<SchemaField> fields = new ArrayList<>();
            fields.add(createSchemaField("id", "long", true));
            fields.add(createSchemaField("name", "string", false));
            fields.add(createSchemaField("ts", "timestamptz", false));
            icebergSchema = IcebergSchema.newBuilder().fields(fields).build();
                                
            // Create partition spec
            IcebergPartitionField partitionField = IcebergPartitionField.newBuilder()
                .sourceId(2)
                .transform("identity")
                .name("region")
                .fieldId(1001)
                .build();
            List<IcebergPartitionField> partitionFields = new ArrayList<>();
            partitionFields.add(partitionField);
            IcebergPartitionSpec partitionSpec = IcebergPartitionSpec.newBuilder()
                .specId(0)
                .fields(partitionFields)
                .build();
                                
            // Create iceberg metadata
            IcebergMetadata icebergMetadata = IcebergMetadata.newBuilder()
                .schema(icebergSchema)
                .partitionSpec(partitionSpec)
                .build();
            
            // Set metadata
            TableMetadata metadata = new TableMetadata();
            metadata = TableMetadata.newBuilder().iceberg(icebergMetadata).build();
            
            // Add encryption configuration
            EncryptionConfiguration encryptionConfig = new EncryptionConfiguration();
            encryptionConfig = EncryptionConfiguration.newBuilder().sseAlgorithm("AES256").build();
            
            CreateTableResult createResult = client.createTable(
                    CreateTableRequest.newBuilder()
                            .tableBucketARN(tableBucketARN)
                            .namespace(namespaceName)
                            .name(tableName)
                            .format("ICEBERG")
                            .metadata(metadata)
                            .encryptionConfiguration(encryptionConfig)
                            .build());

            // Assert successful creation
            Assert.assertNotNull(createResult);
            Assert.assertEquals(200, createResult.statusCode());
            Assert.assertNotNull(createResult.tableARN());
            Assert.assertNotNull(createResult.versionToken());
            System.out.printf("Successfully created table with ARN: %s%n", createResult.tableARN());

            String tableArn = createResult.tableARN();

            // 2. Get the created table to verify it exists
            GetTableResult getResult = client.getTable(
                    GetTableRequest.newBuilder()
                            .tableBucketARN(tableBucketARN)
                            .namespace(namespaceName)
                            .name(tableName)
                            .build());

            Assert.assertNotNull(getResult);
            Assert.assertEquals(200, getResult.statusCode());
            Assert.assertEquals(tableName, getResult.name());
            Assert.assertNotNull(getResult.tableARN());
            Assert.assertEquals(namespaceName, getResult.namespace().get(0));
            System.out.printf("Successfully getTable table with tableBucketARN, tableARN is %s%n", tableArn);

            // 2.1 Get the created table to verify it exists
            GetTableResult getResultARN = client.getTable(
                    GetTableRequest.newBuilder()
                            .tableArn(tableArn)
                            .build());

            Assert.assertNotNull(getResultARN);
            Assert.assertEquals(200, getResultARN.statusCode());
            Assert.assertEquals(tableName, getResultARN.name());
            Assert.assertNotNull(getResultARN.tableARN());
            Assert.assertEquals(namespaceName, getResultARN.namespace().get(0));
            System.out.printf("Successfully getTable table with tableArn, tableARN is %s%n", getResultARN.tableARN());


            getResultARN = client.getTable(
                    GetTableRequest.newBuilder()
                            .tableBucketARN(tableBucketARN)
                            .namespace(namespaceName)
                            .name(tableName)
                            .build());

            Assert.assertNotNull(getResultARN);
            Assert.assertEquals(200, getResultARN.statusCode());
            Assert.assertEquals(tableName, getResultARN.name());
            Assert.assertNotNull(getResultARN.tableARN());
            Assert.assertEquals(namespaceName, getResultARN.namespace().get(0));
            System.out.printf("Successfully getTable table with tableArn, tableARN is %s%n", getResultARN.tableARN());


            // 3. List tables and verify our table is included
            ListTablesResult listResult = client.listTables(
                    ListTablesRequest.newBuilder()
                            .tableBucketARN(tableBucketARN)
                            .namespace(namespaceName)
                            .build());

            Assert.assertNotNull(listResult);
            Assert.assertEquals(200, listResult.statusCode());
            Assert.assertNotNull(listResult.tables());
            Assert.assertTrue("Should contain at least one table", listResult.tables().size() >= 1);

            // Find our table in the list
            boolean found = false;
            for (TableSummary summary : listResult.tables()) {
                if (summary.name().equals(tableName) && summary.namespace().get(0).equals(namespaceName)) {
                    found = true;
                    Assert.assertEquals(tableName, summary.name());
                    Assert.assertEquals(namespaceName, summary.namespace().get(0));
                    break;
                }
            }
            Assert.assertTrue("Created table should be found in the list", found);

        } finally {
            // Cleanup: Delete the table
            try {
                DeleteTableResult deleteResult = client.deleteTable(
                        DeleteTableRequest.newBuilder()
                                .tableBucketARN(tableBucketARN)
                                .namespace(namespaceName)
                                .name(tableName)
                                .build());
                Assert.assertNotNull(deleteResult);
                Assert.assertEquals(204, deleteResult.statusCode());
                System.out.printf("Successfully deleted table, request ID: %s%n", 
                        deleteResult.requestId());
            } catch (Exception e) {
                // If the table was already deleted, that's fine
                System.out.printf("Cleanup: Could not delete table (may have been already deleted): %s%n", 
                        e.getMessage());
            }
            
            // Cleanup: Delete the namespace
            try {
                DeleteNamespaceResult deleteResult = client.deleteNamespace(
                        DeleteNamespaceRequest.newBuilder()
                                .tableBucketARN(tableBucketARN)
                                .namespace(namespaceName)  // namespace expects a String
                                .build());
                Assert.assertNotNull(deleteResult);
                Assert.assertEquals(204, deleteResult.statusCode());
                System.out.printf("Successfully deleted namespace, request ID: %s%n", 
                        deleteResult.requestId());
            } catch (Exception e) {
                // If the namespace was already deleted, that's fine
                System.out.printf("Cleanup: Could not delete namespace (may have been already deleted): %s%n", 
                        e.getMessage());
            }
            
            // Also cleanup the table bucket we created
            try {
                DeleteTableBucketResult deleteBucketResult = client.deleteTableBucket(
                        DeleteTableBucketRequest.newBuilder()
                                .tableBucketARN(tableBucketARN)
                                .build());
                Assert.assertNotNull(deleteBucketResult);
                Assert.assertEquals(204, deleteBucketResult.statusCode());
                System.out.printf("Successfully deleted table bucket, request ID: %s%n", 
                        deleteBucketResult.requestId());
            } catch (Exception e) {
                // If the table bucket was already deleted, that's fine
                System.out.printf("Cleanup: Could not delete table bucket (may have been already deleted): %s%n", 
                        e.getMessage());
            }
        }
    }

    @Test
    public void testRenameTable() {
        OSSTablesClient client = getTablesClient();
        String namespaceName = "test_namespace_" + System.currentTimeMillis();
        String originalTableName = "test2_table_original_" + System.currentTimeMillis();
        String newTableName = "test2_table_new_" + System.currentTimeMillis();
        String bucketName = genTableBucketName();
        String tableBucketARN = null;

        // First create a table bucket to use for namespace operations
        CreateTableBucketResult createBucketResult = client.createTableBucket(
                CreateTableBucketRequest.newBuilder()
                        .name(bucketName)
                        .build());

        Assert.assertNotNull(createBucketResult);
        Assert.assertEquals(200, createBucketResult.statusCode());
        Assert.assertNotNull(createBucketResult.arn());
        System.out.printf("Successfully created table bucket with ARN: %s%n", createBucketResult.arn());

        tableBucketARN = createBucketResult.arn();

        // Create a namespace for the test
        CreateNamespaceResult createNsResult = client.createNamespace(
                CreateNamespaceRequest.newBuilder()
                        .tableBucketARN(tableBucketARN)
                        .namespace(java.util.Arrays.asList(namespaceName))
                        .build());

        Assert.assertNotNull(createNsResult);
        Assert.assertEquals(200, createNsResult.statusCode());
        Assert.assertTrue(createNsResult.namespace().contains(namespaceName));

        try {
            // 1. Create a table first
            IcebergSchema icebergSchema = new IcebergSchema();
                                
            // Create schema fields
            List<SchemaField> fields = new ArrayList<>();
            fields.add(createSchemaField("id", "long", true));
            fields.add(createSchemaField("name", "string", false));
            icebergSchema = IcebergSchema.newBuilder().fields(fields).build();
                                
            // Create iceberg metadata
            IcebergMetadata icebergMetadata = IcebergMetadata.newBuilder()
                .schema(icebergSchema)
                .build();
            
            // Set metadata
            TableMetadata metadata = new TableMetadata();
            metadata = TableMetadata.newBuilder().iceberg(icebergMetadata).build();
            
            CreateTableResult createResult = client.createTable(
                    CreateTableRequest.newBuilder()
                            .tableBucketARN(tableBucketARN)
                            .namespace(namespaceName)
                            .name(originalTableName)
                            .format("ICEBERG")
                            .metadata(metadata)
                            .build());

            // Assert successful creation
            Assert.assertNotNull(createResult);
            Assert.assertEquals(200, createResult.statusCode());
            Assert.assertNotNull(createResult.tableARN());
            Assert.assertNotNull(createResult.versionToken());
            System.out.printf("Successfully created table with ARN: %s%n", createResult.tableARN());

            // 2. Rename the table
            RenameTableResult renameResult = client.renameTable(
                    RenameTableRequest.newBuilder()
                            .tableBucketARN(tableBucketARN)
                            .namespace(namespaceName)
                            .name(originalTableName)
                            .newName(newTableName)
                            .build());

            Assert.assertNotNull(renameResult);
            Assert.assertEquals(204, renameResult.statusCode());
            System.out.printf("Successfully renamed table from %s to %s%n", originalTableName, newTableName);

            // 3. Verify the table has been renamed by getting it with the new name
            GetTableResult getResult = client.getTable(
                    GetTableRequest.newBuilder()
                            .tableBucketARN(tableBucketARN)
                            .namespace(namespaceName)
                            .name(newTableName)
                            .build());

            Assert.assertNotNull(getResult);
            Assert.assertEquals(200, getResult.statusCode());
            Assert.assertEquals(newTableName, getResult.name());

            // 4. Verify the original table name no longer exists
            try {
                GetTableResult originalResult = client.getTable(
                        GetTableRequest.newBuilder()
                                .tableBucketARN(tableBucketARN)
                                .namespace(namespaceName)
                                .name(originalTableName)
                                .build());
                
                // If we get here, it means the original table still exists, which is unexpected
                Assert.fail("Original table name should no longer exist after rename");
            } catch (Exception e) {
                // This is expected: the original table name should not exist anymore
                System.out.printf("Confirmed original table name %s no longer exists%n", originalTableName);
            }

        } finally {
            // Cleanup: Try to delete the renamed table
            try {
                DeleteTableResult deleteResult = client.deleteTable(
                        DeleteTableRequest.newBuilder()
                                .tableBucketARN(tableBucketARN)
                                .namespace(namespaceName)
                                .name(newTableName)
                                .build());
                Assert.assertNotNull(deleteResult);
                Assert.assertEquals(204, deleteResult.statusCode());
                System.out.printf("Successfully deleted renamed table, request ID: %s%n", 
                        deleteResult.requestId());
            } catch (Exception e) {
                // If the table was already deleted, that's fine
                System.out.printf("Cleanup: Could not delete renamed table (may have been already deleted): %s%n", 
                        e.getMessage());
            }
            
            // Cleanup: Delete the namespace
            try {
                DeleteNamespaceResult deleteResult = client.deleteNamespace(
                        DeleteNamespaceRequest.newBuilder()
                                .tableBucketARN(tableBucketARN)
                                .namespace(namespaceName)
                                .build());
                Assert.assertNotNull(deleteResult);
                Assert.assertEquals(204, deleteResult.statusCode());
                System.out.printf("Successfully deleted namespace, request ID: %s%n", 
                        deleteResult.requestId());
            } catch (Exception e) {
                // If the namespace was already deleted, that's fine
                System.out.printf("Cleanup: Could not delete namespace (may have been already deleted): %s%n", 
                        e.getMessage());
            }
            
            // Also cleanup the table bucket we created
            try {
                DeleteTableBucketResult deleteBucketResult = client.deleteTableBucket(
                        DeleteTableBucketRequest.newBuilder()
                                .tableBucketARN(tableBucketARN)
                                .build());
                Assert.assertNotNull(deleteBucketResult);
                Assert.assertEquals(204, deleteBucketResult.statusCode());
                System.out.printf("Successfully deleted table bucket, request ID: %s%n", 
                        deleteBucketResult.requestId());
            } catch (Exception e) {
                // If the table bucket was already deleted, that's fine
                System.out.printf("Cleanup: Could not delete table bucket (may have been already deleted): %s%n", 
                        e.getMessage());
            }
        }
    }

    @Test
    public void testCreateTableUsePathStyle() {
        CredentialsProvider provider = new StaticCredentialsProvider(accessKeyId(), accessKeySecret());
        OSSTablesClient client = OSSTablesClient.newBuilder()
                .region(region())
                .usePathStyle(true)
                .endpoint(tablesEndpoint())
                .credentialsProvider(provider)
                .build();

        String namespaceName = "test_namespace_" + System.currentTimeMillis();
        String tableName = "test1_table_" + System.currentTimeMillis();
        String bucketName = genTableBucketName();
        String tableBucketARN = null;

        // First create a table bucket to use for namespace operations
        CreateTableBucketResult createBucketResult = client.createTableBucket(
                CreateTableBucketRequest.newBuilder()
                        .name(bucketName)
                        .build());

        Assert.assertNotNull(createBucketResult);
        Assert.assertEquals(200, createBucketResult.statusCode());
        Assert.assertNotNull(createBucketResult.arn());
        System.out.printf("Successfully created table bucket with ARN: %s%n", createBucketResult.arn());

        tableBucketARN = createBucketResult.arn();

        // First create a namespace for the test
        CreateNamespaceResult createNsResult = client.createNamespace(
                CreateNamespaceRequest.newBuilder()
                        .tableBucketARN(tableBucketARN)  // Use newly created tableBucketARN
                        .namespace(java.util.Arrays.asList(namespaceName))  // namespace expects a List<String>
                        .build());

        Assert.assertNotNull(createNsResult);
        Assert.assertEquals(200, createNsResult.statusCode());
        Assert.assertTrue(createNsResult.namespace().contains(namespaceName));

        try {
            // 1. Create a table
            IcebergSchema icebergSchema = new IcebergSchema();

            // Create schema fields
            List<SchemaField> fields = new ArrayList<>();
            fields.add(createSchemaField("id", "long", true));
            fields.add(createSchemaField("name", "string", false));
            fields.add(createSchemaField("ts", "timestamptz", false));
            icebergSchema = IcebergSchema.newBuilder().fields(fields).build();

            // Create partition spec
            IcebergPartitionField partitionField = IcebergPartitionField.newBuilder()
                    .sourceId(2)
                    .transform("identity")
                    .name("region")
                    .fieldId(1001)
                    .build();
            List<IcebergPartitionField> partitionFields = new ArrayList<>();
            partitionFields.add(partitionField);
            IcebergPartitionSpec partitionSpec = IcebergPartitionSpec.newBuilder()
                    .specId(0)
                    .fields(partitionFields)
                    .build();

            // Create iceberg metadata
            IcebergMetadata icebergMetadata = IcebergMetadata.newBuilder()
                    .schema(icebergSchema)
                    .partitionSpec(partitionSpec)
                    .build();

            // Set metadata
            TableMetadata metadata = new TableMetadata();
            metadata = TableMetadata.newBuilder().iceberg(icebergMetadata).build();

            // Add encryption configuration
            EncryptionConfiguration encryptionConfig = new EncryptionConfiguration();
            encryptionConfig = EncryptionConfiguration.newBuilder().sseAlgorithm("AES256").build();

            CreateTableResult createResult = client.createTable(
                    CreateTableRequest.newBuilder()
                            .tableBucketARN(tableBucketARN)
                            .namespace(namespaceName)
                            .name(tableName)
                            .format("ICEBERG")
                            .metadata(metadata)
                            .encryptionConfiguration(encryptionConfig)
                            .build());

            // Assert successful creation
            Assert.assertNotNull(createResult);
            Assert.assertEquals(200, createResult.statusCode());
            Assert.assertNotNull(createResult.tableARN());
            Assert.assertNotNull(createResult.versionToken());
            System.out.printf("Successfully created table with ARN: %s%n", createResult.tableARN());

            String tableArn = createResult.tableARN();

            // 2. Get the created table to verify it exists
            GetTableResult getResult = client.getTable(
                    GetTableRequest.newBuilder()
                            .tableBucketARN(tableBucketARN)
                            .namespace(namespaceName)
                            .name(tableName)
                            .build());

            Assert.assertNotNull(getResult);
            Assert.assertEquals(200, getResult.statusCode());
            Assert.assertEquals(tableName, getResult.name());
            Assert.assertNotNull(getResult.tableARN());
            Assert.assertEquals(namespaceName, getResult.namespace().get(0));
            System.out.printf("Successfully getTable table with tableBucketARN, tableARN is %s%n", tableArn);

            // 2.1 Get the created table to verify it exists
            GetTableResult getResultARN = client.getTable(
                    GetTableRequest.newBuilder()
                            .tableArn(tableArn)
                            .build());

            Assert.assertNotNull(getResultARN);
            Assert.assertEquals(200, getResultARN.statusCode());
            Assert.assertEquals(tableName, getResultARN.name());
            Assert.assertNotNull(getResultARN.tableARN());
            Assert.assertEquals(namespaceName, getResultARN.namespace().get(0));
            System.out.printf("Successfully getTable table with tableArn, tableARN is %s%n", getResultARN.tableARN());


            getResultARN = client.getTable(
                    GetTableRequest.newBuilder()
                            .tableBucketARN(tableBucketARN)
                            .namespace(namespaceName)
                            .name(tableName)
                            .build());

            Assert.assertNotNull(getResultARN);
            Assert.assertEquals(200, getResultARN.statusCode());
            Assert.assertEquals(tableName, getResultARN.name());
            Assert.assertNotNull(getResultARN.tableARN());
            Assert.assertEquals(namespaceName, getResultARN.namespace().get(0));
            System.out.printf("Successfully getTable table with tableArn, tableARN is %s%n", getResultARN.tableARN());


            // 3. List tables and verify our table is included
            ListTablesResult listResult = client.listTables(
                    ListTablesRequest.newBuilder()
                            .tableBucketARN(tableBucketARN)
                            .namespace(namespaceName)
                            .build());

            Assert.assertNotNull(listResult);
            Assert.assertEquals(200, listResult.statusCode());
            Assert.assertNotNull(listResult.tables());
            Assert.assertTrue("Should contain at least one table", listResult.tables().size() >= 1);

            // Find our table in the list
            boolean found = false;
            for (TableSummary summary : listResult.tables()) {
                if (summary.name().equals(tableName) && summary.namespace().get(0).equals(namespaceName)) {
                    found = true;
                    Assert.assertEquals(tableName, summary.name());
                    Assert.assertEquals(namespaceName, summary.namespace().get(0));
                    break;
                }
            }
            Assert.assertTrue("Created table should be found in the list", found);

        } finally {
            // Cleanup: Delete the table
            try {
                DeleteTableResult deleteResult = client.deleteTable(
                        DeleteTableRequest.newBuilder()
                                .tableBucketARN(tableBucketARN)
                                .namespace(namespaceName)
                                .name(tableName)
                                .build());
                Assert.assertNotNull(deleteResult);
                Assert.assertEquals(204, deleteResult.statusCode());
                System.out.printf("Successfully deleted table, request ID: %s%n",
                        deleteResult.requestId());
            } catch (Exception e) {
                // If the table was already deleted, that's fine
                System.out.printf("Cleanup: Could not delete table (may have been already deleted): %s%n",
                        e.getMessage());
            }

            // Cleanup: Delete the namespace
            try {
                DeleteNamespaceResult deleteResult = client.deleteNamespace(
                        DeleteNamespaceRequest.newBuilder()
                                .tableBucketARN(tableBucketARN)
                                .namespace(namespaceName)  // namespace expects a String
                                .build());
                Assert.assertNotNull(deleteResult);
                Assert.assertEquals(204, deleteResult.statusCode());
                System.out.printf("Successfully deleted namespace, request ID: %s%n",
                        deleteResult.requestId());
            } catch (Exception e) {
                // If the namespace was already deleted, that's fine
                System.out.printf("Cleanup: Could not delete namespace (may have been already deleted): %s%n",
                        e.getMessage());
            }

            // Also cleanup the table bucket we created
            try {
                DeleteTableBucketResult deleteBucketResult = client.deleteTableBucket(
                        DeleteTableBucketRequest.newBuilder()
                                .tableBucketARN(tableBucketARN)
                                .build());
                Assert.assertNotNull(deleteBucketResult);
                Assert.assertEquals(204, deleteBucketResult.statusCode());
                System.out.printf("Successfully deleted table bucket, request ID: %s%n",
                        deleteBucketResult.requestId());
            } catch (Exception e) {
                // If the table bucket was already deleted, that's fine
                System.out.printf("Cleanup: Could not delete table bucket (may have been already deleted): %s%n",
                        e.getMessage());
            }
        }
    }

    private SchemaField createSchemaField(String name, String type, boolean required) {
        return SchemaField.newBuilder()
            .name(name)
            .type(type)
            .required(required)
            .build();
    }
}