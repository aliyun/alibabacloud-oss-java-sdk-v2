package com.aliyun.sdk.service.oss2.transform;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import java.util.Map;

public final class SerdeBucketRequestPayment {


    public static OperationInput fromPutBucketRequestPayment(PutBucketRequestPaymentRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
            .opName("PutBucketRequestPayment")
            .method("PUT");

        // default headers
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");
        builder.headers(headers);
         
        // parameters
        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("requestPayment", "");
        builder.parameters(parameters);

        // body
        BinaryData body = SerdeUtils.serializeXmlBody(request.requestPaymentConfiguration());
        builder.body(body);

        builder.bucket(request.bucket());

        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static PutBucketRequestPaymentResult toPutBucketRequestPayment(OperationOutput output) {
        Object innerBody = null;
        return PutBucketRequestPaymentResult.newBuilder()
            .headers(output.headers)
            .status(output.status)
            .statusCode(output.statusCode)
            .innerBody(innerBody)
            .build();
    }


    public static OperationInput fromGetBucketRequestPayment(GetBucketRequestPaymentRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
            .opName("GetBucketRequestPayment")
            .method("GET");

        // default headers
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");
        builder.headers(headers);
         
        // parameters
        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("requestPayment", "");
        builder.parameters(parameters);

        builder.bucket(request.bucket());

        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static GetBucketRequestPaymentResult toGetBucketRequestPayment(OperationOutput output) {
        Object innerBody = null;
        innerBody = SerdeUtils.deserializeXmlBody(output, RequestPaymentConfiguration.class);
         
        return GetBucketRequestPaymentResult.newBuilder()
            .headers(output.headers)
            .status(output.status)
            .statusCode(output.statusCode)
            .innerBody(innerBody)
            .build();
    }
}