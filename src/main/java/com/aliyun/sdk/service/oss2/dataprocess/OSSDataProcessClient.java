package com.aliyun.sdk.service.oss2.dataprocess;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOptions;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.dataprocess.models.*;


/**
 * A client for accessing OSSDataProcess synchronously.
 * This can be created using the static {@link #newBuilder()} method.
 */
public interface OSSDataProcessClient extends AutoCloseable {

    static OSSDataProcessClientBuilder newBuilder() {
        return new DefaultOSSDataProcessClientBuilder();
    }

    // common api
    //-----------------------------------------------------------------------
    default OperationOutput invokeOperation(OperationInput input, OperationOptions opts) {
        throw new UnsupportedOperationException();
    }
    //-----------------------------------------------------------------------

    // Dataset api
    //-----------------------------------------------------------------------
    /**
     * Creates a dataset.
     *
     * @param request A {@link CreateDatasetRequest} for CreateDataset operation.
     * @return A {@link CreateDatasetResult} for CreateDataset operation.
     * @throws RuntimeException If an error occurs
     */
    default CreateDatasetResult createDataset(CreateDatasetRequest request) {
        return createDataset(request, OperationOptions.defaults());
    }

    default CreateDatasetResult createDataset(CreateDatasetRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Gets the information of a dataset.
     *
     * @param request A {@link GetDatasetRequest} for GetDataset operation.
     * @return A {@link GetDatasetResult} for GetDataset operation.
     * @throws RuntimeException If an error occurs
     */
    default GetDatasetResult getDataset(GetDatasetRequest request) {
        return getDataset(request, OperationOptions.defaults());
    }

    default GetDatasetResult getDataset(GetDatasetRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Updates a dataset.
     *
     * @param request A {@link UpdateDatasetRequest} for UpdateDataset operation.
     * @return A {@link UpdateDatasetResult} for UpdateDataset operation.
     * @throws RuntimeException If an error occurs
     */
    default UpdateDatasetResult updateDataset(UpdateDatasetRequest request) {
        return updateDataset(request, OperationOptions.defaults());
    }

    default UpdateDatasetResult updateDataset(UpdateDatasetRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Deletes a dataset.
     *
     * @param request A {@link DeleteDatasetRequest} for DeleteDataset operation.
     * @return A {@link DeleteDatasetResult} for DeleteDataset operation.
     * @throws RuntimeException If an error occurs
     */
    default DeleteDatasetResult deleteDataset(DeleteDatasetRequest request) {
        return deleteDataset(request, OperationOptions.defaults());
    }

    default DeleteDatasetResult deleteDataset(DeleteDatasetRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Lists datasets.
     *
     * @param request A {@link ListDatasetsRequest} for ListDatasets operation.
     * @return A {@link ListDatasetsResult} for ListDatasets operation.
     * @throws RuntimeException If an error occurs
     */
    default ListDatasetsResult listDatasets(ListDatasetsRequest request) {
        return listDatasets(request, OperationOptions.defaults());
    }

    default ListDatasetsResult listDatasets(ListDatasetsRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }
    //-----------------------------------------------------------------------

    // Query api
    //-----------------------------------------------------------------------
    /**
     * Queries files in a dataset using structured query language.
     *
     * @param request A {@link SimpleQueryRequest} for SimpleQuery operation.
     * @return A {@link SimpleQueryResult} for SimpleQuery operation.
     * @throws RuntimeException If an error occurs
     */
    default SimpleQueryResult simpleQuery(SimpleQueryRequest request) {
        return simpleQuery(request, OperationOptions.defaults());
    }

    default SimpleQueryResult simpleQuery(SimpleQueryRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Queries files in a dataset using natural language.
     *
     * @param request A {@link SemanticQueryRequest} for SemanticQuery operation.
     * @return A {@link SemanticQueryResult} for SemanticQuery operation.
     * @throws RuntimeException If an error occurs
     */
    default SemanticQueryResult semanticQuery(SemanticQueryRequest request) {
        return semanticQuery(request, OperationOptions.defaults());
    }

    default SemanticQueryResult semanticQuery(SemanticQueryRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }
    //-----------------------------------------------------------------------

    // DataPipeline api
    //-----------------------------------------------------------------------
    /**
     * Creates or updates a Data Pipeline configuration.
     *
     * @param request A {@link PutDataPipelineConfigurationRequest} for PutDataPipelineConfiguration operation.
     * @return A {@link PutDataPipelineConfigurationResult} for PutDataPipelineConfiguration operation.
     * @throws RuntimeException If an error occurs
     */
    default PutDataPipelineConfigurationResult putDataPipelineConfiguration(PutDataPipelineConfigurationRequest request) {
        return putDataPipelineConfiguration(request, OperationOptions.defaults());
    }

    default PutDataPipelineConfigurationResult putDataPipelineConfiguration(PutDataPipelineConfigurationRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Gets a Data Pipeline configuration.
     *
     * @param request A {@link GetDataPipelineConfigurationRequest} for GetDataPipelineConfiguration operation.
     * @return A {@link GetDataPipelineConfigurationResult} for GetDataPipelineConfiguration operation.
     * @throws RuntimeException If an error occurs
     */
    default GetDataPipelineConfigurationResult getDataPipelineConfiguration(GetDataPipelineConfigurationRequest request) {
        return getDataPipelineConfiguration(request, OperationOptions.defaults());
    }

    default GetDataPipelineConfigurationResult getDataPipelineConfiguration(GetDataPipelineConfigurationRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Deletes a Data Pipeline configuration.
     *
     * @param request A {@link DeleteDataPipelineConfigurationRequest} for DeleteDataPipelineConfiguration operation.
     * @return A {@link DeleteDataPipelineConfigurationResult} for DeleteDataPipelineConfiguration operation.
     * @throws RuntimeException If an error occurs
     */
    default DeleteDataPipelineConfigurationResult deleteDataPipelineConfiguration(DeleteDataPipelineConfigurationRequest request) {
        return deleteDataPipelineConfiguration(request, OperationOptions.defaults());
    }

    default DeleteDataPipelineConfigurationResult deleteDataPipelineConfiguration(DeleteDataPipelineConfigurationRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Lists Data Pipeline configurations.
     *
     * @param request A {@link ListDataPipelineConfigurationsRequest} for ListDataPipelineConfigurations operation.
     * @return A {@link ListDataPipelineConfigurationsResult} for ListDataPipelineConfigurations operation.
     * @throws RuntimeException If an error occurs
     */
    default ListDataPipelineConfigurationsResult listDataPipelineConfigurations(ListDataPipelineConfigurationsRequest request) {
        return listDataPipelineConfigurations(request, OperationOptions.defaults());
    }

    default ListDataPipelineConfigurationsResult listDataPipelineConfigurations(ListDataPipelineConfigurationsRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Pauses a Data Pipeline.
     *
     * @param request A {@link PauseDataPipelineRequest} for PauseDataPipeline operation.
     * @return A {@link PauseDataPipelineResult} for PauseDataPipeline operation.
     * @throws RuntimeException If an error occurs
     */
    default PauseDataPipelineResult pauseDataPipeline(PauseDataPipelineRequest request) {
        return pauseDataPipeline(request, OperationOptions.defaults());
    }

    default PauseDataPipelineResult pauseDataPipeline(PauseDataPipelineRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }

    /**
     * Restarts a Data Pipeline.
     *
     * @param request A {@link RestartDataPipelineRequest} for RestartDataPipeline operation.
     * @return A {@link RestartDataPipelineResult} for RestartDataPipeline operation.
     * @throws RuntimeException If an error occurs
     */
    default RestartDataPipelineResult restartDataPipeline(RestartDataPipelineRequest request) {
        return restartDataPipeline(request, OperationOptions.defaults());
    }

    default RestartDataPipelineResult restartDataPipeline(RestartDataPipelineRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }
    //-----------------------------------------------------------------------
}
