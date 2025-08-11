package com.aliyun.sdk.service.oss2.internal;

import com.aliyun.sdk.service.oss2.transport.RequestMessage;
import com.aliyun.sdk.service.oss2.transport.ResponseMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

/**
 * Execution stack class used to manage and run middleware chain in sequence
 */
public class ExecuteStack {

    /**
     * Transport layer middleware, acts as the end processor of the chain
     */
    private final TransportExecuteMiddleware transport;
    /**
     * List of middleware creation functions used to build the chain
     */
    private final List<Function<ExecuteMiddleware, ExecuteMiddleware>> stack;
    /**
     * Cached resolved middleware chain instance to avoid re-building
     */
    private volatile ExecuteMiddleware cached;

    /**
     * Constructor that initializes the execution stack with a transport middleware
     *
     * @param transport Base transport layer handler
     */
    public ExecuteStack(TransportExecuteMiddleware transport) {
        this.transport = transport;
        this.stack = new ArrayList<>();
    }

    /**
     * Resolves and builds the full middleware chain
     *
     * @return The fully built middleware instance
     */
    public ExecuteMiddleware resolve() {
        if (cached == null) {
            synchronized (ExecuteStack.class) {
                if (cached == null) {
                    ExecuteMiddleware prev = transport;
                    for (int i = stack.size() - 1; i >= 0; i--) {
                        prev = stack.get(i).apply(prev);
                    }
                    cached = prev;
                }
            }
        }
        return cached;
    }

    /**
     * Adds a new middleware factory function to the stack
     *
     * @param create A function used to create a new middleware node
     * @param __     Debug name
     */
    public void push(Function<ExecuteMiddleware, ExecuteMiddleware> create, String __) {
        this.stack.add(create);
        this.cached = null;
    }

    /**
     * Synchronously executes the entire middleware chain and returns the response message
     *
     * @param request The request message object {@link RequestMessage}
     * @param context The execution context object {@link ExecuteContext}
     * @return A processed response message object {@link ResponseMessage}
     * @throws Exception If an error occurs during execution
     */
    public ResponseMessage execute(RequestMessage request, ExecuteContext context) throws Exception {
        ExecuteMiddleware handler = resolve();
        return handler.execute(request, context);
    }

    /**
     * Asynchronously executes the middleware chain and returns a future containing the response
     *
     * @param request The request message object {@link RequestMessage}
     * @param context The execution context object {@link ExecuteContext}
     * @return A CompletableFuture wrapping the response message object {@link ResponseMessage}
     */
    public CompletableFuture<ResponseMessage> executeAsync(RequestMessage request, ExecuteContext context) {
        ExecuteMiddleware handler = resolve();
        return handler.executeAsync(request, context);
    }
}
