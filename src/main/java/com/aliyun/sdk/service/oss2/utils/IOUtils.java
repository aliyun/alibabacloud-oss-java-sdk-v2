package com.aliyun.sdk.service.oss2.utils;

import com.aliyun.sdk.service.oss2.transport.InputStreamBinaryData;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Objects;

public class IOUtils {

    /**
     * The default buffer size ({@value}) to use in copy methods.
     */
    public static final int DEFAULT_BUFFER_SIZE = 8192;

    /**
     * Represents the end-of-file (or stream) value {@value}.
     */
    public static final int EOF = -1;

    /**
     * Internal byte array buffer, intended for write only operations.
     */
    private static final byte[] SCRATCH_BYTE_BUFFER_WO = byteArray();

    private IOUtils() {
    }

    public static OutputStream discradOutputStream() {
        return new OutputStream() {
            private volatile boolean closed;

            private void ensureOpen() throws IOException {
                if (closed) {
                    throw new IOException("Stream closed");
                }
            }

            @Override
            public void write(int b) throws IOException {
                ensureOpen();
            }

            @Override
            public void write(byte[] b, int off, int len) throws IOException {
                //Objects.checkFromIndexSize(off, len, b.length);
                ensureOpen();
            }

            @Override
            public void close() {
                closed = true;
            }
        };
    }

    /**
     * Closes a {@link AutoCloseable} unconditionally.
     *
     * @param is the object to close, may be null
     */
    public static void closeQuietly(AutoCloseable is) {
        if (is != null) {
            try {
                is.close();
            } catch (Exception ignored) {
            }
        }
    }

    /**
     * Closes a {@link Object} unconditionally.
     *
     * @param maybeCloseable the object to close, may be null
     */
    public static void closeIfCloseable(Object maybeCloseable) {
        if (maybeCloseable instanceof AutoCloseable) {
            closeQuietly((AutoCloseable) maybeCloseable);
        }
    }

    /**
     * Returns a new byte array of size {@link #DEFAULT_BUFFER_SIZE}.
     *
     * @return a new byte array of size {@link #DEFAULT_BUFFER_SIZE}.
     */
    public static byte[] byteArray() {
        return byteArray(DEFAULT_BUFFER_SIZE);
    }

    /**
     * Returns a new byte array of the given size.
     *
     * @param size array size.
     * @return a new byte array of the given size.
     * @throws NegativeArraySizeException if the size is negative.
     */
    public static byte[] byteArray(final int size) {
        return new byte[size];
    }

    /**
     * Copies bytes from a large {@link InputStream} to an {@link OutputStream}.
     *
     * @param inputStream  the {@link InputStream} to read.
     * @param outputStream the {@link OutputStream} to write.
     * @return the number of bytes copied.
     * @throws NullPointerException if the InputStream is {@code null}.
     * @throws NullPointerException if the OutputStream is {@code null}.
     * @throws IOException          if an I/O error occurs.
     */
    public static long copyLarge(final InputStream inputStream, final OutputStream outputStream)
            throws IOException {
        return copyLarge(inputStream, outputStream, byteArray(DEFAULT_BUFFER_SIZE));
    }

    /**
     * Copies bytes from a large {@link InputStream} to an {@link OutputStream}.
     *
     * @param inputStream  the {@link InputStream} to read.
     * @param outputStream the {@link OutputStream} to write.
     * @param buffer       the buffer to use for the copy
     * @return the number of bytes copied.
     * @throws NullPointerException if the InputStream is {@code null}.
     * @throws NullPointerException if the OutputStream is {@code null}.
     * @throws IOException          if an I/O error occurs.
     */
    public static long copyLarge(final InputStream inputStream, final OutputStream outputStream, final byte[] buffer)
            throws IOException {
        Objects.requireNonNull(inputStream, "inputStream");
        Objects.requireNonNull(outputStream, "outputStream");
        long count = 0;
        int n;
        while (EOF != (n = inputStream.read(buffer))) {
            outputStream.write(buffer, 0, n);
            count += n;
        }
        return count;
    }

    /**
     * Copies bytes from an {@link InputStream} to an {@link OutputStream} using an internal buffer of the
     * given size.
     *
     * @param inputStream  the {@link InputStream} to read.
     * @param outputStream the {@link OutputStream} to write to
     * @param bufferSize   the bufferSize used to copy from the input to the output
     * @return the number of bytes copied.
     * @throws NullPointerException if the InputStream is {@code null}.
     * @throws NullPointerException if the OutputStream is {@code null}.
     * @throws IOException          if an I/O error occurs.
     */
    public static long copy(final InputStream inputStream, final OutputStream outputStream, final int bufferSize)
            throws IOException {
        return copyLarge(inputStream, outputStream, byteArray(bufferSize));
    }

