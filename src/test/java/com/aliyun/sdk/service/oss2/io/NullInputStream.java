package com.aliyun.sdk.service.oss2.io;


import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

import static com.aliyun.sdk.service.oss2.utils.IOUtils.EOF;

public class NullInputStream extends InputStream {

    private final long size;
    private final boolean throwEofException;
    private final boolean markSupported;
    private boolean closed;
    private long position;
    private long mark = -1;
    private long readLimit;

    public NullInputStream() {
        this(0, true, false);
    }

    public NullInputStream(final long size) {
        this(size, true, false);
    }

    public NullInputStream(final long size, final boolean markSupported, final boolean throwEofException) {
        this.size = size;
        this.markSupported = markSupported;
        this.throwEofException = throwEofException;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(final boolean closed) {
        this.closed = closed;
    }

    void checkOpen() throws IOException {
        if (isClosed()) {
            throw new IOException("Closed");
        }
    }

    @Override
    public int available() {
        if (isClosed()) {
            return 0;
        }
        final long avail = size - position;
        if (avail <= 0) {
            return 0;
        }
        if (avail > Integer.MAX_VALUE) {
            return Integer.MAX_VALUE;
        }
        return (int) avail;
    }

    /**
     * Throws {@link EOFException} if {@code throwEofException} is enabled.
     *
     * @param message The {@link EOFException} message.
     * @throws EOFException Thrown if {@code throwEofException} is enabled.
     */
    private void checkThrowEof(final String message) throws EOFException {
        if (throwEofException) {
            throw new EOFException(message);
        }
    }

    /**
     * Closes this input stream.
     *
     * @throws IOException If an error occurs.
     */
    @Override
    public void close() throws IOException {
        super.close();
        closed = true;
        mark = -1;
    }

    /**
     * Gets the current position.
     *
     * @return the current position.
     */
    public long getPosition() {
        return position;
    }

    /**
     * Gets the size this {@link InputStream} emulates.
     *
     * @return The size of the input stream to emulate.
     */
    public long getSize() {
        return size;
    }

    /**
     * Handles End of File.
     *
     * @return {@code -1} if {@code throwEofException} is set to {@code false}
     * @throws IOException if {@code throwEofException} is set to {@code true}.
     */
    private int handleEof() throws IOException {
        checkThrowEof("handleEof()");
        return EOF;
    }

    /**
     * Initializes or re-initializes this instance for reuse.
     *
     * @return this instance.
     */
    public NullInputStream init() {
        setClosed(false);
        position = 0;
        mark = -1;
        readLimit = 0;
        return this;
    }

    /**
     * Marks the current position.
     *
     * @param readLimit The number of bytes before this marked position is invalid.
     * @throws UnsupportedOperationException if mark is not supported.
     */
    @Override
    public synchronized void mark(final int readLimit) {
        if (!markSupported) {
            throw new UnsupportedOperationException("mark not supported");
        }
        mark = position;
        this.readLimit = readLimit;
    }

    /**
     * Tests whether <em>mark</em> is supported.
     *
     * @return Whether <em>mark</em> is supported or not.
     */
    @Override
    public boolean markSupported() {
        return markSupported;
    }

    /**
     * Returns a byte value for the {@code read()} method.
     * <p>
     * This implementation returns zero.
     * </p>
     *
     * @return This implementation always returns zero.
     */
    protected int processByte() {
        // do nothing - overridable by subclass
        return 0;
    }

    /**
     * Processes the bytes for the {@code read(byte[], offset, length)} method.
     * <p>
     * This implementation leaves the byte array unchanged.
     * </p>
     *
     * @param bytes  The byte array
     * @param offset The offset to start at.
     * @param length The number of bytes.
     */
    protected void processBytes(final byte[] bytes, final int offset, final int length) {
        // do nothing - overridable by subclass
    }

    /**
     * Reads a byte.
     *
     * @return Either The byte value returned by {@code processByte()} or {@code -1} if the end of file has been reached and {@code throwEofException} is set to
     * {@code false}.
     * @throws EOFException if the end of file is reached and {@code throwEofException} is set to {@code true}.
     * @throws IOException  if trying to read past the end of file.
     */
    @Override
    public int read() throws IOException {
        checkOpen();
        if (position == size) {
            return handleEof();
        }
        position++;
        return processByte();
    }

    /**
     * Reads some bytes into the specified array.
     *
     * @param bytes The byte array to read into
     * @return The number of bytes read or {@code -1} if the end of file has been reached and {@code throwEofException} is set to {@code false}.
     * @throws EOFException if the end of file is reached and {@code throwEofException} is set to {@code true}.
     * @throws IOException  if trying to read past the end of file.
     */
    @Override
    public int read(final byte[] bytes) throws IOException {
        return read(bytes, 0, bytes.length);
    }

    /**
     * Reads the specified number bytes into an array.
     *
     * @param bytes  The byte array to read into.
     * @param offset The offset to start reading bytes into.
     * @param length The number of bytes to read.
     * @return The number of bytes read or {@code -1} if the end of file has been reached and {@code throwEofException} is set to {@code false}.
     * @throws EOFException if the end of file is reached and {@code throwEofException} is set to {@code true}.
     * @throws IOException  if trying to read past the end of file.
     */
    @Override
    public int read(final byte[] bytes, final int offset, final int length) throws IOException {
        if (bytes.length == 0 || length == 0) {
            return 0;
        }
        checkOpen();
        if (position == size) {
            return handleEof();
        }
        position += length;
        int returnLength = length;
        if (position > size) {
            returnLength = length - (int) (position - size);
            position = size;
        }
        processBytes(bytes, offset, returnLength);
        return returnLength;
    }

    /**
     * Resets the stream to the point when mark was last called.
     *
     * @throws UnsupportedOperationException if mark is not supported.
     * @throws IOException                   If no position has been marked or the read limit has been exceeded since the last position was marked.
     */
    @Override
    public synchronized void reset() throws IOException {
        if (!markSupported) {
            throw new UnsupportedOperationException("reset not supported");
        }
        if (mark < 0) {
            throw new IOException("No position has been marked");
        }
        if (position > mark + readLimit) {
            throw new IOException("Marked position [" + mark + "] is no longer valid - passed the read limit [" + readLimit + "]");
        }
        position = mark;
        setClosed(false);
    }

    /**
     * Skips a specified number of bytes.
     *
     * @param numberOfBytes The number of bytes to skip.
     * @return The number of bytes skipped or {@code -1} if the end of file has been reached and {@code throwEofException} is set to {@code false}.
     * @throws EOFException if the end of file is reached and {@code throwEofException} is set to {@code true}.
     * @throws IOException  if trying to read past the end of file.
     */
    @Override
    public long skip(final long numberOfBytes) throws IOException {
        if (isClosed()) {
            checkThrowEof("skip(long)");
            return EOF;
        }
        if (position == size) {
            return handleEof();
        }
        position += numberOfBytes;
        long returnLength = numberOfBytes;
        if (position > size) {
            returnLength = numberOfBytes - (position - size);
            position = size;
        }
        return returnLength;
    }

}