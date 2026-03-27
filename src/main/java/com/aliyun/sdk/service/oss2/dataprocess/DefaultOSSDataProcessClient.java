package com.aliyun.sdk.service.oss2.dataprocess;

import com.aliyun.sdk.service.oss2.*;
import com.aliyun.sdk.service.oss2.dataprocess.models.*;
import com.aliyun.sdk.service.oss2.dataprocess.operations.DatasetBasic;
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

    @Override
    public void close() throws Exception {
        this.clientImpl.close();
    }
}
