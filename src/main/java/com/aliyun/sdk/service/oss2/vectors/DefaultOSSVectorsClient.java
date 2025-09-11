package com.aliyun.sdk.service.oss2.vectors;

import com.aliyun.sdk.service.oss2.*;
import com.aliyun.sdk.service.oss2.internal.ClientImpl;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.Function;
import com.aliyun.sdk.service.oss2.vectors.models.*;
import com.aliyun.sdk.service.oss2.vectors.operations.VectorBucketBasic;

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
    public void close() throws Exception {
        this.clientImpl.close();
    }
}
