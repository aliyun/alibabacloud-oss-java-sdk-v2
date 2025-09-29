package com.aliyun.sdk.service.oss2.vectors.operations;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOptions;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.internal.ClientImpl;
import com.aliyun.sdk.service.oss2.vectors.models.*;
import com.aliyun.sdk.service.oss2.vectors.transform.SerdeVectorIndexBasic;
import java.util.concurrent.CompletableFuture;
import static java.util.Objects.requireNonNull;

public final class VectorIndexBasic {

    public static PutVectorIndexResult putVectorIndex(ClientImpl impl, PutVectorIndexRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeVectorIndexBasic.fromPutVectorIndex(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeVectorIndexBasic.toPutVectorIndex(output);
    }

    public static CompletableFuture<PutVectorIndexResult> putVectorIndexAsync(ClientImpl impl, PutVectorIndexRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeVectorIndexBasic.fromPutVectorIndex(request);
        return impl.executeAsync(input, options)
                .thenApply(SerdeVectorIndexBasic::toPutVectorIndex);
    }

    public static GetVectorIndexResult getVectorIndex(ClientImpl impl, GetVectorIndexRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeVectorIndexBasic.fromGetVectorIndex(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeVectorIndexBasic.toGetVectorIndex(output);
    }

    public static CompletableFuture<GetVectorIndexResult> getVectorIndexAsync(ClientImpl impl, GetVectorIndexRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeVectorIndexBasic.fromGetVectorIndex(request);
        return impl.executeAsync(input, options)
                .thenApply(SerdeVectorIndexBasic::toGetVectorIndex);
    }

    public static ListVectorIndexesResult listVectorIndexes(ClientImpl impl, ListVectorIndexesRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeVectorIndexBasic.fromListVectorIndexes(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeVectorIndexBasic.toListVectorIndexes(output);
    }

    public static CompletableFuture<ListVectorIndexesResult> listVectorIndexesAsync(ClientImpl impl, ListVectorIndexesRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeVectorIndexBasic.fromListVectorIndexes(request);
        return impl.executeAsync(input, options)
                .thenApply(SerdeVectorIndexBasic::toListVectorIndexes);
    }

    public static DeleteVectorIndexResult deleteVectorIndex(ClientImpl impl, DeleteVectorIndexRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");
        requireNonNull(request.vectorIndexNameInfo().indexName(), "request.indexName is required");

        OperationInput input = SerdeVectorIndexBasic.fromDeleteVectorIndex(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeVectorIndexBasic.toDeleteVectorIndex(output);
    }

    public static CompletableFuture<DeleteVectorIndexResult> deleteVectorIndexAsync(ClientImpl impl, DeleteVectorIndexRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");
        requireNonNull(request.vectorIndexNameInfo().indexName(), "request.indexName is required");

        OperationInput input = SerdeVectorIndexBasic.fromDeleteVectorIndex(request);
        return impl.executeAsync(input, options)
                .thenApply(SerdeVectorIndexBasic::toDeleteVectorIndex);
    }
}

