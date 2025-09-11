package com.aliyun.sdk.service.oss2;

import com.aliyun.sdk.service.oss2.credentials.CredentialsProvider;
import com.aliyun.sdk.service.oss2.retry.Retryer;
import com.aliyun.sdk.service.oss2.signer.Signer;
import com.aliyun.sdk.service.oss2.transport.HttpClient;

import java.time.Duration;
import java.util.List;


/**
 * This includes configuration specific to OSS that is supported by
 * {@link OSSClientBuilder}, {@link OSSAsyncClientBuilder} and {@link OSSDualClientBuilder}
 */
public interface BaseClientBuilder<B extends BaseClientBuilder<B, T>, T> {
    /**
     * An immutable object that is created from the
     * properties that have been set on the builder.
     *
     * @return an instance of T
     */
    T build();

    B region(String value);

    B endpoint(String value);

    B signer(Signer value);

    B retryMaxAttempts(Integer value);

    B retryer(Retryer value);

    B credentialsProvider(CredentialsProvider value);

    B signatureVersion(String value);

    B disableSsl(Boolean value);

    B useDualStackEndpoint(Boolean value);

    B useInternalEndpoint(Boolean value);

    B useAccelerateEndpoint(Boolean value);

    B useCName(Boolean value);

    B usePathStyle(Boolean value);

    B httpClient(HttpClient value);

    B additionalHeaders(List<String> value);

    B userAgent(String value);

    B connectTimeout(Duration value);

    B readWriteTimeout(Duration value);

    B insecureSkipVerify(boolean value);

    B enabledRedirect(boolean value);

    B proxyHost(String value);

    B disableUploadCRC64Check(boolean value);

    B accountId(String value);
}
