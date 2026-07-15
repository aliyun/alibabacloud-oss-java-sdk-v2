package com.aliyun.sdk.service.oss2.agentic.paginator;

import com.aliyun.sdk.service.oss2.agentic.OSSAgenticBucketClient;
import com.aliyun.sdk.service.oss2.agentic.models.ListAgenticBucketsRequest;
import com.aliyun.sdk.service.oss2.agentic.models.ListAgenticBucketsResult;
import com.aliyun.sdk.service.oss2.paginator.PaginatedIterable;
import com.aliyun.sdk.service.oss2.paginator.PaginatorOptions;
import com.aliyun.sdk.service.oss2.utils.StringUtils;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

/**
 * A paginator for ListAgenticBuckets
 * This class is an iterable of {ListAgenticBucketsResult} that can be used to iterate through all the
 * response pages of the operation.
 */
public class ListAgenticBucketsIterable implements PaginatedIterable<ListAgenticBucketsResult> {

    private final OSSAgenticBucketClient client;
    private final ListAgenticBucketsRequest firstRequest;

    public ListAgenticBucketsIterable(OSSAgenticBucketClient client, ListAgenticBucketsRequest request) {
        this(client, request, PaginatorOptions.defaults());
    }

    public ListAgenticBucketsIterable(OSSAgenticBucketClient client, ListAgenticBucketsRequest request, PaginatorOptions options) {
        Objects.requireNonNull(client);
        Objects.requireNonNull(request);
        this.client = client;
        this.firstRequest = applyRequest(request, options);
    }

    ListAgenticBucketsRequest applyRequest(ListAgenticBucketsRequest request, PaginatorOptions options) {
        if (options.limit().isPresent()) {
            return request.toBuilder().maxKeys(options.limit().get()).build();
        }
        return request;
    }

    ListAgenticBucketsRequest cloneRequest() {
        return ListAgenticBucketsRequest.newBuilder()
                .headers(this.firstRequest.headers())
                .parameters(this.firstRequest.parameters())
                .build();
    }

    @Override
    public Iterator<ListAgenticBucketsResult> iterator() {
        return new IteratorImpl(cloneRequest());
    }

    private class IteratorImpl implements Iterator<ListAgenticBucketsResult> {

        private boolean firstPage;
        private boolean isTruncated;
        private String continueToken;
        private ListAgenticBucketsRequest request;

        IteratorImpl(ListAgenticBucketsRequest request) {
            this.firstPage = true;
            this.isTruncated = false;
            this.continueToken = null;
            this.request = request;
        }

        @Override
        public boolean hasNext() {
            return (this.firstPage || this.isTruncated);
        }

        @Override
        public ListAgenticBucketsResult next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more pages left");
            }

            if (!StringUtils.isNullOrEmpty(this.continueToken)) {
                this.request = this.request.toBuilder().continuationToken(this.continueToken).build();
            }

            ListAgenticBucketsResult result = client.listAgenticBuckets(this.request);

            this.firstPage = false;
            this.isTruncated = Optional.ofNullable(result.isTruncated()).orElse(false);
            this.continueToken = result.nextContinuationToken();

            return result;
        }
    }
}
