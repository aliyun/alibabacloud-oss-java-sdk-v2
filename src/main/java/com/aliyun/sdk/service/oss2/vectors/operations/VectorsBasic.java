package com.aliyun.sdk.service.oss2.vectors.operations;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOptions;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.internal.ClientImpl;
import com.aliyun.sdk.service.oss2.vectors.models.*;
import com.aliyun.sdk.service.oss2.vectors.transform.SerdeVectorsBasic;
import java.util.concurrent.CompletableFuture;
import static java.util.Objects.requireNonNull;

public final class VectorsBasic {

    public static PutVectorsResult putVectors(ClientImpl impl, PutVectorsRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeVectorsBasic.fromPutVectors(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeVectorsBasic.toPutVectors(output);
    }

    public static CompletableFuture<PutVectorsResult> putVectorsAsync(ClientImpl impl, PutVectorsRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeVectorsBasic.fromPutVectors(request);
        return impl.executeAsync(input, options)
                .thenApply(SerdeVectorsBasic::toPutVectors);
    }

    public static GetVectorsResult getVectors(ClientImpl impl, GetVectorsRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeVectorsBasic.fromGetVectors(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeVectorsBasic.toGetVectors(output);
    }

    public static CompletableFuture<GetVectorsResult> getVectorsAsync(ClientImpl impl, GetVectorsRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeVectorsBasic.fromGetVectors(request);
        return impl.executeAsync(input, options)
                .thenApply(SerdeVectorsBasic::toGetVectors);
    }

    public static ListVectorsResult listVectors(ClientImpl impl, ListVectorsRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeVectorsBasic.fromListVectors(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeVectorsBasic.toListVectors(output);
    }

    public static CompletableFuture<ListVectorsResult> listVectorsAsync(ClientImpl impl, ListVectorsRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeVectorsBasic.fromListVectors(request);
        return impl.executeAsync(input, options)
                .thenApply(SerdeVectorsBasic::toListVectors);
    }

    public static DeleteVectorsResult deleteVectors(ClientImpl impl, DeleteVectorsRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeVectorsBasic.fromDeleteVectors(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeVectorsBasic.toDeleteVectors(output);
    }

    public static CompletableFuture<DeleteVectorsResult> deleteVectorsAsync(ClientImpl impl, DeleteVectorsRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeVectorsBasic.fromDeleteVectors(request);
        return impl.executeAsync(input, options)
                .thenApply(SerdeVectorsBasic::toDeleteVectors);
    }

    public static QueryVectorsResult queryVectors(ClientImpl impl, QueryVectorsRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeVectorsBasic.fromQueryVectors(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeVectorsBasic.toQueryVectors(output);
    }

    public static CompletableFuture<QueryVectorsResult> queryVectorsAsync(ClientImpl impl, QueryVectorsRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeVectorsBasic.fromQueryVectors(request);
        return impl.executeAsync(input, options)
                .thenApply(SerdeVectorsBasic::toQueryVectors);
    }
}
