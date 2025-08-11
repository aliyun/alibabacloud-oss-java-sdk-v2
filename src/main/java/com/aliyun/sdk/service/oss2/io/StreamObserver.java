package com.aliyun.sdk.service.oss2.io;

import java.io.IOException;

public abstract class StreamObserver {

    /**
     * Constructs a new instance for subclasses.
     */
    public StreamObserver() {
        // empty
    }

    /**
     * Called to indicate that the Stream has been closed.
     *
     * @throws IOException if an I/O error occurs.
     */
    public void closed() throws IOException {
        // noop
    }

    /**
     * Called to indicate that Stream's read have been called, and are about to invoke data.
     *
     * @param buffer The byte array, which has been passed to the read call, and where data has been stored.
     * @param offset The offset within the byte array, where data has been stored.
     * @param length The number of bytes, which have been stored in the byte array.
     * @throws IOException if an I/O error occurs.
     */
    public void data(final byte[] buffer, final int offset, final int length) throws IOException {
        // noop
    }

    /**
     * Called to indicate, that Stream's read has been invoked and will return a value.
     *
     * @param value The value, which is being returned. This will never be -1 (EOF), because, in that case,
     *              {@link #finished()} will be invoked instead.
     * @throws IOException if an I/O error occurs.
     */
    public void data(final int value) throws IOException {
        // noop
    }

    /**
     * Called to indicate that an error occurred on the underlying stream.
     *
     * @param exception the exception to throw
     * @throws IOException if an I/O error occurs.
     */
    public void error(final IOException exception) throws IOException {
        throw exception;
    }

    /**
     * Called to indicate that EOF has been seen on the underlying stream. This method may be called multiple times,
     * if the reader keeps invoking either of the read methods, and they will consequently keep returning EOF.
     *
     * @throws IOException if an I/O error occurs.
     */
    public void finished() throws IOException {
        // noop
    }

    /**
     * Called to indicate that the state of the underlying stream is reset.
     */
    public void reset() {
        // noop
    }
}
