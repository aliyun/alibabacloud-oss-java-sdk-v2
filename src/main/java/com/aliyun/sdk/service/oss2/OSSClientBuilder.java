package com.aliyun.sdk.service.oss2;

public interface OSSClientBuilder extends BaseClientBuilder<OSSClientBuilder, OSSClient> {
    OSSClientBuilder useApacheHttpClient4(boolean value);
}
