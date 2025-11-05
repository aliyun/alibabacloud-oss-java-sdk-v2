package com.aliyun.sdk.service.oss2.types;

import com.aliyun.sdk.service.oss2.OperationInput;

public interface EndpointProvider {
    String buildURL(OperationInput input);
}
