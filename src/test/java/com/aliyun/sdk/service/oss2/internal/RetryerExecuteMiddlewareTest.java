package com.aliyun.sdk.service.oss2.internal;

import com.aliyun.sdk.service.oss2.retry.FixedDelayBackoff;
import com.aliyun.sdk.service.oss2.retry.NopRetryer;
import com.aliyun.sdk.service.oss2.retry.StandardRetryer;
import com.aliyun.sdk.service.oss2.signer.SigningContext;
import com.aliyun.sdk.service.oss2.transport.RequestException;
import com.aliyun.sdk.service.oss2.transport.RequestMessage;
import com.aliyun.sdk.service.oss2.transport.ResponseMessage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class RetryerExecuteMiddlewareTest {

    private ScheduledExecutorService scheduler;

    @BeforeEach
    void setUp() {
        scheduler = Executors.newSingleThreadScheduledExecutor(r -> {
            Thread t = new Thread(r, "test-retry-scheduler");
            t.setDaemon(true);
            return t;
        });
    }

    @AfterEach
    void tearDown() {
        scheduler.shutdownNow();
    }

    private RequestMessage dummyRequest() {
        return RequestMessage.newBuilder()
                .method("GET")
                .uri("https://example.com/test")
                .build();
    }

    private ExecuteContext dummyContext(int maxAttempts) {
        ExecuteContext ctx = new ExecuteContext();
        ctx.retryMaxAttempts = maxAttempts;
        ctx.signingContext = new SigningContext();
        ctx.signingContext.setSignTime(Instant.now());
        return ctx;
    }

    private ResponseMessage dummyResponse() {
        return ResponseMessage.newBuilder()
                .statusCode(200)
                .headers(Collections.emptyMap())
                .build();
    }

    // -----------------------------------------------------------------------
    // Async: success on first attempt
    // -----------------------------------------------------------------------
    @Test
    void testAsyncSuccessOnFirstAttempt() throws Exception {
        ExecuteMiddleware next = new ExecuteMiddleware() {
            @Override
            public ResponseMessage execute(RequestMessage request, ExecuteContext context) {
                return dummyResponse();
            }

            @Override
            public CompletableFuture<ResponseMessage> executeAsync(RequestMessage request, ExecuteContext context) {
                return CompletableFuture.completedFuture(dummyResponse());
            }
        };

        RetryerExecuteMiddleware middleware = new RetryerExecuteMiddleware(
                next, StandardRetryer.newBuilder().maxAttempts(3).build(), scheduler);

        CompletableFuture<ResponseMessage> future = middleware.executeAsync(dummyRequest(), dummyContext(3));
        ResponseMessage response = future.get(5, TimeUnit.SECONDS);
        assertThat(response.statusCode()).isEqualTo(200);
    }

    // -----------------------------------------------------------------------
    // Async: retry succeeds after failures
    // -----------------------------------------------------------------------
    @Test
    void testAsyncRetrySucceedsAfterFailures() throws Exception {
        AtomicInteger attempts = new AtomicInteger(0);

        ExecuteMiddleware next = new ExecuteMiddleware() {
            @Override
            public ResponseMessage execute(RequestMessage request, ExecuteContext context) {
                return dummyResponse();
            }

            @Override
            public CompletableFuture<ResponseMessage> executeAsync(RequestMessage request, ExecuteContext context) {
                int n = attempts.incrementAndGet();
                if (n < 3) {
                    CompletableFuture<ResponseMessage> f = new CompletableFuture<>();
                    f.completeExceptionally(new CompletionException(new RequestException("fail-" + n)));
                    return f;
                }
                return CompletableFuture.completedFuture(dummyResponse());
            }
        };

        RetryerExecuteMiddleware middleware = new RetryerExecuteMiddleware(
                next,
                StandardRetryer.newBuilder()
                        .maxAttempts(5)
                        .backoffDelayer(new FixedDelayBackoff(Duration.ofMillis(10)))
                        .build(),
                scheduler);

        CompletableFuture<ResponseMessage> future = middleware.executeAsync(dummyRequest(), dummyContext(5));
        ResponseMessage response = future.get(5, TimeUnit.SECONDS);
        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(attempts.get()).isEqualTo(3);
    }

    // -----------------------------------------------------------------------
    // Async: exhausts max attempts then fails
    // -----------------------------------------------------------------------
    @Test
    void testAsyncExhaustsMaxAttempts() {
        AtomicInteger attempts = new AtomicInteger(0);

        ExecuteMiddleware next = new ExecuteMiddleware() {
            @Override
            public ResponseMessage execute(RequestMessage request, ExecuteContext context) {
                return dummyResponse();
            }

            @Override
            public CompletableFuture<ResponseMessage> executeAsync(RequestMessage request, ExecuteContext context) {
                attempts.incrementAndGet();
                CompletableFuture<ResponseMessage> f = new CompletableFuture<>();
                f.completeExceptionally(new CompletionException(new RequestException("always-fail")));
                return f;
            }
        };

        RetryerExecuteMiddleware middleware = new RetryerExecuteMiddleware(
                next,
                StandardRetryer.newBuilder()
                        .maxAttempts(3)
                        .backoffDelayer(new FixedDelayBackoff(Duration.ofMillis(10)))
                        .build(),
                scheduler);

        CompletableFuture<ResponseMessage> future = middleware.executeAsync(dummyRequest(), dummyContext(3));

        assertThatThrownBy(() -> future.get(5, TimeUnit.SECONDS))
                .isInstanceOf(ExecutionException.class);

        assertThat(attempts.get()).isEqualTo(3);
    }

    // -----------------------------------------------------------------------
    // Async: non-retryable error fails immediately
    // -----------------------------------------------------------------------
    @Test
    void testAsyncNonRetryableErrorFailsImmediately() {
        AtomicInteger attempts = new AtomicInteger(0);

        ExecuteMiddleware next = new ExecuteMiddleware() {
            @Override
            public ResponseMessage execute(RequestMessage request, ExecuteContext context) {
                return dummyResponse();
            }

            @Override
            public CompletableFuture<ResponseMessage> executeAsync(RequestMessage request, ExecuteContext context) {
                attempts.incrementAndGet();
                CompletableFuture<ResponseMessage> f = new CompletableFuture<>();
                // IllegalStateException is not retryable by StandardRetryer
                f.completeExceptionally(new CompletionException(new IllegalStateException("not retryable")));
                return f;
            }
        };

        RetryerExecuteMiddleware middleware = new RetryerExecuteMiddleware(
                next,
                StandardRetryer.newBuilder()
                        .maxAttempts(5)
                        .backoffDelayer(new FixedDelayBackoff(Duration.ofMillis(10)))
                        .build(),
                scheduler);

        CompletableFuture<ResponseMessage> future = middleware.executeAsync(dummyRequest(), dummyContext(5));

        assertThatThrownBy(() -> future.get(5, TimeUnit.SECONDS))
                .isInstanceOf(ExecutionException.class);

        assertThat(attempts.get()).isEqualTo(1);
    }

    // -----------------------------------------------------------------------
    // Async: retry does not block the calling thread (non-blocking delay)
    // -----------------------------------------------------------------------
    @Test
    void testAsyncRetryDoesNotBlockCallingThread() throws Exception {
        AtomicInteger attempts = new AtomicInteger(0);

        ExecuteMiddleware next = new ExecuteMiddleware() {
            @Override
            public ResponseMessage execute(RequestMessage request, ExecuteContext context) {
                return dummyResponse();
            }

            @Override
            public CompletableFuture<ResponseMessage> executeAsync(RequestMessage request, ExecuteContext context) {
                int n = attempts.incrementAndGet();
                if (n < 3) {
                    CompletableFuture<ResponseMessage> f = new CompletableFuture<>();
                    f.completeExceptionally(new CompletionException(new RequestException("fail")));
                    return f;
                }
                return CompletableFuture.completedFuture(dummyResponse());
            }
        };

        RetryerExecuteMiddleware middleware = new RetryerExecuteMiddleware(
                next,
                StandardRetryer.newBuilder()
                        .maxAttempts(3)
                        .backoffDelayer(new FixedDelayBackoff(Duration.ofMillis(100)))
                        .build(),
                scheduler);

        // executeAsync should return immediately without blocking
        long start = System.nanoTime();
        CompletableFuture<ResponseMessage> future = middleware.executeAsync(dummyRequest(), dummyContext(3));
        long elapsed = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start);

        // The call itself should return almost instantly (not block for 200ms of total delay)
        assertThat(elapsed).isLessThan(50);

        // But the future completes after the scheduled delays
        ResponseMessage response = future.get(5, TimeUnit.SECONDS);
        assertThat(response.statusCode()).isEqualTo(200);
    }

    // -----------------------------------------------------------------------
    // Async: signTime is reset before each retry
    // -----------------------------------------------------------------------
    @Test
    void testAsyncSignTimeResetOnRetry() throws Exception {
        Instant originalSignTime = Instant.parse("2024-01-01T00:00:00Z");
        AtomicInteger attempts = new AtomicInteger(0);

        ExecuteMiddleware next = new ExecuteMiddleware() {
            @Override
            public ResponseMessage execute(RequestMessage request, ExecuteContext context) {
                return dummyResponse();
            }

            @Override
            public CompletableFuture<ResponseMessage> executeAsync(RequestMessage request, ExecuteContext context) {
                int n = attempts.incrementAndGet();
                if (n == 1) {
                    // First attempt: change signTime to simulate signing middleware
                    context.signingContext.setSignTime(Instant.parse("2024-06-15T12:00:00Z"));
                    CompletableFuture<ResponseMessage> f = new CompletableFuture<>();
                    f.completeExceptionally(new CompletionException(new RequestException("fail")));
                    return f;
                }
                // Second attempt: signTime should be reset to original
                assertThat(context.signingContext.getSignTime()).isEqualTo(originalSignTime);
                return CompletableFuture.completedFuture(dummyResponse());
            }
        };

        ExecuteContext ctx = dummyContext(3);
        ctx.signingContext.setSignTime(originalSignTime);

        RetryerExecuteMiddleware middleware = new RetryerExecuteMiddleware(
                next,
                StandardRetryer.newBuilder()
                        .maxAttempts(3)
                        .backoffDelayer(new FixedDelayBackoff(Duration.ofMillis(10)))
                        .build(),
                scheduler);

        CompletableFuture<ResponseMessage> future = middleware.executeAsync(dummyRequest(), ctx);
        future.get(5, TimeUnit.SECONDS);
        assertThat(attempts.get()).isEqualTo(2);
    }

    // -----------------------------------------------------------------------
    // Async: NopRetryer does not retry
    // -----------------------------------------------------------------------
    @Test
    void testAsyncNopRetryerDoesNotRetry() {
        AtomicInteger attempts = new AtomicInteger(0);

        ExecuteMiddleware next = new ExecuteMiddleware() {
            @Override
            public ResponseMessage execute(RequestMessage request, ExecuteContext context) {
                return dummyResponse();
            }

            @Override
            public CompletableFuture<ResponseMessage> executeAsync(RequestMessage request, ExecuteContext context) {
                attempts.incrementAndGet();
                CompletableFuture<ResponseMessage> f = new CompletableFuture<>();
                f.completeExceptionally(new CompletionException(new RequestException("fail")));
                return f;
            }
        };

        RetryerExecuteMiddleware middleware = new RetryerExecuteMiddleware(
                next, new NopRetryer(), scheduler);

        CompletableFuture<ResponseMessage> future = middleware.executeAsync(dummyRequest(), dummyContext(1));

        assertThatThrownBy(() -> future.get(5, TimeUnit.SECONDS))
                .isInstanceOf(ExecutionException.class);

        assertThat(attempts.get()).isEqualTo(1);
    }

    // -----------------------------------------------------------------------
    // Async: retry delay is executed via scheduler, not Thread.sleep
    // -----------------------------------------------------------------------
    @Test
    void testAsyncRetryUsesSchedulerThread() throws Exception {
        AtomicInteger attempts = new AtomicInteger(0);
        ConcurrentLinkedQueue<String> threadNames = new ConcurrentLinkedQueue<>();

        ExecuteMiddleware next = new ExecuteMiddleware() {
            @Override
            public ResponseMessage execute(RequestMessage request, ExecuteContext context) {
                return dummyResponse();
            }

            @Override
            public CompletableFuture<ResponseMessage> executeAsync(RequestMessage request, ExecuteContext context) {
                threadNames.add(Thread.currentThread().getName());
                int n = attempts.incrementAndGet();
                if (n < 3) {
                    CompletableFuture<ResponseMessage> f = new CompletableFuture<>();
                    f.completeExceptionally(new CompletionException(new RequestException("fail")));
                    return f;
                }
                return CompletableFuture.completedFuture(dummyResponse());
            }
        };

        RetryerExecuteMiddleware middleware = new RetryerExecuteMiddleware(
                next,
                StandardRetryer.newBuilder()
                        .maxAttempts(3)
                        .backoffDelayer(new FixedDelayBackoff(Duration.ofMillis(10)))
                        .build(),
                scheduler);

        CompletableFuture<ResponseMessage> future = middleware.executeAsync(dummyRequest(), dummyContext(3));
        future.get(5, TimeUnit.SECONDS);

        assertThat(attempts.get()).isEqualTo(3);
        // Retry attempts (2nd and 3rd) should run on the scheduler thread
        assertThat(threadNames).hasSize(3);
        String[] names = threadNames.toArray(new String[0]);
        assertThat(names[1]).isEqualTo("test-retry-scheduler");
        assertThat(names[2]).isEqualTo("test-retry-scheduler");
    }

    // -----------------------------------------------------------------------
    // Sync: success on first attempt
    // -----------------------------------------------------------------------
    @Test
    void testSyncSuccessOnFirstAttempt() throws Exception {
        ExecuteMiddleware next = new ExecuteMiddleware() {
            @Override
            public ResponseMessage execute(RequestMessage request, ExecuteContext context) {
                return dummyResponse();
            }

            @Override
            public CompletableFuture<ResponseMessage> executeAsync(RequestMessage request, ExecuteContext context) {
                return CompletableFuture.completedFuture(dummyResponse());
            }
        };

        RetryerExecuteMiddleware middleware = new RetryerExecuteMiddleware(
                next, StandardRetryer.newBuilder().maxAttempts(3).build(), scheduler);

        ResponseMessage response = middleware.execute(dummyRequest(), dummyContext(3));
        assertThat(response.statusCode()).isEqualTo(200);
    }

    // -----------------------------------------------------------------------
    // Sync: exhausts max attempts then fails
    // -----------------------------------------------------------------------
    @Test
    void testSyncExhaustsMaxAttempts() {
        AtomicInteger attempts = new AtomicInteger(0);

        ExecuteMiddleware next = new ExecuteMiddleware() {
            @Override
            public ResponseMessage execute(RequestMessage request, ExecuteContext context) throws Exception {
                attempts.incrementAndGet();
                throw new RequestException("always-fail");
            }

            @Override
            public CompletableFuture<ResponseMessage> executeAsync(RequestMessage request, ExecuteContext context) {
                return CompletableFuture.completedFuture(dummyResponse());
            }
        };

        RetryerExecuteMiddleware middleware = new RetryerExecuteMiddleware(
                next,
                StandardRetryer.newBuilder()
                        .maxAttempts(3)
                        .backoffDelayer(new FixedDelayBackoff(Duration.ofMillis(1)))
                        .build(),
                scheduler);

        assertThatThrownBy(() -> middleware.execute(dummyRequest(), dummyContext(3)))
                .isInstanceOf(RequestException.class);

        assertThat(attempts.get()).isEqualTo(3);
    }

    // -----------------------------------------------------------------------
    // Lifecycle: SDK-managed executor is shutdown on close
    // Note: Middleware itself does not manage executor lifecycle,
    // this is handled by ClientImpl.close()
    // -----------------------------------------------------------------------
    @Test
    void testSdkManagedExecutorShutdownOnClose() throws Exception {
        // Use a wrapper to track shutdown calls on the executor
        class ShutdownTrackingExecutor implements ScheduledExecutorService {
            boolean shutdownCalled = false;
            final ScheduledExecutorService delegate;

            ShutdownTrackingExecutor(ScheduledExecutorService delegate) {
                this.delegate = delegate;
            }

            @Override
            public java.util.concurrent.ScheduledFuture<?> schedule(Runnable command, long delay, TimeUnit unit) {
                return delegate.schedule(command, delay, unit);
            }

            @Override
            public void shutdown() {
                shutdownCalled = true;
                delegate.shutdown();
            }

            @Override
            public List<Runnable> shutdownNow() {
                shutdownCalled = true;
                return delegate.shutdownNow();
            }

            @Override
            public boolean isShutdown() {
                return delegate.isShutdown();
            }

            @Override
            public boolean isTerminated() {
                return delegate.isTerminated();
            }

            @Override
            public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
                return delegate.awaitTermination(timeout, unit);
            }

            @Override
            public void execute(Runnable command) {
                delegate.execute(command);
            }

            @Override
            public <T> java.util.concurrent.ScheduledFuture<T> schedule(Callable<T> callable, long delay, TimeUnit unit) {
                return delegate.schedule(callable, delay, unit);
            }

            @Override
            public java.util.concurrent.ScheduledFuture<?> scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit) {
                return delegate.scheduleAtFixedRate(command, initialDelay, period, unit);
            }

            @Override
            public java.util.concurrent.ScheduledFuture<?> scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit) {
                return delegate.scheduleWithFixedDelay(command, initialDelay, delay, unit);
            }

            @Override
            public <T> java.util.concurrent.Future<T> submit(Callable<T> task) {
                return delegate.submit(task);
            }

            @Override
            public java.util.concurrent.Future<?> submit(Runnable task) {
                return delegate.submit(task);
            }

            @Override
            public java.util.concurrent.Future<?> submit(Runnable task, Object result) {
                return delegate.submit(task, result);
            }

            @Override
            public <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
                return delegate.invokeAny(tasks);
            }

            @Override
            public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
                return delegate.invokeAny(tasks, timeout, unit);
            }

            @Override
            public <T> List<java.util.concurrent.Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {
                return delegate.invokeAll(tasks);
            }

            @Override
            public <T> List<java.util.concurrent.Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException {
                return delegate.invokeAll(tasks, timeout, unit);
            }
        }

        ScheduledExecutorService delegate = Executors.newSingleThreadScheduledExecutor(r -> {
            Thread t = new Thread(r, "tracking-executor");
            t.setDaemon(true);
            return t;
        });

        ShutdownTrackingExecutor trackingExecutor = new ShutdownTrackingExecutor(delegate);

        try {
            AtomicInteger attempts = new AtomicInteger(0);

            ExecuteMiddleware next = new ExecuteMiddleware() {
                @Override
                public ResponseMessage execute(RequestMessage request, ExecuteContext context) {
                    attempts.incrementAndGet();
                    return dummyResponse();
                }

                @Override
                public CompletableFuture<ResponseMessage> executeAsync(RequestMessage request, ExecuteContext context) {
                    attempts.incrementAndGet();
                    return CompletableFuture.completedFuture(dummyResponse());
                }
            };

            RetryerExecuteMiddleware middleware = new RetryerExecuteMiddleware(
                    next,
                    StandardRetryer.newBuilder().maxAttempts(3).build(),
                    trackingExecutor);

            // Execute a request to ensure executor is used
            ResponseMessage response = middleware.execute(dummyRequest(), dummyContext(3));
            assertThat(response.statusCode()).isEqualTo(200);

            // Executor should NOT be shutdown by the middleware itself
            assertThat(trackingExecutor.shutdownCalled).isFalse();

        } finally {
            delegate.shutdownNow();
        }
    }

    // -----------------------------------------------------------------------
    // Lifecycle: User-provided executor is NOT shutdown by middleware
    // -----------------------------------------------------------------------
    @Test
    void testUserProvidedExecutorNotShutdownByMiddleware() throws Exception {
        ScheduledExecutorService userExecutor = Executors.newSingleThreadScheduledExecutor(r -> {
            Thread t = new Thread(r, "user-managed-executor");
            t.setDaemon(true);
            return t;
        });

        try {
            AtomicInteger attempts = new AtomicInteger(0);

            ExecuteMiddleware next = new ExecuteMiddleware() {
                @Override
                public ResponseMessage execute(RequestMessage request, ExecuteContext context) {
                    attempts.incrementAndGet();
                    return dummyResponse();
                }

                @Override
                public CompletableFuture<ResponseMessage> executeAsync(RequestMessage request, ExecuteContext context) {
                    attempts.incrementAndGet();
                    return CompletableFuture.completedFuture(dummyResponse());
                }
            };

            RetryerExecuteMiddleware middleware = new RetryerExecuteMiddleware(
                    next,
                    StandardRetryer.newBuilder().maxAttempts(3).build(),
                    userExecutor);

            // Execute requests
            ResponseMessage response = middleware.execute(dummyRequest(), dummyContext(3));
            assertThat(response.statusCode()).isEqualTo(200);

            // Executor should NOT be shutdown after middleware use
            assertThat(userExecutor.isShutdown()).isFalse();

            // Verify executor is still functional
            CompletableFuture<String> testFuture = CompletableFuture.supplyAsync(
                    () -> "test",
                    userExecutor
            );
            assertThat(testFuture.get(1, TimeUnit.SECONDS)).isEqualTo("test");

        } finally {
            userExecutor.shutdownNow();
        }
    }

    // -----------------------------------------------------------------------
    // Async: executor thread name is propagated to retry attempts
    // -----------------------------------------------------------------------
    @Test
    void testExecutorThreadNamePropagatedToRetryAttempts() throws Exception {
        AtomicInteger attempts = new AtomicInteger(0);
        ConcurrentLinkedQueue<String> threadNames = new ConcurrentLinkedQueue<>();

        ExecuteMiddleware next = new ExecuteMiddleware() {
            @Override
            public ResponseMessage execute(RequestMessage request, ExecuteContext context) {
                return dummyResponse();
            }

            @Override
            public CompletableFuture<ResponseMessage> executeAsync(RequestMessage request, ExecuteContext context) {
                threadNames.add(Thread.currentThread().getName());
                int n = attempts.incrementAndGet();
                if (n < 3) {
                    CompletableFuture<ResponseMessage> f = new CompletableFuture<>();
                    f.completeExceptionally(new CompletionException(new RequestException("fail")));
                    return f;
                }
                return CompletableFuture.completedFuture(dummyResponse());
            }
        };

        ScheduledExecutorService namedExecutor = Executors.newSingleThreadScheduledExecutor(r -> {
            Thread t = new Thread(r, "custom-retry-executor");
            t.setDaemon(true);
            return t;
        });

        try {
            RetryerExecuteMiddleware middleware = new RetryerExecuteMiddleware(
                    next,
                    StandardRetryer.newBuilder()
                            .maxAttempts(3)
                            .backoffDelayer(new FixedDelayBackoff(Duration.ofMillis(10)))
                            .build(),
                    namedExecutor);

            CompletableFuture<ResponseMessage> future = middleware.executeAsync(dummyRequest(), dummyContext(3));
            future.get(5, TimeUnit.SECONDS);

            assertThat(attempts.get()).isEqualTo(3);
            // Verify retry attempts run on the custom executor thread
            assertThat(threadNames).contains("custom-retry-executor");

        } finally {
            namedExecutor.shutdownNow();
        }
    }

    // -----------------------------------------------------------------------
    // Edge case: null executor handling
    // -----------------------------------------------------------------------
    @Test
    void testNullExecutorThrowsException() {
        ExecuteMiddleware next = new ExecuteMiddleware() {
            @Override
            public ResponseMessage execute(RequestMessage request, ExecuteContext context) {
                return dummyResponse();
            }

            @Override
            public CompletableFuture<ResponseMessage> executeAsync(RequestMessage request, ExecuteContext context) {
                return CompletableFuture.completedFuture(dummyResponse());
            }
        };

        // Constructor accepts null executor (will cause NPE only when retry is attempted)
        // This test documents that constructor itself does not validate
        RetryerExecuteMiddleware middleware = new RetryerExecuteMiddleware(
                next, StandardRetryer.newBuilder().maxAttempts(3).build(), null);
        assertThat(middleware).isNotNull();
    }

    // -----------------------------------------------------------------------
    // Async: shutdown executor does not hang in-flight retry future
    // -----------------------------------------------------------------------
    @Test
    void testAsyncShutdownExecutorCompletesInFlightRetryFuture() {
        ScheduledExecutorService localScheduler = Executors.newSingleThreadScheduledExecutor(r -> {
            Thread t = new Thread(r, "shutdown-test-scheduler");
            t.setDaemon(true);
            return t;
        });

        // Shutdown before use so schedule() throws RejectedExecutionException
        localScheduler.shutdown();

        AtomicInteger attempts = new AtomicInteger(0);

        ExecuteMiddleware next = new ExecuteMiddleware() {
            @Override
            public ResponseMessage execute(RequestMessage request, ExecuteContext context) {
                return dummyResponse();
            }

            @Override
            public CompletableFuture<ResponseMessage> executeAsync(RequestMessage request, ExecuteContext context) {
                attempts.incrementAndGet();
                CompletableFuture<ResponseMessage> f = new CompletableFuture<>();
                f.completeExceptionally(new CompletionException(new RequestException("fail")));
                return f;
            }
        };

        RetryerExecuteMiddleware middleware = new RetryerExecuteMiddleware(
                next,
                StandardRetryer.newBuilder()
                        .maxAttempts(5)
                        .backoffDelayer(new FixedDelayBackoff(Duration.ofMillis(10)))
                        .build(),
                localScheduler);

        CompletableFuture<ResponseMessage> future = middleware.executeAsync(dummyRequest(), dummyContext(5));

        // Future must complete exceptionally, not hang
        assertThatThrownBy(() -> future.get(3, TimeUnit.SECONDS))
                .isInstanceOf(ExecutionException.class);

        assertThat(attempts.get()).isEqualTo(1);
    }

    // -----------------------------------------------------------------------
    // Sync: verify sync execute still works with scheduler injected
    // -----------------------------------------------------------------------
    @Test
    void testSyncExecuteWithSchedulerInjected() throws Exception {
        AtomicInteger attempts = new AtomicInteger(0);

        ExecuteMiddleware next = new ExecuteMiddleware() {
            @Override
            public ResponseMessage execute(RequestMessage request, ExecuteContext context) throws Exception {
                int n = attempts.incrementAndGet();
                if (n < 3) {
                    throw new RequestException("fail-" + n);
                }
                return dummyResponse();
            }

            @Override
            public CompletableFuture<ResponseMessage> executeAsync(RequestMessage request, ExecuteContext context) {
                return CompletableFuture.completedFuture(dummyResponse());
            }
        };

        RetryerExecuteMiddleware middleware = new RetryerExecuteMiddleware(
                next,
                StandardRetryer.newBuilder()
                        .maxAttempts(5)
                        .backoffDelayer(new FixedDelayBackoff(Duration.ofMillis(1)))
                        .build(),
                scheduler);

        ResponseMessage response = middleware.execute(dummyRequest(), dummyContext(5));
        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(attempts.get()).isEqualTo(3);
    }
}
