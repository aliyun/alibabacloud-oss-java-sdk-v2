package com.aliyun.sdk.service.oss2.transform;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import java.util.Map;

public final class SerdeBucketTransferAcceleration {

    public static OperationInput fromPutBucketTransferAcceleration(PutBucketTransferAccelerationRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
            .opName("PutBucketTransferAcceleration")
            .method("PUT");

        // default headers
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");
        builder.headers(headers);
         
        // parameters
        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("transferAcceleration", "");
        builder.parameters(parameters);
        
        // body
        BinaryData body = SerdeUtils.serializeXmlBody(request.transferAccelerationConfiguration());
        builder.body(body);

        builder.bucket(request.bucket());
        

        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static PutBucketTransferAccelerationResult toPutBucketTransferAcceleration(OperationOutput output) {
        Object innerBody = null;
        return PutBucketTransferAccelerationResult.newBuilder()
            .headers(output.headers)
            .status(output.status)
            .statusCode(output.statusCode)
            .innerBody(innerBody)
            .build();
    }


    public static OperationInput fromGetBucketTransferAcceleration(GetBucketTransferAccelerationRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
            .opName("GetBucketTransferAcceleration")
            .method("GET");

        // default headers
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");
        builder.headers(headers);
         
        // parameters
        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("transferAcceleration", "");
        builder.parameters(parameters);

        builder.bucket(request.bucket());

        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static GetBucketTransferAccelerationResult toGetBucketTransferAcceleration(OperationOutput output) {
        Object innerBody = null;
        innerBody = SerdeUtils.deserializeXmlBody(output, TransferAccelerationConfiguration.class);
         
        return GetBucketTransferAccelerationResult.newBuilder()
            .headers(output.headers)
            .status(output.status)
            .statusCode(output.statusCode)
            .innerBody(innerBody)
            .build();
    }
}