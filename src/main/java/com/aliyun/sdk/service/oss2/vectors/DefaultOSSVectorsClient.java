package com.aliyun.sdk.service.oss2.vectors;

import com.aliyun.sdk.service.oss2.*;
import com.aliyun.sdk.service.oss2.internal.ClientImpl;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.Function;
import com.aliyun.sdk.service.oss2.vectors.models.*;
import com.aliyun.sdk.service.oss2.vectors.operations.VectorBucketBasic;
import com.aliyun.sdk.service.oss2.vectors.operations.VectorIndexBasic;
import com.aliyun.sdk.service.oss2.vectors.operations.VectorsBasic;

/**
 * Internal implementation of {@link OSSClient}.
 */
public class DefaultOSSVectorsClient implements OSSVectorsClient {
    final ClientImpl clientImpl;

    public DefaultOSSVectorsClient(ClientConfiguration config) {
        this(config, new ArrayList<>());
    }

    @SafeVarargs
    public DefaultOSSVectorsClient(ClientConfiguration config, Function<ClientOptions, ClientOptions>... optFns) {
        this(config, Arrays.asList(optFns));
    }

    private DefaultOSSVectorsClient(ClientConfiguration config, Collection<Function<ClientOptions, ClientOptions>> optFns) {
        this.clientImpl = new ClientImpl(config, optFns);
    }

    @Override
    public OperationOutput invokeOperation(OperationInput input, OperationOptions opts) {
        return this.clientImpl.execute(input, opts);
    }

    @Override
    public PutVectorBucketResult putVectorBucket(PutVectorBucketRequest request, OperationOptions options) {
        return VectorBucketBasic.putVectorBucket(this.clientImpl, request, options);
    }

    @Override
    public GetVectorBucketResult getVectorBucket(GetVectorBucketRequest request, OperationOptions options) {
        return VectorBucketBasic.getVectorBucket(this.clientImpl, request, options);
    }

    @Override
    public DeleteVectorBucketResult deleteVectorBucket(DeleteVectorBucketRequest request, OperationOptions options) {
        return VectorBucketBasic.deleteVectorBucket(this.clientImpl, request, options);
    }

    @Override
    public ListVectorBucketsResult listVectorBuckets(ListVectorBucketsRequest request, OperationOptions options) {
        return VectorBucketBasic.listVectorBuckets(this.clientImpl, request, options);
    }

    @Override
    public PutVectorIndexResult putVectorIndex(PutVectorIndexRequest request, OperationOptions options) {
        return VectorIndexBasic.putVectorIndex(this.clientImpl, request, options);
    }

    @Override
    public GetVectorIndexResult getVectorIndex(GetVectorIndexRequest request, OperationOptions options) {
        return VectorIndexBasic.getVectorIndex(this.clientImpl, request, options);
    }

    @Override
    public ListVectorIndexesResult listVectorIndexes(ListVectorIndexesRequest request, OperationOptions options) {
        return VectorIndexBasic.listVectorIndexes(this.clientImpl, request, options);
    }

    @Override
    public DeleteVectorIndexResult deleteVectorIndex(DeleteVectorIndexRequest request, OperationOptions options) {
        return VectorIndexBasic.deleteVectorIndex(this.clientImpl, request, options);
    }

    @Override
    public PutVectorsResult putVectors(PutVectorsRequest request, OperationOptions options) {
        return VectorsBasic.putVectors(this.clientImpl, request, options);
    }

    @Override
    public GetVectorsResult getVectors(GetVectorsRequest request, OperationOptions options) {
        return VectorsBasic.getVectors(this.clientImpl, request, options);
    }

    @Override
    public ListVectorsResult listVectors(ListVectorsRequest request, OperationOptions options) {
        return VectorsBasic.listVectors(this.clientImpl, request, options);
    }

    @Override
    public DeleteVectorsResult deleteVectors(DeleteVectorsRequest request, OperationOptions options) {
        return VectorsBasic.deleteVectors(this.clientImpl, request, options);
    }

    @Override
    public QueryVectorsResult queryVectors(QueryVectorsRequest request, OperationOptions options) {
        return VectorsBasic.queryVectors(this.clientImpl, request, options);
    }


    @Override
    public void close() throws Exception {
        this.clientImpl.close();
    }
}
