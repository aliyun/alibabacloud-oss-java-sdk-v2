package com.aliyun.sdk.service.oss2.internal;

import com.aliyun.sdk.service.oss2.transport.RequestMessage;
import com.aliyun.sdk.service.oss2.transport.ResponseMessage;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

/**
 * Middleware for checking and processing response messages after request execution
 */
public class ResponseCheckerExecuteMiddleware implements ExecuteMiddleware {

    /**
     * Reference to the next middleware handler in the chain
     */
    private final ExecuteMiddleware nextHandler;

    /**
     * Constructor that initializes the next handler in the middleware chain
     *
     * @param nextHandler The next middleware to invoke
     */
    public ResponseCheckerExecuteMiddleware(ExecuteMiddleware nextHandler) {
        this.nextHandler = nextHandler;
    }

    /**
     * Synchronously executes the next middleware and processes the response message
     *
     * @param request The request message object {@link RequestMessage}
     * @param context The execution context object {@link ExecuteContext}
     * @return Returns the processed response message object {@link ResponseMessage}
     * @throws Exception If an error occurs during execution
     */
    @Override
    public ResponseMessage execute(RequestMessage request, ExecuteContext context) throws Exception {
        return onResponseMessage(nextHandler.execute(request, context), context);
    }

    /**
     * Asynchronously executes the next middleware and processes the response message
     *
     * @param request The request message object {@link RequestMessage}
     * @param context The execution context object {@link ExecuteContext}
     * @return A CompletableFuture wrapping the processed response message object
     */
    @Override
    public CompletableFuture<ResponseMessage> executeAsync(RequestMessage request, ExecuteContext context) {
        return nextHandler.executeAsync(request, context).thenApply(response -> onResponseMessage(response, context));
    }

    /**
     * Performs common processing on the response message, such as triggering callbacks
     *
     * @param response The original response message object {@link ResponseMessage}
     * @param context  The execution context object {@link ExecuteContext}
     * @return Returns the original or modified response message object
     */
    private ResponseMessage onResponseMessage(ResponseMessage response, ExecuteContext context) {
        if (context.onResponseMessage != null) {
            for (Consumer<ResponseMessage> consumer : context.onResponseMessage) {
                consumer.accept(response);
            }
        }
        return response;
    }
}
