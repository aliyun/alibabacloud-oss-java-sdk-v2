package com.aliyun.sdk.service.oss2.transport.apache5client;

import com.aliyun.sdk.service.oss2.transport.*;
import org.apache.hc.client5.http.classic.methods.HttpUriRequestBase;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.io.HttpClientConnectionManager;
import org.apache.hc.client5.http.protocol.HttpClientContext;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.ByteArrayEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.InputStreamEntity;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.io.CloseMode;
import org.apache.hc.core5.util.Timeout;
import org.apache.http.HttpResponse;

import java.io.IOException;
import java.time.Duration;
import java.util.*;

public class Apache5HttpClient implements HttpClient, AutoCloseable {

    private static final List<String> IGNORE_HEADERS = Collections.singletonList("Content-Length");

    private final CloseableHttpClient httpClient;
    private final HttpClientConnectionManager connectionManager;
    private final RequestConfig requestConfig;

    Apache5HttpClient(
            CloseableHttpClient httpClient,
            HttpClientConnectionManager connectionManager,
            RequestConfig requestConfig
    ) {
        this.httpClient = httpClient;
        this.connectionManager = connectionManager;
        this.requestConfig = requestConfig;
    }

    /**
     * Creates builder object for construction of custom
     * {@link Apache5HttpClient} instances.
     */
    public static Apache5HttpClientBuilder custom() {
        return Apache5HttpClientBuilder.create();
    }

    /**
     * Creates {@link Apache5HttpClient} instance with default configuration.
     */
    public static Apache5HttpClient createDefault() {
        return Apache5HttpClientBuilder.create().build();
    }

    @Override
    public ResponseMessage send(RequestMessage request, RequestContext context) {
        if (RequestContext.HttpCompletionOption.ResponseHeadersRead.equals(
                context.get(RequestContext.Key.HTTP_COMPLETION_OPTION))) {
            return sendReturnStream(request, context);
        }
        return sendReturnBytes(request, context);
    }

    private ResponseMessage sendReturnStream(RequestMessage request, RequestContext context) {
        CloseableHttpResponse httpResponse = null;
        try {
            // request
            HttpUriRequestBase httpRequest = toHttpRequest(request, context);
            HttpClientContext httpContext = toHttpClientContext(context);

            // do
            httpResponse = this.httpClient.execute(httpRequest, httpContext);

            // response
            ResponseMessage.Builder respBuilder = ResponseMessage.newBuilder()
                    .request(request)
                    .statusCode(httpResponse.getCode())
                    .headers(fromHttpHeaders(httpResponse.getHeaders()));

            if (httpResponse.getEntity() != null) {
                if (determineReturnBytes(httpResponse)) {
                    respBuilder.body(BinaryData.fromBytes(EntityUtils.toByteArray(httpResponse.getEntity())));
                } else {
                    // cast to Abortable stream
                    final CloseableHttpResponse closeableResponse = httpResponse;
                    respBuilder.body(BinaryData.fromStream(
                            AbortableInputStream.create(
                                    httpResponse.getEntity().getContent(),
                                    () -> closeQuietly(closeableResponse)
                            )));
                }
            }

            return respBuilder.build();

        } catch (Exception e) {
            closeQuietly(httpResponse);
            throw new RequestException("Send request raised an exception", e);
        }
    }

    private ResponseMessage sendReturnBytes(RequestMessage request, RequestContext context) {
        try {
            // request
            HttpUriRequestBase httpRequest = toHttpRequest(request, context);
            HttpClientContext httpContext = toHttpClientContext(context);

            return this.httpClient.execute(httpRequest, httpContext, response -> {
                // Process response message and convert it into a value object
                ResponseMessage.Builder respBuilder = ResponseMessage.newBuilder()
                        .request(request)
                        .statusCode(response.getCode())
                        .headers(fromHttpHeaders(response.getHeaders()));

                if (response.getEntity() != null) {
                    respBuilder.body(BinaryData.fromBytes(EntityUtils.toByteArray(response.getEntity())));
                }

                return respBuilder.build();
            });
        } catch (Exception e) {
            throw new RequestException("Send request raised an exception", e);
        }
    }

    @Override
    public String name() {
        return "Apache HttpClient 5.x";
    }

    @Override
    public void close() throws Exception {
        IdleConnectionReaper.getInstance().deregisterConnectionManager(connectionManager);
        connectionManager.close(CloseMode.IMMEDIATE);
    }

    /**
     * Gets the connection manager instance
     *
     * @return The {@link HttpClientConnectionManager} instance.
     */
    public HttpClientConnectionManager getConnectionManager() {
        return this.connectionManager;
    }

    private HttpUriRequestBase toHttpRequest(RequestMessage request, RequestContext context) {
        HttpUriRequestBase httpRequest = new HttpUriRequestBase(request.method(), request.uri());

        // headers
        request.headers().forEach((name, value) -> {
            if (IGNORE_HEADERS.stream().noneMatch(name::equalsIgnoreCase)) {
                httpRequest.addHeader(name, value);
            }
        });

        // body
        httpRequest.setEntity(toHttpEntity(request.body()));

        return httpRequest;
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

    private HttpEntity toHttpEntity(BinaryData body) {
        if (body == null) {
            return null;
        }

        if (body instanceof StringBinaryData) {
            return new StringEntity(body.toString(), (ContentType)null);
        } else if (body instanceof ByteArrayBinaryData ||
                body instanceof ByteBufferBinaryData) {
            return new ByteArrayEntity(body.toBytes(), null);
        } else {
            long length = Optional.ofNullable(body.getLength()).orElse(-1L);
            return new InputStreamEntity(body.toStream(), length, null);
        }
    }

    private Map<String, String> fromHttpHeaders(Header[] headers) {
        Map<String, String> h = new TreeMap<String, String>(String.CASE_INSENSITIVE_ORDER);
        for (Header header : headers) {
            h.put(header.getName(), header.getValue());
        }
        return h;
    }

    private static boolean determineReturnBytes(CloseableHttpResponse response) {
        // status code
        int statusCode = response.getCode();
        if (statusCode >= 300 || statusCode == 203) {
            return true;
        }

        // size
        if (!response.getEntity().isChunked()) {
            if (response.getEntity().getContentLength() <= 32 * 1024L) {
                return true;
            }
        }

        return false;
    }

    private static void closeQuietly(AutoCloseable is) {
        if (is != null) {
            try {
                is.close();
            } catch (Exception ignored) {
            }
        }
    }
}
