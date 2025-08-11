package com.aliyun.sdk.service.oss2;

/**
 * A client for accessing OSS synchronously or asynchronously.
 * This can be created using the static {@link #newBuilder()} method.
 */
public interface OSSDualClient extends OSSClient, OSSAsyncClient {

    static OSSDualClientBuilder newBuilder() {
        return new DefaultOSSDualClientBuilder();
    }

}
