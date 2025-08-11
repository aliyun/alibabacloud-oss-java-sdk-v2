package com.aliyun.sdk.service.oss2.paginator;

import com.aliyun.sdk.service.oss2.OSSClient;
import com.aliyun.sdk.service.oss2.models.ListObjectsRequest;
import com.aliyun.sdk.service.oss2.models.ListObjectsResult;
import com.aliyun.sdk.service.oss2.utils.StringUtils;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

/**
 * A paginator for ListObjects
 * This class is an iterable of {ListObjectsResult} that can be used to iterate through all the
 * response pages of the operation.
 */
public class ListObjectsIterable implements PaginatedIterable<ListObjectsResult> {

    private final OSSClient client;

    private final ListObjectsRequest firstRequest;

    public ListObjectsIterable(OSSClient client, ListObjectsRequest request) {
        this(client, request, PaginatorOptions.defaults());
    }

    public ListObjectsIterable(OSSClient client, ListObjectsRequest request, PaginatorOptions options) {
        Objects.requireNonNull(client);
        Objects.requireNonNull(request);
        this.client = client;
        this.firstRequest = applyRequest(request, options);
    }

    ListObjectsRequest applyRequest(ListObjectsRequest request, PaginatorOptions options) {
        if (options.limit().isPresent()) {
            return request.toBuilder().maxKeys(options.limit().get()).build();
        }
        return request;
    }

    ListObjectsRequest cloneRequest() {
        return ListObjectsRequest.newBuilder()
                .bucket(this.firstRequest.bucket())
                .headers(this.firstRequest.headers())
                .parameters(this.firstRequest.parameters())
                .build();
    }

    @Override
    public Iterator<ListObjectsResult> iterator() {
        return new IteratorImpl(cloneRequest());
    }

    private class IteratorImpl implements Iterator<ListObjectsResult> {

        private boolean firstPage;
        private boolean isTruncated;
        private String nextMarker;
        private ListObjectsRequest request;

        IteratorImpl(ListObjectsRequest request) {
            this.firstPage = true;
            this.isTruncated = false;
            this.nextMarker = null;
            this.request = request;
        }

        @Override
        public boolean hasNext() {
            return (this.firstPage || this.isTruncated);
        }

        @Override
        public ListObjectsResult next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more pages left");
            }

            if (!StringUtils.isNullOrEmpty(this.nextMarker)) {
                this.request = this.request.toBuilder().marker(this.nextMarker).build();
            }

            ListObjectsResult result = client.listObjects(this.request);

            this.firstPage = false;
            this.isTruncated = Optional.ofNullable(result.isTruncated()).orElse(false);
            this.nextMarker = result.nextMarker();

            return result;
        }
    }
}
