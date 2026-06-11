package com.aliyun.sdk.service.oss2.dataprocess;

import com.aliyun.sdk.service.oss2.BaseClientBuilder;

/**
 * Builder for creating {@link OSSDataProcessClient} instances (synchronous data processing API).
 *
 * @see OSSDataProcessClient
 * @see BaseClientBuilder
 */
public interface OSSDataProcessClientBuilder extends BaseClientBuilder<OSSDataProcessClientBuilder, OSSDataProcessClient> {

    /**
     * Sets whether to use Apache HttpClient 4.x instead of the default Apache HttpClient 5.x.
     * <p>
     * Default: {@code false} (uses Apache HttpClient 5.x).
     * <p>
     * Set to {@code true} if your environment requires Apache HttpClient 4.x compatibility.
     * This option is ignored if a custom {@link #httpClient} is provided.
     *
     * @param value {@code true} to use Apache HttpClient 4.x
     * @return this builder for method chaining
     */
    OSSDataProcessClientBuilder useApacheHttpClient4(boolean value);
}
