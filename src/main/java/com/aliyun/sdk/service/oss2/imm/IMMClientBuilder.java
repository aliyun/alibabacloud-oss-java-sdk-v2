package com.aliyun.sdk.service.oss2.imm;

import com.aliyun.sdk.service.oss2.BaseClientBuilder;

public interface IMMClientBuilder extends BaseClientBuilder<IMMClientBuilder, IMMClient> {
    IMMClientBuilder useApacheHttpClient4(boolean value);
}
