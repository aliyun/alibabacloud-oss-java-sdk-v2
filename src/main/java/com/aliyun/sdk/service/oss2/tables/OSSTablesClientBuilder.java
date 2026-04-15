package com.aliyun.sdk.service.oss2.tables;

import com.aliyun.sdk.service.oss2.BaseClientBuilder;

public interface OSSTablesClientBuilder extends BaseClientBuilder<OSSTablesClientBuilder, OSSTablesClient> {
    OSSTablesClientBuilder useApacheHttpClient4(boolean value);
}