    /**
     * Copies some or all bytes from a large {@link InputStream} to an
     * {@link OutputStream}, optionally skipping input bytes.
     *
     * @param input       the {@link InputStream} to read.
     * @param output      the {@link OutputStream} to write.
     * @param inputOffset number of bytes to skip from input before copying, these bytes are ignored.
     * @param length      number of bytes to copy.
     * @param buffer      the buffer to use for the copy.
     * @return the number of bytes copied.
     * @throws NullPointerException if the input or output is null.
     * @throws IOException          if an I/O error occurs.
     */
    public static long copyLarge(final InputStream input, final OutputStream output,
                                 final long inputOffset, final long length, final byte[] buffer) throws IOException {
        if (inputOffset > 0) {
            skipFully(input, inputOffset);
        }
        if (length == 0) {
            return 0;
        }
        final int bufferLength = buffer.length;
        int bytesToRead = bufferLength;
        if (length > 0 && length < bufferLength) {
            bytesToRead = (int) length;
        }
        int read;
        long totalRead = 0;
        while (bytesToRead > 0 && EOF != (read = input.read(buffer, 0, bytesToRead))) {
            output.write(buffer, 0, read);
            totalRead += read;
            if (length > 0) { // only adjust length if not reading to the end
                // Note the cast must work because buffer.length is an integer
                bytesToRead = (int) Math.min(length - totalRead, bufferLength);
            }
        }
        return totalRead;
    }

    /**
     * Copies some or all chars from a large {@link InputStream} to an
     * {@link OutputStream}, optionally skipping input chars.
     *
     * @param input       the {@link InputStream} to read.
     * @param output      the {@link OutputStream} to write.
     * @param inputOffset number of chars to skip from input before copying
     *                    -ve values are ignored
     * @param length      number of chars to copy. -ve means all
     * @return the number of chars copied
     * @throws NullPointerException if the input or output is null
     * @throws IOException          if an I/O error occurs
     */
    public static long copyLarge(final InputStream input, final OutputStream output, final long inputOffset, final long length)
            throws IOException {
        return copyLarge(input, output, inputOffset, length, byteArray());
    }

    /**
     * Skips bytes from an input byte stream.
     *
     * @param input      byte stream to skip
     * @param skip       number of bytes to skip.
     * @param skipBuffer the buffer to use for reading.
     * @return number of bytes actually skipped.
     * @throws IOException              if there is a problem reading the file
     * @throws IllegalArgumentException if toSkip is negative
     * @see InputStream#skip(long)
     */
    public static long skip(final InputStream input, final long skip, byte[] skipBuffer) throws IOException {
        if (skip < 0) {
            throw new IllegalArgumentException("Skip count must be non-negative, actual: " + skip);
        }

        if (skipBuffer == null) {
            skipBuffer = SCRATCH_BYTE_BUFFER_WO;
        }

        long remain = skip;
        while (remain > 0) {
            final long n = input.read(skipBuffer, 0, (int) Math.min(remain, skipBuffer.length));
            if (n < 0) { // EOF
                break;
            }
            remain -= n;
        }
        return skip - remain;
    }

    /**
     * Skips the requested number of bytes or fail if there are not enough left.
     *
     * @param input  stream to skip
     * @param toSkip the number of bytes to skip
     * @throws IOException              if there is a problem reading the file
     * @throws IllegalArgumentException if toSkip is negative
     * @throws EOFException             if the number of bytes skipped was incorrect
     * @see InputStream#skip(long)
     */
    public static void skipFully(final InputStream input, final long toSkip) throws IOException {
        final long skipped = skip(input, toSkip, SCRATCH_BYTE_BUFFER_WO);
        if (skipped != toSkip) {
            throw new EOFException("Bytes to skip: " + toSkip + " actual: " + skipped);
        }
    }


    /**
     * Consumes bytes from a {@link InputStream} and ignores them.
     *
     * @param input the {@link InputStream} to read.
     * @return the number of bytes copied. or {@code 0} if {@code input is null}.
     * @throws NullPointerException if the InputStream is {@code null}.
     * @throws IOException          if an I/O error occurs.
     */
    public static long consume(final InputStream input) throws IOException {
        return copyLarge(input, discradOutputStream(), byteArray());
    }

    /**
     * Gets the contents of an {@link InputStream} as a {@code byte[]}.
     *
     * @param inputStream the {@link InputStream} to read.
     * @return the requested byte array.
     */
    public static byte[] toByteArray(final InputStream inputStream) {
        if (inputStream == null) {
            return new byte[0];
        }

        try {
            AccessibleByteArrayOutputStream dataOutputBuffer = new AccessibleByteArrayOutputStream();
            copyLarge(inputStream, dataOutputBuffer);
            return dataOutputBuffer.toByteArrayUnsafe();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    static class AccessibleByteArrayOutputStream extends ByteArrayOutputStream {
        public AccessibleByteArrayOutputStream() {
            super();
        }
        public AccessibleByteArrayOutputStream(int initialCapacity) {
            super(initialCapacity);
        }
        @Override
        public byte[] toByteArray() {
            return Arrays.copyOf(buf, count);
        }
        public byte[] toByteArrayUnsafe() {
            return (buf.length == count) ? buf : toByteArray();
        }
        public ByteBuffer toByteBuffer() {
            return ByteBuffer.wrap(buf, 0, count).asReadOnlyBuffer();
        }
        public int count() {
            return count;
        }
    }
}