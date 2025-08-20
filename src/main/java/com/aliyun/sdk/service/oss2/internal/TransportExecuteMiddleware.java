package com.aliyun.sdk.service.oss2.internal;

import com.aliyun.sdk.service.oss2.io.ObservableInputStream;
import com.aliyun.sdk.service.oss2.io.StreamObserver;
import com.aliyun.sdk.service.oss2.transport.*;

import java.nio.channels.WritableByteChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Middleware that handles actual network transport using an HTTP client
 */
public class TransportExecuteMiddleware implements ExecuteMiddleware {
    /**
     * The HTTP client used for making network requests
     */
    final HttpClient httpClient;

    /**
     * Constructor that initializes the middleware with an HTTP client
     *
     * @param httpClient A {@link HttpClient} instance used for network communication
     */
    public TransportExecuteMiddleware(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    private static RequestMessage processRequest(RequestMessage request, ExecuteContext context) {
        //change to ObserverInputStream
        if (context.requestBodyObserver != null && !context.requestBodyObserver.isEmpty()) {
            ObservableInputStream ois = new ObservableInputStream(request.body().toStream());
            for (StreamObserver observer : context.requestBodyObserver) {
                ois.add(observer);
            }
            return request.toBuilder().body(BinaryData.fromStream(ois)).build();
        }
        return request;
    }

    private static ResponseMessage processResponse(ResponseMessage response) {
        // always save response body into memory when status code is not 2xx(not include 203)
        int statusCode = response.statusCode();
        if (statusCode == 203 || statusCode / 100 != 2) {
            BinaryData body = response.body();
            if (body instanceof InputStreamBinaryData) {
                ResponseMessage res = response.toBuilder().body(new ByteArrayBinaryData(body.toBytes())).build();
                ((InputStreamBinaryData) body).tryClose();
                return res;
            }
        }
        return response;
    }

    private static RequestContext toRequestContext(ExecuteContext context) {
        RequestContext ctx = RequestContext.empty();
        if (context.responseHeadersRead != null) {
            ctx.put(RequestContext.Key.HTTP_COMPLETION_OPTION, RequestContext.HttpCompletionOption.ResponseHeadersRead);
        }
        return ctx;
    }

    private static RequestContext toRequestContextAsync(ExecuteContext context) {
        RequestContext ctx = toRequestContext(context);
        if (context.requestBodyObserver != null && !context.requestBodyObserver.isEmpty()) {
            List<ObservableByteChannel>  channels = new ArrayList<>();
            for (StreamObserver observer : context.requestBodyObserver) {
                channels.add(new ByteChannelObserver(observer));
            }
            ctx.put(RequestContext.Key.UPLOAD_OBSERVER_CHANNEL, channels);
        }
        return ctx;
    }

    /**
     * Synchronously sends the request over the network and returns the response
     *
     * @param request The request message object {@link RequestMessage}
     * @param context The execution context object {@link ExecuteContext}
     * @return Returns the response message received from the server {@link ResponseMessage}
     * @throws Exception If an error occurs during request execution
     */
    @Override
    public ResponseMessage execute(RequestMessage request, ExecuteContext context) throws Exception {
        return processResponse(httpClient.send(processRequest(request, context), toRequestContext(context)));
    }

    /**
     * Asynchronously sends the request and returns a future containing the response
     *
     * @param request The request message object {@link RequestMessage}
     * @param context The execution context object {@link ExecuteContext}
     * @return A CompletableFuture wrapping the response message object {@link ResponseMessage}
     */
    @Override
    public CompletableFuture<ResponseMessage> executeAsync(RequestMessage request, ExecuteContext context) {
        return httpClient.sendAsync(request, toRequestContextAsync(context))
                .thenApplyAsync(TransportExecuteMiddleware::processResponse);
    }
}
