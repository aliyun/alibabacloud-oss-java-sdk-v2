package com.aliyun.sdk.service.oss2.paginator.flow;

import com.aliyun.sdk.service.oss2.OSSAsyncClient;
import com.aliyun.sdk.service.oss2.models.ListObjectsV2Request;
import com.aliyun.sdk.service.oss2.models.ListObjectsV2Result;
import com.aliyun.sdk.service.oss2.paginator.ListObjectsV2Iterable;
import com.aliyun.sdk.service.oss2.paginator.PaginatorOptions;

import java.util.concurrent.CompletableFuture;

public class ListObjectsV2Publisher extends PaginatedPublisher<ListObjectsV2Result> {

    private final OSSAsyncClient client;

    private final ListObjectsV2Request firstRequest;

    public ListObjectsV2Publisher(OSSAsyncClient client, ListObjectsV2Request request) {
        this(client, request, PaginatorOptions.defaults());
    }

    public ListObjectsV2Publisher(OSSAsyncClient client, ListObjectsV2Request request, PaginatorOptions options) {
        this.client = client;
        this.firstRequest = ListObjectsV2Iterable.applyRequest(request, options);
    }

    @Override
    public void subscribe(java.util.concurrent.Flow.Subscriber<? super ListObjectsV2Result> subscriber) {
        subscriber.onSubscribe(new PaginatedSubscription<>(subscriber, new PageFetcherImpl()));
    }

    private class PageFetcherImpl implements PaginatedSubscription.PageFetcher<ListObjectsV2Result> {

        @Override
        public boolean hasNextPage(ListObjectsV2Result previousPage) {
            if (previousPage == null || previousPage.isTruncated() == null) {
                return false;
            }
            return previousPage.isTruncated();
        }

        @Override
        public CompletableFuture<ListObjectsV2Result> nextPage(ListObjectsV2Result previousPage) {
            if (previousPage == null) {
                return client.listObjectsV2Async(firstRequest);
            }

            return client.listObjectsV2Async(
                    firstRequest.toBuilder()
                            .continuationToken(previousPage.nextContinuationToken())
                            .build());
        }
    }
}
