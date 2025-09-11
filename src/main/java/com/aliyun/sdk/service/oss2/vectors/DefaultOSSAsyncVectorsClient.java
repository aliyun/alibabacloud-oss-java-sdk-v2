package com.aliyun.sdk.service.oss2.vectors;

import com.aliyun.sdk.service.oss2.*;
import com.aliyun.sdk.service.oss2.internal.ClientImpl;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import com.aliyun.sdk.service.oss2.vectors.models.*;
import com.aliyun.sdk.service.oss2.vectors.operations.VectorBucketBasic;

/**
 * Internal implementation of {@link OSSAsyncVectorsClient}.
 */
public class DefaultOSSAsyncVectorsClient implements OSSAsyncVectorsClient {

    private final ClientImpl clientImpl;

    public DefaultOSSAsyncVectorsClient(ClientConfiguration config) {
        this(config, new ArrayList<>());
    }

    @SafeVarargs
    public DefaultOSSAsyncVectorsClient(ClientConfiguration config, Function<ClientOptions, ClientOptions>... optFns) {
        this(config, Arrays.asList(optFns));
    }

    private DefaultOSSAsyncVectorsClient(ClientConfiguration config, Collection<Function<ClientOptions, ClientOptions>> optFns) {
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
    public CompletableFuture<PutVectorBucketResult> putVectorBucketAsync(PutVectorBucketRequest request, OperationOptions options) {
        return VectorBucketBasic.putVectorBucketAsync(this.clientImpl, request, options);
    }

    @Override
    public CompletableFuture<GetVectorBucketResult> getVectorBucketAsync(GetVectorBucketRequest request, OperationOptions options) {
        return VectorBucketBasic.getVectorBucketAsync(this.clientImpl, request, options);
    }

    @Override
    public CompletableFuture<DeleteVectorBucketResult> deleteVectorBucketAsync(DeleteVectorBucketRequest request, OperationOptions options) {
        return VectorBucketBasic.deleteVectorBucketAsync(this.clientImpl, request, options);
    }

    @Override
    public CompletableFuture<ListVectorBucketsResult> listVectorBucketsAsync(ListVectorBucketsRequest request, OperationOptions options) {
        return VectorBucketBasic.listVectorBucketsAsync(this.clientImpl, request, options);
    }
}
