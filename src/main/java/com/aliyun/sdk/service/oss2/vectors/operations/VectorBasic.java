package com.aliyun.sdk.service.oss2.vectors.operations;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOptions;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.internal.ClientImpl;
import com.aliyun.sdk.service.oss2.vectors.models.*;
import com.aliyun.sdk.service.oss2.vectors.transform.SerdeVectorBasic;
import java.util.concurrent.CompletableFuture;
import static java.util.Objects.requireNonNull;

public final class VectorBasic {

    public static PutVectorsResult putVectors(ClientImpl impl, PutVectorsRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeVectorBasic.fromPutVectors(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeVectorBasic.toPutVectors(output);
    }

    public static CompletableFuture<PutVectorsResult> putVectorsAsync(ClientImpl impl, PutVectorsRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeVectorBasic.fromPutVectors(request);
        return impl.executeAsync(input, options)
                .thenApply(SerdeVectorBasic::toPutVectors);
    }

    public static GetVectorsResult getVectors(ClientImpl impl, GetVectorsRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeVectorBasic.fromGetVectors(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeVectorBasic.toGetVectors(output);
    }

    public static CompletableFuture<GetVectorsResult> getVectorsAsync(ClientImpl impl, GetVectorsRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeVectorBasic.fromGetVectors(request);
        return impl.executeAsync(input, options)
                .thenApply(SerdeVectorBasic::toGetVectors);
    }

    public static ListVectorsResult listVectors(ClientImpl impl, ListVectorsRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeVectorBasic.fromListVectors(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeVectorBasic.toListVectors(output);
    }

    public static CompletableFuture<ListVectorsResult> listVectorsAsync(ClientImpl impl, ListVectorsRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeVectorBasic.fromListVectors(request);
        return impl.executeAsync(input, options)
                .thenApply(SerdeVectorBasic::toListVectors);
    }

    public static DeleteVectorsResult deleteVectors(ClientImpl impl, DeleteVectorsRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeVectorBasic.fromDeleteVectors(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeVectorBasic.toDeleteVectors(output);
    }

    public static CompletableFuture<DeleteVectorsResult> deleteVectorsAsync(ClientImpl impl, DeleteVectorsRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeVectorBasic.fromDeleteVectors(request);
        return impl.executeAsync(input, options)
                .thenApply(SerdeVectorBasic::toDeleteVectors);
    }

    public static QueryVectorsResult queryVectors(ClientImpl impl, QueryVectorsRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeVectorBasic.fromQueryVectors(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeVectorBasic.toQueryVectors(output);
    }

    public static CompletableFuture<QueryVectorsResult> queryVectorsAsync(ClientImpl impl, QueryVectorsRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeVectorBasic.fromQueryVectors(request);
        return impl.executeAsync(input, options)
                .thenApply(SerdeVectorBasic::toQueryVectors);
    }
}
