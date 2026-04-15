package com.aliyun.sdk.service.oss2.tables;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOptions;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.tables.models.*;


/**
 * A client for accessing OSS Tables synchronously.
 * This can be created using the static {@link #newBuilder()} method.
 */
public interface OSSTablesClient extends AutoCloseable {

    static OSSTablesClientBuilder newBuilder() {
        return new DefaultOSSTablesClientBuilder();
    }

    // common api
    //-----------------------------------------------------------------------
    default OperationOutput invokeOperation(OperationInput input, OperationOptions opts) {
        throw new UnsupportedOperationException();
    }
    //-----------------------------------------------------------------------


    // namespace api
    //-----------------------------------------------------------------------

    /**
     * Creates a namespace.
     *
     * @param request A {@link CreateNamespaceRequest} for CreateNamespace operation.
     * @return A {@link CreateNamespaceResult} for CreateNamespace operation.
     * @throws RuntimeException If an error occurs
     */
    default CreateNamespaceResult createNamespace(CreateNamespaceRequest request) {
        return createNamespace(request, OperationOptions.defaults());
    }

    /**
     * Creates a namespace.
     *
     * @param request A {@link CreateNamespaceRequest} for CreateNamespace operation.
     * @param options The operation options.
     * @return A {@link CreateNamespaceResult} for CreateNamespace operation.
     * @throws RuntimeException If an error occurs
     */
    default CreateNamespaceResult createNamespace(CreateNamespaceRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Deletes a namespace.
     *
     * @param request A {@link DeleteNamespaceRequest} for DeleteNamespace operation.
     * @return A {@link DeleteNamespaceResult} for DeleteNamespace operation.
     * @throws RuntimeException If an error occurs
     */
    default DeleteNamespaceResult deleteNamespace(DeleteNamespaceRequest request) {
        return deleteNamespace(request, OperationOptions.defaults());
    }

    /**
     * Deletes a namespace.
     *
     * @param request A {@link DeleteNamespaceRequest} for DeleteNamespace operation.
     * @param options The operation options.
     * @return A {@link DeleteNamespaceResult} for DeleteNamespace operation.
     * @throws RuntimeException If an error occurs
     */
    default DeleteNamespaceResult deleteNamespace(DeleteNamespaceRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Gets the information of a namespace.
     *
     * @param request A {@link GetNamespaceRequest} for GetNamespace operation.
     * @return A {@link GetNamespaceResult} for GetNamespace operation.
     * @throws RuntimeException If an error occurs
     */
    default GetNamespaceResult getNamespace(GetNamespaceRequest request) {
        return getNamespace(request, OperationOptions.defaults());
    }

    /**
     * Gets the information of a namespace.
     *
     * @param request A {@link GetNamespaceRequest} for GetNamespace operation.
     * @param options The operation options.
     * @return A {@link GetNamespaceResult} for GetNamespace operation.
     * @throws RuntimeException If an error occurs
     */
    default GetNamespaceResult getNamespace(GetNamespaceRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Lists namespaces.
     *
     * @param request A {@link ListNamespacesRequest} for ListNamespaces operation.
     * @return A {@link ListNamespacesResult} for ListNamespaces operation.
     * @throws RuntimeException If an error occurs
     */
    default ListNamespacesResult listNamespaces(ListNamespacesRequest request) {
        return listNamespaces(request, OperationOptions.defaults());
    }

    /**
     * Lists namespaces.
     *
     * @param request A {@link ListNamespacesRequest} for ListNamespaces operation.
     * @param options The operation options.
     * @return A {@link ListNamespacesResult} for ListNamespaces operation.
     * @throws RuntimeException If an error occurs
     */
    default ListNamespacesResult listNamespaces(ListNamespacesRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }
    //-----------------------------------------------------------------------


    // table bucket api
    //-----------------------------------------------------------------------

    /**
     * Creates a table bucket.
     *
     * @param request A {@link CreateTableBucketRequest} for CreateTableBucket operation.
     * @return A {@link CreateTableBucketResult} for CreateTableBucket operation.
     * @throws RuntimeException If an error occurs
     */
    default CreateTableBucketResult createTableBucket(CreateTableBucketRequest request) {
        return createTableBucket(request, OperationOptions.defaults());
    }

    /**
     * Creates a table bucket.
     *
     * @param request A {@link CreateTableBucketRequest} for CreateTableBucket operation.
     * @param options The operation options.
     * @return A {@link CreateTableBucketResult} for CreateTableBucket operation.
     * @throws RuntimeException If an error occurs
     */
    default CreateTableBucketResult createTableBucket(CreateTableBucketRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Deletes a table bucket.
     *
     * @param request A {@link DeleteTableBucketRequest} for DeleteTableBucket operation.
     * @return A {@link DeleteTableBucketResult} for DeleteTableBucket operation.
     * @throws RuntimeException If an error occurs
     */
    default DeleteTableBucketResult deleteTableBucket(DeleteTableBucketRequest request) {
        return deleteTableBucket(request, OperationOptions.defaults());
    }

    /**
     * Deletes a table bucket.
     *
     * @param request A {@link DeleteTableBucketRequest} for DeleteTableBucket operation.
     * @param options The operation options.
     * @return A {@link DeleteTableBucketResult} for DeleteTableBucket operation.
     * @throws RuntimeException If an error occurs
     */
    default DeleteTableBucketResult deleteTableBucket(DeleteTableBucketRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Gets the information of a table bucket.
     *
     * @param request A {@link GetTableBucketRequest} for GetTableBucket operation.
     * @return A {@link GetTableBucketResult} for GetTableBucket operation.
     * @throws RuntimeException If an error occurs
     */
    default GetTableBucketResult getTableBucket(GetTableBucketRequest request) {
        return getTableBucket(request, OperationOptions.defaults());
    }

    /**
     * Gets the information of a table bucket.
     *
     * @param request A {@link GetTableBucketRequest} for GetTableBucket operation.
     * @param options The operation options.
     * @return A {@link GetTableBucketResult} for GetTableBucket operation.
     * @throws RuntimeException If an error occurs
     */
    default GetTableBucketResult getTableBucket(GetTableBucketRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Lists table buckets.
     *
     * @param request A {@link ListTableBucketsRequest} for ListTableBuckets operation.
     * @return A {@link ListTableBucketsResult} for ListTableBuckets operation.
     * @throws RuntimeException If an error occurs
     */
    default ListTableBucketsResult listTableBuckets(ListTableBucketsRequest request) {
        return listTableBuckets(request, OperationOptions.defaults());
    }

    /**
     * Lists table buckets.
     *
     * @param request A {@link ListTableBucketsRequest} for ListTableBuckets operation.
     * @param options The operation options.
     * @return A {@link ListTableBucketsResult} for ListTableBuckets operation.
     * @throws RuntimeException If an error occurs
     */
    default ListTableBucketsResult listTableBuckets(ListTableBucketsRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }
    //-----------------------------------------------------------------------


    // table bucket config api
    //-----------------------------------------------------------------------

    /**
     * Deletes the encryption configuration of a table bucket.
     *
     * @param request A {@link DeleteTableBucketEncryptionRequest} for DeleteTableBucketEncryption operation.
     * @return A {@link DeleteTableBucketEncryptionResult} for DeleteTableBucketEncryption operation.
     * @throws RuntimeException If an error occurs
     */
    default DeleteTableBucketEncryptionResult deleteTableBucketEncryption(DeleteTableBucketEncryptionRequest request) {
        return deleteTableBucketEncryption(request, OperationOptions.defaults());
    }

    /**
     * Deletes the encryption configuration of a table bucket.
     *
     * @param request A {@link DeleteTableBucketEncryptionRequest} for DeleteTableBucketEncryption operation.
     * @param options The operation options.
     * @return A {@link DeleteTableBucketEncryptionResult} for DeleteTableBucketEncryption operation.
     * @throws RuntimeException If an error occurs
     */
    default DeleteTableBucketEncryptionResult deleteTableBucketEncryption(DeleteTableBucketEncryptionRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Gets the encryption configuration of a table bucket.
     *
     * @param request A {@link GetTableBucketEncryptionRequest} for GetTableBucketEncryption operation.
     * @return A {@link GetTableBucketEncryptionResult} for GetTableBucketEncryption operation.
     * @throws RuntimeException If an error occurs
     */
    default GetTableBucketEncryptionResult getTableBucketEncryption(GetTableBucketEncryptionRequest request) {
        return getTableBucketEncryption(request, OperationOptions.defaults());
    }

    /**
     * Gets the encryption configuration of a table bucket.
     *
     * @param request A {@link GetTableBucketEncryptionRequest} for GetTableBucketEncryption operation.
     * @param options The operation options.
     * @return A {@link GetTableBucketEncryptionResult} for GetTableBucketEncryption operation.
     * @throws RuntimeException If an error occurs
     */
    default GetTableBucketEncryptionResult getTableBucketEncryption(GetTableBucketEncryptionRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Sets the encryption configuration of a table bucket.
     *
     * @param request A {@link PutTableBucketEncryptionRequest} for PutTableBucketEncryption operation.
     * @return A {@link PutTableBucketEncryptionResult} for PutTableBucketEncryption operation.
     * @throws RuntimeException If an error occurs
     */
    default PutTableBucketEncryptionResult putTableBucketEncryption(PutTableBucketEncryptionRequest request) {
        return putTableBucketEncryption(request, OperationOptions.defaults());
    }

    /**
     * Sets the encryption configuration of a table bucket.
     *
     * @param request A {@link PutTableBucketEncryptionRequest} for PutTableBucketEncryption operation.
     * @param options The operation options.
     * @return A {@link PutTableBucketEncryptionResult} for PutTableBucketEncryption operation.
     * @throws RuntimeException If an error occurs
     */
    default PutTableBucketEncryptionResult putTableBucketEncryption(PutTableBucketEncryptionRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Gets the maintenance configuration of a table bucket.
     *
     * @param request A {@link GetTableBucketMaintenanceConfigurationRequest} for GetTableBucketMaintenanceConfiguration operation.
     * @return A {@link GetTableBucketMaintenanceConfigurationResult} for GetTableBucketMaintenanceConfiguration operation.
     * @throws RuntimeException If an error occurs
     */
    default GetTableBucketMaintenanceConfigurationResult getTableBucketMaintenanceConfiguration(GetTableBucketMaintenanceConfigurationRequest request) {
        return getTableBucketMaintenanceConfiguration(request, OperationOptions.defaults());
    }

    /**
     * Gets the maintenance configuration of a table bucket.
     *
     * @param request A {@link GetTableBucketMaintenanceConfigurationRequest} for GetTableBucketMaintenanceConfiguration operation.
     * @param options The operation options.
     * @return A {@link GetTableBucketMaintenanceConfigurationResult} for GetTableBucketMaintenanceConfiguration operation.
     * @throws RuntimeException If an error occurs
     */
    default GetTableBucketMaintenanceConfigurationResult getTableBucketMaintenanceConfiguration(GetTableBucketMaintenanceConfigurationRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Sets the maintenance configuration of a table bucket.
     *
     * @param request A {@link PutTableBucketMaintenanceConfigurationRequest} for PutTableBucketMaintenanceConfiguration operation.
     * @return A {@link PutTableBucketMaintenanceConfigurationResult} for PutTableBucketMaintenanceConfiguration operation.
     * @throws RuntimeException If an error occurs
     */
    default PutTableBucketMaintenanceConfigurationResult putTableBucketMaintenanceConfiguration(PutTableBucketMaintenanceConfigurationRequest request) {
        return putTableBucketMaintenanceConfiguration(request, OperationOptions.defaults());
    }

    /**
     * Sets the maintenance configuration of a table bucket.
     *
     * @param request A {@link PutTableBucketMaintenanceConfigurationRequest} for PutTableBucketMaintenanceConfiguration operation.
     * @param options The operation options.
     * @return A {@link PutTableBucketMaintenanceConfigurationResult} for PutTableBucketMaintenanceConfiguration operation.
     * @throws RuntimeException If an error occurs
     */
    default PutTableBucketMaintenanceConfigurationResult putTableBucketMaintenanceConfiguration(PutTableBucketMaintenanceConfigurationRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }



    /**
     * Deletes the policy of a table bucket.
     *
     * @param request A {@link DeleteTableBucketPolicyRequest} for DeleteTableBucketPolicy operation.
     * @return A {@link DeleteTableBucketPolicyResult} for DeleteTableBucketPolicy operation.
     * @throws RuntimeException If an error occurs
     */
    default DeleteTableBucketPolicyResult deleteTableBucketPolicy(DeleteTableBucketPolicyRequest request) {
        return deleteTableBucketPolicy(request, OperationOptions.defaults());
    }

    /**
     * Deletes the policy of a table bucket.
     *
     * @param request A {@link DeleteTableBucketPolicyRequest} for DeleteTableBucketPolicy operation.
     * @param options The operation options.
     * @return A {@link DeleteTableBucketPolicyResult} for DeleteTableBucketPolicy operation.
     * @throws RuntimeException If an error occurs
     */
    default DeleteTableBucketPolicyResult deleteTableBucketPolicy(DeleteTableBucketPolicyRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Gets the policy of a table bucket.
     *
     * @param request A {@link GetTableBucketPolicyRequest} for GetTableBucketPolicy operation.
     * @return A {@link GetTableBucketPolicyResult} for GetTableBucketPolicy operation.
     * @throws RuntimeException If an error occurs
     */
    default GetTableBucketPolicyResult getTableBucketPolicy(GetTableBucketPolicyRequest request) {
        return getTableBucketPolicy(request, OperationOptions.defaults());
    }

    /**
     * Gets the policy of a table bucket.
     *
     * @param request A {@link GetTableBucketPolicyRequest} for GetTableBucketPolicy operation.
     * @param options The operation options.
     * @return A {@link GetTableBucketPolicyResult} for GetTableBucketPolicy operation.
     * @throws RuntimeException If an error occurs
     */
    default GetTableBucketPolicyResult getTableBucketPolicy(GetTableBucketPolicyRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Sets the policy of a table bucket.
     *
     * @param request A {@link PutTableBucketPolicyRequest} for PutTableBucketPolicy operation.
     * @return A {@link PutTableBucketPolicyResult} for PutTableBucketPolicy operation.
     * @throws RuntimeException If an error occurs
     */
    default PutTableBucketPolicyResult putTableBucketPolicy(PutTableBucketPolicyRequest request) {
        return putTableBucketPolicy(request, OperationOptions.defaults());
    }

    /**
     * Sets the policy of a table bucket.
     *
     * @param request A {@link PutTableBucketPolicyRequest} for PutTableBucketPolicy operation.
     * @param options The operation options.
     * @return A {@link PutTableBucketPolicyResult} for PutTableBucketPolicy operation.
     * @throws RuntimeException If an error occurs
     */
    default PutTableBucketPolicyResult putTableBucketPolicy(PutTableBucketPolicyRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }



    // table api
    //-----------------------------------------------------------------------

    /**
     * Creates a table.
     *
     * @param request A {@link CreateTableRequest} for CreateTable operation.
     * @return A {@link CreateTableResult} for CreateTable operation.
     * @throws RuntimeException If an error occurs
     */
    default CreateTableResult createTable(CreateTableRequest request) {
        return createTable(request, OperationOptions.defaults());
    }

    /**
     * Creates a table.
     *
     * @param request A {@link CreateTableRequest} for CreateTable operation.
     * @param options The operation options.
     * @return A {@link CreateTableResult} for CreateTable operation.
     * @throws RuntimeException If an error occurs
     */
    default CreateTableResult createTable(CreateTableRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Deletes a table.
     *
     * @param request A {@link DeleteTableRequest} for DeleteTable operation.
     * @return A {@link DeleteTableResult} for DeleteTable operation.
     * @throws RuntimeException If an error occurs
     */
    default DeleteTableResult deleteTable(DeleteTableRequest request) {
        return deleteTable(request, OperationOptions.defaults());
    }

    /**
     * Deletes a table.
     *
     * @param request A {@link DeleteTableRequest} for DeleteTable operation.
     * @param options The operation options.
     * @return A {@link DeleteTableResult} for DeleteTable operation.
     * @throws RuntimeException If an error occurs
     */
    default DeleteTableResult deleteTable(DeleteTableRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Gets the information of a table.
     *
     * @param request A {@link GetTableRequest} for GetTable operation.
     * @return A {@link GetTableResult} for GetTable operation.
     * @throws RuntimeException If an error occurs
     */
    default GetTableResult getTable(GetTableRequest request) {
        return getTable(request, OperationOptions.defaults());
    }

    /**
     * Gets the information of a table.
     *
     * @param request A {@link GetTableRequest} for GetTable operation.
     * @param options The operation options.
     * @return A {@link GetTableResult} for GetTable operation.
     * @throws RuntimeException If an error occurs
     */
    default GetTableResult getTable(GetTableRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Lists tables.
     *
     * @param request A {@link ListTablesRequest} for ListTables operation.
     * @return A {@link ListTablesResult} for ListTables operation.
     * @throws RuntimeException If an error occurs
     */
    default ListTablesResult listTables(ListTablesRequest request) {
        return listTables(request, OperationOptions.defaults());
    }

    /**
     * Lists tables.
     *
     * @param request A {@link ListTablesRequest} for ListTables operation.
     * @param options The operation options.
     * @return A {@link ListTablesResult} for ListTables operation.
     * @throws RuntimeException If an error occurs
     */
    default ListTablesResult listTables(ListTablesRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Renames a table.
     *
     * @param request A {@link RenameTableRequest} for RenameTable operation.
     * @return A {@link RenameTableResult} for RenameTable operation.
     * @throws RuntimeException If an error occurs
     */
    default RenameTableResult renameTable(RenameTableRequest request) {
        return renameTable(request, OperationOptions.defaults());
    }

    /**
     * Renames a table.
     *
     * @param request A {@link RenameTableRequest} for RenameTable operation.
     * @param options The operation options.
     * @return A {@link RenameTableResult} for RenameTable operation.
     * @throws RuntimeException If an error occurs
     */
    default RenameTableResult renameTable(RenameTableRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }
    //-----------------------------------------------------------------------


    // table config api
    //-----------------------------------------------------------------------

    /**
     * Gets the encryption configuration of a table.
     *
     * @param request A {@link GetTableEncryptionRequest} for GetTableEncryption operation.
     * @return A {@link GetTableEncryptionResult} for GetTableEncryption operation.
     * @throws RuntimeException If an error occurs
     */
    default GetTableEncryptionResult getTableEncryption(GetTableEncryptionRequest request) {
        return getTableEncryption(request, OperationOptions.defaults());
    }

    /**
     * Gets the encryption configuration of a table.
     *
     * @param request A {@link GetTableEncryptionRequest} for GetTableEncryption operation.
     * @param options The operation options.
     * @return A {@link GetTableEncryptionResult} for GetTableEncryption operation.
     * @throws RuntimeException If an error occurs
     */
    default GetTableEncryptionResult getTableEncryption(GetTableEncryptionRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Gets the maintenance configuration of a table.
     *
     * @param request A {@link GetTableMaintenanceConfigurationRequest} for GetTableMaintenanceConfiguration operation.
     * @return A {@link GetTableMaintenanceConfigurationResult} for GetTableMaintenanceConfiguration operation.
     * @throws RuntimeException If an error occurs
     */
    default GetTableMaintenanceConfigurationResult getTableMaintenanceConfiguration(GetTableMaintenanceConfigurationRequest request) {
        return getTableMaintenanceConfiguration(request, OperationOptions.defaults());
    }

    /**
     * Gets the maintenance configuration of a table.
     *
     * @param request A {@link GetTableMaintenanceConfigurationRequest} for GetTableMaintenanceConfiguration operation.
     * @param options The operation options.
     * @return A {@link GetTableMaintenanceConfigurationResult} for GetTableMaintenanceConfiguration operation.
     * @throws RuntimeException If an error occurs
     */
    default GetTableMaintenanceConfigurationResult getTableMaintenanceConfiguration(GetTableMaintenanceConfigurationRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Sets the maintenance configuration of a table.
     *
     * @param request A {@link PutTableMaintenanceConfigurationRequest} for PutTableMaintenanceConfiguration operation.
     * @return A {@link PutTableMaintenanceConfigurationResult} for PutTableMaintenanceConfiguration operation.
     * @throws RuntimeException If an error occurs
     */
    default PutTableMaintenanceConfigurationResult putTableMaintenanceConfiguration(PutTableMaintenanceConfigurationRequest request) {
        return putTableMaintenanceConfiguration(request, OperationOptions.defaults());
    }

    /**
     * Sets the maintenance configuration of a table.
     *
     * @param request A {@link PutTableMaintenanceConfigurationRequest} for PutTableMaintenanceConfiguration operation.
     * @param options The operation options.
     * @return A {@link PutTableMaintenanceConfigurationResult} for PutTableMaintenanceConfiguration operation.
     * @throws RuntimeException If an error occurs
     */
    default PutTableMaintenanceConfigurationResult putTableMaintenanceConfiguration(PutTableMaintenanceConfigurationRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Gets the maintenance job status of a table.
     *
     * @param request A {@link GetTableMaintenanceJobStatusRequest} for GetTableMaintenanceJobStatus operation.
     * @return A {@link GetTableMaintenanceJobStatusResult} for GetTableMaintenanceJobStatus operation.
     * @throws RuntimeException If an error occurs
     */
    default GetTableMaintenanceJobStatusResult getTableMaintenanceJobStatus(GetTableMaintenanceJobStatusRequest request) {
        return getTableMaintenanceJobStatus(request, OperationOptions.defaults());
    }

    /**
     * Gets the maintenance job status of a table.
     *
     * @param request A {@link GetTableMaintenanceJobStatusRequest} for GetTableMaintenanceJobStatus operation.
     * @param options The operation options.
     * @return A {@link GetTableMaintenanceJobStatusResult} for GetTableMaintenanceJobStatus operation.
     * @throws RuntimeException If an error occurs
     */
    default GetTableMaintenanceJobStatusResult getTableMaintenanceJobStatus(GetTableMaintenanceJobStatusRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Gets the metadata location of a table.
     *
     * @param request A {@link GetTableMetadataLocationRequest} for GetTableMetadataLocation operation.
     * @return A {@link GetTableMetadataLocationResult} for GetTableMetadataLocation operation.
     * @throws RuntimeException If an error occurs
     */
    default GetTableMetadataLocationResult getTableMetadataLocation(GetTableMetadataLocationRequest request) {
        return getTableMetadataLocation(request, OperationOptions.defaults());
    }

    /**
     * Gets the metadata location of a table.
     *
     * @param request A {@link GetTableMetadataLocationRequest} for GetTableMetadataLocation operation.
     * @param options The operation options.
     * @return A {@link GetTableMetadataLocationResult} for GetTableMetadataLocation operation.
     * @throws RuntimeException If an error occurs
     */
    default GetTableMetadataLocationResult getTableMetadataLocation(GetTableMetadataLocationRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Updates the metadata location of a table.
     *
     * @param request A {@link UpdateTableMetadataLocationRequest} for UpdateTableMetadataLocation operation.
     * @return A {@link UpdateTableMetadataLocationResult} for UpdateTableMetadataLocation operation.
     * @throws RuntimeException If an error occurs
     */
    default UpdateTableMetadataLocationResult updateTableMetadataLocation(UpdateTableMetadataLocationRequest request) {
        return updateTableMetadataLocation(request, OperationOptions.defaults());
    }

    /**
     * Updates the metadata location of a table.
     *
     * @param request A {@link UpdateTableMetadataLocationRequest} for UpdateTableMetadataLocation operation.
     * @param options The operation options.
     * @return A {@link UpdateTableMetadataLocationResult} for UpdateTableMetadataLocation operation.
     * @throws RuntimeException If an error occurs
     */
    default UpdateTableMetadataLocationResult updateTableMetadataLocation(UpdateTableMetadataLocationRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Deletes the policy of a table.
     *
     * @param request A {@link DeleteTablePolicyRequest} for DeleteTablePolicy operation.
     * @return A {@link DeleteTablePolicyResult} for DeleteTablePolicy operation.
     * @throws RuntimeException If an error occurs
     */
    default DeleteTablePolicyResult deleteTablePolicy(DeleteTablePolicyRequest request) {
        return deleteTablePolicy(request, OperationOptions.defaults());
    }

    /**
     * Deletes the policy of a table.
     *
     * @param request A {@link DeleteTablePolicyRequest} for DeleteTablePolicy operation.
     * @param options The operation options.
     * @return A {@link DeleteTablePolicyResult} for DeleteTablePolicy operation.
     * @throws RuntimeException If an error occurs
     */
    default DeleteTablePolicyResult deleteTablePolicy(DeleteTablePolicyRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Gets the policy of a table.
     *
     * @param request A {@link GetTablePolicyRequest} for GetTablePolicy operation.
     * @return A {@link GetTablePolicyResult} for GetTablePolicy operation.
     * @throws RuntimeException If an error occurs
     */
    default GetTablePolicyResult getTablePolicy(GetTablePolicyRequest request) {
        return getTablePolicy(request, OperationOptions.defaults());
    }

    /**
     * Gets the policy of a table.
     *
     * @param request A {@link GetTablePolicyRequest} for GetTablePolicy operation.
     * @param options The operation options.
     * @return A {@link GetTablePolicyResult} for GetTablePolicy operation.
     * @throws RuntimeException If an error occurs
     */
    default GetTablePolicyResult getTablePolicy(GetTablePolicyRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Sets the policy of a table.
     *
     * @param request A {@link PutTablePolicyRequest} for PutTablePolicy operation.
     * @return A {@link PutTablePolicyResult} for PutTablePolicy operation.
     * @throws RuntimeException If an error occurs
     */
    default PutTablePolicyResult putTablePolicy(PutTablePolicyRequest request) {
        return putTablePolicy(request, OperationOptions.defaults());
    }

    /**
     * Sets the policy of a table.
     *
     * @param request A {@link PutTablePolicyRequest} for PutTablePolicy operation.
     * @param options The operation options.
     * @return A {@link PutTablePolicyResult} for PutTablePolicy operation.
     * @throws RuntimeException If an error occurs
     */
    default PutTablePolicyResult putTablePolicy(PutTablePolicyRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

}
