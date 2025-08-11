package com.aliyun.sdk.service.oss2.transport;

import java.io.FilterInputStream;
import java.io.InputStream;

public final class AbortableInputStream extends FilterInputStream implements Abortable {

    private final Abortable abortable;

    private AbortableInputStream(InputStream delegate, Abortable abortable) {
        super(delegate);
        this.abortable = abortable;
    }

    /**
     * Creates an instance of {@link AbortableInputStream}.
     *
     * @param delegate the delegated input stream
     * @param abortable the abortable
     * @return a new instance of AbortableInputStream
     */
    public static AbortableInputStream create(InputStream delegate, Abortable abortable) {
        return new AbortableInputStream(delegate, abortable);
    }

    @Override
    public void abort() {
        abortable.abort();
    }

    /**
     * Access the underlying delegate stream
     */
    public InputStream unwarp() {
        return in;
    }

}