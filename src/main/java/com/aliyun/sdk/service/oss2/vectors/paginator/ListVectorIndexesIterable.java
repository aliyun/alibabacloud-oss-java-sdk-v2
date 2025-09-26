package com.aliyun.sdk.service.oss2.vectors.paginator;

import com.aliyun.sdk.service.oss2.paginator.PaginatedIterable;
import com.aliyun.sdk.service.oss2.paginator.PaginatorOptions;
import com.aliyun.sdk.service.oss2.vectors.OSSVectorsClient;
import com.aliyun.sdk.service.oss2.vectors.models.ListVectorIndexesRequest;
import com.aliyun.sdk.service.oss2.vectors.models.ListVectorIndexesResult;
import com.aliyun.sdk.service.oss2.utils.StringUtils;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * A paginator for ListVectorIndexes
 * This class is an iterable of {ListVectorIndexesResult} that can be used to iterate through all the
 * response pages of the operation.
 */
public class ListVectorIndexesIterable implements PaginatedIterable<ListVectorIndexesResult> {

    private final OSSVectorsClient client;

    private final ListVectorIndexesRequest firstRequest;

    public ListVectorIndexesIterable(OSSVectorsClient client, ListVectorIndexesRequest request) {
        this(client, request, PaginatorOptions.defaults());
    }

    public ListVectorIndexesIterable(OSSVectorsClient client, ListVectorIndexesRequest request, PaginatorOptions options) {
        Objects.requireNonNull(client);
        Objects.requireNonNull(request);
        this.client = client;
        this.firstRequest = applyRequest(request, options);
    }

    ListVectorIndexesRequest applyRequest(ListVectorIndexesRequest request, PaginatorOptions options) {
        if (options.limit().isPresent()) {
            return request.toBuilder().maxResults(options.limit().get()).build();
        }
        return request;
    }

    ListVectorIndexesRequest cloneRequest() {
        return ListVectorIndexesRequest.newBuilder()
                .bucket(this.firstRequest.bucket())
                .headers(this.firstRequest.headers())
                .parameters(this.firstRequest.parameters())
                .maxResults(this.firstRequest.listVectorIndexesRequestJson().maxResults)
                .prefix(this.firstRequest.listVectorIndexesRequestJson().prefix)
                .build();
    }

    @Override
    public Iterator<ListVectorIndexesResult> iterator() {
        return new IteratorImpl(cloneRequest());
    }

    private class IteratorImpl implements Iterator<ListVectorIndexesResult> {

        private boolean firstPage;
        private boolean isTruncated;
        private String nextToken;
        private ListVectorIndexesRequest request;

        IteratorImpl(ListVectorIndexesRequest request) {
            this.firstPage = true;
            this.isTruncated = false;
            this.nextToken = null;
            this.request = request;
        }

        @Override
        public boolean hasNext() {
            return (this.firstPage || this.isTruncated);
        }

        @Override
        public ListVectorIndexesResult next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more pages left");
            }

            if (!StringUtils.isNullOrEmpty(this.nextToken)) {
                this.request = this.request.toBuilder().nextToken(this.nextToken).build();
            }

            ListVectorIndexesResult result = client.listVectorIndexes(this.request);

            this.firstPage = false;
            this.nextToken = result.nextToken();
            this.isTruncated = !StringUtils.isNullOrEmpty(this.nextToken);

            return result;
        }
    }
}

