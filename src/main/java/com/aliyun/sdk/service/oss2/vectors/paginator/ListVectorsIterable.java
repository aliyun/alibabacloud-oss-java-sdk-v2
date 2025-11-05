package com.aliyun.sdk.service.oss2.vectors.paginator;

import com.aliyun.sdk.service.oss2.paginator.PaginatedIterable;
import com.aliyun.sdk.service.oss2.paginator.PaginatorOptions;
import com.aliyun.sdk.service.oss2.vectors.OSSVectorsClient;
import com.aliyun.sdk.service.oss2.vectors.models.ListVectorsRequest;
import com.aliyun.sdk.service.oss2.vectors.models.ListVectorsResult;
import com.aliyun.sdk.service.oss2.utils.StringUtils;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * A paginator for ListVectors
 * This class is an iterable of {ListVectorsResult} that can be used to iterate through all the
 * response pages of the operation.
 */
public class ListVectorsIterable implements PaginatedIterable<ListVectorsResult> {

    private final OSSVectorsClient client;

    private final ListVectorsRequest firstRequest;

    public ListVectorsIterable(OSSVectorsClient client, ListVectorsRequest request) {
        this(client, request, PaginatorOptions.defaults());
    }

    public ListVectorsIterable(OSSVectorsClient client, ListVectorsRequest request, PaginatorOptions options) {
        Objects.requireNonNull(client);
        Objects.requireNonNull(request);
        this.client = client;
        this.firstRequest = applyRequest(request, options);
    }

    ListVectorsRequest applyRequest(ListVectorsRequest request, PaginatorOptions options) {
        if (options.limit().isPresent()) {
            return request.toBuilder().maxResults(options.limit().get().intValue()).build();
        }
        return request;
    }

    ListVectorsRequest cloneRequest() {
        return ListVectorsRequest.newBuilder()
                .bucket(this.firstRequest.bucket())
                .headers(this.firstRequest.headers())
                .parameters(this.firstRequest.parameters())
                .bodyFields(this.firstRequest.bodyFields())
                .build();
    }

    @Override
    public Iterator<ListVectorsResult> iterator() {
        return new IteratorImpl(cloneRequest());
    }

    private class IteratorImpl implements Iterator<ListVectorsResult> {

        private boolean firstPage;
        private boolean isTruncated;
        private String nextToken;
        private ListVectorsRequest request;

        IteratorImpl(ListVectorsRequest request) {
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
        public ListVectorsResult next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more pages left");
            }

            if (!StringUtils.isNullOrEmpty(this.nextToken)) {
                this.request = this.request.toBuilder().nextToken(this.nextToken).build();
            }

            ListVectorsResult result = client.listVectors(this.request);

            this.firstPage = false;
            this.nextToken = result.nextToken();
            this.isTruncated = !StringUtils.isNullOrEmpty(this.nextToken);

            return result;
        }
    }
}