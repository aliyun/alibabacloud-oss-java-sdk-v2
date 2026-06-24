package com.aliyun.sdk.service.oss2.dataprocess;

import com.aliyun.sdk.service.oss2.*;
import com.aliyun.sdk.service.oss2.dataprocess.models.*;
import com.aliyun.sdk.service.oss2.dataprocess.operations.DatasetBasic;
import com.aliyun.sdk.service.oss2.dataprocess.operations.DataPipelineBasic;
import com.aliyun.sdk.service.oss2.internal.ClientImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.Function;

/**
 * Internal implementation of {@link OSSDataProcessClient}.
 */
public class DefaultOSSDataProcessClient implements OSSDataProcessClient {
    final ClientImpl clientImpl;

    public DefaultOSSDataProcessClient(ClientConfiguration config) {
        this(config, new ArrayList<>());
    }

    @SafeVarargs
    public DefaultOSSDataProcessClient(ClientConfiguration config, Function<ClientOptions, ClientOptions>... optFns) {
        this(config, Arrays.asList(optFns));
    }

    private DefaultOSSDataProcessClient(ClientConfiguration config, Collection<Function<ClientOptions, ClientOptions>> optFns) {
        this.clientImpl = new ClientImpl(config, optFns);
    }

    @Override
    public OperationOutput invokeOperation(OperationInput input, OperationOptions opts) {
        return this.clientImpl.execute(input, opts);
    }

    @Override
    public CreateDatasetResult createDataset(CreateDatasetRequest request, OperationOptions options) {
        return DatasetBasic.createDataset(this.clientImpl, request, options);
    }

    @Override
    public GetDatasetResult getDataset(GetDatasetRequest request, OperationOptions options) {
        return DatasetBasic.getDataset(this.clientImpl, request, options);
    }

    @Override
    public UpdateDatasetResult updateDataset(UpdateDatasetRequest request, OperationOptions options) {
        return DatasetBasic.updateDataset(this.clientImpl, request, options);
    }

    @Override
    public DeleteDatasetResult deleteDataset(DeleteDatasetRequest request, OperationOptions options) {
        return DatasetBasic.deleteDataset(this.clientImpl, request, options);
    }

    @Override
    public ListDatasetsResult listDatasets(ListDatasetsRequest request, OperationOptions options) {
        return DatasetBasic.listDatasets(this.clientImpl, request, options);
    }

    @Override
    public SimpleQueryResult simpleQuery(SimpleQueryRequest request, OperationOptions options) {
        return DatasetBasic.simpleQuery(this.clientImpl, request, options);
    }

    @Override
    public SemanticQueryResult semanticQuery(SemanticQueryRequest request, OperationOptions options) {
        return DatasetBasic.semanticQuery(this.clientImpl, request, options);
    }

    // DataPipeline API
    @Override
    public PutDataPipelineConfigurationResult putDataPipelineConfiguration(PutDataPipelineConfigurationRequest request, OperationOptions options) {
        return DataPipelineBasic.putDataPipelineConfiguration(this.clientImpl, request, options);
    }

    @Override
    public GetDataPipelineConfigurationResult getDataPipelineConfiguration(GetDataPipelineConfigurationRequest request, OperationOptions options) {
        return DataPipelineBasic.getDataPipelineConfiguration(this.clientImpl, request, options);
    }

    @Override
    public DeleteDataPipelineConfigurationResult deleteDataPipelineConfiguration(DeleteDataPipelineConfigurationRequest request, OperationOptions options) {
        return DataPipelineBasic.deleteDataPipelineConfiguration(this.clientImpl, request, options);
    }

    @Override
    public ListDataPipelineConfigurationsResult listDataPipelineConfigurations(ListDataPipelineConfigurationsRequest request, OperationOptions options) {
        return DataPipelineBasic.listDataPipelineConfigurations(this.clientImpl, request, options);
    }

    @Override
    public PauseDataPipelineResult pauseDataPipeline(PauseDataPipelineRequest request, OperationOptions options) {
        return DataPipelineBasic.pauseDataPipeline(this.clientImpl, request, options);
    }

    @Override
    public RestartDataPipelineResult restartDataPipeline(RestartDataPipelineRequest request, OperationOptions options) {
        return DataPipelineBasic.restartDataPipeline(this.clientImpl, request, options);
    }

    // MetaQuery API
    @Override
    public OpenMetaQueryResult openMetaQuery(OpenMetaQueryRequest request, OperationOptions options) {
        return DatasetBasic.openMetaQuery(this.clientImpl, request, options);
    }

    @Override
    public GetMetaQueryStatusResult getMetaQueryStatus(GetMetaQueryStatusRequest request, OperationOptions options) {
        return DatasetBasic.getMetaQueryStatus(this.clientImpl, request, options);
    }

    @Override
    public CloseMetaQueryResult closeMetaQuery(CloseMetaQueryRequest request, OperationOptions options) {
        return DatasetBasic.closeMetaQuery(this.clientImpl, request, options);
    }

    @Override
    public DoMetaQueryResult doMetaQuery(DoMetaQueryRequest request, OperationOptions options) {
        return DatasetBasic.doMetaQuery(this.clientImpl, request, options);
    }

    // FileMeta API
    @Override
    public DeleteFileMetaResult deleteFileMeta(DeleteFileMetaRequest request, OperationOptions options) {
        return DatasetBasic.deleteFileMeta(this.clientImpl, request, options);
    }

    // SmartCluster API
    @Override
    public CreateSmartClusterResult createSmartCluster(CreateSmartClusterRequest request, OperationOptions options) {
        return DatasetBasic.createSmartCluster(this.clientImpl, request, options);
    }

    @Override
    public GetSmartClusterResult getSmartCluster(GetSmartClusterRequest request, OperationOptions options) {
        return DatasetBasic.getSmartCluster(this.clientImpl, request, options);
    }

    @Override
    public UpdateSmartClusterResult updateSmartCluster(UpdateSmartClusterRequest request, OperationOptions options) {
        return DatasetBasic.updateSmartCluster(this.clientImpl, request, options);
    }

    @Override
    public DeleteSmartClusterResult deleteSmartCluster(DeleteSmartClusterRequest request, OperationOptions options) {
        return DatasetBasic.deleteSmartCluster(this.clientImpl, request, options);
    }

    @Override
    public ListSmartClustersResult listSmartClusters(ListSmartClustersRequest request, OperationOptions options) {
        return DatasetBasic.listSmartClusters(this.clientImpl, request, options);
    }

    @Override
    public void close() throws Exception {
        this.clientImpl.close();
    }
}
