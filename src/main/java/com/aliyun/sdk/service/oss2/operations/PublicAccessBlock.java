package com.aliyun.sdk.service.oss2.operations;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOptions;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.internal.ClientImpl;
import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.transform.SerdePublicAccessBlock;
import java.util.concurrent.CompletableFuture;

public final class PublicAccessBlock {


    public static GetPublicAccessBlockResult getPublicAccessBlock(ClientImpl impl, GetPublicAccessBlockRequest request, OperationOptions options) {
        OperationInput input = SerdePublicAccessBlock.fromGetPublicAccessBlock(request);
        OperationOutput output = impl.execute(input, options);
        return SerdePublicAccessBlock.toGetPublicAccessBlock(output);
    }

    public static CompletableFuture<GetPublicAccessBlockResult> getPublicAccessBlockAsync(ClientImpl impl, GetPublicAccessBlockRequest request, OperationOptions options) {
        OperationInput input = SerdePublicAccessBlock.fromGetPublicAccessBlock(request);
        return impl.executeAsync(input, options).thenApply(SerdePublicAccessBlock::toGetPublicAccessBlock);
    }


    public static PutPublicAccessBlockResult putPublicAccessBlock(ClientImpl impl, PutPublicAccessBlockRequest request, OperationOptions options) {
        OperationInput input = SerdePublicAccessBlock.fromPutPublicAccessBlock(request);
        OperationOutput output = impl.execute(input, options);
        return SerdePublicAccessBlock.toPutPublicAccessBlock(output);
    }

    public static CompletableFuture<PutPublicAccessBlockResult> putPublicAccessBlockAsync(ClientImpl impl, PutPublicAccessBlockRequest request, OperationOptions options) {
        OperationInput input = SerdePublicAccessBlock.fromPutPublicAccessBlock(request);
        return impl.executeAsync(input, options).thenApply(SerdePublicAccessBlock::toPutPublicAccessBlock);
    }


    public static DeletePublicAccessBlockResult deletePublicAccessBlock(ClientImpl impl, DeletePublicAccessBlockRequest request, OperationOptions options) {
        OperationInput input = SerdePublicAccessBlock.fromDeletePublicAccessBlock(request);
        OperationOutput output = impl.execute(input, options);
        return SerdePublicAccessBlock.toDeletePublicAccessBlock(output);
    }

    public static CompletableFuture<DeletePublicAccessBlockResult> deletePublicAccessBlockAsync(ClientImpl impl, DeletePublicAccessBlockRequest request, OperationOptions options) {
        OperationInput input = SerdePublicAccessBlock.fromDeletePublicAccessBlock(request);
        return impl.executeAsync(input, options).thenApply(SerdePublicAccessBlock::toDeletePublicAccessBlock);
    }


}