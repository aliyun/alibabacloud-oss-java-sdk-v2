package com.aliyun.sdk.service.oss2.dataprocess;

import com.aliyun.sdk.service.oss2.BaseClientBuilder;

public interface OSSDataProcessClientBuilder extends BaseClientBuilder<OSSDataProcessClientBuilder, OSSDataProcessClient> {
    OSSDataProcessClientBuilder useApacheHttpClient4(boolean value);
}
