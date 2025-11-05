package com.aliyun.sdk.service.oss2.transform;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.models.AsyncProcessObjectRequest;
import com.aliyun.sdk.service.oss2.models.AsyncProcessObjectResult;
import com.aliyun.sdk.service.oss2.models.internal.AsyncProcessJson;
import com.aliyun.sdk.service.oss2.models.internal.ImageProcessJson;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.transport.StringBinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import com.aliyun.sdk.service.oss2.models.ProcessObjectRequest;
import com.aliyun.sdk.service.oss2.models.ProcessObjectResult;
import java.util.Map;

public class SerdeProcessObject {

    public static OperationInput fromProcessObject(ProcessObjectRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("ProcessObject")
                .method("POST");

        // default headers
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");
        builder.headers(headers);

        // parameters
        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("x-oss-process", "");
        builder.parameters(parameters);

        // body
        String processParam = "x-oss-process=" + request.process();
        BinaryData body = new StringBinaryData(processParam);
        builder.body(body);

        builder.bucket(request.bucket());
        builder.key(request.key());

        OperationInput input = builder.build();

        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static ProcessObjectResult toProcessObject(OperationOutput output) {

        ProcessObjectResult.Builder builder = ProcessObjectResult.newBuilder();
        ImageProcessJson imageProcess = SerdeUtils.fromJsonBody(output, ImageProcessJson.class);

        return builder
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .innerBody(imageProcess)
                .build();
    }

    public static OperationInput fromAsyncProcessObject(AsyncProcessObjectRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("AsyncProcessObject")
                .method("POST");

        // default headers
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");
        builder.headers(headers);

        // parameters
        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("x-oss-async-process", "");
        builder.parameters(parameters);

        // body
        String processParam = "x-oss-async-process=" + request.process();
        BinaryData body = new StringBinaryData(processParam);
        builder.body(body);

        builder.bucket(request.bucket());
        builder.key(request.key());

        OperationInput input = builder.build();

        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static AsyncProcessObjectResult toAsyncProcessObject(OperationOutput output) {

        AsyncProcessObjectResult.Builder builder = AsyncProcessObjectResult.newBuilder();
        AsyncProcessJson asyncProcess = SerdeUtils.fromJsonBody(output, AsyncProcessJson.class);

        return builder
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .innerBody(asyncProcess)
                .build();
    }

}
