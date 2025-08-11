package com.aliyun.sdk.service.oss2.paginator.flow;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

class PaginatedSubscription<T> implements java.util.concurrent.Flow.Subscription {

    protected final java.util.concurrent.Flow.Subscriber subscriber;
    protected final PageFetcher<T> nextPageFetcher;
    private final AtomicBoolean isTerminated = new AtomicBoolean(false);
    private final AtomicBoolean isTaskRunning = new AtomicBoolean(false);
    protected AtomicLong requestN = new AtomicLong(0);
    protected volatile T currentPage;

    PaginatedSubscription(java.util.concurrent.Flow.Subscriber subscriber, PageFetcher<T> pageFetcher) {
        this.subscriber = subscriber;
        this.nextPageFetcher = pageFetcher;
    }

    @Override
    public void request(long n) {
        if (isTerminated()) {
            return;
        }

        if (n <= 0) {
            subscriber.onError(new IllegalArgumentException("Non-positive request signals are illegal"));
        }

        AtomicBoolean startTask = new AtomicBoolean(false);
        synchronized (this) {
            requestN.addAndGet(n);
            startTask.set(startTask());
        }

        if (startTask.get()) {
            handleRequests();
        }
    }

    @Override
    public void cancel() {
        cleanup();
    }

    private void completeSubscription() {
        if (!isTerminated()) {
            subscriber.onComplete();
            cleanup();
        }
    }

    @SuppressWarnings("unchecked")
    private void handleRequests() {
        if (!hasNextPage()) {
            completeSubscription();
            return;
        }

        synchronized (this) {
            if (requestN.get() <= 0) {
                stopTask();
                return;
            }
        }

        if (!isTerminated()) {
            requestN.getAndDecrement();
            nextPageFetcher.nextPage(currentPage)
                    .whenComplete(((response, error) -> {
                        if (response != null) {
                            currentPage = response;
                            subscriber.onNext(response);
                            handleRequests();
                        }
                        if (error != null) {
                            subscriber.onError(error);
                            cleanup();
                        }
                    }));
        }
    }

    private boolean hasNextPage() {
        return currentPage == null || nextPageFetcher.hasNextPage(currentPage);
    }

    private void terminate() {
        isTerminated.compareAndSet(false, true);
    }

    private boolean isTerminated() {
        return isTerminated.get();
    }

    private void stopTask() {
        isTaskRunning.set(false);
    }

    private synchronized boolean startTask() {
        return !isTerminated() && isTaskRunning.compareAndSet(false, true);
    }

    private synchronized void cleanup() {
        terminate();
        stopTask();
    }

    public interface PageFetcher<T> {
        boolean hasNextPage(T oldPage);

        CompletableFuture<T> nextPage(T oldPage);
    }
}
