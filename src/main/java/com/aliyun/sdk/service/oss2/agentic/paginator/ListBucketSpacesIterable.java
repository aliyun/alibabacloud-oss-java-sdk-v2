package com.aliyun.sdk.service.oss2.agentic.paginator;

import com.aliyun.sdk.service.oss2.agentic.OSSAgenticBucketClient;
import com.aliyun.sdk.service.oss2.agentic.models.ListBucketSpacesRequest;
import com.aliyun.sdk.service.oss2.agentic.models.ListBucketSpacesResult;
import com.aliyun.sdk.service.oss2.paginator.PaginatedIterable;
import com.aliyun.sdk.service.oss2.paginator.PaginatorOptions;
import com.aliyun.sdk.service.oss2.utils.StringUtils;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

/**
 * A paginator for ListBucketSpaces
 * This class is an iterable of {ListBucketSpacesResult} that can be used to iterate through all the
 * response pages of the operation.
 */
public class ListBucketSpacesIterable implements PaginatedIterable<ListBucketSpacesResult> {

    private final OSSAgenticBucketClient client;
    private final ListBucketSpacesRequest firstRequest;

    public ListBucketSpacesIterable(OSSAgenticBucketClient client, ListBucketSpacesRequest request) {
        this(client, request, PaginatorOptions.defaults());
    }

    public ListBucketSpacesIterable(OSSAgenticBucketClient client, ListBucketSpacesRequest request, PaginatorOptions options) {
        Objects.requireNonNull(client);
        Objects.requireNonNull(request);
        this.client = client;
        this.firstRequest = applyRequest(request, options);
    }

    ListBucketSpacesRequest applyRequest(ListBucketSpacesRequest request, PaginatorOptions options) {
        if (options.limit().isPresent()) {
            return request.toBuilder().maxKeys(options.limit().get()).build();
        }
        return request;
    }

    ListBucketSpacesRequest cloneRequest() {
        ListBucketSpacesRequest.Builder builder = ListBucketSpacesRequest.newBuilder()
                .headers(this.firstRequest.headers())
                .parameters(this.firstRequest.parameters());
        if (this.firstRequest.bucket() != null) {
            builder.bucket(this.firstRequest.bucket());
        }
        return builder.build();
    }

    @Override
    public Iterator<ListBucketSpacesResult> iterator() {
        return new IteratorImpl(cloneRequest());
    }

    private class IteratorImpl implements Iterator<ListBucketSpacesResult> {

        private boolean firstPage;
        private boolean isTruncated;
        private String continueToken;
        private ListBucketSpacesRequest request;

        IteratorImpl(ListBucketSpacesRequest request) {
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
        public ListBucketSpacesResult next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more pages left");
            }

            if (!StringUtils.isNullOrEmpty(this.continueToken)) {
                this.request = this.request.toBuilder().continuationToken(this.continueToken).build();
            }

            ListBucketSpacesResult result = client.listBucketSpaces(this.request);

            this.firstPage = false;
            this.isTruncated = Optional.ofNullable(result.isTruncated()).orElse(false);
            this.continueToken = result.nextContinuationToken();

            return result;
        }
    }
}
