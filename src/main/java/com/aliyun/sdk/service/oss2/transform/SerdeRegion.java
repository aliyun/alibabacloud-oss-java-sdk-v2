package com.aliyun.sdk.service.oss2.transform;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.models.DescribeRegionsRequest;
import com.aliyun.sdk.service.oss2.models.DescribeRegionsResult;
import com.aliyun.sdk.service.oss2.models.RegionInfoList;
import com.aliyun.sdk.service.oss2.utils.MapUtils;

import java.util.Map;

public final class SerdeRegion {


    public static OperationInput fromDescribeRegions(DescribeRegionsRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("DescribeRegions")
                .method("GET");

        // default headers
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");
        builder.headers(headers);


        // parameters
        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("regions", "");
        builder.parameters(parameters);


        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static DescribeRegionsResult toDescribeRegions(OperationOutput output) {
        Object innerBody = null;
        innerBody = SerdeUtils.deserializeXmlBody(output, RegionInfoList.class);

        return DescribeRegionsResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .innerBody(innerBody)
                .build();
    }


}