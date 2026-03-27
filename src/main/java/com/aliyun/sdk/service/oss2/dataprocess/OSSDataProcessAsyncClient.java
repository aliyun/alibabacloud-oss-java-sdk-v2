package com.aliyun.sdk.service.oss2.dataprocess;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOptions;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.dataprocess.models.*;

import java.util.concurrent.CompletableFuture;

/**
 * A client for accessing OSSDataProcess asynchronously.
 * This can be created using the static {@link #newBuilder()} method.
 */
public interface OSSDataProcessAsyncClient extends AutoCloseable {

    static OSSDataProcessAsyncClientBuilder newBuilder() {
        return new DefaultOSSDataProcessAsyncClientBuilder();
    }

    // common api
    //-----------------------------------------------------------------------
    default CompletableFuture<OperationOutput> invokeOperationAsync(OperationInput input, OperationOptions opts) {
        throw new UnsupportedOperationException();
    }
    //-----------------------------------------------------------------------

    // Dataset api
    //-----------------------------------------------------------------------
    /**
     * Creates a dataset.
     *
     * @param request A {@link CreateDatasetRequest} for CreateDataset operation.
     * @return A Java Future containing the {@link CreateDatasetResult} of the CreateDataset operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<CreateDatasetResult> createDatasetAsync(CreateDatasetRequest request) {
        return createDatasetAsync(request, OperationOptions.defaults());
    }

    default CompletableFuture<CreateDatasetResult> createDatasetAsync(CreateDatasetRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Gets the information of a dataset.
     *
     * @param request A {@link GetDatasetRequest} for GetDataset operation.
     * @return A Java Future containing the {@link GetDatasetResult} of the GetDataset operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<GetDatasetResult> getDatasetAsync(GetDatasetRequest request) {
        return getDatasetAsync(request, OperationOptions.defaults());
    }

    default CompletableFuture<GetDatasetResult> getDatasetAsync(GetDatasetRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Updates a dataset.
     *
     * @param request A {@link UpdateDatasetRequest} for UpdateDataset operation.
     * @return A Java Future containing the {@link UpdateDatasetResult} of the UpdateDataset operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<UpdateDatasetResult> updateDatasetAsync(UpdateDatasetRequest request) {
        return updateDatasetAsync(request, OperationOptions.defaults());
    }

    default CompletableFuture<UpdateDatasetResult> updateDatasetAsync(UpdateDatasetRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Deletes a dataset.
     *
     * @param request A {@link DeleteDatasetRequest} for DeleteDataset operation.
     * @return A Java Future containing the {@link DeleteDatasetResult} of the DeleteDataset operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<DeleteDatasetResult> deleteDatasetAsync(DeleteDatasetRequest request) {
        return deleteDatasetAsync(request, OperationOptions.defaults());
    }

    default CompletableFuture<DeleteDatasetResult> deleteDatasetAsync(DeleteDatasetRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Lists datasets.
     *
     * @param request A {@link ListDatasetsRequest} for ListDatasets operation.
     * @return A Java Future containing the {@link ListDatasetsResult} of the ListDatasets operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<ListDatasetsResult> listDatasetsAsync(ListDatasetsRequest request) {
        return listDatasetsAsync(request, OperationOptions.defaults());
    }

    default CompletableFuture<ListDatasetsResult> listDatasetsAsync(ListDatasetsRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }
    //-----------------------------------------------------------------------

    // Query api
    //-----------------------------------------------------------------------
    /**
     * Queries files in a dataset using structured query language.
     *
     * @param request A {@link SimpleQueryRequest} for SimpleQuery operation.
     * @return A Java Future containing the {@link SimpleQueryResult} of the SimpleQuery operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<SimpleQueryResult> simpleQueryAsync(SimpleQueryRequest request) {
        return simpleQueryAsync(request, OperationOptions.defaults());
    }

    default CompletableFuture<SimpleQueryResult> simpleQueryAsync(SimpleQueryRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Queries files in a dataset using natural language.
     *
     * @param request A {@link SemanticQueryRequest} for SemanticQuery operation.
     * @return A Java Future containing the {@link SemanticQueryResult} of the SemanticQuery operation.
     * @throws RuntimeException If an error occurs
     */
    default CompletableFuture<SemanticQueryResult> semanticQueryAsync(SemanticQueryRequest request) {
        return semanticQueryAsync(request, OperationOptions.defaults());
    }

    default CompletableFuture<SemanticQueryResult> semanticQueryAsync(SemanticQueryRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }
    //-----------------------------------------------------------------------

}
