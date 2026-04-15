package com.aliyun.sdk.service.oss2.tables;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOptions;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.tables.models.*;

import java.util.concurrent.CompletableFuture;

/**
 * A client for accessing OSS Tables asynchronously.
 * This can be created using the static {@link #newBuilder()} method.
 */
public interface OSSAsyncTablesClient extends AutoCloseable {

    static OSSAsyncTablesClientBuilder newBuilder() {
        return new DefaultOSSAsyncTablesClientBuilder();
    }

    // common api
    //-----------------------------------------------------------------------
    default CompletableFuture<OperationOutput> invokeOperationAsync(OperationInput input, OperationOptions opts) {
        throw new UnsupportedOperationException();
    }


    //-----------------------------------------------------------------------

    // namespace api
    //-----------------------------------------------------------------------

    /**
     * Creates a namespace.
     *
     * @param request A {@link CreateNamespaceRequest} for CreateNamespace operation.
     * @return A Java Future containing the {@link CreateNamespaceResult} of the CreateNamespace operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<CreateNamespaceResult> createNamespaceAsync(CreateNamespaceRequest request) {
        return createNamespaceAsync(request, OperationOptions.defaults());
    }

    /**
     * Creates a namespace.
     *
     * @param request A {@link CreateNamespaceRequest} for CreateNamespace operation.
     * @param options The operation options.
     * @return A Java Future containing the {@link CreateNamespaceResult} of the CreateNamespace operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<CreateNamespaceResult> createNamespaceAsync(CreateNamespaceRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Deletes a namespace.
     *
     * @param request A {@link DeleteNamespaceRequest} for DeleteNamespace operation.
     * @return A Java Future containing the {@link DeleteNamespaceResult} of the DeleteNamespace operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<DeleteNamespaceResult> deleteNamespaceAsync(DeleteNamespaceRequest request) {
        return deleteNamespaceAsync(request, OperationOptions.defaults());
    }

    /**
     * Deletes a namespace.
     *
     * @param request A {@link DeleteNamespaceRequest} for DeleteNamespace operation.
     * @param options The operation options.
     * @return A Java Future containing the {@link DeleteNamespaceResult} of the DeleteNamespace operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<DeleteNamespaceResult> deleteNamespaceAsync(DeleteNamespaceRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Gets the information of a namespace.
     *
     * @param request A {@link GetNamespaceRequest} for GetNamespace operation.
     * @return A Java Future containing the {@link GetNamespaceResult} of the GetNamespace operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<GetNamespaceResult> getNamespaceAsync(GetNamespaceRequest request) {
        return getNamespaceAsync(request, OperationOptions.defaults());
    }

    /**
     * Gets the information of a namespace.
     *
     * @param request A {@link GetNamespaceRequest} for GetNamespace operation.
     * @param options The operation options.
     * @return A Java Future containing the {@link GetNamespaceResult} of the GetNamespace operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<GetNamespaceResult> getNamespaceAsync(GetNamespaceRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Lists namespaces.
     *
     * @param request A {@link ListNamespacesRequest} for ListNamespaces operation.
     * @return A Java Future containing the {@link ListNamespacesResult} of the ListNamespaces operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<ListNamespacesResult> listNamespacesAsync(ListNamespacesRequest request) {
        return listNamespacesAsync(request, OperationOptions.defaults());
    }

    /**
     * Lists namespaces.
     *
     * @param request A {@link ListNamespacesRequest} for ListNamespaces operation.
     * @param options The operation options.
     * @return A Java Future containing the {@link ListNamespacesResult} of the ListNamespaces operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<ListNamespacesResult> listNamespacesAsync(ListNamespacesRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }
    //-----------------------------------------------------------------------


    // table bucket api
    //-----------------------------------------------------------------------

    /**
     * Creates a table bucket.
     *
     * @param request A {@link CreateTableBucketRequest} for CreateTableBucket operation.
     * @return A Java Future containing the {@link CreateTableBucketResult} of the CreateTableBucket operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<CreateTableBucketResult> createTableBucketAsync(CreateTableBucketRequest request) {
        return createTableBucketAsync(request, OperationOptions.defaults());
    }

    /**
     * Creates a table bucket.
     *
     * @param request A {@link CreateTableBucketRequest} for CreateTableBucket operation.
     * @param options The operation options.
     * @return A Java Future containing the {@link CreateTableBucketResult} of the CreateTableBucket operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<CreateTableBucketResult> createTableBucketAsync(CreateTableBucketRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Deletes a table bucket.
     *
     * @param request A {@link DeleteTableBucketRequest} for DeleteTableBucket operation.
     * @return A Java Future containing the {@link DeleteTableBucketResult} of the DeleteTableBucket operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<DeleteTableBucketResult> deleteTableBucketAsync(DeleteTableBucketRequest request) {
        return deleteTableBucketAsync(request, OperationOptions.defaults());
    }

    /**
     * Deletes a table bucket.
     *
     * @param request A {@link DeleteTableBucketRequest} for DeleteTableBucket operation.
     * @param options The operation options.
     * @return A Java Future containing the {@link DeleteTableBucketResult} of the DeleteTableBucket operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<DeleteTableBucketResult> deleteTableBucketAsync(DeleteTableBucketRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Gets the information of a table bucket.
     *
     * @param request A {@link GetTableBucketRequest} for GetTableBucket operation.
     * @return A Java Future containing the {@link GetTableBucketResult} of the GetTableBucket operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<GetTableBucketResult> getTableBucketAsync(GetTableBucketRequest request) {
        return getTableBucketAsync(request, OperationOptions.defaults());
    }

    /**
     * Gets the information of a table bucket.
     *
     * @param request A {@link GetTableBucketRequest} for GetTableBucket operation.
     * @param options The operation options.
     * @return A Java Future containing the {@link GetTableBucketResult} of the GetTableBucket operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<GetTableBucketResult> getTableBucketAsync(GetTableBucketRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Lists table buckets.
     *
     * @param request A {@link ListTableBucketsRequest} for ListTableBuckets operation.
     * @return A Java Future containing the {@link ListTableBucketsResult} of the ListTableBuckets operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<ListTableBucketsResult> listTableBucketsAsync(ListTableBucketsRequest request) {
        return listTableBucketsAsync(request, OperationOptions.defaults());
    }

    /**
     * Lists table buckets.
     *
     * @param request A {@link ListTableBucketsRequest} for ListTableBuckets operation.
     * @param options The operation options.
     * @return A Java Future containing the {@link ListTableBucketsResult} of the ListTableBuckets operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<ListTableBucketsResult> listTableBucketsAsync(ListTableBucketsRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }
    //-----------------------------------------------------------------------


    // table bucket config api
    //-----------------------------------------------------------------------

    /**
     * Deletes the encryption configuration of a table bucket.
     *
     * @param request A {@link DeleteTableBucketEncryptionRequest} for DeleteTableBucketEncryption operation.
     * @return A Java Future containing the {@link DeleteTableBucketEncryptionResult} of the DeleteTableBucketEncryption operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<DeleteTableBucketEncryptionResult> deleteTableBucketEncryptionAsync(DeleteTableBucketEncryptionRequest request) {
        return deleteTableBucketEncryptionAsync(request, OperationOptions.defaults());
    }

    /**
     * Deletes the encryption configuration of a table bucket.
     *
     * @param request A {@link DeleteTableBucketEncryptionRequest} for DeleteTableBucketEncryption operation.
     * @param options The operation options.
     * @return A Java Future containing the {@link DeleteTableBucketEncryptionResult} of the DeleteTableBucketEncryption operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<DeleteTableBucketEncryptionResult> deleteTableBucketEncryptionAsync(DeleteTableBucketEncryptionRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Gets the encryption configuration of a table bucket.
     *
     * @param request A {@link GetTableBucketEncryptionRequest} for GetTableBucketEncryption operation.
     * @return A Java Future containing the {@link GetTableBucketEncryptionResult} of the GetTableBucketEncryption operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<GetTableBucketEncryptionResult> getTableBucketEncryptionAsync(GetTableBucketEncryptionRequest request) {
        return getTableBucketEncryptionAsync(request, OperationOptions.defaults());
    }

    /**
     * Gets the encryption configuration of a table bucket.
     *
     * @param request A {@link GetTableBucketEncryptionRequest} for GetTableBucketEncryption operation.
     * @param options The operation options.
     * @return A Java Future containing the {@link GetTableBucketEncryptionResult} of the GetTableBucketEncryption operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<GetTableBucketEncryptionResult> getTableBucketEncryptionAsync(GetTableBucketEncryptionRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Sets the encryption configuration of a table bucket.
     *
     * @param request A {@link PutTableBucketEncryptionRequest} for PutTableBucketEncryption operation.
     * @return A Java Future containing the {@link PutTableBucketEncryptionResult} of the PutTableBucketEncryption operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<PutTableBucketEncryptionResult> putTableBucketEncryptionAsync(PutTableBucketEncryptionRequest request) {
        return putTableBucketEncryptionAsync(request, OperationOptions.defaults());
    }

    /**
     * Sets the encryption configuration of a table bucket.
     *
     * @param request A {@link PutTableBucketEncryptionRequest} for PutTableBucketEncryption operation.
     * @param options The operation options.
     * @return A Java Future containing the {@link PutTableBucketEncryptionResult} of the PutTableBucketEncryption operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<PutTableBucketEncryptionResult> putTableBucketEncryptionAsync(PutTableBucketEncryptionRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Gets the maintenance configuration of a table bucket.
     *
     * @param request A {@link GetTableBucketMaintenanceConfigurationRequest} for GetTableBucketMaintenanceConfiguration operation.
     * @return A Java Future containing the {@link GetTableBucketMaintenanceConfigurationResult} of the GetTableBucketMaintenanceConfiguration operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<GetTableBucketMaintenanceConfigurationResult> getTableBucketMaintenanceConfigurationAsync(GetTableBucketMaintenanceConfigurationRequest request) {
        return getTableBucketMaintenanceConfigurationAsync(request, OperationOptions.defaults());
    }

    /**
     * Gets the maintenance configuration of a table bucket.
     *
     * @param request A {@link GetTableBucketMaintenanceConfigurationRequest} for GetTableBucketMaintenanceConfiguration operation.
     * @param options The operation options.
     * @return A Java Future containing the {@link GetTableBucketMaintenanceConfigurationResult} of the GetTableBucketMaintenanceConfiguration operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<GetTableBucketMaintenanceConfigurationResult> getTableBucketMaintenanceConfigurationAsync(GetTableBucketMaintenanceConfigurationRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Sets the maintenance configuration of a table bucket.
     *
     * @param request A {@link PutTableBucketMaintenanceConfigurationRequest} for PutTableBucketMaintenanceConfiguration operation.
     * @return A Java Future containing the {@link PutTableBucketMaintenanceConfigurationResult} of the PutTableBucketMaintenanceConfiguration operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<PutTableBucketMaintenanceConfigurationResult> putTableBucketMaintenanceConfigurationAsync(PutTableBucketMaintenanceConfigurationRequest request) {
        return putTableBucketMaintenanceConfigurationAsync(request, OperationOptions.defaults());
    }

    /**
     * Sets the maintenance configuration of a table bucket.
     *
     * @param request A {@link PutTableBucketMaintenanceConfigurationRequest} for PutTableBucketMaintenanceConfiguration operation.
     * @param options The operation options.
     * @return A Java Future containing the {@link PutTableBucketMaintenanceConfigurationResult} of the PutTableBucketMaintenanceConfiguration operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<PutTableBucketMaintenanceConfigurationResult> putTableBucketMaintenanceConfigurationAsync(PutTableBucketMaintenanceConfigurationRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }


    /**
     * Deletes the policy of a table bucket.
     *
     * @param request A {@link DeleteTableBucketPolicyRequest} for DeleteTableBucketPolicy operation.
     * @return A Java Future containing the {@link DeleteTableBucketPolicyResult} of the DeleteTableBucketPolicy operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<DeleteTableBucketPolicyResult> deleteTableBucketPolicyAsync(DeleteTableBucketPolicyRequest request) {
        return deleteTableBucketPolicyAsync(request, OperationOptions.defaults());
    }

    /**
     * Deletes the policy of a table bucket.
     *
     * @param request A {@link DeleteTableBucketPolicyRequest} for DeleteTableBucketPolicy operation.
     * @param options The operation options.
     * @return A Java Future containing the {@link DeleteTableBucketPolicyResult} of the DeleteTableBucketPolicy operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<DeleteTableBucketPolicyResult> deleteTableBucketPolicyAsync(DeleteTableBucketPolicyRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Gets the policy of a table bucket.
     *
     * @param request A {@link GetTableBucketPolicyRequest} for GetTableBucketPolicy operation.
     * @return A Java Future containing the {@link GetTableBucketPolicyResult} of the GetTableBucketPolicy operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<GetTableBucketPolicyResult> getTableBucketPolicyAsync(GetTableBucketPolicyRequest request) {
        return getTableBucketPolicyAsync(request, OperationOptions.defaults());
    }

    /**
     * Gets the policy of a table bucket.
     *
     * @param request A {@link GetTableBucketPolicyRequest} for GetTableBucketPolicy operation.
     * @param options The operation options.
     * @return A Java Future containing the {@link GetTableBucketPolicyResult} of the GetTableBucketPolicy operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<GetTableBucketPolicyResult> getTableBucketPolicyAsync(GetTableBucketPolicyRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Sets the policy of a table bucket.
     *
     * @param request A {@link PutTableBucketPolicyRequest} for PutTableBucketPolicy operation.
     * @return A Java Future containing the {@link PutTableBucketPolicyResult} of the PutTableBucketPolicy operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<PutTableBucketPolicyResult> putTableBucketPolicyAsync(PutTableBucketPolicyRequest request) {
        return putTableBucketPolicyAsync(request, OperationOptions.defaults());
    }

    /**
     * Sets the policy of a table bucket.
     *
     * @param request A {@link PutTableBucketPolicyRequest} for PutTableBucketPolicy operation.
     * @param options The operation options.
     * @return A Java Future containing the {@link PutTableBucketPolicyResult} of the PutTableBucketPolicy operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<PutTableBucketPolicyResult> putTableBucketPolicyAsync(PutTableBucketPolicyRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }



    // table api
    //-----------------------------------------------------------------------

    /**
     * Creates a table.
     *
     * @param request A {@link CreateTableRequest} for CreateTable operation.
     * @return A Java Future containing the {@link CreateTableResult} of the CreateTable operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<CreateTableResult> createTableAsync(CreateTableRequest request) {
        return createTableAsync(request, OperationOptions.defaults());
    }

    /**
     * Creates a table.
     *
     * @param request A {@link CreateTableRequest} for CreateTable operation.
     * @param options The operation options.
     * @return A Java Future containing the {@link CreateTableResult} of the CreateTable operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<CreateTableResult> createTableAsync(CreateTableRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Deletes a table.
     *
     * @param request A {@link DeleteTableRequest} for DeleteTable operation.
     * @return A Java Future containing the {@link DeleteTableResult} of the DeleteTable operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<DeleteTableResult> deleteTableAsync(DeleteTableRequest request) {
        return deleteTableAsync(request, OperationOptions.defaults());
    }

    /**
     * Deletes a table.
     *
     * @param request A {@link DeleteTableRequest} for DeleteTable operation.
     * @param options The operation options.
     * @return A Java Future containing the {@link DeleteTableResult} of the DeleteTable operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<DeleteTableResult> deleteTableAsync(DeleteTableRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Gets the information of a table.
     *
     * @param request A {@link GetTableRequest} for GetTable operation.
     * @return A Java Future containing the {@link GetTableResult} of the GetTable operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<GetTableResult> getTableAsync(GetTableRequest request) {
        return getTableAsync(request, OperationOptions.defaults());
    }

    /**
     * Gets the information of a table.
     *
     * @param request A {@link GetTableRequest} for GetTable operation.
     * @param options The operation options.
     * @return A Java Future containing the {@link GetTableResult} of the GetTable operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<GetTableResult> getTableAsync(GetTableRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Lists tables.
     *
     * @param request A {@link ListTablesRequest} for ListTables operation.
     * @return A Java Future containing the {@link ListTablesResult} of the ListTables operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<ListTablesResult> listTablesAsync(ListTablesRequest request) {
        return listTablesAsync(request, OperationOptions.defaults());
    }

    /**
     * Lists tables.
     *
     * @param request A {@link ListTablesRequest} for ListTables operation.
     * @param options The operation options.
     * @return A Java Future containing the {@link ListTablesResult} of the ListTables operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<ListTablesResult> listTablesAsync(ListTablesRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Renames a table.
     *
     * @param request A {@link RenameTableRequest} for RenameTable operation.
     * @return A Java Future containing the {@link RenameTableResult} of the RenameTable operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<RenameTableResult> renameTableAsync(RenameTableRequest request) {
        return renameTableAsync(request, OperationOptions.defaults());
    }

    /**
     * Renames a table.
     *
     * @param request A {@link RenameTableRequest} for RenameTable operation.
     * @param options The operation options.
     * @return A Java Future containing the {@link RenameTableResult} of the RenameTable operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<RenameTableResult> renameTableAsync(RenameTableRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }
    //-----------------------------------------------------------------------


    // table config api
    //-----------------------------------------------------------------------

    /**
     * Gets the encryption configuration of a table.
     *
     * @param request A {@link GetTableEncryptionRequest} for GetTableEncryption operation.
     * @return A Java Future containing the {@link GetTableEncryptionResult} of the GetTableEncryption operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<GetTableEncryptionResult> getTableEncryptionAsync(GetTableEncryptionRequest request) {
        return getTableEncryptionAsync(request, OperationOptions.defaults());
    }

    /**
     * Gets the encryption configuration of a table.
     *
     * @param request A {@link GetTableEncryptionRequest} for GetTableEncryption operation.
     * @param options The operation options.
     * @return A Java Future containing the {@link GetTableEncryptionResult} of the GetTableEncryption operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<GetTableEncryptionResult> getTableEncryptionAsync(GetTableEncryptionRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Gets the maintenance configuration of a table.
     *
     * @param request A {@link GetTableMaintenanceConfigurationRequest} for GetTableMaintenanceConfiguration operation.
     * @return A Java Future containing the {@link GetTableMaintenanceConfigurationResult} of the GetTableMaintenanceConfiguration operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<GetTableMaintenanceConfigurationResult> getTableMaintenanceConfigurationAsync(GetTableMaintenanceConfigurationRequest request) {
        return getTableMaintenanceConfigurationAsync(request, OperationOptions.defaults());
    }

    /**
     * Gets the maintenance configuration of a table.
     *
     * @param request A {@link GetTableMaintenanceConfigurationRequest} for GetTableMaintenanceConfiguration operation.
     * @param options The operation options.
     * @return A Java Future containing the {@link GetTableMaintenanceConfigurationResult} of the GetTableMaintenanceConfiguration operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<GetTableMaintenanceConfigurationResult> getTableMaintenanceConfigurationAsync(GetTableMaintenanceConfigurationRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Sets the maintenance configuration of a table.
     *
     * @param request A {@link PutTableMaintenanceConfigurationRequest} for PutTableMaintenanceConfiguration operation.
     * @return A Java Future containing the {@link PutTableMaintenanceConfigurationResult} of the PutTableMaintenanceConfiguration operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<PutTableMaintenanceConfigurationResult> putTableMaintenanceConfigurationAsync(PutTableMaintenanceConfigurationRequest request) {
        return putTableMaintenanceConfigurationAsync(request, OperationOptions.defaults());
    }

    /**
     * Sets the maintenance configuration of a table.
     *
     * @param request A {@link PutTableMaintenanceConfigurationRequest} for PutTableMaintenanceConfiguration operation.
     * @param options The operation options.
     * @return A Java Future containing the {@link PutTableMaintenanceConfigurationResult} of the PutTableMaintenanceConfiguration operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<PutTableMaintenanceConfigurationResult> putTableMaintenanceConfigurationAsync(PutTableMaintenanceConfigurationRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Gets the maintenance job status of a table.
     *
     * @param request A {@link GetTableMaintenanceJobStatusRequest} for GetTableMaintenanceJobStatus operation.
     * @return A Java Future containing the {@link GetTableMaintenanceJobStatusResult} of the GetTableMaintenanceJobStatus operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<GetTableMaintenanceJobStatusResult> getTableMaintenanceJobStatusAsync(GetTableMaintenanceJobStatusRequest request) {
        return getTableMaintenanceJobStatusAsync(request, OperationOptions.defaults());
    }

    /**
     * Gets the maintenance job status of a table.
     *
     * @param request A {@link GetTableMaintenanceJobStatusRequest} for GetTableMaintenanceJobStatus operation.
     * @param options The operation options.
     * @return A Java Future containing the {@link GetTableMaintenanceJobStatusResult} of the GetTableMaintenanceJobStatus operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<GetTableMaintenanceJobStatusResult> getTableMaintenanceJobStatusAsync(GetTableMaintenanceJobStatusRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Gets the metadata location of a table.
     *
     * @param request A {@link GetTableMetadataLocationRequest} for GetTableMetadataLocation operation.
     * @return A Java Future containing the {@link GetTableMetadataLocationResult} of the GetTableMetadataLocation operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<GetTableMetadataLocationResult> getTableMetadataLocationAsync(GetTableMetadataLocationRequest request) {
        return getTableMetadataLocationAsync(request, OperationOptions.defaults());
    }

    /**
     * Gets the metadata location of a table.
     *
     * @param request A {@link GetTableMetadataLocationRequest} for GetTableMetadataLocation operation.
     * @param options The operation options.
     * @return A Java Future containing the {@link GetTableMetadataLocationResult} of the GetTableMetadataLocation operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<GetTableMetadataLocationResult> getTableMetadataLocationAsync(GetTableMetadataLocationRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Updates the metadata location of a table.
     *
     * @param request A {@link UpdateTableMetadataLocationRequest} for UpdateTableMetadataLocation operation.
     * @return A Java Future containing the {@link UpdateTableMetadataLocationResult} of the UpdateTableMetadataLocation operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<UpdateTableMetadataLocationResult> updateTableMetadataLocationAsync(UpdateTableMetadataLocationRequest request) {
        return updateTableMetadataLocationAsync(request, OperationOptions.defaults());
    }

    /**
     * Updates the metadata location of a table.
     *
     * @param request A {@link UpdateTableMetadataLocationRequest} for UpdateTableMetadataLocation operation.
     * @param options The operation options.
     * @return A Java Future containing the {@link UpdateTableMetadataLocationResult} of the UpdateTableMetadataLocation operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<UpdateTableMetadataLocationResult> updateTableMetadataLocationAsync(UpdateTableMetadataLocationRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Deletes the policy of a table.
     *
     * @param request A {@link DeleteTablePolicyRequest} for DeleteTablePolicy operation.
     * @return A Java Future containing the {@link DeleteTablePolicyResult} of the DeleteTablePolicy operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<DeleteTablePolicyResult> deleteTablePolicyAsync(DeleteTablePolicyRequest request) {
        return deleteTablePolicyAsync(request, OperationOptions.defaults());
    }

    /**
     * Deletes the policy of a table.
     *
     * @param request A {@link DeleteTablePolicyRequest} for DeleteTablePolicy operation.
     * @param options The operation options.
     * @return A Java Future containing the {@link DeleteTablePolicyResult} of the DeleteTablePolicy operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<DeleteTablePolicyResult> deleteTablePolicyAsync(DeleteTablePolicyRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Gets the policy of a table.
     *
     * @param request A {@link GetTablePolicyRequest} for GetTablePolicy operation.
     * @return A Java Future containing the {@link GetTablePolicyResult} of the GetTablePolicy operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<GetTablePolicyResult> getTablePolicyAsync(GetTablePolicyRequest request) {
        return getTablePolicyAsync(request, OperationOptions.defaults());
    }

    /**
     * Gets the policy of a table.
     *
     * @param request A {@link GetTablePolicyRequest} for GetTablePolicy operation.
     * @param options The operation options.
     * @return A Java Future containing the {@link GetTablePolicyResult} of the GetTablePolicy operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<GetTablePolicyResult> getTablePolicyAsync(GetTablePolicyRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Sets the policy of a table.
     *
     * @param request A {@link PutTablePolicyRequest} for PutTablePolicy operation.
     * @return A Java Future containing the {@link PutTablePolicyResult} of the PutTablePolicy operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<PutTablePolicyResult> putTablePolicyAsync(PutTablePolicyRequest request) {
        return putTablePolicyAsync(request, OperationOptions.defaults());
    }

    /**
     * Sets the policy of a table.
     *
     * @param request A {@link PutTablePolicyRequest} for PutTablePolicy operation.
     * @param options The operation options.
     * @return A Java Future containing the {@link PutTablePolicyResult} of the PutTablePolicy operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<PutTablePolicyResult> putTablePolicyAsync(PutTablePolicyRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

}
