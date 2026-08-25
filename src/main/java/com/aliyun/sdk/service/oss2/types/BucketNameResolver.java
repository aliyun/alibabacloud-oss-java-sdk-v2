package com.aliyun.sdk.service.oss2.types;

import com.aliyun.sdk.service.oss2.OperationInput;

public interface BucketNameResolver {
    String buildBucketName(OperationInput input);
}
