package com.aliyun.sdk.service.oss2.imm;

import com.aliyun.sdk.service.oss2.*;
import com.aliyun.sdk.service.oss2.imm.models.*;
import com.aliyun.sdk.service.oss2.imm.operations.DatasetBasic;
import com.aliyun.sdk.service.oss2.internal.ClientImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

/**
 * Internal implementation of {@link IMMAsyncClient}.
 */
public class DefaultIMMAsyncClient implements IMMAsyncClient {

    private final ClientImpl clientImpl;

    public DefaultIMMAsyncClient(ClientConfiguration config) {
        this(config, new ArrayList<>());
    }

    @SafeVarargs
    public DefaultIMMAsyncClient(ClientConfiguration config, Function<ClientOptions, ClientOptions>... optFns) {
        this(config, Arrays.asList(optFns));
    }

    private DefaultIMMAsyncClient(ClientConfiguration config, Collection<Function<ClientOptions, ClientOptions>> optFns) {
        this.clientImpl = new ClientImpl(config, optFns);
    }

    @Override
    public void close() throws Exception {
        this.clientImpl.close();
    }

    @Override
    public CompletableFuture<OperationOutput> invokeOperationAsync(OperationInput input, OperationOptions opts) {
        return this.clientImpl.executeAsync(input, opts);
    }

    @Override
    public CompletableFuture<CreateDatasetResult> createDatasetAsync(CreateDatasetRequest request, OperationOptions options) {
        return DatasetBasic.createDatasetAsync(this.clientImpl, request, options);
    }

    @Override
    public CompletableFuture<GetDatasetResult> getDatasetAsync(GetDatasetRequest request, OperationOptions options) {
        return DatasetBasic.getDatasetAsync(this.clientImpl, request, options);
    }

    @Override
    public CompletableFuture<UpdateDatasetResult> updateDatasetAsync(UpdateDatasetRequest request, OperationOptions options) {
        return DatasetBasic.updateDatasetAsync(this.clientImpl, request, options);
    }

    @Override
    public CompletableFuture<DeleteDatasetResult> deleteDatasetAsync(DeleteDatasetRequest request, OperationOptions options) {
        return DatasetBasic.deleteDatasetAsync(this.clientImpl, request, options);
    }

    @Override
    public CompletableFuture<SimpleQueryResult> simpleQueryAsync(SimpleQueryRequest request, OperationOptions options) {
        return DatasetBasic.simpleQueryAsync(this.clientImpl, request, options);
    }

    @Override
    public CompletableFuture<SemanticQueryResult> semanticQueryAsync(SemanticQueryRequest request, OperationOptions options) {
        return DatasetBasic.semanticQueryAsync(this.clientImpl, request, options);
    }
}
