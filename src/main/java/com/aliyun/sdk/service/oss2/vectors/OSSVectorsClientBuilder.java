package com.aliyun.sdk.service.oss2.vectors;

import com.aliyun.sdk.service.oss2.BaseClientBuilder;
import com.aliyun.sdk.service.oss2.OSSClient;

public interface OSSVectorsClientBuilder extends BaseClientBuilder<OSSVectorsClientBuilder, OSSVectorsClient> {
    OSSVectorsClientBuilder useApacheHttpClient4(boolean value);
}
