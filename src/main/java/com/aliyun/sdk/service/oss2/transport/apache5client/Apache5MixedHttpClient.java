package com.aliyun.sdk.service.oss2.transport.apache5client;

import com.aliyun.sdk.service.oss2.transport.HttpClient;
import com.aliyun.sdk.service.oss2.transport.RequestContext;
import com.aliyun.sdk.service.oss2.transport.RequestMessage;
import com.aliyun.sdk.service.oss2.transport.ResponseMessage;
import org.apache.hc.core5.util.Args;

import java.util.concurrent.CompletableFuture;

public class Apache5MixedHttpClient implements HttpClient, AutoCloseable {

    private final Apache5HttpClient syncClient;
    private final Apache5AsyncHttpClient asyncClient;

    public Apache5MixedHttpClient(Apache5HttpClient syncClient, Apache5AsyncHttpClient asyncClient) {
        this.syncClient = Args.notNull(syncClient, "Apache5HttpClient");
        this.asyncClient = Args.notNull(asyncClient, "Apache5AsyncHttpClient");
    }

    @Override
    public CompletableFuture<ResponseMessage> sendAsync(RequestMessage request, RequestContext context) {
        return this.asyncClient.sendAsync(request, context);
    }

    @Override
    public ResponseMessage send(RequestMessage request, RequestContext context) {
        return this.syncClient.send(request, context);
    }

    @Override
    public String name() {
        return "Apache Mixed HttpClient 5.x";
    }

    @Override
    public void close() throws Exception {
        this.syncClient.close();
        this.asyncClient.close();
    }
}
