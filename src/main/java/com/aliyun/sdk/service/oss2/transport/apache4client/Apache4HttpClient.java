package com.aliyun.sdk.service.oss2.transport.apache4client;

import com.aliyun.sdk.service.oss2.transport.*;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.*;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

public class Apache4HttpClient implements HttpClient, AutoCloseable {

    private final CloseableHttpClient httpClient;
    private final HttpClientConnectionManager connectionManager;
    private final RequestConfig requestConfig;

    Apache4HttpClient(
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
     * {@link Apache4HttpClient} instances.
     */
    public static Apache4HttpClientBuilder custom() {
        return Apache4HttpClientBuilder.create();
    }

    /**
     * Creates {@link Apache4HttpClient} instance with default configuration.
     */
    public static Apache4HttpClient createDefault() {
        return Apache4HttpClientBuilder.create().build();
    }

    @Override
    public ResponseMessage send(RequestMessage request, RequestContext context) {
        if (RequestContext.HttpCompletionOption.ResponseHeadersRead.equals(
                context.get(RequestContext.Key.HTTP_COMPLETION_OPTION))) {
            return sendReturnStream(request, context);
        }
        return sendReturnBytes(request, context);
    }

    public ResponseMessage sendReturnStream(RequestMessage request, RequestContext context) {
        CloseableHttpResponse httpResponse = null;

        try {
            // request
            HttpRequestBase httpRequest = toHttpRequest(request, context);
            HttpClientContext httpContext = toHttpClientContext(context);

            // do
            httpResponse = this.httpClient.execute(httpRequest, httpContext);

            // response
            ResponseMessage.Builder respBuilder = ResponseMessage.newBuilder()
                    .request(request)
                    .statusCode(httpResponse.getStatusLine().getStatusCode())
                    .headers(fromHttpHeaders(httpResponse.getAllHeaders()));

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
            HttpRequestBase httpRequest = toHttpRequest(request, context);
            HttpClientContext httpContext = toHttpClientContext(context);

            return this.httpClient.execute(
                    httpRequest,
                    response->{
                        ResponseMessage.Builder respBuilder = ResponseMessage.newBuilder()
                                .request(request)
                                .statusCode(response.getStatusLine().getStatusCode())
                                .headers(fromHttpHeaders(response.getAllHeaders()));

                        if (response.getEntity() != null) {
                            respBuilder.body(BinaryData.fromBytes(EntityUtils.toByteArray(response.getEntity())));
                        }
                        return respBuilder.build();
                    },
                    httpContext);

        } catch (Exception e) {
            throw new RequestException("Send request raised an exception", e);
        }
    }

    @Override
    public String name() {
        return "Apache HttpClient 4.x";
    }

    @Override
    public void close() throws Exception {
        IdleConnectionReaper.getInstance().deregisterConnectionManager(connectionManager);
        connectionManager.shutdown();
    }

    /**
     * Gets the connection manager instance
     *
     * @return The {@link HttpClientConnectionManager} instance.
     */
    public HttpClientConnectionManager getConnectionManager() {
        return this.connectionManager;
    }

    private HttpRequestBase toHttpRequest(RequestMessage request, RequestContext context) {
        String uri = request.uri().toString();
        String method = request.method();
        BinaryData body = request.body();

        HttpRequestBase httpRequest;
        switch (method.toLowerCase()) {
            case "post":
                HttpPost postMethod = new HttpPost(uri);
                if (body != null) {
                    postMethod.setEntity(toHttpEntity(body));
                }
                httpRequest = postMethod;
                break;
            case "put":
                HttpPut putMethod = new HttpPut(uri);
                if (body != null) {
                    putMethod.setEntity(toHttpEntity(body));
                }
                httpRequest = putMethod;
                break;
            case "get":
                httpRequest = new HttpGet(uri);
                break;
            case "delete":
                if (body != null) {
                    HttpDeleteWithBody deleteMethod = new HttpDeleteWithBody(uri);
                    deleteMethod.setEntity(toHttpEntity(body));
                    httpRequest = deleteMethod;
                } else {
                    httpRequest = new HttpDelete(uri);
                }
                break;
            case "head":
                httpRequest = new HttpHead(uri);
                break;
            case "options":
                httpRequest = new HttpOptions(uri);
                break;
            default:
                throw new IllegalArgumentException("Unknown HTTP method name: " + method);
        }

        // headers
        request.headers().forEach(httpRequest::addHeader);

        return httpRequest;
    }

    private HttpClientContext toHttpClientContext(RequestContext context) {
        RequestConfig config = this.requestConfig;

        Duration rwTimeout = context.get(RequestContext.Key.READWRITE_TIMEOUT);
        if (rwTimeout != null) {
            config = RequestConfig.copy(config).setSocketTimeout((int) rwTimeout.toMillis()).build();
        }

        HttpClientContext httpClientContext = HttpClientContext.create();
        httpClientContext.setRequestConfig(config);
        return httpClientContext;
    }

    private HttpEntity toHttpEntity(BinaryData body) {
        if (body != null) {
            if (body instanceof StringBinaryData) {
                return new StringEntity(body.toString(), (ContentType) null);
            } else if (body instanceof ByteArrayBinaryData ||
                    body instanceof ByteBufferBinaryData) {
                return new ByteArrayEntity(body.toBytes(), (ContentType) null);
            } else {
                long length = Optional.ofNullable(body.getLength()).orElse(-1L);
                return new InputStreamEntity(body.toStream(), length, (ContentType) null);
            }
        }
        return null;
    }

    private Map<String, String> fromHttpHeaders(Header[] headers) {
        Map<String, String> h = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        for (Header header : headers) {
            h.put(header.getName(), header.getValue());
        }
        return h;
    }

    private static boolean determineReturnBytes(HttpResponse response) {
        // status code
        int statusCode = response.getStatusLine().getStatusCode();
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
