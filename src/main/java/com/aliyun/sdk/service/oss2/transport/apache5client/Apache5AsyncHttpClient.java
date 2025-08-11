package com.aliyun.sdk.service.oss2.transport.apache5client;

import com.aliyun.sdk.service.oss2.transport.*;
import org.apache.hc.client5.http.async.methods.SimpleHttpRequest;
import org.apache.hc.client5.http.async.methods.SimpleHttpResponse;
import org.apache.hc.client5.http.async.methods.SimpleResponseConsumer;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.async.CloseableHttpAsyncClient;
import org.apache.hc.client5.http.io.HttpClientConnectionManager;
import org.apache.hc.client5.http.nio.AsyncClientConnectionManager;
import org.apache.hc.client5.http.protocol.HttpClientContext;
import org.apache.hc.core5.concurrent.FutureCallback;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.nio.AsyncEntityProducer;
import org.apache.hc.core5.http.nio.AsyncRequestProducer;
import org.apache.hc.core5.http.nio.entity.BasicAsyncEntityProducer;
import org.apache.hc.core5.http.nio.entity.StringAsyncEntityProducer;
import org.apache.hc.core5.http.nio.support.BasicRequestProducer;
import org.apache.hc.core5.util.Timeout;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.CompletableFuture;

public class Apache5AsyncHttpClient implements HttpClient, AutoCloseable {

    private static final List<String> IGNORE_HEADERS = Collections.singletonList("Content-Length");

    private final CloseableHttpAsyncClient httpClient;
    private final AsyncClientConnectionManager connectionManager;
    private final RequestConfig requestConfig;

    Apache5AsyncHttpClient(
            CloseableHttpAsyncClient httpClient,
            AsyncClientConnectionManager connectionManager,
            RequestConfig requestConfig
    ) {
        this.httpClient = httpClient;
        this.connectionManager = connectionManager;
        this.requestConfig = requestConfig;

        // start
        this.httpClient.start();
    }

    /**
     * Creates builder object for construction of custom
     * {@link Apache5AsyncHttpClient} instances.
     */
    public static Apache5AsyncHttpClientBuilder custom() {
        return Apache5AsyncHttpClientBuilder.create();
    }

    /**
     * Creates {@link Apache5AsyncHttpClient} instance with default configuration.
     */
    public static Apache5AsyncHttpClient createDefault() {
        return Apache5AsyncHttpClientBuilder.create().build();
    }

    @Override
    public CompletableFuture<ResponseMessage> sendAsync(RequestMessage request, RequestContext context) {
        CompletableFuture<ResponseMessage> cf = new CompletableFuture<>();

        try {
            // Not support ResponseHeadersRead
            if (RequestContext.HttpCompletionOption.ResponseHeadersRead.equals(
                    context.get(RequestContext.Key.HTTP_COMPLETION_OPTION))) {
                throw new UnsupportedOperationException(this.name() + " does not support ResponseHeadersRead.");
            }

            // request
            AsyncRequestProducer requestProducer = toRequestProducer(request, context);
            HttpClientContext httpContext = toHttpClientContext(context);

            this.httpClient.execute(
                    requestProducer,
                    SimpleResponseConsumer.create(),
                    null,
                    httpContext
                    , new FutureCallback<SimpleHttpResponse>() {
                        @Override
                        public void completed(SimpleHttpResponse response) {
                            // Process response message and convert it into a value object
                            ResponseMessage.Builder respBuilder = ResponseMessage.newBuilder()
                                    .request(request)
                                    .statusCode(response.getCode())
                                    .headers(fromHttpHeaders(response.getHeaders()));

                            byte[] body = response.getBodyBytes();
                            if (body != null) {
                                respBuilder.body(BinaryData.fromBytes(body));
                            }
                            cf.complete(respBuilder.build());
                        }

                        @Override
                        public void failed(final Exception ex) {
                            cf.completeExceptionally(ex);
                        }

                        @Override
                        public void cancelled() {
                            cf.cancel(true);
                        }
                    });
        } catch (Exception e) {
            cf.completeExceptionally(e);
        }
        return cf;
    }

    @Override
    public String name() {
        return "Apache Async HttpClient 5.x";
    }

    @Override
    public void close() throws Exception {
        this.httpClient.close();
    }

    /**
     * Gets the connection manager instance
     *
     * @return The {@link HttpClientConnectionManager} instance.
     */
    public AsyncClientConnectionManager getConnectionManager() {
        return this.connectionManager;
    }

    private AsyncRequestProducer toRequestProducer(RequestMessage request, RequestContext context) {
        SimpleHttpRequest httpRequest = new SimpleHttpRequest(request.method(), request.uri());

        // headers
        request.headers().forEach((name, value) -> {
            if (IGNORE_HEADERS.stream().noneMatch(name::equalsIgnoreCase)) {
                httpRequest.addHeader(name, value);
            }
        });

        return new BasicRequestProducer(httpRequest, toEntityProducer(request.body()));
    }

    private AsyncEntityProducer toEntityProducer(BinaryData body) {
        final AsyncEntityProducer entityProducer;
        if (body != null) {
            if (body instanceof ByteArrayBinaryData) {
                entityProducer = new BasicAsyncEntityProducer(body.toBytes(), null);
            } else if (body instanceof StringBinaryData) {
                entityProducer = new StringAsyncEntityProducer(body.toString(), null);
            } else if (body instanceof InputStreamBinaryData) {
                //entityProducer = null;
                throw new UnsupportedOperationException(this.name() + " does not support InputStreamBinaryData.");
            } else {
                // Default is to ByteBuffer
                entityProducer = new ByteBufferAsyncEntityProducer(body.toByteBuffer(), null);
            }
        } else {
            entityProducer = null;
        }
        return entityProducer;
    }

    private HttpClientContext toHttpClientContext(RequestContext context) {

        RequestConfig config = this.requestConfig;
        Duration rwTimeout = context.get(RequestContext.Key.READWRITE_TIMEOUT);

        if (rwTimeout != null) {
            config = RequestConfig.copy(config).setResponseTimeout(Timeout.ofMilliseconds(rwTimeout.toMillis())).build();
        }

        HttpClientContext clientContext = new HttpClientContext();
        clientContext.setRequestConfig(config);

        return clientContext;
    }

    private Map<String, String> fromHttpHeaders(Header[] headers) {
        Map<String, String> h = new TreeMap<String, String>(String.CASE_INSENSITIVE_ORDER);
        for (Header header : headers) {
            h.put(header.getName(), header.getValue());
        }
        return h;
    }
}
