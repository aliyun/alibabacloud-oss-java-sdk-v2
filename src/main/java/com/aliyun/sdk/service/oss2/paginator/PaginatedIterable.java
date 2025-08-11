package com.aliyun.sdk.service.oss2.paginator;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public interface PaginatedIterable<T> extends Iterable<T> {

    default Stream<T> stream() {
        return StreamSupport.stream(spliterator(), false);
    }
}
