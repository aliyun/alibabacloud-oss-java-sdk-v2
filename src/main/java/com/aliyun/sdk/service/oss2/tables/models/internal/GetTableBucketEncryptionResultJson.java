package com.aliyun.sdk.service.oss2.tables.models.internal;

import com.aliyun.sdk.service.oss2.tables.models.EncryptionConfiguration;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GetTableBucketEncryptionResultJson {

    @JsonProperty("encryptionConfiguration")
    public EncryptionConfiguration encryptionConfiguration;
}
