package com.aliyun.sdk.service.oss2.tables;

import com.aliyun.sdk.service.oss2.exceptions.ServiceException;
import com.aliyun.sdk.service.oss2.tables.models.*;
import org.junit.Assert;
import org.junit.Test;

public class ClientNamespacesTest extends TestBaseTables {

    @Test
    public void testNamespaceLifecycle() {
        OSSTablesClient client = getTablesClient();
        String namespaceName = "test_namespace_" + System.currentTimeMillis();
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

        try {
            // 1. Create a namespace
            CreateNamespaceResult createResult = null;
            try {
                createResult = client.createNamespace(
                        CreateNamespaceRequest.newBuilder()
                                .tableBucketARN(tableBucketARN)
                                .namespace(java.util.Arrays.asList(namespaceName))
                                .build());
            } catch (Exception e) {
                System.err.println("Error creating namespace: " + e.getMessage());
                e.printStackTrace();
                throw e;
            }

            // Assert successful creation
            Assert.assertNotNull(createResult);
            Assert.assertEquals(200, createResult.statusCode());
            Assert.assertEquals(namespaceName, createResult.namespace().get(0));
            Assert.assertEquals(tableBucketARN, createResult.tableBucketARN());
            System.out.printf("Successfully created namespace: %s with ARN: %s%n", namespaceName, createResult.tableBucketARN());

            // 2. Get the created namespace to verify it exists
            GetNamespaceResult getResult = client.getNamespace(
                    GetNamespaceRequest.newBuilder()
                            .tableBucketARN(tableBucketARN)
                            .namespace(namespaceName)
                            .build());

            // Assert successful retrieval
            Assert.assertNotNull(getResult);
            Assert.assertEquals(200, getResult.statusCode());
            Assert.assertEquals(namespaceName, getResult.namespace().get(0));

            System.out.printf("Successfully retrieved namespace: %s%n", getResult.namespace().get(0));

            // 3. List namespaces to verify our namespace is included
            ListNamespacesResult listResult = client.listNamespaces(
                    ListNamespacesRequest.newBuilder()
                            .tableBucketARN(tableBucketARN)
                            .prefix("test_namespace_")
                            .maxNamespaces(10)
                            .build());

            // Assert successful listing
            Assert.assertNotNull(listResult);
            Assert.assertEquals(200, listResult.statusCode());
            Assert.assertNotNull(listResult.namespaces().get(0));
            Assert.assertTrue("Should contain at least one namespace", listResult.namespaces().size() >= 1);
            System.out.printf("Successfully listed namespaces, found %d namespaces%n", listResult.namespaces().size());

            // Find our namespace in the list
            boolean found = false;
            for (NamespaceSummary ns : listResult.namespaces()) {
                if (ns.namespace().get(0).equals(namespaceName)) {
                    found = true;
                    Assert.assertEquals(namespaceName, ns.namespace().get(0)); // Changed from Assert.assertEquals(namespaceName, ns.namespace());
                    // We can't directly verify tableBucketId since it's an internal ID, just verify namespace exists
                    break;
                }
            }
            Assert.assertTrue("Created namespace should be found in the list", found);

        } finally {
            // 4. Cleanup: Delete the test namespace if it still exists
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
            } catch (ServiceException e) {
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
            } catch (ServiceException e) {
                // If the table bucket was already deleted, that's fine
                System.out.printf("Cleanup: Could not delete table bucket (may have been already deleted): %s%n", 
                        e.getMessage());
            }
        }
    }

    @Test
    public void testListNamespacesWithParameters() {
        OSSTablesClient client = getTablesClient();
        String namespaceName1 = "test_namespace_1_" + System.currentTimeMillis();
        String namespaceName2 = "test_namespace_2_" + (System.currentTimeMillis() + 1);
        String bucketName = genTableBucketName();
        String tableBucketARN = null;

        // First create a table bucket to use for namespace operations
        CreateTableBucketResult createBucketResult = null;
        try {
            createBucketResult = client.createTableBucket(
                    CreateTableBucketRequest.newBuilder()
                            .name(bucketName)
                            .build());
        } catch (Exception e) {
            System.err.println("Error creating table bucket: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }

        Assert.assertNotNull(createBucketResult);
        Assert.assertEquals(200, createBucketResult.statusCode());
        Assert.assertNotNull(createBucketResult.arn());
        System.out.printf("Successfully created table bucket with ARN: %s%n", createBucketResult.arn());

        tableBucketARN = createBucketResult.arn();

        try {
            // 1. Create two namespaces
            CreateNamespaceResult createResult1 = null;
            try {
                createResult1 = client.createNamespace(
                        CreateNamespaceRequest.newBuilder()
                                .tableBucketARN(tableBucketARN)
                                .namespace(java.util.Arrays.asList(namespaceName1))
                                .build());
            } catch (Exception e) {
                System.err.println("Error creating first namespace: " + e.getMessage());
                e.printStackTrace();
                throw e;
            }
            
            CreateNamespaceResult createResult2 = null;
            try {
                createResult2 = client.createNamespace(
                        CreateNamespaceRequest.newBuilder()
                                .tableBucketARN(tableBucketARN)
                                .namespace(java.util.Arrays.asList(namespaceName2))
                                .build());
            } catch (Exception e) {
                System.err.println("Error creating second namespace: " + e.getMessage());
                e.printStackTrace();
                throw e;
            }

            // Assert successful creation
            Assert.assertNotNull(createResult1);
            Assert.assertEquals(200, createResult1.statusCode());
            Assert.assertNotNull(createResult2);
            Assert.assertEquals(200, createResult2.statusCode());
            System.out.printf("Successfully created namespaces: %s, %s%n", namespaceName1, namespaceName2);

            // 2. List namespaces with prefix filter
            ListNamespacesResult listResult = client.listNamespaces(
                    ListNamespacesRequest.newBuilder()
                            .tableBucketARN(tableBucketARN)
                            .prefix("test_namespace_")
                            .maxNamespaces(5)
                            .build());

            // Assert successful listing with filters
            Assert.assertNotNull(listResult);
            Assert.assertEquals(200, listResult.statusCode());
            Assert.assertNotNull(listResult.namespaces());
            Assert.assertTrue("Should contain at least two namespaces", listResult.namespaces().size() >= 2);
            System.out.printf("Successfully listed namespaces with prefix, found %d namespaces%n", listResult.namespaces().size());

            // Check that our namespaces are in the results
            boolean found1 = false;
            boolean found2 = false;
            for (NamespaceSummary ns : listResult.namespaces()) {
                if (ns.namespace().get(0).equals(namespaceName1)) {
                    found1 = true;
                }
                if (ns.namespace().get(0).equals(namespaceName2)) {
                    found2 = true;
                }
            }
            Assert.assertTrue("First created namespace should be found in the list", found1);
            Assert.assertTrue("Second created namespace should be found in the list", found2);

        } finally {
            // 3. Cleanup: Delete the test namespaces if they still exist
            try {
                DeleteNamespaceResult deleteResult1 = client.deleteNamespace(
                        DeleteNamespaceRequest.newBuilder()
                                .tableBucketARN(tableBucketARN)
                                .namespace(namespaceName1)
                                .build());
                Assert.assertNotNull(deleteResult1);
                Assert.assertEquals(204, deleteResult1.statusCode());
                
                DeleteNamespaceResult deleteResult2 = client.deleteNamespace(
                        DeleteNamespaceRequest.newBuilder()
                                .tableBucketARN(tableBucketARN)
                                .namespace(namespaceName2)
                                .build());
                Assert.assertNotNull(deleteResult2);
                Assert.assertEquals(204, deleteResult2.statusCode());
                
                System.out.printf("Successfully cleaned up namespaces%n");
            } catch (ServiceException e) {
                // If the namespaces were already deleted, that's fine
                System.out.printf("Cleanup: Could not delete namespaces (may have been already deleted): %s%n", 
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
            } catch (ServiceException e) {
                // If the table bucket was already deleted, that's fine
                System.out.printf("Cleanup: Could not delete table bucket (may have been already deleted): %s%n", 
                        e.getMessage());
            }
        }
    }
}