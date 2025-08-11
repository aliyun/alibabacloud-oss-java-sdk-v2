package com.aliyun.sdk.service.oss2.transform;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.models.GetSymlinkRequest;
import com.aliyun.sdk.service.oss2.models.GetSymlinkResult;
import com.aliyun.sdk.service.oss2.models.PutSymlinkRequest;
import com.aliyun.sdk.service.oss2.models.PutSymlinkResult;
import com.aliyun.sdk.service.oss2.utils.MapUtils;

import java.util.Map;

public final class SerdeObjectSymlink {


    public static OperationInput fromPutSymlink(PutSymlinkRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("PutSymlink")
                .method("PUT");

        // default headers
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");
        builder.headers(headers);


        // parameters
        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("symlink", "");
        builder.parameters(parameters);


        builder.bucket(request.bucket());
        builder.key(request.key());

        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static PutSymlinkResult toPutSymlink(OperationOutput output) {
        Object innerBody = null;
        return PutSymlinkResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .innerBody(innerBody)
                .build();
    }


    public static OperationInput fromGetSymlink(GetSymlinkRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("GetSymlink")
                .method("GET");

        // default headers
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");
        builder.headers(headers);


        // parameters
        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("symlink", "");
        builder.parameters(parameters);

        builder.bucket(request.bucket());
        builder.key(request.key());

        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static GetSymlinkResult toGetSymlink(OperationOutput output) {
        Object innerBody = null;
        return GetSymlinkResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .innerBody(innerBody)
                .build();
    }


}