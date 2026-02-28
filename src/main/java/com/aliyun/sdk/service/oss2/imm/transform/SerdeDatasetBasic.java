package com.aliyun.sdk.service.oss2.imm.transform;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.transform.SerdeUtils;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import com.aliyun.sdk.service.oss2.imm.models.*;

import java.util.Map;


public final class SerdeDatasetBasic {

    public static OperationInput fromCreateDataset(CreateDatasetRequest request) {
        // headers
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");

        // TODO parameters
        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        //parameters.put("cors", "");

        // TODO body
        BinaryData body = null;
        //body = SerdeUtils.serializeXmlBody(request.corsConfiguration());

        // opMetadata

        OperationInput input = OperationInput.newBuilder()
                .opName("CreateDataset")
                .bucket(request.bucket())
                .method("PUT")
                .headers(headers)
                .parameters(parameters)
                .body(body)
                .build();

        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);

        return input;
    }

    public static CreateDatasetResult toCreateDataset(OperationOutput output) {
        Object innerBody = null;
        return CreateDatasetResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .innerBody(innerBody)
                .build();
    }
}
