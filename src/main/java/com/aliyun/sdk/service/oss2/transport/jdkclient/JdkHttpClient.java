package com.aliyun.sdk.service.oss2.transport.jdkclient;

import com.aliyun.sdk.service.oss2.transport.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.concurrent.CompletableFuture;

public final class JdkHttpClient implements HttpClient, AutoCloseable {

    final java.net.http.HttpClient jdkHttpClient;

    public JdkHttpClient(java.net.http.HttpClient jdkHttpClient) {
        this.jdkHttpClient = jdkHttpClient;
    }

    static java.net.http.HttpRequest toJdkHttpRequest(RequestMessage request) {
        String method = request.method().toUpperCase();
        java.net.http.HttpRequest.BodyPublisher bodyPublisher = (method.equals("GET") || method.equals("HEAD"))
                ? java.net.http.HttpRequest.BodyPublishers.noBody()
                : toBodyPublisher(request);

        Map<String, String> headers = request.headers();

        java.net.http.HttpRequest.Builder builder = java.net.http.HttpRequest.newBuilder()
                .uri(request.uri())
                .method(method, bodyPublisher);

        for (Map.Entry<String, String> entry : headers.entrySet()) {
            String name = entry.getKey();
            String value = entry.getValue();
            if (name != null && value != null) {
                builder.header(name, value);
            }
        }

        return builder.build();
    }

    static java.net.http.HttpRequest.BodyPublisher toBodyPublisher(RequestMessage request) {
        BinaryData body = request.body();

        if (body == null) {
            return java.net.http.HttpRequest.BodyPublishers.noBody();
        }

        java.net.http.HttpRequest.BodyPublisher publisher;
        byte[] bytes = body.toBytes();
        publisher = java.net.http.HttpRequest.BodyPublishers.ofByteArray(bytes);
        Long contentLength = body.getLength();
        if (contentLength == null || contentLength <= 0) {
            //
        } else {
            publisher = java.net.http.HttpRequest.BodyPublishers.fromPublisher(publisher, contentLength);
        }

        return publisher;
    }

    static ResponseMessage toMessageResponse(RequestMessage request, java.net.http.HttpResponse<InputStream> response) {
        return ResponseMessage.newBuilder()
                .request(request)
                .statusCode(response.statusCode())
                .headers(fromJdkHttpHeaders(response.headers()))
                .build();
    }

    static ResponseMessage toMessageResponseBytes(RequestMessage request, java.net.http.HttpResponse<byte[]> response) {
        return ResponseMessage.newBuilder()
                .request(request)
                .statusCode(response.statusCode())
                .headers(fromJdkHttpHeaders(response.headers()))
                .body(fromJdkHttpResponse(response))
                .build();
    }

    static Map<String, String> fromJdkHttpHeaders(java.net.http.HttpHeaders headers) {
        Map<String, String> h = new TreeMap<String, String>(String.CASE_INSENSITIVE_ORDER);
        for (Map.Entry<String, List<String>> kvp : headers.map().entrySet()) {
            List<String> value = kvp.getValue();
            if (value != null && !value.isEmpty()) {
                h.put(kvp.getKey(), value.get(0));
            }
        }
        return h;
    }

    static BinaryData fromJdkHttpResponse(java.net.http.HttpResponse<byte[]> response) {
        byte[] val = response.body();
        return new ByteArrayBinaryData(val);
    }

    @Override
    public CompletableFuture<ResponseMessage> sendAsync(RequestMessage request, RequestContext context) {
        RequestContext.HttpCompletionOption completion = Optional
                .ofNullable(context.get(RequestContext.Key.HTTP_COMPLETION_OPTION))
                .orElse(RequestContext.HttpCompletionOption.ResponseContentRead);

        java.net.http.HttpRequest req = toJdkHttpRequest(request);

        // non-stream
        if (completion.equals(RequestContext.HttpCompletionOption.ResponseContentRead)) {
            java.net.http.HttpResponse.BodyHandler<byte[]> bodyHandler = java.net.http.HttpResponse.BodyHandlers.ofByteArray();
            return jdkHttpClient.sendAsync(req, bodyHandler).thenApplyAsync((res) -> toMessageResponseBytes(request, res));
        } else {
            java.net.http.HttpResponse.BodyHandler<InputStream> bodyHandler = java.net.http.HttpResponse.BodyHandlers.ofInputStream();
            return jdkHttpClient.sendAsync(req, bodyHandler).thenApplyAsync((res) -> toMessageResponse(request, res));
        }
    }

    @Override
    public ResponseMessage send(RequestMessage request, RequestContext context) {
        RequestContext.HttpCompletionOption completion = Optional
                .ofNullable(context.get(RequestContext.Key.HTTP_COMPLETION_OPTION))
                .orElse(RequestContext.HttpCompletionOption.ResponseContentRead);

        java.net.http.HttpRequest req = toJdkHttpRequest(request);
        try {
            if (completion.equals(RequestContext.HttpCompletionOption.ResponseContentRead)) {
                java.net.http.HttpResponse.BodyHandler<byte[]> bodyHandler = java.net.http.HttpResponse.BodyHandlers.ofByteArray();
                return toMessageResponseBytes(request, jdkHttpClient.send(req, bodyHandler));
            } else {
                java.net.http.HttpResponse.BodyHandler<InputStream> bodyHandler = java.net.http.HttpResponse.BodyHandlers.ofInputStream();
                return toMessageResponse(request, jdkHttpClient.send(req, bodyHandler));
            }
        } catch (IOException | InterruptedException e) {
            // TODO
            throw new RuntimeException();
        }
    }

    @Override
    public void close() throws Exception {
    }
}
