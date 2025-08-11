package com.aliyun.sdk.service.oss2.paginator;

import com.aliyun.sdk.service.oss2.OSSClient;
import com.aliyun.sdk.service.oss2.models.ListObjectVersionsRequest;
import com.aliyun.sdk.service.oss2.models.ListObjectVersionsResult;
import com.aliyun.sdk.service.oss2.utils.StringUtils;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

/**
 * A paginator for ListObjectVersions
 * This class is an iterable of {ListObjectVersionsResult} that can be used to iterate through all the
 * response pages of the operation.
 */
public class ListObjectVersionsIterable implements PaginatedIterable<ListObjectVersionsResult> {

    private final OSSClient client;

    private final ListObjectVersionsRequest firstRequest;

    public ListObjectVersionsIterable(OSSClient client, ListObjectVersionsRequest request) {
        this(client, request, PaginatorOptions.defaults());
    }

    public ListObjectVersionsIterable(OSSClient client, ListObjectVersionsRequest request, PaginatorOptions options) {
        Objects.requireNonNull(client);
        Objects.requireNonNull(request);
        this.client = client;
        this.firstRequest = applyRequest(request, options);
    }

    ListObjectVersionsRequest applyRequest(ListObjectVersionsRequest request, PaginatorOptions options) {
        if (options.limit().isPresent()) {
            return request.toBuilder().maxKeys(options.limit().get()).build();
        }
        return request;
    }

    ListObjectVersionsRequest cloneRequest() {
        return ListObjectVersionsRequest.newBuilder()
                .bucket(this.firstRequest.bucket())
                .headers(this.firstRequest.headers())
                .parameters(this.firstRequest.parameters())
                .build();
    }

    @Override
    public Iterator<ListObjectVersionsResult> iterator() {
        return new IteratorImpl(cloneRequest());
    }

    private class IteratorImpl implements Iterator<ListObjectVersionsResult> {

        private boolean firstPage;
        private boolean isTruncated;
        private String nextKeyMarker;
        private String nextVersionIdMarker;
        private ListObjectVersionsRequest request;

        IteratorImpl(ListObjectVersionsRequest request) {
            this.firstPage = true;
            this.isTruncated = false;
            this.nextKeyMarker = null;
            this.nextVersionIdMarker = null;
            this.request = request;
        }

        @Override
        public boolean hasNext() {
            return (this.firstPage || this.isTruncated);
        }

        @Override
        public ListObjectVersionsResult next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more pages left");
            }

            if (!StringUtils.isNullOrEmpty(this.nextKeyMarker)) {
                this.request = this.request.toBuilder()
                        .keyMarker(this.nextKeyMarker)
                        .versionIdMarker(this.nextVersionIdMarker)
                        .build();
            }

            ListObjectVersionsResult result = client.listObjectVersions(this.request);

            this.firstPage = false;
            this.isTruncated = Optional.ofNullable(result.isTruncated()).orElse(false);
            this.nextKeyMarker = result.nextKeyMarker();
            this.nextVersionIdMarker = result.nextVersionIdMarker();

            return result;
        }
    }
}
