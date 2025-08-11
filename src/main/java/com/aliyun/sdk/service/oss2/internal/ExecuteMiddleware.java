package com.aliyun.sdk.service.oss2.internal;

import com.aliyun.sdk.service.oss2.transport.RequestMessage;
import com.aliyun.sdk.service.oss2.transport.ResponseMessage;

import java.util.concurrent.CompletableFuture;

/**
 * Interface for defining synchronous and asynchronous request execution logic
 */
public interface ExecuteMiddleware {
    /**
     * Synchronously executes a request and returns the response message
     *
     * @param request The request message object {@link RequestMessage}
     * @param context The execution context object {@link ExecuteContext}
     * @return The processed response message object {@link ResponseMessage}
     * @throws Exception If an error occurs during execution
     */
    ResponseMessage execute(RequestMessage request, ExecuteContext context) throws Exception;

    /**
     * Asynchronously executes a request and returns a future containing the response message
     *
     * @param request The request message object {@link RequestMessage}
     * @param context The execution context object {@link ExecuteContext}
     * @return A CompletableFuture containing the response message object {@link ResponseMessage}
     */
    CompletableFuture<ResponseMessage> executeAsync(RequestMessage request, ExecuteContext context);

}
