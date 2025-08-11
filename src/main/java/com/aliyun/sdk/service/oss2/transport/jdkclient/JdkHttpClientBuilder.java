package com.aliyun.sdk.service.oss2.transport.jdkclient;

import com.aliyun.sdk.service.oss2.transport.HttpClient;

import java.time.Duration;
import java.util.concurrent.Executor;


public class JdkHttpClientBuilder {

    private java.net.http.HttpClient.Builder httpClientBuilder;
    private Executor executor;

    private Duration connectionTimeout;
    private Duration writeTimeout;
    private Duration responseTimeout;
    private Duration readTimeout;

    public JdkHttpClientBuilder() {
    }

    public JdkHttpClientBuilder(java.net.http.HttpClient.Builder httpClientBuilder) {
        this.httpClientBuilder = httpClientBuilder;
    }

    public JdkHttpClientBuilder executor(Executor executor) {
        this.executor = executor;
        return this;
    }

    public JdkHttpClientBuilder connectionTimeout(Duration connectionTimeout) {
        // setConnectionTimeout can be null
        this.connectionTimeout = connectionTimeout;
        return this;
    }

    public JdkHttpClientBuilder writeTimeout(Duration writeTimeout) {
        this.writeTimeout = writeTimeout;
        return this;
    }

    public JdkHttpClientBuilder responseTimeout(Duration responseTimeout) {
        this.responseTimeout = responseTimeout;
        return this;
    }

    public JdkHttpClientBuilder readTimeout(Duration readTimeout) {
        this.readTimeout = readTimeout;
        return this;
    }

    public HttpClient build() {
        java.net.http.HttpClient.Builder httpClientBuilder
                = this.httpClientBuilder == null ? java.net.http.HttpClient.newBuilder() : this.httpClientBuilder;

        // Azure JDK http client supports HTTP 1.1 by default.
        httpClientBuilder.version(java.net.http.HttpClient.Version.HTTP_1_1);

        if (executor != null) {
            httpClientBuilder.executor(executor);
        }

        return new JdkHttpClient(httpClientBuilder.build());
    }
}