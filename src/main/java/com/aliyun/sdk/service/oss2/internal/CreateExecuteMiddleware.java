package com.aliyun.sdk.service.oss2.internal;

/**
 * Functional interface for creating execute middleware in a chain-of-responsibility pattern
 */
@FunctionalInterface
public interface CreateExecuteMiddleware {
    /**
     * Creates and returns a new ExecuteMiddleware instance
     *
     * @param next The next middleware node in the execution stack
     * @return A newly created {@link ExecuteMiddleware} instance
     */
    ExecuteMiddleware create(ExecuteMiddleware next);
}
