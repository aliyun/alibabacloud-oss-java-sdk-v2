package com.aliyun.sdk.service.oss2.operations;


import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOptions;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.internal.ClientImpl;
import com.aliyun.sdk.service.oss2.models.DescribeRegionsRequest;
import com.aliyun.sdk.service.oss2.models.DescribeRegionsResult;
import com.aliyun.sdk.service.oss2.transform.SerdeRegion;

import java.util.concurrent.CompletableFuture;

public final class Region {


    public static DescribeRegionsResult describeRegions(ClientImpl impl, DescribeRegionsRequest request, OperationOptions options) {


        OperationInput input = SerdeRegion.fromDescribeRegions(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeRegion.toDescribeRegions(output);
    }

    public static CompletableFuture<DescribeRegionsResult> describeRegionsAsync(ClientImpl impl, DescribeRegionsRequest request, OperationOptions options) {


        OperationInput input = SerdeRegion.fromDescribeRegions(request);
        return impl.executeAsync(input, options).thenApply(SerdeRegion::toDescribeRegions);
    }


}