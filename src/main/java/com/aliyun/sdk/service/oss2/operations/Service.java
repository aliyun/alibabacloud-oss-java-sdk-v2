package com.aliyun.sdk.service.oss2.operations;


import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOptions;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.internal.ClientImpl;
import com.aliyun.sdk.service.oss2.models.ListBucketsRequest;
import com.aliyun.sdk.service.oss2.models.ListBucketsResult;
import com.aliyun.sdk.service.oss2.transform.SerdeService;

import java.util.concurrent.CompletableFuture;

public final class Service {


    public static ListBucketsResult listBuckets(ClientImpl impl, ListBucketsRequest request, OperationOptions options) {


        OperationInput input = SerdeService.fromListBuckets(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeService.toListBuckets(output);
    }

    public static CompletableFuture<ListBucketsResult> listBucketsAsync(ClientImpl impl, ListBucketsRequest request, OperationOptions options) {


        OperationInput input = SerdeService.fromListBuckets(request);
        return impl.executeAsync(input, options).thenApply(SerdeService::toListBuckets);
    }


}